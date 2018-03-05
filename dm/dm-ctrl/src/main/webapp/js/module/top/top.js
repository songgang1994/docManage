/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：课题新增/编辑js
 */
Namespace.register("Globals.topJS");

Globals.topJS = (function() {
	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	//创建 设备预约pop对象
	var deviceReservePopupJS = new Globals.deviceReservePopupJS();

	//课题详细画面初期化处理
	function _InitTop() {
		//监听按钮按钮点击事件
		$("a").not('.btn-upd').on("click",function(e){
			e.preventDefault();
			e.stopPropagation();

			switch(e.target.id){
				case "toTreatDocSubmit":
					window.location=path+'/doc/search'
					break;
				case "toCheckDocSubmit":
					window.location=path+'/doc/batchcheck'
					break;
				case "deviceReserveSubmit":
					window.location=path+'/device/reservemonth'
					break;
				case "toWorkingTimeSubmit":
					window.location=path+'/outlay/monthlyhour'
					break;
				default:
					break;
			}
		});
		//文档处理
	 	$("#toTreatDoc").on('click','.btn-upd',function(){
	 		var mode ='1';
	 		var documentCode =$(this).attr("documentCode");
	 		window.location=path+'/doc/enter?mode='+mode+'&documentCode='+documentCode;
	 	})
		//文档审核
	 	$("#toCheckDoc").on('click','.btn-upd',function(){
	 		var mode ='3';
	 		var documentCode =$(this).attr("documentCode");
	 		window.location=path+'/doc/enter?mode='+mode+'&documentCode='+documentCode;
	 	})
		//修改预约
	 	$("#deviceReserve").on('click','.btn-upd',function(){
	 		var deviceNo =$(this).attr("deviceNo");
	 		var reserveNo =$(this).attr("reserveNo");
	 		getReserveByNO(deviceNo,reserveNo);
	 	})
		//工时填写
		$("#workingTimeWrite").click(function(){
			var userId=$("#topUserId").val();
			var deptIds = $("#topdeptIds").val();
			window.location.href =path+"/outlay/hourenter?userId="+userId+"&deptId="+deptIds;
		})
	}
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
						 window.location=path+'/top/init'
					 });
				 }
			}
		};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	}
	// 构造方法
	function constructor() {
		_this = this;

		//课题详细画面初期化处理
		this.InitTop= _InitTop;
	}

	return constructor;
})();
//继承公共AJAX类
Globals.topJS.prototype = new Globals.comAjaxJS();
