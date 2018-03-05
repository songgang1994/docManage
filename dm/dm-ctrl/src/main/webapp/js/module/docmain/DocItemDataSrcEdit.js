/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：文档项目数据源新建/修改js
 */
Namespace.register("Globals.DocItemDataSrcEditJS");

Globals.DocItemDataSrcEditJS = (function() {
	// ========================================================
	// 私有属性区域
	//User类实例，回调函数中使用，不可直接用this
	var _this = null;
	var maxNum = $("#maxNum").val();
	// ========================================================
	// Ajax请求函数定义区域
	// ========================================================
	// 课题一览画面初期化
	function _InitDocItemDataSrcEdit(){
		//初始化表单验证
		_initFormVaild();
		//监听修改画面返回按钮点击事件
		$("#cancel").on('click',function(e){
			//防止表单默认提交
			e.preventDefault();
			e.stopPropagation();
			window.history.back();
		})
		// 不同情况下的样式及按钮状态
		_InitFlagSuitation();

		_tableclick();

		//监听添加条目按钮点击事件
		$("#addDataItem").on('click',function(){
			_AddDataItem();
		})

		//监听修改画面确定按钮点击事件
		$("#dataSrcSubmit").on('click',function(e){
			//防止表单默认提交
			e.preventDefault();
			e.stopPropagation();
			_DataItemSubmitAddOrEdit();
		})
	}

	function _tableclick(){
		// 监听表格内点击事件
		$("#dataSrcItemTable tr td a").on('click',function(event){
			var name = event.target.name;
			if("remove_up" == name){// 上移
				_DataSrcItemUp(this);
			}else if("remove_down" == name){// 下移
				_DataSrcItemDown(this);
			}else if("remove" == name){// 删除
				_DataSrcItemDelete(this);
			}
		})
	}

	/**********************************表单验证******************************/
	function _initFormVaild(){
		//表单验证
		$('#dataSrcEditForm').validate({
    	    errorElement: 'span', //default input error message container
    	    errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
    	    rules:
    	    {
    	    	documentItemSourceName:{
    	    		required: true
    	    	},
    	    	detailDataItem:{
    	    		required: true
	        	}

    	    },
	        messages:
	        {
	        	documentItemSourceName:{
	        		required:ConstMsg.HTML_MSG_MUSTINPUT_SOURCENAME()
		    	},
		    	detailDataItem:
	        	{
		    		required: ConstMsg.HTML_MSG_MUSTINPUT_TMNAME()
	        	}
	        },
	        invalidHandler : function(event, validator) {

			},

			highlight : function(element) { // hightlight error inputs
				$(element).closest('.form-group').addClass('has-error');

			},

			success : function(label) {
				label.closest('.form-group').removeClass('has-error');
				label.remove();

			}
		})
	}
	/**********************************表单验证结束******************************/

	// ========================================================
	// 不同情况下的样式及按钮状态
	function _InitFlagSuitation(){
		var flag = $("#dataSrcFlag").val();
		if(flag == "add"){
			var addInitTr = '<tr id="newTr" class="form-group has-error">'+
														    '<td width="40%"><div  class="form-group has-error">'+
																	'<input type="text" name="detailDataItem" class="detailDataItem" style="width: 100%">'+
															'</div></td>'+
															'<td width="30%"><div  class="form-group has-error">'+
																	'<input type="text" name="detailDataItemOther" class="detailDataItemOther" style="width: 100%">'+
															'</div></td>'+
															'<td width="30%"><div  style="text-align:left;">'+
																	'<a  class="remove_remo" name="remove_down" style="padding-right:1%">下移&nbsp;&nbsp;</a>'+
																	'<a class="remove_remo" name="remove_up" style="padding-right:1%">上移&nbsp;&nbsp;</a>'+
																	'<a class="remove_remo" name="remove" >删除&nbsp;&nbsp;</a>'+
																	'<input type="hidden" class="itemVal" value="initVal"/>'+
															'</div></td>'+
									'</tr>';
			$("#dataSrcItemTable").append(addInitTr);
			//数据源名称解除禁用
			$(".text").attr("disabled", false)
			//添加条目按钮解除禁用
			$(".remo").attr("disabled", false)
		}else if(flag == "edit"){
			//编辑框解除禁用
			$(".detail").removeAttr("disabled");
			//数据源名称解除禁用
			$(".text").attr("disabled", false)
			//添加条目按钮解除禁用
			$(".remo").attr("disabled", false)
		}else if(flag == "deta"){
			//隐藏确定按钮
			$("#dataSrcSubmit").css("visibility","hidden")
			//a标签设置失效(取消标签点击事件)
			$(".remove_remo").removeattr('onclick');
			$("#cancel").attr('onclick')
		}
	}

	// ========================================================
	// 点击上移按钮
	function _DataSrcItemUp(me){
		var $tr = $(me).closest("tr");
		//非第一行的场合
        if ($tr.index() != ConstText.HTML_MSG_NUM_ZERO()) {
            $tr.fadeOut().fadeIn();
            $tr.prev().before($tr);
        }
	}

	// ========================================================
	// 点击下移按钮
	function _DataSrcItemDown(me){
		var $down = $(".remove_down");
	    var $tr = $(me).closest("tr");
	    var $tb = $(me).closest("tbody");
	    var len = $tb.find("tr").length;
	    //非最后一行的场合
        if ($tr.index() != len - 1) {
            $tr.fadeOut().fadeIn();
            $tr.next().after($tr);
        }
	}

	// ========================================================
	// 点击删除按钮删除行
	function _DataSrcItemDelete(me){
		var $tr = $(me).closest("tr");
		//获取选择行号
		var index = $tr.index();
		//获取数据源编号
		var documentItemSourceCode = $("#documentItemSourceCode").val();
		//获取数据源条目值
		var itemValue = $(".itemVal")[index].value;
		//点击行为新添加行的场合
		if(itemValue == "clickAdd"){
			var user = ConstMsg.HTML_TXT_IS_GIVEUP_UPDATE_DEVICE_CURRENTLINE()+"?";
			Globals.msgboxJS.Confirm(user,function(){
			//直接删除
			$tr.remove();
			})
		//新建数据源，点击行不是新添加行的场合
		}else if(itemValue == "initVal"){
			Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_TRCANT_DELETE());
		//修改数据源，点击行不是新添加行的场合
		}else{
			var user = ConstMsg.BIZ_CODE_SHUR_DELETE()+documentItemSourceCode+"?";
			var ajaxOptions = {
				type: "post",
				url : 'docmain/dataSrcDelete',// 地址
				data : {
					documentItemSourceCode:documentItemSourceCode,
					itemVal:itemValue
				},
				success : function(data) {
					ajaxSuccess(data,$tr,itemValue);
				} // 请求成功时处理
			}
			var comAjax = new Globals.comAjaxJS();
			comAjax.sendAjax(ajaxOptions);
		}

	}

	// ========================================================
	// 点击添加条目按钮
	function _AddDataItem(){
		var nu = ++maxNum
		//点击添加条目按钮表格底部增加一行
		var newTr = '<tr id="newTr"><td width="40%"><div class="form-group has-error"><input type="text" name="detailDataItem'+nu+'" class="detailDataItem" style="width: 100%"></div></td><td width="30%"><div class="form-group has-error"><input type="text" name="detailDataItemOther'+nu+'" class="detailDataItemOther" style="width: 100%"></div></td><td width="30%"><div  style="text-align:left;"><a  class="remove_remo" name="remove_down" style="padding-right:1%">下移&nbsp;&nbsp;</a><a class="remove_remo" name="remove_up" style="padding-right:1%" >上移&nbsp;&nbsp;</a><a class="remove_remo" name="remove" style="padding-right:1%">删除&nbsp;&nbsp;</a><input type="hidden" class="itemVal" value="clickAdd"/></div></td></tr>';
		$("#dataSrcItemTable").append(newTr);
		//光标自动聚焦到添加的input框上
		$("#dataSrcItemTable tr#newTr td input.detailDataItem").focus();
		// 监听表格内添加行点击事件
		$("#dataSrcItemTable tr#newTr td a").on('click',function(event){
			var name = event.target.name;
			if("remove_up" == name){// 上移
				_DataSrcItemUp(this);
			}else if("remove_down" == name){// 下移
				_DataSrcItemDown(this);
			}else if("remove" == name){// 删除
				_DataSrcItemDelete(this);
			}
		})
		  _initFormVaild();
		   $("input[name = 'detailDataItem"+nu+"']").rules("add", {
	     	   required: true,
	     	   messages: {
	     		   required:ConstMsg.HTML_MSG_MUSTINPUT_TMNAME(),
	     	   }
		   });

	}

	// ========================================================
	// 点击确定按钮
	function _DataItemSubmitAddOrEdit(){
		if($("#dataSrcEditForm").valid() == false){
			return;
		}
		//获取数据源名称
		var documentItemSourceName = $("#documentItemSourceName").val();
		//获取数据源编号
		var documentItemSourceCode = $("#documentItemSourceCode").val();
		//获取数据源条目值，以逗号分隔开
		var itemValue = new Array();
		//获取数据源条目名称，以逗号分隔开
		var detailDataItem = new Array();
		var detail = "";
		var detailDataItemOther = new Array();
		var otherDetail = "";
		for(var i = 0;i < $(".itemVal").length;i++){
			itemValue[i] = $(".itemVal")[i].value;
			detailDataItem[i] = $(".detailDataItem")[i].value;
			if($(".detailDataItemOther")[i].value == ""){
				detailDataItemOther[i] = " "
			}else{
			detailDataItemOther[i] = $(".detailDataItemOther")[i].value;
			}
		}
		var itemVal = itemValue.join(",");
		detail = detailDataItem.join(",");
		otherDetail = detailDataItemOther.join(",");
		//发送ajax请求
		var ajaxOptions = {
			type: "post",
			url : 'docmain/dataSrcAddOrEdit',// 地址
			data : {
				documentItemSourceName:documentItemSourceName,
				documentItemSourceCode:documentItemSourceCode,
				detail:detail,
				otherDetail:otherDetail,
				itemVal:itemVal
			},
			success : function(data) {
				ajaxSuccess(data);
			} // 请求成功时处理
		}
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	}

	// ========================================================
	// ajax回调
	function ajaxSuccess(json,$tr,itemValue) {
		if(json.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
			//显示msg
			var documentItemSourceName = $("#backdocumentItemSourceName").val();
			switch(json.data.bizCode){
				//插入成功的场合
				case ConstCode.BIZ_CODE_INSERT_SUCCESS():
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_INSERT_SUCCESS());
					setTimeout(function(){window.location.reload()},2000);
					break;
				//更新成功的场合
				case ConstCode.BIZ_CODE_UPDATE_SUCCESS():
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS());
					setTimeout(function(){window.location.reload()},2000);
					break;
				//删除成功的场合
				case ConstCode.BIZ_CODE_DELETE_SUCCESS():
					//不是添加条目和不是新建第一行的场合
					if((itemValue != "clickAdd")&&(itemValue != "initVal")){
						//行直接删除
						$tr.remove();
					}
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DELETE_SUCCESS());
					break;
				//条目不允许删除的场合
				case ConstCode.BIZ_CODE_ITEMCANT_DELETE():
					Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_ITEMCANT_DELETE());
					break;
				//操作失败的场合
				case ConstCode.BIZ_CODE_HANDLE_FAILED():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED());
					break;
			}
		}else{
			Globals.utilJS.Globals_API_ERR_MSG(json.rtnCode);
		}
	}
	// ========================================================
	// 构造方法
	function constructor() {
		_this = this;
		this.InitDocItemDataSrcEdit = _InitDocItemDataSrcEdit;
	}
	return constructor;
})();

// 继承公共AJAX类
Globals.DocItemDataSrcEditJS.prototype = new Globals.comAjaxJS();