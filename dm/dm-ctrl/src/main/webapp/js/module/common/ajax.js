/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：Ajax公共类提供Ajax基础方法,所有Ajax业务类都要继承该类
 */
Namespace.register("Globals.comAjaxJS");

Globals.comAjaxJS = (function(){
	//========================================================
	//私有属性区域
	var _this = null;

	//========================================================

	//========================================================
	//私有方法区域

	//发送Ajax请求
	function _sendAjax(ajaxOptions){
		if(typeof(ajaxOptions) == "undefined")
			return;

		for(var prop in ajaxOptions)
		{
			for(var defaultProp in this.ajaxDefaultOptions)
			{
				if(prop == defaultProp)
				{
					this.ajaxDefaultOptions[defaultProp] = ajaxOptions[prop];
					break;
				}
			}
		}

		Globals.utilJS.ShowMaskDiv();

		_this = this;
		$.ajax(this.ajaxDefaultOptions);
	}

	//默认请求成功时处理函数
	function _callback_successFun(jsonData,textStatus){
		console.log("【Ajax基类】成功时处理函数被调用！");
		return;
	}

	//默认请求出错处理函数
	function _callback_errorFun(XMLHttpRequest, textStatus, errorThrown){
		/*
		 * XMLHttpRequest.readyState: 状态码的意思
		 * 0 － （未初始化）还没有调用send()方法
		 * 1 － （载入）已调用send()方法，正在发送请求
		 * 2 － （载入完成）send()方法执行完成，已经接收到全部响应内容
		 * 3 － （交互）正在解析响应内容
		 * 4 － （完成）响应内容解析完成，可以在客户端调用了
		 *
		 * 错误类型textStatus:
		 * null
		 * timeout
		 * error
		 * notmodified
		 * parsererror
		 */
		var errorInfo = "XMLHttpRequest状态码:" + XMLHttpRequest.readyState + "\r\n"
		              + "错误类型textStatus:" + textStatus + "\r\n"
		              + "异常对象errorThrown:" + errorThrown;
		console.log(errorInfo);
		Globals.msgboxJS.AlertError("MSG_00000");
		//alert(errorInfo);
	}

	//默认请求完成的处理函数
	function _callback_completeFun(){
		console.log("【Ajax基类】请求完成的处理函数被调用！");
		console.log("【Ajax基类】Ajax Options:\r\n" + JSON.stringify(_this.ajaxDefaultOptions));
		Globals.utilJS.HideMaskDiv();
		return;
	}

	//默认请求前的处理函数
	function _beforeSendFun(){
		console.log("【Ajax基类】请求前的处理函数被调用！");
		console.log("【Ajax基类】Ajax Options:\r\n" + JSON.stringify(_this.ajaxDefaultOptions));
		return;
	};

	//========================================================

	//========================================================
	//构造方法
	function constructor(){
		this.sendAjax = _sendAjax;
		this.ajaxDefaultOptions = {
				url:null,//地址 必须
				data:null,//参数
				contentType: "application/x-www-form-urlencoded;charset=UTF-8",// 默认提交数据类型
				traditional:false,//traditional为false，深度序列化参数对象
				type:'POST',//提交方式 可以选择post/get 推荐post
				async: true,//false:同步true:异步
				cache: false,//不缓存
				dataType: 'json',//返回数据类型
				beforeSend:function(){_beforeSendFun();},//请求前的处理
				success:function(jsonData,textStatus){_callback_successFun(jsonData,textStatus);}, //请求成功时处理
				error:function(XMLHttpRequest, textStatus, errorThrown){_callback_errorFun(XMLHttpRequest, textStatus, errorThrown);}//请求出错处理
//				complete:function(){_callback_completeFun();}//请求完成的处理
		};

	}


	return constructor;
})();