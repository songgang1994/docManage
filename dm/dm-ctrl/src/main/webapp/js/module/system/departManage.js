/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：组织结构维护
 */
Namespace.register("Globals.departManageJS");

Globals.departManageJS = (function() {
	var _this = null;
	var departNodeId='';
	function _departManageInit(){
		//组织结构目录树初始化
		_departManageList();
		//数据保存初始化
		$("#deptMentSubmit").bind("click", function(){
			_departManageSubmit();
		});
		//表单初始化
		_departManageForm();
		//部门负责人popup画面初始化
		_departLeader();
		//返回按钮
		$("#deptMentCancel").bind("click", function(){
			 var deptMentCancel = ConstMsg.CONFIRM_BACK();
	            Globals.msgboxJS.Confirm(deptMentCancel,function(){
	            	Globals.utilJS.ReturnHomePage();
	            },function(){
	              return;
	           })
		});
	}
	//部门负责人初始化
	function _departLeader(){
		$("#leaderSearch").click(function(){
			var person = new Globals.staffSearchPopupJS();
			person.ShowModal('checkbox',function(data){
				var userIds;
				var userNames;
				var userList=[];
				//画面设置选中人员Id和name
				for(var i=0;i<data.length;i++){
					if(i==0){
						userIds = data[i][0].userId;
						userNames = data[i][0].userName;
					}else{
						userList = userIds.split(",");
						if(userList.indexOf(data[i][0].userId,0)==-1){
							userIds = userIds+","+data[i][0].userId;
							userNames = userNames+" " +data[i][0].userName;
						}
					}
				}
				$("#userId").val(userIds);
				$("#userName").val(userNames);
				$("#userName").attr("title",userNames);
			});
		})
	}
	//组织结构目录树初始化
	function _departManageList(){
		var ajaxOptions = {
				type: "post",
				url :'system/departmanageTree',// 地址
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
					    var nodeList = [];
					    for(var i=0;i<result.listData.length;i++){
					    	nodeList.push(new Node(result.listData[i]));
					    }
					    //循环创建根节点
					    let rootList = [];
					    for(var i=0;i<nodeList.length;i++){
					    	  //当departId为0时，根节点名称为公司名称
						      if (nodeList[i].depart.parentDeptId == null) {
						        rootList.push(nodeList[i]);
						        nodeList[i].id = nodeList[i].depart.deptId;
						        nodeList[i].text = nodeList[i].depart.deptName;
						        nodeList[i].state.opened = true;
						      }
					    }
					    //获取根部门下的下属部门
					    for(var j=0;j<nodeList.length;j++){
					    	 for (let i = 0; i < nodeList.length; i++) {
							        //当部门id等于部门parentDepartId时；
							        if ((nodeList[j].depart.deptId == nodeList[i].depart.parentDeptId)&&nodeList[i].depart.parentDeptId!=null) {
							        	nodeList[j].children.push(nodeList[i]);
							          nodeList[i].id = nodeList[i].depart.deptId;
							          nodeList[i].text = nodeList[i].depart.deptName;
							        }
							      }
					    }
						_departManageTree(rootList);
					}else{
						Globals.msgboxJS.ToastrError(constMsg.HTML_MSG_ERROR_SYSTEM());
					}
				},
				error: function(){
				}
			};

		_this.sendAjax(ajaxOptions);
	}
	//组织结构目录树生成
	function _departManageTree(rootList){
		 $('#jstree_demo').jstree({
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
		      'contextmenu': {
		          "items": {
		            "Create": {
		              "label": "添加下级",
		              "action": function(data) {
		            	   var ref = $('#jstree_demo').jstree(true),
			                    sel = ref.get_selected();
			                  if (!sel.length) {
			                    return false;
			                  }
			                  var nodeId = sel[0];
		            	  NodeAdd(ref,nodeId);
		              }
		            },
		            "Edit": {
		              "label": "修改",
		              "action": function(data) {
		                  var ref = $('#jstree_demo').jstree(true),
		                    sel = ref.get_selected();
		                  if (!sel.length) {
		                    return false;
		                  }
		                  var nodeId = sel[0];
		                  NodeEdit(ref,nodeId);
		                }
		            },
		            "delete": {
			              "label": "删除",
			              "action": function(data) {
			                  var ref = $('#jstree_demo').jstree(true),
			                    sel = ref.get_selected();
			                  if (!sel.length) {
			                    return false;
			                  }
			                  var nodeId = sel[0];
			                  //弹出删除提示框
			                  var departName = ConstMsg.HTML_MSG_DEPARTMANANGE_DELETE();
			                  Globals.msgboxJS.Confirm(departName,function(){
			                	  NodeDelete(ref,nodeId);
			                  },function(){
			                    return;
			                  })
			                }
			            }
		          }
		        },
		      "plugins": ["types","contextmenu"]
		    });
		 	//新增节点
			function NodeAdd(ref,nodeId){
				//清空父节点存储input
				_refreshForm();
				//获取上级名称
				var deptParentName = ref.get_node(nodeId).text;
				$("#deptParentName").text(deptParentName);
				//获取随机部门Id
				var deptId = randomWord(true, 15, 15);
				$("#deptId").val(deptId);
				//获取父节点Id
				var parentId = $('#jstree_demo').jstree(true).get_node(nodeId).parent
				departNodeId = nodeId;
				if(parentId!="#"){
					$("#parentDeptId").attr("value",nodeId);
				}
				$("#deptId").attr("readOnly",false);
				$("#departdetail").show();
			}
			//修改节点
			function NodeEdit(ref,nodeId){
				//清空父节点存储input
				_refreshForm();
				//获取上级名称
				$("#departdetail").show();
				var parentId = ref.get_node(nodeId).parent;
				var deptParentName = ref.get_node(parentId).text;
				$("#deptParentName").text(deptParentName);
					 ajaxOptions = {
								type: "post",
								url : 'system/departDetail/'+nodeId,// 地址
								success : function(result) {
									_cb_DepartDetail_successFun(result);
								}, // 请求成功时处理
								error: function(){
								}
							};
					 _this.sendAjax(ajaxOptions);
			}
		 //删除部门
		 function NodeDelete(ref,nodeId){
			 ajaxOptions = {
						type: "post",
						url : 'system/departDelete/'+nodeId,// 地址
						success : function(result) {
							_cb_DepartDelete_successFun(result);
						}, // 请求成功时处理
						error: function(){
						}
					};
			 _this.sendAjax(ajaxOptions);
		 }

	}
	//部门删除执行成功的场合
	function _cb_DepartDelete_successFun(result){
		if(result.data!=null){
			if(result.data.bizCode==ConstCode.BIZ_CODE_SUCCESS()){
				Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DELETE_SUCCESS());
				$('#jstree_demo').jstree(true).delete_node(result.data.deptId);
			}else{
				Globals.msgboxJS.ToastrWarning(ConstMsg.DEPARTMANAGE_UNABLE_DELETE());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
		}
	}
	//修改执行成功的场合
	function _cb_DepartDetail_successFun(result){
		if(result.data!=null){
			if(result.data.bizCode==ConstCode.BIZ_CODE_SUCCESS()){
				$("#parentDeptId").val(result.data.parentDeptId);
				$("#departManageFlag").val(result.data.departManageFlag);
				$("#deptId").val(result.data.deptId);
				$("#deptId").attr("readOnly",true);
				$("#userName").val(result.data.userName);
				$("#userName").attr("title",result.data.userName);
				$("#userId").val(result.data.userId);
				$("#deptName").val(result.data.deptName);
			}else{
				Globals.msgboxJS.ToastrWarning(ConstMsg.DEPART_UNEXIST());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.HTML_MSG_ERROR_SYSTEM());
		}
	}
	//表单验证
	function _departManageForm(){
		$('#departManageForm').validate({
    	    errorElement: 'span', //default input error message container
    	    errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
    	    rules:
    	    {
	        	userName:
	        	{
	        		required: true
	        	},
	        	deptName:
	        	{
	        		required: true,
	        		maxlength:ConstText.HTML_MSG_NUM_OHB()
	        	}
    	    },
        messages:
        {
	    	userName:
        	{
	   		 required: ConstMsg.HTML_MSG_CHOOSE_DEPTPRESON()
    		},
    		deptName:
        	{
        		required: ConstMsg.HTML_MSG_MUSTINPUT_DEPTNAME(),
        		maxlength:ConstMsg.HTML_MSG_INPUTEXCEED_HUNDRED()
        	}
        },
        invalidHandler: function (event, validator)
        {
        },
		errorPlacement: function(error, element) {
		    if(element.attr("name") == "userName" ) {
		    	error = $("<p />").append(error);
		    	error.addClass('form-group');
		    	error.addClass('has-error');
		    	error.insertAfter("#leaderSearch");
		    } else {
		      error.insertAfter(element);
		    }
		},
        highlight: function (element)
        {
            $(element)
                .closest('.form-group').addClass('has-error'); // set error class to the control group
        },

        success: function (label)
        {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        }
    	});
	}
	//表单提交
	function _departManageSubmit(){
		if ($("#departManageForm").valid() == false) {
			return;
		}
		var departFlag = $("#departManageFlag").val();
		var ajaxOptions;
		if(departFlag!="upd"){
			 ajaxOptions = {
					type: "post",
					url : 'system/departAdd',// 地址
					contentType: "application/json",
					data :JSON.stringify($('#departManageForm').serializeJSON()),
					success : function(result) {
						_cb_addDepart_successFun(result);
					}, // 请求成功时处理
					error: function(){
					}
				};
		}	else	{
			 ajaxOptions = {
						type: "post",
						url : 'system/departUpd',// 地址
						contentType: "application/json",
						data :JSON.stringify($('#departManageForm').serializeJSON()),
						success : function(result) {
							_cb_updDepart_successFun(result);
						}, // 请求成功时处理
						error: function(){
						}
					};
		}
		_this.sendAjax(ajaxOptions);
	}
	//新增成功的场合
	function _cb_addDepart_successFun(result){
		if(result.data!=null){
			if(result.data.bizCode==ConstCode.BIZ_CODE_SUCCESS()){
				var parentId = departNodeId;
				var deptId = result.data.deptId;
				var deptName = result.data.deptName;
				 $("#jstree_demo").jstree("create_node", parentId,  {"id":deptId,"text":deptName}, "last", false, true);
				 departNodeId='';
				 Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_INSERT_SUCCESS());
			} else {
				Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_INSERT_FAILED());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_INSERT_FAILED());
		}
	}
	//修改操作成功的场合
	function _cb_updDepart_successFun(result){
		if(result.data!=null){
			if(result.data.bizCode==ConstCode.BIZ_CODE_SUCCESS()){
				var departId =  result.data.deptId;
				var deptName = result.data.deptName;
				 $("#jstree_demo").jstree("rename_node",departId,deptName);
				 Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS());
			} else {
				Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED());
		}
	}
	//刷新Form表单
	function _refreshForm(){
		$(':input','#departManageForm')
		 .not(':button, :submit, :reset, :hidden')
		 .val('')
		 .removeAttr('checked')
		 .removeAttr('selected');
		$("#parentDeptId").val("");
		$("#departManageFlag").val("");
		$("#departManageForm").validate().resetForm();
	}
	//随机字符串生成
	function randomWord(randomFlag, min, max){
	    var str = "",
	        range = min,
	        arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

	    // 随机产生
	    if(randomFlag){
	        range = Math.round(Math.random() * (max-min)) + min;
	    }
	    for(var i=0; i<range; i++){
	        pos = Math.round(Math.random() * (arr.length-1));
	        str += arr[pos];
	    }
	    return str;
	}
	// 构造方法
	function constructor() {
		_this = this;
        this.departManageInit= _departManageInit;
	}
	return constructor;
})();

//继承公共AJAX类
Globals.departManageJS.prototype = new Globals.comAjaxJS();