/**
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：李辉--%>
 * <%-- 模块名：角色管理js --%>
 */
Namespace.register("Globals.authorityListJS");

Globals.authorityListJS = (function() {

	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	// 初始化人员检索pop
	function _InitAuthorityList() {
		// 关闭事件
		$("#roleClose").click(function() {
			 var rolecancel = ConstMsg.CONFIRM_BACK();
	            Globals.msgboxJS.Confirm(rolecancel,function(){
	            	$("#roleDetail").modal("hide");
	            },function(){
	              return;
	           })
		})

		// 表单提交
		$("#roleSearch").click(function() {
			  $("#selectForm").ajaxSubmit(function(data) {
					if(data != null && data.listData !=null){
						if(data.listData.length == ConstText.HTML_MSG_NUM_ZERO()){
							$("#roleTable").hide();
							$("#noRoleResult").show();
							$("#roleDelete").hide();
						}else{
							_InitTable(data.listData);
							$("#roleTable").show();
							$("#noRoleResult").hide();
							$("#roleDelete").show();
						}
					}
				});
		})

		//新增事件
		$("#roleAdd").bind("click",function(){
			_refreshForm();
			$("#roleflag").val("add");
			$("#roleDetail").modal("show");
		});

		//权限设定
	 	$("#authorityRoleTable").on('click','.btn-set',function(event){
	 		var roleId =$(this).attr("roleId");
	 		var roleName =$(this).attr("roleName");
	 		var description =$(this).attr("description");
	 		var roleNameSearch =$("#roleNameSearch").val();
	 		var descriptionSearch =$("#descriptionSearch").val();
	 		window.location.href =path+"/system/authoritymanage?roleId="+roleId+"&roleName="+encodeURI(encodeURI(roleName))+"&description="+encodeURI(encodeURI(description))+"&roleNameSearch="+encodeURI(encodeURI(roleNameSearch))+"&descriptionSearch="+encodeURI(encodeURI(descriptionSearch));
	 	})

	 	//修改事件
	 	$("#authorityRoleTable").on('click','.btn-upd',function(){
	 		_refreshForm();
	 		var roleId =$(this).attr("roleId");
	 		var roleName =$(this).attr("roleName");
	 		var description =$(this).attr("description");
	 		$("#roleId").val(roleId);
	 		$("#roleName").val(roleName);
	 		$("#description").val(description);
	 		$("#roleflag").val("upd");
	 		$("#roleDetail").modal("show");
	 	})

	 	//表单验证
	 	_RoleForm();

	 	//保存事件
		$("#roleSubmit").bind("click",function(){
			_roleSubmit();
		})

		//删除事件
		$("#roleDelete").bind("click",function(){
			_roleDelete();
		})

		//返回事件
		$("#authorityListCancel").bind("click",function(){
			Globals.utilJS.ReturnHomePage();
		})

		//table行点击事件
		 $('#authorityRoleTable tbody').on('click', 'td', function() {
				// 多选
			 	var clickTd = $(this).parents("tr").find("td").index($(this));
			 	if(clickTd!=3){
			 		if ($(this).parents("tr").hasClass('selected')) {
						$(this).parents("tr").removeClass('selected');
						$(this).parents("tr").find(":checkbox").prop('checked', false);
					} else {
						$(this).parents("tr").addClass('selected');
						$(this).parents("tr").find(":checkbox").prop('checked', true);
					}
			 		if($("input[name='checkbox']:checked").length==$("input[name='checkbox']").length){
						$('#checkAll').prop("checked",true);
					}else{
						$('#checkAll').prop("checked",false);
					}
			 	}
		});
		 //权限设定后处理
		 _AuthorityListInit();
	}
	 //权限设定后处理
	function _AuthorityListInit(){
		var authorityFlag = $("#authorityFlag").val();
		if(authorityFlag=="set"){
			$("#roleSearch").click();
		}
		$("authorityFlag").val("");
	}
	//表单验证
	function _RoleForm(){
		$('#roleform').validate({
    	    errorElement: 'span',
    	    errorClass: 'help-block',
    	    focusInvalid: false,
    	    ignore: "",
    	    rules:
    	    {
    	    	roleName:{
    	    		 required: true,
    	    		 maxlength: ConstText.HTML_MSG_NUM_FIRETY()
    	    	},
    	    	description:
    	    	{
    	    		maxlength: ConstText.HTML_MSG_NUM_THB()
	        	}
    	    },
        messages:
        {
        	roleName:{
        		required:ConstMsg.HTML_MSG_MUSTINPUT_ROLENAME(),
        		maxlength:ConstMsg.HTML_MSG_INPUTEXCEED_FIRETY()
	    	},
	    	description:
        	{
	    		maxlength: ConstMsg.HTML_MSG_INPUTEXCEED_THB()
    		}
        },
        invalidHandler: function (event, validator)
        {
        },

        highlight: function (element)
        {
            $(element)
                .closest('.form-group').addClass('has-error');
        },

        success: function (label)
        {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        }
    	});
	}
	// 初始化datatable
	function _InitTable(result) {
		var table = $('#authorityRoleTable');
		 table.dataTable({
					paging : false, // 禁止分页
					ordering : false,// 不可排序
					bDestroy : true,
					autoWidth:false,
					data : result,
					"columns" : [
							{
								"title" : "<input id='checkAll' type='checkbox' ><lable  style='margin-left:10px'>全选</lable>",
								"width" : " 10%",
								"className":"text-center",
								"data" : "roleId",
								render : function(data) {
									return '<div class="text-center"><input name="checkbox" type="checkbox" value=' + data + '></div>';
								}
							},
							{
								"title" : "角色名称",
								"width" : " calc( 100% - 1.2em )",
								"className":"text-center",
								"data" : "roleName",
								render : function(data) {
									return ' <div class="overflow-custom" title="'+data+'" style="text-align:left;">'+data+'</div>';
								}
							},
							{
								"title" : "角色描述",
								"width" : " calc( 100% - 1.2em )",
								"className":"text-center",
								"data" : "description",
								render : function(data) {
									return ' <div class="overflow-custom" title="'+data+'" style="text-align:left;">'+data+'</div>';
								}
							},{
								"title" : "操作",
								"width" : " calc( 100% - 1.2em )",
								"className":"text-center",
								"data" : null
							} ],
					"pagingType" : "bootstrap_full_number",
					"language" : {
						"emptyTable" : "无可用数据"
					},
					dom : 't',
					"columnDefs" : [ {
						 "targets": [3],
					      render: function(data, type, row, meta) {
					            return '<div style="text-align:left;"><a name="roleActionSet" class="btn-set" roleId="'+row.roleId+'" roleName="'+row.roleName+'" description="'+row.description+'">权限设定</a>'+
		             					   '<a name="roleUpd" class="btn-upd" roleId="'+row.roleId+'" roleName="'+row.roleName+'" description="'+row.description+'" style="margin-left:10px">修改</a></div>';
					      }
					} ],
					 "fnDrawCallback":function(){
						    var api =this.api();
			                 api.column(0).nodes().each(function(cell, i) {
			                        $(cell).css("width","10%");
			                        });
					     if(result.length>=8){
		                	 $("#authorityRoleTable  tbody").css("height","313px");
		                 } else {
		                	 $("#authorityRoleTable  tbody").css("height","");
		                 }
					 }
				});
			//反选
			$("input[name='checkbox']").click(function(){
				if($("input[name='checkbox']:checked").length==$("input[name='checkbox']").length){
					$('#checkAll').prop("checked",true);
				}else{
					$('#checkAll').prop("checked",false);
				}
			})

			//全选事件
			 $("#checkAll").click(function(){
				 var input = $('input:checkbox[type="checkbox"]');
				 var tr = $(input).closest('tr');
				 if ($("#checkAll").prop("checked")) {
					  tr.addClass('selected');
				      input.prop("checked", true);
				 } else {
					  tr.removeClass('selected');
				      input.prop("checked", false);
				}
	         });
	}

	//保存事件
	function _roleSubmit(){
		//check表单验证
		if ($("#roleform").valid() == false) {
			return;
		}
		var roleflag = $("#roleflag").val();
		if(roleflag=="add"){
			var ajaxOptions = {
					type: "post",
					url : 'system/roleAdd',// 地址
					contentType: "application/json",
					data :JSON.stringify($('#roleform').serializeJSON()),
					success : function(result) {
						_cb_addRole_successFun(result);
					}, // 请求成功时处理
					error: function(){
					}
				};
		} else {
			var ajaxOptions = {
					type: "post",
					url : 'system/roleUpd',// 地址
					contentType: "application/json",
					data :JSON.stringify($('#roleform').serializeJSON()),
					success : function(result) {
						_cb_updRole_successFun(result);
					}, // 请求成功时处理
					error: function(){
					}
				};
		}
		_this.sendAjax(ajaxOptions);
	}
	//新增执行成功的场合
	function _cb_addRole_successFun(result){
		if(result.data!=null){
			if(result.data.bizCode==ConstCode.BIZ_CODE_SUCCESS()){
				 Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_INSERT_SUCCESS());
				$("#roleDetail").modal("hide");
				$("#roleSearch").click();
			} else {
				Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_INSERT_FAILED());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_INSERT_FAILED());
		}
	}
	//修改执行失败的场合
	function _cb_updRole_successFun(result){
		if(result.data!=null){
			if(result.data.bizCode==ConstCode.BIZ_CODE_SUCCESS()){
				 Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS());
					$("#roleDetail").modal("hide");
					$("#roleSearch").click();
			} else {
				Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED());
		}
	}
	//删除事件
	function _roleDelete(){
		var data = $('#authorityRoleTable').DataTable().rows('.selected').data();
		// 执行从父页面传来的方法
		if(data.length == 0){
			//如果没选择，就提示
			Globals.msgboxJS.ToastrWarning(ConstMsg.DELETE_UNSELECT());
			return;
		}
		var roleIds=[];
		var roleNames=[];
		for(var i=0;i<data.length;i++){
				roleIds.push(data[i].roleId);
				roleNames.push(data[i].roleName);
		}
		  var roleName = ConstMsg.CONFIRM_DELETE()+roleNames+"?";
          Globals.msgboxJS.Confirm(roleName,function(){
  	 		_RoletDelete(roleIds);
          },function(){
            return;
          })
	}
	//删除ajax
	function _RoletDelete(roleIds){
		var ajaxOptions = {
				type: "post",
				url : 'system/roleDelete',// 地址
				contentType : "application/x-www-form-urlencoded; charset=UTF-8",
				traditional: true,
				data :{
					roleIds:roleIds
				},
				success : function(data) {
					_cb_roleDelete_successFun(data);
				}, // 请求成功时处理
				error: function(){
				}
			};

		_this.sendAjax(ajaxOptions);
	}
	function _cb_roleDelete_successFun(result){
		if(result.data!=null){
			if(result.data.bizCode==ConstCode.BIZ_CODE_SUCCESS()){
				 Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DELETE_SUCCESS());
				$("#roleSearch").click();
			} else if(result.data.bizCode==ConstCode.BIZ_CODE_SRCCANT_DELETE()){
				Globals.msgboxJS.ToastrWarning(ConstMsg.ROLE_UNDELETE());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_DELETE_DOCUMENT_FAILED());
		}
	}
	//刷新Form表单
	function _refreshForm(){
		$(':input','#roleform')
		 .not(':button, :submit, :reset')
		 .val('')
		$("#roleform").validate().resetForm();
	}
	// ========================================================

	// 构造方法
	function constructor() {
		_this = this;
		this.InitAuthorityList = _InitAuthorityList;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.authorityListJS.prototype = new Globals.comAjaxJS();