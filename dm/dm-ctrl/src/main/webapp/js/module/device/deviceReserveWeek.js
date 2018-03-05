/*
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：邵林军 --%>
 * <%-- 模块名：设备预约（周） --%>
 */
Namespace.register("Globals.deviceReserveWeekJS");

Globals.deviceReserveWeekJS = (function() {
	var _this = null;
	//选中的设备对象 数组
	var deviceObjs=[];
	//创建 设备预约pop对象
	var deviceReservePopupJS = new Globals.deviceReservePopupJS();
	//创建 设备检索pop对象
	var deviceSearchPopupJS = new Globals.deviceSearchPopupJS();

	//========================= 初始化 设备预约（周） ===============================
	function _initDeviceReserveWeek() {
		//设置年月下拉框
		_InitDatetimepicker();

		//初始化日历
		_InitCalendar();

		//初始化表单验证
		_InitFormValid();

		// 按键监听
		_ButtonClickListener();

		//设备检索Popup
		$("#deviceSearch").click(function(){
			var device = new Globals.deviceSearchPopupJS();
			device.ShowModal('checkbox',function(data) {
				var lkdeviceName = '';
				var deviceNo = '';
				var deviceName = '';
				var deviceWeek = '';
				for(var i = 0; i < data.length; i++){
					lkdeviceName += data[i][0].deviceName + ',';
					deviceNo = "<input type='hidden' class='newdeviceNo' value='"+data[i][0].deviceNo+"'/>"
					deviceName = "<input type='hidden' class='newdeviceName' value='"+data[i][0].deviceName+"'/>"
					deviceWeek += deviceNo + deviceName;
				}
				$("#newdeviceName-error").empty();
				$("#deviceNameErr").empty();
				lkdeviceName = lkdeviceName.substring(0,lkdeviceName.length-1)
				$("#newdeviceName").val(lkdeviceName)
				$("#newdeviceName").attr("title",lkdeviceName)
				$("#deviceNameErr").append(deviceWeek);
			})
		})
	}
	//========================= 初始化 设备预约（周）结束 =====================

	//========================= 初始化时间选择器 ===========================
	function _InitDatetimepicker(){
		// 时间选择器
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
		//选择日期;
		$("#dateInfo").prop("readonly", true).datepicker({
			changeMonth : true,
			dateFormat : "yy/m/d",
		});
		// 设置默认日期
		$("#dateInfo").val(moment().format("YYYY/M/D"));
	}
	//========================= 初始化时间选择器结束  ===========================

	//========================= 初始化日历==================================
	function _InitCalendar(){
		var handle;//计时器
		$('#calendar').fullCalendar({
			//免费开源key
			schedulerLicenseKey: 'GPL-My-Project-Is-Open-Source',
			defaultView: 'timelineWeek',
			editable:false,
			slotDuration:{day:1},//很重要，每个槽显示多长时间
			height: "auto",//很重要
			nextDayThreshold:"00:00:00",//这个很重要，事件超过几点在下一天显示
			firstDay:1,//第一天是周几，0：周日，1：周一 ...
	        displayEventTime : false,//不显示事件时间
	        resourceAreaWidth: '30%',

			//汉化
			monthNames: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
			monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
			dayNames: ['日','一','二','三','四','五','六'],
			dayNamesShort:['日','一','二','三','四','五','六'],

			slotLabelFormat: [
			    'dddd M/D', // top level of text
			],
			buttonText: {
			    prev: "",
			    next: "",
			    today: '今天'
			},
			//汉化结束

			//标题栏
			header: {
				left: 'prev,next today',
				center: 'title',
				right: ''	//不显示右侧的 月 周 天 选项(必须写)
			},
			//视图设置
	        views: {
	        	timelineWeek: {
	        		titleFormat: "YYYY年 M月 D日 ", //标题设置
					type: 'timeline',
				}
	        },
	        //资源列设置
			resourceColumns: [
				{
					labelText: '设备编号',
					field: 'deviceNo',
					render: function(resource, el) {
						el.css('text-align','center');
					}
				},{
					labelText: '设备名称',
					field: 'deviceName',
					render: function(resource, el) {
						el.css('text-align','center');
					}
				}
			],
			//资源加载
			resources: function(callback) {
				if(_FullCalendarResourcesLoad()!=null){
			        callback(_FullCalendarResourcesLoad());
				}
			},
			//将今天设置为灰色
			dayRender: function (date, cell) {
			    if (date.format("YYYY MM DD") === moment().format("YYYY MM DD")) {
			        cell.css("background-color", "	#C0C0C0");
			    }
			},
			//当画面改变时，重新渲染事件（很重要）
			windowResize: function(view) {
				//资源列 头部居中
				$(".fc-cell-content").css('text-align','center');
				//重新渲染
		      	$('#calendar').fullCalendar('rerenderEvents');
			},

			//事件渲染后
			eventAfterRender: function (event, element, view) {
				var start = moment(event.start._i);
				var end =  moment(event.end._i);

				//开始和结束那天0点
				var start0 = moment(moment(start).format('YYYY-MM-DD')+"T00:00:00");
				var end0 =  moment(moment(end).format('YYYY-MM-DD')+"T00:00:00");

				//计算开始和结束的padding百分比,四舍五入取两位小数
				var swidth = (start.diff(start0,"m")/(24*60)).toFixed(2);
				var ewidth = (end.diff(end0,"m")/(24*60)).toFixed(2);

				//获取每天的宽度
				var dwidth = $(".fc-day").width();
				//起始点偏移（解决事件跨行偏移）
				if(element[0].classList.contains('fc-start')){
					element[0].style.left = (parseInt(element[0].style.left) + dwidth*(swidth)) +"px"
				}
				//结束点偏移（解决事件跨行偏移）
				if(element[0].classList.contains('fc-end')){
					//解决00:00:00 的问题
					if(end.hours() + end.minute() != 0){
						element[0].style.right = (parseInt(element[0].style.right) + dwidth*(1-ewidth)) +"px"
					}
				}
				element[0].style.top = (parseInt(element[0].style.top) +4) +"px";

				$('.fc-title').css('left','-10px');//去除最前面的三角形，很重要

	        },
			//点击天
		  	dayClick: function(date, jsEvent, view, resourceObj) {
		  		if(_FormValid()==true){
			  		//创建传入pop的数据
		        	var popData = {
		        			deviceNo : resourceObj.deviceNo,
		        			deviceName : resourceObj.deviceName,
		        			reserveNo : "",
		        			startDt : date.format('YYYY/MM/DD'),
		        	}
		        	//显示pop并设置操作后的回调函数(新增模式)
					deviceReservePopupJS.ShowModal(ConstText.RESERVE_PATTERN_ADD(), popData, function(){
						//刷新日历 重新加载事件
						$("#searchReserve").click();
					});
		  		}
		    },
		  	//事件点击的效果
		    eventClick: function(event, jsEvent, view) {
		    	//通过No 获取预约详细
		    	getReserveByNO(event.deviceNo,event.reserveNo)
			},
			//鼠标滑过事件的效果
			eventMouseover: function(event, jsEvent, view) {
				//创建计时器，防止过于频繁的显示tip
				handle = setTimeout(function(){
									 	var top = jsEvent.pageY-10;
										var left = jsEvent.pageX+10;
										$('#tip').css({
											'position': 'absolute',
											'top':top+'px',
											'left': left+ 'px',
											'z-index': '9999',
											});
										//内容赋值
										$('#tipDeviceName').text(event.deviceName);
										$('#tipUser').text(event.userName);
										$('#tipStart').text(event.start.format('M/D HH:mm'));
										$('#tipEnd').text(event.end.format('M/D HH:mm'));
										//显示
										$('#tip').fadeIn('fast');
									 }, 200);
			},
			//鼠标滑出事件的效果
			eventMouseout:function( event, jsEvent, view ) {
				//清空计时器
				clearTimeout(handle);
				//隐藏提示
				$("#tip").hide();
			},
		});
		//上月
		$(".fc-prev-button").click(function () {
			_CalendarDayChange()
	    })
		//下月
	    $(".fc-next-button").click(function () {
	    	_CalendarDayChange()
	    })
	    //今天按钮
	    $(".fc-today-button").click(function () {
	    	_CalendarDayChange()
	    })


	    //去除列滑动模块（很重要）
		$(".fc-col-resizer").remove();
		//资源列 头部居中
		$(".fc-cell-content").css('text-align','center');
	}
	//点击上一月，下一月， 今天  触发的函数
	function _CalendarDayChange(){
		if($("#deviceReserveWeekForm").validate().form()){
			date = $('#calendar').fullCalendar("getDate").format("YYYY-MM-DD");
			searchReserve(date);
		}else{
			//记住选中年月，resetForm会把select也reset掉
			var yearMonth = $("#dateInfo").val();
			//去除显示的验证
			$("#deviceReserveWeekForm").validate().resetForm();
			$("#dateInfo").val(yearMonth);
		}
	}
	//========================= 初始化日历结束 ===============================


	//========================= 日历资源设置 =================================
	function _FullCalendarResourcesLoad(devices){
		//这个方法不可少
		return deviceObjs;
	}
	//========================= 日历资源设置结束 ==============================

	//获得选中日期
	function _GetSelectDatas(){
		/*var devices = [{
		 		id:'123456789',
		        deviceNo: '123456789',
		        deviceName: '1号设备'
		    },{
		    	id:'10',
		    	deviceNo: '10',
		    	deviceName: '测试设备2号',
		    }];*/
		var devices=[];
		return devices;
	}

	// 搜索
	function searchReserve(date){
		//日历跳到选中个月
		$('#calendar').fullCalendar('gotoDate',date);
		//重新加载资源
		_FullCalendarResourcesLoad(deviceObjs);
		$('#calendar').fullCalendar('refetchResources');
		//加载事件
		_FullCalendarEventsLoad(deviceObjs,date);
	}

	//========================= 初始化表单验证 =========================================
	function _InitFormValid(){
		//表单提交验证
		$("#deviceReserveWeekForm").validate({
			errorClass: 'help-block',
    	    focusInvalid: false,
    	    onfocusout:false,//关闭失去焦点时验证
    	    onkeyup:false,//关闭在 keyup 时验证。
    	    onclick:false,//关闭在点击复选框和单选按钮时验证。
			errorElement: "span",
			rules: {
				newdeviceName: {
					required: true,
				},
				dateInfo: {
					required: true
				}
		    },
			messages: {
				newdeviceName: {
					required: ConstMsg.HTML_FRM_CHOOSE_DEVICE(),//请选择设备
				},
				dateInfo: {
					required: ConstMsg.HTML_FRM_CHOOSE_YEAR_MONTH(),//请选择年月
				}
			},
			errorPlacement: function(error, element) {
			    if(element.attr("name") == "newdeviceName" ) {
			    	error = $("<p />").append(error);
			    	error.addClass('form-group');
			    	error.addClass('has-error');
			    	error.insertAfter("#deviceNameErr");
			    } else {
			      error.insertAfter(element);
			    }
			},
			highlight: function (element){ // hightlight error inputs
	            $(element).closest('.form-err').addClass('has-error'); // set error class to the control group
	        },
	        success: function (label){
	            label.closest('.form-err').removeClass('has-error');
	            label.remove();
	        }
		});
	}
	//========================= 初始化表单验证结束 =========================================

	//表单验证
	function _FormValid(){
		return $("#deviceReserveWeekForm").valid()
	}

	// 按键监听
	function _ButtonClickListener(){
		//监听按钮按钮点击事件
		$("button").on("click",function(e){
			 e.preventDefault();
			 e.stopPropagation();

			switch(e.target.id){
				case "searchReserve":
					var titdeviceName = '';
					var titdeviceNo = '';
					var deviceName = $(".newdeviceName").val();
					var deviceNo = $(".newdeviceNo").val();
					deviceObjs.length = 0;
					$(".newdeviceName").each(function(i,nam){
						var deviceName = $(this).val();
						$(".newdeviceNo").each(function(a,nan){
							if(i == a){
								deviceObjs.push({id:$(this).val(),deviceName : deviceName,deviceNo : $(this).val()})
								titdeviceNo += $(this).val() + ','
								titdeviceName += deviceName + ','
							}
						})
					})
					titdeviceName = titdeviceName.substring(0,titdeviceName.length-1)
					titdeviceNo = titdeviceNo.substring(0,titdeviceNo.length-1)
					$("#deviceNo").val(titdeviceNo);
					$("#newdeviceName").val(titdeviceName);
					//验证
					if(_FormValid() == true){
						//获取时间
						var dateinfo = $("#dateInfo").val().replace('/', '-');
						//搜索
						searchReserve(dateinfo);
					}
					break;
				default:
					break;
			}
		});
	}

	//========================= 前后台交互 =================================
	//日历事件加载
	function _FullCalendarEventsLoad(devices,date){
		//当周日历上最开始和最后 00:00:00 - 23:59:59
		var view = $('#calendar').fullCalendar('getView');
		var startDt = view.start.format("YYYY-MM-DD HH:mm:ss");
        var endDt = view.end.subtract(0.1, 'seconds').format("YYYY-MM-DD HH:mm:ss");

//        //当周最开始和最后 00:00:00 - 23:59:59
//		var startDt = moment(date).startOf("isoWeek").format("YYYY-MM-DD HH:mm:ss");
//		var endDt = moment(date).endOf("isoWeek").format("YYYY-MM-DD HH:mm:ss");

		//提取选中的设备id
		var deviceNos = [];
		for(var i in devices){
			deviceNos.push(devices[i].deviceNo);
		}

		//访问后台
		$.ajax({
        	type: "post",
			url : 'device/deviceReserveInfo',// 地址
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			traditional: true,//可以传数组
			data: {
				deviceNos:deviceNos,
				startDt:moment(startDt).format('YYYY/MM/DD HH:mm:ss'),	//周开始时间转成时间
				endDt:moment(endDt).format('YYYY/MM/DD HH:mm:ss')	//周结束时间转成时间
				},
			success: function(json) { // 获取当前月的数据
				var events = [];
				if(json.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
                	$.each(json.listData,function(i,c) {
                		events.push({
                			resourceId:c.deviceNo,//和日历中资源关联项
                			deviceNo:c.deviceNo,
                			reserveNo:c.reserveNo,
                            color:c.color,
                            deviceName:c.deviceName,
                            userName:c.userName,
                            start: new Date(Date.parse(c.startDt)),
                            end:new Date(Date.parse(c.endDt))
                        });
                	});

                	//重新渲染事件
                	$('#calendar').fullCalendar('removeEvents');
                    $('#calendar').fullCalendar('addEventSource', events);
                    $('#calendar').fullCalendar('rerenderEvents' );
                }
            }
		})
	}
	//日历事件加载结束

	// 通过设备号和预约号获取预约
	function getReserveByNO(deviceNo, reserveNo){
		//准备请求
		var ajaxOptions = {
			type: "post",
			url : 'device/getReserve',// 地址
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			data: {
				deviceNo:deviceNo,
				reserveNo:reserveNo
			},
			success: function(json) {
				 if(json.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
					 //显示pop并设置操作后的回调函数（修改模式）
					 deviceReservePopupJS.ShowModal(ConstText.RESERVE_PATTERN_UPDATE(), json.data, function(){
						//刷新日历 重新加载事件
						$("#searchReserve").click();
					 });
				 }
			}
		};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	}
	//========================= 前后台交互结束 =================================

	//========================= 构造方法  =================================
	function constructor() {
		_this = this;
		this.InitDeviceReserveWeek = _initDeviceReserveWeek;
	}
	return constructor;

})();
//继承公共AJAX类
Globals.deviceReserveWeekJS.prototype = new Globals.comAjaxJS();
