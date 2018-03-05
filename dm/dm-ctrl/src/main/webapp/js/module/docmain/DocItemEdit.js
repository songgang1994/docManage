/*系统名：NSK-NRDC业务系统
 作成人：张丽--%>
模块名：文档数据项管理 js */

Namespace.register("Globals.docItemEditJS");

Globals.docItemEditJS = (function() {
	// ========================================================
	// 私有属性区域
	//User类实例，回调函数中使用，不可直接用this
	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	function _init() {

		 //按钮点击事件
		$("#btn-submit").bind("click", function(e){_confirm(e);});
		$("#btn-update").bind("click", function(e){_updatel(e);});

		//详细模式设置为只读
		if($("#flag").val() == ConstText.HTML_MSG_NUM_ZERO()){
			//调用设置属性函数
			setDisabled();
		}

		//定义id名
		var documentItemTypeId = ("#documentItemType");
		//获取隐藏框值，进行监听
		var document_Type = $("#document_Type");
		var date_type = $("#date_Type");
		//调用共通函数
		Globals.utilJS.GetDateTypeList(date_type,document_Type,documentItemTypeId);
		timeID=setTimeout(function(){
			clearTimeout(timeID);
			//判断数据源是否禁用
			_typeofSourceCode();
			//判断数据项是否为小数
			_numbertype();
			//初始化表单验证
			 _initFormVaild();
		},ConstText.HTML_MSG_NUM_THB());

	}

	//设置页面不可编辑函数
	function setDisabled(){
		$('input').attr("disabled","disabled")
		$('select').attr("disabled","disabled")
		$('.combox').click(function(){
			return false;
		})
	}
	//获取下拉框值显示隐藏数字格式
	$("#documentItemType").on("change",function(){
		_numbertype();
		_typeofSourceCode();
	})
/**********************************表单验证******************************/

	$.validator.addMethod("decimal",function(value,element,params){
		var jNum = value.split("#").length - 1;
		var dNum = value.split(",").length - 1;
		var xNum = value.split(".").length - 1;
		if(xNum <=1){
				if(value.indexOf(",") == 0 || value.indexOf(".") == 0){
					return false;
				}else{
					var chfNum = new Array();
					chfNum = value.split("");
					for(var i =0; i<chfNum.length; i++){
						if(chfNum[i] == chfNum[i+1] && chfNum[i] == ","){
							return false;
						}
						if(chfNum[i] == "," && chfNum[i+1] != "#"){
							return false;
						}
						if(chfNum[i] == "." && chfNum[i+1] != "#"){
							return false;
						}
					}
					return true;
				}
		}
	})

	$.validator.addMethod("decimala",function(value,element,params){
		var jNum = value.split("#").length - 1;
		var dNum = value.split(",").length - 1;
		var xNum = value.split(".").length - 1;
			if(value.length == jNum + dNum + xNum){
				return true;
			}else{
				return false;
			}
	})

	function _initFormVaild(){
		$("#form-item").validate({
			errorElement: 'span', //default input error message container
    	    errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
			rules: {
				documentItemType: {			// 文档数据项类型
					required: true
				},
				documentItemSourceCode: { 			// 文档数据源选择
					required: true
				},
				documentItemName: { 			// 数据项显示名称
					required: true
				},
				numFormat:{
					decimala: true,
					decimal: true
				},
				maxLength: { 			// 最大长度
					number:true
				},
		    },
			messages: {
				documentItemType: {			// 文档数据项类型
					required: ConstMsg.HTML_MSG_MUSTCHOOSE_ITEMNAME()
				},
				documentItemSourceCode: { 			// 文档数据源选择
					required: ConstMsg.HTML_MSG_MUSTCHOOSE_SOURCENAME()
				},
				documentItemName: { 			// 数据项显示名称
					required: ConstMsg.HTML_MSG_NOTNULL_ITEMNAME()
				},
				numFormat:{
					decimala: ConstMsg.HTML_MSG_MUSTINPUT_NUMTYPR(),
					decimal: ConstMsg.HTML_MSG_MUSTINPUT_TRUENUMTYPR()
				},
				maxLength: { 			// 最大长度
					number:ConstMsg.HTML_MSG_MUSTINPUT_NUMBER()
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

		        }
		});
	}
/**********************************表单验证结束******************************/
/**********************************按钮点击事件******************************/
	//通过数据项值判断数据源是否禁用
	function _typeofSourceCode(){
		var documentItemType = $("#documentItemType option:selected").text();
		if(documentItemType == ConstText.HTML_MSG_STR_XIALA() || documentItemType == ConstText.HTML_MSG_STR_FUXUAN() || documentItemType == ConstText.HTML_MSG_STR_DANXUAN()){
			$("#documentItemSourceCode").attr("disabled",false)
		}else{
			$("#documentItemSourceCode").attr("disabled","disabled")
			$("#documentItemSourceCode").val("");
			$("#documentItemSourceCode-error").remove();
		}
	}

	//combox  fromto点击事件判断
	var  comvalue = $("#isFromToItem").val();
	var  newvalue = $("#newFrom").val();
	$("#isFromToItem").change(function(){
		if($("#isFromToItem").val()==ConstText.HTML_MSG_NUM_ONE()){
			 //combox赋值0
			 $("#isFromToItem").val(ConstText.HTML_MSG_NUM_ZERO());
		}else{
			//combox赋值1
			$("#isFromToItem").val(ConstText.HTML_MSG_NUM_ONE());
		}
	});

	//返回按钮跳转上一个画面
	$("#btn-back").bind("click",function(e){
		//防止表单默认提交
		e.preventDefault();
		e.stopPropagation();
		//弹框
		 var doccancel = ConstMsg.CONFIRM_BACK();
            Globals.msgboxJS.Confirm(doccancel,function(){
            	var docItemName = $("#docItemsName1").val();
				var itemId = $("#itemId1").val();
				var itemName = $("#itemName1").val();
				window.location.href=path+"/docmain/itemsearch?docItemName="+encodeURI(encodeURI(docItemName))+"&itemId="+itemId+"&itemName="+encodeURI(encodeURI(itemName));
            },function(){
              return;
           })
	});

	//combox  公共点击事件判断
	$("#isPublicItem").change(function(){
		if($("#isPublicItem").val()==ConstText.HTML_MSG_NUM_ONE()){
			 //combox赋值0
			$("#isPublicItem").val(ConstText.HTML_MSG_NUM_ZERO());
		}else{
			 //combox赋值1
			$("#isPublicItem").val(ConstText.HTML_MSG_NUM_ONE());
		}
	})

	//新增按钮点击事件
	function _confirm(e){
		//防止表单默认提交
		e.preventDefault();
		e.stopPropagation();
		var numformat = $("#numFormat").val();
		$("#newnumfot").val(numformat);
		//表单提交判断
		if($("#form-item").valid() == false){
			return;
		}
		var  ajaxOptions = {
					async:false,
		            url:"docmain/docItem?numformat="+ escape (encodeURIComponent(numformat)),
		            type:"post",
		            data:JSON.stringify($('#form-item').serializeJSON()),
		            contentType: "application/json",
		            success:function(data){
		            	_addsuccess(data);
		            }
		       };
		   var comAjax = new Globals.comAjaxJS();
		   comAjax.sendAjax(ajaxOptions);
	}

	//修改按钮点击事件
	function _updatel(e){
		//防止表单默认提交
		e.preventDefault();
		e.stopPropagation();
		if($("#form-item").valid() == false){
			return;
		}
		//修改前的FromTo项目选中，画面修改成不选中
		if(newvalue==ConstText.HTML_MSG_NUM_ONE() && $("#isFromToItem").val()==ConstText.HTML_MSG_NUM_ZERO()){
			$("#state").val(ConstText.HTML_MSG_STR_YTN());
		//修改前的FromTo项目不选中，画面修改成选中
		}else if(newvalue==ConstText.HTML_MSG_NUM_ZERO() && $("#isFromToItem").val()==ConstText.HTML_MSG_NUM_ONE()){
			$("#state").val(ConstText.HTML_MSG_STR_NTY());
		//FromTo项目不修改
		}else{
			$("#state").val(ConstText.HTML_MSG_STR_NOT());
		}
		var numformat = $("#numFormat").val();
		$("#newnumfot").val(numformat);
		//调用修改的ajax
		var  ajaxOptions = {
				async:false,
				 url:"docmain/docItem?numformat="+ escape (encodeURIComponent(numformat)),
	            type:"post",
	            data:JSON.stringify($('#form-item').serializeJSON()),
	            contentType: "application/json",
	            success:function(data){
	            	_updatesuccess(data);
	            }
	       };
	    var comAjax = new Globals.comAjaxJS();
	    comAjax.sendAjax(ajaxOptions);
	}

/**********************************按钮点击事件结束******************************/
/**********************************数字类型事件******************************/

	function _numbertype(){
		var documentItemType = $("#documentItemType option:selected").text();
		var numbfomt =$("#newnumfot").val();
		if(numbfomt==null||numbfomt==""|| typeof(numbfomt)=='undefined'){
			if(documentItemType == ConstText.HTML_MSG_STR_DECIAML()){
				var number = '<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >'
								+'<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">'
								+'<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">'
								+'<span >数字格式</span></div>'
								+'<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">'
								+'<input id="numFormat" name="numFormat"  type="text"  value=""  maxLength="20" style="width:100%"/>'
								+'</div></section></div>'
				$("#numbertype").append(number)
			}else{
				$("#numbertype").empty();
			}
		}else{
			if(documentItemType == ConstText.HTML_MSG_STR_DECIAML()){
				var number = '<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >'
					+'<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">'
					+'<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">'
					+'<span >数字格式</span></div>'
					+'<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">'
					+'<input id="numFormat" name="numFormat"  type="text"  value="'+numbfomt+'"  maxLength="20" style="width:100%"/>'
					+'</div></section></div>'
				$("#numbertype").append(number)
			}else{
				$("#numbertype").empty();
			}
		}

	}

/**********************************数字类型事件结束*************************************/
/************************************Msg区域****************************************/
	//修改msg
	function _updatesuccess(json) {
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
				case 	ConstCode.BIZ_CODE_UPDATE_SUCCESS():
					//提示信息
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS());
					//调用返回函数
					_cancel();
				    break;
				case 	 ConstCode.BIZ_CODE_INVALID_PARAMS():
					Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_INVALID_PARAMS())
					break;
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}
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
				case 	 ConstCode.BIZ_CODE_INVALID_PARAMS():
					Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_INVALID_PARAMS())
					break;
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}

	//返回函数
	function _cancel(){
		//参数传递
		var docItemName = $("#docItemsName1").val();
		var itemId = $("#itemId1").val();
		var itemName = $("#itemName1").val();
		//跳转首页（设定时间）
		timeID=setTimeout(function(){
			clearTimeout(timeID);
			window.location.href=path+"/docmain/itemsearch?docItemName="+encodeURI(encodeURI(docItemName))+"&itemId="+itemId+"&itemName="+encodeURI(encodeURI(itemName));
		},ConstText.HTML_MSG_NUM_MS());
	}

	function GetConst(BizCode) {
		var bizCode= new Globals.constJS();
		return bizCode.GetCode(BizCode);
	}
/**********************************Msg区域*************************************/

/**********************************构造方法******************************/
	// 构造方法
	function constructor() {
		_this = this;
		this.init = _init;
	}

	return constructor;
/**********************************构造方法结束******************************/


})();

// 继承公共AJAX类
Globals.docItemEditJS.prototype = new Globals.comAjaxJS();