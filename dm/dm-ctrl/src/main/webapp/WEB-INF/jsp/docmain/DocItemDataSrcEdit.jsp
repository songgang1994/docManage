<!--
	NSK-NRDC业务系统
	作成人：张丽
	新增/修改文档数据项数据源画面
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false" import="com.dm.dto.DocItemDataSourceCodeDto"
	import="com.dm.tool.Constant"%>
<%@include file="../include/inc_header.jsp"%>
		<fieldset>
			<c:choose>
				<c:when test="${dataSrcFlag == 'add'}">
					<legend>新建文档数据项数据源</legend>
				</c:when>
				<c:when test="${dataSrcFlag == 'edit'}">
					<legend>修改文档数据项数据源</legend>
				</c:when>
				<c:otherwise>
					<legend>文档数据项数据源</legend>
				</c:otherwise>
			</c:choose>
			<div class="row " name="detail">
			<form id="dataSrcEditForm" name="dataSrcEditForm">
				<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<!-- 数据源名称 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
							<span>数据源名称</span>
						</div>
						<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
							<input id="documentItemSourceName" class="text"
								 disabled name="documentItemSourceName" type="text"
								value="${documentItemSourceName}" style="width: 100%"> <input
								id="backdocumentItemSourceName" type="hidden"
								value="${documentItemSourceName }"> <input
								id="documentItemSourceCode" type="hidden"
								value="${detailDataSrcCode}"> <input id="dataSrcFlag"
								type="hidden" value="${dataSrcFlag}">
						</div>
					</div>
				</div>

				<!-- 添加条目按钮 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<button id="addDataItem" name="dataSrcBut" type="button" disabled
							class="btn btn-primary remo">添加条目</button>
					</div>
				</div>
				<input  type="hidden" id="maxNum" name="maxNum" value = "0" />

				<div class="wapper">
					<div class="content-wapper">
						<div id="dataSrcItem" class="row-fluid table-content">
							<!-- 条目操作表格 -->
							<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
								<div class="col col-sm-12 col-md-9 col-lg-9 col-xs-12 table-responsive">
									<table class="table table-striped table-bordered"
										id="dataSrcItemTable" style="margin-bottom: 5px;margin-top:6px; table-layout: fixed;">
										<thead>
											<tr>
												<th width="40%" class='text-center' >名称</th>
												<c:if test='${detailDataSrcCode == "DATA_SOURCE_F00001"}'>
													<th width="30%" class='text-center' >主文档编号</th>
												</c:if>
												<c:if test='${detailDataSrcCode != "DATA_SOURCE_F00001"}'>
													<th width="30%" class='text-center' >备注</th>
												</c:if>
												<th width="30%" class='text-center' >操作</th>
											</tr>
										</thead>
										<tbody>
											<!-- 循环 -->
											<c:forEach items="${detailList}" var="detailList" varStatus="loop">
												<tr>
													<td width="40%">
														<div class="form-group has-error">
															<input type="text" disabled name="detailDataItem"
																class="detailDataItem detail" value="${detailList}" style="width: 100%">
														</div>
													</td>
													<td width="30%">
														<div class="form-group has-error">
															<input id="detailOtherList" type="text"
																name="detailDataItemOther" disabled
																class="detailDataItemOther detail"
																value="${detailOtherList[loop.count-1]}" style="width: 100%">
														</div>
													</td>
													<td width="30%" >
														<div style="text-align: left;">
															<a id="remove_down" name="remove_down"
																class="remove_remo" style="padding-right:1%">下移&nbsp;</a>
															<a id="remove_up" name="remove_up"
															     class="remove_remo" style="padding-right:1%">上移&nbsp;</a>
															<a id="remove" name="remove" class="remove_remo" style="padding-right:1%">删除&nbsp;</a>
															<input id="" type="hidden" class="itemVal"
																value="${detailValList[loop.count-1]}" />
														</div>
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="buttongroup">
					<button id="dataSrcSubmit" class="btn btn-primary " style="visibility:visible">保存</button>
					<button id="cancel" class="btn">返回</button>
					<input id="DocItemDataSrcEdit" type="hidden" value="${documentItemSourceName}">
				</div>
				</div>

	</div>
			</form>
	</div>
		</fieldset>
<script src="js/module/docmain/DocItemDataSrcEdit.js"></script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var DocItemDataSrcEditJS = new Globals.DocItemDataSrcEditJS();
		DocItemDataSrcEditJS.InitDocItemDataSrcEdit();
	});
</script>
<%@include file="../include/inc_footer.jsp"%>