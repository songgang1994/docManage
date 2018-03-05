<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：邵林军 --%>
<%-- 模块名：实验设备统计 --%>

<%@include file="../include/inc_header.jsp"%>

</head>
<body>
	<fieldset>
		<legend>实验设备统计</legend>

		<!-- 条件栏 -->
		<form id="deviceStatisticsForm" >
		<!-- 查询条件  第一行 -->
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
					<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
						<span>设备</span>
					</div>
					<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
						<input type="text" id="deviceNames" name="deviceNames" readonly
							class="col-sm-11 col-md-11 col-lg-11 col-xs-10 no-padding"/>
	                	<span id="deviceSearch" >
	                		<i class="fa fa-search"></i>
	               	 	</span>
	               	 	<input type="hidden" id="deviceNos" name="deviceNos" />
					</div>
				</div>

				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
					<div class="col col-sm-12  col-md-3 col-lg-3 col-xs-12 no-padding">
						<span>期间</span>
					</div>
					<div class="form-group col col-sm-4 col-md-3 col-lg-3 col-xs-12 no-padding">
						<select id="startDt" name="startDt" style="width: 100%"></select>
					</div>
					<div class="form-group col-sm-1 col-md-1 col-lg-1 col-xs-12"
						style="paddingRight: 1%;">~</div>
					<div class="form-group col col-sm-4 col-md-3 col-lg-3 col-xs-12 no-padding">
						<select id="endDt" name="endDt" style="width: 100%"></select>
					</div>
				</div>
			</div>

			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
						<button id="searchDeviceStatistics"  class="btn btn-primary"  >查询</button>
						</div>
				</div>
			</div>

		</form>
		<!-- 条件栏结束 -->

		<!-- 表格  start -->
		<div  id="queryTableDiv" class=" row col-sm-12 col-md-12 col-lg-12 col-xs-12">

			<div id="queryTable" style="display: none; width: 98%;" class=" col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
			</div>
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noResult" style="display: none" class="">
				无查询结果！
			</div>
		</div>
		<!-- 表格  end -->

		<!-- 底部按钮 start -->
		<div class="row  col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="buttongroup ">
				<button id="exportDeviceStatistics" name ="exportDeviceStatistics" class="btn btn-primary"  style="display: none">
	   				导出
	    		</button>
	    		<button id="deviceStatisticsBack" name="cancel"  class="btn ">
	    			返回
	    		</button>
			</div>
		</div>
		<!-- 底部按钮 end -->

	</fieldset>

	<!--动态包含 pop画面-->
	<jsp:include page="../com/DeviceSearchPopup.jsp" />


	<!-- 本页面对应的js -->
	<script src="js/module/report/deviceStatistics.js"></script>
	<script >
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var deviceStatisticsJS = new Globals.deviceStatisticsJS();
			deviceStatisticsJS.InitDeviceStatistics();
		});
	</script>
<%@include file="../include/inc_footer.jsp"%>