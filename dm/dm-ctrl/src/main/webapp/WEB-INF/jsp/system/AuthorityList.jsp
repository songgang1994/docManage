<!--
	nSK-NRDC业务系统
	作成人：李辉
	角色管理列表画面
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
<style>
table tbody {
    display:block;
    overflow-y:scroll;
}

table thead, tbody tr {
    display:table;
    width:100%;
    table-layout:fixed;
}

table thead {
    width: calc( 100% - 1.2em )
}
</style>
	<fieldset>
		<legend>角色管理</legend>
		<!-- 	查询条件 Start -->
		<div class="row">
			<form id="selectForm" action="system/roleList"
				ModelAttribute="roleInfo" method="post" name="queryForm">
				<input id="authorityFlag" type="hidden" value="${authorityFlag}" />
				<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<!-- 角色名称 start -->
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
								<span>角色名称</span>
							</div>
							<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
								<input id="roleNameSearch" type="text" name="roleName"
									value="${roleNameSearch}" maxlength="50" style="width: 100%">
							</div>
						</div>
						<!-- 角色名称 end -->
						<!-- 角色描述 start -->
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
								<span>角色描述</span>
							</div>
							<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
								<input id="descriptionSearch" type="text" name="description"
									value="${descriptionSearch}" maxlength="200"
									style="width: 100%;">
							</div>
						</div>
						<!-- 角色描述 end -->
					</div>
					<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
							<button id="roleSearch" name="queryBut" type="button"
								class="btn btn-primary">查询</button>
							<button id="roleAdd" type="button" name="queryBut"
								class="btn btn-primary" style="float: right">新增</button>
						</div>
					</div>
				</div>
			</form>
		</div>
	    <!-- 	查询结果 end -->

		<div class="wapper " >
   		<div class="content-wapper">
		<div id="roleTable" class="row-fluid table-content"  class="" style="display: none">
		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
				<table id="authorityRoleTable" class="table table-bordered table-hover" >
					<thead>
					</thead>
					<tbody>
					</tbody>
            </table>
            </div>
		</div>
		 <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noRoleResult"  class="" style="display: none">
						无查询结果！
		</div>
		<!-- 底部按钮 Start -->
			<div class="buttongroup">
				<div class="row text-center">
			    <button id="roleDelete" class="btn btn-primary " name="roleDelete"   style="display:none" >删除</button>
				<button id="authorityListCancel"   name="cancel" class="btn" >返回</button>
				</div>
			</div>
		<!-- 底部按钮 End -->
		</div>
		</div>
	<!-- 导出弹框 : start -->
		<div class="modal fade" id="roleDetail" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-body">
	            	<form id="roleform"  ModelAttribute="roleInfo"  method="post">
		            	<fieldset>
		            	    <input id="roleflag" name="roleflag"  type='hidden' />
		            		<input id="roleId" name="roleId"  type='hidden' />
							<div  style="padding-top:10px">

							<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
										<span>角色名称</span>
									</div>
									<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
										<input id="roleName" name="roleName"  type='text' maxlength="50" style="width: 100%" />
									</div>
							</div>
							<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
										<span>角色描述</span>
									</div>
									<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
										<input id="description" name="description" type='text' maxlength="200" style="width: 100%" />
									</div>
							</div>

								<div class="buttongroup">
									<div class="row text-center">
										<button  id="roleSubmit" name="queryBut"  type="button" class="btn btn-primary">
											保存
										</button>
										<button id="roleClose" name="cancel"  type="button"  class="btn ">
											关闭
										</button>
									</div>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
		<!-- 查询结果 End -->
	</fieldset>
	<!-- 	查询条件 End -->
	<div id="projectTable" ></div>
<script src="js/module/system/authorityList.js"></script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var authorityListJS = new Globals.authorityListJS();
		authorityListJS.InitAuthorityList();
	});
</script>
<%@include file="../include/inc_footer.jsp"%>