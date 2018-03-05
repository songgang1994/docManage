<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：邵林军 --%>
<%-- 模块名：法人检索POP --%>

<%-- <%@include file="../include/inc_header.jsp"%> --%>
<!-- 不能加include 头文件，会jq冲突 -->


<div class="modal fade" id="legalSearchPopup" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog ">
        <div class="modal-content">
            <div class="modal-body">
				<fieldset >
			  		<legend>法人检索</legend>
					<!-- 条件栏 -->
					<div class="row">
						<form id="legalSearchForm">
							<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
										<span class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">法人名称</span>
										<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
											<input type="text" id="legalName" style="width: 100%"/>
										</div>
									</div>
								</div>

								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<button id="searchLegal" class="btn btn-primary ">
											查询</button>
									</div>
								</div>

							</div>
						</form>
					</div>
					<!-- 条件栏结束 -->

					<!-- 表格 -->
					<div class="wrapper">
					   	<div id="legalSearchTableDiv"  class="content-wapper" style="display: none">
					    	<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
								<table id="legalSearchTable" style="margin-bottom:5px;table-layout: fixed;"  class=" table table-bordered table-hover">
									<tr>
									</tr>
								</table>
					       	</div>
					    </div>
					    <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noLegalResult"  style="display: none">
							无查询结果！
						</div>
						<!-- 按钮组 -->
					    <div  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					       	<div class="buttongroup">
								<button id="legalConfirm"  class="btn btn-primary">确认</button>
								<button id="legalClose"  class="btn ">关闭</button>
							</div>
						</div>
					</div>

				</fieldset>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 本页面对应的js -->
<script src="js/module/com/legalSearchPopup.js"></script>
<script>
	jQuery(document).ready(function() {
		var legalSearchPopupJS = new Globals.legalSearchPopupJS();
		legalSearchPopupJS.InitLegalSearchPopup();
	});
</script>
