Namespace.register("Globals.deviceListJS");
/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：张丽
 * 模块名：实验设备
 */
Globals.deviceListJS = (function() {
	// ====================私有属性区域===========================
	//User类实例，回调函数中使用，不可直接用this
	var _this = null;
	var deviceTable=null;
	//创建 设备预约pop对象
	var deviceHourEnterJS = new Globals.deviceHourEnterJS();
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	//设备一览列表处理
	function _DeviceList(pageLength){
		var table = $('#tbl-device');
		deviceTable=table.dataTable({
			ordering : false,// 不可排序
			bDestroy:true,
			serverSide:true,//后台分页
			ajax : {
				type: "post",
				url : "device/devicelist",
				dataSrc : "listData",
				data :$('#form-search').serializeJSON(),
				error: function(){
				}
			},
			"columns" : [
			{
				"data" : null,
				"targets":0
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
				"data" : "state",
				render : function(data) {
					return '<div style="text-align:left;" >' + data + '</div>';
				}
			}, {
				"data" : "location",
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
			            return '<a name="deviceUpd" class="btn-edit " data-device-no="'+row.deviceNo+'">编辑&nbsp</a>'+
             					   '<a name="deviceDelete" class="btn-delete " data-device-no="'+row.deviceNo+'">删除&nbsp</a>'+
             					   '<a name="deviceWrite" class="btn-use"  data-device-no="'+row.deviceNo+'">使用时间录入</a>';
			      }
			} ],
			 "fnDrawCallback":function(){
	                var api =this.api();
	                var startIndex= api.context[0]._iDisplayStart;        //获取到本页开始的条数 　
	                 api.column(0).nodes().each(function(cell, i) {
	                        cell.innerHTML = startIndex + i + 1;
	                        });
	               //按钮设定
	         		Globals.utilJS.ActionSetting();
	                 }
		});
		//查询无结果时 隐藏
		$('#tbl-device').on('xhr.dt', function ( e, settings, json, xhr ) {
			if(json != null && json.listData !=null){
				if(json.listData.length == ConstText.HTML_MSG_NUM_ZERO()){
					$("#deviceListTable").hide();
					$("#nodeviceResult").show();
				}else{
					$("#deviceListTable").show();
					$("#nodeviceResult").hide();
				}
			}
	    } );
	}
	// ====================表单验证区域===========================
	 // 初始化表单验证
	function initValidation() {
		$("#form-search").validate({
			errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
			errorElement: "span",
			rules: {
				deviceNo: { 			// 设备编号
					required: false,
					maxlength: ConstText.HTML_MSG_MAXLENGTH_TTY()
				},
				deviceName: {			// 设备名称
					required: false,
					maxlength: ConstText.HTML_MSG_MAXLENGTH_OSC()
				},
				deviceLocation: {		// 设备位置
					required: false,
					maxlength: ConstText.HTML_MSG_MAXLENGTH_TEN()
				}
		    },//表单验证
			messages: {
				deviceNo: {
					required: ConstMsg.HTML_MSG_CHOOSE_DEVICENUM(),
					maxlength: ConstMsg.HTML_MSG_LENGTH_DEVICENUM()
				},
				deviceName: {
					required: ConstMsg.HTML_MSG_CHOOSE_DEVICENAME(),
					maxlength: ConstMsg.HTML_MSG_LENGTH_DEVICENAME()
				},
				deviceLocation: {
					required: ConstMsg.HTML_MSG_CHOOSE_DEVICEPOSITION(),
					maxlength: ConstMsg.HTML_MSG_LENGTH_DEVICEPOSITION()
				}
			},
			highlight: function (element){ // hightlight error inputs
	            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
	        },
	        success: function (label){
	            label.closest('.form-group').removeClass('has-error'); //删除has-error后 删除label
	            label.remove();
	        }
		});
	}
	// ====================表单验证结束区域===========================
	// ====================Ajax请求函数定义区域===========================
	// 位置下拉框
	function _init(){
		timeID=setTimeout(function(){
			clearTimeout(timeID);
			//查询按钮点击
			if($("#flug").val()!=""){
				$("#btn-query").click();
				//返回按钮显示
				$("#deviceListCancel").show();
			}
		},ConstText.HTML_MSG_NUM_FHB());

		//定义Id名
		var locationId = ("#eqplace");
		var locationn = $("#locationn").val();
		//调用共通ajax
		Globals.utilJS.GetUserPlaceList(locationId,locationn);
	}
	// ========================================================
	// 检索设备一览信息
	function _initDeviceList() {

		// 初始化表单验证
		initValidation();

		Globals.utilJS.GetUsePlaceList($('[name=deviceLocation]').data('value'));
		   //查询事件
		   $("#btn-query").click(function(){
				//check表单验证
				if ($("#form-search").valid() == false) {
					return;
				}

				//加载datatable
				var pageLength = Globals.utilJS.getCookie(ConstText.DEVICE_PAGE_LENGTH());
				_DeviceList(pageLength);
				deviceTable.load();
		   })

		// 设备删除按钮事件绑定
		$("#tbl-device").on("click", ".btn-delete", function() {
			//获得删除行的设备名称
			var deleteName = $(this).parent().prev().prev().prev().children().text()
			//是否确定删除所选内容
			 var del = ConstMsg.BIZ_CODE_SHUR_DELETE()+deleteName+"?";
			 //获得需要删除的设备编号
			 var deviceNo = $(this).data('device-no');
				//是否确定删除所选内容
			 Globals.msgboxJS.Confirm(del,function(){
				 $.ajax({
					url: "device/delete/" + deviceNo,
					type: 'GET',
					dataType: 'json',
					success: function(data) {
						//如果成功删除 跳转到查询页面
						//如果删除失败 跳出提示 留在当前页面
						_addsuccess(data)
					}
				});
			 },function(){
		            return;
		        })
		});

		// 设备编辑按钮事件绑定
		$("#tbl-device").on("click", ".btn-edit", function() {
			var deviceNo1 = $("#deviceNo1").val();
			var deviceName = $("#deviceName1").val();
			var locationId = $("#eqplace").val();
			var location = $("#eqplace").find("option:selected").text();
			//防止链接打开URL
			event.preventDefault();
			window.location.href = path+"/device/editor?deviceNo=" + $(this).data('device-no')+"&deviceNo1="+deviceNo1+"&deviceName="+encodeURI(encodeURI(deviceName))+"&locationId="+locationId+"&location="+encodeURI(encodeURI(location));
			return false;
		});

		// 设备时间录入按钮事件绑定
		$("#tbl-device").on("click", ".btn-use", function() {
			var deviceNo1 = $("#deviceNo1").val();
			var deviceName = $("#deviceName1").val();
			var locationId = $("#eqplace").val();
			var location = $("#eqplace").find("option:selected").text();
			var device={deviceNo1:deviceNo1,deviceName:deviceName,locationId:locationId,location:location}
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
		});

		// 设备新增按钮帮顶
		$("#btn-add-new").click(function(event) {
			var deviceNo1 = $("#deviceNo1").val();
			var deviceName = $("#deviceName1").val();
			var locationId = $("#eqplace").val();
			var location = $("#eqplace").find("option:selected").text();
			//防止链接打开URL
			event.preventDefault();
			window.location.href = path+"/device/edit?deviceNo=" + $(this).data('device-no')+"&deviceNo1="+deviceNo1+"&deviceName="+encodeURI(encodeURI(deviceName))+"&locationId="+locationId+"&location="+encodeURI(encodeURI(location));
		});

		//返回事件
		$("#deviceListCancel").bind("click",function(){
			Globals.utilJS.ReturnHomePage();
		});
	}



	// ========================================================
	// ====================Msg区域=========================
	//删除msg
	function _addsuccess(json) {
		if (json.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
			//显示msg
			switch(json.data.bizCode){
			    //成功时直接跳转页面
				case ConstCode.BIZ_CODE_RESERVEDEVICE_DELETE_FAILED() :
					//跳转首页 设备有预约删除失败
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_RESERVEDEVICE_DELETE_FAILED())
					break;
				case ConstCode.BIZ_CODE_USEDEVICE_DELETE_FAILED():
					//设备在使用跳转失败
					Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_USEDEVICE_DELETE_FAILED())
					break;
				case ConstCode.BIZ_CODE_DEVICE_DELETE_SUCCESS():
					//删除成功 并点击查询按钮
					$("#btn-query").click();
					Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_DEVICE_DELETE_SUCCESS())
			}
		} else {
			//失败报msg
			Globals.msgboxJS.ToastrError(ConstMsg.BIZ_CODE_HANDLE_FAILED())
		}
	}
	// 构造方法
	function constructor() {
		_this = this;
		this.init= _init;
		this.InitDeviceList = _initDeviceList;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.deviceListJS.prototype = new Globals.comAjaxJS();