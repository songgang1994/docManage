/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：登录js
 */
Namespace.register("Globals.loginJS");
Globals.loginJS = (function() {
	var _this = null;
	function _initPage(){
	   	// 表单验证
    	$('#login-form').validate({
    	    errorElement: 'span', //default input error message container
    	    errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
    	    rules:
    	    {
    	    	userCode:
    	    	{
		            required: true,
		            maxlength: 16
	        	},
	        	password:
	        	{
		            required: true,
		            maxlength: 64
	        	}
    	    },
        messages:
        {
        	userCode:
        	{
	   		 required: "用户名必须输入",
	   		 maxlength:"用户名最长为16个字符"
    		},
    		password:
    		{
   	   		 required: "密码必须输入",
   	   		 maxlength:"密码最长为64个字符"
       		}
        },
        invalidHandler: function (event, validator)
        { //display error alert on form submit
        },

        highlight: function (element)
        { // hightlight error inputs
            $(element)
                .closest('.form-group').addClass('has-error'); // set error class to the control group
        },

        success: function (label)
        {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        }
    	});
 	   //登录事件
 	   $("#loginSubmit").click(function(){
 			//check表单验证
 			if ($("#login-form").valid() == false) {
 				$("#errMsg").empty();
 			}
 	   })
	}
	// 构造方法
	function constructor() {
		_this = this;

		this.InitPage = _initPage;
	}

	return constructor;
})();
//继承公共AJAX类
Globals.loginJS.prototype = new Globals.comAjaxJS();