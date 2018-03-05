<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spt" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:choose>
	<c:when test="${_formItem.documentItemType == 0}">
		<!-- 空白 -->
		#BLANK#
	</c:when>
	<c:when test="${_formItem.documentItemType == 1}">

		<!-- 文本 -->
		<input class="item-type-text" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
			style="width: 100%"
			/>

	</c:when>
	<c:when test="${_formItem.documentItemType == 2}">

		<!-- 多行文本 -->
		<textarea class="item-type-multiline" style="resize:none"
			name="${_formItem.dbItemName}"
			disabled
			style="width: 100%" rows="4"
			>${_formItem.value}</textarea>

	</c:when>
	<c:when test="${_formItem.documentItemType == 3}">
		<!-- 整型 -->
		<input class="item-type-integer form-valid-integer" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
			style="width: 100%"
			/>

	</c:when>
	<c:when test="${_formItem.documentItemType == 4}">

		<!-- 金额 -->
		<input class="item-type-currency form-valid-currency" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
		 	style="width: 100%"
		 	/>

	</c:when>
	<c:when test="${_formItem.documentItemType == 5}">

		<!-- 小数 -->
		<input class="item-type-decimal form-valid-decimal" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
			style="width: 100%"
			onblur="fmoney(this)"
			/>

	</c:when>
	<c:when test="${_formItem.documentItemType == 6}">


		<input class="item-type-date" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
			style="width: 100%"

			 />

	</c:when>
	<c:when test="${_formItem.documentItemType == 7}">
		<!-- 下拉列表 -->
		<select class="item-type-dropdown"
			disabled
			style="width: 100%"

			name="${_formItem.dbItemName}">
			<c:if test="${not empty _formItem.dataSourceList}">
			<c:forEach var="dsi" items="${_formItem.dataSourceList}" varStatus="s" >
				<option value="${dsi.itemValue}"
					<c:if test="${_formItem.value == dsi.itemValue}">selected</c:if> >
					${dsi.itemName}
				</option>
			</c:forEach>
			</c:if>

			<c:if test="${empty _formItem.dataSourceList}">
			<c:forEach var="dsi" items="${_formItem.paramMstList}" varStatus="s">
				<option value="${dsi.value}"
				<c:if test="${_formItem.value == String.valueOf(dsi.value)}">selected</c:if>>
					${dsi.dispName}
				</option>
			</c:forEach>
			</c:if>
		</select>

	</c:when>
	<c:when test="${_formItem.documentItemType == 8}">

		<!-- 复选框 -->
		<c:if test="${_formItem.dataSourceList != null}">
		<c:forEach var="dsi" items="${_formItem.dataSourceList}" varStatus="s">
			<input class="item-type-checkbox" type="checkbox" disabled
				name="${_formItem.dbItemName}"
				<c:if test="${_formItem.value.contains(dsi.itemValue)}">checked</c:if>

			/>
			${dsi.itemName}
		</c:forEach>
		</c:if>

	</c:when>
	<c:when test="${_formItem.documentItemType == 9}">

		<!-- 单选 -->
		<c:if test="${_formItem.dataSourceList != null}">
		<c:forEach var="dsi" items="${_formItem.dataSourceList}" varStatus="s">
			<input class="item-type-radio" type="radio" disabled
				name="${_formItem.dbItemName}"
				<c:if test="${_formItem.value.contains(dsi.itemValue)}">checked</c:if>
				/>
			${dsi.itemName}
		</c:forEach>

		</c:if>

	</c:when>
	<c:when test="${_formItem.documentItemType == 10}">

		<!-- 单文件上传/主文件 -->
		<input class="item-type-text" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
			style="width: 100%"

			/>

	</c:when>
	<c:when test="${_formItem.documentItemType == 11}">

		<!-- 多文件上传/子文件 -->
		<input class="item-type-text" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
			style="width: 100%"

			/>

	</c:when>
	<c:when test="${_formItem.documentItemType == 12}">

		<!-- PopUp 人员检索 -->
		<input type="text" disabled name="__TEXT_${_formItem.dbItemName}" class="col-md-8" value="${_formItem.value}" style="font-weight: normal;width:95%" title="${_formItem.value}"/>
			<span class="item-type-popup-staff " disabled><i class="fa fa-search"></i></span>

	</c:when>


	<c:when test="${_formItem.documentItemType == 13}">
		<c:choose>
		<c:when test="${empty _formItem.dbItemName}">
			<!-- 文档可查看部门 -->

			<input type="text" class="col-md-8" disabled name="__TEXT_DOCUMENT_VIEW_DEPT" value="${_formItem.value}" style="font-weight: normal;width:95%" title="${_formItem.value}"/>
			<span class="item-type-popup-dept " disabled><i class="fa fa-search"></i></span>
		</c:when>
		<c:otherwise>
		<input type="text" disabled name="__TEXT_${_formItem.dbItemName}" class="col-md-8" value="${_formItem.value}" style="font-weight: normal;width:95%" title="${_formItem.value}"/>
			<span class="item-type-popup-dept "disabled ><i class="fa fa-search"></i></span>

		</c:otherwise>
		</c:choose>
	</c:when>

	<c:when test="${_formItem.documentItemType == 14}">

		<!-- PopUp 人员检索 -->
		<input type="text" disabled name="__TEXT_${_formItem.dbItemName}" class="col-md-8"
			 value="${_formItem.value}" style="font-weight: normal;width:95%" title="${_formItem.value}"/>
			<span class="item-type-popup-staff-mul " disabled
			><i class="fa fa-search"></i></span>

	</c:when>


	<c:when test="${_formItem.documentItemType == 15}">
		<!-- PopUp 部门检索 -->
		<input type="text" disabled name="__TEXT_${_formItem.dbItemName}" class="col-md-8" value="${_formItem.value}" style="font-weight: normal;width:95%" title="${_formItem.value}"/>
		<span class="item-type-popup-dept-mul " disabled
		><i class="fa fa-search"></i></span>

	</c:when>
</c:choose>