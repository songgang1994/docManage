/**
 * 系统名：NSK-NRDC业务系统 作成人：曾雷 模块名：人员维护
 */
Namespace.register("Globals.staffManageJS");

Globals.staffManageJS = (function() {
	var _this = null;
	var checkedIds= [];//保存选中的id，亦适用于分页
	var data = [];
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	function _initPage() {
		// 查询
		$('#query').on('click', function() {
			query();
		})

		if($("#flug").val()!=""){
			$("#query").click();
		}

		// 返回
		$('#form_staff_ret').on('click', function() {
			Globals.utilJS.ReturnHomePage();
		});

		//checkbox点击事件
		$('#staffmange tbody').on('click', 'input:checkbox', function() {
			//获取行
			var tr = $(this).closest('tr');
			//当checkbox选中的场合
		    if($(this).is(":checked")){
		    	//添加数据
		   	 	checkedIds.push($(this).attr("value"));
		   	 	data.push($('#batchCheckTable').DataTable().rows(tr).data())
		     }else{
		    	 //移除数据
			    for(var i=0; i<checkedIds.length; i++){
			            if($(this).attr("value") == checkedIds[i]){
			            checkedIds.splice(i, 1);
			            data.splice(i,1);
			            }
			        }
		     }
		    if($("input[name='checkbox']:checked").length==$("input[name='checkbox']").length){
				$('#selectAll').prop("checked",true);
			}else{
				$('#selectAll').prop("checked",false);
			}
		 });
	}

	//点击新增按钮
	$("#btn-add").click(function(){
		var userName1 = $("#userName").val();
		var userId1 = $("#userId").val();
		var email1 = $("#email").val()
		var roleId1 = $("#role").val()
		var roleName1 = $("#role").find("option:selected").text();
		window.location.href=path+"/system/staffadd?userName1="+encodeURI(encodeURI(userName1))+"&userId1="+userId1+"&email1="+email1+"&roleId1="+roleId1+"&roleName1="+encodeURI(encodeURI(roleName1));
	})
    //查询函数
	function query() {
		//加载datetable
		var pageLength = Globals.utilJS.getCookie(ConstText.STAFF_PAGE_LENGTH());
		_StaffList(pageLength);
		StaffTable.load();
	};
	function _StaffList(pageLength){
		var userName = $('#userName').val();
		var userId = $('#userId').val();
		var email = $('#email').val();
		var roleId = $('#role').val();
		$('#selectAll').prop("checked",false);
		var table = $('#batchCheckTable');
		StaffTable=table.dataTable({
			ordering : false,// 不可排序
			bDestroy:true,
			serverSide:true,//后台分页
			autoWidth:false,
			ajax : {
				type : "post",
				url : 'system/getusers',// 地址
				dataType : 'json',
				dataSrc : "listData",
				data : {
					userName : userName,
					userId : userId,
					emailAddress : email,
					roleId : roleId
				},
			},
			"columns" : [
			{
				"data" : 'userId',
				render : function(data) {
					return '<div style="text-align:center;"><input class="checbox" style="align:center" name="checkbox" type="checkbox" value="' + data + '"></div>';
				}
			}, {
				"title" : "用户ID",
				"width" : "10%",
				"className":"text-center",
				"data" : "userId",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}, {
				"title" : "姓名",
				"width" : "25%",
				"className":"text-center",
				"data" : "userName",
				render : function(data) {
					return ' <div style="text-align:left;">'+data+'</div>';
				}
			}, {
				"title" : "电子邮件",
				"width" : "25%",
				"className":"text-center",
				"data" : "email",
				render : function(data) {
					if(data!=null){
					return ' <div style="text-align:left;">'+data+'</div>';
					}else{
						return ' <div style="text-align:left;"></div>';
					}
				}
			}, {
				"title" : "角色",
				"width" : "20%",
				"className":"text-center",
				"data" : "roleName",
				render : function(data) {
					if(data!=null){
						return ' <div style="text-align:left;">'+data+'</div>';
					}else{
						return ' <div style="text-align:left;"></div>';
					}

				}
			} , {
				"title" : "操作",
				"width" : "20%",
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
			dom: 't<lp <"col-xs-12 text-right" i>><"clear">',
			"columnDefs" : [ {
				 "targets": [5],
			      render: function(data, type, row, meta) {
			            return '<a  class="upUser " name="userId" userId="'+row.userId+'">修改&nbsp</a>'+
             					   '<a  name="tableDel"  class="delUser" userId="'+row.userId+'">删除&nbsp</a>'
			      }
			} ],
			"fnDrawCallback": function () {
				setChecked();
	        }
		});

		/*$('#staffmange tbody').on('click', 'tr', function() {
			 var tr = $(this).closest('tr');
			 //获取点击td
		     var clickTd = $(this).find("td").index($(this));
				if(clickTd != 0){
				// 多选
				if ($(this).parents("tr").find(":checkbox").checked) {
					$(this).removeClass('selected');
					$(this).find(":checkbox").prop('checked', false);
					for(var i=0; i<checkedIds.length; i++){
			             if($(this).parents("tr").find(":checkbox").attr("value") == checkedIds[i]){
			             checkedIds.splice(i, 1);
			             data.splice(i,1);
			             }
			         }
				} else {
					$(this).addClass('selected');
					$(this).find(":checkbox").prop('checked', true);
					 checkedIds.push($(this).find(":checkbox").attr("value"));
			         data.push($('#batchCheckTable').DataTable().rows(tr).data())
				}
				}
		});*/
		//查询无结果时 隐藏
		$('#batchCheckTable').on('xhr.dt', function ( e, settings, json, xhr ) {
			if(json != null && json.listData !=null){
				if(json.listData.length == 0){
					$("#batchCheckList").hide();
					$("#noBatchResult").show();
					$("#form_del_staff").hide();
				}else{
					$("#batchCheckList").show();
					$("#noBatchResult").hide();
					$("#form_del_staff").show();
				}
			}
	    });

		//所在行修改点击事件
		$("#batchCheckTable").on("click", ".upUser",function() {
			var userId =$(this).attr("userId");
			var userName1 = $("#userName").val();
			var userId1 = $("#userId").val();
			var email1 = $("#email").val()
			var roleId1 = $("#role").val()
			var roleName1 = $("#role").find("option:selected").text();
			window.location.href=path+"/system/staffedit?userId="+userId+"&userName1="+encodeURI(encodeURI(userName1))+"&userId1="+userId1+"&email1="+email1+"&roleId1="+roleId1+"&roleName1="+encodeURI(encodeURI(roleName1));
		})
		//所在行删除按钮点击事件
		$("#batchCheckTable").on("click", ".delUser",function() {
			//层级关系寻找userId
			var userId=$(this).attr("userId");
	        var user = ConstMsg.BIZ_CODE_SHUR_DELETE()+ConstMsg.HTML_MSG_NAME_USER()+userId+"?";
	        Globals.msgboxJS.Confirm(user,function(){
	        	 //调用ajax
				var  ajaxOptions = {
							async:false,
				            url:"system/deleteFlg",
				            type:"post",
				            data:{
				            	userId:userId
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
		});
	}

	//全选事件
	 $("#selectAll").click(function(){
		 var input = $('input:checkbox[type="checkbox"]');
		 var tr = $(input).closest('tr');
		 if ($("#selectAll").prop("checked")) {
			  tr.addClass('selected');
		      input.prop("checked", true);
		      input.each(function(){
		    	  var rt = $(this).closest('tr')
		    	  if($(this).val() != "on"){
		    	  checkedIds.push($(this).val());
				  data.push($('#batchCheckTable').DataTable().rows(rt).data())
		    	  }
		      })
		 } else {
			  tr.removeClass('selected');
		      input.prop("checked", false);
		      input.each(function(){
		    	  var inpval = $(this).val();
		    	  for(var i=0; i<checkedIds.length; i++){
			         if(inpval == checkedIds[i]){
			         checkedIds.splice(i, 1);
			          data.splice(i,1);
			           }
			        }
		      })


		}
    });
	//table行点击事件
	 $('#batchCheckTable tbody').on('click', 'td', function() {
			//获取行
			var tr = $(this).closest('tr');
			// 多选
		 	var clickTd = $(this).parents("tr").find("td").index($(this));
		 	//操作一列无法选中
		 	if(clickTd!=5 && clickTd!=0){
		 		if ($(this).parents("tr").find(":checkbox")[0].checked) {
					$(this).parents("tr").removeClass('selected');
					$(this).parents("tr").find(":checkbox").prop('checked', false);
					 for(var i=0; i<checkedIds.length; i++){
				            if($(this).parents("tr").find("input").attr("value") == checkedIds[i]){
				            checkedIds.splice(i, 1);
				            data.splice(i,1);
				            }
				        }
		 		} else {
					$(this).parents("tr").addClass('selected');
					$(this).parents("tr").find(":checkbox").prop('checked', true);
					//添加数据
			   	 	checkedIds.push($(this).parents("tr").find("input").attr("value"));
			   	 	data.push($('#batchCheckTable').DataTable().rows(tr).data())
				}
		 		if($("input[name='checkbox']:checked").length==$("input[name='checkbox']").length){
					$('#selectAll').prop("checked",true);
				}else{
					$('#selectAll').prop("checked",false);
				}
		 	}

	});

	//翻页后设置是否选中
	    function setChecked(){
	       var $boxes = $("#staffmange input:checkbox[name='checkbox']");
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
	       if($("input[name='checkbox']:checked").length==$("input[name='checkbox']").length){
				$('#selectAll').prop("checked",true);
			}else{
				$('#selectAll').prop("checked",false);
			}
	    }


	// 表格外删除监听
	$('#form_del_staff').on('click', function() {
		delStaff();
	});

	function delStaff() {
		var userValue = new Array();
		for(var i = 0;i<data.length;i++){
			/*userValue.push(data[i].userId)*/
			userValue.push(data[i][0].userId)
		}
		//将userId放入集合用逗号隔开
		var  userId=  userValue.join(",");

		if(data.length == 0){
			Globals.msgboxJS.ToastrWarning(ConstMsg.HTML_MSG_CHOOSE_USER());
			return;
		}else{
			var del = ConstMsg.CONFIRM_DELETE()+ConstMsg.HTML_MSG_CHOOSE_CITEM()+"?";
	        Globals.msgboxJS.Confirm(del,function(){
	        	 //调用ajax
				var  ajaxOptions = {
							async:false,
				            url:"system/deleteAllFlg",
				            type:"post",
				            data:{
				            	userId:userId
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
		}
	};

/*******************************************Msg区域****************************************/
	//删除msg
	function _deleteSuccess(json) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
			    //删除成功，报msg
				case ConstCode.BIZ_CODE_DELETE_SUCCESS() :
				    Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DELETE_SUCCESS())
		    			//再次查询
				    	$("#query").click();
				    $('#selectAll').prop("checked",false);
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

	function GetConst(BizCode) {
		var bizCode= new Globals.constJS();
		return bizCode.GetCode(BizCode);
	}
/*******************************************Msg区域结束****************************************/
	// 构造方法
	function constructor() {
		_this = this;
		this.InitPage = _initPage;
	}
	return constructor;

})();

// 继承公共AJAX类
Globals.staffManageJS.prototype = new Globals.comAjaxJS();