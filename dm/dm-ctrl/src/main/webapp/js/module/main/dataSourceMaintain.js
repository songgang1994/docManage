/*系统名：NSK-NRDC业务系统
 作成人：张丽--%>
模块名：字典数据维护 js */

Namespace.register("Globals.dataSourceMaintainJS");

Globals.dataSourceMaintainJS = (function() {
// ========================================================
	// 私有属性区域
	//User类实例，回调函数中使用，不可直接用this
	var _this = null;
/**********************************设置，获取全局变量******************************/
	//添加行号变量
	var maxNum = $("#maxNum").val();

/**********************************设置，获取全局变量结束******************************/
	function _init() {
		//初始化按钮点击事件
		$("#btn-confirm").bind("click", function(e){_confirm(e);});
		//初始化表单验证
		_initFormVaild();
		//调用共通函数
		var legalId = "#Legal";
		var date_type = $("#date_Type");
		Globals.utilJS.GetLegalTypeList(date_type,legalId);

	}
/**********************************表单验证******************************/
	function _initFormVaild(){
		$("#source-form").validate({
			errorElement: 'span', //default input error message container
    	    errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
    	    rules:{
    	    	Legal:{
		            required: true,
	        	},
    	    }, //验证表单是否为空
	        messages:{
	        	Legal:{
		   		 	required: ConstMsg.HTML_FRM_SELECT_LEGAL(),
	    		},
	        },
			invalidHandler: function (event, validator){ //display error alert on form submit
		    },
		    highlight: function (element){ // hightlight error inputs
		    	$(element)
		            .closest('.form-group').addClass('has-error'); // set error class to the control group
		    },
		    //防止验证添加空的span
		    errorPlacement: function(error, element) {
		    	var p = error;
				if(error!=null){
					if(error.text()!=""){
						p.appendTo(element.parent());
					}
				}
			},
		    success: function (span){
		    }
		});

	}
/**********************************表单验证结束******************************/
/**********************************按钮点击事件******************************/
	//返回按钮跳转上一个画面
	$("#close").bind("click",function(e){
		//防止表单默认提交
		e.preventDefault();
		e.stopPropagation();
		//弹框
		 var maincancel = ConstMsg.CONFIRM_BACK();
            Globals.msgboxJS.Confirm(maincancel,function(){
            	//返回首页
        		Globals.utilJS.ReturnHomePage();
            },function(){
              return;
           })
	});

	//确认按钮点击事件
	function _confirm(e){
		e.preventDefault();
		e.stopPropagation();
		if($("#source-form").valid() == false){
			return;
		}
		//获取下拉框查出的类型（放入集合）,以逗号分开
		var type1Value = new Array();
		for(var i = ConstText.HTML_MSG_NUM_ZERO() ;i< $(".con").length;i++){
             type1Value[i] = $(".con")[i].value;
		}
		var  type1=  type1Value.join(",");
		if($(".addcon").length!=ConstText.HTML_MSG_NUM_ZERO()){
			//获取新增行的类型（放入集合）,以逗号分开
			var addValue = new Array();
			for(var j = ConstText.HTML_MSG_NUM_ZERO();j<$(".addcon").length;j++){
				addValue[j] = $(".addcon")[j].value;
			}
			var addType = addValue.join(",");
		}

		//获取当前行（放入集合）,以逗号分开
		var nowNuml = new Array();
		for(var j = ConstText.HTML_MSG_NUM_ZERO();j<$(".num").length;j++){
			nowNuml[j] = $(".num")[j].value;
		}
		var nowNum = nowNuml.join(",");
		//下拉框选中选项对应的值
		var type = $("#Legal").val();
		//发送ajax请求
		var ajaxOption = {
				type: "post",
				url : 'main/changeSource',// 地址
				data : {
					type1:type1,
					addType:addType,
					nowNum:nowNum,
					type : type
				},
				success : function(data) {
                     _successadd(data);
				} // 请求成功时处理
			};
			var comAjax = new Globals.comAjaxJS();
			comAjax.sendAjax(ajaxOption);
	}

	//查询按钮点击事件
	$("#search").click(function(){
		if($("#source-form").valid() == false){
			return;
		}
		//判断是否选中类型
		if($("#Legal").val()==null ||$("#Legal").val()==""){
			$("#initDiv").show();
			$(".second").remove();
			maxNum=ConstText.HTML_MSG_NUM_ZERO();
			return;
		}else{
			var type1 = $("#Legal").val();
			var ajaxOptions = {
					type: "post",
					url : 'main/getDisName',// 地址
					data : {
						type1:type1
					},
					success : function(data) {
						_successlist(data);
					} // 请求成功时处理
				};

				var comAjax = new Globals.comAjaxJS();
				comAjax.sendAjax(ajaxOptions);
		}

	})

	//页面添加按钮点击事件
   function add(){
	   var nu = ++maxNum
	   $("#add").remove();
						   $("#oold")
								.before(
										"<div class='col col-sm-12 col-md-12 col-lg-12 col-xs-12 second'>"
										        + "<div class='col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding'>"
												+ "<div class='col col-sm-12 col-md-3 col-lg-3 col-xs-12'>"
												+ "<span>"
												+ '内容'
												+ nu
												+ "</span>"
												+ "<input type='hidden' class='num' value="
												+ nu
												+ ">"
												+ "</div>"
												+ "<div class='form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12'>"
												+ "<input class='addcon' name='content"
												+ nu
												+ "' type='text' style='width:100%'/>"
												+ "</div>"
												+ "</div>"
												+ "<div class='col col-sm-12 col-md-3 col-lg-3 col-xs-12'>"
												+		"	<input type='hidden'  name='conten" + nu+"'>"
												+"</div>"
												+ "</div>") ;
	    //确定位置，将按钮加入最后一行输入框
	   var jia = "<button id='add'  type='button' class='btn btn-default'>+加</button>"
	   $("input[name=conten"+nu+"]").after(jia);
	   //添加按钮点击事件
	   $("#add").on("click", function(e) {
		   	add();
		});
	   $("input[name = 'content"+nu+"']").rules("add", {
     	   required: true,
     	   messages: {
     		   required:ConstMsg.HTML_MSG_MUSTINPUT_CONTENT(),
     	   }
	   });
   }

/** *******************************按钮点击事件结束***************************** */
/** *******************************点击事件触发函数***************************** */
   function _successlist(data){
		$("#initDiv").hide();
		$(".second").remove();
		//设置下拉框不可选
		$("#Legal").attr("disabled","disabled");
		//字符串拼接变量
		var all ="";
		//遍历中的 数字变量
		var u = ConstText.HTML_MSG_NUM_ZERO();
		for(var i =ConstText.HTML_MSG_NUM_ZERO(); i<data.length;i++){
			var u = ++u;
			all += "<div  class='col col-sm-12 col-md-12 col-lg-12 col-xs-12 second'>"
				    + "<div class='col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding'>"
					+ "<div class='col col-sm-12 col-md-3 col-lg-3 col-xs-12'>"
					+ "<span >" + '内容' + u + "</span>"
					+ "<input type='hidden' class='num' value=" + u + ">"
					+ "</div>"
					+ "<div class='form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12'>"
					+ "<input class='con' value='" + data[i].dispName
					+ " ' name='content" + i
					+ "' type='text' style='width:100%'/>" + "</div>"
					+ "</div>"
					+ "<div class='col col-sm-12 col-md-3 col-lg-3 col-xs-12'>"
					+		"	<input type='hidden'   name='conten" + i+"'>"
					+"</div>"
					+ "</div>"
		}
		$("#source-form").valid();
		maxNum=data.length;
		// 新增行显示
		 $("#reset").show();
		//追加按钮显示
		$("#oold").show();
		//确认按钮显示
		$("#btn-confirm").show();
		//append数据
		$("#oold").before(all);
		//确定位置，将按钮加入最后一行输入框
		var jia ="	<button id='add'  type='button' class='btn btn-default'>+加</button>"
		$("input[name=conten"+(maxNum-1)+"]").after(jia);
		//添加按钮点击事件
		$("#add").on("click", function(e) {
		   	add();
		});
		//循环，动态添加验证
		for(var i=0;i<data.length;i++){
			//内容验证添加
			   $("input[name = 'content"+i+"']").rules("add", {
		     	   required: true,
		     	   messages: {
		     		   required:ConstMsg.HTML_MSG_MUSTINPUT_CONTENT(),
		     	   }
			   });
		}
	}

	//插入更新成功后清空表单
	function _resetForm(){
		//下拉框变为可编辑
	    $('#Legal').removeAttr("disabled");
	    $('#Legal').val("");
	    //删除新增行
	    $("#reset").hide();
	    //确认按钮隐藏
	    $("#btn-confirm").hide();
	}
/*********************************点击事件触发函数结束******************************/
/**********************************Msg区域*************************************/
	//新增msg
	function _successadd(json) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
				//新增成功
				case ConstCode.BIZ_CODE_INSERT_SUCCESS() :
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_INSERT_SUCCESS())
					//清空表单数据
					timeID=setTimeout(function(){
						clearTimeout(timeID);
						_resetForm();
					},ConstText.HTML_MSG_NUM_MS());
					break;
				//更新字段成功
				case 	ConstCode.BIZ_CODE_UPDATE_SUCCESS():
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS())
					//清空表单数据
					timeID=setTimeout(function(){
						clearTimeout(timeID);
						_resetForm();
					},ConstText.HTML_MSG_NUM_MS());
				    break;
				//参数错误，新增失败
				case 	 ConstCode.BIZ_CODE_INVALID_PARAMS():
					Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_INVALID_PARAMS())
					break;
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
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

//继承公共AJAX类
Globals.dataSourceMaintainJS.prototype = new Globals.comAjaxJS();