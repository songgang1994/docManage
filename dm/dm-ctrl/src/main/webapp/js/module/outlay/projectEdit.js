/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：课题新增/编辑js
 */
Namespace.register("Globals.projectEditJS");

Globals.projectEditJS = (function() {
	var _this = null;
	var timeID;//计时器
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	//课题详细画面初期化处理
	function _initProjectEdit() {
		_ProjectFormInit();
    	//初期化处理年度和分类下拉框
		_ProjectSelect();
		//所属部门新增初始化
		_DeptAdd();
		//法人新增
		_SubjectLegalAdd();
		//新增确认
		$("#projectAdd").bind("click", function(){
			_ProjectAdd();
		});
		//编辑确认
		$("#projectEdit").bind("click", function(){
	           _ProjectEdit();
		});
		//返回
		$("#projectCancel").bind("click",function(){
			 var projectcancel = ConstMsg.CONFIRM_BACK();
	            Globals.msgboxJS.Confirm(projectcancel,function(){
	            	var deptNames=$("#deptNames1").val();
	        		var fyYear=$("#fyYear1").val();
	        		var deptId=$("#deptId1").val();
	        		var projectType=$("#projectType1").val();
	        		var projectName=$("#projectName1").val();
	        		window.location.href =path+"/outlay/list?fyYear="+fyYear+"&deptNames="+encodeURI(encodeURI(deptNames))+"&deptId="+deptId+"&projectName="+encodeURI(encodeURI(projectName))+"&projectType="+projectType;
	            },function(){
	              return;
	           })
		});
	}
	//form表单初期化处理
	function _ProjectFormInit(){
		//表单验证
    	$('#projectForm').validate({
    	    errorElement: 'span',
    	    errorClass: 'help-block',
    	    focusInvalid: false,
    	    ignore: "",
    	    rules:
    	    {
    	    	fyYear:{
    	    		 required: true
    	    	},
    	    	projectNo:
    	    	{
		            required: true
	        	},
	        	projectName:
	        	{
	        		required: true
	        	},
	        	projectGoal:
	        	{
	        		required: true
	        	},
	        	deptIds:{
	        		required: true
	        	},
	        	planTimes:
	        	{
	        		required: true,
	        		number: true
	        	},
	        	projectType:
	        	{
	        		required: true
	        	},
	        	contents:
	        	{
	        		required: true
	        	},
	        	legalIds:
	        	{
	        		required: true
	        	},
	        	percentages:
	        	{
	        		legalCount:true
	        	}
    	    },
        messages:
        {
        	fyYear:{
        		required:ConstMsg.HTML_MSG_CHOOSE_YEARS()
	    	},
        	projectNo:
        	{
	   		 required: ConstMsg.HTML_MSG_MUSTINPUT_PROJECTNUM()
    		},
    		projectName:
        	{
        		required: ConstMsg.HTML_MSG_MUSTINPUT_THEME()
        	},
        	projectGoal:
        	{
        		required: ConstMsg.HTML_MSG_MUSTINPUT_TARGET()
        	},
        	deptIds:{
        		required: ConstMsg.HTML_MSG_CHOOSE_DEPARTMENT()
        	},
    		planTimes:
        	{
    			required: ConstMsg.HTML_MSG_MUSTINPUT_PLANMANUAL(),
        		number: ConstMsg.HTML_MSG_MUSTNUM_PLANMANUAL()
        	},
        	projectType:
        	{
        		required:ConstMsg.HTML_MSG_CHOOSE_PROJECTTYPE()
        	},
        	contents:
        	{
        	required: ConstMsg.HTML_MSG_MUSTINPUT_CONTENT()
        	},
        	legalIds:
        	{
        	required: ConstMsg.HTML_MSG_CHOOSE_LEGAL()
        	},
        	percentages:
        	{
        		legalCount:ConstMsg.HTML_MSG_PROPORTION_LEGAL()
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
    	//法人验证
    	jQuery.validator.addMethod("legalCount", function () {
    		var returnVal = true;
    		var count=parseInt(ConstText.HTML_MSG_NUM_ZERO());
    		var percentages;
    		//遍历法人
    		$("#subjectLegal input").each(
    		           function(i) {
    		        	   count=count+parseInt($(this).val());
    		        	   //法人比例集合拼接
    		        	   if(i==ConstText.HTML_MSG_NUM_ZERO()){
    		        		   percentages = $(this).val();
    		        	   }else{
    		        		   percentages=percentages+","+$(this).val();
    		        	   }
    		             }
    		)
    		$("#percentages").val(percentages);
    		if(count!=ConstText.HTML_MSG_NUM_OHB()){
    			returnVal = false;
    		}
    	    return returnVal;
    	});
    	$("#subjectLegal input").each(function(i){
    		 $("input[name = 'subjectlegal"+i+"']").rules("add", {
          	   required: true,
          	   number :true,
          	   messages: {
          		   required:ConstMsg.HTML_MSG_LEGAL_PROPORTION(),
          		   number:ConstMsg.HTML_MSG_MUSTINPUT_NUMBER()
          	   }
             });
    	})
	}
	//下拉框初期化处理
	function _ProjectSelect(){
		var project_type = $("#project_type");
		//年度下拉框
    	Globals.utilJS.GetFyYear();
    	//课题分类下拉框
    	Globals.utilJS.GetProjectTypeList(project_type)
    	var fyYear = $("#fy_Year").val();
    	//设置默认选中值
    	if(fyYear!=null&&fyYear!='' &&fyYear.length!=ConstText.HTML_MSG_NUM_ZERO()){
    		$("#fyYear").find("option[value="+fyYear+"]").attr("selected",true);
    	}
	}
	//所属部门新增初始化
	function _DeptAdd(){
		$("#deptAdd").click(function(){
			var depart = new Globals.departSearchPopupJS();
			depart.ShowModal('checkbox',function(data){
				//移除验证
				$("#deptIds").closest('.form-group').removeClass('has-error');
				$("#deptIds-error").remove();
				var deptIds;
				$("#deptIds").val("");
				$("#deptNames").empty();
				//添加选中部门Id和名称
				for(var i=0;i<data.length;i++){
					if(i==ConstText.HTML_MSG_NUM_ZERO()){
						deptIds = data[i][0].deptId;
						$("#deptNames").append($("<lable>"+data[i][0].deptName+"</lable>"));
					}else{
						deptIds = deptIds+","+data[i][0].deptId;
						$("#deptNames").append($("<br><lable>"+data[i][0].deptName+"</lable>"));
					}
				}
				$("#deptIds").val(deptIds);
			});
		})
	}
	//法人新增
	function _SubjectLegalAdd(){
		$("#subjectLegalAdd").click(function(){
			var legal = new Globals.legalSearchPopupJS();
			legal.ShowModal('checkbox',function(data){
				$("#legalIds").closest('.form-group').removeClass('has-error');
				$("#legalIds-error").remove();
				$("#percentages").closest('.form-group').removeClass('has-error');
				$("#percentages-error").remove();
				var legalIds;
				$("#legalIds").val("");
				$("#subjectLegal").empty();
				for(var i=0;i<data.length;i++){
					if(i==ConstText.HTML_MSG_NUM_ZERO()){
						legalIds = data[i][0].value;
					} else {
						legalIds = legalIds+","+data[i][0].value;
					}
						//添加法人
						$("#subjectLegal").append($("<div class='col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding'>"+
																	"<div class='form-group col col-sm-4 col-md-4 col-lg-4 col-xs-12 no-padding'>"+
																	"<div class='col col-sm-5 col-md-5 col-lg-5 col-xs-12 no-padding'>"+
																	"<lable style='width:20%'>"+data[i][0].dispName+"</lable>"+
																	"</div><lable>比例&nbsp;</lable>"+
																	"<input type='text' style='width:30%' name='subjectlegal"+i+"' />"+
																	"</div><div class='col-sm-1 col-md-1 col-lg-1 col-xs-12 no-padding'><lable>%</lable></div><div>"));
							//法人验证添加
						   $("input[name = 'subjectlegal"+i+"']").rules("add", {
			            	   required: true,
			            	   number :true,
			            	   messages: {
			            		   required:ConstMsg.HTML_MSG_LEGAL_PROPORTION(),
			            		   number:ConstMsg.HTML_MSG_MUSTINPUT_NUMBER()
			            	   }
			               });
				}
				$("#legalIds").val(legalIds);
			});
		})
	}
	// 新增课题
	function _ProjectAdd() {
		//check表单验证
		if ($("#projectForm").valid() == false) {
			return;
		}
		var ajaxOptions = {
			type: "post",
			url : 'outlay/add',// 地址
			contentType: "application/json",
			data :JSON.stringify($('#projectForm').serializeJSON()),
			success : function(data) {
				_cb_addProject_successFun(data);
			}, // 请求成功时处理
			error: function(){
			}
		};

		_this.sendAjax(ajaxOptions);
	}
	//修改课题
	function _ProjectEdit(){
		//check表单验证
		if ($("#projectForm").valid() == false) {
			return;
		}
		var ajaxOptions = {
				type: "post",
				url : 'outlay/update',// 地址
				contentType: "application/json",
				data :JSON.stringify($('#projectForm').serializeJSON()),
				success : function(data) {
					_cb_updProject_successFun(data);
				}, // 请求成功时处理
				error: function(){
				}
			};

		_this.sendAjax(ajaxOptions);
	}
	//新增返回成功的场合
	function _cb_addProject_successFun(json) {
		//新增成功的场合
		if(json.data!=null){
			if (json.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()) {
				Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_INSERT_SUCCESS());
				_cancel();
			//新增失败的场合
			} else {
				Globals.msgboxJS.ToastrWarning(ConstMsg.PROJECTNO_REPEAT());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_INSERT_FAILED());
		}

	}
	//编辑返回成功的场合
	function _cb_updProject_successFun(json){
		//编辑成功的场合
		if(json.data!=null){
			if (json.data.bizCode ==   ConstCode.BIZ_CODE_SUCCESS()) {
				Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_UPDATE_SUCCESS());
				 _cancel();
			} else {
				//编辑失败的场合
				Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED());
		}
	}
	//返回函数
	function _cancel(){
		var deptNames=$("#deptNames1").val();
		var fyYear=$("#fyYear1").val();
		var deptId=$("#deptId1").val();
		var projectType=$("#projectType1").val();
		var projectName=$("#projectName1").val();
		timeID=setTimeout(function(){
			clearTimeout(timeID);
			window.location.href =path+"/outlay/list?fyYear="+fyYear+"&deptNames="+encodeURI(encodeURI(deptNames))+"&deptId="+deptId+"&projectName="+encodeURI(encodeURI(projectName))+"&projectType="+projectType;
		},ConstText.HTML_MSG_NUM_MS());
	}
	// 构造方法
	function constructor() {
		_this = this;

		//课题详细画面初期化处理
		this.InitProjectEdit = _initProjectEdit;
	}

	return constructor;
})();
//继承公共AJAX类
Globals.projectEditJS.prototype = new Globals.comAjaxJS();
