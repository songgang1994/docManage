<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：邵林军 --%>
<%-- 模块名：课题检索POP --%>

<%-- <%@include file="../include/inc_header.jsp"%> --%>
<!-- 不能加include 头文件，会jq冲突 -->


<div class="modal fade" id="projectSearchPopup" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
				<fieldset >
			  		<legend>课题检索</legend>

					<!-- 条件栏 -->
					<div class="row">
						<form id="projectSearchForm">
							<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
											<span>课题编号</span>
										</div>
										<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
											<input type="text" id="projectNo" style="width: 100%"/>
										</div>
									</div>
									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
											<span>主题</span>
										</div>
										<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
											<input type="text" id="projectName" style="width: 100%"/>
										</div>
									</div>
								</div>

								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
											<span>年度</span>
										</div>
										<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
										    <select  id="fyYear" style="width: 50%">
												<option></option>
											</select>
										</div>
									</div>
									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
											<span>课题分类</span>
										</div>
										<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
											<select  id="projectType" style="width: 100%">
											<option></option>
											</select>
										</div>
									</div>
								</div>

								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<button id="searchProject" class="btn btn-primary ">
											查询</button>
									</div>
								</div>

							</div>
						</form>
					</div>
					<!-- 条件栏结束 -->

					<!-- 表格 -->
					<div class="wrapper">
					   	<div id="projectSearchTableDiv"  class="content-wapper" style="display: none">
					    	<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
								<table id="projectSearchTable"  style="margin-bottom:5px;table-layout: fixed;" class=" table table-bordered table-hover">
									<tr>
									</tr>
								</table>
					       	</div>
					    </div>
					    <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noProjectResult"  style="display: none">
							无查询结果！
						</div>
						<!-- 按钮组 -->
					    <div  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					       	<div class="buttongroup">
								<button id="projectConfirm"  class="btn btn-primary">确认</button>
								<button id="projectClose"  class="btn ">关闭</button>
							</div>
						</div>
					</div>
				</fieldset>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 本页面对应的js -->
<script src="js/module/com/projectSearchPopup.js"></script>
<script>
	jQuery(document).ready(function() {
		var projectSearchPopupJS = new Globals.projectSearchPopupJS();
		projectSearchPopupJS.InitProjectSearchPopup();
	});
</script>
