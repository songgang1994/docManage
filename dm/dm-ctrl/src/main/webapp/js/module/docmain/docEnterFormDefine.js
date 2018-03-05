Namespace.register("Globals.docEnterFormDefineJS");
/*
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：曾雷 --%>
 * <%-- 模块名：文档录入表单定义 --%>
 */
Globals.docEnterFormDefineJS = (function() {
	// ========================================================
	// 私有属性区域
	// User类实例，回调函数中使用，不可直接用this
	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	function _initPage() {

		// 设置拖拽框高度
		$('#sort2').height($('#sort1').height());
		$('#sort3').height($('#sort1').height());

		$("#doc_enter_form_define").validate({
			errorElement: 'span', //default input error message container
    	    errorClass: 'help-block col-md-offset-0', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
			rules: {
				approvalID: {			// 文档数据项类型
					required: true
				}
		    },
			messages: {
				approvalID: {			// 文档数据项类型
					required: ConstMsg.DOC_LAST_APPROVAL_MUST_SELECT()
				}

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



		// 下拉框为空，不进行后面操作，返回前画面
		if($('#selectCount').val() == ConstCode.BIZ_CODE_RECORD_0()){
			Globals.msgboxJS.ToastrError(ConstMsg.HTML_MSG_DOC_ENTER_DEFINE());

			setTimeout(function(){//两秒后跳转
				Globals.utilJS.ReturnHomePage();
             },ConstText.HTML_MSG_NUM_MS());
		}
		// 可拖拽
		$("#sort1, #sort2, #sort3").sortable({
			connectWith : ".connectedSortable",
			// cursor: "move",
			opacity : 0.6,
			revert : 300,

		}).disableSelection();

		// 拖拽至第一列
		$('#sort1').bind('sortreceive', function(event, ui) {
			// 获取拖拽div对象
			var div = ui.item;
			// 删除div中的checkbox
			var checkbox = $(div).find('span');
			$(checkbox).remove();
		});

		// 拖拽至其他列
		$('#sort2,#sort3').bind('sortreceive',
			function(event, ui) {
				// 获取拖拽div对象
				var div = ui.item;

				if ($(div).find("input[name='checkbox']").length > 0) {// 存在空白项目
					// 获取拖拽div对象
					var div = ui.helper;
					// 判断是否从第一列拖拽
					var span = $(div).find('span').length;
					if (span == 0) {// 第一列
						// 添加checkbox
						var span = "<span style='cursor: pointer;' class='removeLine'>x</span><span style='cursor: pointer;float:right;padding-right:25px'><a href-void class='a_sort'>详细</a></span>";
						$(div).append(span);
						$(div).css("width", $('#td_sort').width());
					}
				} else {
					// 判断是否从第一列拖拽
					var span = $(div).find('span').length;
					if (span == 0) {// 第一列
						// 添加checkbox
						var span = "<span style='cursor: pointer;float:right;padding-right:25px'><input type='checkbox' name='checkbox' /></span>";
						$(ui.item).append(span);
					}
				}

			});

		// 空白项拖拽
		$("#blankItem").draggable({
			connectToSortable : "#sort2, #sort3",
			helper : "clone",
			revert : "invalid",

		});

		$( "#blankItem" ).on( "dragstop", function( event, ui ) {
			// 获取拖拽div对象
			var div = ui.helper;
			$(div).css("width",$('#td_sort').width());
			$(div).css("height",$('.sort-item').height());
		} );

		// 表单提交
		$("#define_form_submit").on("click", function(event){
			event.preventDefault();

			if($("#doc_enter_form_define").valid() == false){
				return;
			}

			formSubmit();
		})

		// 文档类型监听
		$("#docType").on("change", function() {
			location.href =path+ '/docmain/enterformdefine?docType='+$(this).val();
		})

		// 弹出画面 - 人员检索
		$(".item-type-popup-staff").click(function() {
			$this = $(this);
			var staff = new Globals.staffSearchPopupJS();
			var data={
				leaderFlg : true
			}
			staff.ShowModal('checkbox',data, function(data) {
				if (data.length > 0) {
					var userIds = data.map(function(x){return x[0].userId}).join(',');
					var userNames = data.map(function(x){return x[0].userName}).join(',');
					$('[name="approval"]').val(userNames);
					$('[name="approval"]').attr("title",userNames);
					$('[name="approvalID"]').val(userIds);
				}
			});
		});

		//返回按钮
		$('#ret').on('click',function(){
			Globals.utilJS.ReturnHomePage();
		})

	}

	function formSubmit() {
		// ajax表单提交

		// 获取文档类型
		var itemVal = $("#docType").val();

		// 获取左侧显示项目
		var docItemCode1 = [], docItemNo1 = [], isSelect1 = [], row = [], colu = [],isFromToItem=[];
		$("#sort2").find(".sort-item").each(function(index) {
			docItemCode1.push($(this).find("input[name='documentItemCode']").val());
			docItemNo1.push($(this).find("input[name='documentItemNo']").val());
			isFromToItem.push($(this).find("input[name='isFromToItem']").val());
			if($(this).find("input[name='checkbox']").is(':checked')){
				isSelect1.push("1");
			}else{
				isSelect1.push("2")
			}
			row.push(index + 1);//行
			colu.push("1");//列
		})

		// 获取右侧显示项目
		$("#sort3").find(".sort-item").each(function(index) {
			docItemCode1.push($(this).find("input[name='documentItemCode']").val());
			docItemNo1.push($(this).find("input[name='documentItemNo']").val());
			isFromToItem.push($(this).find("input[name='isFromToItem']").val());
			if($(this).find("input[name='checkbox']").is(':checked')){
				isSelect1.push("1");
			}else{
				isSelect1.push("2")
			}
			row.push(index + 1);//行
			colu.push("2");//列
		})

		// 获取审核人
		var lastApprovalUserId = $('[name="approvalID"]').val().split(',');

		// 设置ajax数据
		data = {
			documentType : itemVal,
			documentItemCode : docItemCode1,
			documentItemNo : docItemNo1,
			isFromToItem:isFromToItem,
			layoutRow : row,
			layoutCol : colu,
			inputRequire : isSelect1,
			lastApprovalUserId:lastApprovalUserId
		};

		// ajax请求
		var ajaxOptions = {
			type : "post",
			url : 'docmain/enterform',// 地址
			contentType : "application/json",
			data : JSON.stringify(data),
			success : function(data) {
				if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
					if(data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()){
						Globals.msgboxJS.ToastrSuccess(ConstMsg.HTML_MSG_DOC_ENTER_FORM_EDIT_SUCCESS());
					}else if(data.data.bizCode == ConstCode.BIZ_CODE_DOC_DATA_ITEM_ALREADY_EXISTED()){
						Globals.msgboxJS.ToastrError(ConstMsg.HTML_MSG_DOC_CANNOT_DELETE());
					}
				}else{
					Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
				}
			} // 请求成功时处理
		};

		_this.sendAjax(ajaxOptions);
	}
	;

	// 构造方法
	function constructor() {
		_this = this;
		this.InitPage = _initPage;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.docEnterFormDefineJS.prototype = new Globals.comAjaxJS();