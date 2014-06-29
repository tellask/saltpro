package com.subject.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import marst.component.FinalString;
import marst.component.SystemMenusComparator;
import marst.util.ComObjUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.subject.biz.AccountUserService;
import com.subject.biz.SystemUserMenusService;
import com.subject.pojo.AccountUser;
import com.subject.pojo.SystemMenus;

import marst.util.MD5;
import marst.util.json.JsonConverter;

@Controller
@RequestMapping(value = "/page/login.do")
public class LoginController {
	@Autowired
	private AccountUserService accountUserService;
	@Autowired
	private SystemUserMenusService systemUserMenusService;
	
	@RequestMapping(params="method=firstload", method = RequestMethod.GET)
	public String firstload(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("转到登录页面");
		return "login";
	}
	@RequestMapping(params="method=goreg", method = RequestMethod.GET)
	public String goload(HttpServletRequest request,HttpServletResponse response) {
		System.out.println("转到注册页面");
		return "registration";
	}
	@RequestMapping(params="method=vfuserName", method = RequestMethod.POST)
	public String verifyUerName(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		String accountNum=request.getParameter("accountNum");
		AccountUser accountUser=new AccountUser();
		accountUser.setAccountNum(accountNum);
		AccountUser currUser=accountUserService.getInfoByUserName(accountNum);
		if(ComObjUtil.isEmpty(currUser)){
			map.addAttribute("msgVal", "账号不存在，请注册！");
			return "login";
		}else{
			return "login";
		}
	}
	
	@RequestMapping(params="method=login", method = RequestMethod.POST)
	public String userLogin(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		String accountNum=request.getParameter("accountNum");
		String pwd=request.getParameter("psw");
		//密码加密
		String MDpwd=MD5.getMD5ofStr(pwd);
		AccountUser accountUser=new AccountUser();
		accountUser.setAccountNum(accountNum);
		accountUser.setPassWord(MDpwd);
		AccountUser currUser=accountUserService.getInfoByUser(accountUser);
		if(!ComObjUtil.isEmpty(currUser)){
			request.getSession().setAttribute("loginMan", currUser);
			//登录成功，获得相关权限,根据权限转到不同页面
			if(currUser.getAccountType()==FinalString.ACCOUNT_OPERATOR){
				//普通用户
				return "pageform/operator/index";
			}else if(currUser.getAccountType()==FinalString.ACCOUNT_MANAGER){
				//管理员
				return "pageform/manager/index";
			}else if(currUser.getAccountType()==FinalString.ACCOUNT_ADMIN){
				//超级管理员
				return "pageform/admin/index";
			}else {
				return "login";
			}
		}else{
			map.addAttribute("msgVal", "登录失败，请喝管理员联系！");
			return "login";
			
		}
	}
	/**
	 * 加载菜单
	 * @param request
	 * @param response
	 * @param map
	 */
	@RequestMapping(params="method=loadMenus", method = RequestMethod.GET)
	public void loadMenus(HttpServletRequest request,HttpServletResponse response,ModelMap map){
		if(!ComObjUtil.isEmpty(request.getSession().getAttribute("loginMan"))){
			AccountUser currUser=(AccountUser) request.getSession().getAttribute("loginMan");
			List<SystemMenus> menusList=systemUserMenusService.getMenusListByUserId(currUser.getId());
			java.util.Collections.sort(menusList, new SystemMenusComparator()); //进行排序
			String jsonStr= JsonConverter.obj2Json(menusList);	//转化为json格式
			//返回json格式
			response.setContentType("application/x-json");
		    PrintWriter out;
			try {
				out = response.getWriter();
				out.print(jsonStr);
			    out.flush();   // 必须有这行，不然页面不会显示
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request,HttpServletResponse response,ModelMap map){
		if(!ComObjUtil.isEmpty(request.getSession().getAttribute("loginMan"))){
			AccountUser accountUser=(AccountUser) request.getSession().getAttribute("loginMan");
			//清理缓存的菜单项
			systemUserMenusService.clearMenus(accountUser.getId());
			//清理登录用户
			request.getSession().removeAttribute("loginMan");
		}
		if(!ComObjUtil.isEmpty(request.getSession().getAttribute("menusList"))){
			//清理菜单集合
			request.getSession().removeAttribute("menusList");
		}
		return "index";
	}
	/**
	 * 注册用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params="method=regtion", method = RequestMethod.POST)
	public String userRegistration(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		String accountNum=request.getParameter("accountNum");
		String userName=request.getParameter("userName");
		String pwd=request.getParameter("pwd");
		AccountUser currUser=accountUserService.getInfoByUserName(accountNum);
		if(ComObjUtil.isEmpty(currUser)){
			//如果账号未重复则注册
			//密码加密
			String MDpwd=MD5.getMD5ofStr(pwd);
			AccountUser newAcount=new AccountUser();
			newAcount.setAccountNum(accountNum);
			newAcount.setUserName(userName);
			newAcount.setPassWord(MDpwd);
			boolean res=accountUserService.insertInfo(newAcount);
			if(res){
				map.addAttribute("msgVal", "注册成功!");
				return "transitionpage";
			}else{
				map.addAttribute("msgVal", "账号失败，请联系管理员！");
				return "registration";
			}
		}else{
			map.addAttribute("msgVal", "账号重复，请重新填写！");
			return "registration";
		}
	}
	public AccountUserService getAccountUserService() {
		return accountUserService;
	}
	public void setAccountUserService(AccountUserService accountUserService) {
		this.accountUserService = accountUserService;
	}
	public SystemUserMenusService getSystemUserMenusService() {
		return systemUserMenusService;
	}
	public void setSystemUserMenusService(
			SystemUserMenusService systemUserMenusService) {
		this.systemUserMenusService = systemUserMenusService;
	}
	
}
