/**
 * -------------------------------------------------------
 *
 * @模块描述:常量定义
 * @作者:邵林军
 * @修改履历:新作成 2017/12/17 -------------------------------------------------------
 *           调用方法 var ConstText = new Globals.constJS();(此定义已包含在inc_header.jsp中)
 *           alert(ConstText.ITEM_TYPE_TEXT())
 */

Namespace.register("Globals.constJS");

Globals.constJS = (function() {
	var _this = null;
	// ========================================================
	// 消息常量定义区域
	var Const = {
		// --------------数据项类型 ----------
		ITEM_TYPE_TEXT : 1,// 文本
		ITEM_TYPE_AREA : 2,// 多行文本
		ITEM_TYPE_INT : 3,// 整型
		ITEM_TYPE_MONEY : 4,// 金额
		ITEM_TYPE_FLOAT : 5,// 小数
		ITEM_TYPE_DATE : 6,// 日期
		ITEM_TYPE_SELECT : 7,// 下拉列表
		ITEM_TYPE_CHECKBOX : 8,// 复选框
		ITEM_TYPE_RADIO : 9,// 单选框
		ITEM_TYPE_FILE_SINGLE : 10,// 单文件上传
		ITEM_TYPE_FILE_MULT : 11,// 多文件上传
		ITEM_TYPE_POP_USER : 12,// 人员检索Popup
		ITEM_TYPE_POP_DEPT : 13,// 部门检索Popup
		ITEM_TYPE_POP_USER_MULT : 14,// 人员检索Popup(多选）
		ITEM_TYPE_POP_DEPT_MULT : 15,// 部门检索Popup(多选）
		ITEM_TYPE_POP_STAFF_MULT : '#',// 人员#常量
		// ------------------------------------

		// -------------------固定项-----------------------
		DOCUMENT_ITEM_F00000 : "DOCUMENT_ITEM_F00000",
		DOCUMENT_ITEM_F00001 : "DOCUMENT_ITEM_F00001",
		DOCUMENT_ITEM_F00002 : "DOCUMENT_ITEM_F00002",
		DOCUMENT_ITEM_F00003 : "DOCUMENT_ITEM_F00003",
		DOCUMENT_ITEM_F00004 : "DOCUMENT_ITEM_F00004",
		DOCUMENT_ITEM_F00005 : "DOCUMENT_ITEM_F00005",
		DOCUMENT_ITEM_F00006 : "DOCUMENT_ITEM_F00006",
		DOCUMENT_ITEM_F00007 : "DOCUMENT_ITEM_F00007",
		DOCUMENT_ITEM_F00008 : "DOCUMENT_ITEM_F00008",
		DOCUMENT_ITEM_F00009 : "DOCUMENT_ITEM_F00009",
		DOCUMENT_ITEM_F000010 : "DOCUMENT_ITEM_F00010",
		DOCUMENT_ITEM_F000012 : "DOCUMENT_ITEM_F00012",
		// ------------------------------------------
		// --------------------匹配方式--------------------
		MATCHING_TYPE_EQ : 1,// =
		MATCHING_TYPE_LIKE : 2,// LIKE
		MATCHING_TYPE_GE : 3,// >=
		MATCHING_TYPE_LE : 4,// <=
		MATCHING_TYPE_IN : 5,// IN
		// -------------------------------------------------

		// ----------------------------文档状态------------------
		DOCUMENT_STATUS_01 : 1,// 做成中
		DOCUMENT_STATUS_02 : 2,// 审核中
		DOCUMENT_STATUS_03 : 3,// 已完成
		// -----------------------------------------------------

		// ------------------------------审核结果状态------------------
		APPROVE_RESULT_TYPE_1 : 1,// 通过
		APPROVE_RESULT_TYPE_2 : 2,// 退回
		// ---------------------------------------------------------

		// ------------------------------设备预约模式 ------------------
		RESERVE_PATTERN_ADD : 1,// 新增
		RESERVE_PATTERN_UPDATE : 2,// 修改
		RESERVE_PATTERN_DELETE : 3,// 删除
		// ----------------------------------------------------------

		// -------------------前端Msg ------------------
		HTML_MSG_NUM_ZERO : 0,// 0
		HTML_MSG_NUM_NEGAONE : -1,// -1
		HTML_MSG_NUM_ONE : 1,// 1
		HTML_MSG_NUM_TWO : 2,// 2
		HTML_MSG_NUM_FIRE : 5,// 5
		HTML_MSG_NUM_FIRETY : 50,// 50
		HTML_MSG_NUM_TER : 12,// 12
		HTML_MSG_NUM_OHB : 100,// 100
		HTML_MSG_NUM_THB : 200,// 200
		HTML_MSG_NUM_FHB : 300,// 300
		HTML_MSG_STR_YTN : "ytn",// 字符ytn
		HTML_MSG_STR_NTY : "nty",// 字符nty
		HTML_MSG_STR_NOT : "nothing",// 字符nothing
		HTML_MSG_NUM_MS : 2000,// 毫秒2000
		HTML_MSG_LENGTH_ITEM : 20,// 文档数据项长度
		HTML_MSG_MAXLENGTH_OSC : 1000,// 最大长度1000
		HTML_MSG_MAXLENGTH_FOR : 4,// 最大长度4
		HTML_MSG_MAXLENGTH_TTY : 20,// 最大长度20
		HTML_MSG_MAXLENGTH_TEN : 10,// 最大长度10
		HTML_MSG_STR_HIDDEN : "隐藏",// 隐藏
		HTML_MSG_STR_DISPLAY : "显示",// 显示
		HTML_MSG_STR_DECIAML : "小数",// 小数
		HTML_MSG_STR_XIALA : "下拉列表",// 下拉列表
		HTML_MSG_STR_FUXUAN : "复选框",// 复选框
		HTML_MSG_STR_DANXUAN : "单选框",// 单选框
		// ----------------------------------------------------------

		// 设备预约标题最大长度（数据库中的限制）
		RESERVE_TITLE_MAX_LENGTH : 200,
		// 设备预约使用目的最大长度（数据库中的限制）
		RESERVE_USE_GOAL_MAX_LENGTH : 500,

		// 默认值db长度
		DEFAULT_VALUE_LENGTH : "200",
		// 设备预约颜色
		RESERVE_COLOR : "#8fff00",

		// --------- 返回按钮返回类型
		RET_TYPE_INDEX : '0',
		BACK_TO_BATCH_CHECK : '1',
		BACK_TO_DOC_SEARCH : '2',
		// ---------------------------

		// cookie key
		PROJECT_PAGE_LENGTH : "projectPageLength",
		DEVICE_PAGE_LENGTH : "devicePageLength",
		ITEM_PAGE_LENGTH : "itemPageLength",
		ITEMSRC_PAGE_LENGTH : "itemsrcPageLength",
		STAFF_PAGE_LENGTH : "staffPageLength",
		LOG_PAGE_LENGTH : "logPageLength",
		USE_PAGE_LENGTH : "usePageLength",
		DOCBATCH_PAGE_LENGTH : "docBatchPageLength",
		DOCSEARCH_PAGE_LENGTH : "docSearchPageLength"
	};

	// ========================================================

	for ( var i in Const) {// 下面的\'不要动，不要在''和里面的字符之间产生空格，很重要！！！ （"
		// #8fff00"这样的input不认识）
		eval('function _' + i + '(){return \'' + Const[i] + '\'} ')
	}

	function constructor() {
		_this = this;
		for ( var i in Const) {
			eval('this.' + i + ' = _' + i);
		}
	}

	return constructor;
})();
