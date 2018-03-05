
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：曾雷 --%>
<%-- 模块名：月度工时 --%>
<%@include file="../include/inc_header.jsp"%>
<jsp:include page="../com/DepartSearchPopup.jsp"/>
<fieldset>
	<legend>月度工时</legend>
	<!-- 查询 start  -->
	<div  class="row">
	<form id="manage-form">
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
		<!-- 查询条件  第一行 -->
		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
					<span>部门</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
					<input type="text" id="departName" name="departName" readonly
						style="font-weight: normal" value="${deptName }"
						class="col-sm-11 col-md-11 col-lg-11 col-xs-10 no-padding" />
					<input type="hidden" id="depart" value="${departId }"/>
					<div id="directorSearch">
						<i class="fa fa-search"></i>
					</div>
					<input type="hidden" id="yearmo" value="${fyYear }" />
				</div>
			</div>
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
					<span>FY年月</span>
				</div>
				<div class="form-group has-error col col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
					<select name="fyYear" id="fy_yeay" style="width: 50%" value="${fyYear }">
					</select>
				</div>
			</div>
		</div>
		<!-- 第二行查询按钮 -->
		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
					<button id="queryMonthHours" name="queryMonthHours" type="button"
						class="btn btn-primary">查询</button>
				</div>
			</div>
		</div>
		</div>
	</form>
	</div>
	<!-- 条件栏结束 -->

	<!-- 月度工时表格 start -->
	<div class="wapper" id="queryTableDiv">
		<div class="content-wapper" style="overflow: hidden;">
			<div class="content" id="queryTable"
				style="display: none; overflow: auto;"></div>
		</div>
	</div>
	<!-- 月度工时表格 end -->

	<!-- 底部按钮 start -->
	<div class="buttongroup">
		<div id="appen"></div>
		<button id="export" type="button"  class="btn btn-primary" style="display: none">导出</button>
		<button id="cancel" type="button"  class="btn">返回</button>
	</div>
	<!-- 底部按钮 end -->
</fieldset>

<script src="js/module/outlay/MonthlyHour.js"></script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var monthlyHourJS = new Globals.monthlyHourJS();
		monthlyHourJS.InitPage();

	});
</script>

<script type="text/javascript">
	$("#export").hide();
</script>
<%@include file="../include/inc_footer.jsp"%>

<%-- 段定义结束位置--%>