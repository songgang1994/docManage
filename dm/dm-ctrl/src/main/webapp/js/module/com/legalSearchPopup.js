/**
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：邵林军 --%>
 * <%-- 模块名：法人检索POP --%>
 */
Namespace.register("Globals.legalSearchPopupJS");

Globals.legalSearchPopupJS = (function() {

	var _this = null;
	var dt = $('#legalSearchTable');
	var isRadio = true;
	var onOkCallBack = function(){};
	var checkedIds= [];//保存选中的id，亦适用于分页
	var data = [];
	// 初始化人员检索pop
	function _InitLegalSearchPopup() {
		// 初始化datatable
		_InitDataTable();

		// 按键监听
		_ButtonClickListener();

		// 定义关闭模态框时执行的操作
		_InitHide();

		//checkbox点击事件
		$('#legalSearchTable tbody').on('click', 'input:checkbox', function() {
			//获取行
			var tr = $(this).closest('tr');
			//当checkbox选中的场合
		    if($(this).is(":checked")){
		    	//添加数据
		   	 	checkedIds.push($(this).attr("value"));
		   	 	data.push( dt.DataTable().rows(tr).data())
		     }else{
		    	 //移除数据
			    for(var i=0; i<checkedIds.length; i++){
			            if($(this).attr("value") == checkedIds[i]){
			            checkedIds.splice(i, 1);
			            data.splice(i,1);
			            }
			        }
		     }
		 });
	}

	// 初始化datatable
	function _InitDataTable() {
		dt.DataTable({
			ajax : {
				url : "com/legalSearch",
				dataSrc : "listData",
				data : {
					legalName : $("#legalName").val(),
				},
				error:function(){
				}
			},
			destroy:true,//解决重新加载表格内容问题
			serverSide: true,//开启服务器模式
			searching : false,// 不显示搜索器
			ordering : false,// 不可排序
			autoWidth:false,
			lengthMenu : [  [ 5, 15, 20, -1 ], [ 5, 15, 20, "全部" ] ],// 选择每页显示多少条
			pageLength : 5,// 每页显示多少条
			pagingType : "bootstrap_full_number",// 分页按钮显示选项
			language : {// 汉化
				lengthMenu: "_MENU_",
				info : "第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				emptyTable : "无可用数据",
				infoEmpty: "没有记录可以显示",
				loadingRecords: "请等待，数据正在加载中......",
				paginate : {
					processing: "正在处理中。。。",
					previous : "上一页",
					next : "下一页",
					last : "最后一页",
					first : "第一页"
				}
			},
			dom: 't<lp<"col-xs-12 text-right" i>><"clear">',
			columns : [ {
				bVisible : false,
				data : "value",
			}, {
				bVisible : true,
				title : "选择",
				width : "20%",
				className:"text-center",
				data : "value",
				render : function(data) {
					return '<div class="text-center"><input type="radio" value=' + data + '></div>';
				}
			}, {
				bVisible : false,
				title : "选择",
				width : "20%",
				className:"text-center",
				data : "value",
				render : function(data) {
					return '<div class="text-center"><input type="checkbox" value=' + data + '></div>';
				}
			}, {
				title : "<div class='text-center' style='vertical-align: middle;'>法人ID</div>",
				width : "40%",
				data : "value",
				render : function(data) {
					return '<div style="text-align:left;" class="overflow-custom" title="'+data+'">' + data + '</div>';
				}
			}, {
				title : "<div class='text-center' style='vertical-align: middle;'>法人</div>",
				width : "40%",
				data : "dispName",
				render : function(data) {
					return '<div style="text-align:left;" class="overflow-custom" title="'+data+'">' + data + '</div>';
				}
			} ],
			"fnDrawCallback": function () {
				setChecked();
	        }
		});

		$('#legalSearchTable tbody').on('click', 'td', function() {
			 var tr = $(this).closest('tr');
			 //获取点击td
		     var clickTd = $(this).parents("tr").find("td").index($(this));
			if (isRadio) {
				// 单选
				dt.DataTable().$('tr').find(":radio").prop('checked', false);
				$(this).parents("tr").addClass('selected');
				$(this).parents("tr").find(":radio").prop('checked', true);
				//先清空，在保存单选值
				checkedIds.length= 0;//清空Id
				data.length= 0;//清空传值
				checkedIds.push($(this).parents("tr").find(":radio").attr("value"));
		        data.push( dt.DataTable().rows(tr).data())
			} else {
				// 多选
				if(clickTd != 0 && clickTd != -1){
					if ($(this).parents("tr").find(":checkbox")[0].checked) {
						$(this).parents("tr").find(":checkbox").prop('checked', false);
						for(var i=0; i<checkedIds.length; i++){
				             if($(this).parents("tr").find(":checkbox").attr("value") == checkedIds[i]){
				             checkedIds.splice(i, 1);
				             data.splice(i,1);
				             }
				         }
					} else {
						$(this).parents("tr").find(":checkbox").prop('checked', true);
						 checkedIds.push($(this).parents("tr").find(":checkbox").attr("value"));
				         data.push( dt.DataTable().rows(tr).data())
					}
				}
			}
		});

		//查询无结果时 隐藏dt
		dt.on('xhr.dt', function ( e, settings, json, xhr ) {
			if(json != null && json.listData !=null){
				if(json.listData.length == 0){
					$("#legalSearchTableDiv").hide();
					$("#noLegalResult").show();
					$("#legalConfirm").hide();
				}else{
					$("#legalSearchTableDiv").show();
					$("#noLegalResult").hide();
					$("#legalConfirm").show();
				}
			}
	    } );

	}

	// 按键监听
	function _ButtonClickListener() {
		// 监听按钮按钮点击事件
		$("button").on("click", function(e) {
			e.preventDefault();
			e.stopPropagation();

			switch (e.target.id) {
			case "searchLegal":
				_DataTableAjax();
				break;
			case "legalConfirm":
				_OnConfirm();
				break;
			case "legalClose":
				$("#legalSearchPopup").modal('hide');
				break;
			default:
				break;
			}
		});
	}

	// 初始化 模态框隐藏时触执行的的方法
	function _InitHide() {
		$("#legalSearchPopup").on("hide.bs.modal", function() {
			// 搜索值清空
			$('#legalSearchForm')[0].reset();
			// 清除表格数据
			dt.DataTable().clear().draw();
			//清除msg
			Globals.msgboxJS.ToastrClear();
		})
	}

	// 根据Flag 显示多选或单选按钮
	function _ShowBox(flag) {
		isRadio = flag;
		if (isRadio) {
			dt.DataTable().column(1).visible(true);
			dt.DataTable().column(2).visible(false);
		} else {
			dt.DataTable().column(1).visible(false);
			dt.DataTable().column(2).visible(true);
		}
	}

	// dataTable ajax请求后台
	function _DataTableAjax() {
		//更改搜索条件
		dt.DataTable().on(
				'preXhr.dt',
				function(e, settings, data) {
					data.legalName = $("#legalName").val()
				})
		//加载
		dt.DataTable().ajax.url('com/legalSearch').load();
	}

	// 点击确认按钮
	function _OnConfirm() {
		// 执行从父页面传来的方法
		if(data.length == 0){
			//如果没选择，就提示
			Globals.msgboxJS.ToastrWarning(ConstMsg.HTML_MSG_CHOOSE_LEGAL());
			return;
		}
		//这两个顺序不要乱
		$("#legalSearchPopup").modal('hide');
		onOkCallBack(data);
	}

	// ================对外方法==============================
	// 显示模态框
	function _ShowModal(flag,callback) {
		 checkedIds.length= 0;//清空Id
		 data.length= 0;//清空传值
		//保存点击确认后要执行的自定义函数
		onOkCallBack = callback;
		//判断多选还是单选
		if (flag !=null && flag.toLowerCase() === 'checkbox') {
			_ShowBox(false);
		} else {
			_ShowBox(true);
		}
		//清空dataTable选中项
		setChecked();
		$("#legalSearchPopup").modal('show');
	}

	//翻页后设置是否选中
    function setChecked(){
       var $boxes = $("#legalSearchTable input");
       for(var i=0;i<$boxes.length;i++){
           var value = $boxes[i].value;
           if(checkedIds!=null&&checkedIds.length!=0){
        	   if(checkedIds.indexOf(value,0)!=-1){
                   $boxes[i].checked = true;
               }else{
                   $boxes[i].checked = false;
               }
           } else {
        	   $boxes[i].checked = false;
           }
       }
    }
	// ========================================================

	// 构造方法
	function constructor() {
		_this = this;
		this.InitLegalSearchPopup = _InitLegalSearchPopup;
		this.ShowModal = _ShowModal;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.legalSearchPopupJS.prototype = new Globals.comAjaxJS();