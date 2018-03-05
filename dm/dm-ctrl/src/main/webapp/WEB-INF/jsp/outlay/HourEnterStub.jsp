<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：曾雷 --%>
<%-- 模块名：工时填写 --%>

<%@include file="../include/inc_header.jsp"%>

<!-- testDatepicker -->
<link href="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.css" rel="stylesheet" />
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.js"></script>
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/i18n/jquery-ui-timepicker-zh-CN.js" type="text/javascript" ></script>
<jsp:include page="../com/ProjectSearchPopup.jsp"/>
<fieldset>
	<legend>工时填写</legend>
	<div class="row">
		<form name="hour_form" id="hour_form" method="post"
			onSubmit="return false;">
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-4 col-md-4 col-lg-4 col-xs-12 no-padding">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
							<span>人员</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
							<input type="hidden" class="totime" value="${time }" /> <input
								type="hidden" id="userId" value="${userId}">
							<lable>${userName}</lable>
						</div>
					</div>

					<div class="col col-sm-4 col-md-4 col-lg-4  col-xs-12">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
							<span>所属部署</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
							<lable>${deptName}</lable>
						</div>
					</div>

					<div class="col col-sm-4 col-md-4 col-lg-4  col-xs-12">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
							<span>日期</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
							<input type="text" id="selectDate" readonly style="width: 50%" />
						</div>
					</div>
				</div>

				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<button id="addProject" type="button" style="float: right"
							class="btn Add btn-primary">添加课题</button>
							<br />
							<br />
					</div>
				</div>
			</div>

			<!-- 参数传递隐藏 -->
			<input type="hidden" id="deptNames1" value="${deptNames}"> <input
				type="hidden" id="fyYear1" value="${fyYear}"> <input
				type="hidden" id="deptId1" value="${deptId}"> <input
				type="hidden" id="projectType1" value="${projectType}"> <input
				type="hidden" id="projectName1" value="${projectName}">

			<c:forEach items="${list1}" var="list1" varStatus="i">
				<div class="hidbody">
					<input type="hidden" class="toprojectNo" value="${list1.projectNo}">
					<input type="hidden" class="tofyYear" value="${list1.fyYear}">
				</div>
			</c:forEach>

			<!-- <div class="wrapper"> -->
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div
					class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 table-responsive"">
					<table class="table table-bordered table-hover" id="projectTable">
						<thead>
							<tr>
								<th width="10%"></th>
								<th width="20%">FY年度</th>
								<th width="40%">课题主题</th>
								<th width="20%">工时(小时)${flug}</th>
								<th width="10%"></th>
								<input type="hidden" id="flug" value="${flug}">
								<input type="hidden" id="toflug" value="${toflug}">
								<input type="hidden" id="new" value="${size}">
							</tr>
						</thead>
						<tbody id="tbodyAll">
							<c:forEach items="${list}" var="list" varStatus="i">
								<tr class="body">
									<td id="projectNo" class="projectNo" style="display: none">${list.projectNo}</td>
									<td width="10%" align="left">${i.count}</td>
									<td width="20%" class="fyYear" align="left">${list.fyYear}</td>
									<td width="40%" align="left">${list.projectName}</td>
									<input type="hidden" class="number">
									<td width="20%" align="left">
										<div class="form-group">
											<input type="text" name="workingTimes"
												class="workingTimes${i.count}" value="${list.workingTimes}">
											<input type="hidden" class="workingTimesi"
												name="workingTimesi" value="${i.count}" />
										</div>
									</td>
									<td width="10%"><button class="btn dede">x&nbsp;&nbsp;删</button></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<input type="hidden" id="maxNum" name="maxNum" value="0" />
				</div>
			</div>

			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<span>注意:每个月5日后不能填写上个月工时</span>
				</div>
			</div>


			<div class="buttongroup">
				<button type="submit" class='btn btn-primary' id="hour_form_submit">确认</button>
				<button type="button" class='btn' id="ret">返回</button>
			</div>
		</form>
	</div>
</fieldset>
<script src="js/module/outlay/hourEnter.js"></script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var workingTimesJS = new Globals.workingTimesJS();
		//初始化
		workingTimesJS.InitPage();
	});
</script>


<%@include file="../include/inc_footer.jsp"%>

<%-- 段定义结束位置--%>