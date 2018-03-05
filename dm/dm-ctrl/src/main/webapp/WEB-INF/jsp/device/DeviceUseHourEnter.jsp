<%@page import="com.dm.entity.DeviceInfoEntity"%>
<%@page import="com.dm.dto.DeviceUseInfoDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
 <%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：张丽--%>
<%-- 模块名：实验设备工时录入 jsp--%>
<%-- <%@include file="../include/inc_header.jsp"%> --%>
<!-- <meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge"> -->
<!-- testDatepicker -->
<link href="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.css" rel="stylesheet" />
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.js"></script>
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/i18n/jquery-ui-timepicker-zh-CN.js" type="text/javascript" ></script>
<!-- <meta
    content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
    name="viewport"> -->
<body>
<!-- 获取指定使用编号下的设备信息 -->
<%DeviceUseInfoDto info = (DeviceUseInfoDto)request.getAttribute("useInfo");%>
<div class="modal fade" id="hourEnterPopup" tabindex="-1" role="dialog" aria-hidden="true"  data-backdrop="static" data-keyboard="false">
	    <div class="modal-dialog">
	        <div class="modal-content">
				<div class="modal-body">
					<fieldset>
						<legend>实验设备使用工时录入</legend>
						<div class="row " name="detail">
							<form id="hour-form">
								<!--  获取control层中定义的设备信息 -->
								<%List<DeviceInfoEntity> list = (List)request.getAttribute("listdevice");%>
								<%Boolean flag = (Boolean)request.getAttribute("flag"); %>
								<!--  画面迁移参数隐藏 结束-->
								<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<!-- 第一行 -->
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
												<span>实验设备</span>
												 <input type="hidden" id="flug" value="${flug}">
											</div>
											<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
												<select id="deviceNo" name="deviceNo" class="deviceNo"
													style="width: 100%;">
													<option value=""></option>
												</select>
												<input type="hidden" id="useNo" name="useNo" />
											</div>
										</div>
									</div>
									<!-- 第二行 -->
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
												<span>类型</span>
											</div>
											<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
												<input id="use_Type" type="hidden" value="${info.useType}" />
												<select id="useType" name="useType" style="width: 100%;">
													<option value=""></option>
												</select>
											</div>
										</div>
									</div>
									<!-- 第三行 -->
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
												<span>使用人</span>
											</div>
											<div class="form-group col-sm-6 col-md-6 col-lg-6 col-xs-9 ">
												<input type="text" id="userName" name="userName"
													value="${info.userName}" style="width: 100%" readonly />
												<input type="hidden" id="userId" name="userId" value="${info.userId}" />
											</div>
											<div class="form-group  col-sm-3 col-md-3 col-lg-3 col-xs-3" style="margin-left:-27px">
											<div id="select" name="select">
												<i class="fa fa-search"></i>
											</div>
										</div>
										</div>
									</div>
									<!-- 第四行 -->
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
												<span>使用期间</span>
											</div>
											<div class="form-group col col-sm-4 col-md-4 col-lg-4 col-xs-12">
												<input type="text" id="startDt" name="startDt"
													value="${info.startDt}" style="width: 100%" readonly />
											</div>
											<div class="form-group col-sm-1 col-md-1 col-lg-1 col-xs-12"
												style="paddingRight: 1%;">~</div>
											<div class="form-group col col-sm-4 col-md-4 col-lg-4  col-xs-12">
												<input type="text" id="endDt" value="${info.endDt}"
													style="width: 100%" name="endDt" readonly />
											</div>
										</div>
									</div>
									<!-- 第五行 -->
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
											<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
												<span>备注</span>
											</div>
											<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
												<textarea rows="4" id="commentInfo" value="${info.commentInfo}" maxLength="500"
													name="commentInfo" style="width: 100%; resize: none">${info.commentInfo}</textarea>
											</div>
										</div>
									</div>

									<!-- 不同状态对应不同的按钮，通过control定义的flag判断 -->
									<div class="buttongroup">
										<div class="row text-center">
											<button id="updatel" type="button" name="submit"
												class="btn btn-primary" style="display: none">保存</button>
											<button id="confirm" type="button" name="submit"
												class="btn btn-primary">保存</button>
											<button id="deletel" type="button"
												class="btn btn-primary">删除</button>
											<button id="close" type="button" name="cancel"
												class="btn">关闭</button>
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
</body>
<!-- 调用人员选择的popup框 -->
<jsp:include page="../com/StaffSearchPopup.jsp"/>
<script src="js/module/device/deviceUseHourEnter.js"></script>
<script type="text/javascript">
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var deviceHourEnterJS = new Globals.deviceHourEnterJS();
			deviceHourEnterJS.init();
		});
</script>
<%@include file="../include/inc_footer.jsp"%>