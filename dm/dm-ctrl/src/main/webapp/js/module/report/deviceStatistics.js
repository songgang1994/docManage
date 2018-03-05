/*
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：邵林军 --%>
 * <%-- 模块名：实验设备统计 --%>
 */
Namespace.register("Globals.deviceStatisticsJS");

Globals.deviceStatisticsJS = (function() {
	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	//========================= 初始化 设备预约（月） ===============================
	function _InitDeviceStatistics() {
		//设置年月下拉框
		Globals.utilJS.ThisYearMonthSelect("#startDt");
		Globals.utilJS.ThisYearMonthSelect("#endDt");
		//初始化form提交验证
		_InitFormValid();
		//按键监听
		_ButtonClickListener();
		//返回事件
		$("#deviceStatisticsBack").bind("click",function(){
			Globals.utilJS.ReturnHomePage();
		});
	}
	//========================= 初始化 设备预约（月）结束 ===============================

	//========================= 初始化表单验证  ===============================
	function _InitFormValid(){

		//判断结束时间是否大于开始时间的验证规则
		jQuery.validator.methods.compareDate = function(value, element, param) {
			var startDate = new Date(Date.parse(jQuery(param).val().replace('年','-').replace('月','-')));
			var endDate = new Date(Date.parse(value.replace('年','-').replace('月','-')));
			return	 startDate <= endDate
		};

		//表单提交验证
		$("#deviceStatisticsForm").validate({
			errorClass: 'help-block',
    	    focusInvalid: false,
    	    onfocusout:false,//关闭失去焦点时验证
    	    onkeyup:false,//关闭在 keyup 时验证。
    	    onclick:false,//关闭在点击复选框和单选按钮时验证。
			errorElement: "span",
			rules: {
				deviceNames: {
					required: true,
				},
				endDt: {
					compareDate:"#startDt"
				}
		    },
			messages: {
				deviceNames: {
					required:ConstMsg.HTML_FRM_CHOOSE_DEVICE(),//请选择设备
				},
				endDt: {
					compareDate:ConstMsg.HTML_FRM_PERIOD_START_GREATER_END(),//"期间结束时间必须大于期间开始时间"
				}
			},
			errorPlacement: function (error, element) {
				var p = error;
				if(error!=null){
					if(error.text()!=""){
						p = $("<p />").append(error);
					}
				}
				p.appendTo(element.parent());
			},
			highlight: function (element){ // hightlight error inputs
	            $(element)
	                .closest('.form-group').addClass('has-error'); // set error class to the control group
	        },
	        success: function (label){
	            label.closest('.form-group').removeClass('has-error');
	            label.remove();
	        }
		});
	}
	//========================= 初始化表单验证结束  ===============================

	// 按键监听
	function _ButtonClickListener() {
		// 监听按钮按钮点击事件
		$("button").on("click", function(e) {
			e.preventDefault();
			e.stopPropagation();

			switch (e.target.id) {
				case "searchDeviceStatistics"://查询数据
//					$("#deviceNos").val("10,16,123456789,11,1231223,13,213213,314,1111");
//					$("#deviceNames").val("2号设备,7号设备,1号设备,4号设备,3号设备,6号设备,8号设备,9号设备,5号设备");
//					$("#deviceNames").attr("title","2号设备,7号设备,1号设备,4号设备,3号设备,6号设备,8号设备,9号设备,5号设备")
					_SearchDeviceStatistics();
					break;
				case "exportDeviceStatistics"://导出数据
					_ExportDeviceStatistics();
					break;
				default:
					break;
				}
			});

		//设备选择popup
		$("#deviceSearch").on('click',function(){
			var device = new Globals.deviceSearchPopupJS();
			device.ShowModal('checkbox', function(data) {
				if (data.length > 0) {
					var deviceNos = data.map(function(x){
						return x[0].deviceNo;
					}).join(',');
					var deviceNames = data.map(function(x){
						return x[0].deviceName;
					}).join(',');
					//赋值
					$("#deviceNames").val(deviceNames);
					$("#deviceNames").attr("title",deviceNames);
					$("#deviceNos").val(deviceNos);
					$("#deviceNames").prev().attr('title',deviceNames);
					//清除表单验证
					$("#deviceNames").closest('.form-group').removeClass('has-error');
					$("#deviceNames-error").remove();
				}
			})
		})
	}

	//查询操作
	var _SearchDeviceStatistics = function(){
		//验证
		if($("#deviceStatisticsForm").valid() == false){
			return;
		}
		//准备请求
		var ajaxOptions = {
			type: "post",
			url : 'report/getDeviceStatistics',// 地址
			data : {
				deviceNos : $("#deviceNos").val(),
				startDt : new Date($("#startDt").val().replace("年", "/").replace("月", "/")),
				endDt :new Date($("#endDt").val().replace("年", "/").replace("月", "/")),
			},
			success : function(data) {
				if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS() && data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()){
					if(data.data.table.length > 2){// 存在数据，显示表格
						// 清空数据
						$("#queryTable").empty();
						$("#queryTable").css('display','block');
						_SearchDeviceStatistics_Success(data.data.table);
					}
				}else{
					//无记录
					$("#queryTable").empty();
					$("#queryTable").css('display','none');
					$("#noResult").css('display','block');
					// 表格存在数据，显示导出按钮
					$("#exportDepartAnnualStatistics").hide();
				}
			}
		};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	}

	// 数据处理准备显示
	var _SearchDeviceStatistics_Success = function(data) {
		var lines = data;

		var thead = "<table id='deviceStatisticsTable' cellspacing='0' style='width:98%;'><thead><tr>";
		//第一行
		for(var i=0;i<lines[0].length;i++){
			if(i<2){
				thead += "<th class='device-column'>"+lines[0][i]+"</th>";
			}else{
				thead += "<th>"+lines[0][i]+"</th>";
			}
		}
		thead += "</tr><tr>"

		//第二行
		for(var i=0;i<lines[1].length;i++){
			if(i<2){
				thead += "<th class=>"+lines[1][i]+"</th>";
			}else{
				thead += "<th class='data-column'>"+lines[1][i]+"</th>";
			}
		}
		thead +="</tr></thead>";

		// tbody
		var tbody = "<tbody>";
		for(var i=2;i<lines.length;i++){
			tbody += "<tr>";
			for(var j=0; j<lines[i].length;j++){
				tbody += "<td >"+ lines[i][j] + "</td>"
			}
			tbody += "</tr>";
		}

		// tfoot
		var tfoot = "</tbody></table>";

		var table = thead + tbody + tfoot;

		// 显示表格div
		$("#queryTableDiv").css('display','block');
		$("#queryTable").append(table);

		// 设置 样式
		$("#deviceStatisticsTable").addClass('table text-center vertical-align table-bordered table-hover dataTable no-footer');
		$("#queryTable tr th").css('vertical-align','middle');
		$("#queryTable tr td").css('vertical-align','middle');

		//列属性
		var columnList = [];
		for (var i = 0; i < lines[1].length; i++) {
            var obj = {};
            if(i<2){
           	 	obj['width']="100px";
            }else{
            	obj['width']="40px";
            }
            columnList.push(obj);
        }

		//datatable设置项
		var settings = {
	        retrieve: true,//检测datatable实例
	        searching : false,// 不显示搜索器
			ordering : false,// 不可排序
			paging : false,//不分页
			info : false,//不显示表格信息
			scrollX: true,
			scrollY: '350px',
			scrollCollapse: true,//当显示更少记录时，是否允许表格减少高度
	        columns: columnList
        };
		$('#deviceStatisticsTable').dataTable(settings)

		//合并标题（月份）
		for(var i=2;i<lines[0].length;i++){
			$("#queryTable tr").eq(0).children('th').eq(i).attr("colSpan",3);
			$("#queryTable tr").eq(0).children('th').eq(i+1).remove();
			$("#queryTable tr").eq(0).children('th').eq(i+1).remove();	//这个必须要

		}
		//合并设备编号单元格
		$("#queryTable tr").eq(0).children('th').eq(0).attr("rowSpan",2);
		$("#queryTable tr").eq(1).children('th').eq(0).remove();
		//合并设备名称单元格
		$("#queryTable tr").eq(0).children('th').eq(1).attr("rowSpan",2);
		$("#queryTable tr").eq(1).children('th').eq(0).remove();
		//消除dt出现横向滚动条时，头部莫名出现偏移的情况
		$(".dataTables_scrollHeadInner").css("padding-left","0px");
		// 表格存在数据，显示导出按钮
		$("#exportDeviceStatistics").show();
	}


	//导出查询数据
	var _ExportDeviceStatistics = function(){
		   var deviceExport = ConstMsg.DEVICE_FILE_EXPORT();
           Globals.msgboxJS.Confirm(deviceExport,function(){
        	 //准备请求
   			var ajaxOptions = {
   				type: "post",
   				url : 'report/exportDeviceStatistics',// 地址
   				success : function(data) {
   					if(data.data!=null){
   					if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS() && data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()){
   						Globals.msgboxJS.ToastrSuccess(ConstMsg.FILE_EXPORT_SUCCESS());
   						window.location=path+"/file/export?fileName="+encodeURI(encodeURI(data.data.fileName));
   					}else{
   						Globals.msgboxJS.ToastrError(ConstMsg.FILE_EXPORT_ERROR());
   					}
   					} else {
   						//导出失败
   						Globals.msgboxJS.ToastrError(ConstMsg.FILE_EXPORT_ERROR());
   					}
   				}
   			};
   			var comAjax = new Globals.comAjaxJS();
   			comAjax.sendAjax(ajaxOptions);
           },function(){
             return;
           })
	}



	//========================= 构造方法  =========================================
	function constructor() {
		_this = this;
		this.InitDeviceStatistics = _InitDeviceStatistics;
	}
	return constructor;

})();
//继承公共AJAX类
Globals.deviceStatisticsJS.prototype = new Globals.comAjaxJS();
