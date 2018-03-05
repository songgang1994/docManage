/* 系统名：NSK-NRDC业务系统
 作成人：张丽
 模块名：实验设备工时录入 js*/

Namespace.register("Globals.deviceHourEnterJS");

Globals.deviceHourEnterJS = (function() {
	var _this = null;
	var deviceNew = {};
	var update =null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	function _init(){

         //按钮点击事件
		$("#confirm").bind("click", function(){_insert();});
	 	$("#deletel").bind("click", function(){_deletel();});
	 	$("#updatel").bind("click", function(){_updatel();});

	 	$("#startDt").click(function(){
	 		$("#endDt-error").remove();
	 	})

		//初始化表单验证
		_initformvaild();

		//时间日历初始化
		_initTime();

		//定义id名
		var typeId = ("#useType")
		//获取隐藏框值，进行监听
        var use_Type = $("#use_Type");
		//调用共通ajax
	    Globals.utilJS.GetUserTypeList(use_Type,typeId)
	}

	function _ShowModal(parttern,dat,device,callback) {
		deviceNew = device;
		if(parttern == ConstText.HTML_MSG_NUM_ONE()){
			//新增模式
			var all = ""
			for(var i = 0;i<dat.listData.length;i++){
				all+="<option value="+dat.listData[i].deviceNo+">" +
						dat.listData[i].deviceName+
						"</option>"
			}
			$("#deletel").hide();
			$('.deviceNo').append(all);
		}else if(parttern == ConstText.RESERVE_PATTERN_UPDATE()){
			var useNo = deviceNew.useNo;
			//修改模式
			var more = ""
			for(var i = 0;i<dat.listData.length;i++){
				more+="<option value="+dat.listData[i].deviceNo+">" +
						dat.listData[i].deviceName+
						"</option>"
			}
			$(".deviceNo").append(more);
			//ajax请求数据渲染至画面
			var ajaxOptions = {
					type: "post",
					url: "device/hourupdate",
					data:{useNo:useNo},
					success : function(data) {
						//将值渲染至画面
						$("#userName").val(data.userName);
						$("#userId").val(data.userId);
						$(".deviceNo").find("option[value="+data.deviceNo+"]").attr("selected",true);
						$("#useType").find("option[value="+data.useType+"]").attr("selected",true);
						$("#startDt").val(data.startDt);
						$("#endDt").val(data.endDt);
						$("#commentInfo").text(data.commentInfo);
					}
			};
			var comAjax = new Globals.comAjaxJS();
			comAjax.sendAjax(ajaxOptions);
			$("#useNo").val(deviceNew.useNo)
			//新增按钮隐藏
			$("#confirm").hide();
			//修改按钮显示
			$("#updatel").show();
		}else{
			//显示警告
			return;
		}
		//显示模态框
		$("#hourEnterPopup").modal('show');
	}
/**********************************工时录入表单验证******************************/

	//判断结束时间是否大于开始时间的验证规则
	jQuery.validator.methods.compareDate = function(value, element, param) {
		var startDate = new Date(Date.parse(jQuery(param).val().replace('年','-').replace('月','-')));
		var endDate = new Date(Date.parse(value.replace('年','-').replace('月','-')));
		return	 startDate < endDate
	};
	//判断开始时间是否大于系统时间的验证规则
	jQuery.validator.methods.startDtDate = function(value, element, param) {
		var day = new Date();
		var startDt = new Date(Date.parse(value.replace('年','-').replace('月','-')));
		return startDt < day;
	};
	//判断结束时间是否大于系统时间的验证规则
	jQuery.validator.methods.endDtDate = function(value, element, param) {
		var day = new Date();
		var endDate = new Date(Date.parse(value.replace('年','-').replace('月','-')));
		return	 endDate < day
	};
	function _initformvaild(){
		$('#hour-form').validate({
    	    errorElement: 'span', //default input error message container
    	    errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
    	    rules:{
    	    	deviceNo:{
		            required: true,
	        	},
	        	useType:{
	        		required: true
	        	},
	        	userName:{
	        		required: true
	        	},
	        	startDt:{
	        		required: true,
	        		startDtDate:true,
	        	},
	        	endDt:{
	        		required: true,
	        		compareDate: "#startDt",
	        		endDtDate:true,
	        	},
	        	commentInfo:{
	        		required: true
	        	},
    	    },
        messages:{
        	deviceNo:{
	   		 	required: ConstMsg.HTML_MSG_MUSTCHOOSE_DEVICE(),
    		},
    		useType:{
    			required: ConstMsg.HTML_MSG_MUSTCHOOSE_DEVICETYPE(),
    		},
    		userName:{
    			required: ConstMsg.HTML_MSG_CHOOSE_CHOOSEMAN(),
        	},
    		startDt:{
    			required: ConstMsg.HTML_MSG_MUSTCHOOSE_STARTTIME(),
    			startDtDate:ConstMsg.HTML_MSG_MUSTLESS_STARTTIME()
    		},
    		endDt:{
    			required: ConstMsg.HTML_MSG_MUSTCHOOSE_ENDTIME(),
    			remote:ConstMsg.HTML_MSG_GAGINCHOOSE_USEREPEAT(),
    			compareDate:ConstMsg.HTML_FRM_PERIOD_START_GREATER_END(),//"期间结束时间必须大于期间开始时间"
    			endDtDate:ConstMsg.HTML_MSG_MUSTLESS_ENDTIME()
    		},
          	commentInfo:{
          		required: ConstMsg.HTML_MSG_MUSTCHOOSE_DEVICEREMARKS(),
          	},

        },
        invalidHandler: function (event, validator)
        { //display error alert on form submit
        },

        highlight: function (element)
        { // hightlight error inputs
            $(element)
                .closest('.form-group').addClass('has-error'); // set error class to the control group
        },

        success: function (label)
        {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        }
    	});
	}
/**********************************工时录入表单验证结束******************************/
/**********************************时间日历初始化***********************************/
	function _initTime(){
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
			$("#startDt").datetimepicker({
				changeMonth: true,
				dateFormat: "yy/mm/dd",
			});
			$("#endDt").datetimepicker({
				changeMonth: true,
				dateFormat: "yy/mm/dd",
			});
	}

/**********************************时间日历初始化结束******************************/
/**********************************按钮点击事件******************************/

	//选择人员点击事件
	$("#select").click(function(){
		var project = new Globals.staffSearchPopupJS();
        project.ShowModal('radio',"",function(data){
        		$("#userId").val(data[0][0].userId);
        		$("#userName").val(data[0][0].userName);
        		//清除表单验证
				$("#userName").closest('.form-group').removeClass('has-error');
				$("#userName-error").remove();
        })
	})

	//关闭按钮跳转上一个画面
	$("#close").bind("click",function(){
		 var closecancel = ConstMsg.CONFIRM_BACK();
            Globals.msgboxJS.Confirm(closecancel,function(){
            	if(deviceNew.flug == 1){
            		_cancelUse();
            	}else{
            		_cancelOnce();
            	}
            },function(){
              return;
           })
	});

	//点击新增按钮
	function _insert(){
		if($("#hour-form").valid() == false){
			return;
		}
		var flag = true;
		var  ajaxOptionsss = {
				async:false,
	            url:"device/hourGet",
	            type:"post",
	            data:JSON.stringify($('#hour-form').serializeJSON()),
	            contentType: "application/json",
	            success:function(data){
	            	if(data == false){
	            		Globals.msgboxJS.ToastrWarning(ConstMsg.HTML_MSG_NOTGAGIN_USE())
	            		 flag = false;
	            	}
	            }
	       };
		 var comAjax = new Globals.comAjaxJS();
		 comAjax.sendAjax(ajaxOptionsss);
		//新增按钮提交表单
		if(flag == true){
			var  ajaxOptions = {
					async:false,
		            url:"device/hourEnter",
		            type:"post",
		            data:JSON.stringify($('#hour-form').serializeJSON()),
		            contentType: "application/json",
		            success:function(data){
		            	_entersuccess(data);
		            }
		      };
			  var comAjax = new Globals.comAjaxJS();
			  comAjax.sendAjax(ajaxOptions);
		}
	}

	//点击删除按钮
	function _deletel(){
		//获取设备名称
		var deviceName = $(".deviceNo option:selected").text();
		var del = ConstMsg.BIZ_CODE_SHUR_DELETE()+deviceName+"?";
		Globals.msgboxJS.Confirm(del,function(){
			//删除信息
			var useNo = $("#useNo").val();
			var  ajaxOption = {
						async:false,
			            url:"device/hourDelete",
			            type:"GET",
			            data: "useNo="+useNo,
			            contentType: "application/json",
			            success:function(data){
			            	_deletesuccess(data);
			            }
			 };
			 var comAjax = new Globals.comAjaxJS();
			 comAjax.sendAjax(ajaxOption);
		},function(){
            return;
        })
	}

	//点击修改按钮
	function _updatel(){
		if($("#hour-form").valid() == false){
			return;
		}
	//定义flg用来判断是否为修改方式
    var flagg  = true ;
	var  ajaxOptiona = {
			async:false,
            url:"device/hourGet",
            type:"post",
            data:JSON.stringify($('#hour-form').serializeJSON()),
            contentType: "application/json",
            success:function(data){
            	if(data == false){
            		Globals.msgboxJS.ToastrWarning(ConstMsg.HTML_MSG_NOTGAGIN_USE())
            		 flagg = false;
            	}
            }
	};
	var comAjax = new Globals.comAjaxJS();
	comAjax.sendAjax(ajaxOptiona);

	//修改按钮提交表单
	if(flagg == true){
		var  ajaxOptionss = {
				async:false,
	            url:"device/hourUpdate",
	            type:"post",
	            data:JSON.stringify($('#hour-form').serializeJSON()),
	            contentType: "application/json",
	            success:function(data){
	            	_updatesuccess(data);
	            }
	     };
		 var comAjax = new Globals.comAjaxJS();
		 comAjax.sendAjax(ajaxOptionss);
		}
	}

/**********************************按钮点击事件结束******************************/
/**********************************Msg区域***************************************/
	//新增msg
	function _entersuccess(json) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
			    //成功时直接跳转页面
				case ConstCode.BIZ_CODE_INSERT_SUCCESS() :
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_INSERT_SUCCESS())
					if(deviceNew.flug == 1){
						_cancelDelay();
	            	}else{
	            		_cancel();
	            	}
					break;
				case 	ConstCode.BIZ_CODE_HANDLE_FAILED():
					Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_HANDLE_FAILED())
					break;
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}
	//删除msg
	function _deletesuccess(json) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
			    //删除成功，报msg
				case ConstCode.BIZ_CODE_DELETE_SUCCESS() :
				    Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DELETE_SUCCESS())
				    _cancelDelay();
					break;
				//参数错误，修改失败
				case 	ConstCode.BIZ_CODE_INVALID_PARAMS():
					Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_INVALID_PARAMS())
					break;
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}

	//修改msg
	function _updatesuccess(json) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
			    //修改成功，报msg
				case ConstCode.BIZ_CODE_UPDATE_SUCCESS() :
				    Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS())
				    _cancelDelay();
					break;
				//参数错误，修改失败
				case 	ConstCode.BIZ_CODE_INVALID_PARAMS():
					Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_INVALID_PARAMS())
					break;
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}
	//返回函数(延时2秒)
	function _cancel(){
		var deviceNo1 = deviceNew.deviceNo1;
    	var deviceName = deviceNew.deviceName;
    	var locationId =deviceNew.locationId;
    	var location = deviceNew.location;
    	var flug = 1;
    	timeID=setTimeout(function(){
			clearTimeout(timeID);
    	window.location.href=path+"/device/list?deviceNo="+deviceNo1+"&deviceName="+encodeURI(encodeURI(deviceName))+"&locationId="+locationId+"&location="+encodeURI(encodeURI(location))+"&flug="+flug;
    	},ConstText.HTML_MSG_NUM_MS());
   }

	//返回函数跳转设备使用一览
	function _cancelUse(){
		var deviceNo1 = deviceNew.deviceNo;
    	var deviceName = deviceNew.deviceName;
    	var locationId =deviceNew.locationId;
    	var location = deviceNew.location;
    	var useDt = deviceNew.useDt;
    	var flug = 1;
    	window.location.href=path+"/device/uselist?deviceNo="+deviceNo1+"&deviceName="+encodeURI(encodeURI(deviceName))+"&locationId="+locationId+"&location="+encodeURI(encodeURI(location))+"&flug="+flug+"&useDt="+useDt;
	}

	//返回函数跳转设备使用一览(添加后跳转)
	function _cancelDelay(){
		var deviceNo1 = deviceNew.deviceNo;
    	var deviceName = deviceNew.deviceName;
    	var locationId =deviceNew.locationId;
    	var location = deviceNew.location;
    	var useDt = deviceNew.useDt;
    	var flug = 1;
    	timeID=setTimeout(function(){
			clearTimeout(timeID);
			window.location.href=path+"/device/uselist?deviceNo="+deviceNo1+"&deviceName="+encodeURI(encodeURI(deviceName))+"&locationId="+locationId+"&location="+encodeURI(encodeURI(location))+"&flug="+flug+"&useDt="+useDt;
    	},ConstText.HTML_MSG_NUM_MS());
	}

	//返回函数(立刻返回)
	function _cancelOnce(){
		var deviceNo1 = deviceNew.deviceNo1;
    	var deviceName = deviceNew.deviceName;
    	var locationId =deviceNew.locationId;
    	var location = deviceNew.location;
    	var flug = 1;
    	window.location.href=path+"/device/list?deviceNo="+deviceNo1+"&deviceName="+encodeURI(encodeURI(deviceName))+"&locationId="+locationId+"&location="+encodeURI(encodeURI(location))+"&flug="+flug;
	}

	function GetConst(BizCode) {
		var bizCode= new Globals.constJS();
		return bizCode.GetCode(BizCode);
	}
/**********************************Msg区域结束***************************************/

	// 构造方法
	function constructor() {
		_this = this;
        this.init= _init;
        this.ShowModal=_ShowModal;
	}
	return constructor;
})();

//继承公共AJAX类
Globals.deviceHourEnterJS.prototype = new Globals.comAjaxJS();