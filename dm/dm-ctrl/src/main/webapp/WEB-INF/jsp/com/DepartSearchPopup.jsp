<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：邵林军 --%>
<%-- 模块名：部门检索POP --%>

<%-- <%@include file="../include/inc_header.jsp"%> --%>
<!-- 不能加include 头文件，会jq冲突 -->
<div class="modal fade" id="departSearchPopup" tabindex="-1" role="dialog" aria-hidden="true"  data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog ">
        <div class="modal-content">
            <div class="modal-body">
				<fieldset >
					<legend>部门检索</legend>

					<!-- 条件栏 -->
					<div class="row">
						<form id="departSearchForm">
							<input type="hidden" id="level" readonly>
							<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
										<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">部门名称</span>
										<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
											<input type="text" id="deptPopDeptName" style="width: 100%" />
										</div>
									</div>
								</div>

								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<button id="searchDepart" class="btn btn-primary ">
											查询</button>
									</div>
								</div>

							</div>
						</form>
					</div>
					<!-- 条件栏结束 -->

					<!-- 表格 -->
					<div class="wrapper">
					   	<div id="departSearchTableDiv"  class="content-wapper" style="display: none">
					    	<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
								<table id="departSearchTable" style="margin-bottom:5px;table-layout: fixed;"  class=" table table-bordered table-hover">
									<tr>
									</tr>
								</table>
					       	</div>
					    </div>
					    <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noDepartResult"  style="display: none">
							无查询结果！
						</div>
						<!-- 按钮组 -->
					    <div  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					       	<div class="buttongroup">
								<button id="departConfirm"  class="btn btn-primary">确认</button>
								<button id="departClose"  class="btn ">关闭</button>
							</div>
						</div>
					</div>
				</fieldset>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 本页面对应的js -->
<script src="js/module/com/departSearchPopup.js"></script>
<script>
	jQuery(document).ready(function() {
		var departSearchPopupJS = new Globals.departSearchPopupJS();
		departSearchPopupJS.InitDepartSearchPopup();
	});
</script>
