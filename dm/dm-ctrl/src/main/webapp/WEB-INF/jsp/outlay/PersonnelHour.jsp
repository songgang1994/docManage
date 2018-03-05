<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：曾雷 --%>
<%-- 模块名：人员工时查看 --%>

<%@include file="../include/inc_header.jsp"%>
<form action="outlay/hourenter" method="post" name="queryForm"
	id="mSubjectQueryForm">
	<input type="hidden" id="deptId" name="deptId" value="${deptId }">
	<input type="hidden" id="fyYear" value="${fyYear }">
	<fieldset>
		<legend>人员工时查看</legend>
		<div  class="row">
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
		<!-- 查询 start  -->
		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
					<span>FY年月</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
					<select name="Item" id="fy_yeay" style="width: 50%">
					</select>
				</div>
			</div>
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
					<span>人员</span>
				</div>
				<div class="form-group has-error col col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
					<input type="hidden" id="userId" name="userId" value="${userId}" />
					<input type="hidden" id="deptName" name="deptName" value="${deptName}" />
					<input type="hidden" id="userName" name="userName" value="${userName}" />
					<lable>${userName}</lable>
				</div>
			</div>
		</div>
		<!-- 查询 end  -->
		</div>
		</div>

		<!-- 个人月度工时表格 start -->
		<div class="wapper" id="queryTableDiv">
			<div class="content-wapper">
				<div class="content" id="queryTable"
					style="display: none; height: 500px; overflow: auto"></div>
				<div class="row-fluid" align="center" id="noResult" style="display: none">
					<br />无查询结果！
				</div>
			</div>
		</div>
		<!-- 个人月度工时表格 end -->
		<!-- 底部按钮 start -->
		<div class="buttongroup">
			<button id="cancel" name="cancel"  class="btn ">返回</button>
		</div>
		<!-- 底部按钮 end -->
	</fieldset>
</form>
<script src="js/module/outlay/PersonnelHour.js"></script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var personnelHourJS = new Globals.personnelHourJS();
		personnelHourJS.InitPage();

	});
</script>
<script type="text/javascript">

</script>
<%@include file="../include/inc_footer.jsp"%>

<%-- 段定义结束位置--%>