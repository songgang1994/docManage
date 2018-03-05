<!--
	NSK-NRDC业务系统
	作成人：曾雷
	域用户一览
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
<fieldset>
		<legend>用户管理</legend>

		<!-- 	查询条件 start -->
		<form>
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12  col-md-3 col-lg-3 col-xs-12">
					<span>姓名</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
					<input type="text" id="userName" value="${userName}"
						style="width: 100%" /> <input type="hidden" id="flug"
						value="${flug}">
				</div>
			</div>

			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
					<span>用户ID</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding" >
					<input type="text" id="userId" value="${userId}"
						style="width: 100%" />
				</div>
			</div>
		</div>
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12  col-md-3 col-lg-3 col-xs-12">
					<span>邮件地址</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
					<input type="text" id="email" value="${email}" style="width: 100%" />
				</div>
			</div>

			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
					<span>角色</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding" >
					<select id="role" style="width: 100%">
						<option value=""></option>
						<c:forEach items="${roles}" var="item">
							<c:choose>
								<c:when test="${item.value == roleId}">
									<option value="${item.value}" selected>${item.name}</option>
								</c:when>
								<c:otherwise>
									<option value="${item.value}">${item.name }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</div>
			</div>
		</div>
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<button id="query" name="staffListQuery" type="button"
						class="btn btn-primary">查询</button>
					<button id="btn-add" type="button"
						class="btn btn-primary" style="float: right">新增</button>
				</div>

		</form>
		<!-- 	查询条件 end -->

		<!-- 	查询结果 start -->
					<div class="wapper" >
				   	<div id = "staffmange" class="content-wapper">
				        <div  id="batchCheckList"  class="row-fluid table-content" style="display: none">
							<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
							<table class="table  table-bordered table-hover" id="batchCheckTable" >
								<thead>
									<tr>
										<th class="text-center" style="vertical-align: middle;width:10%;"><input type="checkbox"
											id="selectAll" />全选</th>
										<th class="text-center" style="vertical-align: middle;width:10%">用户ID</th>
										<th class="text-center" style="vertical-align: middle;width:20%">姓名</th>
										<th class="text-center" style="vertical-align: middle;width:20%">电子邮件</th>
										<th class="text-center" style="vertical-align: middle;width:20%">角色</th>
										<th class="text-center" style="vertical-align: middle;width:20%">操作</th>
									</tr>
								</thead>
								<tbody id="queryTableBody">

								</tbody>
							</table>
							</div>
						</div>
						<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noBatchResult"  class="" style="display: none">
									无查询结果！
						</div>
					</div>
					</div>
					<!-- 查询结果 end -->

					<!-- 底部按钮 start -->
					<div class="buttongroup">
						<div class="row text-center">
					    <button id="form_del_staff" class="btn btn-primary " name="form_del_staff"   style="display:none" >删除</button>
						<button id="form_staff_ret"   name="cancel" class="btn" >返回</button>
						</div>
					</div>
					<!-- 底部按钮 end -->
</fieldset>
<script src="js/module/system/StaffManage.js">

</script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var staffManageJS = new Globals.staffManageJS();
		staffManageJS.InitPage();
	});
</script>
<%@include file="../include/inc_footer.jsp"%>