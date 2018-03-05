<!--
	nSK-NRDC业务系统
	作成人：李辉
	课题人工统计月度报表
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
<style>
#projectHourMonthlyTable  tbody {
    display:block;
    overflow-y:scroll;
}

 #projectHourMonthlyTable thead, #projectHourMonthlyTable tbody tr {
    display:table;
    width:100%;
    table-layout:fixed;
}

 #projectHourMonthlyTable thead {
    width: calc( 100% - 1.2em )
}
</style>
	<fieldset>
		<legend>课题人工统计月度报表</legend>
		<!-- 	查询条件 Start -->
		<div class="row">
		<form id="selectForm" action="report/projecthourmonthlyList" ModelAttribute="projectInfo" method="post" name="queryForm" >
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">

					<div class="col col-sm-4 col-md-4 col-lg-4 col-xs-12 no-padding">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
							<span>部门</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
							<input type="text" id="deptNames" name="deptNames" readonly
								style="font-weight: normal" title="${projectSearch.deptNames }"
								value="${projectSearch.deptNames }"
								class="col-sm-11 col-md-11 col-lg-11 col-xs-10 no-padding" />
							<div id="deptSearch">
								<i class="fa fa-search"></i>
							</div>
							<input id="deptId" name="deptId" type="hidden" value="${projectSearch.deptId}" />
						</div>
					</div>

					<div class="col col-sm-4 col-md-4 col-lg-4  col-xs-12">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
							<span>课题名称</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
							<input type="text" name="projectName" style="width: 100%"
								value="${projectSearch.projectName}" maxlength="100">
						</div>
					</div>

					<div class="col col-sm-4 col-md-4 col-lg-4  col-xs-12">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
							<span>年月</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
							<input id="projectDate" type="hidden" />
							<select id="dateInfo" name="dateInfo" style="width: 70%">
							</select>
						</div>
					</div>
				</div>

				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
							<button id="projectSearch" name="queryBut" type="button"
								class="btn btn-primary">查询</button>
						</div>
				</div>
		</div>

	</form>
	</div>
		<!-- 	查询结果 Start -->
		<div class="wapper" >
   		<div class="content-wapper">
		<div id="projectTable"  class="row-fluid table-content" style="display: none">
		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<table id="projectHourMonthlyTable" class="table table-bordered table-hover" >
					<thead >
					</thead>
					<tbody>
					</tbody>
            </table>
            </div>
		</div>
		 <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noProjectResult"  class="" style="display: none">
						无查询结果！
			</div>
		<!-- 底部按钮 Start -->
			<div class="row  col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="buttongroup">
				<button id="projectHourMonthlyReportExport" name="projectHourMonthlyReportExport"class="btn btn-primary" style="display:none;">
	   				导出
	    		</button>
	    		<button id="projectHourMonthlyCancel" name="cancel"  class="btn ">
	    			返回
	    		</button>
			</div>
		</div>
		<!-- 底部按钮 End -->
		</div>
		</div>
	<!-- 导出弹框 : start -->
		<div class="modal fade" id="projectHourMonthlyExport" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-body">
	            	<form id="file_form" action="report/export"  method="post">
		            	<fieldset>
		            		<input id="deptNumber" name="deptNumber" type="hidden"/>
		            		<input id="deptIdList" name="deptIdList" type="hidden"/>
		            		<input id="dateInfo1" name="dateInfo" type="hidden" value="${dateInfo}"/>

							<div  style="padding-top:10px" id="deptNumberWrite"></div>

								<div class="buttongroup">
									<div class="row text-center">
										<button  id="projectHourMonthlysubmit" name="queryBut"  type="button" class="btn btn-primary">
											生成报表
										</button>
										<button id="projectHourMonthlyClose" name="cancel"  type="button"  class="btn " data-dismiss="modal">
											关闭
										</button>
									</div>
								</div>
						</fieldset>
					</form>
				</div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
		<!-- 查询结果 End -->
	</fieldset>
	<!-- 	查询条件 End -->
	<div id="projectTable" ></div>
<%@include file="../com/DepartSearchPopup.jsp"%>
<script src="js/module/report/projectHourMonthlyReport.js"></script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var projectHourMonthlyReportJS = new Globals.projectHourMonthlyReportJS();
		projectHourMonthlyReportJS.InitProjectHourMonthlyReport();
	});
</script>
<%@include file="../include/inc_footer.jsp"%>