/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：消息框基础类
 */
Namespace.register("Globals.msgboxJS");

toastr.options = {
	"closeButton": false, //是否显示关闭按钮
	"debug": false, //是否使用debug模式
	"positionClass": "toast-top-right",//弹出窗的位置
	"showDuration": "300",//显示的动画时间
	"hideDuration": "1000",//消失的动画时间
	"timeOut": "5000", //展现时间
	"extendedTimeOut": "2000",//加长展示时间
	"showEasing": "swing",//显示时的动画缓冲方式
	"hideEasing": "linear",//消失时的动画缓冲方式
	"showMethod": "fadeIn",//显示时的动画方式
	"hideMethod": "fadeOut" //消失时的动画方式
};
//确认消息窗口显示全局配置
$.confirm.options = {
    text: "确定操作吗?",
    title: "",
    confirmButton: "确定",
    cancelButton: "取消",
    post: false,
    submitForm: false,
    confirmButtonClass: "btn-primary",
    cancelButtonClass: "btn",
    dialogClass: "modal-dialog",
    modalOptionsBackdrop:"static"
}

Globals.msgboxJS = {
	//确认窗口
	Confirm:function(msg,confirmCallback,cancelCallback){
		$.confirm.options.text = msg;
		$.confirm({
		     confirm: function() {
		           confirmCallback();
		     },
		     cancel: function() {
		          cancelCallback();
		     }
		});
	},
	//操作消息框提示
	//操作成功时，消息显示用
	ToastrSuccess:function(msg){
		toastr.clear();
		toastr.success(msg);
	},
	//操作后，提示消息显示用
	ToastrInfo:function(msg){
		toastr.clear();
		toastr.info(msg);
	},
	//操作后，警告消息显示用
	ToastrWarning:function(msg){
		toastr.clear();
		toastr.warning(msg);
	},
	//操作后，错误消息显示用
	ToastrError:function(msg){
		toastr.clear();
	    toastr.error(msg);
	},
	//清除toastr
	ToastrClear:function(){
	    toastr.clear();
	},

	// 提示
	AlertInfo : function(msgCode) {
		$.messager.alert('提示', this.GetMsgContent(msgCode), 'info');
	},
	// 错误
	AlertError : function(msgCode) {
		$.messager.alert('错误', this.GetMsgContent(msgCode), 'error');
	},
	// 警告
	AlertWarning : function(msgCode) {
		$.messager.alert('警告', this.GetMsgContent(msgCode), 'warning');
	},
	// 确认
//	Confirm : function(msgCode, callbackFun) {
//		$.messager.confirm('确认', this.GetMsgContent(msgCode), function(rtn) {
//			if (rtn) {
//				callbackFun();
//			}
//		});
//
//	},
	// 输入
	Prompt : function(msgCode, callbackFun) {
		$.messager.prompt('输入', this.GetMsgContent(msgCode), function(val) {
			callbackFun(val);
		});
	},
	GetMsgContent : function(msgCode) {
		var message = new Globals.messageJS();
		return message.GetMsgById(msgCode);
	}

};
