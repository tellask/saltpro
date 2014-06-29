package com.subject.controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import marst.util.ComObjUtil;
import marst.util.ComStringUtil;
import marst.util.json.JsonConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dbcom.dataset.QueryPageResult;
import com.subject.biz.SystemMenusService;
import com.subject.biz.SystemRightsTypeService;
import com.subject.biz.SystemUserMenusService;
import com.subject.pojo.AccountUser;
import com.subject.pojo.SystemMenus;

@Controller
@RequestMapping(value = "/page/menus.do")
public class MenusListController {
	@Autowired
	private SystemMenusService systemMenusService;
	@Autowired
	private SystemRightsTypeService systemRightsTypeService;
	@Autowired
	private SystemUserMenusService systemUserMenusService;
	
	private AccountUser loginMan=null;
	/**
	 * 页面加载时
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@RequestMapping(params="method=loadPage", method = RequestMethod.GET)
	public String loadPage(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		if(!ComObjUtil.isEmpty(request.getSession().getAttribute("loginMan"))){
			loginMan=(AccountUser) request.getSession().getAttribute("loginMan");
		}
		return "pageform/admin/setup/rightsmanage/menuslist";
	}
	@RequestMapping(params="method=toAddPage", method = RequestMethod.GET)
	public String toAddPage(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		return "pageform/admin/setup/rightsmanage/menusadd";
	}
	@RequestMapping(params="method=listQuery", method = RequestMethod.POST)
	public void systemListQuery(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		String page=request.getParameter("page");	//页索引
		String pagesize=request.getParameter("pagesize");	//每页数量
		String sortname=request.getParameter("sortname");	//排序名
		String sortorder=request.getParameter("sortorder");	//排序方向
		//拿到参数
		SystemMenus systemMenus=new SystemMenus();
		String beginDate=request.getParameter("beginDate");
		String endDate=request.getParameter("endDate");
		if(!ComStringUtil.isEmpty(request.getParameter("menusCode")) ){
			systemMenus.setMenusCode(request.getParameter("menusCode"));
		}
		if(!ComStringUtil.isEmpty(request.getParameter("menusName")) ){
			systemMenus.setMenusName(request.getParameter("menusName"));
		}
		if(!ComStringUtil.isEmpty(request.getParameter("menusUrl")) ){
			systemMenus.setMenusUrl(request.getParameter("menusUrl"));
		}
		if(!ComStringUtil.isEmpty(request.getParameter("menusImg")) ){
			systemMenus.setMenusImg(request.getParameter("menusImg"));
		}
		if(!ComStringUtil.isEmpty(request.getParameter("isUse")) ){
			systemMenus.setStatus(request.getParameter("isUse"));
		}
		QueryPageResult queryPageResult=systemMenusService.getMenusListForBg(systemMenus, beginDate, endDate, page, pagesize,sortname,sortorder);
		Map<String, Object> resMap=new HashMap<String, Object>();
		resMap.put("Total", queryPageResult.getTotalCount());
		resMap.put("Rows", queryPageResult.getQueryList());
		String jsonStr= JsonConverter.obj2Json(resMap);	//转化为json格式
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
	@RequestMapping(params="method=addMenus", method = RequestMethod.POST)
	public String addMenus(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
		String parentMenusId=request.getParameter("parentMenusId");
		String comTypeId=request.getParameter("comTypeId");
		String rightsType=request.getParameter("rightsType");
		if(!ComStringUtil.isInt(parentMenusId) && !ComStringUtil.isInt(comTypeId) && !ComStringUtil.isInt(rightsType)){
			//拿到参数
			SystemMenus systemMenus=new SystemMenus();
			systemMenus.setMenusCode(request.getParameter("menusCode"));
			systemMenus.setMenusName(request.getParameter("menusName"));
			systemMenus.setMenusUrl(request.getParameter("menusUrl"));
			systemMenus.setMenusImg(request.getParameter("menusImg"));
			systemMenus.setParentMenusId(Integer.parseInt(parentMenusId));
			systemMenus.setComTypeId(Integer.parseInt(comTypeId));
			systemMenus.setRightsType(Integer.parseInt(rightsType));
			systemMenus.setRemark(request.getParameter("remark"));
			systemMenus.setCreateUserId(loginMan.getId());
			systemMenusService.insertSystemMenus(systemMenus);
			map.put("result", "新增成功!");
		}else{
			map.put("result", "新增失败!");
		}
		return "pageform/admin/setup/rightsmanage/menuslist";
	}
	public SystemMenusService getSystemMenusService() {
		return systemMenusService;
	}

	public void setSystemMenusService(SystemMenusService systemMenusService) {
		this.systemMenusService = systemMenusService;
	}


	public SystemRightsTypeService getSystemRightsTypeService() {
		return systemRightsTypeService;
	}


	public void setSystemRightsTypeService(
			SystemRightsTypeService systemRightsTypeService) {
		this.systemRightsTypeService = systemRightsTypeService;
	}


	public SystemUserMenusService getSystemUserMenusService() {
		return systemUserMenusService;
	}


	public void setSystemUserMenusService(
			SystemUserMenusService systemUserMenusService) {
		this.systemUserMenusService = systemUserMenusService;
	}
	
}
