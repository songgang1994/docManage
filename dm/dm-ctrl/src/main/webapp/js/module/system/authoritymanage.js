/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：权限维护js
 */
Namespace.register("Globals.authorityManageJS");

Globals.authorityManageJS = (function() {
	var _this = null;
	var timeID;//计时器
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	function _authorityManageInit(){
		//权限维护树目录初始化
		_authorityManageTreeInit();
		//权限维护保存
		_authorityManageEnter();
		//返回事件
		$("#authorityManageCancel").bind("click",function(){
			 var projectcancel = ConstMsg.CONFIRM_BACK();
	            Globals.msgboxJS.Confirm(projectcancel,function(){
	            	var roleNameSearch = $("#roleNameSearch").val();
	    			var descriptionSearch = $("#descriptionSearch").val();
	    			window.location.href =path+"/system/authorityList?roleNameSearch="+encodeURI(encodeURI(roleNameSearch))+"&descriptionSearch="+encodeURI(encodeURI(descriptionSearch))+"&authorityFlag=set";
	            },function(){
	              return;
	           })
		});
	}
	//权限维护树目录初始化
	function _authorityManageTreeInit(){
		var roleId = $("#roleId").val();
		var ajaxOptions = {
				type: "post",
				url : 'system/authoritymanageTree/'+roleId,// 地址
				success : function(result) {
					// 请求成功时处理
					if(result.listData!=null&&result.rtnCode==ConstCode.BIZ_CODE_SUCCESS()){
						//申明node对象
					    function Node(menu) {
					      this.id = '';
					      this.text = '';
					      this.state = [];
					      this.menu = menu;
					      this.children = [];
					    }
					    //创建节点
					    var menuList = [];
					    for(var i=0;i<result.data.sysMenuList.length;i++){
					    	menuList.push(new Node(result.data.sysMenuList[i]));
					    }
					    var actionList = [];
					    for(var i=0;i<result.data.roleActionList.length;i++){
					    	actionList.push(new Node(result.data.roleActionList[i]));
					    }
					    //循环创建根节点
					    var rootList = [];
					    for(var i=0;i<menuList.length;i++){
					    	  //当departId为0时，根节点名称为公司名称
						      if (menuList[i].menu.parent == null) {
						    	  //当departId为0时，根节点名称为公司名称
							      if (menuList[i].menu.parent == null) {
							        rootList.push(menuList[i]);
							        menuList[i].id = menuList[i].menu.actionId;
							        menuList[i].text = menuList[i].menu.text;
							        menuList[i].state.selected = menuList[i].menu.selected;
							        menuList[i].state.opened = true;
						      }
					    }
					    }
					    //获取根部门下的下属部门
					    for(var j=0;j<menuList.length;j++){
					    	  for(let i=0;i<actionList.length;i++){
								    if(menuList[j].menu.menuId == actionList[i].menu.menuId){
								    	menuList[j].children.push(actionList[i]);
								    	actionList[i].id = actionList[i].menu.actionId;
								    	actionList[i].text = "【"+actionList[i].menu.dispName+"】" +actionList[i].menu.elementName;
								    	actionList[i].state.selected = actionList[i].menu.selected;
								    }
								  }
							      for (let i = 0; i < menuList.length; i++) {
							        //当部门id等于部门parentDepartId时；
							        if ((menuList[j].menu.menuId == menuList[i].menu.parent)) {
							        	menuList[j].children.push(menuList[i]);
							          menuList[i].id = menuList[i].menu.actionId;
							          menuList[i].text = menuList[i].menu.text;
							          menuList[i].state.selected = menuList[i].menu.selected;
							          menuList[i].state.opened = true;
							        }
							      }
					    }
						_authorityManageTree(rootList);
					} else{
						Globals.msgboxJS.ToastrWarning(constMsg.PROJECTNO_REPEAT());
					}
				},
				error: function(){
				}
			};

		_this.sendAjax(ajaxOptions);
	}
	function _authorityManageTree(rootList){
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
              "checkbox": {
                  "cascade_to_disabled" : false,
                  "three_state": false,
                },
		      "plugins": ["types","checkbox"]
		    });
		 	//checkbox选中事件处理
		    $("#jstree_demo").on("select_node.jstree", function(e, data) {
		    	//获取树节点
			     var ref = $('#jstree_demo').jstree(true);
		    	 //获取主键id
			     var manuId = data.node.id;
			     //获取父节点
			     var ParentId = ref.get_node(manuId).parent;
			     //获取父节点状态
			     var selected = ref.get_node(ParentId).state.selected;
			     if(selected==false){
			    	 ref.deselect_node(manuId,true);
			     }
		    })
		    	//checkbox取消事件处理
		    $("#jstree_demo").on("deselect_node.jstree", function(e, data) {
		    	//获取树节点
			     var ref = $('#jstree_demo').jstree(true);
		    	 //获取主键id
			     var manuId = data.node.id;
			     //获取父节点
			     var childernId = ref.get_node(manuId).children;
			     //获取父节点状态
			     for(var i=0;i<childernId.length;i++){
			    	 ref.deselect_node(childernId[i],true);
			     }
		    })
	}
	function _authorityManageEnter(){
		$("#authorityManageSubmit").click(function(){
			//获取权限Id
			var roleId = $("#roleId").val();
			//获取选中项Id
			var actionSelected = $('#jstree_demo').jstree(true).get_selected();
			if(actionSelected.length==ConstText.HTML_MSG_NUM_ZERO()){
				Globals.msgboxJS.ToastrWarning(ConstMsg.AUTHORITY_UNSELECTED());
				return;
			}
			var ajaxOptions = {
					type: "post",
					url : 'system/authoritymanageAdd',// 地址
					traditional: true,
					data :{
						actionSelected:actionSelected,
						roleId:roleId
					},
					success : function(data) {
						_cb_authorityEnter_successFun(data);
					}, // 请求成功时处理
					error: function(){
					}
				};

			_this.sendAjax(ajaxOptions);
		})
	}
	//权限编辑成功的场合
	function _cb_authorityEnter_successFun(data){
		if (data.data!=null&&data.data.bizCode ==ConstCode.BIZ_CODE_SUCCESS()) {
			Globals.msgboxJS.ToastrSuccess(ConstMsg.AUTHORITY_SUCCESS());
			var roleNameSearch = $("#roleNameSearch").val();
			var descriptionSearch = $("#descriptionSearch").val();
			timeID=setTimeout(function(){
				clearTimeout(timeID);
				window.location.href =path+"/system/authorityList?roleNameSearch="+encodeURI(encodeURI(roleNameSearch))+"&descriptionSearch="+encodeURI(encodeURI(descriptionSearch))+"&authorityFlag=set";
			},ConstText.HTML_MSG_NUM_MS());
		} else {
			Globals.msgboxJS.ToastrWarning(ConstMsg.AUTHORITY_ERROR());
		}
	}
	// 构造方法
	function constructor() {
		_this = this;
        this.authorityManageInit= _authorityManageInit;
	}
	return constructor;
})();

//继承公共AJAX类
Globals.authorityManageJS.prototype = new Globals.comAjaxJS();