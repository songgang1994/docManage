/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：共通header的js
 */
Namespace.register("Globals.headerJS");

Globals.headerJS = (function() {
	var _this = null;
	var timeID;//计时器
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	//课题详细画面初期化处理
	function _InitHeader() {
		//按钮设定
		Globals.utilJS.ActionSetting();
		//session超时设定
		$.ajaxSetup({
            contentType:"application/x-www-form-urlencoded;charset=utf-8",
            complete:function(XMLHttpRequest,textStatus){
                    var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，
                    if(sessionstatus=="timeout"){
                    	Globals.msgboxJS.ToastrError(ConstMsg.HTML_MSG_EXPORT_ERROR());
                        //如果超时就处理 ，指定要跳转的页面
                    	timeID=setTimeout(function(){
        					clearTimeout(timeID);
        					window.top.location =path+"/login/init";
        				},ConstText.HTML_MSG_NUM_MS());
                                }
                     }
          });
		_headerExit();
	}
	function _headerExit(){
		$("#headerExit").click(function(){
			window.location.href=path+'/login/init';
		})
	}
	// 构造方法
	function constructor() {
		_this = this;

		//课题详细画面初期化处理
		this.InitHeader= _InitHeader;
	}

	return constructor;
})();
