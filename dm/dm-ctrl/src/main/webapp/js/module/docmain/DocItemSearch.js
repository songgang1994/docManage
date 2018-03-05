Namespace.register("Globals.deviceListJS");

Globals.docItemSearchJS = (function() {
	// ========================================================
	// 私有属性区域
	//User类实例，回调函数中使用，不可直接用this
	var _this = null;
	var itemDataTable=null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	/**
	 * 初始化表单验证
	 */
	function initValidation() {
		$("#form-search").validate({
			errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
			errorElement: "span",
			rules: {
				docItemsName: { 			// 文档数据项名称
					required: false,
					maxlength: ConstText.HTML_MSG_LENGTH_ITEM()
				},
				docItemsType: {			// 文档数据项类型
					required: false
				}
		    },
			messages: {
				docItemName: { 			// 文档数据项名称
					required: "",
					maxlength: ConstMsg.HTML_MSG_LENGTH_ITEMNAME()
				},
				docItemType: {			// 文档数据项类型
					required: ""
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

	function _DocItemSearchList(pageLength) {
		itemDataTable=$("#tbl-item-search").dataTable({
			ordering : false,// 不可排序
			bDestroy:true,
			serverSide:true,//后台分页
			autoWidth:false,
			ajax : {
				type: "post",
				url : "docmain/searchList",
				dataSrc : "listData",
				data :{
					docItemsName:$("#docItemsName").val(),
					docItemsType:$("#docItemsType").val()
				},
				error: function(){
				}
			},
			"columns" : [{
				"title" : "数据项名称",
				"width" : "25%",
				"className":"text-center",
				"data" : "documentItemName",
				render : function(data) {
					return '<div style="text-align:left;" >' + data + '</div>';
				}
			}, {
				"title" : "数据源类型",
				"width" : "25%",
				"className":"text-center",
				"data" : "itemTypeDispName",
				 render : function(data) {
					return '<div style="text-align:left;" >' + data + '</div>';
				}
			}, {
				"title" : "数据源",
				"width" : "25%",
				"className":"text-center",
				"data" : "documentItemSourceName",
				 render : function(data, type, row, meta) {
					 if(data!=null&&data!=''){
						 display="block";
					 }else{
						 display="none";
					 }
					return '<div style="text-align:left;display:'+display+';">' + data + '<a style="float:right;" class="btn-detailOne" data-item-code="'+row.documentItemSourceCode+'" data-item-name="'+ data +'"  name="btn-detail-data">详细</a></div>';
				}
			}, {
				"title" : "操作",
				"width" : "25%",
				"className":"text-center",
				"data" : null
			} ],
			"aLengthMenu" : [ [ 5, 15, 20, -1 ], [ 5, 15, 20, "全部" ] ],
			"pageLength" : pageLength,
			"pagingType" : "bootstrap_full_number",
			"language" : {
				"lengthMenu" : "_MENU_ ",
				 "info": "第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				 "infoEmpty" : "没有记录可以显示",
				 "sEmptyTable": "无可用数据",
				"paginate" : {
					"previous" : "上一页",
					"next" : "下一页",
					"last" : "最后一页",
					"first" : "第一页"
				}
			},
			dom: 't<lp<"col-xs-12 text-right" i>><"clear">',
			"columnDefs" : [ {
				 "targets": [3],
			      render: function(data, type, row, meta) {
			    	  if(row.isFixItem == 1){
			    		  display1="block";
			    		  display2="none";
			    	  }else{
			    		  display1="none";
			    		  display2="block";
			    	  }
			            return '<div style="text-align:left;display:'+display1+';"><a data-item-code="'+row.documentItemCode+'" name="btn-detail-item" class="btn-detail">详细</a></div>'+
            					   '<div  style="text-align:left;display:'+display2+';"><a data-item-code="'+row.documentItemCode+'" data-item-no="'+row.documentItemNo+'" name="doc-btn-edit" class="btn-edit">修改&nbsp</a>'+
            					   '<a data-item-code="'+row.documentItemCode+'" name="doc-btn-delete" class="btn-delete">删除</a></div>';
			      }
			} ]
		});
		//查询无结果时 隐藏
		$('#tbl-item-search').on('xhr.dt', function ( e, settings, json, xhr ) {
			if(json != null && json.listData !=null){
				if(json.listData.length == 0){
					$("#docItemTable").hide();
					$("#nodocItemResult").show();
				}else{
					$("#docItemTable").show();
					$("#nodocItemResult").hide();
				}
			}
	    } );
	}

	// ========================================================
	// Ajax请求函数定义区域
	// ========================================================
	// 检索设备一览信息
	function _initDocItemSearch() {

		// 初始化表单验证
		initValidation();

		// 表单查询按钮事件绑定
		$("#btn-query").click(function(){
			//check表单验证
			if ($("#form-search").valid() == false) {
				return;
			}
			//加载datatable
			var pageLength = Globals.utilJS.getCookie(ConstText.ITEM_PAGE_LENGTH());
			_DocItemSearchList(pageLength);
			itemDataTable.load();
		})
		// 设备删除按钮事件绑定
		$("#tbl-item-search").on("click", ".btn-delete", function() {
			var itemCode = $(this).data('item-code');
			var deleteName = $(this).parent().parent().prev().prev().prev().children().text();
			var user = ConstMsg.BIZ_CODE_SHUR_DELETE()+deleteName+"?";
			var me = $(this).parent().parent().parent()
	        Globals.msgboxJS.Confirm(user,function(){
	        	 //调用ajax
				var  ajaxOptions = {
							async:false,
				            url:"docmain/delete",
				            type:"post",
				            data:{
				            	docItemsCode: itemCode
				            },
							dataType: 'json',
				            success:function(data){
				            	_deleteSuccess(data,me)
				            }
				  };
				 var comAjax = new Globals.comAjaxJS();
				 comAjax.sendAjax(ajaxOptions);
	        },function(){
	          return;
	        })
		});

		$("#btn-add-new").click(function(){
			var docItemsName = $("#docItemsName").val();
			var itemId = $("#docItemsType").val();
			var itemName = $("#docItemsType").find("option:selected").text()
			event.preventDefault();
			location.href = path+"/docmain/itemedit?docItemsName="+encodeURI(encodeURI(docItemsName))+"&itemId="+itemId+"&itemName="+encodeURI(encodeURI(itemName));
		})

		// 设备编辑按钮事件绑定
		$("#tbl-item-search").on("click", ".btn-edit", function() {
			var docItemsName = $("#docItemsName").val();
			var itemId = $("#docItemsType").val();
			var itemName = $("#docItemsType").find("option:selected").text()
			event.preventDefault();
			location.href = path+"/docmain/itemupdate?documentItemCode=" + $(this).data('item-code')+"&docItemsName="+encodeURI(encodeURI(docItemsName))+"&itemId="+itemId+"&itemName="+encodeURI(encodeURI(itemName));
			return false;
		});

		$("#tbl-item-search").on("click", ".btn-detail", function() {
			event.preventDefault();
			location.href =path+ "/docmain/itemlook?documentItemCode=" + $(this).data('item-code');
			return false;
		});
		$("#tbl-item-search").on("click", ".btn-detailOne", function() {
			event.preventDefault();
			location.href = path+"/docmain/editDataSrca/"+$(this).data('item-code')+"?documentItemSourceName="+encodeURI(encodeURI($(this).data('item-name')));
			return false;
		});

		//返回事件
		$("#docItemCancel").bind("click",function(){
			Globals.utilJS.ReturnHomePage();
		});

		$("#btn-query").click();
	}


	// =====================================================
	// ===========================Msg区域========================================
	function _deleteSuccess(json,me) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
		    //该数据项已在文档录入表单定义表中使用，不能进行删除处理，报msg
			case ConstCode.BIZ_CODE_DELETE_DOCUMENT_ALREADY():
			    Globals.msgboxJS.ToastrError(json.data.bizExeMsgs[0])
				break;
			//该数据项已在文档一览检索数据项定义表中使用，不能进行删除处理
			case ConstCode.BIZ_CODE_DELETE_DOCUMENT_USE():
				Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_DELETE_DOCUMENT_USE())
				break;
			//删除成功
			case ConstCode.BIZ_CODE_DELETE_DOCUMENT_SUCCESS():
				Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DELETE_DOCUMENT_SUCCESS())
				$("#tbl-item-search").DataTable().ajax.reload();
				break;
			//删除失败
			case ConstCode.BIZ_CODE_DELETE_DOCUMENT_FAILED():
				Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_DELETE_DOCUMENT_FAILED())
				break;
		}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}





	// ==========================Msg区域结束=============================================


	// 构造方法
	function constructor() {
		_this = this;

		this.InitDocItemSearch = _initDocItemSearch;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.docItemSearchJS.prototype = new Globals.comAjaxJS();