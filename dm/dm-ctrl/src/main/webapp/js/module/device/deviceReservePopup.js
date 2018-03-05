/*
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：邵林军 --%>
 * <%-- 模块名：设备预约POP --%>
 */
Namespace.register("Globals.deviceReservePopupJS");

Globals.deviceReservePopupJS = (function() {
	var _this = null;
	//父页面函数，在点击确认或删除时执行
	var parentPageFunction = function(){};

	//======================== 初始化 设备预约Popup ====================
	function _initDeviceReservePopup() {
		//初始化时间选择器
		_InitDatetimepicker();

		//初始化 表单验证
		_InitFormValid();

		//按键监听
		_ButtonClickListener();

		//定义关闭模态框时执行的操作
		_InitHide();

		//初始化颜色选择按钮
		_InitColor();
	}
	//======================== 初始化 设备预约Popup结束 ====================

	//======================== 初始化时间选择器 ===========================
	function _InitDatetimepicker(){
		// 时间选择器
		$.datepicker.regional['zh-CN'] = {
			changeMonth: true,
			changeYear: true,
			clearText: '清除',
			clearStatus: '清除已选日期',
			closeText: '关闭',
			closeStatus: '不改变当前选择',
			prevText: '<上月',
			prevStatus: '显示上月',
			prevBigText: '<<',
			prevBigStatus: '显示上一年',
			nextText: '下月>',
			nextStatus: '显示下月',
			nextBigText: '>>',
			nextBigStatus: '显示下一年',
			currentText: '今天',
			currentStatus: '显示本月',
			monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
			monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
			monthStatus: '选择月份',
			yearStatus: '选择年份',
			weekHeader: '周',
			weekStatus: '年内周次',
			dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
			dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
			dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
			dayStatus: '设置 DD 为一周起始',
			dateStatus: '选择 m月 d日, DD',
			dateFormat: 'yy-mm-dd',
			firstDay: 1,
			initStatus: '请选择日期',
			isRTL: false
		};
		$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
		//开始时间
		$("#deviceReservePopup #startDt").datetimepicker({
			closeText:"确认",
			changeMonth: true,
			dateFormat: "yy/mm/dd",
		});
		//结束时间
		$("#deviceReservePopup #endDt").datetimepicker({
			closeText:"确认",
			changeMonth: true,
			dateFormat: "yy/mm/dd",
		});
		// 选择时间 结束
	}
	//========================= 初始化时间选择器结束  ===========================

	//========================= 初始化表单验证  ===============================
	function _InitFormValid(){
		//判断结束时间是否大于系统时间的验证规则
		jQuery.validator.addMethod("sysDateTimeLater", function(value, element) {
			var time = new Date(value);
			var systime =  new Date();
		    return this.optional(element) || (systime<time);
		});

		//判断结束时间是否大于开始时间的验证规则
		jQuery.validator.methods.compareDate = function(value, element, param) {
			var startDate = jQuery(param).val();
	        var date1 = new Date(Date.parse(startDate.replace("-", "/")));
	        var date2 = new Date(Date.parse(value.replace("-", "/")));
	        return date1 < date2;
	    };

		//表单提交验证
		$("#deviceReservePopupForm").validate({
			errorClass: 'help-block',
    	    focusInvalid: false,
    	    onfocusout:false,//关闭失去焦点时验证
    	    onkeyup:false,//关闭在 keyup 时验证。
    	    onclick:false,//关闭在点击复选框和单选按钮时验证。
			errorElement: "span",
			rules: {
				title: {
					required: true,
					maxlength:ConstText.RESERVE_TITLE_MAX_LENGTH(),//200
				},
				useGoal: {
					required: true,
					maxlength: ConstText.RESERVE_USE_GOAL_MAX_LENGTH(),//500
				},
				startDt: {
					required: true
				},
				endDt: {
					required: true,
					compareDate:"#deviceReservePopup #startDt",
					sysDateTimeLater:true
				}
		    },
			messages: {
				title: {
					required: ConstMsg.HTML_FRM_INPUT_TITLE(),	//"请输入标题"
					maxlength:$.validator.format(ConstMsg.HTML_FRM_TITLE_MAX()),//"标题最长输入{0}个字符"
				},
				useGoal: {
					required: ConstMsg.HTML_FRM_INPUT_USE_GOAL(),//"请输入使用目的"
					maxlength:$.validator.format(ConstMsg.HTML_FRM_USE_GOAL_MAX()),//"使用目的最长输入{0}个字符"
				},
				startDt: {
					required: ConstMsg.HTML_FRM_INPUT_START_TIEM()//"请输入开始时间"
				},
				endDt: {
					required: ConstMsg.HTML_FRM_INPUT_END_TIME(),//"请输入结束时间"
					compareDate:ConstMsg.HTML_FRM_PERIOD_START_GREATER_END(),//"期间结束时间必须大于期间开始时间"
					sysDateTimeLater:ConstMsg.HTML_FRM_PERIOD_END_GREATER_SYS()//"期间结束时间必须大于系统时间"
				}
			},
			errorPlacement: function(error, element) {
			    if(element.attr("name") == "startDt" ){
			    	error.addClass('has-error');
			    	error.insertAfter("#startDtErr");
			    }else if( element.attr("name") == "endDt"){
			    	error.addClass('has-error');
			    	error.insertAfter("#endDtErr");
			    } else {
			      error.insertAfter(element);
			    }
			},
			highlight: function (element){ // hightlight error inputs
	            $(element)
	                .closest('.form-group').addClass('has-error'); // set error class to the control group
	        },
	        success: function (label){
	            label.closest('.form-group').removeClass('has-error');
	            label.remove();
	        }
		});
	}
	//========================= 初始化表单验证结束  ===============================

	//按键监听
	function _ButtonClickListener(){
		//监听按钮按钮点击事件
		$("button").on("click",function(e){
			e.preventDefault();
			e.stopPropagation();

			switch(e.target.id){
				case "submitReserve"://确认按钮
					submitReserve();
					break;
				case "deleteReserve"://删除按钮
					Globals.msgboxJS.Confirm(ConstMsg.HTML_TXT_IS_DELETE_DEVICE_RESERVE(),function(){
						$("#deviceReservePopup #pattern").val(ConstText.RESERVE_PATTERN_DELETE());//3代表删除模式
						submitReserve();
	                },function(){return;})
					break;
				case "closeReserve"://关闭按钮
					var pattern = $("#deviceReservePopup #pattern").val();//判断操作模式
					var confirmMsg="";
					if(pattern == ConstText.RESERVE_PATTERN_ADD()){
						confirmMsg = ConstMsg.HTML_TXT_IS_GIVEUP_ADD_DEVICE_RESERVE();
					}else{
						confirmMsg = ConstMsg.HTML_TXT_IS_GIVEUP_UPDATE_DEVICE_RESERVE();
					}
	                Globals.msgboxJS.Confirm(confirmMsg,function(){
	                	_CloseModal()
	                },function(){return;})
					break;
				default:
					break;
			}
		});
	}

	//定义关闭模态框时执行的操作
	function _InitHide(){
		$("#deviceReservePopup").on("hide.bs.modal", function() {
			//重置验证
			$("#deviceReservePopupForm").validate().resetForm();
			//重置表单
			$('#deviceReservePopupForm')[0].reset();
			$("#deviceReservePopup #deviceNo").val("");
	    	$("#deviceReservePopup #reserveNo").val("");
	    	$("#deviceReservePopup #pattern").val(ConstText.RESERVE_PATTERN_ADD());//1代表新增模式
	    	$("#deviceReservePopup #deviceNoLab").html("");
	    	$("#deviceReservePopup #deviceNameLab").html("");
	    	$("#deviceReservePopup #title").val("");
	    	$("#deviceReservePopup #useGoal").val("");
	    	$("#deviceReservePopup #startDt").val("");
	    	$("#deviceReservePopup #endDt").val("");
	    	document.getElementById("color").value = ConstText.RESERVE_COLOR();
	    	$("#deviceReservePopup .simple_color_kitchen_sink").val($("#deviceReservePopup #color").val());
	    	$("#deviceReservePopup #deleteReserve").hide();
			//清除msg
//			Globals.msgboxJS.ToastrClear();
		})
	}

	//关闭模态框
	function _CloseModal(){
		//隐藏
		$("#deviceReservePopup").modal("hide");
	}

	//初始化颜色选择按钮
	function _InitColor(){
		$("#deviceReservePopup .simple_color_kitchen_sink").val(ConstText.RESERVE_COLOR());
		document.getElementById("color").value = ConstText.RESERVE_COLOR();
	}


	//========================= 前后台交互  ===============================
	// 提交
	function submitReserve(){
		//非删除模式下验证
		if($("#deviceReservePopup #pattern").val()!=ConstText.RESERVE_PATTERN_DELETE()){
			if($("#deviceReservePopupForm").valid() == false){
				return;
			}
		}
		//准备请求
		var ajaxOptions = {
			type: "post",
			url : 'device/reserve',// 地址
			contentType: "application/json",
			data :JSON.stringify($('#deviceReservePopupForm').serializeJSON()),
			success : function(data) {
				ajaxSuccess(data);
			}
		};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	}

	//ajax成功
	function ajaxSuccess(json) {
		if(json.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
			//显示msg
			switch(json.data.bizCode){
				case ConstCode.BIZ_CODE_SUCCESS():
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_SUCCESS());
					//关闭模态框
					_CloseModal();
					//执行父页面函数
					parentPageFunction();
					break;
				case ConstCode.BIZ_CODE_RESERVE_ADD_SUCCESS():
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_RESERVE_ADD_SUCCESS());
					_CloseModal();
					parentPageFunction();
					break;
				case ConstCode.BIZ_CODE_RESERVE_UPDATE_SUCCESS():
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_RESERVE_UPDATE_SUCCESS());
					_CloseModal();
					parentPageFunction();
					break;
				case ConstCode.BIZ_CODE_RESERVE_DELETE_SUCCESS():
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_RESERVE_DELETE_SUCCESS());
					_CloseModal();
					parentPageFunction();
					break;
				case ConstCode.BIZ_CODE_RESERVE_ADD_FAILED():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_RESERVE_ADD_FAILED());
					break;
				case ConstCode.BIZ_CODE_RESERVE_UPDATE_FAILED():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_RESERVE_UPDATE_FAILED());
					break;
				case ConstCode.BIZ_CODE_RESERVE_DELETE_FAILED():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_RESERVE_DELETE_FAILED());
					break;
				case ConstCode.BIZ_CODE_RESERVE_PATTERN_ERR():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_RESERVE_PATTERN_ERR());
					break;
				case ConstCode.BIZ_CODE_DURATION_ERR():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_DURATION_ERR());
					break;
			}
		}else{
			Globals.utilJS.Globals_API_ERR_MSG(json.rtnCode);
		}
	}
	//========================= 前后台交互结束 ===========================

	//========================= 对外方法 ===============================
	// 显示模态框
	function _ShowModal(parttern,data,callback) {
		$(".simpleColorContainer").remove();
		$('.simple_color_kitchen_sink').show();
		//设置pop画面参数
    	$("#deviceReservePopup #deviceNo").val(data.deviceNo);
    	$("#deviceReservePopup #deviceNoLab").html(data.deviceNo);
    	$("#deviceReservePopup #deviceNameLab").html(data.deviceName);//这个数据有问题
    	$("#deviceReservePopup #pattern").val(parttern);//1代表修改模式，2代表修改模式

		if(parttern == ConstText.RESERVE_PATTERN_ADD()){
			//新增模式
			$("#deviceReservePopup #reserveNo").val("");
		}else if(parttern == ConstText.RESERVE_PATTERN_UPDATE()){
			//修改模式
			$("#deviceReservePopup #reserveNo").val(data.reserveNo);
	    	$("#deviceReservePopup #title").val(data.title);
	    	$("#deviceReservePopup #useGoal").val(data.useGoal);
	    	$("#deviceReservePopup #endDt").val(data.endDt);
	    	$("#deviceReservePopup #startDt").val(data.startDt);
	    	$("#deviceReservePopup #color").val(data.color);
	    	$("#deviceReservePopup .simple_color_kitchen_sink").val(data.color);
	    	$('#deviceReservePopup #deleteReserve').show();
		}else{
			//显示警告
			return;
		}
		  $('.simple_color_kitchen_sink').simpleColor({
			    onSelect: function(hex, element) {
			    	$(element).css("background-color", '#'+hex);
			    	$("#deviceReservePopup #color").val('#'+hex);
			    }
			  });

		//保存父页面函数
		parentPageFunction = callback;
		//显示模态框
		$("#deviceReservePopup").modal('show');
	}

	//========================= 构造方法 ===================================
	function constructor() {
		_this = this;
		this.InitDeviceReservePopup = _initDeviceReservePopup;
		this.ShowModal = _ShowModal;
	}

	return constructor;

})();

//继承公共AJAX类
Globals.deviceReservePopupJS.prototype = new Globals.comAjaxJS();