<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spt" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.dm.dto.LoginDto"%>
<%@ page import=" com.dm.tool.Constant"%>
  <%
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
  %>
  <% LoginDto user = (LoginDto)session.getAttribute(Constant.LOGON_INFO_SESSION_KEY);
	 pageContext.setAttribute("user", user);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta  content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <base href="<%=basePath %>" />
<link rel="stylesheet" href="js/thirdParty/bootstrap/css/bootstrap.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="js/thirdParty/adminlte/css/AdminLTE.min.css">
<link rel="stylesheet" href="js/thirdParty/adminlte/css/skins/_all-skins.min.css">
<link rel="stylesheet" href="js/thirdParty/adminlte/css/skins/skin-blue.min.css">
<!-- JQ-UI -->
<link rel="stylesheet" href="js/thirdParty/jquery/jquery-ui.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="js/thirdParty/FontAwesome/css/font-awesome.min.css">
<!-- Pagination -->
<link rel="stylesheet" href="js/thirdParty/jqPagination/css/pagination.css">
<!-- iCheck -->
<link rel="stylesheet" href="js/thirdParty/iCheck/all.css">
<!-- NRDC -->
<link rel="stylesheet" href="css/base.css">
<!-- DataTables 3.3.6 -->
<link rel="stylesheet" type="text/css" href="js/thirdParty/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<!--jsTree  -->
<link rel="stylesheet" href="js/thirdParty/jstree/css/jstree.min.css"/>
<!-- toastr -->
<link rel="stylesheet" type="text/css" href="js/thirdParty/toastr/toastr.min.css"/>
<!-- jQuery 2.2.0 -->
<script type="text/javascript"  src="js/thirdParty/jquery/jQuery-2.2.0.min.js"></script>
<script type="text/javascript"  src="js/thirdParty/jquery/jquery-ui.min.js"></script>
<script type="text/javascript"  src="js/thirdParty/jquery/jquery.validate.min.js"></script>
<script type="text/javascript" src="js/thirdParty/jquery/jquery.pagination.js"></script>
<script type="text/javascript" src="js/thirdParty/jquery/jquery.form.js"></script>
<script type="text/javascript" src="js/thirdParty/jquery-confirm/jquery.confirm.js"></script>
<!-- Bootstrap 3.3.6 -->
<script type="text/javascript"  src="js/thirdParty/bootstrap/js/bootstrap.min.js"></script>
<script src="js/thirdParty/jquery/jquery.serializejson.min.js"></script>
<!-- DataTables 3.3.6 -->
<script type="text/javascript" src="js/thirdParty/datatables/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/thirdParty/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<!-- jsTree -->
<script src="js/thirdParty/jstree/dist/jstree.min.js"></script>
<!-- toastr -->
<script src="js/thirdParty/toastr/toastr.min.js"></script>
<!-- momenty -->
<script src="js/thirdParty/moment/moment.js"></script>
<script src="js/thirdParty/moment/zh-cn.js"></script>
<!-- module -->
<script type="text/javascript" src="js/module/frame/app.js"></script>
<script src="js/namespace.js" type="text/javascript"></script>
<script src="js/module/const/msg.js" type="text/javascript"></script>
<script src="js/module/const/const.js" type="text/javascript"></script>
<script src="js/module/const/code.js" type="text/javascript"></script>
<script src="js/module/common/msgbox.js" type="text/javascript"></script>
<script src="js/module/common/util.js" type="text/javascript"></script>
<script src="js/module/common/ajax.js" type="text/javascript"></script>
<!-- header的js -->
<script src="js/module/include/inc_header.js"></script>
	<script>
		 jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
		  	var headerJS = new Globals.headerJS();
			headerJS.InitHeader();
		});
	</script>
<!-- 定义全局通用对象 -->
<script>
	/* 全局code对象 */
	var ConstCode = new Globals.constCode();
	/* 全局msg对象 */
	var ConstMsg = new Globals.constMsg();
	/* 全局text对象 */
	var ConstText = new Globals.constJS();
</script>

</head>
<body>
<input id="sysactions" type="hidden" value="${user.sysActions}">
<input id="allactions" type="hidden" value="${user.allActions}">