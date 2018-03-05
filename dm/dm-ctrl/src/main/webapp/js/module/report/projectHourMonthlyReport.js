/**
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：李辉--%>
 * <%-- 模块名：课题人工统计月度报表js --%>
 */
Namespace.register("Globals.projectHourMonthlyReportJS");

Globals.projectHourMonthlyReportJS = (function() {

	var _this = null;
	var handle;//计时器
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	// 初始化人员检索pop
	function _InitProjectHourMonthlyReport() {

		//设置年月下拉框
		_ProjectDate();

		//所属部门初期化处理
		 _departInit();
		 //部门form表单验证
		 _DeptFormInit();
		// 导出事件
		$("#projectHourMonthlyReportExport").bind("click",function(){
				_ProjectHourMonthlyReportExport();
		});
			//生成报表事件
			$("#projectHourMonthlysubmit").click(function(){
				var deptNumber=[];
				var deptIdList=[];
				$("#deptNumberWrite .deptNumber").each(function(){
					deptNumber.push($(this).val());
				})
				$("#deptNumber").val(deptNumber);
				$("#deptNumberWrite .deptId").each(function(){
					deptIdList.push($(this).val());
				})
				$("#deptIdList").val(deptIdList);
				var dataInfo = $("#dateInfo").val();
				$("#dateInfo1").val(dataInfo);
				//check表单验证
				if ($("#file_form").valid() == false) {
					return;
				}
				   $("#file_form").ajaxSubmit(function(data){
						   _formClear();
						   _cb_fileForm_successFun(data);
			        });
			});
			//关闭事件
			$("#projectHourMonthlyClose").click(function(){
				_formClear();
				$("#projectHourMonthlyExport").modal("hide");
			})

		 //表单提交
		$("#projectSearch").click(function(){
			  $("#selectForm").ajaxSubmit(function(data){
					if(data != null && data.listData !=null){
						if(data.listData.length == 0){
							$("#projectTable").hide();
							$("#noProjectResult").show();
							$("#projectHourMonthlyReportExport").hide();
						}else{
							var deptNames = data.listData[0].deptNameList;
							var deptIds = data.listData[0].deptIdList;
							// 清空数据
							$("#deptNumberWrite").empty();
							 //当部门Id存在的场合
							if(deptNames!=null){
									$("#projectDate").val(data.listData[0].dateInfo);
									for(var i=0;i<deptNames.length;i++){
									   $("#deptNumberWrite").append($("<div class='col col-sm-12 col-md-12 col-lg-12 col-xs-12'><div class='col col-sm-12 col-md-3 col-lg-3 col-xs-12'><input class='deptId' type='hidden' value="+deptIds[i]+">"+
											   												"<span style='width:100%'>"+
												  											deptNames[i]+
									   														"人数</span></div>" +
									   														"<div class='form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 '>" +
									   														"<input name='deptNumber"+i+"' class='deptNumber' type='text' style='width: 100%' /></div></div>"));

										//部门人数验证添加
									   $("input[name = 'deptNumber"+i+"']").rules("add", {
						            	   required: true,
						            	   number :true,
						            	   messages: {
						            		   required:ConstMsg.HTML_MSG_MUSTINPUT_DEPARTMRNTNUM(),
						            		   number:ConstMsg.HTML_MSG_MUSTINPUT_NUMBER()
						            	   }
						               });
									}
							}
							 _InitTable(data.listData);
							$("#projectTable").show();
							$("#noProjectResult").hide();
							$("#projectHourMonthlyReportExport").show();
						}
					}
		        }
			  );
		})
		//返回事件
		$("#projectHourMonthlyCancel").click(function(){
			Globals.utilJS.ReturnHomePage();
			})
	}

	function _ProjectDate(){
		Globals.utilJS.YearMonthSelect("#dateInfo");
		 var date=new Date;
		 var year=date.getFullYear();
		 var month=date.getMonth()+1;
		 month =(month<10 ? "0"+month:month);
		 var mydate = year+"年"+month+"月";
		 $("#dateInfo").find("option[value="+mydate+"]").attr("selected",true);
	}
	//所属部门初期化处理
	function _departInit(){
		$("#deptSearch").click(function(){
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
				$("#deptId").val(deptId);
				$("#deptNames").val(deptNames);
				$("#deptNames").attr("title",deptNames);
			});
		})
	}
	// 初始化datatable
	function _InitTable(result) {
		var table = $('#projectHourMonthlyTable');
		table.dataTable({
			paging: false, // 禁止分页
			ordering : false,// 不可排序
			bDestroy:true,
			autoWidth:false,
			data:result,
			"columns" : [
			{
				"title" : "序号",
				"width" : "5%",
				"className":"text-center",
				"data" : null,
				"targets":0
			}, {
				"title" : "部门",
				"width" : " calc( 100% - 1.2em )",
				"className":"text-center",
				"data" : "deptName",
				render : function(data) {
					return ' <div class="overflow-custom" title="'+data+'" style="text-align:left;">'+data+'</div>';
				}
			}, {
				"title" : "分类",
				"width" : " calc( 100% - 1.2em )",
				"className":"text-center",
				"data" : "dispName",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}, {
				"title" : "主题",
				"width" : " calc( 100% - 1.2em )",
				"className":"text-center",
				"data" : "projectName",
				render : function(data) {
					return ' <div class="overflow-custom" title="'+data+'" style="text-align:left;">'+data+'</div>';
				}
			}, {
				"title" : "内容",
				"width" : " calc( 100% - 1.2em )",
				"className":"text-center",
				"data" : "contents",
				render : function(data) {
					return ' <div class="overflow-custom" title="'+data+'" style="text-align:left;">'+data+'</div>';
				}
			}, {
				"title" : "课题编号",
				"width" : " calc( 100% - 1.2em )",
				"className":"text-center",
				"data" : "projectNo",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}, {
				"title" : "计划人工",
				"width" : " calc( 100% - 1.2em )",
				"className":"text-center",
				"data" : "planTimes",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}, {
				"title" : "工作时间",
				"width" : " calc( 100% - 1.2em )",
				"className":"text-center",
				"data" : "timesTotal",
				render : function(data) {
					if(data==null){
						data='-'
					}
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}],
			"pagingType" : "bootstrap_full_number",
			"language" : {
				"emptyTable" : "无可用数据",
				"infoEmpty" : "没有记录可以显示  "
			},
			dom: 't',
			 "fnDrawCallback":function(){
	                var api =this.api();
	                var startIndex= api.context[0]._iDisplayStart;        //获取到本页开始的条数 　
	                 api.column(0).nodes().each(function(cell, i) {
	                	 	$(cell).css("width","5%");
	                        cell.innerHTML = startIndex + i + 1;
	                        });
	                 if(result.length>=8){
	                	 $("#projectHourMonthlyTable  tbody").css("height","330px");
	                 } else {
	                	 $("#projectHourMonthlyTable  tbody").css("height","");
	                 }
	         }
		});
	}

	//导出画面
	function _ProjectHourMonthlyReportExport(){
		$("#projectHourMonthlyExport").modal("show");
	}
	//导出操作成功的场合
	function _cb_fileForm_successFun(data){
		if(data.data!=null){
			if(data.data.bizCode ==ConstCode.BIZ_CODE_SUCCESS()){
				window.location=path+"/file/export?fileName="+encodeURI(encodeURI(data.data.fileName));
				Globals.msgboxJS.ToastrSuccess(ConstMsg.FILE_EXPORT_SUCCESS());
				$("#projectHourMonthlyExport").modal("hide");
			} else {
				Globals.msgboxJS.ToastrError(ConstMsg.FILE_EXPORT_ERROR());
			}
		} else {
			Globals.msgboxJS.ToastrError(ConstMsg.FILE_EXPORT_ERROR());
		}
	}
	//form表单初期化处理
	function _DeptFormInit(){
		//表单验证
    	$('#file_form').validate({
    	    errorElement: 'span',
    	    errorClass: 'help-block',
    	    focusInvalid: false,
    	    ignore: "",
    	    rules:
    	    {
    	    },
        messages:
        {
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
	//清空表单
	function _formClear(){
		$("#deptNumberWrite .deptNumber").each(function(i){
				$(this).val("");
				$("#deptNumber"+i+"").closest('.form-group').removeClass('has-error');
				$("#deptNumber"+i+"-error").remove();
   	})
	}
	// ========================================================

	// 构造方法
	function constructor() {
		_this = this;
		this.InitProjectHourMonthlyReport = _InitProjectHourMonthlyReport;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.projectHourMonthlyReportJS.prototype = new Globals.comAjaxJS();