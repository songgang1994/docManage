<%@page import="java.math.BigDecimal"%>
<%@page import="com.dm.entity.DocItemsEntity"%>
<%@page import="com.dm.entity.DocItemDataSourceCodeEntity"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%--
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：文档数据项管理
 */
--%>

<%@include file="../include/inc_header.jsp"%>

<fieldset>
	<!-- 查询下拉框内容数据项类型 -->
	<%List<DocItemDataSourceCodeEntity> list = (List)request.getAttribute("list"); %>
	<!-- 通过数据项编号查询的对应的数据 -->
	<%DocItemsEntity doc = (DocItemsEntity)request.getAttribute("docitem"); %>
	<!-- 确定修改新增详情三种模式 -->
	<%int flag = (int)request.getAttribute("flag");%>
	<%if(flag == 2){%>
	<legend>新建文档数据项</legend>
	<%}else if(flag == 1){%>
	<legend>修改文档数据项</legend>
	<%}else{%>
	<legend>文档数据项</legend>
	<%}%>
	<!-- 数据项使用参数隐藏 -->
	<input type="hidden" id="docItemsName1" value="${docItemsName}">
	<input type="hidden" id="itemId1" value="${itemId}"> <input
		type="hidden" id="itemName1" value="${itemName}">
	<div class="row " name="detail">
		<!-- 数据项使用参数隐藏结束 -->
		<form id="form-item">
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<!-- 第一行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>数据项类型</span>
						</div>
						<input id="state" name="state" type="hidden" value=""> <input
							id="flag" name="flag" type="hidden" value=<%=flag%>>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<input type="hidden" id="document_Type" value="${doc.documentItemType}" />
							<select id="documentItemType" name="documentItemType"
								style="width: 100%" onchange="_numbertype()">
							</select>
							<%if(flag == 1){%>
							<input type="hidden" id="documentItemCode"
								name="documentItemCode" value="${documentItemCode }" />
							<%}%>
						</div>
					</div>
				</div>
				<!-- 第二行 -->
				<!-- 通过数据项编号查询的对应的数据 -->
				<%DocItemsEntity docc = (DocItemsEntity)request.getAttribute("doc"); %>
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>数据源选择</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<select id="documentItemSourceCode" name="documentItemSourceCode"
								style="width: 100%">
								<option value=""></option>
								<% for(int i = 0 ; i<list.size();i++){%>
								<option value=<%=list.get(i).getDocumentItemSourceCode() %>
									<%if(flag !=2){%>
									<%if(list.get(i).getDocumentItemSourceCode().equals(docc.getDocumentItemSourceCode())){%>
									selected <%} %> <%}%>><%=list.get(i).getDocumentItemSourceName() %></option>
								<%}%>
							</select>
						</div>
					</div>
				</div>
				<!-- 第三行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>数据项显示名称</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<input id="documentItemName" name="documentItemName" type="text"
								value="${doc.documentItemName}" maxLength="20" style="width: 100%" />
						</div>
					</div>
				</div>
				<!-- 第四行 -->
				<!-- 数字格式 -->
				<div id="numbertype">
					<input id="newnumfot" type="hidden" value="${doc.numberFormat}" />
				</div>
				<!-- 第五行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>最大长度</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<input id="maxLength" name="maxLength" type="text"
								value="${doc.maxLength}" maxLength="4" style="width: 100%" />
						</div>
					</div>
				</div>
				<!-- 第六行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>公共项目</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<%if(flag != 2){%>
							<%if(docc.getIsPublicItem().equals("1")){%>
							<input id="isPublicItem" class="combox" name="isPublicItem"
								type="checkbox" value="1" checked="checked" />
							<%} else{%>
							<input id="isPublicItem" class="combox" name="isPublicItem"
								type="checkbox" value="0" />
							<%}%>
							<%}else{%>
							<input id="isPublicItem" class="combox" name="isPublicItem"
								type="checkbox" value="0" />
							<%}%>
						</div>
					</div>
				</div>
				<!-- 第七行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>FromTo项目</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<%if(flag != 2){%>
							<%if(docc.getIsFromToItem().intValue()==1){%>
							<input id="newFrom" type="hidden" value="1"> <input
								type="checkbox" class="combox" id="isFromToItem"
								name="isFromToItem" value="1" checked="checked" />
							<%}else{%>
							<input id="newFrom" type="hidden" value="0"> <input
								type="checkbox" class="combox" id="isFromToItem"
								name="isFromToItem" value="0" />
							<%}%>
							<%}else{%>
							<input type="checkbox" name="isFromToItem" id="isFromToItem"
								value="0" />
							<%}%>
						</div>
					</div>
				</div>
			<!-- 按钮行 -->
			<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="buttongroup">
					<%if(flag == 1){%>
					<button id="btn-update" class="btn btn-primary ">保存</button>
					<%}else if(flag == 2){%>
					<button id="btn-submit" class="btn btn-primary ">保存</button>
					<%}%>
					<button id="btn-back" class="btn">返回</button>
				</div>
			</div>
			</div>
		</form>
	</div>
</fieldset>

<script src="js/module/docmain/DocItemEdit.js"></script>

<script type="text/javascript">
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var docItemEditJS = new Globals.docItemEditJS();
			docItemEditJS.init();
		});
</script>

<%@include file="../include/inc_footer.jsp"%>
