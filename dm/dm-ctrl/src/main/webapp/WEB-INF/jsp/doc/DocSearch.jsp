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
<div id="MDocBatchStub">
	<fieldset>
		<legend>文档查询</legend>

		<!-- 	顶部按钮 start -->
		<div class=" buttongroup">
			<a name="querySetting"
				href="docmain/searchdefaultitemedit?locationType=-1"
				id="querySetting" class="btn btn-primary" style="float: right">查询条件设置</a>
			<button id="queryHide" name="queryHide" class="btn btn-primary " style="float: right;margin-right: 2px"">显示</button>
			<br>
		</div>
		<!-- 	顶部按钮 end -->
		<!-- 	查询条件 start -->
		<div class="row" id="query" style="display: none">

			<c:set var="leftExists" value="${false}" />
			<c:forEach var="formItem" items="${formItems}" varStatus="s">

				<c:if test="${formItem.layoutCol == 1}">
					<c:if test="${s.index > 0}">
		</div>
		</c:if>
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<c:set var="leftExists" value="${true}" />
			</c:if>
			<c:if test="${formItem.layoutCol == 2 and !leftExists}">
</div>
				<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 <c:if test="${formItem.layoutCol == 1} ">no-padding</c:if>"></div>
						<c:set var="leftExists" value="${true}" />
			</c:if>

			<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 <c:if test="${formItem.layoutCol == 1} ">no-padding</c:if>">
				<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
					<span>${formItem.documentItemName}</span>
				</div>

				<c:choose>
					<c:when
						test="${not empty formItem.isFromToItem && formItem.isFromToItem == 1}">

						<div
							class="form-group col col-sm-4 col-md-3 col-lg-3 col-xs-12 <c:if test="${formItem.layoutCol == 2} ">no-padding</c:if>">
							<c:set var="_formItem" value="${formItem}" />
							<%@ include file="include/FormItem2.jsp"%>
						</div>

						<c:forEach var="_formItem"
							items="${extraItems[formItem.documentItemCode]}">
							<div class="col  col-sm-1 col-md-1 col-lg-1 col-xs-12"
								style="padding-right: 1%">To</div>

							<div
								class="form-group  col-sm-4 col-md-3 col-lg-3 col-xs-12 ">
								<%@ include file="include/FormItem2.jsp"%>
							</div>
						</c:forEach>

					</c:when>
					<c:otherwise>
						<div
							class="form-group  col-sm-9 col-md-7 col-lg-7 col-xs-12 <c:if test="${formItem.layoutCol == 1} ">no-padding</c:if>">
							<c:set var="_formItem" value="${formItem}" />
							<%@ include file="include/FormItem2.jsp"%>
						</div>
					</c:otherwise>
				</c:choose>

			</div>
			<c:if test="${formItem.layoutCol == 2}">
				<c:set var="leftExists" value="${false}" />
			</c:if>
			</c:forEach>
		</div>
</div>

<!-- 	查询条件 end -->

<!-- 	查询按钮 start -->

<div class=" buttongroup">
	<button id="queryDoc" name="queryDoc" type="button"
		class="btn btn-primary" style="float: left">查询</button>
	<a class="btn btn-primary" href="doc/enter?mode=-1&locationType=2"
		style="float: right">新建</a>
</div>
<!-- 查询按钮 end -->

<!-- 	查询结果 start -->
<div class="row">
	<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12"
		id="queryTable" style="width: 98%;">
		<div style="padding-left: 1%">
			<table id="docSearchTable"
				class="table vertical-align table-bordered table-hover dataTable no-footer" width="100%">
				<thead >
					<tr>
						<c:forEach items="${listItems }" var="item">
							<th class="text-center" style="white-space: nowrap">${item.documentItemName }</th>
						</c:forEach>
						<th class="text-center" style="white-space: nowrap">操作</th>
					</tr>
				</thead>
				<tbody class="text-left">

				</tbody>
			</table>
		</div>
	</div>
	<div class="row-fluid" align="center" id="noResult"
		style="display: none">无查询结果！</div>
</div>
<!-- 查询结果 end -->

<!-- 底部按钮 start -->
<div class="buttongroup" id="expartDataDiv">
	<input name="expartData" type="button" class="btn btn-primary"
		value="数据导出" id="expartData" style="display: none; margin-right: 0px" />
	<input id="DocSearchCancel" name="cancel" type="button"
		class="btn" value="返回" />
</div>
<!-- 底部按钮 end -->
</form>

</fieldset>
</div>
<script type="text/javascript">
function fmoney(even)
{
	s= $(even).val();
 	var a="";
	if(Number.isInteger(parseInt(s))){
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(2) + "";
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
		t = "";
		for (i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		}
   		 a = t.split("").reverse().join("") + "." + r;
	}
    $(even).val(a);
}
</script>
<script src="js/module/doc/docSearch.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var docSearchJS = new Globals.docSearchJS();
		docSearchJS.InitPage();
	});
</script>

<%@include file="../include/inc_footer.jsp"%>
