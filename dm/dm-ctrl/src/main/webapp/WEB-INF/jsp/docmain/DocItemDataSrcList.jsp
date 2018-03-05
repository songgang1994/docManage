<!--
	NSK-NRDC业务系统
	作成人：张丽
	文档项目数据源画面
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
	<fieldset>
		<legend>文档项目数据源</legend>
		<div class="row">
		<form id="dataSrcSearchForm" action="docmain/dataSrcList" method="POST" name="dataSrcSearchForm" >
		<!-- 条件栏 -->
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
		    <!-- 查询条件  第一行 -->
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
					<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
						<span>数据源名称</span>
					</div>
					<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
						<input id="documentItemSourceName" class="text" name="documentItemSourceName" type="text"
		   				value="${documentItemSourceName}" style="width: 100%">
					</div>
				</div>
			</div>

			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<button id="dataSrcBut" name="dataSrcBut" type="button"
						class="btn btn-primary">查询</button>
					<button id="dataSrcAdd" type="button" name="dataSrcAdd"
						class="btn btn-primary" style="float: right">新增</button>
				</div>
			</div>
		</div>
		</form>
		</div>

	<div class="wapper">
   	<div class="content-wapper">
        <!-- 结果栏 -->
        <div id="dataSrcListTable" class="row-fluid table-content" style="display: none">
        <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
            <table id="dataSrcList" style="margin-bottom:5px;table-layout: fixed;" class="table table-bordered table-hover">
                <thead>
                </thead>
             	 <tbody>
				</tbody>
            </table>
            </div>
			</div>
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noDataSrcResult"  class="" style="display: none">
						无查询结果！
			</div>
       	</div>
    </div>
    <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="buttongroup">
				<input id="cancel" name="cancel" type="button" class="btn" value="返回" >
			</div>
	</div>
	</fieldset>

	<div class="theme-popover"></div>
	<div class="theme-popover-mask"></div>
	<div class="html-temp"></div>
	<script src="js/module/docmain/DocItemDataSrcList.js"></script>
	<script>
		jQuery(document).ready(function() {
			var DocItemDataSrcListAjaxJS = new Globals.DocItemDataSrcListAjaxJS();
			DocItemDataSrcListAjaxJS.InitProject();
		});
	</script>
<%@include file="../include/inc_footer.jsp"%>