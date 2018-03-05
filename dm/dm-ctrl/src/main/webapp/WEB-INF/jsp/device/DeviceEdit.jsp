<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
 <%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：张丽--%>
<%-- 模块名：设备新增修改 jsp--%>

<%@include file="../include/inc_header.jsp"%>
<%@page import="com.dm.entity.ParmMstEntity"%>
<%@page import="com.dm.entity.DeviceInfoEntity"%>
<%@page import="java.util.List"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta
    content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
    name="viewport">
<%-- 段定义开始位置--%>
<fieldset>
<!-- 通过flag去判断状态修改，新增 -->
<%Boolean flag = (Boolean)request.getAttribute("flag"); %>
      <%if(flag!=null){%>
          <legend>实验设备修改</legend>
      <%}else{%>
         <legend>实验设备新增</legend>
      <%}%>
      <!-- 实验设备一览使用参数隐藏 -->
      <input type="hidden"  id="deviceNo1" value="${deviceNo1}">
      <input type="hidden"  id="deviceName1" value="${deviceName}">
      <input type="hidden"  id="locationId1" value="${locationId}">
      <input type="hidden"  id="location1" value="${location}">
      <!-- 实验设备一览使用参数隐藏结束 -->
	<div class="row " name="detail">
		<form id="manage-form">
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<!-- 第一行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>设备编号</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<%
								if (flag != null) {
							%>
							<input type="hidden" value="${deviceNo}" name="deviceNo">
							<input type="text" id="eqnumber" value="${deviceNo}"
								maxLength="20" disabled style="width: 100%;"
								onkeyup="value=value.replace(/[\W]/g,'') "
								onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
							<%
								} else {
							%>
							<input type="text" id="eqnumber" value="${deviceNo}"
								maxLength="20" name="deviceNo"  style="width: 100%;"
								onkeyup="value=value.replace(/[\W]/g,'') "
								onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))">
							<%
								}
							%>
						</div>
					</div>
				</div>
				<!-- 第二行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>设备名称</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<input type="text" id="eqname" maxLength="200"
								value="${device.deviceName }" name="deviceName" style="width: 100%;"/>
						</div>
					</div>
				</div>
			<!-- 通过设备编号获取设备信息 -->
			<%DeviceInfoEntity deviceinfo = (DeviceInfoEntity)request.getAttribute("deviceinfo"); %>
			    <!-- 第三行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>设备状态</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<input id="eq_state" type="hidden"
								value="${deviceinfo.deviceStatus}" />
								<select id="eqstate" name="deviceStatus" style="width: 100%;">
								<option value=""></option>
							    </select>
						</div>
					</div>
				</div>
			    <!-- 第四行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>参数</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<textarea rows="4" id="eqparameter" maxLength="200"
								name="deviceParm" style="width: 100%;resize: none">${device.deviceParm }</textarea>
						</div>
					</div>
				</div>
			    <!-- 第五行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>位置</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<input id="eq_place" type="hidden" value="${deviceinfo.locationId}" />
							<select id="eqplace" name="locationId" style="width: 100%;">
							<option value=""></option>
							 </select>
						</div>
					</div>
				</div>
			    <!-- 第六行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>说明</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<textarea rows="4" id="eqdiscribe" maxLength="500"
								name="commentInfo" style="width: 100%; resize: none">${device.commentInfo }</textarea>
						</div>
					</div>
				</div>

			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<section class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<label class="col col-sm-3 col-md-3 col-lg-3 col-xs-3"></label>
					<%if(flag!=null){%>
					<button id="update" type="button" name="submit"
						class="btn btn-primary  ">保存</button>
					<%}else{%>
					<button id="confirm" type="button" name="submit"
						class="btn btn-primary  ">保存</button>
					<%}%>
					<div class="col col-sm-2 col-md-2 col-lg-2 col-xs-2"></div>
					<button id="cancel" type="button" name="cancel"
						class="btn">返回</button>
				</section>
			</div>
			</div>
		</form>
	</div>
</fieldset>

<!-- 视图状态 -->

<%-- 段定义结束位置--%>

<script src="js/module/device/deviceEdit.js"></script>
<script type="text/javascript">
	    jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var deviceManageJS = new Globals.deviceManageJS();
			deviceManageJS.init();
		});
</script>
<%@include file="../include/inc_footer.jsp"%>