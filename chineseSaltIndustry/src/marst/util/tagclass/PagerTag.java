package marst.util.tagclass;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.*;

import marst.util.ComStringUtil;
/**
 * 分页标签类
 * @author taddy
 *
 */
public class PagerTag extends TagSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String url;
	private int pageSize=10;
	private int pageNo=1;
	private int recordCount;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException{
		int pageCount=(recordCount+pageSize-1)/pageSize;	//计算总页数
		
		//到页面的HTML文本
		StringBuffer sb=new StringBuffer();
		sb.append("<style type=\"text/css\">");
		sb.append(".pagination {padding:5px;float:right;font-size:12px;}");
		sb.append(".pagination a, .pagination a:link, .paginaion a:visited "
				+ "{padding:2px 5px;margin:2px;border:1px solid #aaaadd;"
				+ "text-decoration:none;color:#006699;}");
		sb.append(".pagination a:hover, "
				+ ".pagination a:active {border:1px sold #ff0000;color:#000;text-decoration:none;}");
		sb.append(".pagination span.current {padding:2px 5px;margin:2px;"
				+ "border:1px solid #ff0000;font-weight:bold;backgroun-color:#ff0000;color:#fff;}");
		sb.append(".pagination span.disabled {padding:2px 5px;margin:2px;border:1px solid #eee; color:#ddd;}");
		sb.append("</style>\r\n");
		sb.append("<div class=\"pagination\">\r\n");
		if(recordCount==0){
			sb.append("<strong>没有可显示的记录</strong>\r\n");
		}else{
			//页码过界
			if(pageNo>pageCount){pageNo=pageCount;}
			if(pageNo<1){pageNo=1;}
			sb.append("<form method='post' action=\"").append(this.url)
			.append("\" name=\"qPagerForm\">\r\n");
			
			//获取请求参数
			HttpServletRequest request=(HttpServletRequest) pageContext.getRequest();
			Enumeration<String> enumeration=request.getParameterNames();
			String name=null;	//参数名
			String value=null;	//参数值
			
			//把请求中的参数放在隐藏域中
			while(enumeration.hasMoreElements()){
				name=enumeration.nextElement();
				value=request.getParameter(name);
				//去除页号
				if(name.equals("pageNo")){
					if(ComStringUtil.isEmpty(value)){
						pageNo=Integer.parseInt(value);
					}
					continue;
				}
				sb.append("<input type='hidden' name=\"").append(name)
					.append("\" value=\"")
					.append(value)
					.append("\"/>\r\n");
			}
			//把当前页号设置成请求参数
			sb.append("<input type='hidden' name=\"").append("pageNo")
				.append("\" value=\"").append(pageNo).append("\"/>\r\n");
			
			//输出统计数据
			sb.append("&nbsp;共<strong>").append(recordCount)
				.append("</strong>项")
				.append(".<strong>")
				.append(pageCount)
				.append("</strong>页:&nbsp;\r\n");
			//上一页处理
			if(pageNo==1){
				sb.append("<span class=\"disabled\">&laquo;&nbsp;上一页")
					.append("</span>\r\n");
			}else{
				sb.append("<a href=\"javascript:turnOverPage(")
					.append((pageCount-1))
					.append(")\">&laquo;&nbsp;上一页</a>\r\n");
			}
			
			//如果前面页数过多，显示“。。。”
			int start=1;
			if(this.pageNo>4){
				start=this.pageNo-1;
				sb.append("<a href=\"javascript:turnOverPage(1)\">1</a>\r\n");
				sb.append("<a href=\"javascript:turnOverPage(2)\">2</a>\r\n");
				sb.append("&hellip:\r\n");
			}
			
			//显示当页附近的页
			int end=this.pageNo+1;
			if(end>pageCount){
				end=pageCount;
			}
			for(int i=start;i<=end;i++){
				if(pageNo==i){
					sb.append("<span class=\"current\">")
						.append(i)
						.append("</span>\r\n");
				}else{
					sb.append("<a href=\"javascript:turnOverPage(")
					.append(i).append(")\">")
					.append(i).append("</a>\r\n");
					
				}
			}
			//如果后面页码多，显示"..."
			if(end<pageCount-2){
				sb.append("&hellip;\r\n");
			}
			if(end<pageCount-1){
				sb.append("<a href=\"javascript:turnOverPage(")
					.append(pageCount-1)
					.append(")\">")
					.append(pageCount-1)
					.append("</a>\r\n");
			}
			if(end<pageCount){
				sb.append("<a href=\"javascript:turnOverPage(")
					.append(pageCount)
					.append(")\">")
					.append(pageCount)
					.append("</a>\r\n");
			}
			
			//下一页处理
			if(pageNo==pageCount){
				sb.append("<span class=\"disabled\">下一页&nbsp;&raquo;")
					.append("</span>\r\n");
			}else{
				sb.append("<a href=\"javascript:turnOverPage(")
					.append((pageCount+1))
					.append(")\"下一页&nbsp;&raquo;</a>\r\n");
			}
			sb.append("</fprm>\r\n");
			
			//生成提交表单的JS
			sb.append("<scro[t language=\"javascript\">\r\n");
			sb.append("   function turnOverPage(no){\r\n");
			sb.append(" 	if(no>").append(pageCount).append("){");
			sb.append("		no=").append(pageCount).append(";}\r\n");
			sb.append("		if(no<1){no=1;}\r\n");
			sb.append("		document.qPagerForm.pageNo.value=no;\r\n");
			sb.append("		document.qPagerFomr.submit();\r\n");
			sb.append("	}\r\n");
			sb.append("</script>\r\n");
			
		}
		sb.append("</div>\r\n");
		try{
			pageContext.getOut().print(sb.toString());
		}catch(IOException e){
			throw new JspException(e);
		}
		return SKIP_BODY;	//本标签主体为字，所以直接跳过主体
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	
}
