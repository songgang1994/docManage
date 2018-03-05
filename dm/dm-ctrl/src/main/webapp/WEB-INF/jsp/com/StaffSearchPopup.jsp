<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：邵林军 --%>
<%-- 模块名：人员检索POP --%>

<%-- <%@include file="../include/inc_header.jsp"%> --%>
<!-- 不能加include 头文件，会jq冲突 -->

	<div class="modal fade" id="staffSearchPopup" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
	    <div class="modal-dialog modal-xlg">
	        <div class="modal-content">
	            <div class="modal-body">
					<fieldset >
				  		<legend>人员检索</legend>
					<!-- 条件栏 -->
					<div class="row">
						<form id="staffSearchForm">
							<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
											<span>部门</span>
										</div>
										<div class="form-group has-error col col-sm-9 col-md-7 col-lg-7 col-xs-12">
											<input type="text" id="deptIdNames" name="newdeviceName"
												readonly style="font-weight: normal"
												class="col-sm-11 col-md-11 col-lg-11 col-xs-10 no-padding" />
											<div id="popDeptSearch">
												<i class="fa fa-search"></i>
											</div>
											<input type="hidden" id="deptIds" />
										</div>
									</div>

									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
											<span>部门负责人</span>
										</div>
										<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
											<input type="checkbox" id="leaderFlg"/>
										</div>
									</div>
								</div>

								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
											<span>员工ID</span>
										</div>
										<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
											<input type="text"  id="staffId"  style="width: 100%">
										</div>
									</div>

									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
											<span>姓名</span>
										</div>
										<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
											<input type="text"  id="staffName"  style="width: 100%">
										</div>
									</div>
								</div>

								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<button id="searchStaff" class="btn btn-primary ">
											查询</button>
									</div>
								</div>

							</div>
						</form>
					</div>
					<!-- 条件栏结束 -->

					<!-- 表格 -->
					<div class="wrapper">
						<!-- 结果组 -->
						<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
							<!-- 组织结构目录树 : start -->
							<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12 " style="overflow: auto">
								<div id="popJstree_demo"></div>
							</div>
							<!-- 组织结构目录树 : end -->

							<!-- 一览部分 : start -->
						   	<div id="staffSearchTableDiv"  class="content-wapper" style="display: none">
						    	<div class="col col-sm-7 col-md-7 col-lg-7 col-xs-12" >
									<table id="staffSearchTable" style="margin-bottom:5px;table-layout: fixed;"  class=" table table-bordered table-hover">
										<tr>
										</tr>
									</table>
						       	</div>
						    </div>
						    <div class="col col-sm-7 col-md-7 col-lg-7  col-xs-12" align="center" id="noStaffResult"  style="display: none">
								无查询结果！
							</div>
							<!-- 一览部分 : end -->

							<!-- 已选部分 : start -->
							<div id="staffSelected"  class="col col-sm-2 col-md-2 col-lg-2 col-xs-12 " >
							<span>已选择人员:</span>
								<textarea id="staffText" rows="12" maxLength="200"
								style="width: 130%; resize: none"></textarea>
							</div>
							<!-- 已选部分 : end -->
						</div>

						<!-- 按钮组 -->
					    <div  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					       	<div class="buttongroup">
								<button id="staffConfirm"  class="btn btn-primary">确认</button>
								<button id="staffClose"  class="btn ">关闭</button>
							</div>
						</div>
					</div>
					</fieldset>
				</div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	<%@include file="../com/DepartSearchPopup.jsp"%>
	<!-- 本页面对应的js -->
	<script src="js/module/com/staffSearchPopup.js"></script>
	<script>
		jQuery(document).ready(function() {
			var staffSearchPopupJS = new Globals.staffSearchPopupJS();
			staffSearchPopupJS.InitStaffSearchPopup();
		});
	</script>
