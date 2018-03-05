<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%--
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：文档数据项管理
 */
--%>
<%@include file="../include/inc_header.jsp"%>
<!-- 调用人员选择的popup框 -->
<jsp:include page="../com/StaffSearchPopup.jsp"/>
<div id="MDocBatchStub">
	<!-- 子文件popup start -->
	<div class="modal fade" id="childFiles" tabindex="-1" role="dialog"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-body" id="childFilesModalBody"></div>
			</div>
		</div>
	</div>
	<!-- 子文件popup end -->
	<fieldset>
		<legend>文档审核</legend>
		<div style="display: none">
			<!-- 	查询条件 start -->
			<div class="row" style="padding-top: 1%">
				<div class="col col-sm-2 col-lg-2 col-xs-2"
					style="padding-left: 25px">
					<span>文档编号</span>
				</div>

				<div class="col-sm-3 col-lg-3 col-xs-3">
					<input type="text" id="docCode" />
				</div>
				<div class="col col-sm-2 col-lg-2 col-xs-2">
					<span>文档类型</span>
				</div>

				<div class="col-sm-1 col-lg-1 col-xs-1">
					<select id="docType">
						<option value=" "></option>
						<c:forEach items="${docItems }" var="item">
							<option value=" ${item.itemValue }">${item.itemName }</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="row" style="padding-top: 1%">
				<div class="col col-sm-2 col-lg-2 col-xs-2"
					style="padding-left: 25px">
					<span>所属部门</span>
				</div>

				<div class="col-sm-3 col-lg-3 col-xs-3">
					<select id="Dept">
						<option value=" "></option>
						<c:forEach items="${subDept }" var="item">
							<option value=" ${item.deptId }">${item.deptName }</option>
						</c:forEach>
						<c:forEach items="${auditDept }" var="item">
							<option value=" ${item.deptId }">${item.deptName }</option>
						</c:forEach>
					</select>
				</div>
				<div class="col col-sm-2 col-lg-2 col-xs-2">
					<span>文档状态</span>
				</div>

				<div class="col-sm-3 col-lg-3 col-xs-3">
					<select id="docStatus">
						<option value=" "></option>
						<c:forEach items="${status }" var="item">
							<option value=" ${item.value }">${item.dispName }</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="row" style="padding-top: 1%">
				<div class="col col-sm-2 col-lg-2 col-xs-2"
					style="padding-left: 25px">
					<span>责任人</span>
				</div>

				<div class="col-sm-3 col-lg-3 col-xs-3">
					<input type="text" id="director" value=""> <span
						id="directorSearch"><i class="fa fa-search"></i></span> <input
						type="hidden" id="directorId" value="">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 col-lg-12 col-xs-12 "
					style="padding-left: 25px">
					<div class="buttongroup">
						<input type="button" id="query" class="btn btn-primary" value="查询">
					</div>
				</div>
			</div>
		</div>
		<!-- 	查询条件 end -->
		<!-- 	查询结果 start -->
		<div class="wrapper">
			<div id="queryTable" class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				</div>
			</div>
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noResultq"  style="display: none">
				无查询结果！
			</div>
			<!-- 查询结果 end -->
			<!-- 审核意见 start -->
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding"  id="doc_comment_form_div"  style="display: none">
				<form name="doc_comment_form" id="doc_comment_form" method="post" onSubmit="return false;">
					<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding" id="approveComment">
						<div class="col col-sm-12 col-md-1 col-lg-1 col-xs-12">
							<span>审核意见</span>
						</div>
						<div class="form-group col col-sm-12 col-md-11 col-lg-11 col-xs-12 ">
							<textarea rows="5" name="approve_comment" cols="10" data-rule-maxlength="${approve.maxLength }" data-msg-maxlength="审核意见不得超过${approve.maxLength }字符"
								id="approve_comment" style="width: 99%; resize: none"></textarea>
						</div>
					</div>
				</form>
			</div>
			<!-- 审核意见 end -->
			<!-- 底部按钮 start -->
			<div  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
				<div class="buttongroup no-padding" >
					<button id="form_batch_submit" name="cancel"  class="btn btn-primary" style="display: none;">通过</button>
					<button id="DocBatchCancel" name="cancel"  class="btn">返回</button>
				</div>
			</div>
			<!-- 底部按钮 end -->
			</div>
	</fieldset>
</div>

<script src="js/module/doc/docBatchApprove.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var docBatchApproveJS = new Globals.docBatchApproveJS();
		docBatchApproveJS.InitPage();
	});
</script>

<%@include file="../include/inc_footer.jsp"%>
