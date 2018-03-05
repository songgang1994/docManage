<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<%--
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：文档管理
 */
--%>

<%@include file="../include/inc_header.jsp"%>

<!-- testDatepicker -->
<link
	href="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.css"
	rel="stylesheet" />
<script
	src="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.js"></script>
<script
	src="js/thirdParty/testDatepicker/jQuery-Timepicker/i18n/jquery-ui-timepicker-zh-CN.js"
	type="text/javascript"></script>
<div name="detail">
	<fieldset>
		<legend>

			<c:choose>
				<c:when test="${mode == 0}">文档信息</c:when>
				<c:when test="${mode == 1}">修改文档</c:when>
				<c:when test="${mode == 2 || mode == -1}">填写文档</c:when>
				<c:when test="${mode == 3}">审核文档</c:when>
			</c:choose>

		</legend>
		<form id="form-doc" action="doc/enter" method="POST"
			enctype="multipart/form-data">
			<input type="hidden" name="updateTime"
				value="<fmt:formatDate value="${docDetailInfo.updateDt }" pattern="yyyy-MM-dd HH:mm:ss"/>" /> <input type="hidden"
				name="mode" value="${mode}" /> <input type="hidden"
				id="locationType" value="${locationType }" />

			<c:choose>
				<c:when test="${mode == '-1'}">
					<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
								<span>${docTypeItem.documentItemName}</span>
							</div>
							<div
								class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
								<select name="documentType" style="width: 100%">
									<c:forEach var="type" items="${docTypeItemDS}" varStatus="s">
										<option value="${type.itemValue}"
											<c:if test="${type.itemValue == documentType or s.index == 0}">
	  								selected
	  							</c:if>>
	  							${type.itemName}
	  						</option>
	  					</c:forEach>
	  				</select>
							</div>
						</div>
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
								<span>${docCodeItem.documentItemName}</span>
							</div>
							<div class="col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
							<input type="text" name="documentCode" class="col-sm-10 col-md-10 col-lg-10 col-xs-9 no-padding"
	  					value="${deviceNo}" readonly />
						<input type="submit" id="btn-fetchcode" class="btn btn-xs btn-primary col-sm-2 col-md-2 col-lg-2 col-xs-3 no-padding" value="获取" />


							</div>
						</div></div>
						</c:when>
	  			<c:otherwise>
					<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
								<span>${docTypeItem.documentItemName}</span>
							</div>
							<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
								<input name = "documentType" type="hidden" value="${documentType}"/>
								<select name="documentType1" style="width:100%" disabled>
				  					<c:forEach var="type" items="${docTypeItemDS}" varStatus="s">
				  						<option value="${type.itemValue}"
				  							<c:if test="${type.itemValue == documentType or s.index == 0}">
				  								selected
				  							</c:if>>
				  							${type.itemName}
				  						</option>
				  					</c:forEach>
				  				</select>
							</div>
						</div>
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
							<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 ">
								<span>${docCodeItem.documentItemName}</span>
							</div>
							<div class="col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
							<input type="text" name="documentCode" class="col-sm-10 col-md-10 col-lg-10 col-xs-9 no-padding" style="width: 100%"
	  							value="${documentCode}" readonly />

							</div>

					</div>

					<c:if test="${formItems.size()> 0 }"></div></c:if>
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
										<c:if test="${s.index > 0}">
							</div>
							</c:if>
							<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
								<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding"></div>
									<c:set var="leftExists" value="${true}" />
						</c:if>
						<c:if test="${formItem.leftLayoutCol}">
						<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
					    <div class="col col-sm-12 col-md-2 col-lg-2 col-xs-12">
								<span>${formItem.documentItemName}</span>
							</div>


								<c:choose>
									<c:when test="${not empty formItem.isFromToItem && formItem.isFromToItem == 1}">

										<div class="form-group col col-sm-5 col-md-4 col-lg-4 col-xs-12 no-padding">
											<c:set var="_formItem" value="${formItem}" />
											<%@ include file="include/FormItem.jsp"%>
										</div>

										<c:forEach var="_formItem"
											items="${extraItems[formItem.documentItemCode]}">
											<div class="col  col-sm-1 col-md-1 col-lg-1 col-xs-12 text-center" style="padding-right:1%">
											To
											</div>

											<div class="form-group  col-sm-5 col-md-4 col-lg-4 col-xs-12 no-padding">
												<%@ include file="include/FormItem.jsp"%>
											</div>
										</c:forEach>

									</c:when>
									<c:otherwise>
										<div
											class="form-group  col-sm-12 col-md-9 col-lg-9 col-xs-12 no-padding">
											<c:set var="_formItem" value="${formItem}" />
											<%@ include file="include/FormItem.jsp"%>
										</div>
									</c:otherwise>
								</c:choose>
						</div>
						</c:if>
							<c:if test="${!formItem.leftLayoutCol}">
						<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
													<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
								<span>${formItem.documentItemName}</span>
							</div>


								<c:choose>
									<c:when test="${not empty formItem.isFromToItem && formItem.isFromToItem == 1}">

										<div class="form-group col col-sm-5 col-md-4 col-lg-4 col-xs-12 no-padding">
											<c:set var="_formItem" value="${formItem}" />
											<%@ include file="include/FormItem.jsp"%>
										</div>

										<c:forEach var="_formItem"
											items="${extraItems[formItem.documentItemCode]}">
											<div class="col  col-sm-1 col-md-1 col-lg-1 col-xs-12 text-center" style="padding-right:1%">
											To
											</div>

											<div class="form-group  col-sm-5 col-md-4 col-lg-4 col-xs-12 no-padding">
												<%@ include file="include/FormItem.jsp"%>
											</div>
										</c:forEach>

									</c:when>
									<c:otherwise>
										<div
											class="form-group  col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
											<c:set var="_formItem" value="${formItem}" />
											<%@ include file="include/FormItem.jsp"%>
										</div>
									</c:otherwise>
								</c:choose>
						</div>
						</c:if>




						<c:if test="${formItem.layoutCol == 2}">
							<c:set var="leftExists" value="${false}" />
						</c:if>
						</c:forEach>
			</div>

						</c:otherwise>
			</c:choose>





	    <div class="buttongroup">
	    	<c:if test="${mode == 1 ||mode == 2 }">
	    	<input id="btn-save" class="btn btn-primary"
	    		data-mode="${mode}" type="submit" value="保存" />
	    	<input id="btn-commit" class="btn btn-primary" type="submit" value="提交" />
	    	</c:if>
			<c:if test="${mode== 3}">
	    	<input id="btn-approve" class="btn btn-primary" type="submit" value="审核通过" />
	    	<input id="btn-decline" class="btn btn-primary" type="submit" value="退回" />
			</c:if>
	    	<input class="btn" id="btn-back" type="button" value="返回" />
		</div>

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
<script src="js/module/doc/docEnter.js"></script>

<script>
$(function() {
	var docEnterJS = new Globals.docEnterJS();
	docEnterJS.InitDocEnterJS();
});
</script>


<!-- 人员检索 PopUp -->
<jsp:include page="../com/StaffSearchPopup.jsp" />


<%@include file="../include/inc_footer.jsp"%>