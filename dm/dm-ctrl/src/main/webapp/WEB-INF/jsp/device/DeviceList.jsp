<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%--
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：实验设备
 */
--%>
<%@include file="../include/inc_header.jsp"%>
<fieldset>
	<legend>实验设备一览</legend>
	<div  class="row">
	<form id="form-search" action="device/devicelist"
		ModelAttribute="deviceInfo" method="POST">
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
		<!-- 查询条件  第一行 -->
		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
					<span>设备编号</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
					<input type="text" name="deviceNo" id="deviceNo1"
						value="${deviceNo}" style="width: 100%" />
					 <input type="hidden"  value="${flug}" id="flug">
				</div>
			</div>
		</div>
		<!-- 查询条件  第二行 -->
		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
					<span>设备名称</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
					<input type="text" name="deviceName" id="deviceName1"
						value="${deviceName}" style="width: 100%" />
				</div>
			</div>
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
					<span>位置</span>
				</div>
				<div
					class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
					<input type="hidden" id="locationn" value="${locationId}">
					<select id="eqplace" name="locationId" style="width: 100%;">
						<option value=""></option>
					</select>
				</div>
			</div>
		</div>
		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<button id="btn-query" name="btn-query" type="button"
					class="btn btn-primary">查询</button>
				<button id="btn-add-new" type="button" name="btn-add-new"
					class="btn btn-primary" style="float: right">新增</button>
			</div>
		</div>
		</div>
	</form>
	</div>
	<!-- WRAPPER_BEGIN -->
<div class="wrapper">
   	<div id="deviceListTable"  class="content-wapper" style="display: none">
    	<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
			<table id="tbl-device"  style="margin-bottom:5px;table-layout: fixed;"  class="table table-bordered table-hover" >
				<thead>
					<tr>
						<th class='text-center' style="vertical-align: middle;width:10%">序号</th>
						<th class='text-center' style="vertical-align: middle;width:15%">设备编号</th>
						<th class='text-center' style="vertical-align: middle;width:20%">设备名称</th>
						<th class='text-center' style="vertical-align: middle;width:15%">设备状态</th>
						<th class='text-center' style="vertical-align: middle;width:20%">位置</th>
						<th class='text-center' style="vertical-align: middle;width:20%">操作</th>
					</tr>
				</thead>

				<tbody>
				</tbody>

			</table>
       	</div>
    </div>
    	<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="nodeviceResult"  class="" style="display: none">
						无查询结果！
		</div>
    	<div  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
       		<div class="buttongroup">
				<button id="deviceListCancel" name="cancel"  class="btn ">返回</button>
			</div>
		</div>
</div>
</fieldset>
<!-- WRAPPER_END -->

<div class="html-temp"></div>

<!--动态包含 pop画面-->
	<jsp:include page="./DeviceUseHourEnter.jsp"/>
<script src="js/module/device/deviceList.js"></script>

<script>
$(function() {
	var deviceListJS = new Globals.deviceListJS();
	deviceListJS.InitDeviceList();
	deviceListJS.init();
});
</script>

<%@include file="../include/inc_footer.jsp"%>
