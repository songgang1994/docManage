<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：邵林军 --%>
<%-- 模块名：设备预约（月） --%>

<%@include file="../include/inc_header.jsp"%>
<!-- FullCalendar -->
<link href='js/thirdParty/fullcalendar/dist/fullcalendar.css' rel='stylesheet' />
<link href='js/thirdParty/fullcalendar/dist/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='js/thirdParty/fullcalendar/node_modules/moment/moment.js'></script>
<script src='js/thirdParty/fullcalendar/dist/fullcalendar.js'></script>
</head>
<body>
	<fieldset>
		<legend>设备预约月状况</legend>

<jsp:include page="../com/DeviceSearchPopup.jsp"/>
		<!-- 条件栏 -->
		<form id="deviceReserveMonthForm">
			<!-- 查询条件  第一行 -->
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
					<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
						<span>设备名称(单选)</span>
					</div>
					<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
						<input type="text" id="deviceName" name="deviceName" readonly
							style="font-weight: normal"
							class="col-sm-11 col-md-11 col-lg-11 col-xs-10 no-padding" />
						<div id="deviceSearch">
							<i class="fa fa-search"></i>
						</div>
						<input type="hidden" id="deviceNo" name="deviceNo" />
						<div id="deviceNameErr"></div>
					</div>
				</div>
				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
					<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
						<span>年月</span>
					</div>
					<div class="form-group has-error col col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding" >
						<select id="dateInfo" name="dateInfo" style="width: 50%">
							<option value=""></option>
						</select>
					</div>
				</div>
			</div>
			<!-- 第二行查询按钮 -->
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
					<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
						<button id="searchReserve" name="searchReserve" type="button"
							class="btn btn-primary">查询</button>
					</div>
				</div>
			</div>
		</form>
		<!-- 条件栏结束 -->

		<div class=" col-sm-12 col-md-12 col-lg-12 col-xs-12 ">
			<!-- 日历 -->
			<div id='calendar' name="detail" ></div>
		</div>

		<!-- 悬浮提示框 -->
		<div id="tip" style="width:auto;  display:inline;display:none;margin:10px 0;padding:10px;border:1px solid #696969;background-color:	#FFF68F;" >
			<lable id="tipDeviceName"></lable>
			<br>
			<lable id="tipUser"></lable>
			<br>
			<label id="tipStart" style="padding:0px;margin:0px"></label>&nbsp;~&nbsp;<label id="tipEnd" style="padding:0px;margin:0px"></label>
			<br>
		</div>


	</fieldset>

	<!--动态包含 pop画面-->
	<jsp:include page="./DeviceReservePopup.jsp"/>
	<!-- 本页面对应的js -->
	<script src="js/module/device/deviceReserveMonth.js"></script>
	<script >
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var deviceReserveMonthJS = new Globals.deviceReserveMonthJS();
			deviceReserveMonthJS.InitDeviceReserveMonth();
		});
	</script>



<%@include file="../include/inc_footer.jsp"%>