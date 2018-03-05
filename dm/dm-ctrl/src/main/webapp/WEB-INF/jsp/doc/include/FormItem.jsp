<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
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
		<c:if test="${(mode == 3 ||mode == 0)}">
			<input class="item-type-text" type="text"
				name="${_formItem.dbItemName}" value="${_formItem.value}" disabled
				style="width: 100%" />
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
			<input class="item-type-text" type="text"
				name="${_formItem.dbItemName}" value="${_formItem.value}"
				<c:if test="${_formItem.readonly}">readonly</c:if>
				<c:if test="${_formItem.inputRequire == 1}">
				required
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
				<c:if test="${not empty _formItem.maxLength}">
				maxlength="${_formItem.maxLength}"
				data-rule-maxlength="${_formItem.maxLength}"
				data-msg-maxlength="${_formItem.documentItemName}最大长度限制为${_formItem.maxLength}"
			</c:if>
				<c:if test="${_formItem.documentItemCode == 'DOCUMENT_ITEM_F00002'}">
				data-rule-old_Code="true"
				data-msg-old_Code="旧文件编号已录入。"
			</c:if>
				style="width: 100%" />
		</c:if>


	</c:when>
	<c:when test="${_formItem.documentItemType == 2}">

		<!-- 多行文本 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
			<textarea class="item-type-multiline"
				style="resize: none; width: 100%" rows="4"
				name="${_formItem.dbItemName}" disabled>${_formItem.value}</textarea>
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
			<textarea class="item-type-multiline"
				style="resize: none; width: 100%" rows="4"
				name="${_formItem.dbItemName}"
				<c:if test="${_formItem.readonly}">readonly</c:if>

			<c:if test="${_formItem.inputRequire == 1}">
				required
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
			<c:if test="${not empty _formItem.maxLength}">
				maxlength="${_formItem.maxLength}"
				data-rule-maxlength="${_formItem.maxLength}"
				data-msg-maxlength="${_formItem.documentItemName}最大长度限制为${_formItem.maxLength}"
			</c:if>
			>${_formItem.value}</textarea>
		</c:if>


	</c:when>
	<c:when test="${_formItem.documentItemType == 3}">
		<!-- 整型 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
		<input class="item-type-integer form-valid-integer" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
			style="width: 100%"
			/>
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<input class="item-type-integer form-valid-integer" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"

			<c:if test="${_formItem.readonly}">readonly</c:if>
			<c:if test="${_formItem.inputRequire == 1}">
				required
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
			<c:if test="${not empty _formItem.maxLength}">
				maxlength="${_formItem.maxLength}"
				data-rule-maxlength="${_formItem.maxLength}"
				data-msg-maxlength="${_formItem.documentItemName}最大长度限制为${_formItem.maxLength}"
			</c:if>
			<c:if test="${_formItem.documentItemNo == 1}">
			data-rule-fromTo="true"
			data-msg-fromTo="to项目不得小于from项目"
			</c:if>

			data-rule-integer="true"
			data-msg-integer="${_formItem.documentItemName}必须为整数类型"
			style="width: 100%"
			/>
		</c:if>

	</c:when>
	<c:when test="${_formItem.documentItemType == 4}">

		<!-- 金额 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
		<input class="item-type-currency form-valid-currency" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
		 	style="width: 100%"
		 	/>
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<input class="item-type-currency form-valid-currency" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			style="width: 100%"
			<c:if test="${_formItem.readonly}">readonly</c:if>
			<c:if test="${_formItem.inputRequire == 1}">
				required
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
			<c:if test="${not empty _formItem.maxLength}">
				maxlength="${_formItem.maxLength}"
				data-rule-maxlength="${_formItem.maxLength}"
				data-msg-maxlength="${_formItem.documentItemName}最大长度限制为${_formItem.maxLength}"
			</c:if>
			<c:if test="${_formItem.documentItemNo == 1}">
			data-rule-fromTo="true"
			data-msg-fromTo="to项目不得小于from项目"
			</c:if>

			data-rule-currency="true"
			data-msg-currency="${_formItem.documentItemName}必须是金额类型" />

		</c:if>

	</c:when>
	<c:when test="${_formItem.documentItemType == 5}">

		<!-- 小数 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
		<input class="item-type-decimal form-valid-decimal" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
			style="width: 100%"
			onblur="fmoney(this)"
			/>
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<input class="item-type-decimal form-valid-decimal" type="text" onblur="fmoney(this)"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
		    style="width: 100%"
			<c:if test="${_formItem.readonly}">readonly</c:if>
			<c:if test="${_formItem.inputRequire == 1}">
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
			<c:if test="${not empty _formItem.maxLength}">
				maxlength="${_formItem.maxLength}"
				data-rule-maxlength="${Math.ceil(_formItem.maxLength/3)+_formItem.maxLength}"
				data-msg-maxlength="${_formItem.documentItemName}最大长度限制为${_formItem.maxLength}"
			</c:if>
			<c:if test="${_formItem.documentItemNo == 1}">
			data-rule-fromTo="true"
			data-msg-fromTo="to项目不得小于from项目"
			</c:if>

			data-rule-decimal="true"
			data-msg-decimal="${_formItem.documentItemName}必须是小数类型" />

		</c:if>

	</c:when>
	<c:when test="${_formItem.documentItemType == 6}">

		<!-- 日期 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
		<input class="item-type-date" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			disabled
			style="width: 100%"
			placeholder="年/月/日"
			 />
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<c:if test="${_formItem.readonly}">
		<input type="hidden"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}" />
		</c:if>

		<input class="item-type-date" type="text"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}"
			<c:if test="${_formItem.readonly}">disabled</c:if>

			<c:if test="${_formItem.inputRequire == 1}">
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>

			<c:if test="${_formItem.documentItemNo == 1}">
			data-rule-fromTo="true"
			data-msg-fromTo="to项目不得小于from项目"
			</c:if>
           style="width: 100%"
           placeholder="年/月/日"
			 />
		</c:if>

	</c:when>
	<c:when test="${_formItem.documentItemType == 7}">

		<!-- 下拉列表 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
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
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<select class="item-type-dropdown" style="width: 100%"
		<c:if test="${_formItem.inputRequire == 1}">
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
			<c:if test="${_formItem.readonly}">disabled</c:if>
			name="${_formItem.dbItemName}">
			<c:choose>
			<c:when test="${not empty _formItem.dataSourceList}">
			<c:forEach var="dsi" items="${_formItem.dataSourceList}" varStatus="s">
				<option value="${dsi.itemValue}"
					<c:if test="${_formItem.value == dsi.itemValue}">selected</c:if> >
					${dsi.itemName}
				</option>
			</c:forEach>
			</c:when>
			<c:otherwise>
			<c:forEach var="dsi" items="${_formItem.paramMstList}" varStatus="s">
				<option value="${dsi.value}"
				<c:if test="${_formItem.value == String.valueOf(dsi.value)}">selected</c:if>>
					${dsi.dispName}
				</option>
			</c:forEach>
			</c:otherwise>
			</c:choose>

		</select>

		<c:if test="${_formItem.readonly}">
		<input type="hidden"
			name="${_formItem.dbItemName}"
			value="${_formItem.value}" />
		</c:if>
		</c:if>

	</c:when>
	<c:when test="${_formItem.documentItemType == 8}">

		<!-- 复选框 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
		<c:if test="${_formItem.dataSourceList != null}">
		<c:forEach var="dsi" items="${_formItem.dataSourceList}" varStatus="s">
			<input class="item-type-checkbox" type="checkbox" disabled
				name="${_formItem.dbItemName}"
				<c:if test="${_formItem.values.contains(dsi.itemValue)}">checked</c:if>

			/>
			${dsi.itemName}
		</c:forEach>
		</c:if>
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<c:if test="${_formItem.dataSourceList != null}">
		<c:forEach var="dsi" items="${_formItem.dataSourceList}" varStatus="s">
			<input class="item-type-checkbox" type="checkbox"
				name="${_formItem.dbItemName}"
				value="${dsi.itemValue}"
				<c:if test="${_formItem.values.contains(dsi.itemValue)}">checked</c:if>
				<c:if test="${_formItem.inputRequire == 1}">
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
				/>
			${dsi.itemName}
		</c:forEach>

		</c:if>
		</c:if>
	</c:when>
	<c:when test="${_formItem.documentItemType == 9}">

		<!-- 单选 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
		<c:if test="${_formItem.dataSourceList != null}">
		<c:forEach var="dsi" items="${_formItem.dataSourceList}" varStatus="s">
			<input class="item-type-radio" type="radio" disabled
				name="${_formItem.dbItemName}"
				<c:if test="${_formItem.values.contains(dsi.itemValue)}">checked</c:if>
				/>
			${dsi.itemName}
		</c:forEach>

		</c:if>
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<c:if test="${_formItem.dataSourceList != null}">
		<c:forEach var="dsi" items="${_formItem.dataSourceList}" varStatus="s">
			<input class="item-type-radio" type="radio"
				name="${_formItem.dbItemName}"
				<c:if test="${_formItem.values.contains(dsi.itemValue)}">checked</c:if>
				value="${dsi.itemValue}"
				 <c:if test="${_formItem.inputRequire == 1}">
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
				/>
			${dsi.itemName}
		</c:forEach>
		</c:if>
		</c:if>
	</c:when>
	<c:when test="${_formItem.documentItemType == 10}">
	<input name="mFile" value="${formItem.documentItemCode}" type="hidden" />

		<!-- 单文件上传/主文件 -->
		<c:choose><c:when test="${mode == 2}">
		<div>
			<input class="item-type-singlefile" type="file"
				name="mainFile"
				<c:if test="${_formItem.inputRequire == 1}">
					required
					data-rule-required="${_formItem.inputRequire == 1}"
					data-msg-required="${_formItem.documentItemName}为必填项"
				</c:if>
				<c:if test="${not empty _formItem.maxLength}">
					maxlength="${_formItem.maxLength}"
					data-rule-maxlength="${Math.ceil(_formItem.maxLength/3)+_formItem.maxLength}"
					data-msg-maxlength="${_formItem.documentItemName}最大长度限制为${_formItem.maxLength}"
				</c:if>
				/>
				</div>
		</c:when><c:otherwise>

				<div>
				<span class="file-main-file" >
					<a href="doc/download/${mainFile.documentCode}/1">
						${mainFile.fileName}
					</a>
				</span>

				<c:if test="${mode == 1 && not empty mainFile}">
				<span class="delMainFileSpan" data-target-require="${_formItem.inputRequire == 1}">[删除]
				</span>
				<span class="updMainFileSpan">[更新]
				</span>
				<input type="file" style="display:none" name="mainFile" class="mainFile" />
					</c:if>
				<c:if test="${mode == 1 && empty mainFile}">
					<input type="file"  name="mainFile" class="mainFile" />
				</c:if>
				</div>
		</c:otherwise></c:choose>

	</c:when>

	<c:when test="${_formItem.documentItemType == 11}">

		<!-- 多文件上传/子文件 -->
		<c:if test="${(mode == 3 ||mode == 0) && not empty subFiles}">
		<c:forEach var="f" items="${subFiles}">
		<span class="file-sub-file">
				<a href="doc/download/${f.documentCode}/2?fileNo=${f.fileNo}">
				${f.fileName}
			</a>
		</span>
		</c:forEach>
		</c:if>
		<c:if test="${(mode == 2 ||mode == 1) }">
		<c:if test="${not empty subFiles}">
		<input type="hidden" name="delSubFilesNo" value="">
		<input type="hidden" name="updSubFilesNo" value="">
		<c:forEach var="f" items="${subFiles}">
		<div>
		<input type="hidden" name="subFiles" value="${f.fileName}" />
		<span class="file-sub-file">
			<a href="doc/download/${f.documentCode}/2?fileNo=${f.fileNo}">
				${f.fileName}
			</a>
			<input type="hidden" name="fileNo" value="${f.fileNo}" />
		</span>
		<span class="delSubFileSpan">[删除]
		</span>
		<span class="updSubFileSpan">[更新]
		</span>
		<input type="file" style="display:none" name="updSubFiles" class="updSubFiles"/>
		</div>
		</c:forEach>
		</c:if>

		<div>
		<span class="item-type-multifile" data-target-require="${_formItem.inputRequire == 1}">添加</span>

		<input name="subFiles" type="file"
				data-target-require="${_formItem.inputRequire == 1}"
				data-rule-needFile="true"
				data-msg-needFile="${_formItem.documentItemName}为必填项"
		/>
		</div>

		</c:if>

	</c:when>
	<c:when test="${_formItem.documentItemType == 12}">

		<!-- PopUp 人员检索 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
		<input type="text" disabled name="__TEXT_${_formItem.dbItemName}" class="col-md-8" value="${_formItem.value}" style="font-weight: normal;width:95%"  title="${_formItem.value}"/>
			<span class="item-type-popup-staff " disabled><i class="fa fa-search"></i></span>

		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<input type="text" readonly name="__TEXT_${_formItem.dbItemName}" class="col-md-8" style="font-weight: normal;width:95%"  title="${_formItem.value}"
			<c:if test="${_formItem.readonly}">readonly</c:if> value="${_formItem.value}"/>
			<span class="item-type-popup-staff " <c:if test="${_formItem.readonly}">disabled</c:if>
			data-target-text="__TEXT_${_formItem.dbItemName}"
			data-target-value="${_formItem.dbItemName}"><i class="fa fa-search"></i></span>

		<input type="hidden"
			name="${_formItem.dbItemName}"
			value="${_formItem.key}"
			<c:if test="${_formItem.inputRequire == 1}">
				required
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
			/>
		</c:if>
	</c:when>


	<c:when test="${_formItem.documentItemType == 13}">

		<c:if test="${(mode == 3 ||mode == 0)}">
		<input type="text" disabled name="__TEXT_${_formItem.dbItemName}" class="col-md-8" value="${_formItem.value}" style="font-weight: normal;width:95%" title="${_formItem.value}"/>
			<span class="item-type-popup-dept "disabled ><i class="fa fa-search"></i></span>

		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<input type="text" readonly name="__TEXT_${_formItem.dbItemName}" class="col-md-8"  style="font-weight: normal;width:95%" title="${_formItem.value}"
			<c:if test="${_formItem.readonly}" >readonly</c:if> value="${_formItem.value}"/>
			<span class="item-type-popup-dept " <c:if test="${_formItem.readonly}">disabled</c:if>
			data-target-text="__TEXT_${_formItem.dbItemName}"
			data-target-value="${_formItem.dbItemName}"><i class="fa fa-search"></i></span>

			<input type="hidden"
			name="${_formItem.dbItemName}" value="${_formItem.key}"
			value="${_formItem.key}"
			<c:if test="${_formItem.inputRequire == 1}">
				required
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
			/>
		</c:if>
	</c:when>

	<c:when test="${_formItem.documentItemType == 14}">

		<!-- PopUp 人员检索 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
		<input type="text" disabled name="__TEXT_${_formItem.dbItemName}" class="col-md-8"  style="font-weight: normal;width:95%" title="${_formItem.value}"
			 value="${_formItem.value}"/>
			<span class="item-type-popup-staff-mul " disabled
			><i class="fa fa-search"></i></span>

		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<input type="text" readonly name="__TEXT_${_formItem.dbItemName}" class="col-md-8"  style="font-weight: normal;width:95%" title="${_formItem.value}"
			<c:if test="${_formItem.readonly}">readonly</c:if> value="${_formItem.value}" />
			<span class="item-type-popup-staff-mul " <c:if test="${_formItem.readonly}">disabled</c:if>
			data-target-text="__TEXT_${_formItem.dbItemName}"
			data-target-value="${_formItem.dbItemName}"><i class="fa fa-search"></i></span>

		<input type="hidden"
			name="${_formItem.dbItemName}"
			value="${_formItem.key}"
			<c:if test="${_formItem.inputRequire == 1}">
				required
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
			/>
		</c:if>

	</c:when>


	<c:when test="${_formItem.documentItemType == 15}">
		<!-- PopUp 部门检索 -->


		<c:choose>
		<c:when test="${empty _formItem.dbItemName}">
			<!-- 文档可查看部门 -->
		<c:if test="${(mode == 3 ||mode == 0)}">
					<input type="text" class="col-md-8" disabled name="__TEXT_DOCUMENT_VIEW_DEPT" value="${_formItem.value}"  style="font-weight: normal;width:95%" title="${_formItem.value}"/>
			<span class="item-type-popup-dept-mul " disabled><i class="fa fa-search"></i></span>

		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<input type="text" class="col-md-8" readonly name="__TEXT_DOCUMENT_VIEW_DEPT"  style="font-weight: normal;width:95%" title="${_formItem.value}"
			<c:if test="${_formItem.readonly}" >readonly</c:if> value="${_formItem.value}"/>
			<span class="item-type-popup-dept-mul " <c:if test="${_formItem.readonly}">disabled</c:if>
			data-target-text="__TEXT_DOCUMENT_VIEW_DEPT"
			data-target-value="DOCUMENT_VIEW_DEPT"><i class="fa fa-search"></i></span>
			<input type="hidden" name="DOCUMENT_VIEW_DEPT" value="${_formItem.key}"
			<c:if test="${_formItem.inputRequire == 1}">
				required
				data-rule-required="${_formItem.inputRequire == 1}"
				data-msg-required="${_formItem.documentItemName}为必填项"
			</c:if>
			/>
		</c:if>
		</c:when>
		<c:otherwise>
		<c:if test="${(mode == 3 ||mode == 0)}">
		<input type="text" disabled name="__TEXT_${_formItem.dbItemName}" class="col-md-8" value="${_formItem.value}"  style="font-weight: normal;width:95%" title="${_formItem.value}"/>
		<span class="item-type-popup-dept-mul " disabled
		><i class="fa fa-search"></i></span>

		</c:if>
		<c:if test="${(mode == 2 ||mode == 1)}">
		<input type="text" readonly name="__TEXT_${_formItem.dbItemName}" class="col-md-8"  style="font-weight: normal;width:95%" title="${_formItem.value}"
		<c:if test="${_formItem.readonly}" >readonly</c:if> value="${_formItem.value}" />
		<span class="item-type-popup-dept-mul " <c:if test="${_formItem.readonly}">disabled</c:if>
		data-target-text="__TEXT_${_formItem.dbItemName}"
		data-target-value="${_formItem.dbItemName}"><i class="fa fa-search"></i></span>
		<input type="hidden"
			name="${_formItem.dbItemName}" value="${_formItem.key}"
			<c:if test="${_formItem.inputRequire == 1}">
			required
			data-rule-required="${_formItem.inputRequire == 1}"
			data-msg-required="${_formItem.documentItemName}为必填项"
		</c:if>
			/>
		</c:if>
		</c:otherwise>
		</c:choose>


	</c:when>
</c:choose>