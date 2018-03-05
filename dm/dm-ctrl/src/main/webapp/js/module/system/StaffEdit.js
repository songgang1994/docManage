/**
 * NSK-NRDC业务系统
 *	作成人：张丽
 *	人员维护  js
 */

Namespace.register("Globals.staffEditJS");

Globals.staffEditJS = (function() {
	var _this = null;
	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	function _init(){

        //按钮点击事件
		$("#btn-save").bind("click", function(){_insert();});
	 	$("#btn-update").bind("click", function(){_updatel();});

		//初始化表单验证
		_initformvaild();

		$(".leaderFlg").click(function(){
    		//判断combox状态进行赋值
    		if($(this).val()==ConstText.HTML_MSG_NUM_ZERO()){
    			$(this).val(ConstText.HTML_MSG_NUM_ONE());
    		}else{
    			$(this).val(ConstText.HTML_MSG_NUM_ZERO());
    		}
    	})

	}
/**********************************人员维护表单验证******************************/
	function _initformvaild(){
		$('#form-user').validate({
   	    errorElement: 'span', //default input error message container
   	    errorClass: 'help-block', // default input error message class
   	    focusInvalid: false, // do not focus the last invalid input
   	    ignore: "",
   	    rules:{
	        	userCode:{
	        		required: true
	        	},
	        	userName:{
	        		required: true
	        	},
	        	email:{
	        		required: true,
	        		email : true
	        	},
   	    },
       messages:{
		   		userCode:{
		   			required: ConstMsg.HTML_MSG_MUSTCHOOSE_USERNAME(),
		   		},
		   		userName:{
		   			required: ConstMsg.HTML_MSG_MUSTCHOOSE_REALNAME(),
		       	},
		       	email:{
		   			required: ConstMsg.HTML_MSG_MUSTCHOOSE_EMAIL(),
		   			email : ConstMsg.HTML_MSG_EFFECT_EMAIL(),
		   		},
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
           label.closest('.form-group').removeClass('has-error');
           label.remove();
       }
   	});
	}
/**********************************人员维护表单验证结束******************************/

/**********************************按钮点击事件******************************/
	//返回按钮跳转上一个画面
	$("#btn-back").bind("click",function(e){
		//防止表单默认提交
		e.preventDefault();
		e.stopPropagation();
		//弹框
		 var staffcancel = ConstMsg.CONFIRM_BACK();
            Globals.msgboxJS.Confirm(staffcancel,function(){
            	var userName1 = $("#userName1").val();
				var userId1 = $("#userId1").val();
				var email1 = $("#email1").val();
				var roleId1 = $("#roleId1").val();
				var roleName1 = $("#roleName1").val();
				var flug = 1;
				window.location.href=path+"/system/staffmanage?userName="+encodeURI(encodeURI(userName1))+"&userId="+userId1+"&email="+email1+"&roleId="+roleId1+"&roleName="+encodeURI(encodeURI(roleName1))+"&flug="+flug;
            },function(){
              return;
           })
	});
	//点击删除事件
   $(".delet").click(function(){
	   //通过层级关系找指定的值
	   var me = this;
	   var deptId = $(this).parent().parent().children().children().val()    //部门id
	   var deptName = $(this).parent().parent().children().children().next().val()   //部门名称
	   var userId = $("#userId").val();																	//用户id
	   var deptName = ConstMsg.BIZ_CODE_SHUR_DELETE()+deptName+"?";
       Globals.msgboxJS.Confirm(deptName,function(){
    	   //删除所在行数据
		   $(me).parent().parent().remove();
    	   //调用ajax
    	   var  ajaxOptions = {
    				async:false,
    	            url:"system/deleteDept",
    	            type:"post",
    	            data:{
    	            	userId:userId,
    	            	deptId:deptId
    	            },
    	            success:function(data){
    	            	_deleteSuccess(data)
    	            }
    	       };
    		var comAjax = new Globals.comAjaxJS();
    		comAjax.sendAjax(ajaxOptions);
       },function(){
         return;
       })
   })

	//部门添加点击事件
    $("#deptt").click(function(){
    	var all = "";
    	var map = [];
    	var project = new Globals.departSearchPopupJS();
        project.ShowModal('checkbox',function(data){
        	var allNamemsg = "";
        	for(var i=0; i<data.length;i++){
        		var allId = data[i][0].deptId;
        		var allName = data[i][0].deptName;
        		var count = ConstText.HTML_MSG_NUM_ZERO();
        		if($(".deptId").length>ConstText.HTML_MSG_NUM_ZERO()){
                	$(".deptId").each(function(i,input){
                		var deptId = $(input).val();
                		if(deptId == allId){
                			count++;
                			allNamemsg += allName + ","
                		}
                	})
                	if(count == ConstText.HTML_MSG_NUM_ZERO()){
                		all+="<div class='  col-sm-12 col-md-12 col-lg-12 col-xs-12  no-padding all'>" +
        				"<div class='  col-sm-4 col-md-4 col-lg-4 col-xs-4  no-padding'>"+
        					"<input type='hidden' class='deptId' value="+allId+">"+
        					allName+
        					"</div>"+
        					"<div class=' col-sm-4 col-md-4 col-lg-4 col-xs-4  no-padding'>"+
        						"<input type='checkbox' value='0' class='leaderFlg'>"+"负责人"+
        					"</div>"+
        					"<div class=' col-sm-3 col-md-3 col-lg-3 col-xs-3 no-padding'>"+
    				 			"<button class='btn btn-default btn-xs delet'  style='width:100%'>"+"删除"+"</button>"+
    				 		"</div>"+
    				 		"</div>"

            		}
        			}else{
        				all+="<div class='  col-sm-12 col-md-12 col-lg-12 col-xs-12  no-padding all'>" +
        				"<div class=' col-sm-4 col-md-4 col-lg-4 col-xs-4 no-padding'>"+
        					"<input type='hidden' class='deptId' value="+allId+">"+
        					allName+
        					"</div>"+
        					"<div class='col-sm-4 col-md-4 col-lg-4 col-xs-4  no-padding'>"+
        						"<input type='checkbox' value='0' class='leaderFlg'>"+"负责人"+
        					"</div>"+
        					"<div class='  col-sm-3 col-md-3 col-lg-3 col-xs-3 no-padding'>"+
					 			"<button class='btn btn-default btn-xs delet'  style='width:100%'>"+"删除"+"</button>"+
					 		"</div>"+
					 		"</div>"
        			}

        		}
        	//清空提示
        	$("#tip").html("");
        	//添加部门
        	$("#addnew").append(all);

        	//负责人combox点击事件
        	$(".leaderFlg").click(function(){
        		//判断combox状态进行赋值
        		if($(this).val()==ConstText.HTML_MSG_NUM_ZERO()){
        			$(this).val(ConstText.HTML_MSG_NUM_ONE());
        		}else{
        			$(this).val(ConstText.HTML_MSG_NUM_ZERO());
        		}
        	})
        	//点击删除按钮，删除所在行
        	$(".delet").click(function(){
        		$(this).parent().parent().remove();
        	})
        	if(allNamemsg!=""){
        		Globals.msgboxJS.ToastrError(allNamemsg+ConstMsg.HTML_MSG_DEPART_ESXIT())

        	}

        });
    })
	//点击新增按钮
	function _insert(){
		if($("#form-user").valid() == false || $(".deptId").length==0 ){
			$("#tip").html("部门必须选择")
			return;
		}
		//是否为负责人（放入集合）用逗号隔开
		var leaderValue = new Array();
		for(var i=0;i<$(".leaderFlg").length;i++){
			leaderValue[i]=$(".leaderFlg")[i].value;
		}
		var  Flg=  leaderValue.join(",");

		//部门id（放入集合）用逗号隔开
		var deptValue = new Array();
		for(var j=0;j<$(".deptId").length;j++){
			deptValue[j]=$(".deptId")[j].value;
		}
		var dept =  deptValue.join(",");

		//角色Id（放入集合）用逗号隔开
		var role=$("#roleName").val();
		var roleName = role.join(",");
		//将参数放入对象中
        var UserDeptRoleDto={userCode:$("#userCode").val() ,
        									userName:$("#userName").val() ,
        									email:$("#email").val() ,
        									roleName:roleName,
        									dept:dept,
        									Flg:Flg};
		var  ajaxOption = {
				async:false,
	            url:"system/addUser",
	            type:"post",
	            data:JSON.stringify(UserDeptRoleDto),
	            contentType: "application/json",
	            success:function(data){
	            	_enterSuccess(data)
	            }
	       };
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOption);

	}

 //点击修改按钮
	function _updatel(){
		if($("#form-user").valid() == false){
			return;
		}
		//是否为负责人（放入集合）用逗号隔开
		var leaderValue = new Array();
		for(var i=0;i<$(".leaderFlg").length;i++){
			leaderValue[i]=$(".leaderFlg")[i].value;
		}
		var  Flg=  leaderValue.join(",");
		//部门id（放入集合）用逗号隔开
		var deptValue = new Array();
		for(var j=0;j<$(".deptId").length;j++){
			deptValue[j]=$(".deptId")[j].value;
		}
		var dept =  deptValue.join(",");
		var role=$("#roleName").val();
		var roleName = role.join(",");
		//将参数放入对象中
       var UserDeptRoleDto={userId:$("#userId").val(),
   										userCode:$("#userCode").val() ,
       									userName:$("#userName").val() ,
       									email:$("#email").val() ,
       									roleName:roleName,
       									dept:dept,
       									Flg:Flg};
		var  ajaxOption = {
				async:false,
	            url:"system/updateUser",
	            type:"post",
	            data:JSON.stringify(UserDeptRoleDto),
	            contentType: "application/json",
	            success:function(data){
	            	_updatesuccess(data)
	            }
	       };
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOption);



	}

