<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：邵林军 --%>
<%-- 模块名：设备预约POP --%>

<%-- <%@include file="../include/inc_header.jsp"%> --%>
<!-- 不能加include 头文件，会jq冲突 -->

<!-- testDatepicker -->
<link href="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.css" rel="stylesheet" />
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.js"></script>
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/i18n/jquery-ui-timepicker-zh-CN.js" type="text/javascript" ></script>
</head>
<body>
	<div class="modal fade" id="deviceReservePopup" tabindex="-1" role="dialog" aria-hidden="true"  data-backdrop="static" data-keyboard="false">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-body">
						<fieldset >
					  		<legend>设备预约</legend>
					  		<div class="row " >
							<form id="deviceReservePopupForm"  role="form" method="post" >
					  			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">

									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">设备编号</span>
											<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
													<lable id="deviceNoLab"></lable>
													<input type="hidden" id="deviceNo" name="deviceNo" />
													<!-- 设备编号 -->
													<input type="hidden" id="reserveNo" name="reserveNo" />
													<!-- 预约号 -->
													<input type="hidden" id="pattern" name="pattern" />
													<!-- 操作模式 -->
											</div>
										</div>
									</div>

									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">标题</span>
											<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12">
													<input type="text" id="title" name="title" style="width: 100%"></input>
											</div>
										</div>
									</div>

									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">设备名称</span>
											<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12">
													<lable id="deviceNameLab"></lable>
													<input type="hidden" id="deviceName" name="deviceName" value="设备名称" style="width: 100%">
											</div>
										</div>
									</div>

									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12"">使用目的</span>
											<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12">
												<textarea rows="4" id="useGoal" name="useGoal" style="width: 100%; resize: none"></textarea>
											</div>
										</div>
									</div>

									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">期间</span>
											<div class="form-group col col-sm-4 col-md-4 col-lg-4 col-xs-12">
												<input type="text" id="startDt" name="startDt" readonly
													style="width: 100%" /> <span id="startDtErr"></span>
											</div>
											<div class="form-group col-sm-1 col-md-1 col-lg-1 col-xs-12"
												style="paddingRight: 1%;">~</div>
											<div class="form-group col col-sm-4 col-md-4 col-lg-4  col-xs-12">
												<input type="text" id="endDt" name="endDt" readonly
													style="width: 100%" /> <span id="endDtErr"></span>
											</div>
										</div>
									</div>

									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">表现颜色</span>
											<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12">
												<input id="colorBtn" class='simple_color_kitchen_sink' />
												<input type="hidden" id="color" name="color" />
											</div>
										</div>
									</div>
									<div class="buttongroup">
										<div class="row text-center">
											<button type="button" id="submitReserve"  class="btn btn-primary">确认</button>
											<button type="button" id="deleteReserve"  class="btn btn-primary" style="display: none">删除</button>
											<button type="button" id="closeReserve" class="btn ">关闭</button>
										</div>
									</div>
								</div>
					</form>
							</div>
						</fieldset>

				</div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>

	<!-- 本页面对应的js -->
	<script type="text/javascript"  src="js/thirdParty/simpleColor/jquery.simple-color.js"></script>
	<script src="js/module/device/deviceReservePopup.js"></script>
	<script >
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var deviceReservePopupJS = new Globals.deviceReservePopupJS();
			deviceReservePopupJS.InitDeviceReservePopup();
		});
	</script>

<%@include file="../include/inc_footer.jsp"%>