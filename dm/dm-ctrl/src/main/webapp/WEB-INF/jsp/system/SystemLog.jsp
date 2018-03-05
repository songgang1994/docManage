<!--
		NSK-NRDC业务系统
		作成人：张丽
		系统日志
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
<!-- testDatepicker -->
<link href="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.css" rel="stylesheet" />
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.js"></script>
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/i18n/jquery-ui-timepicker-zh-CN.js" type="text/javascript" ></script>
<fieldset>
		<legend>系统日志</legend>
		<form id="search-log" >
		<!-- 查询条件  第一行 -->
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12  col-md-3 col-lg-3 col-xs-12">
					<span>时间</span>
				</div>
				<div class="form-group col col-sm-4 col-md-3 col-lg-3 col-xs-12">
					<input type="text" id="startDt" name="startDt" style="width: 100%"
						readonly />
				</div>
				<div class="form-group col-sm-1 col-md-1 col-lg-1 col-xs-12"
					style="paddingRight: 1%;">~</div>
				<div class="form-group col col-sm-4 col-md-3 col-lg-3 col-xs-12">
					<input type="text" id="endDt" name="endDt" style="width: 100%"
						readonly />
				</div>
			</div>

			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
					<span>文档编号</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding" >
					<input type="text" id="documentCode" name="documentCode"
						style="width: 100%">
				</div>
			</div>
		</div>
		<!-- 查询条件  第二行 -->
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
					<span>用户ID</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
					<input type="text" id="userId" name="userId" style="width: 100%" />
				</div>
			</div>
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
					<span>用户姓名</span>
				</div>
				<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
					<input type="text" id="userName" name="userName"
						style="width: 100%">
				</div>
			</div>
		</div>
		<!-- 查询条件  第三行 -->
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">

				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
					<span>操作内容</span>
				</div>
				<div class="form-group col col col-sm-9 col-md-7 col-lg-7 col-xs-12">
					<select id="oprContent" name="oprContent" style="width: 100%">
						<option value=""></option>
					</select>
				</div>
			</div>
		</div>

		<!-- 第四行查询按钮 -->
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
		<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
				<button id="btn-search"  name="dataSrcBut" type="button" class="btn btn-primary"  >查询</button>
				</div>
		</div>
		</div>
		</form>
		<!-- 查询结果  开始-->
	<div class="wapper" >
	   	<div class="content-wapper">
	        <div  id="batchCheckList"  class="row-fluid table-content" style="display: none">
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
				<table  style="margin-bottom:5px;table-layout: fixed;" class="table table-hover  table-bordered" id="batchCheckTable" >
					<thead>
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

		<!-- 底部按钮 start -->
		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="buttongroup">
					<input type="button" class="btn" id="form_staff_ret" name="cancel" value="返回">
				</div>
		</div>
		<!-- 底部按钮 end -->

</fieldset>

<script src="js/module/system/SystemLog.js">
</script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var SystemLogJS = new Globals.SystemLogJS();
		SystemLogJS.init();
	});
</script>