/**********************************按钮点击事件结束******************************/
/**********************************Msg区域***************************************/
	//新增msg
	function _enterSuccess(json) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
			    //成功时直接跳转页面
				case ConstCode.BIZ_CODE_INSERT_SUCCESS() :
					//提示信息
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_INSERT_SUCCESS());
					//调用返回函数
					_cancel();
					break;
				case 	ConstCode.BIZ_CODE_HANDLE_FAILED():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
					break;
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}
	//删除msg
	function _deleteSuccess(json) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
			    //删除成功，报msg
				case ConstCode.BIZ_CODE_DELETE_SUCCESS() :
				    Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DELETE_SUCCESS())
					break;
				//参数错误，修改失败
				case 	ConstCode.BIZ_CODE_INVALID_PARAMS():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_INVALID_PARAMS())
					break;
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}

	//修改msg
	function _updatesuccess(json) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
			    //修改成功，报msg
				case ConstCode.BIZ_CODE_UPDATE_SUCCESS() :
					//提示信息
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS());
					//调用返回函数
					 _cancel();
					break;
				//参数错误，修改失败
				case 	ConstCode.BIZ_CODE_INVALID_PARAMS():
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_INVALID_PARAMS())
					break;
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}

	//返回函数
	function _cancel(){
		//参数传递
		var userName1 = $("#userName1").val();
		var userId1 = $("#userId1").val();
		var email1 = $("#email1").val();
		var roleId1 = $("#roleId1").val();
		var roleName1 = $("#roleName1").val();
		var flug = 1;
		//跳转首页（设定时间）
		timeID=setTimeout(function(){
			clearTimeout(timeID);
			window.location.href=path+"/system/staffmanage?userName="+encodeURI(encodeURI(userName1))+"&userId="+userId1+"&email="+email1+"&roleId="+roleId1+"&roleName="+encodeURI(encodeURI(roleName1))+"&flug="+flug;
		},ConstText.HTML_MSG_NUM_MS());
	}

	function GetConst(BizCode) {
		var bizCode= new Globals.constJS();
		return bizCode.GetCode(BizCode);
	}


/**********************************Msg区域结束***************************************/

	// 构造方法
	function constructor() {
		_this = this;
       this.init= _init;
	}
	return constructor;
})();

//继承公共AJAX类
Globals.staffEditJS.prototype = new Globals.comAjaxJS();