<!--
	NSK-NRDC业务系统
	作成人：李辉
	组织结构维护
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
		<!-- fieldset : start -->
		<fieldset>
			<legend>权限设定</legend>
			<!-- 角色信息:start -->
			<div class="row">
			<input id="roleNameSearch" type="hidden" value="${roleNameSearch}"/>
			<input id="descriptionSearch"  type="hidden" value="${descriptionSearch}"/>
			<input  id="roleId" name="roleId" type="hidden" value="${roleId}" />
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
						<span>角色名称</span>
						</div>
						<div class="col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
							<input  id="roleName" name="roleName" type="text" disabled="disabled" style="width: 100%"  value="${roleName}"/>
						</div>
					</div>
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
							<span>角色描述</span>
						</div>
						<div class="col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
							<input  id="description" name="description" type="text" disabled="disabled" style="width: 100%"  value="${description}"/>
						</div>
					</div>
			</div>
			</div>
			</div>
			<!-- 角色信息:end -->
			<!-- 权限目录树:start -->
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" style="padding-top: 1%;">
				<div id="jstree_demo" ></div>
			</div>
			<!-- 权限目录树:end -->
		<div class="row" style="padding-top: 2%;">
			<div class=" text-center">
				<input id="authorityManageSubmit" type="button" class='btn btn-primary'value="保存">
				<input id="authorityManageCancel" name="cancel" type="button" class="btn" value="返回" />
			</div>
		</div>
	</fieldset>
	<!-- fieldset : end -->
<script src="js/module/system/authoritymanage.js"></script>
<script>
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var authorityManageJS = new Globals.authorityManageJS();
			authorityManageJS.authorityManageInit();
		});
	</script>
<%@include file="../include/inc_footer.jsp"%>