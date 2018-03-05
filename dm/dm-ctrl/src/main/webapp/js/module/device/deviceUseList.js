/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：实验设备使用一览js
 */
Namespace.register("Globals.userAjaxJS");

Globals.deviceUseListAjaxJS = (function() {
// ========================================================
// 私有属性区域
//User类实例，回调函数中使用，不可直接用this
	var _this = null;
//创建 设备使用工时录入pop对象
	var deviceHourEnterJS = new Globals.deviceHourEnterJS();

	// 实验设备使用一览画面初期化
	function _init(){
		if($("#flug").val()!=""){
			timeID=setTimeout(function(){
				clearTimeout(timeID);
				$("#btn-query").click();
	    	},ConstText.HTML_MSG_NUM_FHB());

		}

		//时间日历初始化
		_initTime();

		//定义Id名
		var locationId = ("#eqplace");
		var locationn = $("#locationn").val();
		//调用共通ajax
		Globals.utilJS.GetUserPlaceList(locationId,locationn);
	}
//=========================时间日历初始化=============================//
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
			$("#useDt").datetimepicker({
				changeMonth: true,
				dateFormat: "yy/mm/dd",
			});

	}
//==========================时间日历初始化结束========================//
//==========================按钮点击事件============================//
	//查询按钮点击事件
	$("#btn-query").on('click',function(){
		//加载datatable
		var pageLength = Globals.utilJS.getCookie(ConstText.USE_PAGE_LENGTH());
		_DeviceUseList(pageLength);
		useTable.load();
	})

	//返回按钮点击事件
	$("#deviceListCancel").on('click',function(){
		Globals.utilJS.ReturnHomePage();
	})

	//新增使用按钮点击事件
	$("#btn-add-new").on('click',function(){
		cancel();
	})

	//修改时间录入点击事件
	$("#tbl-device").on('click','.btn-use',function(){
		var useNo = $(this).attr("useNo");
		_cancelUpdate(useNo);
	 })

//==========================按钮点击事件结束=========================//
//==========================datetable后台分页========================//
	function _DeviceUseList(pageLength){
		var table = $('#tbl-device');
		useTable=table.dataTable({
			ordering : false,// 不可排序
			bDestroy:true,
			serverSide:true,//后台分页
			ajax : {
				type: "post",
				url : "device/getList",
				dataSrc : "listData",
				data :$('#deviceUseForm').serializeJSON(),
				error: function(){
				}
			},
			"columns" : [
			{
				"data" : null,
				"targets":ConstText.HTML_MSG_NUM_ZERO()
			}, {
				"data" : "deviceNo",
				render : function(data) {
					return '<div style="text-align:left;" class="overflow-custom" title="'+data+'">' + data + '</div>';
				}
			}, {
				"data" : "deviceName",
				render : function(data) {
					return '<div style="text-align:left;" class="overflow-custom" title="'+data+'">' + data + '</div>';
				}
			}, {
				"data" : "useTime",
				render : function(data) {
					return '<div style="text-align:left;">' + data + '</div>';
				}
			}, {
				"data" : "userName",
				render : function(data) {
					return '<div style="text-align:left;" class="overflow-custom" title="'+data+'">' + data + '</div>';
				}
			}, {
				"data" : null
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
			dom: 't<lp<"col-xs-12 text-right" i>><"clear">',
			"columnDefs" : [ {
				 "targets": [5],
			      render: function(data, type, row, meta) {
			            return '<a name="deviceWrite" class="btn-use"  useNo="'+row.useNo+'" >修改时间录入</a>';
			      }
			} ],
			 "fnDrawCallback":function(){
	                var api =this.api();
	                var startIndex= api.context[0]._iDisplayStart;        //获取到本页开始的条数 　
	                 api.column(0).nodes().each(function(cell, i) {
	                        cell.innerHTML = startIndex + i + 1;
	                        }); }
		});
		//查询无结果时 隐藏
		$('#tbl-device').on('xhr.dt', function ( e, settings, json, xhr ) {
			if(json != null && json.listData !=null){
				if(json.listData.length == 0){
					$("#deviceListTable").hide();
					$("#nodeviceResult").show();
				}else{
					$("#deviceListTable").show();
					$("#nodeviceResult").hide();
				}
			}
	    } );
	}
//==========================datatable后台分页结束=====================//
//==========================专用函数区域===========================//

//==========================专用函数区域结束========================//
//==========================页面跳转参数传递========================//
	//新增时间期间
	function cancel(){
		var deviceNo = $("#deviceNo").val();
		var deviceName = $("#deviceName").val();
		var locationId = $("#eqplace").val();
		var location = $("#eqplace").find("option:selected").text();
		var useDt = $("#useDt").val();
		var flug = 1;
		var device = {deviceNo:deviceNo,deviceName:deviceName,locationId:locationId,location:location,useDt:useDt,flug:flug}
		//防止链接打开URL
		event.preventDefault();
		var ajaxOptions = {
				type: "post",
				url: "device/hour",
				contentType: "application/json",
				success : function(data) {
					//显示pop并设置操作后的回调函数(新增模式)
					deviceHourEnterJS.ShowModal(1,data,device,function(){
					});
				}
		};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	}

	//修改时间期间
	function _cancelUpdate(useNo){
		var deviceNo = $("#deviceNo").val();
		var deviceName = $("#deviceName").val();
		var locationId = $("#eqplace").val();
		var location = $("#eqplace").find("option:selected").text();
		var useDt = $("#useDt").val();
		var flug = 1;
		var device = {deviceNo:deviceNo,deviceName:deviceName,locationId:locationId,location:location,useDt:useDt,flug:flug,useNo:useNo}
		//防止链接打开URL
		event.preventDefault();
		var ajaxOptions = {
				type: "post",
				url: "device/hour",
				contentType: "application/json",
				success : function(data) {
					//显示pop并设置操作后的回调函数(新增模式)
					deviceHourEnterJS.ShowModal(2,data,device,function(){
					});
				}
		};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	}
//==========================页面跳转参数传递结束=====================//

// ========================================================
// 构造方法
	function constructor() {
		_this = this;
		this.init = _init;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.deviceUseListAjaxJS.prototype = new Globals.comAjaxJS();