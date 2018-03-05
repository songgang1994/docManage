<!--
	NSK-NRDC业务系统
	作成人：李辉
	组织结构维护
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
	<!-- fieldset : start -->
	<fieldset>
		<legend>组织结构维护</legend>
		<div class="row" name="detail">
			<!-- 组织结构目录树 : start -->
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
				<div id=jstree_demo></div>
			</div>
			<!-- 组织结构目录树 : end -->
			<!-- 表单 : start -->
			<div id="departdetail"
				class="col col-sm-6 col-md-6 col-lg-6 col-xs-12"
				style="display: none;">
				<form id="departManageForm" modelAttribute="departManageForm"
					method="post">
					<input id="parentDeptId" name="parentDeptId" type="hidden" /> <input
						id="departManageFlag" name="departManageFlag" type="hidden"
						value="${departManageFlag}" />
					<!-- 上级部门 : start -->
					<div class="row" >
						<section class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
							<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">上级部门</span>
							<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12">
								<span id="deptParentName" name="deptParentName"></span>
							</div>
						</section>
					</div>
					<!-- 上级部门 : end -->
					<input id="deptId" name="deptId" type="hidden" />
					<!-- 部门负责人 : start -->
					<div class="row" >
						<section class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
							<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">部门负责人</span>
							<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12">
								<input id="userName" name="userName" type="text" readonly
									 maxlength="100"  style="width: 80%;font-weight:normal"/>
									<span id="leaderSearch" >
									<i class="fa fa-search"></i>
									</span>
									 <input id="userId" name="userId"  type="hidden" />
							</div>
						</section>
					</div>
					<!-- 部门负责人 : end -->
					<!-- 部门名 : start -->
					<div class="row" >
						<section class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
							<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12"">部门名</span>
							<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12">
								<input id="deptName" name="deptName" type="text" maxlength="100"
									style="width: 80%" />
							</div>
						</section>
					</div>
					<!-- 部门名 : end -->
				</form>
			</div>
			<!-- 表单 : end -->
		</div>
		<!-- 按钮 : start -->
		<div class="row  col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="buttongroup ">
				<button id="deptMentSubmit"  class="btn btn-primary" >保存</button>
	    		<button id="deptMentCancel" name="cancel"  class="btn">返回</button>
			</div>
		</div>
		<!-- 按钮 : end -->
	</fieldset>
	<!-- fieldset : end -->
<%@include file="../com/StaffSearchPopup.jsp"%>
<script src="js/module/system/departManage.js"></script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var departManageJS = new Globals.departManageJS();
		departManageJS.departManageInit();
	});
</script>
<%@include file="../include/inc_footer.jsp"%>