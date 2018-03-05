/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：文档项目数据源js
 */
Namespace.register("Globals.userAjaxJS");

Globals.DocItemDataSrcListAjaxJS = (function() {
	// ========================================================
	// 私有属性区域
	//User类实例，回调函数中使用，不可直接用this
	var _this = null;
	var itemDataTable=null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	// ========================================================
	// Ajax请求函数定义区域
	// ========================================================
	// 课题一览画面初期化
	function _InitProject(){
		//绑定修改按钮
		$("#dataSrcList").on("click",".btn-updDataSrc",function(){
			var documentItemSourceCode =$(this).attr("documentItemSourceCode");
	 		var documentItemSourceName =$(this).closest("tr").children().children(".docName").text()
			_DataSrcEdit(documentItemSourceCode,documentItemSourceName);
		})
		//绑定增加按钮
		$("#dataSrcAdd").click(function(){
			 _AddSreEdit();
		})
		//返回事件
		$("#cancel").bind("click",function(){
			Globals.utilJS.ReturnHomePage();
		});

		//绑定删除按钮
		$("#dataSrcList").on("click",".btn-deleteDataSrc",function(){
				var documentItemSourceCode =$(this).attr("documentItemSourceCode");
		 		_DataSrcDelete(documentItemSourceCode,this);
		})
		// 表单查询按钮事件绑定
		$("#dataSrcBut").click(function(){
			//加载datatable
			var pageLength = Globals.utilJS.getCookie(ConstText.ITEMSRC_PAGE_LENGTH());
			_DocItemDataSrcList(pageLength);
			itemDataTable.load();
		})
		$("#dataSrcBut").click();
	}
	// ========================================================
	// ========================================================
	//点击修改按钮
	function _DataSrcEdit(documentItemSourceCode,documentItemSourceName){
		//跳转修改页面
		window.location.href = path+"/docmain/editDataSrc/"+documentItemSourceCode+"?documentItemSourceName="+encodeURI(encodeURI(documentItemSourceName));
	}
	//点击增加按钮
	function _AddSreEdit(){
		var documentItemSourceName = $("#documentItemSourceName").val()
		window.location.href =path+"/docmain/addDataSrc?documentItemSourceName=" + encodeURI(encodeURI(documentItemSourceName));
	}

	// ========================================================
	//点击删除按钮
	function _DataSrcDelete(documentItemSourceCode,me){
		var $tr = $(me).closest("tr");
		var deleteName = $(me).parent().parent().prev().prev().children().text();
		var user = ConstMsg.BIZ_CODE_SHUR_DELETE()+deleteName+"?";
		Globals.msgboxJS.Confirm(user,function(){
		var ajaxOptions = {
			type: "post",
			url : 'docmain/dataSrcListDelete',// 地址
			data : {
				documentItemSourceCode:documentItemSourceCode
			},
			success : function(data) {
				ajaxSuccess(data,$tr);
			} // 请求成功时处理
		};

		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
		},function(){
	          return;
	        })
	}

	// ========================================================
	// ajax回调
	function ajaxSuccess(json,$tr) {
		if(json.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
			//显示msg
			switch(json.data.bizCode){
				//删除成功的场合
				case ConstCode.BIZ_CODE_DELETE_SUCCESS():
					$('#dataSrcList').DataTable().row($tr).remove().draw( false );
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DELETE_SUCCESS());
					break;
				//数据源不允许删除的场合
				case ConstCode.BIZ_CODE_SRCCANT_DELETE():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_SRCCANT_DELETE());
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
	//课题一览列表处理
	function _DocItemDataSrcList(pageLength){
		var table = $('#dataSrcList');
		// begin first table
		itemDataTable=table.dataTable({
			ordering : false,// 不可排序
			bDestroy:true,
			serverSide:true,//后台分页
			autoWidth:false,
			ajax : {
				type: "post",
				url : "docmain/dataSrcList",
				dataSrc : "listData",
				data :{
					documentItemSourceName:$("#documentItemSourceName").val()
				},
				error: function(){
				}
			},
			"columns" : [ {
				"title" : "数据源名称",
				"width" : "30%",
				"className":"text-center",
				"data" : "documentItemSourceName",
				render : function(data) {
					return '<div style="text-align:left;" class="docName" >' + data + '</div>';
				}
			}, {
				"title" : "详细内容",
				"width" : "35%",
				"className":"text-center",
				"data" : "detail",
				render : function(data) {
					return ' <div style="text-align:left;" class="overflow-custom" title="'+data+'">'+data+'</div>';
				}
			}, {
				"title" : "操作",
				"width" : "35%",
				"className":"text-center",
				"data" : null
			} ],
			"aLengthMenu" : [ [ 5, 15, 20, -1 ], [ 5, 15, 20, "全部" ] // change per page values here
			],
			// set the initial value
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
				 "targets": [2],
				  render: function(data, type, row, meta) {
				        return '<div style="text-align:left;"><a name="btn-updDataSrc" class="btn-updDataSrc" documentItemSourceCode="'+row.documentItemSourceCode+'">修改&nbsp</a>'+
	            				   '<a  name="btn-deleteDataSrc" class="btn-deleteDataSrc" documentItemSourceCode="'+row.documentItemSourceCode+'">删除</a></div>';
				      }
			} ]
		});

		//查询无结果时 隐藏
		$('#dataSrcList').on('xhr.dt', function ( e, settings, json, xhr ) {
			if(json != null && json.listData !=null){
				if(json.listData.length == ConstText.HTML_MSG_NUM_ZERO()){
					$("#dataSrcListTable").hide();
					$("#noDataSrcResult").show();
				}else{
					$("#dataSrcListTable").show();
					$("#noDataSrcResult").hide();
				}
			}
	    } );
	}

	// ========================================================
	// 构造方法
	function constructor() {
		_this = this;

		this.InitProject = _InitProject;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.DocItemDataSrcListAjaxJS.prototype = new Globals.comAjaxJS();