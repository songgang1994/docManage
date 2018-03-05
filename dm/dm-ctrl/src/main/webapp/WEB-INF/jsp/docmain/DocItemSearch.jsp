<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%--
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：文档数据项管理
 */
--%>

<%@include file="../include/inc_header.jsp"%>

<fieldset>
	<legend>文档数据项查询</legend>
	<div class="row">
	<form id="form-search" action="docmain/searchList" method="POST">
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<!-- 查询条件  第一行 -->
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
					<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
						<span>数据项名称</span>
					</div>
					<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
						 <input type="text" name="docItemsName" id="docItemsName" value="${docItemName}"
							maxlength="20"  style="width: 100%"/>
					</div>
				</div>
			</div>
			<!-- 查询条件  第二行 -->
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
					<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
						<span>数据项类型</span>
					</div>
					<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
							<select id="docItemsType" name="docItemsType"  style="width: 100%">
							 	<option value=""></option>
								<c:forEach var="item" items="${itemTypes}">
									<c:choose>
										<c:when test="${item.value == itemId}">
											<option value="${item.value}" selected>${item.dispName}</option>
										</c:when>
										<c:otherwise>
											<option value="${item.value}">${item.dispName}</option>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
					</div>
				</div>
			</div>

			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<button id="btn-query" name="doc-btn-query" type="button"
						class="btn btn-primary">查询</button>
					<button id="btn-add-new" type="button" name="btn-add-new"
						class="btn btn-primary" style="float: right">新增</button>
				</div>
			</div>

		</div>
	</form>
	</div>

	<!-- WRAPPER_BEGIN -->
	<div class="wrapper">
		<div class="content-wapper">
				<div  id="docItemTable"  style="display: none">
					<div class='col col-sm-12 col-md-12 col-lg-12 col-xs-12'>
						<table id="tbl-item-search" class="table table-bordered table-hover" style="margin-bottom: 5px; table-layout: fixed;">
							<thead>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</div>
				 <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="nodocItemResult"  class="" style="display: none">
						无查询结果！
				</div>
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="buttongroup">
						<button id="docItemCancel" name="cancel"  class="btn ">返回</button>
					</div>
				</div>
			</div>
	</div>
</fieldset>
<!-- WRAPPER_END -->


<script src="js/module/docmain/DocItemSearch.js"></script>

<script>
	$(function() {
		var docItemSearchJS = new Globals.docItemSearchJS();
		docItemSearchJS.InitDocItemSearch();
	});
</script>

<%@include file="../include/inc_footer.jsp"%>
