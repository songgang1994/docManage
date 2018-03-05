/*
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：邵林军 --%>
 * <%-- 模块名：部署年度文档统计 --%>
 */
Namespace.register("Globals.departAnnualStatisticsJS");

Globals.departAnnualStatisticsJS = (function() {
	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	//========================= 初始化 设备预约（月） ===============================
	function _InitDepartAnnualStatistics() {
		//设置年月下拉框
		Globals.utilJS.GetFyYear();

		//获取数据项
		_InitDocItemType();

		//初始化form提交验证
		_InitFormValid();

		// 按键监听
		_ButtonClickListener();

		  //返回事件
		$("#departAnnualStatisticsBack").bind("click",function(){
			Globals.utilJS.ReturnHomePage();
		});
	}
	//========================= 初始化 设备预约（月）结束 ===============================

	//初始化文件类型
	function _InitDocItemType(){
		var ajaxOptions = {
			type: "post",
			url : 'parm/docType',
			success : function(data) {
				if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS() ){
					var checkBoxValue = data.listData;
                    var chkhtml = [];
                    for (var i = 0; i < checkBoxValue.length; i++) {
                       chkhtml.push('<label><input type="checkbox" name="docType" value="'+checkBoxValue[i].itemValue+'"/>'+checkBoxValue[i].itemName+'</label>');
                    }
                    $("#checkboxSpan").append(chkhtml)
				}
			}
		};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	}

	//========================= 初始化表单验证  ===============================
	function _InitFormValid(){

		//判断结束时间是否大于开始时间的验证规则
		jQuery.validator.methods.compareDate = function(value, element, param) {
			var startDate = new Date(Date.parse(jQuery(param).val().replace('年','-').replace('月','-')));
			var endDate = new Date(Date.parse(value.replace('年','-').replace('月','-')));
			return	 startDate <= endDate
		};

		//表单提交验证
		$("#departAnnualStatisticsForm").validate({
			errorClass: 'help-block',
    	    focusInvalid: false,
    	    onfocusout:false,//关闭失去焦点时验证
    	    onkeyup:false,//关闭在 keyup 时验证。
    	    onclick:false,//关闭在点击复选框和单选按钮时验证。
			errorElement: "span",
			rules: {
				fyYear: {
					required: true,
				},
				docType: {
					required: true,
					minlength: ConstText.HTML_MSG_NUM_ZERO()
				}
		    },
			messages: {
				fyYear: {
					required: ConstMsg.MONTHLY_HOUR_CHOOSE_FYYEAR(),
				},
				docType: {
					required: ConstMsg.MONTHLY_HOUR_CHOOSE_DOCUMENTTYPE()
				}
			},
			errorPlacement: function(error, element) {
			    if(element.attr("name") == "docType" ) {
			    	error = $("<p />").append(error);
			    	error.addClass('form-group');
			    	error.addClass('has-error');
			    	error.insertAfter("#checkboxSpan");
			    } else {
			      error.insertAfter(element);
			    }
			},
			highlight: function (element){ // hightlight error inputs
	            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
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
				case "searchDepartAnnualStatistics"://查询数据
					_SearchDepartAnnualStatistics();
					break;
				case "exportDepartAnnualStatistics"://导出数据
					_ExportDepartAnnualStatistics();
					break;
				default:
					break;
				}
			});
	}

	//查询操作
	var _SearchDepartAnnualStatistics = function(){
		//验证
		if($("#departAnnualStatisticsForm").valid() == false){
			return;
		}

		//获取选中的 文档类型
		var docTypes = [];
		$("input[name = docType]").each(function() {
            if ($(this).is(':checked')) {
            	docTypes.push($(this).val());
            }
        });

		//准备请求
		var ajaxOptions = {
			type: "post",
			url : 'report/getDepartAnnualStatistics',// 地址
			data : {
				fyYear : new Date($("#fyYear").val()),
				docTypes :docTypes
			},
			success : function(data) {
				if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
					if(data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()){// 存在数据，显示表格
						_SearchDepartAnnualStatistics_Success(data.data.table);
					}else if(data.data.bizCode == ConstCode.BIZ_CODE_RECORD_0()){
						//无记录
						$("#queryTable").empty();
						$("#queryTable").css('display','none');
						$("#noResult").css('display','block');
						// 表格存在数据，显示导出按钮
						$("#exportDepartAnnualStatistics").hide();
					}
				}else{

				}
			}
		};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	}

	// 数据处理准备显示
	var _SearchDepartAnnualStatistics_Success = function(data) {
		// 清空数据
		$("#queryTable").empty();
		$("#queryTable").css('display','block');
		$("#noResult").css('display','none');

		var lines = data;

		var thead = "<table id='departAnnualStatisticsTable' cellspacing='0' style='width:98%;'><thead><tr>";
		//第一行
		for(var i=0;i<lines[0].length;i++){
			if(i<2){
				thead += "<th class='device-column'>"+lines[0][i]+"</th>";
			}else{
				thead += "<th class='data-column'>"+lines[0][i]+"</th>";
			}
		}
		thead +="</tr></thead>";

		// tbody
		var tbody = "<tbody>";
		for(var i=1;i<lines.length;i++){
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
		$("#departAnnualStatisticsTable").addClass('table text-center vertical-align table-bordered table-hover dataTable no-footer');
		$("#queryTable tr th").css('vertical-align','middle');
		$("#queryTable tr td").css('vertical-align','middle');

		//列属性
		var columnList = [];
		for (var i = 0; i < lines[0].length; i++) {
            var obj = {};
            if(i==0){
           	 	obj['width']="30px";
            }else if(i==1){
            	obj['width']="100px";
            }else{
            	obj['width']="60px";
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
		$('#departAnnualStatisticsTable').dataTable(settings);
		//消除dt出现横向滚动条时，头部莫名出现偏移的情况
		$(".dataTables_scrollHeadInner").css("padding-left","0px");
		// 表格存在数据，显示导出按钮
		$("#exportDepartAnnualStatistics").show();
	}


	//导出查询数据
	var _ExportDepartAnnualStatistics = function(){
		var deviceAnnualExport = ConstMsg.DEVICEANNUAL_FILE_EXPORT();
		Globals.msgboxJS.Confirm(deviceAnnualExport,function(){
		//准备请求
		var ajaxOptions = {
			type: "post",
			url : 'report/exportDepartAnnualStatistics',// 地址
			success : function(data) {
				if(data.data!=null){
				if(data.rtnCode == ConstCode.RTN_CODE_SUCCESS() && data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()){
					window.location=path+"/file/export?fileName="+encodeURI(encodeURI(data.data.fileName));
					Globals.msgboxJS.ToastrSuccess(ConstMsg.FILE_EXPORT_SUCCESS());
				}else{
					Globals.msgboxJS.ToastrError(ConstMsg.FILE_EXPORT_ERROR());
				}
				}else {
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
		this.InitDepartAnnualStatistics = _InitDepartAnnualStatistics;
	}
	return constructor;

})();
//继承公共AJAX类
Globals.departAnnualStatisticsJS.prototype = new Globals.comAjaxJS();
