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
<style>
#projectTable table, #projectTable thead th, #projectTable tbody td {
	border: 1px solid #ddd
}
</style>
<script src="js/thirdParty/sortable/vue.js"></script>
<div id="MDocEnterFormDefineStub">
	<input type="hidden" id="selectCount" value="${info.bizCode }">
	<div class="wapper">
		<div class="content-wapper">
			<fieldset>
				<legend>文档录入表单定义</legend>
				<form name="doc_enter_form_define" id="doc_enter_form_define"
					onSubmit="return false;" method="post">
					<!-- 查询条件start -->

						<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
								<span>${info.docDataSource.documentItemName }</span>
							</div>
							<div
								class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
								<select id="docType" name="docType" style="width: 50%">
								<c:forEach items="${info.docDataSource.dataSourceList }"
												var="item">
												<option value="${item.itemValue }"
													<c:if test="${selectDocType ==item.itemValue}">selected</c:if> >${item.itemName }</option>
										</c:forEach>
								</select>
							</div>
						</div>
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
								<span>最终审核人</span>
							</div>
							<div
								class="form-group col col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
								<input type="text" name="approval" readOnly value="${info.userName}"
									class="col-sm-11 col-md-11 col-lg-11 col-xs-10 no-padding">
								<div class="item-type-popup-staff">
									<i class="fa fa-search"></i>								</div>
								<input type="hidden" name="approvalID" value="${info.lastApproveUser}"/>
							</div>
						</div>
					</div>
					<!-- 查询条件end -->
					<!-- 查询结果start -->
					<div id="docItemTable" class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" style="height:530px;overflow-y:scroll;">
										<table class="table table-striped" id="projectTable">
											<thead>
												<tr>
													<th class="col-xs-3 text-center" colspan='2'
														style="border: 1px solid #ddd">原数据项</th>
													<th class="col-xs-6 text-center" colspan='4'
														style="border: 1px solid #ddd">表单项</th>
												</tr>
											</thead>
											<tbody>
												<!-- 循环 -->
												<tr>
													<td class="col-xs-3 text-center"><span>原数据项</span></td>
													<td class="col-xs-1 text-center"><span>操作</span></td>
													<td class="col-xs-3 text-center"><span>左侧显示项目</span></td>
													<td class="col-xs-1 text-center"><span>必须输入</span></td>
													<td class="col-xs-3 text-center"><span>右侧显示项目</span></td>
													<td class="col-xs-1 text-center"><span>必须输入</span></td>
												</tr>
												<tr>
													<td class="col-xs-3 text-left" colspan='2'>
														<div id="sort1" class="connectedSortable">
															<c:forEach items="${info.unDefineInfo }" var="item">
																<c:choose>
																	<c:when test="${item.isBlankItem == 1}">
																	</c:when>
																	<c:otherwise>
																		<div class="sort-item" type="number" label="2222"
																			style="border: 1px solid #ddd; cursor: move">
																			<label style="padding-left: 25px;">${item.documentItemName}</label>
																			<input type="hidden" name="documentItemCode"
																				value="${item.documentItemCode}" /> <input
																				type="hidden" name="documentItemNo"
																				value="${item.documentItemNo}" /><input
																				type="hidden" name="isFromToItem"
																				value="${item.isFromToItem}" />
																		</div>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</div>
													</td>
													<td class="col-xs-3 text-left" colspan='2' id="td_sort">
														<div id="sort2" class="connectedSortable">
															<c:forEach items="${info.leftDefinedInfo}" var="item">
																<c:choose>
																	<c:when test="${item.documentItemCode eq 'DOCUMENT_ITEM_F00000'}">
																		<div id="blankItem" class="sort-item" type="number"
																		label="2222"
																		style="border: 1px solid #ddd; cursor: move">
																		<label style="padding-left: 25px;">空白项目</label>
																		<span style="float: right; padding-right: 25px"
																			onclick="if ($(this).closest('td').attr('id') != 'blanktd') { $(this).closest('div').remove(); } ">x
																		</span>
																		<input type="hidden" name="documentItemCode"
																		value="${item.documentItemCode}" /> <input
																		type="hidden" name="documentItemNo"
																		value="${item.documentItemNo}" /> <input
																		type="hidden" name="checkbox">
																</div>
																	</c:when>
																	<c:otherwise>
																			<div class="sort-item" type="number" label="2222"
																	style="border: 1px solid #ddd; cursor: move">
																	<label style="padding-left: 25px;">${item.documentItemName }</label>
																	<input type="hidden" name="documentItemCode"
																		value="${item.documentItemCode}" /> <input
																		type="hidden" name="documentItemNo"
																		value="${item.documentItemNo}" /><input type="hidden"
																		name="isFromToItem" value="${item.isFromToItem}" /> <span
																		style="float: right; padding-right: 25px"> <input
																		type="checkbox" name="checkbox"
																		<c:if test="${item.inputRequire ==1 }">checked</c:if> />
																	</span>
																</div>
																	</c:otherwise>
																</c:choose>

															</c:forEach>
														</div>
													</td>
													<td class="col-xs-3 text-left" colspan='2'>
														<div id="sort3" class="connectedSortable">
															<c:forEach items="${info.rightDefinedInfo}" var="item">
																<c:choose>
																	<c:when test="${item.documentItemCode eq 'DOCUMENT_ITEM_F00000'}">
																		<div id="blankItem" class="sort-item" type="number"
																		label="2222"
																		style="border: 1px solid #ddd; cursor: move">
																		<label style="padding-left: 25px;">空白项目</label>
																		<span style="float: right; padding-right: 25px"
																			onclick="if ($(this).closest('td').attr('id') != 'blanktd') { $(this).closest('div').remove(); } ">x
																		</span><input type="hidden" name="documentItemCode"
																		value="${item.documentItemCode}" /> <input
																		type="hidden" name="documentItemNo"
																		value="${item.documentItemNo}" /> <input
																		type="hidden" name="checkbox">
																</div>
																	</c:when>
																	<c:otherwise>
																			<div class="sort-item" type="number" label="2222"
																	style="border: 1px solid #ddd; cursor: move">
																	<label style="padding-left: 25px;">${item.documentItemName }</label>
																	<input type="hidden" name="documentItemCode"
																		value="${item.documentItemCode}" /> <input
																		type="hidden" name="documentItemNo"
																		value="${item.documentItemNo}" /><input type="hidden"
																		name="isFromToItem" value="${item.isFromToItem}" /> <span
																		style="float: right; padding-right: 25px"> <input
																		type="checkbox" name="checkbox"
																		<c:if test="${item.inputRequire ==1 }">checked</c:if> />
																	</span>
																</div>
																	</c:otherwise>
																</c:choose>
															</c:forEach>
														</div>
													</td>
												</tr>
												<tr></tr>
												<tr>
													<td id="blanktd" class="col-xs-3 text-left" colspan='2'>
														<c:forEach items="${info.unDefineInfo }" var="item">
															<c:if test="${item.isBlankItem == 1}">
																<div id="blankItem" class="sort-item" type="number"
																	label="2222"
																	style="border: 1px solid #ddd; cursor: move">
																	<label style="padding-left: 25px;">${item.documentItemName}</label>
																	<span style="float: right; padding-right: 25px"
																		onclick="if ($(this).closest('td').attr('id') != 'blanktd') { $(this).closest('div').remove(); } ">x
																	</span> <input type="hidden" name="documentItemCode"
																		value="${item.documentItemCode}" /> <input
																		type="hidden" name="documentItemNo"
																		value="${item.documentItemNo}" /> <input
																		type="hidden" name="checkbox">
																</div>

															</c:if>
														</c:forEach>
													</td>
													<td colspan='2'></td>
													<td colspan='2'></td>
												</tr>
											</tbody>
										</table>
									</div>



					</div>
					<!-- 查询结果end -->
					<!-- 底部按钮start -->
					<div class="buttongroup">
						<button type="submit" class='btn btn-primary'
							name="define_form_submit" id="define_form_submit">保存</button>
						</input> <input type="button" class="btn" name="cancel" value="返回" id="ret" />
					</div>
					<!-- 底部按钮end -->
				</form>
			</fieldset>

		</div>
	</div>
</div>
<script src="js/module/docmain/docEnterFormDefine.js"></script>

<script>
	$(function() {

		var docEnterFormDefineJS = new Globals.docEnterFormDefineJS();
		docEnterFormDefineJS.InitPage();
	});
</script>
<!-- 人员检索 PopUp -->
<jsp:include page="../com/StaffSearchPopup.jsp" />
<%@include file="../include/inc_footer.jsp"%>
