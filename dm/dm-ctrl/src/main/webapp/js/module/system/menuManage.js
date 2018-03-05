/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：系统菜单管理
 */
Namespace.register("Globals.menuManageJS");

Globals.menuManageJS = (function() {
	var _this = null;
	var departNodeId='';
	var oldParentId=null;//节点原父节点
	var oldOrderId=null;//节点原位置
	var nodeFlag = false;//节点flag位
	var nodeIds=[];
	function _menuManageInit(){
		//系统菜单树初始化
		_menuManageList();
		//数据保存初始化
		$("#menuSubmit").bind("click", function(){
			_menuSubmit();
		});
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
	//系统菜单树初始化
	function _menuManageList(){
		var ajaxOptions = {
				type: "post",
				url : 'system/sysmenuTree',// 地址
				success : function(result) {
					// 请求成功时处理
					if(result.listData!=null&&result.rtnCode==ConstCode.BIZ_CODE_SUCCESS()){
						//申明node对象
					    function Node(menu) {
					      this.id = '';
					      this.text = '';
					      this.data = '';
					      this.menu = menu;
					      this.children = [];
					    }
					    //创建节点
					    var menuNodeList = [];
					    for(var i=0;i<result.listData.length;i++){
					    	menuNodeList.push(new Node(result.listData[i]));
					    	nodeIds.push(result.listData[i].menuId);
					    }
					    //循环创建根节点
					    let rootList = [];
					    for(var i=0;i<menuNodeList.length;i++){
					    	  //当departId为0时，根节点名称为公司名称
					        if (menuNodeList[i].menu.parent == null) {
						        rootList.push(menuNodeList[i]);
						        menuNodeList[i].id = menuNodeList[i].menu.menuId;
						        menuNodeList[i].text = menuNodeList[i].menu.text;
						        menuNodeList[i].data = menuNodeList[i].menu.sortNo;
						      }
					    }
					    //获取根部门下的下属部门
					    for(var j=0;j<menuNodeList.length;j++){
					    	  for (let i = 0; i < menuNodeList.length; i++) {
							        //当部门id等于部门parentDepartId时；
							        if ((menuNodeList[j].menu.menuId == menuNodeList[i].menu.parent)&&menuNodeList[i].menu.parent!=null) {
							        	menuNodeList[j].children.push(menuNodeList[i]);
							        	menuNodeList[i].id = menuNodeList[i].menu.menuId;
							        	menuNodeList[i].text = menuNodeList[i].menu.text;
							        	menuNodeList[i].data = menuNodeList[i].menu.sortNo;
							        }
							      }
					    }
						_sysManuTree(rootList);
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
	function _sysManuTree(rootList){
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
		      "plugins": ["types","dnd"]
		    });
		    $(document).on('dnd_stop.vakata', function (e, data) {
		    	//获取树节点
		    	if(nodeFlag){
		    		 var ref = $('#jstree_demo').jstree(true);
				 	  ref.cut(data.data.nodes[0]);
			    	  ref.paste(oldParentId,oldOrderId)
			    	  ref.clear_buffer ();
		    	}
		    });
		 	//拖拽事件处理
		    $("#jstree_demo").on("move_node.jstree", function(e, data) {
		       //获取树节点
		       var ref = $('#jstree_demo').jstree(true);
		      //获取主键id
		      var manuId = data.node.id;
		      var text = data.node.text;
		      //获取拖拽前父节点
		      oldParentId = data.old_parent;
		      //获取拖拽后父节点
		      var newParentId = data.parent;
		      //获取原位置
		      oldOrderId = data.old_position;
		      //拖拽后父节点的父节点
		      var ParentId = ref.get_node(newParentId).parent;
		      if(oldParentId !=newParentId){
		    	  nodeFlag = true;
		    	  return;
		      } else{
		    	  nodeFlag = false;
		      }
		      //所有同级节点
		      var childerns = ref.get_node(oldParentId).children;
		      if(childerns!=null&&childerns.length>0){
		    	  for(var i=0;i<childerns.length;i++){
		    		  ref.get_node(childerns[i]).data = i+1;
		    	  }
		      }
		    })
		    //节点点击事件
		    $("#jstree_demo").on("changed.jstree", function(e,data) {
		    	if(data.node!=null){
		    		//获取树节点
				    var ref = $('#jstree_demo').jstree(true);
				    //获取主键id
				    var manuId = data.node.id;
				    ref.edit(manuId);
		    	}
		    });
		    //节点名称编辑事件
		    $("#jstree_demo").on("rename_node.jstree", function(e, data) {
		      var ref = $('#jstree_demo').jstree(true);
		      var node = data.node;
		      //获取节点Id
		      var menuId = node.id;
		      //获取输入节点名称
		      var menuName = node.text.trim();
		      var menuOldName=data.old;
		      // 名称长度判断
		      if (menuName.length > 50) {
		          Globals.msgboxJS.ToastrWarning(ConstMsg.HTML_MSG_INPUTEXCEED_FIRETY());
		          ref.set_text(menuId, menuOldName);
		          return;
		        }
		    });
	}
	function _menuSubmit(){
		//获取树节点
	    var ref = $('#jstree_demo').jstree(true);
		var menuLength = ref._cnt;
		var menuIds =[];
		var texts=[];
		var sortNos=[];
		for(var i=0;i<menuLength;i++){
			var result = ref.get_node(nodeIds[i]);
			menuIds.push(result.id);
			texts.push(result.text);
			sortNos.push(result.data);
		}
		 ajaxOptions = {
					type: "post",
					url : 'system/menusubmit',// 地址
					 traditional: true,
                    data:{
                    	menuIds:menuIds,
                    	texts:texts,
                    	sortNos:sortNos
                    	}, //只有这一个参数，json格式，后台解析为实体，后台可以直接用
					success : function(result) {
						_cb_MenuUpd_successFun(result);
					}, // 请求成功时处理
					error: function(){
					}
				};
		 _this.sendAjax(ajaxOptions);
	}
	//修改执行成功的场合
	function _cb_MenuUpd_successFun(result){
		if(result.data!=null){
			if(result.data.bizCode==ConstCode.BIZ_CODE_SUCCESS()){
				 Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS());
			} else {
				Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_UPDATE_ERROR());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_UPDATE_ERROR());
		}
	}
	// 构造方法
	function constructor() {
		_this = this;
        this.menuManageInit= _menuManageInit;
	}
	return constructor;
})();

//继承公共AJAX类
Globals.menuManageJS.prototype = new Globals.comAjaxJS();