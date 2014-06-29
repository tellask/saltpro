package com.subject.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import marst.component.FinalString;
import marst.util.staticlass.GeneralException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.subject.biz.SystemUserMenusService;
import com.subject.dao.SystemMenusDao;
import com.subject.dao.SystemRoleMenusDao;
import com.subject.dao.SystemUserMenusDao;
import com.subject.pojo.SystemMenus;
import com.subject.pojo.SystemRolemenus;
import com.subject.pojo.SystemUserMenus;

@Service(value="systemUserMenusService")
@WebService(serviceName="systemUserMenusService",endpointInterface="com.subject.biz.SystemUserMenusService")
public class SystemUserMenusServiceImpl implements SystemUserMenusService {
	@Autowired
	private SystemUserMenusDao systemUserMenusDao;
	@Autowired
	private SystemRoleMenusDao systemRoleMenusDao;
	@Autowired
	private SystemMenusDao systemMenusDao;

	@Override
	public List<SystemUserMenus> getList(SystemUserMenus systemUserauth) {
		List<SystemUserMenus> res=null;
		try {
			res=systemUserMenusDao.getList(systemUserauth);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
	@Override
	public void clearMenus(int userId) {
		List<SystemMenus> authInfoList = userAuths.get(userId);
		if(authInfoList!=null){
			userAuths.remove(userId);
		}
	}

	@Override
	public List<SystemMenus> getMenusListByUserId(int userId) {
		loadAuths();
		// 1. 先从缓存中查找
		List<SystemMenus> authInfoList = userAuths.get(userId);
		if (authInfoList == null) {	// 2. 缓冲中没有，再从AuthProvider 接口获取
			// 2.1 创建用户的权限集合
			Set<SystemMenus> authSet = new HashSet<SystemMenus>();
			// 2.2 将用户的直接权限加入集合
			try {
				List<Integer> userAuthList =systemUserMenusDao.getMenusIdByUserId(userId);
				if (userAuthList != null) {
					for (Object authId : userAuthList) {
						SystemMenus ai = auths.get(authId);
						if (ai != null) {
							authSet.add(ai);
						}
					}
				}
				
				// 2.3 将用户角色包含的权限加入集合
				List<Integer> userRoleList = systemUserMenusDao.getRolesIdByUserId(userId);
				if (userRoleList != null) {
					for (Object roleId : userRoleList) {
						List<SystemMenus> aiList = roles.get(roleId);
						if (aiList != null) {
							authSet.addAll(aiList);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 2.4 生成权限列表，放入Map中
			authInfoList = new ArrayList<SystemMenus>(authSet);
			userAuths.put(userId, authInfoList);
		}
		
		// 3. 返回用户的权限列表
		return authInfoList;
	}
	// 加载系统的权限数据
	private void loadAuths() {
			// 3. 获取系统的所有权限,放入auths Map中
			auths.clear();
			try {
				List<SystemMenus> authList = systemMenusDao.getAllList();
				if (authList == null || authList.size() < 1) {
					GeneralException e = new GeneralException("AuthProvider.getAuths() returns null, system has no any auth defined!");
					e.printStackTrace();
					throw e;
				}
				for (SystemMenus auth : authList) {
					Object authId = auth.getId();
					//String url = auth.getMenusUrl();
					//SystemMenus ai = new SystemMenus(authId, url);
					auths.put(authId, auth);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// 4. 获取系统的所有角色，解析角色包含的所有权限
			reloadRoleAuths();
		}
	public void reloadRoleAuths() {
		roles = loadRoleAuths();
	}
	private Map<Object, List<SystemMenus>> loadRoleAuths()  {
		Map<Object, List<SystemMenus>> roleAuthMap = new Hashtable<Object, List<SystemMenus>>();
		try {
			// 1. 从AuthProvider 接口查询系统的所有角色权限关系
			List<SystemRolemenus> roleAuths = systemRoleMenusDao.getAllList();
			if (roleAuths != null) {
				// 2. key角色ID，set菜单ID集合
				Map<Object, Set> roleMap = parseRoleAuths(roleAuths);
				
				// 3. 根据AuthId查找AuthInfo，组成链表，放入roles中
				Set<Map.Entry<Object, Set>> entrySet = roleMap.entrySet();
				for (Map.Entry<Object, Set> entry : entrySet) {
					Set authIdSet = entry.getValue();
					List<SystemMenus> authInfoList = new ArrayList<SystemMenus>();
					for (Object authId : authIdSet) {
						authInfoList.add(auths.get(authId));
					}
					
					Object roleId = entry.getKey();
					roleAuthMap.put(roleId, authInfoList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return roleAuthMap;
	}
	// key角色ID，set菜单ID集合
	private Map<Object, Set> parseRoleAuths(List<SystemRolemenus> roleAuths) {
		Map<Object, Set> roleMap = new HashMap<Object, Set>();
		for (SystemRolemenus roleAuth : roleAuths) {
			int roleId = roleAuth.getRoleId();
			Set authIdSet = roleMap.get(roleId);
			if (authIdSet == null) {
				authIdSet = new HashSet();
				getRoleAuths(roleId, roleAuths, authIdSet);
				
				roleMap.put(roleId, authIdSet);
			}
		}
		
		return roleMap;
	}
	// 将RoleId包含的权限id放入authIdSet中，
	// 对包含的角色逐级解析出权限，也放入authIdSet中
	private void getRoleAuths(int roleId, List<SystemRolemenus> systemRoleMenusList, Set authIdSet) {
		for (SystemRolemenus systemRolemenus : systemRoleMenusList) {
			if (systemRolemenus.getRoleId() == roleId) {
				if (systemRolemenus.getAuthFlag() ==FinalString.ACCOUNT_AUTH) {
					authIdSet.add(systemRolemenus.getMenusId());
				} else {
					getRoleAuths(systemRolemenus.getMenusId(), systemRoleMenusList, authIdSet);
				}
			}
		}
	}
	
	public SystemUserMenusDao getSystemUserMenusDao() {
		return systemUserMenusDao;
	}


	public void setSystemUserMenusDao(SystemUserMenusDao systemUserMenusDao) {
		this.systemUserMenusDao = systemUserMenusDao;
	}


	public SystemRoleMenusDao getSystemRoleMenusDao() {
		return systemRoleMenusDao;
	}


	public void setSystemRoleMenusDao(SystemRoleMenusDao systemRoleMenusDao) {
		this.systemRoleMenusDao = systemRoleMenusDao;
	}


	public SystemMenusDao getSystemMenusDao() {
		return systemMenusDao;
	}


	public void setSystemMenusDao(SystemMenusDao systemMenusDao) {
		this.systemMenusDao = systemMenusDao;
	}

	// 系统中所有的权限，authId 作为Key, SystemMenus作为Value
	private Map<Object, SystemMenus> auths = new Hashtable<Object, SystemMenus>();
	
	// 系统中所有的角色，roleId 作为key，List<SystemMenus>作为Value
	private Map<Object, List<SystemMenus>> roles  = null;
		
	// 用户权限，userId作为Key，List中的对象为SystemMenus
	private Map<Object, List<SystemMenus>> userAuths = new Hashtable<Object, List<SystemMenus>>();

	
}
