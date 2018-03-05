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
	table,thead th,tbody td{
   border:1px solid #ddd
}
</style>
<script src="js/thirdParty/sortable/vue.js"></script>
<!-- testDatepicker -->
<link href="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.css" rel="stylesheet" />
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/jquery-ui-timepicker-addon.min.js"></script>
<script src="js/thirdParty/testDatepicker/jQuery-Timepicker/i18n/jquery-ui-timepicker-zh-CN.js" type="text/javascript" ></script>
<!-- multiple-select -->
<!-- <link rel="stylesheet" href="js/thirdParty/multiple-select/multiple-select.css" />
<script src="js/thirdParty/multiple-select/multiple-select.js" type="text/javascript" ></script> -->

<div id="MDocFormDefaultStub">
	<div class="wapper">
		<div class="content-wapper">
			<input type="hidden" id="locationType" value="${locationType }">
				<fieldset>
					<legend>文档查询默认条件设置</legend>
					<form name="doc_enter_form_define" id="doc_enter_form_define"
						method="post">

						<!-- 查询结果start -->
						<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12" style="height:590px;overflow-y:scroll;">
							<table class="table table-striped" id="projectTable" >
								<thead>
									<tr>
										<th class="col-xs-3 text-center" colspan='2' style="border:1px solid #ddd" >数据项</th>
										<th class="col-xs-6 text-center" colspan='4' style="border:1px solid #ddd">查询项目</th>
									</tr>
								</thead>
								<tbody>
									<!-- 循环 -->
									<tr>
										<td class="col-xs-3 text-center" ><span>数据项</span></td>
										<td class="col-xs-1 text-center"><span style="visibility:hidden">操作</span></td>
										<td class="col-xs-3 text-center"><span>左侧显示项</span></td>
										<td class="col-xs-1 text-center"><span>设置</span></td>
										<td class="col-xs-3 text-center"><span>右侧显示项</span></td>
										<td class="col-xs-1 text-center"><span>设置</span></td>
									</tr>
									<tr>
										<td class="col-xs-3 text-left" colspan='2'>
											<div id="sort1" class="connectedSortable">
												<c:forEach items="${items }" var="item">
													<div class="sort-item" style="border:1px solid #ddd;cursor:move">
														<label style="padding-left: 25px;">${item.documentItemName}</label>
														<input type="hidden" name="documentItemCode" value="${item.documentItemCode}" />
														<input type="hidden" name="documentItemNo" value="${item.documentItemNo}" />
														<input type="hidden" name="documentItemType" value="${item.documentItemType}" />
														<input type="hidden" name="isFromToItem" value="${item.isFromToItem}" />
														<input type="hidden" name="numberFormat" value="${item.numberFormat}" />
														<input type="hidden" name="maxLength" value="${item.maxLength}" />
														<input type="hidden" name="isListItem" value="" />
														<input type="hidden" name="isSearchItem" value="" />
														<input type="hidden" name="matching" value="" />
														<input type="hidden" name="default" value="" />
													</div>
												</c:forEach>
											</div>
										</td>
										<td class="col-xs-3 text-left" colspan='2' id="td_sort">
											<div id="sort2" class="connectedSortable">
												<c:forEach items="${leftItems}" var="item">
													<div class="sort-item" style="border:1px solid #ddd;cursor:move">
														<label style="padding-left: 25px;">${item.documentItemName }</label>
														<span style='cursor: pointer;' class='removeLine'  onclick="if ($(this).closest('td').attr('id') != 'blanktd') {$(this).closest('div').remove();}">x</span>
														<input type="hidden" name="documentItemCode" value="${item.documentItemCode}" />
														<input type="hidden" name="documentItemNo" value="${item.documentItemNo}" />
														<input type="hidden" name="documentItemType" value="${item.documentItemType}" />
														<input type="hidden" name="isFromToItem" value="${item.isFromToItem}" />
														<input type="hidden" name="numberFormat" value="${item.numberFormat}" />
														<input type="hidden" name="maxLength" value="${item.maxLength}" />
														<input type="hidden" name="isListItem" value="${item.isListItem}" />
														<input type="hidden" name="isSearchItem" value="${item.isSearchItem}" />
														<input type="hidden" name="matching" value="${item.matching}" />
														<input type="hidden" name="default" value="${item.defaultItemValue}" />
														<span style="float: right; padding-right: 25px"> <a name="docFormDetail" class='a_sort'
															href-void>详细</a>
														</span>
													</div>
												</c:forEach>
											</div>
										</td>
										<td class="col-xs-3 text-left" colspan='2'>
											<div id="sort3" class="connectedSortable">
												<c:forEach items="${rightItems}" var="item">
													<div class="sort-item" style="border:1px solid #ddd;cursor:move">
														<label style="padding-left: 25px;">${item.documentItemName }</label>
														<span style='cursor: pointer;' class='removeLine' onclick="if ($(this).closest('td').attr('id') != 'blanktd') {$(this).closest('div').remove();}">x</span>
														<input type="hidden" name="documentItemCode" value="${item.documentItemCode}" />
														<input type="hidden" name="documentItemNo" value="${item.documentItemNo}" />
														<input type="hidden" name="documentItemType" value="${item.documentItemType}" />
														<input type="hidden" name="isFromToItem" value="${item.isFromToItem}" />
														<input type="hidden" name="numberFormat" value="${item.numberFormat}" />
														<input type="hidden" name="maxLength" value="${item.maxLength}" />
														<input type="hidden" name="isListItem" value="${item.isListItem}" />
														<input type="hidden" name="isSearchItem" value="${item.isSearchItem}" />
														<input type="hidden" name="matching" value="${item.matching}" />
														<input type="hidden" name="default" value="${item.defaultItemValue}" />
														<span style="float: right; padding-right: 25px"> <a class="a_sort"  name="docFormDetail1" class='a_sort' href-void>详细</a>
														</span>
													</div>
												</c:forEach>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- 查询结果end -->
						<!-- 底部按钮start -->
						<div class="buttongroup">
							<input type="button" class='btn btn-primary ' name="define_form_submit1"
								id="define_form_submit" value="保存" style="margin-right:0px"></input> <input type="button" name="cancel"
								class="btn" value="返回" id="ret" />
						</div>
						<!-- 底部按钮end -->
					</form>
				</fieldset>
