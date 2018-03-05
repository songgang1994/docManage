/*
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：邵林军 --%>
 * <%-- 模块名：设备预约（月） --%>
 */
Namespace.register("Globals.deviceReserveMonthJS");

Globals.deviceReserveMonthJS = (function() {
	var _this = null;
	//创建 设备预约pop对象
	var deviceReservePopupJS = new Globals.deviceReservePopupJS();

	//========================= 初始化 设备预约（月） ===============================
	function _InitDeviceReserveMonth() {
		//设置年月下拉框
		Globals.utilJS.YearMonthSelect("#dateInfo");

		//初始化日历
		_InitCalendar();

		//初始化表单验证
		_InitFormValid();

		// 按键监听
		_ButtonClickListener();

		//设备检索Popup
		$("#deviceSearch").click(function(){
			_DevicePopup();
		})


	}
	//========================= 初始化 设备预约（月）结束 ===============================

	//========================= 设备检索POPUP=========================================
	function _DevicePopup(){
		var device = new Globals.deviceSearchPopupJS();
		device.ShowModal('radio',function(data) {
			var lkdeviceName = '';
			var deviceNo = '';
			var deviceName = '';
			var deviceNoAndDeviceName = '';
			for(var i = 0; i < data.length; i++){
				lkdeviceName += data[i][0].deviceName;
				deviceNo = "<input type='hidden' class='newdeviceNo' value='"+data[i][0].deviceNo+"'/>"
				deviceName = "<input type='hidden' class='newdeviceName' value='"+data[i][0].deviceName+"'/>"
				deviceNoAndDeviceName = deviceNo + deviceName;
			}
			$("#deviceName-error").empty();
			$("#deviceNameErr").empty();
			$("#deviceNameErr").append(deviceNo + deviceName);
			$("#deviceName").val(lkdeviceName)
		})
	}

	//========================= 设备检索POPUP结束=========================================

	//========================= 初始化日历=========================================
	function _InitCalendar(){
		var handle;//计时器
		$('#calendar').fullCalendar({
			firstDay:1,//第一天是周几，0：周日，1：周一 ...
			displayEventTime : false,//不显示事件时间
			fixedWeekCount:false,//随月份显示周数，不固定显示6周
			editable: false, //不可拖动事件
			eventLimit: true, //时间过多是，显示“more”
			nextDayThreshold:"00:00:00",//这个很重要，事件超过几点在下一天显示
			height: "auto",//很重要
			aspectRatio:2,

			//汉化
			monthNames: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
			monthNamesShort: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
			dayNames: ['日','一','二','三','四','五','六'],
			dayNamesShort:['日','一','二','三','四','五','六'],
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
	            month: { //月视图
	                titleFormat: 'YYYY年 MM月' //标题设置
	                // other view-specific options here
	            }
	        },
	       //日期限制范围  （与上面的select框对应，前后一年）
			validRange: function(nowDate) {
		        return {
		        	start:moment().subtract(1, "years").format("YYYY-MM"),
		        	end:moment().add(13, "months").format("YYYY-MM"),
		        };
			 },
			//将今天设置为灰色
			dayRender: function (date, cell) {
				 if (date.format("YYYY MM DD") === moment().format("YYYY MM DD")) {
				        cell.css("background-color", "	#C0C0C0");
				    }
			},
			//当画面改变时，重新渲染事件（很重要）
			windowResize: function(view) {
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
					element[0].parentNode.style.paddingLeft = dwidth*(swidth)+"px";
				}
				//结束点偏移（解决事件跨行偏移）
				if(element[0].classList.contains('fc-end')){
					//解决00:00:00 的问题
					if(end.hours() + end.minute() != 0){
						element[0].parentNode.style.paddingRight = dwidth*(1-ewidth)+"px";
					}
				}

				 //去除原有margin
				 $(".fc-day-grid-event").css("margin","2px,0px,2px,0px");
	        },
			//点击天
	        dayClick: function(date, jsEvent, view) {
	        	if(_FormValid()==true){
		        	//创建传入pop的数据
		        	var popData = {
		        			deviceNo : $("#deviceNo").val(),
		        			deviceName:$("#deviceName").val(),
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

	    $("#calendar table").css("border-left","1px")

	};
	//点击上一月，下一月， 今天  触发的函数
	function _CalendarDayChange(){
		if($("#deviceReserveMonthForm").validate().form()){
			date = $('#calendar').fullCalendar("getDate").format("YYYY-MM");
			searchReserve(date);
		}else{
			//记住选中年月，resetForm会把select也reset掉
			var yearMonth = $("#dateInfo").val();
			//去除显示的验证
			$("#deviceReserveMonthForm").validate().resetForm();
			$("#dateInfo").val(yearMonth);
		}
	}
	//========================= 初始化日历结束 =========================================

	//========================= 初始化表单验证 =========================================
	function _InitFormValid(){
		//表单提交验证
		$("#deviceReserveMonthForm").validate({
			errorClass: 'help-block',
    	    focusInvalid: false,
    	    onfocusout:false,//关闭失去焦点时验证
    	    onkeyup:false,//关闭在 keyup 时验证。
    	    onclick:false,//关闭在点击复选框和单选按钮时验证。
			errorElement: "span",
			rules: {
				deviceName: {
					required: true,
				},
				dateInfo: {
					required: true
				}
		    },
			messages: {
				deviceName: {
					required: ConstMsg.HTML_FRM_CHOOSE_DEVICE(),//请选择设备
				},
				dateInfo: {
					required: ConstMsg.HTML_FRM_CHOOSE_YEAR_MONTH(),//请选择年月
				}
			},
			errorPlacement: function(error, element) {
			    if(element.attr("name") == "deviceName" ) {
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
		return $("#deviceReserveMonthForm").valid()
	}

	// 按键监听
	function _ButtonClickListener(){
		//监听按钮按钮点击事件
		$("button").on("click",function(e){
			e.preventDefault();
			e.stopPropagation();

			switch(e.target.id){
				case "searchReserve":
					$("#deviceNo").val($(".newdeviceNo").val());
					$("#deviceName").val($(".newdeviceName").val());
					//验证
					if(_FormValid() == true){
						//获取时间
						var dateinfo = $("#dateInfo option:selected").val().replace('年', '-').replace('月', '');
						//搜索
						searchReserve(dateinfo);
					}
					break;
				default:
					break;
			}
		});
	}

	// 搜索预约记录
	function searchReserve(date){
		var deviceNo = $("#deviceNo").val();
		//日历跳到选中个月
		$('#calendar').fullCalendar('gotoDate',date);
		//加载事件
		_FullCalendarEventsLoad(deviceNo,date);
	}

	//========================= 前后台交互 =========================================
	//日历事件加载
	function _FullCalendarEventsLoad(deviceNo,date){

		//当月日历上最开始和最后 00:00:00 - 23:59:59
		var view = $('#calendar').fullCalendar('getView');
		var startDt = view.start.format("YYYY-MM-DD HH:mm:ss");
        var endDt = view.end.subtract(0.1, 'seconds').format("YYYY-MM-DD HH:mm:ss");

//        //当月最开始和最后 00:00:00 - 23:59:59
//		var startDt = moment(date).startOf("month").format("YYYY-MM-DD HH:mm:ss");
//		var endDt = moment(date).endOf("month").format("YYYY-MM-DD HH:mm:ss");

		//设备id 数组化，兼容周查询
		var deviceNos = [];
		deviceNos.push(deviceNo);

		$.ajax({
        	type: "post",
			url : 'device/deviceReserveInfo',// 地址
			contentType : "application/x-www-form-urlencoded; charset=UTF-8",
			traditional: true,//可以传数组
			data: {
				deviceNos:deviceNos,
				startDt:moment(startDt).format('YYYY/MM/DD HH:mm:ss'),	//月开始时间转成时间
				endDt:moment(endDt).format('YYYY/MM/DD HH:mm:ss')	//月结束时间转成时间
				},
			success: function(json) { // 获取当前月的数据
				var events = [];
                if(json.rtnCode == ConstCode.RTN_CODE_SUCCESS()){
                	$.each(json.listData,function(i,c) {
                		events.push({
                			deviceNo:c.deviceNo,
                			reserveNo:c.reserveNo,
                            color:c.color,
                            deviceName:c.deviceName,
                            userName:c.userName,
                            start:new Date(Date.parse(c.startDt)),
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
	//========================= 前后台交互结束 =====================================


	//========================= 构造方法  =========================================
	function constructor() {
		_this = this;
		this.InitDeviceReserveMonth = _InitDeviceReserveMonth;
	}
	return constructor;

})();
//继承公共AJAX类
Globals.deviceReserveMonthJS.prototype = new Globals.comAjaxJS();
