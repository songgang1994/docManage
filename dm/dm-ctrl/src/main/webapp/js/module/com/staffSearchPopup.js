/**
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：邵林军 --%>
 * <%-- 模块名：人员检索POP --%>
 */
Namespace.register("Globals.staffSearchPopupJS");

Globals.staffSearchPopupJS = (function() {

	var _this = null;
	var dt = $('#staffSearchTable');
	var isRadio = true;
	var onOkCallBack = function(){};
	var deptMap={};
	var checkedIds= [];//保存选中的id，亦适用于分页
	var deptIdList=[];
	var staffNames=[];
	var data = [];
	// 初始化人员检索pop
	function _InitStaffSearchPopup() {
		// 初始化datatable
		_InitDataTable();

		// 按键监听
		_ButtonClickListener();

		//定义关闭模态框时执行的操作
		_InitHide();

		//获取所有部门的id和name,备用
		_GetAllDept();

		//部门pop监听事件
		_departInit();

		//组织结构目录树初始化
		_departManageList();

		//checkbox点击事件
		$('#staffSearchTable tbody').on('click', 'input:checkbox', function() {
			//获取行
			var tr = $(this).closest('tr');
			//当checkbox选中的场合
		    if($(this).is(":checked")){
		    	//添加数据
		   	 checkedIds.push($(this).attr("value"));
			 deptIdList.push(dt.DataTable().rows(tr).data()[0].deptId);
			 staffName = dt.DataTable().rows(tr).data()[0].userName +"["+dt.DataTable().rows(tr).data()[0].deptName+"]";
			 staffNames.push(staffName);
	         data.push( dt.DataTable().rows(tr).data())
		     }else{
		    	 //移除数据
		    	for(var i=0; i<checkedIds.length; i++){
			             if($(this).attr("value") == checkedIds[i]&&$(this).attr("deptId") == deptIdList[i]){
			             checkedIds.splice(i, 1);
			             data.splice(i,1);
			             deptIdList.splice(i,1);
			             staffNames.splice(i,1);
			            }
			        }
		     }
		 });
	}

	// 初始化datatable
	function _InitDataTable() {

		dt.DataTable({
			ajax : {
				url : "com/staffSearch",
				dataSrc : "listData",
				data : {
					deptIds :  $("#staffSearchPopup #deptIds").val(),
					leaderFlg : 0,
					staffId :  $("#staffId").val(),
					staffName : $("#staffName").val()
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
				data : "userId",
			}, {
				bVisible : true,
				title : "选择",
				width : "10%",
				className:"text-center",
				data : null
			}, {
				bVisible : false,
				title : "选择",
				width : "10%",
				className:"text-center",
				data : null
			}, {
				title : "<div class='text-center' style='vertical-align: middle;'>员工ID</div>",
				width : "25%",
				data : "userId",
				render : function(data) {
					return '<div style="text-align:left;" class="overflow-custom" title="'+data+'">' + data + '</div>';
				}
			}, {
				title : "<div class='text-center' style='vertical-align: middle;'>员工姓名</div>",
				width : "20%",
				data : "userName",
				render : function(data) {
					return '<div style="text-align:left;" class="overflow-custom" title="'+data+'">' + data + '</div>';
				}
			}, {
				title : "<div class='text-center' style='vertical-align: middle;'>所属部门</div>",
				width : "20%",
				data : "deptName",
				render : function(data) {
					return '<div style="text-align:left;" class="overflow-custom" title="'+data+'">' + data + '</div>';
				}
			}, {
				title : "<div class='text-center' style='vertical-align: middle;'>负责人</div>",
				width : "20%",
				data : "leaderFlg",
				render : function(data) {
					var leader = "否";
					if(data != null && (data=="1" || data==1)){
						leader = "是";
					}
					return leader;
				}
			} ],
			"columnDefs" : [ {
				 "targets": [1],
			      render: function(data, type, row, meta) {
			    	  return '<input type="radio" value=' + row.userId + ' deptId='+row.deptId+'>';
			      }
			},{
				 "targets": [2],
			      render: function(data, type, row, meta) {
			    	  return '<input type="checkbox" value=' + row.userId + ' deptId='+row.deptId+'>';
			      }
			} ],
			"fnDrawCallback": function () {
				setChecked();
	        }
		});

		$('#staffSearchTable tbody').on('click', 'td', function() {
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
				staffNames.length = 0;
				checkedIds.push($(this).parents("tr").find(":radio").attr("value"));
				deptIdList.push(dt.DataTable().rows(tr).data()[0].deptId);
				staffName = dt.DataTable().rows(tr).data()[0].userName +"["+dt.DataTable().rows(tr).data()[0].deptName+"]";
				staffNames.push(staffName);
		        data.push( dt.DataTable().rows(tr).data())
			} else {
				if(clickTd != 0){
				// 多选
				if ($(this).parents("tr").find(":checkbox")[0].checked) {
					$(this).parents("tr").removeClass('selected');
					$(this).parents("tr").find(":checkbox").prop('checked', false);
					for(var i=0; i<checkedIds.length; i++){
			             if($(this).parents("tr").find(":checkbox").attr("value") == checkedIds[i]&&$(this).parents("tr").find(":checkbox").attr("deptId") == deptIdList[i]){
			             checkedIds.splice(i, 1);
			             data.splice(i,1);
			             deptIdList.splice(i,1);
			             staffNames.splice(i,1);
			             }
			         }
				} else {
					$(this).parents("tr").addClass('selected');
					$(this).parents("tr").find(":checkbox").prop('checked', true);
					 checkedIds.push($(this).parents("tr").find(":checkbox").attr("value"));
					 deptIdList.push(dt.DataTable().rows(tr).data()[0].deptId);
					 staffName = dt.DataTable().rows(tr).data()[0].userName +"["+dt.DataTable().rows(tr).data()[0].deptName+"]";
					 staffNames.push(staffName);
			         data.push( dt.DataTable().rows(tr).data())
				}
				}
			}
			$("#staffText").val(staffNames);
		});

		//查询无结果时 隐藏dt
		dt.on('xhr.dt', function ( e, settings, json, xhr ) {
			if(json != null && json.listData !=null){
				if(json.listData.length == 0){
					$("#staffSearchTableDiv").hide();
					$("#noStaffResult").show();
					$("#staffConfirm").hide();
				}else{
					$("#staffSearchTableDiv").show();
					$("#noStaffResult").hide();
					$("#staffConfirm").show();
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
			case "searchStaff":
				_DataTableAjax();
				break;
			case "staffConfirm":
				_OnConfirm();
				break;
			case "staffClose":
				$("#staffSearchPopup").modal('hide');
				break;
			default:
				break;
			}
		});
	}

	// 初始化 模态框隐藏时触执行的的方法
	function _InitHide() {
		$("#staffSearchPopup").on("hide.bs.modal", function() {
			// 搜索值清空
			$('#staffSearchForm')[0].reset();
			// 清除表格数据
			dt.DataTable().clear().draw();
			//清除msg
			Globals.msgboxJS.ToastrClear();
		})
	}

	//获取所有部门的id和names
	function _GetAllDept(){
		//准备请求
		var ajaxOptions = {
			type: "post",
			url : 'com/getAllDept',// 地址
			success: function(json) {
				 if(json.rtnCode == "100"){
					var d = json.listData;
					for(var i=0;i<d.length;i++){
						deptMap[d[i].deptId] = d[i].deptName;
					}
				 }
			},
			error: function(){
			}
		};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
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
					data.deptIds = $("#staffSearchPopup #deptIds").val();
					if( $("#leaderFlg").is(':checked')){
						data.leaderFlg = 1
					}else{
						data.leaderFlg = 0
					};
					data.staffId = $("#staffId").val();
					data.staffName = $("#staffName").val();
				})
		//加载
		dt.DataTable().ajax.url('com/staffSearch').load();
	}

	// 点击确认按钮
	function _OnConfirm() {
		// 执行从父页面传来的方法
		if(data.length == 0){
			//如果没选择，就提示
			Globals.msgboxJS.ToastrWarning(ConstMsg.HTML_MSG_CHOOSE_STAFF());
			return;
		}
		//这两个顺序不要乱
		$("#staffSearchPopup").modal('hide');
		onOkCallBack(data);
	}

	// ================对外方法==============================
	// 显示模态框
	function _ShowModal(flag,param,callback) {
		 checkedIds.length= 0;//清空Id
		 data.length= 0;//清空传值
		 deptIdList.length=0;
		 staffNames.length=0;
		//判断多选还是单选
		if (flag !=null && flag.toLowerCase() === 'checkbox') {
			_ShowBox(false);
		} else {
			_ShowBox(true);
		}

		//判断有没有传参数
		if( typeof(param) != "undefined"){
			if(typeof(param) == "object"){
				var needRelod = false;
				//判断是否有部门对象传过来
				if("deptIds" in param && param.deptIds instanceof Array && param.deptIds.length > 0){
					//数组去重
					var deptIds = Globals.utilJS.ArrayUnique(param.deptIds);

					//通过id获得name
					var deptNames = [];
					for(var i=0;i<deptIds.length;i++){
						deptNames.push(deptMap[deptIds[i]]);
					}
					//将参数赋值
					$("#deptIds").val(deptIds);
					$("#deptIdNames").val(deptNames);
					needRelod = true;
				}
				//判断是否是领导
				if("leaderFlg" in param && typeof(param.leaderFlg) == "boolean"){
					if(param.leaderFlg){
						//打钩
						$("#leaderFlg").attr("checked", true);
						needRelod = true;
					}
				}
				if(needRelod){
					//重新请求
					_DataTableAjax();
				}
			}else if(typeof(param) == 'function'){
				//保存点击确认后要执行的自定义函数
				onOkCallBack = param;
			}
		}

		if( typeof(callback) != "undefined"){
			if(typeof(callback) == 'function'){
				//保存点击确认后要执行的自定义函数
				onOkCallBack = callback;
			}
		}
		//清空dataTable选中项
		setChecked();
		$("#staffSearchPopup").modal('show');
	}

	//所属部门初期化处理
	function _departInit(){
		$("#popDeptSearch").click(function(){
			var depart = new Globals.departSearchPopupJS();
			depart.ShowModal('checkbox',function(data){
				var deptId;
				var deptNames;
				for(var i=0;i<data.length;i++){
					if(i==0){
						deptId = data[i][0].deptId;
						deptNames = data[i][0].deptName;
					}else{
						deptId = deptId+","+data[i][0].deptId;
						deptNames = deptNames+"," +data[i][0].deptName;
					}
				}
				$("#deptIds").val(deptId);
				$("#deptIdNames").val(deptNames);
				$("#deptIdNames").attr("title",deptNames);
			});
		})
	}

	//组织结构目录树初始化
	function _departManageList(){
		var ajaxOptions = {
				type: "post",
				url : 'system/departmanageTree',// 地址
				success : function(result) {
					// 请求成功时处理
					if(result.listData!=null&&result.rtnCode==ConstCode.BIZ_CODE_SUCCESS()){
						//申明node对象
					    function Node(depart) {
					      this.id = '';
					      this.text = '';
					      this.state = [];
					      this.depart = depart;
					      this.children = [];
					    }
					    //创建节点
					    var popNodeList = [];
					    for(var i=0;i<result.listData.length;i++){
					    	popNodeList.push(new Node(result.listData[i]));
					    }
					    //循环创建根节点
					    var rootList = [];
					    for(var i=0;i<popNodeList.length;i++){
					    	  //当departId为0时，根节点名称为公司名称
						      if (popNodeList[i].depart.parentDeptId == null) {
						        rootList.push(popNodeList[i]);
						        popNodeList[i].text = popNodeList[i].depart.deptName;
						        popNodeList[i].state.opened = true;
						      }
					    }
					    //获取根部门下的下属部门
					    for(var j=0;j<popNodeList.length;j++){
					    	 for (let i = 0; i < popNodeList.length; i++) {
							        //当部门id等于部门parentDepartId时；
							        if ((popNodeList[j].depart.deptId == popNodeList[i].depart.parentDeptId)&&popNodeList[i].depart.parentDeptId!=null) {
							        	popNodeList[j].children.push(popNodeList[i]);
							        	popNodeList[i].id = popNodeList[i].depart.deptId;
							        	popNodeList[i].text = popNodeList[i].depart.deptName;
							        }
							      }
					    }
						_departManageTree(rootList);
					}else{
						Globals.msgboxJS.ToastrWarning(constMsg.PROJECTNO_REPEAT());
					}
				},
				error: function(){
				}
			};

		_this.sendAjax(ajaxOptions);
	}
	//组织结构目录树生成
	function _departManageTree(rootList){
		 $('#popJstree_demo').jstree({
		      //规定树形样式和参数
		      "core": {
		        "animation": 200,
		        "check_callback": true,
		        "strings" : {
		           'New node': "新增组织"
		         },
		        "themes": {
		          "stripes": true
		        },
		        'data': rootList
		      },
		      "plugins": ["types"]
		    });
		 //节点点击事件
		    $("#popJstree_demo").on("changed.jstree", function(e, data) {
		      //当点击节点存在的场合
		      if (data.selected.length > 0) {
		        //传递点击节点的departId
		        var departId=data.selected[0];
		        var parentId=data.node.parent;
		        if(parentId==ConstText.ITEM_TYPE_POP_STAFF_MULT()){
		        	departId='';
		        }
		    	//更改搜索条件
				dt.DataTable().on(
						'preXhr.dt',
						function(e, settings, data) {
							data.deptIds = departId;
							data.leaderFlg=0;
							data.staffId="";
							data.staffName="";
						})
				//加载
				dt.DataTable().ajax.url('com/staffSearch').load();
		      } //输出点击的内容

		    });
	}
	//翻页后设置是否选中
    function setChecked(){
       var $boxes = $("#staffSearchTable input");
       for(var i=0;i<$boxes.length;i++){
           var value = $boxes[i].value;
           var deptId = $($boxes[i]).attr("deptId");
           if(checkedIds!=null&&checkedIds.length!=0&&deptIdList!=null&&deptIdList.length!=0){
        	   if(checkedIds.indexOf(value,0)!=-1&&deptIdList.indexOf(deptId,0)!=-1){
                   $boxes[i].checked = true;
               }else{
                   $boxes[i].checked = false;
               }
           } else {
        	   $boxes[i].checked = false;
           }
       }
       if(staffNames==null||staffNames.length==0){
    	   $("#staffText").val(staffNames);
       }
    }
	// ========================================================

	// 构造方法
	function constructor() {
		_this = this;
		this.InitStaffSearchPopup = _InitStaffSearchPopup;
		this.ShowModal = _ShowModal;

	}

	return constructor;
})();

// 继承公共AJAX类
Globals.staffSearchPopupJS.prototype = new Globals.comAjaxJS();