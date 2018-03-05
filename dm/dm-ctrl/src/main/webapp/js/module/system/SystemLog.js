/**
 * NSK-NRDC业务系统
 *	作成人：张丽
 *	系统日志  js
 */

Namespace.register("Globals.SystemLogJS");

Globals.SystemLogJS = (function() {
	var _this = null;
	var projectTable=null;
	function _init(){

        //按钮点击事件
		$("#btn-search").bind("click", function(e){_search();});

		//时间日历初始化
		_initTime();

		//操作内容id定义
		var oprId=("#oprContent")

		//调用共通ajax
		Globals.utilJS.GetOprContentList(oprId);

	}

/**********************************时间日历初始化***********************************/
	function _initTime(){
		$.datepicker.regional['zh-CN'] = {
				changeMonth: true,
				changeYear: true,
				clearText: '清除',
				clearStatus: '清除已选日期',
				closeText: '关闭',
				closeStatus: '不改变当前选择',
				prevText: '<上月',
				prevStatus: '显示上月',
				prevBigText: '<<',
				prevBigStatus: '显示上一年',
				nextText: '下月>',
				nextStatus: '显示下月',
				nextBigText: '>>',
				nextBigStatus: '显示下一年',
				currentText: '今天',
				currentStatus: '显示本月',
				monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
				monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
				monthStatus: '选择月份',
				yearStatus: '选择年份',
				weekHeader: '周',
				weekStatus: '年内周次',
				dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
				dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
				dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
				dayStatus: '设置 DD 为一周起始',
				dateStatus: '选择 m月 d日, DD',
				dateFormat: 'yy-mm-dd',
				firstDay: 1,
				initStatus: '请选择日期',
				isRTL: false
			};
			$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
			$("#startDt").datetimepicker({
				changeMonth: true,
				dateFormat: "yy/mm/dd",
			});
			$("#endDt").datetimepicker({
				changeMonth: true,
				dateFormat: "yy/mm/dd",
			});
	}

/**********************************时间日历初始化结束******************************/
/**********************************按钮点击事件******************************/
	//返回按钮跳转上一个画面
	$("#form_staff_ret").bind("click",function(e){
		//防止表单默认提交
		e.preventDefault();
		e.stopPropagation();
		//返回首页
		Globals.utilJS.ReturnHomePage();
	});

	//点击查询按钮
	function _search(){
		//加载datetable
		var pageLength = Globals.utilJS.getCookie(ConstText.LOG_PAGE_LENGTH());
		_LogList(pageLength);
		LogTable.load();
	}
/**********************************按钮点击事件结束******************************/
/**********************************表格绘制**************************************/
	//系统日志一览列表处理
	function _LogList(pageLength){
		var startDt = $("#startDt").val();
		var endDt = $("#endDt").val();
		var documentCode = $("#documentCode").val();
		var userId = $("#userId").val();
		var oprContent = $("#oprContent").val();
		var userName = $("#userName").val();
		var table = $('#batchCheckTable');
		LogTable=table.dataTable({
			ordering : false,// 不可排序
			bDestroy:true,
			serverSide:true,//后台分页
			autoWidth:false,
			ajax : {
				type: "post",
				url : "system/getLog",
				dataSrc : "listData",
				data :{userId:userId,documentCode:documentCode,oprContent:oprContent,startDt:startDt,endDt:endDt,userName:userName}
			},
			"columns" : [
			{
				"title" : "时间",
				"width" : "25%",
				"className":"text-center",
				"data" : "oprDatetime",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}, {
				"title" : "用户ID",
				"width" : "10%",
				"className":"text-center",
				"data" : "userId",
				render : function(data) {
					return ' <div style="text-align:left;">'+data+'</div>';
				}
			}, {
				"title" : "用户姓名",
				"width" : "15%",
				"className":"text-center",
				"data" : "userName",
				render : function(data) {
					return ' <div style="text-align:left;" class="overflow-custom" title="'+data+'">'+data+'</div>';
				}
			},{
				"title" : "IP地址",
				"width" : "15%",
				"className":"text-center",
				"data" : "ip",
				render : function(data) {
					return ' <div style="text-align:left;" class="overflow-custom" title="'+data+'">'+data+'</div>';
				}
			}, {
				"title" : "文档编号",
				"width" : "25%",
				"className":"text-center",
				"data" : "documentCode",
				render : function(data) {
					return ' <div style="text-align:left;" class="overflow-custom" title="'+data+'">'+data+'</div>';
				}
			}, {
				"title" : "操作内容",
				"width" : "10%",
				"className":"text-center",
				"data" : "dispName",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			} ],
			"aLengthMenu" : [ [ 5, 15, 20, -1 ], [ 5, 15, 20, "全部" ] // change per page values here
			],
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
			dom: 't<lp <"col-xs-12 text-right" i>><"clear">'
		});
		//查询无结果时 隐藏
		$('#batchCheckTable').on('xhr.dt', function ( e, settings, json, xhr ) {
			if(json != null && json.listData !=null){
				if(json.listData.length == ConstText.HTML_MSG_NUM_ZERO()){
					$("#batchCheckList").hide();
					$("#noBatchResult").show();
				}else{
					$("#batchCheckList").show();
					$("#noBatchResult").hide();
				}
			}
	    });
	}


/**********************************表格绘制结束**********************************/

	// 构造方法
	function constructor() {
		_this = this;
       this.init= _init;
	}
	return constructor;
})();

//继承公共AJAX类
Globals.SystemLogJS.prototype = new Globals.comAjaxJS();