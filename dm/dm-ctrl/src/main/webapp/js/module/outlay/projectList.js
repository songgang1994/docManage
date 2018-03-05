/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：课题一览js
 */
Namespace.register("Globals.projectListJS");

Globals.projectListJS = (function() {

	//User类实例，回调函数中使用，不可直接用this
	var _this = null;
	var projectTable=null;
	var timeID;//计时器
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	// 课题一览画面初期化
	function _InitProject(){
		//form表单初期化处理
		_ProjectForm();
		//删除事件
	 	$("#projectList").on('click','.btn-delete',function(){
	 		var fyYear =$(this).attr("fyYear");
	 		var projectNo =$(this).attr("projectNo");
	 	    var projectName = ConstMsg.BIZ_CODE_SHUR_DELETE()+projectNo+"?";
            Globals.msgboxJS.Confirm(projectName,function(){
    	 		_ProjectDelete(fyYear,projectNo);
            },function(){
              return;
            })
	 	})
	 	//新增事件
	 	$("#projectAdd").bind("click",function(){
	 		var fyYear = $("#fyYear").val();
			var deptNames = $("#deptNames").val();
			var deptId = $("#deptId").val();
			var projectName = $("#projectName").val();
			var projectType =$("#projectType").val();
	 		window.location.href=path+"/outlay/addInit?fyYear="+fyYear+"&deptNames="+encodeURI(encodeURI(deptNames))+"&deptId="+deptId+"&projectName="+encodeURI(encodeURI(projectName))+"&projectType="+projectType;
		});
		//编辑事件
	 	$("#projectList").on('click','.btn-upd',function(){
	 		var fyYear =$(this).attr("fyYear");
	 		var projectNo =$(this).attr("projectNo");
	 		_ProjectUpd(fyYear,projectNo);
	 	})
		//工时填写
	 	$("#projectList").on('click','.btn-write',function(){
	 		var projectNo =$(this).attr("projectNo");
	 		var userId = $(this).attr("userId");
	 		var fyYear = $(this).attr("fyYear")
	 		var flug = 1;
	 		_TaskTimeInit(projectNo,userId,fyYear,flug);
	 	})
		// 导出事件
		$("#projectExport").bind("click",function(){
			   var projectExport = ConstMsg.FILE_EXPORT();
	            Globals.msgboxJS.Confirm(projectExport,function(){
	            	_ProjectExport();
	            },function(){
	              return;
	            })
		});
		//导入事件
		$("#allImport").bind("click",function(){
			var fyyear = $('#fyYear option:selected').val()
			$("#fyyear").attr({"value": fyyear})
			_ProjectImport();
		});
    	//批量导入确定按钮
	   _ProjectImportInit();
	   //所属部门初期化处理
	   _departInit();
	   //批量导入关闭
	   _ProjectImportClose();
	   //查询事件
	   $("#projectTableSearch").click(function(){
			//check表单验证
			if ($("#selectForm").valid() == false) {
				return;
			}
			var pageLength = Globals.utilJS.getCookie(ConstText.PROJECT_PAGE_LENGTH());
			_ProjectList(pageLength);
			projectTable.load();
	   })
	   //返回事件
		$("#projectListCancel").bind("click",function(){
			Globals.utilJS.ReturnHomePage();
		});
		//年度和课题分类初始化
		_ProjectSelect();
	}
	//编辑后画面初始化
	function _ProjectInit(){
		   var deptId = $("#deptId").val();
		   var projectName = $("#projectName").val();
		   var projectType =$("#project_type").val();
		   var fyYear = $("#fyYear").val();
		   if(fyYear.length!=0&&projectName.length!=0&&projectType.length!=0&&deptId.length!=0){
			   $("#projectTableSearch").click();
		   }
		   clearTimeout(timeID);
	}
	//form表单验证初期化
	function _ProjectForm(){
		$('#selectForm').validate({
    	    errorElement: 'span',
    	    errorClass: 'help-block',
    	    focusInvalid: false,
    	    ignore: "",
    	    rules:
    	    {
    	    	deptId:{
    	    		 required: true
    	    	},
    	    	fyYear:
    	    	{
		            required: true
	        	},
	        	projectName:
	        	{
	        		required: true
	        	},
	        	projectType:
	        	{
	        		required: true
	        	}
    	    },
        messages:
        {
        	deptId:{
        		required:ConstMsg.HTML_MSG_CHOOSE_USERDEPART()
	    	},
	    	fyYear:
        	{
	   		 required: ConstMsg.HTML_MSG_CHOOSE_YEARS()
    		},
    		projectName:
        	{
        		required: ConstMsg.HTML_MSG_MUSTINPUT_THEME()
        	},
        	projectType:
        	{
        		required: ConstMsg.HTML_MSG_CHOOSE_PROJECTTYPE()
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
	//下拉框初期化处理
	function _ProjectSelect(){
		var project_type = $("#project_type");
    	Globals.utilJS.GetFyYear();
    	Globals.utilJS.GetProjectTypeList(project_type)
    	var fyYear = $("#fy_Year").val();
    	if(fyYear!='' &&fyYear.length!=ConstText.HTML_MSG_NUM_ZERO()){
    		$("#fyYear").find("option[value="+fyYear+"]").attr("selected",true);
    	}
    	//年度chang事件
		$("#fyYear").change(function(){
		       var selected=$(this).children('option:selected').val()
		       if(selected!= "" ){
		    	   //设置批量导入按钮不可点击
		    	   $("#allImport").attr({"disabled": false})
		       }else{
		    	   //设置批量导入按钮可已点击
		    	   $("#allImport").attr({"disabled": true})
		       }
		   });
		 timeID =setTimeout(function(){
			_ProjectInit();
		},ConstText.HTML_MSG_NUM_OHB());
	}
	//批量导入初期化处理
	function _ProjectImportInit(){
		$("#submitOK").bind("click",function(){
			//首先验证文件格式
	        var fileName = $('#file_input').val();
	        if (fileName === '') {
	        	Globals.msgboxJS.ToastrWarning(ConstMsg.SELECT_FILE());
	            return false;
	        }
	        var fileType = (fileName.substring(fileName
	                .lastIndexOf(".") + 1, fileName.length))
	                .toLowerCase();
	        if (fileType !== 'xls' && fileType !== 'xlsx') {
	        	Globals.msgboxJS.ToastrWarning(ConstMsg.FILE_TYLE_ERROR());
	            return false;
	        }

	        $("#file_form").ajaxSubmit(function(data){
	        	_cb_fileForm_successFun(data);
	        	});
		});
	}
	function _cb_fileForm_successFun(data){
		//批量导入成功
		if(data.data!=null){
			if (data.data.bizCode ==ConstCode.BIZ_CODE_SUCCESS()) {
				var error;
				if(data.data.errorTerm.length > ConstText.HTML_MSG_NUM_ZERO()){
					for(var i=0;i<data.data.errorTerm.length;i++){
						if(i==ConstText.HTML_MSG_NUM_ZERO()){
							error = data.data.errorTerm[i];
						}else{
							error = error+","+data.data.errorTerm[i];
						}
					}
					$('#fileError').text(ConstMsg.File_ROW_ERROR()+error);
				}else{
					$('#fileError').text("");
				}
				$("#subjectImport").modal("hide");
				$("#projectTableSearch").click();
			//批量导入失败
			} else {
				Globals.msgboxJS.ToastrError(ConstMsg.FILE_IMPORT_ERROR());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.FILE_IMPORT_ERROR());
		}
	}
	//所属部门初期化处理
	function _departInit(){
		$("#deptSearch").click(function(){
			var depart = new Globals.departSearchPopupJS();
			depart.ShowModal('radio',function(data){
				var deptId;
				var deptNames;
				for(var i=ConstText.HTML_MSG_NUM_ZERO();i<data.length;i++){
					if(i==ConstText.HTML_MSG_NUM_ZERO()){
						deptId = data[i][0].deptId;
						deptNames = data[i][0].deptName;
					}else{
						deptId = deptId+","+data[i][0].deptId;
						deptNames = deptNames+" " +data[i][0].deptName;
					}
				}
				$("#deptId").val(deptId);
				$("#deptNames").val(deptNames);
				//移除验证
				$("#deptId").closest('.form-group').removeClass('has-error');
				$("#deptId-error").remove();
			});
		})
	}
	//批量导入关闭
	function _ProjectImportClose(){
		$("#importClose").click(function(){
			$("#subjectImport").modal("hide");
		})
	}
	//课题一览列表处理
	function _ProjectList(pageLength){
		var table = $('#projectList');
		projectTable=table.dataTable({
			ordering : false,// 不可排序
			bDestroy:true,
			serverSide:true,//后台分页
			autoWidth:false,
			ajax : {
				type: "post",
				url : "outlay/subjectList",
				dataSrc : "listData",
				data :$('#selectForm').serializeJSON(),
				error:function(){
				}
			},
			"columns" : [
			{
				"title" : "序号",
				"width" : "5%",
				"className":"text-center",
				"data" : null,
				"targets":ConstText.HTML_MSG_NUM_ZERO()
			}, {
				"title" : "FY年度",
				"width" : "8%",
				"className":"text-center",
				"data" : "fyYear",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}, {
				"title" : "课题编号",
				"width" : "12%",
				"className":"text-center",
				"data" : "projectNo",
				render : function(data) {
					return ' <div class="overflow-custom" title="'+data+'" style="text-align:left;">'+data+'</div>';
				}
			}, {
				"title" : "主题",
				"width" : "13%",
				"className":"text-center",
				"data" : "projectName",
				render : function(data) {
					return ' <div class="overflow-custom" title="'+data+'" style="text-align:left;">'+data+'</div>';
				}
			}, {
				"title" : "所属部署",
				"width" : "10%",
				"className":"text-center",
				"data" : "deptNames",
				render : function(data) {
					return ' <div style="text-align:left;">'+data+'</div>';
				}
			}, {
				"title" : "分类",
				"width" : "10%",
				"className":"text-center",
				"data" : "dispName",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}, {
				"title" : "计划人工",
				"width" : "10%",
				"className":"text-center",
				"data" : "planTimes",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}, {
				"title" : "目标",
				"width" : "14%",
				"className":"text-center",
				"data" : "projectGoal",
				render : function(data) {
					return ' <div class="overflow-custom" title="'+data+'" style="text-align:left;">'+data+'</div>';
				}
			}, {
				"title" : "操作",
				"width" : "18%",
				"className":"text-center",
				"data" : null
			} ],
			"aLengthMenu" : [ [ 5,10,15, 20, -1 ], [ 5,10,15, 20, "全部" ]],
			"pageLength" : pageLength,
			"pagingType" : "bootstrap_full_number",
			"language" : {
				"lengthMenu" : "_MENU_ ",
				 "info": "第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				"emptyTable" : "无可用数据",
				"infoEmpty" : "没有记录可以显示  ",
				"paginate" : {
					"previous" : "上一页",
					"next" : "下一页",
					"last" : "最后一页",
					"first" : "第一页"
				}
			},
			dom: 't<lp><"col-xs-12 text-right" i><"clear">',
			"columnDefs" : [ {
				 "targets": [8],
			      render: function(data, type, row, meta) {
			            return '<div style="text-align:left;"><a name="projectUpd" class="btn-upd" fyYear="'+row.fyYear+'" projectNo="'+row.projectNo+'">修改&nbsp</a>'+
             					   '<a name="projectDelete" class="btn-delete" fyYear="'+row.fyYear+'" projectNo="'+row.projectNo+'">删除&nbsp</a>'+
             					   '<a name="projectWrite" class="btn-write" projectNo="'+row.projectNo+'" userId="'+row.creator+'" fyYear="'+row.fyYear+'">工时录入</a></div>';
			      }
			} ],
			 "fnDrawCallback":function(){
	                var api =this.api();
	                var startIndex= api.context[ConstText.HTML_MSG_NUM_ZERO()]._iDisplayStart;        //获取到本页开始的条数 　
	                 api.column(ConstText.HTML_MSG_NUM_ZERO()).nodes().each(function(cell, i) {
	                	 	$(cell).removeClass("text-center");
	                        cell.innerHTML = startIndex + i + 1;
	                        });
	               //按钮设定
	         	Globals.utilJS.ActionSetting();
	        }
		});
		//查询无结果时 隐藏
		$('#projectList').on('xhr.dt', function ( e, settings, json, xhr ) {
			if(json != null && json.listData !=null){
				if(json.listData.length == 0){
					$("#projectTable").hide();
					$("#noProjectResult").show();
					$("#projectExport").hide();
				}else{
					$("#projectTable").show();
					$("#noProjectResult").hide();
					$("#projectExport").show();
				}
			}
	    } );
	}
	//课题删除
	function _ProjectDelete(fyYear,projectNo){
		var ajaxOptions = {
				type: "post",
				url : 'outlay/delete',// 地址
				data :{
					fyYear:fyYear,
					projectNo:projectNo
				},
				success : function(data) {
					_cb_deleteProject_successFun(data);
				}, // 请求成功时处理
				error:function(){
				}
			};

		_this.sendAjax(ajaxOptions);
	}
	//删除成功的场合
	function _cb_deleteProject_successFun(data){
		//删除成功
		if(data.data!=null){
			if (data.data.bizCode ==ConstCode.BIZ_CODE_SUCCESS()) {
				Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DELETE_SUCCESS());
				 $("#projectTableSearch").click();
			//删除失败
			} else {
				Globals.msgboxJS.ToastrWarning(ConstMsg.BIZ_CODE_TRCANT_DELETE());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_DELETE_DOCUMENT_FAILED());
		}
	}
	//课题编辑画面迁移
	function _ProjectUpd(fyYear,projectNo){
		   var deptNames = $("#deptNames").val();
		   var deptId = $("#deptId").val();
		   var projectName = $("#projectName").val();
		   var projectType =$("#projectType").val();
			window.location.href =path+"/outlay/updInit/"+fyYear+"/"+projectNo+"/"+deptNames+"/"+deptId+"/"+projectName+"/"+projectType;
	}
	//批量导入modal画面
	function _ProjectImport(){
		var fyYear = $("#fyYear").val();
		$("#fyYear1").text(fyYear);
		$("#subjectImport").modal("show");
	}
	//导出画面
	function _ProjectExport(){
		var ajaxOptions = {
			url : "outlay/export",
			type : "POST",
			contentType: "application/json",
			data :JSON.stringify($('#selectForm').serializeJSON()),
			success : function(data) {
				if(data.data!=null){
					if (data.data.bizCode ==ConstCode.BIZ_CODE_SUCCESS()) {
						window.location=path+"/file/export?fileName="+encodeURI(encodeURI(data.data.fileName));
						//导出成功
						Globals.msgboxJS.ToastrSuccess(ConstMsg.FILE_EXPORT_SUCCESS());
					} else {
						//导出失败
						Globals.msgboxJS.ToastrError(ConstMsg.FILE_EXPORT_ERROR());
					}
				} else {
					//导出失败
					Globals.msgboxJS.ToastrError(ConstMsg.FILE_EXPORT_ERROR());
				}
			},
			error : function(data) {
				Globals.msgboxJS.ToastrError(ConstMsg.FILE_EXPORT_ERROR());
			}
		};

		_this.sendAjax(ajaxOptions);
	}
	//工时填写画面迁移
	function _TaskTimeInit(projectNo,userId,fyYear,flug){
		var deptNames = $("#deptNames").val();
		var deptId = $("#deptId").val();
		var projectName = $("#projectName").val();
		var projectType =$("#projectType").val();
		window.location.href =path+"/outlay/projectHourEdit?projectNo="+projectNo+"&userId="+userId+"&fyYear="+fyYear+"&flug="+flug+"&deptNames="+encodeURI(encodeURI(deptNames))+"&deptId="+deptId+"&projectName="+encodeURI(encodeURI(projectName))+"&projectType="+projectType;
	}
	// ========================================================
	// 构造方法
	function constructor() {
		_this = this;

		//画面初始化
		this.InitProject = _InitProject;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.projectListJS.prototype = new Globals.comAjaxJS();