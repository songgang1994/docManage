<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
 <%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：张丽--%>
<%-- 模块名：字典数据维护  jsp--%>

<%@include file="../include/inc_header.jsp"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta
    content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
    name="viewport">
<%-- 段定义开始位置--%>
<fieldset>
        <legend>字典数据维护</legend>
        <div class="row">
			<form id="source-form">
				<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
								<span>类别</span>
							</div>
							<div
								class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
								<select id="Legal" name="Legal" style="width: 100%">
									<option value=""></option>
								</select>
							</div>
						</div>
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12" >
								<button id="search" type="button" class="btn btn-primary" >查询</button>
							</div>
						</div>
					</div>
					<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
							<br />
					</div>
				</div>

			<div id="reset" class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div id="initDiv" class="col col-sm-12 col-md-12 col-lg-12 col-xs-12""></div>
					<input type="hidden" id="maxNum" name="maxNum" value="0" />
					 <div  id="oold"  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12"  style="display: none;">
					</div>
			</div>

			<div class="buttongroup">
				<div class="row text-center">
			    <button id="btn-confirm" class="btn btn-primary " name="submit"   style="display:none" >保存</button>
				<button id="close"   name="cancel" class="btn" >返回</button>
				</div>
			</div>

			</form>
        </div>
   </fieldset>
<%-- 段定义结束位置--%>

<script src="js/module/main/dataSourceMaintain.js"></script>
<script type="text/javascript">
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var dataSourceMaintainJS = new Globals.dataSourceMaintainJS();
			dataSourceMaintainJS.init();
		});
</script>

<%@include file="../include/inc_footer.jsp"%>