<!--
	NSK-NRDC业务系统
	作成人：李辉
	系统菜单管理
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
	<!-- fieldset : start -->
	<fieldset>
		<legend>系统菜单管理</legend>
		<div class="row">
			<!-- 组织结构目录树 : start -->
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div id=jstree_demo></div>
			</div>
			<!-- 组织结构目录树 : end -->
		</div>
		<!-- 按钮 : start -->
		<div class="row  col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="buttongroup ">
				<button id="menuSubmit"  class="btn btn-primary" >保存</button>
	    		<button id="deptMentCancel" name="cancel"  class="btn ">返回</button>
			</div>
		</div>
		<!-- 按钮 : end -->
	</fieldset>
	<!-- fieldset : end -->
<script src="js/module/system/menuManage.js"></script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var menuManageJS = new Globals.menuManageJS();
		menuManageJS.menuManageInit();
	});
</script>
<%@include file="../include/inc_footer.jsp"%>