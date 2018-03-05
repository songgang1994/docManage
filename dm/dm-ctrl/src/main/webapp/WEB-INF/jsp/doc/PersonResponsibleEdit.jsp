<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%--
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：曾雷
 * 模块名：文档查询
 */
--%>
<%@include file="../include/inc_header.jsp"%>
<div id="MPersonResponsibleEditStub">
	<div class="wapper">
		<div class="content-wapper">
			<div class="content">
			<!-- 责任人popup start!-->
			<jsp:include page="../com/StaffSearchPopup.jsp"/>
			<!-- 责任人popup end!-->
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
					<form id="PREditForm" >
					<legend>文档责任人修改</legend>
						<!-- 	顶部按钮 end -->
						<!-- 	查询条件 start -->
						<div class="row">
							<div class="col col-sm-2 col-lg-2 col-xs-2">
								<span>责任人</span>
								<c:forEach items="${depts }" var="item">
									<input type="hidden" name="deptIds" value="${item.deptId }"/>
								</c:forEach>
							</div>

							<div class=" form-group col col-sm-10 col-lg-10 col-xs-10">
								<input type="hidden" id="docPersonHidden" value=""/>
								<input type="text" id="docPerson" name="docPersonName" readOnly/><span id="queryDocPerson">查询</span>
							</div>
							<div class="col col-sm-2 col-lg-2 col-xs-2">
								<span>文档类型</span>
							</div>

							<div class="col col-sm-10 col-lg-10 col-xs-10">
								<select id="docType">
									<option value=""></option>
									<c:forEach items="${docTypes }" var="item">
										<option value="${item.documentItemSourceCode }">${item.itemName }</option>
									</c:forEach>
								</select>
							</div>

							<div class="col col-sm-2 col-lg-2 col-xs-2">
								<span>文档状态</span>
							</div>

							<div class="col col-sm-10 col-lg-10 col-xs-10">
								<select id="docStatus">
									<option value=""></option>
									<c:forEach items="${docStatus }" var="item">
										<option value="${item.value }">${item.dispName }</option>
									</c:forEach>
								</select>
							</div>
						</div>

						<!-- 	查询条件 end -->

						<!-- 	查询按钮 start -->
						<div class="form-group text-left" >
							<input type="button" id="queryDoc" class="btn btn-primary" value="查询">
						</div>
						<!-- 查询按钮 end -->

						<!-- 	查询结果 start -->
						<div class="row" id="queryResultRow">
							<div  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
								<table class="text-center table table-bordered table-hover" id="docPersonEditTable">
									<thead>
										<tr>
											<th class="text-center" style="width: 50px"><input
												type="checkbox" id="selectAll" /></th>
											<th class="text-center">文件编号</th>
											<th class="text-center">文档类型</th>
											<th class="text-center">文件名称</th>
											<th class="text-center">主文件</th>
											<th class="text-center">子文件</th>
											<th class="text-center">状态</th>
										</tr>
									</thead>
									<tbody id="queryTableBody">

									</tbody>
								</table>
							</div>
						</div>
						<!-- 查询结果 end -->

						<div class="row" id="docAppointPersonRow" style="display:none">
							<div class="col col-sm-2 col-lg-2 col-xs-2">
								<span>指派责任人</span>
							</div>

							<div class="col col-sm-10 col-lg-10 col-xs-10">
								<input type="hidden" id="docAppointPersonHidden" value="">
								<input type="text" id="docAppointPerson" name="docAppointPersonName" readOnly/><span id="queryDocAppointPerson">查询</span>
							</div>

						</div>
						<!-- 底部按钮 start -->
						<div class="buttongroup" style="display:none" id="btnSure">
							<input type="button" class="btn btn-primary" value="确定" id="submit" />
						</div>
						<!-- 底部按钮 end -->
					</form>

				</fieldset>
			</div>
		</div>
	</div>
</div>

<script src="js/module/doc/PersonResponsibleEdit.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var personResponsibleEditJS = new Globals.personResponsibleEditJS();
		personResponsibleEditJS.InitPage();
	});
</script>

<%@include file="../include/inc_footer.jsp"%>
