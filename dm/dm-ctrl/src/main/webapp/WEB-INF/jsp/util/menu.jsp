<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.util.List"%>
<%@ page import="com.dm.entity.MenuEntity"%>
<div class="main-sidebar">
    <div class="sidebar">

        <ul class="sidebar-menu">
    <%
    String contextPath = request.getContextPath();
    List<MenuEntity> root = (List<MenuEntity>)request.getAttribute("root");
    boolean defaultIcon = true;
    for(int i=0;i<root.size();i++){
    	printMenu(root.get(i),out,contextPath,defaultIcon);
    }
    %>
    <%!
    /**
     * 打印菜单
     * @param node:输出节点
     * @param out:输出对象
     * @param contextPath:路径
     * @param defaultImg:是否启用默认图标，否则按照DB中的配置显示图标
     */
 	void printMenu(MenuEntity node, JspWriter out, String contextPath,boolean defaultIcon) throws Exception {
 		String img = null;
         if (node.getChildren().size() > 0&& node.getChildren()!=null) {
 			img = defaultIcon? "fa fa-gear": node.getIcon();
 			out.println("<li class='treeview'>");
 			out.println("<a href='javascript:void(0)'>");
 			out.println(" <i class='" + img + "'></i> <span>" + node.getText() + "</span>");
 			out.println("<i class='fa fa-angle-left pull-right'></i>");
 			out.println("</a>");
 			out.println("<ul class='treeview-menu'>");
 			for (int i=0; i<node.getChildren().size();i++) {
 				MenuEntity child=node.getChildren().get(i);
 					img = defaultIcon? "fa fa-circle-o": child.getIcon();
 					out.println("<li>");
 					out.println("<a href='javascript:void(0)' onclick='setContent(\"" + contextPath+ child.getUrl() + "\")'>");
 					out.println("<i class='" + img + "'></i> <span>" + child.getText() + "</span>");
 					out.println("</a>");
 					out.println("</li>");
 			}
 			out.println("</ul>");
 		} else {
 			img = defaultIcon? "fa fa-circle-o": node.getIcon();
 			out.println("<li>");
 			out.println("<a href='javascript:void(0)' onclick='setContent(\"" + contextPath + node.getUrl() + "\")'>");
 			out.println("<i class='" + img + "'></i> <span>" + node.getText() + "</span>");
 			out.println("</a>");
 			out.println("</li>");
 		}
 	}
	%>

    </div>
</div>