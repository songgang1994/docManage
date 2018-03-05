/*系统名：NSK-NRDC业务系统
 作成人：张丽--%>
模块名：设备新增修改 js */
Namespace.register("Globals.deviceManageJS");

Globals.deviceManageJS = (function() {
	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	function _init(){

		//按钮点击事件
		$("#confirm").bind("click", function(){_insert();});
		$("#update").bind("click", function(){_update();});

		//初始化表单验证
		_initformvaild();

		//定义Id名
		var stateId = ("#eqstate")
		var locationId = ("#eqplace")
		//获取隐藏框值，进行监听
		var use_State = $("#eq_state");
		var eq_place = $("#eq_place");
		//调用共通ajax
		Globals.utilJS.GetUseStateList(use_State,stateId);
		Globals.utilJS.GetUsePlaceList(eq_place,locationId);
	}
/**********************************新增表单验证******************************/
	function _initformvaild(){
		$('#manage-form').validate({
    	    errorElement: 'span', //default input error message container
    	    errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
    	    rules:{
    	    	deviceNo:{
		            required: true,
	        	},
	        	deviceName:{
	        		required: true
	        	},
	        	deviceStatus:{
	        		required: true
	        	},
	        	deviceParm:{
	        		required: true
	        	},
	        	locationId:{
	        		required: true
	        	},
	        	commentInfo:{
	        		required: true
	        	},
    	    }, //验证表单是否为空
        messages:{
        	deviceNo:{
	   		 	required: ConstMsg.HTML_MSG_MUSTINPUT_DEVICENO(),
    		},
    		deviceName:{
    			required: ConstMsg.HTML_MSG_MUSTINPUT_DEVICENAME(),
    		},
    		deviceStatus:{
    			required: ConstMsg.HTML_MSG_MUSTINPUT_DEVICESTATE(),
    		},
    		deviceParm:{
    			required: ConstMsg.HTML_MSG_MUSTINPUT_DEVICEPMATER(),
       		},
       		locationId:{
       			required: ConstMsg.HTML_MSG_MUSTINPUT_DEVICEPOSITION(),
          	},
          	commentInfo:{
          		required: ConstMsg.HTML_MSG_MUSTINPUT_DEVICETYPE(),
          	},

        },
        invalidHandler: function (event, validator)
        { //display error alert on form submit
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
/**********************************新增表单验证结束******************************/

/**********************************按钮点击事件******************************/
	//返回,跳转上一个画面
	$("#cancel").bind("click",function(){
			var devicecancel = ConstMsg.CONFIRM_BACK();
            Globals.msgboxJS.Confirm(devicecancel,function(){
            	//调用返回函数
            	_cancelOnce();
            },function(){
              return;
           })
	});

	//点击新增按钮
	function _insert(){
		if($("#manage-form").valid() == false){
			return;
		}
		//定义flg用来判断是否继续新增
		var flg =true;
		//获取设备编号
		var eqnumber= $("#eqnumber").val()
		//查询设备编号是否重复
		var  ajaxOptionNo = {
					async:false,
		            url:"device/search",
		            type:"post",
		            data:{deviceNo:eqnumber},
		            success:function(data){
		            	if(data == false){
		            		Globals.msgboxJS.ToastrWarning(ConstMsg.DEVICE_EXIST())
		            		flg = false;
		            	}
		            }
		};//发送ajax请求
	    var comAjax = new Globals.comAjaxJS();
	    comAjax.sendAjax(ajaxOptionNo);

	    if(flg == true){
	    	//新增按钮提交表单
			var  ajaxOptions = {
						async:false,
			            url:"device/add",
			            type:"post",
			            data:JSON.stringify($('#manage-form').serializeJSON()),
			            contentType: "application/json",
			            success:function(data){
			            	//调用msg函数
			            	_addsuccess(data);
			            }
			};//发送ajax请求
		    var comAjax = new Globals.comAjaxJS();
		    comAjax.sendAjax(ajaxOptions);
	    }
	}

	//点击修改按钮
	function _update(){
		if($("#manage-form").valid() == false){
			return;
		}
    	//修改按钮提交表单
		var  ajaxOption = {
				async:false,
	            url:"device/update",
	            type:"post",
	            data:JSON.stringify($('#manage-form').serializeJSON()),
	            contentType: "application/json",
	            success:function(data){
	            	_updatesuccess(data);
	            }
		 };//发送ajax请求
		 var comAjax = new Globals.comAjaxJS();
		 comAjax.sendAjax(ajaxOption);
	}
/**********************************按钮点击事件结束********************************/
/**********************************Msg区域***************************************/
		//新增msg
		function _addsuccess(json) {
			if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
				//显示msg
				switch(json.data.bizCode){
				    //成功时直接跳转页面
					case ConstCode.BIZ_CODE_INSERT_SUCCESS() :
						//提示信息
						Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_INSERT_SUCCESS());
						//调用返回函数
						_cancel();
						break;
					//参数错误，修改失败
					case 	ConstCode.BIZ_CODE_HANDLE_FAILED():
						Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_HANDLE_FAILED())
						break;
				}
			} else {
				//失败报msg
				Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
			}
		}
		//修改msg
		function _updatesuccess(json){
			if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
				//显示msg
				switch(json.data.bizCode){
				    //成功时直接跳转页面
					case	ConstCode.BIZ_CODE_UPDATE_SUCCESS() :
						//提示信息
						Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS());
						//调用返回函数
						_cancel();
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
			//参数传递
			var deviceNo = $("#deviceNo1").val();
			var deviceName = $("#deviceName1").val();
			var locationId = $("#locationId1").val();
			var location = $("#location1").val();
			var flug = 1;
			//跳转首页（设定时间）
			timeID=setTimeout(function(){
				clearTimeout(timeID);
				window.location.href=path+"/device/list?deviceNo="+deviceNo+"&deviceName="+encodeURI(encodeURI(deviceName))+"&locationId="+locationId+"&location="+encodeURI(encodeURI(location))+"&flug="+flug;
			},ConstText.HTML_MSG_NUM_MS());
		}

		//返回函数(立刻)
		function _cancelOnce(){
			//参数传递
			var deviceNo = $("#deviceNo1").val();
			var deviceName = $("#deviceName1").val();
			var locationId = $("#locationId1").val();
			var location = $("#location1").val();
			var flug = 1;
			//跳转首页（设定时间）
			window.location.href=path+"/device/list?deviceNo="+deviceNo+"&deviceName="+encodeURI(encodeURI(deviceName))+"&locationId="+locationId+"&location="+encodeURI(encodeURI(location))+"&flug="+flug;
		}

		function GetConst(BizCode) {
			var bizCode= new Globals.constJS();
			return bizCode.GetCode(BizCode);
		}
/**********************************Msg区域结束********************************/
	// 构造方法
	function constructor() {
		_this = this;
        this.init= _init;
	}
	return constructor;

})();

//继承公共AJAX类
Globals.deviceManageJS.prototype = new Globals.comAjaxJS();