<!-- 悬浮提示框 -->
		<div id="tip" style="width:auto;  display:inline;display:none;margin:10px 0;padding:10px;border:1px solid #696969;background-color:	#FFF68F;" >
			<lable id="tipName"></lable>
			<br>
		</div>

			<!-- 详细设置popup start -->
				<div class="modal fade" id="detailSetting" tabindex="-1" role="dialog"  data-backdrop="static" data-keyboard="false"
					aria-hidden="true">
					<form class="modal-default-form">
					<div class="modal-dialog ">
						<div class="modal-content">
							<div class="modal-body" >
							<!-- 条件设置start -->
							<div class="row">
								<div class="col col-sm-4 col-lg-4 col-xs-12"
									style="padding-left: 25px">
									<span>数据项</span>
								</div>

								<div class=" col col-sm-8 col-lg-8 col-xs-12">
									<input type="text" id="modal_itemName" disabled />
								</div>
							</div>

							<div class="row">
								<div class="col col-sm-4 col-lg-4 col-xs-12"
									style="padding-left: 25px">
									<span>一览项目</span>
								</div>

								<div class=" col col-sm-8 col-lg-8 col-xs-8">
									<input type="checkbox" id="modal_list" />
								</div>
							</div>

							<div class="row">
								<div class="col col-sm-4 col-lg-4 col-xs-12"
									style="padding-left: 25px">
									<span>查询项目</span>
								</div>

								<div class=" col col-sm-8 col-lg-8 col-xs-8">
									<input type="checkbox" id="modal_search" />
								</div>
							</div>

							<div class="row">
								<div class="col col-sm-4 col-lg-4 col-xs-12"
									style="padding-left: 25px">
									<span>匹配方式</span>
								</div>

								<div class=" col col-sm-8 col-lg-8 col-xs-8">
									<select id="modal_matching">
									<c:forEach items="${params }" var="item" varStatus="status">
									<option value="${item.value }" index="${status.index }">${item.dispName }</option>
									</c:forEach>
									</select>
								</div>
							</div>

							<div class="row" id="defaultRow">
								<div class="col col-sm-4 col-lg-4 col-xs-12"
									style="padding-left: 25px">
									<span>默认值</span>
								</div>

								<!-- 默认值显示start -->
								<div id="item-type" class="col col-sm-8 col-md-8 col-lg-8 col-xs-8" >
									<!-- 文本 -->

									<input class="item-type-text item-type-text-from" name="item-type-text-from"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									type="text" />

									<!-- 多行文本 -->

									<textarea class="item-type-multiline item-type-multiline-from" name="item-type-multiline-from"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									style="resize: none;display:none"></textarea>

									<!-- 整型 -->

									<input class="item-type-integer item-type-integer-from col-md-4"
									data-rule-integer="true" data-msg-integer="必须为整型"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									type="text" name="item-type-integer-from" style="display:none"/>

									<span class="item-type-integer-span col-md-1" style="display:none">To</span>

									<input class="item-type-integer item-type-integer-to col-md-4"
									data-rule-integer="true" data-msg-integer="必须为整型"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									data-rule-fromTo="true" data-msg-fromTo="To项目必须大于From项目"
									type="text" name="item-type-integer-to" style="display:none"/>

									<!-- 金额 -->

									<input class="item-type-currency item-type-currency-from col-md-4"
									data-rule-currency="true" data-msg-currency="必须为金额类型"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									type="text" name="item-type-currency-from" style="display:none"/>

									<span class="item-type-currency-span col-md-1" style="display:none">To</span>

									<input class="item-type-currency item-type-currency-to col-md-4"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									data-rule-currency="true" data-msg-currency="必须为金额类型"
									data-rule-fromTo="true" data-msg-fromTo="To项目必须大于From项目"
									type="text" name="item-type-currency-to" style="display:none"/>

									<!-- 小数 -->

									<input class="item-type-decimal item-type-decimal-from col-md-4"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									data-rule-decimal="true" data-msg-decimal="必须为小数类型"
									type="text" name="item-type-decimal-from" style="display:none"
									onblur="fmoney(this)"
									/>

									<span class="item-type-decimal-span col-md-1" style="display:none">To</span>
									<input class="item-type-decimal item-type-decimal-to col-md-4"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									data-rule-decimal="true" data-msg-decimal="必须为小数类型"
									data-rule-fromTo="true" data-msg-fromTo="To项目必须大于From项目"
									type="text" name="item-type-decimal-to" style="display:none"
									onblur="fmoney(this)"
									/>
									<!-- 日期 -->
									<input class="item-type-date item-type-date-from col-md-4"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									data-rule-date="true" data-msg-date="必须为日期类型"
									type="text" name="item-type-date-from" style="display:none" placeholder="年/月/日"/>

									<span class="item-type-date-span col-md-1" style="display:none">To</span>

									<input class="item-type-date item-type-date-to col-md-4"
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度"
									data-rule-date="true" data-msg-date="必须为日期类型"
									data-rule-fromTo="true" data-msg-fromTo="To项目必须大于From项目"
									type="text" name="item-type-date-to" style="display:none" placeholder="年/月/日"/>

									<!-- 单选 -->
									<select class="item-type-select-from col-md-4" style="display:none"></select>
									<!-- 下拉，复选 -->
									<select class="item-type-selectMul-from col-md-4" multiple style="display:none"></select>
									<!-- poPup -->
									<span class="item-type-popup-from col-md-8 col-lg-8 col-sm-8 no-padding" style="display:none">
									<div class="col-md-10 no-padding">
									<input type="text" class="item-type-popup-from-value" value="" readOnly style="width:100%"/>
									<input type="hidden" class="item-type-popup-from-default" value=""
									data-rule-maxlength="200" data-msg-maxlength="不得超过最大长度" /></div>
									<div><i class="fa fa-search"></i></div></span>

								</div>
								<div class="col-md-8 col-lg-8 col-sm-8 col-md-offset-4 col-lg-offset-4 col-sm-offset-4" id="modalDefault">
									<input type="hidden" />
									</div>
								<!-- 默认值显示end -->


						</div>
							<!-- 按钮组start -->
							<div class="buttongroup">
								<div class="row text-center">
									<button type="submit" id="docDefaultConfirm" class="btn btn-primary">
										确认
									</button>
									<button type="button" id="docDefaultClose" class="btn default"  data-dismiss="modal">
										关闭
									</button>
								</div>
							</div>
							<!-- 按钮组end -->
							</div>
						</div>
					</div>
					</form>
				</div>
		</div>
	</div>
</div>

<script src="js/module/docmain/docFormDefault.js"></script>
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
<script>
	$(function() {

		var docFormDefaultJS = new Globals.docFormDefaultJS();
		docFormDefaultJS.InitPage();
	});
</script>

<!-- 人员检索 PopUp -->
<jsp:include page="../com/StaffSearchPopup.jsp" />
<%@include file="../include/inc_footer.jsp"%>
