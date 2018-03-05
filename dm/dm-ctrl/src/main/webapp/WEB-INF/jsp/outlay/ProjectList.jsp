<!--
	NSK-NRDC业务系统
	作成人：李辉
	课题一览画面
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
	<div class="text-center" ><lable id="fileError"  style="color:red"></lable></div>
	<!-- 条件栏 : start -->
	<fieldset>
		<legend>课题一览</legend>
		<div class="row">
		<form id="selectForm" action="outlay/subjectList" ModelAttribute="projectInfo" method="post" name="queryForm" >
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">

				<!-- 查询条件  第一行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
							<span>所属部门</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
							<input type="text" id="deptNames" name="deptNames" readonly
								style="font-weight: normal" value="${deptNames}"
								class="col-sm-11 col-md-11 col-lg-11 col-xs-10 no-padding" />
							<div id="deptSearch">
								<i class="fa fa-search"></i>
							</div>
							<input type="hidden" id="deptId" name="deptId" value="${deptId}" />
						</div>
					</div>

					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
							<span>年度</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
							<input id="fy_Year" type="hidden" value="${fyYear}" />
							 <select id="fyYear" name="fyYear" style="width: 50%">
							<option></option>
						</select>
						</div>
					</div>
				</div>

				<!-- 查询条件  第二行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
							<span>课题主题</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
							<input type="text" id="projectName" name="projectName"
								style="font-weight: normal" value="${projectName}" maxlength="100"
								class="col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding" />
						</div>
					</div>

					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
							<span>课题分类</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
							<input id="project_type" type="hidden" value ="${projectType}"/>
							<select id="projectType" name="projectType" id="type" style="width: 50%">
								<option></option>
							</select>
						</div>
					</div>
				</div>
		</div>

		<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<button id="projectTableSearch" name="queryBut" type="button"  class='btn btn-primary' >查询</button>
					<button id="projectAdd" name="projectAdd" type="button"  class='btn btn-primary'  style="float:right">新增</button>
					<input id="allImport"  name="allImport"  type="button"  class='btn btn-primary' style="float:right;" value="批量导入"
					<c:if test="${ empty fyYear}">
					 disabled="true"
					</c:if>
					 />
			</div>
		</div>
	</form>
	</div>
	<!-- 条件栏 : end -->
	<!-- 批量导入弹框 : start -->
		<div class="modal fade" id="subjectImport" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-body">
	            	<form id="file_form" action="outlay/UpdFile" enctype="multipart/form-data" method="post">
		            	<fieldset>
							<legend>批量导入课题数据</legend>
							<div style="padding-left:20px;padding-top:10px">
								<lable>是否要批量导入<lable id="fyYear1"></lable>年课题数据?</lable>
					        	<input type="file" name="file_input" id="file_input" />
					        	<input id="fyyear" type="hidden" name="fyyear"/>
								<div class="buttongroup">
									<div class="row text-center">
										<button id="submitOK"  class="btn btn-primary">
											确认
										</button>
										<button id="importClose"  type="button"  class="btn " >
											关闭
										</button>
									</div>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	<!-- 批量导入弹框 : end -->
	<!-- 结果栏 start-->
	<div class="wapper " >
   	<div class="content-wapper">
        <div  id="projectTable"  class="row-fluid table-content" style="display: none">
        	<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
            <table id="projectList" style="margin-bottom:5px;table-layout: fixed;" class=" table table-bordered table-hover" >
                <thead>
                </thead>
                <tbody>
				</tbody>
            </table>
			</div>
			</div>
			 <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noProjectResult"  class="" style="display: none">
						无查询结果！
			</div>
       	</div>
       	<div  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
       		<div class="buttongroup">
				<input id="projectExport" name="projectExport" type="button" class='btn btn-primary'value="导出" style="display: none">
            	<input id="projectListCancel" name="cancel" type="button" class="btn"
				value="返回" />
			</div>
		</div>
    </div>
    </fieldset>
	<!-- 结果栏 end-->
	<div class="theme-popover"></div>
	<div class="theme-popover-mask"></div>
	<div class="html-temp"></div>
	<script src="js/module/outlay/projectList.js"></script>
	<%@include file="../com/DepartSearchPopup.jsp"%>
	<script>
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var projectListJS = new Globals.projectListJS();
			projectListJS.InitProject();
		});
	</script>
<%@include file="../include/inc_footer.jsp"%>