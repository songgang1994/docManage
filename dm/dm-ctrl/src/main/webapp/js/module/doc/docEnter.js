Namespace.register("Globals.docEnterJS");

Globals.docEnterJS = (function() {
	// ========================================================
	// 私有属性区域
	//User类实例，回调函数中使用，不可直接用this
	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();

	// ========================================================
	// Ajax请求函数定义区域
	// ========================================================

	function _initFormValidation() {

		var rules = {};
		var messages = {};

		$.validator.addMethod('integer', function(value, elemetnt, params) {
			if(value ==''){
				return true;
			}
			return /^\+?[1-9][0-9]*$/.test(value);
		}, ConstMsg.HTML_MSG_INPUT_INTEGER());
		$.validator.addMethod('currency', function(value, elemetnt, params) {
			if(value ==''){
				return true;
			}
			return /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/.test(value);
		}, ConstMsg.HTML_MSG_INPUT_AMOUNT());
		$.validator.addMethod('decimal', function(value, elemetnt, params) {
			if(value ==''){
				return true;
			}
			// 只能输入数字、逗号分隔符
			if(!/^[\d,.]+$/.test(value)){
				return false;
			}
			return true;
		}, ConstMsg.HTML_MSG_INPUT_DECIMAL());
		$.validator.addMethod('needFile', function(value, elemetnt, params) {
			// 获取多文件连接个数
			var count_a = $(elemetnt).parent().parent().find('a').length;
			// 获取是否必须输入
			var require = $(elemetnt).attr('data-target-require');

			if(count_a <= 0 && require == 'true'){
				if($(elemetnt).val() == ''){
					return false;
				}
			}
			return true;
		}, ConstMsg.HTML_MSG_INPUT_DECIMAL());
		$.validator.addMethod('fromTo', function(value, elemetnt, params) {
			if($(elemetnt).prop('class').indexOf('item-type-integer') != -1){//整数
				var to = $(elemetnt).closest('.form-group').prev().prev().find('.item-type-integer').val();
				if(to != ''){
					return parseInt(value) >= parseInt(to);
				}

			}else if($(elemetnt).prop('class').indexOf('item-type-currency')!= -1){//金额
				var to = $(elemetnt).closest('.form-group').prev().prev().find('.item-type-currency').val();
				if(to != ''){
					return parseFloat(value) >= parseFloat(to);
				}

			}else if($(elemetnt).prop('class').indexOf('item-type-decimal')!= -1){//小数
				var to = $(elemetnt).closest('.form-group').prev().prev().find('.item-type-decimal').val();
				if(to != ''){
					return parseInt(value.split(",").join("")) >= parseInt(to.split(",").join(""));
				}

			}else if($(elemetnt).prop('class').indexOf('item-type-date')!= -1){//日期
				var to = $(elemetnt).closest('.form-group').prev().prev().find('.item-type-date').val();
				if(to != ''){
					return parseInt(value.split("/").join("")) >= parseInt(to.split("/").join(""));
				}

			}
			return true;
		}, ConstMsg.HTML_MSG_LESS_FROMTO());

		$("#form-doc").validate({
    	    errorElement: 'span', //default input error message container
    	    errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
    	    rules:{
    	    	DOCUMENT_OLD_CODE:{
    	    		remote:{
    	    			type:'POST',
    	    			url:'doc/check',
    	    			data:{
    	    				docCode:function(){
    	    					return $('[name="documentCode"]').val()
    	    				},
    	    				docOldCode:function(){
    	    					return $('[name="DOCUMENT_OLD_CODE"]').val()
    	    				}
    	    			}
    	    		}
    	    	}
    	    },
    	    messages:{
    	    	DOCUMENT_OLD_CODE:{
    	    		remote:ConstMsg.HTML_MSG_INPUT_OLDNUMBER()
    	    	}

    	    },
	        invalidHandler: function (event, validator) {

	        },
	        highlight: function (element) { // hightlight error inputs
	            $(element)
	                .closest('.form-group').addClass('has-error'); // set error class to the control group
	        },
	        errorPlacement : function(error, element) {
	        	$(element).closest('.form-group').append(error);
			},
	        success: function (label) {
	            label.closest('.form-group').removeClass('has-error');
	            label.remove();
	        }
    	});

	}



	function _InitDocEnterJS() {

		// 单文件
		$('.delMainFileSpan').click(function(){
			var require = $(this).attr('data-target-require');
			var html = '<div><input name="mainFile" type="file"  data-rule-required = '+require+' data-msg-required="单文件为必填项"/></div></div>';
			$(this).parent().parent().append(html);
			$(this).parent().remove();
		})
		$('.updMainFileSpan').click(function(){
			$(this).next().click();
		})
		// 多文件上传
		$('.delSubFileSpan').click(function(){
			var delVal = $(this).closest('.form-group').find('[name="delSubFilesNo"]').val();
			var updVal = $(this).closest('.form-group').find('[name="updSubFilesNo"]').val();
			// 获取删除的文件序号
			var no = $(this).prev().find('[name="fileNo"]').val();
			$(this).closest('.form-group').find('[name="delSubFilesNo"]').val(delVal+no+',');
			// 删除文件已做更新操作，取出更新操作数据
			if(updVal.indexOf(no) != -1){
				$(this).closest('.form-group').find('[name="updSubFilesNo"]').val(updVal.replace(no+',',''));
			}
			$(this).parent().remove();
		})
		$('.updSubFileSpan').click(function(){
			$(this).next().click();
		})
		$('.updSubFiles').change(function(){
			// 获取更新的文件序号
			var upd = $(this).parent().find('[name="fileNo"]').val();
			// 获取已经更新的文件序号
			var upded =$(this).closest('.form-group').find('[name="updSubFilesNo"]').val();
			if(upded.indexOf(upd) == -1){// 更新的文件序号不存在
				$(this).closest('.form-group').find('[name="updSubFilesNo"]').val(upded +upd+',');
			}
		})
		$(".item-type-multifile").click(function() {
			var require = $(this).attr('data-target-require');
			var html = '<div><input name="subFiles" type="file"  data-rule-needFile = "true" data-msg-needFile="文件为必填项"/><span  onclick="return false;">[删除]</span></div></div>';
			var $f = $(html).on('click', 'span', function() {
				$f.remove();
			});
			//	最新显示的子文件未选择文件，不在出现新的子文件
			if($(this).parent().find('[name="subFiles"]:last').val() != ''){
				$(this).parent().append($f);
			}

		});
		// 弹出画面 - 人员检索
		$(".item-type-popup-staff").click(function() {
			if($(this).closest('span').attr("disabled")){
				return;
			}
			$this = $(this);
			var staff = new Globals.staffSearchPopupJS();
			staff.ShowModal('radio', function(data) {
				if (data.length > 0) {
					var userIds = data.map(function(x){return  x[0].userId});
					var userNames = data.map(function(x){return  x[0].userName});

					$('[name=' + $this.data('target-text') + ']').val(userNames);
					$('[name=' + $this.data('target-text') + ']').attr("title",userNames);
					$('[name=' + $this.data('target-value') + ']').val(userIds);
				}
			});
		});
		// 弹出画面 - 部门检索
		$(".item-type-popup-dept").click(function() {
			if($(this).closest('span').attr("disabled")){
				return;
			}
			$this = $(this);
			var depart = new Globals.departSearchPopupJS();
			depart.ShowModal('radio', function(data) {
				if (data.length > 0) {
					var departIds = data.map(function(x){return  x[0].deptId});
					var departNames = data.map(function(x){return  x[0].deptName});

					$('[name=' + $this.data('target-text') + ']').val(departNames);
					$('[name=' + $this.data('target-text') + ']').attr("title",departNames);
					$('[name=' + $this.data('target-value') + ']').val(departIds);
				}
			});
		});

		$(".item-type-popup-staff-mul").click(function() {
			if($(this).closest('span').attr("disabled")){
				return;
			}
			$this = $(this);
			var staff = new Globals.staffSearchPopupJS();
			staff.ShowModal('checkbox', function(data) {
				if (data.length > 0) {
					var userIds = data.map(function(x){return  x[0].userId}).join(',');
					var userNames = data.map(function(x){return  x[0].userName}).join(',');

					$('[name=' + $this.data('target-text') + ']').val(userNames);
					$('[name=' + $this.data('target-text') + ']').attr("title",userNames);
					$('[name=' + $this.data('target-value') + ']').val(userIds);
					$('[name=' + $this.data('target-text') + ']').prev().attr('title',userNames);

				}
			});
		});
		$(".item-type-popup-dept-mul").click(function() {
			if($(this).closest('span').attr("disabled")){
				return;
			}
			var $this = $(this);
			var a;
			if($this.data('target-value') == 'DOCUMENT_VIEW_DEPT'){
				a='1';
			}else{
				a='';
			}
			var depart = new Globals.departSearchPopupJS();
			depart.ShowModal('checkbox',a, function(data) {
				if (data.length > 0) {
					var departIds = data.map(function(x){return  x[0].deptId}).join(',');
					var departNames = data.map(function(x){return  x[0].deptName}).join(',');

					$('[name=' + $this.data('target-text') + ']').val(departNames);
					$('[name=' + $this.data('target-text') + ']').attr("title",departNames);
					$('[name=' + $this.data('target-value') + ']').val(departIds);
					$('[name=' + $this.data('target-text') + ']').prev().attr('title',departNames);
				}
			});
		});
		$("#btn-save").click(function(event) {

			event.preventDefault();

			if($("#form-doc").valid() == false){
				return;
			}

			$("[name=APPROVAL_STATUS]").val("1");
			var action = "";
			switch ($(this).attr('data-mode')) {
				case '1': action = "doc/update"; break;
				case '2': action = "doc/save"; break;
			}
			var me = this;

			var formData = new FormData($( "#form-doc" )[0]);
			 $.ajax({
		          url: action ,
		          type: 'POST',
		          data: formData,
		          async: false,
		          cache: false,
		          traditional: true,
		          contentType: false,
		          processData: false,
		          success: function (data) {
		        	  if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
		        		  	if(data.data.bizExeMsgs != ''){
		        		  		var str = '';
		        		  		for(var s in data.data.bizExeMsgs){
		        		  			str += data.data.bizExeMsgs[s]+'<br/>';
		        		  		}
		        		  		Globals.msgboxJS.ToastrError(str);
		        		  		return ;
		        		  	}
							if(data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()){
								Globals.msgboxJS.ToastrSuccess(ConstMsg.HTML_MSG_DOC_DATA_VATIONSUCCESS());
								setTimeout(function(){//两秒后跳转
								location.href =path+ "/doc/enter?mode=1&documentCode=" + $('[name="documentCode"]').val()
								 },ConstText.HTML_MSG_NUM_MS());
							}
						}else{
							Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
						}
		          }

		     });

		});
		$("#btn-commit").click(function(event) {

			event.preventDefault();

			if($("#form-doc").valid() == false){
				return;
			}

			$("[name=APPROVAL_STATUS]").val("2");
			var formData = new FormData($( "#form-doc" )[0]);
			 $.ajax({
		          url: "doc/commit" ,
		          type: 'POST',
		          data: formData,
		          async: false,
		          cache: false,
		          traditional: true,
		          contentType: false,
		          processData: false,
		          success: function (data) {
		        	  if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
		        		  	if(data.data.bizExeMsgs != ''){
		        		  		var str = '';
		        		  		for(var s in data.data.bizExeMsgs){
		        		  			str += data.data.bizExeMsgs[s]+'<br/>';
		        		  		}
		        		  		Globals.msgboxJS.ToastrError(str);
		        		  		return ;
		        		  	}
							if(data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()){
								Globals.msgboxJS.ToastrSuccess(ConstMsg.HTML_MSG_DOC_DATA_VATIONSUCCESS());
								setTimeout(function(){//两秒后跳转
									location.href=path+"/doc/enter?mode=0&documentCode="+$('input[name="documentCode"]').val();
					             },ConstText.HTML_MSG_NUM_MS());

							}
						}else{
							Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
						}

		          }
		     });

		});
		$("#btn-approve").click(function(event) {
			event.preventDefault();


			$("[name=APPROVAL_STATUS]").val("3");
			var formData = new FormData($( "#form-doc" )[0]);
			 $.ajax({
		          url: "doc/approve" ,
		          type: 'POST',
		          data: formData,
		          async: false,
		          cache: false,
		          traditional: true,
		          contentType: false,
		          processData: false,
		          success: function (data) {
		        	  if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
							if(data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS() || data.data.bizCode == ConstCode.BIZ_CODE_APPROVAL_SUCCESS()){
								Globals.msgboxJS.ToastrSuccess(ConstMsg.HTML_MSG_DOC_TOEXAMINE_SUCCESS());
								setTimeout(function(){//两秒后跳转
									location.href=path+"/doc/enter?mode=0&documentCode="+$('input[name="documentCode"]').val();
					             },ConstText.HTML_MSG_NUM_MS());
							}

						}else{
							Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
						}

		          }
		     });

		});
		$("#btn-decline").click(function(event) {
			event.preventDefault();

			var formData = new FormData($( "#form-doc" )[0]);
			 $.ajax({
		          url: "doc/decline" ,
		          type: 'POST',
		          data: formData,
		          async: false,
		          cache: false,
		          contentType: false,
		          processData: false,
		          success: function (data) {
		        	  if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
							if(data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS() || data.data.bizCode == ConstCode.BIZ_CODE_APPROVAL_FAILED()){
								Globals.msgboxJS.ToastrSuccess(ConstMsg.HTML_MSG_DOC_TOEXAMINE_SUCCESS());
								setTimeout(function(){//两秒后跳转
									location.href=path+"/doc/enter?mode=0&documentCode="+$('input[name="documentCode"]').val();
					             },ConstText.HTML_MSG_NUM_MS());
							}

						}else{
							Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
						}
		          }
		     });
		});
		$('#btn-back').click(function(){
			var back = $('#locationType').val();

			if(back == ConstText.RET_TYPE_INDEX()){// 跳转首页
				Globals.utilJS.ReturnHomePage();
			}else if(back == ConstText.BACK_TO_BATCH_CHECK()){// 跳转批量审核
				location.href = path+"/doc/batchcheck"
			}else if(back == ConstText.BACK_TO_DOC_SEARCH()){// 跳转文档查询
				location.href = path+"/doc/search"
			}
		});
		// 清楚不用的单元格，并扩展
		$("td:contains(#BLANK#)").prev().remove();
		$("td:contains(#BLANK#)").remove();
		$("tr").each(function() {
			if ($('td', this).length == 2) {
				$('td:last', this).attr('colspan', 3);
			}
		});

		// 设置表单验证
		_initFormValidation();

		$('.item-type-decimal').change(function(){
			$(this).val(parseFloat($(this).val()).toLocaleString());
		});

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
			// 日期
			$(".item-type-date").datepicker({
				changeMonth : true,
				dateFormat : "yy/mm/dd",
			});
	}

	// ========================================================
	// 构造方法
	function constructor() {
		_this = this;
		this.InitDocEnterJS = _InitDocEnterJS;

		$("#btn-fetchcode").click(function(event) {

			event.preventDefault();

			var documentType = $("[name=documentType]").val();
			if (documentType == '') {
				return;
			}

			var ajaxOptions = {
				url: 'doc/fetchcode',
				data: {
					documentType: documentType
				},
				dataType: 'json',
				success: function(data) {
					if (data.data.bizCode == ConstCode.BIZ_CODE_HANDLE_FAILED()) {
						Globals.msgboxJS.ToastrError(ConstMsg.DOC_APPROVE_STAFF_NOT_SET());
					} else {
						var documentCode = data.data.bizExeMsgs[0];
						location.href =path + "/doc/enter?mode=2&documentType=" + documentType +
							"&documentCode=" + documentCode;
					}
				}
			};

			_this.sendAjax(ajaxOptions);
		});
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.docEnterJS.prototype = new Globals.comAjaxJS();