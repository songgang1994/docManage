/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：公共函数类，提供常用公共函数
 */
Namespace.register("Globals.utilJS");

Globals.utilJS = {
	// ========================================================
	// 静态公共方法区域
	GetMaskDiv : function() {
		return window.top.$("#maskDiv");
	},
	GetSubWinDiv : function() {
		return window.top.$("#subWinDiv");
	},
	ShowMaskDiv : function() {
		var maskDivObj = this.GetMaskDiv();
		maskDivObj.css("height", window.screen.height);
		maskDivObj.css("width", window.screen.width);
		maskDivObj.show();
	},
	HideMaskDiv : function() {
		this.GetMaskDiv().hide();
	},
	OpenSubWin : function(winStyleOptions) {
		// URL检查
		if (typeof (winStyleOptions.href) == "undefined") {
			Globals.msgboxJS.AlertError("MSG_00005");
			return;
		}

		// 默认值设置
		if (typeof (winStyleOptions.title) == "undefined") {
			winStyleOptions["title"] = "默认标题";
		}
		if (typeof (winStyleOptions.iconCls) == "undefined") {
			winStyleOptions["iconCls"] = "";
		}
		if (typeof (winStyleOptions.width) == "undefined") {
			winStyleOptions["width"] = 500;
		}
		if (typeof (winStyleOptions.height) == "undefined") {
			winStyleOptions["height"] = 400;
		}
		if (typeof (winStyleOptions.top) == "undefined") {
			winStyleOptions["top"] = (window.screen.height - winStyleOptions["height"]) * 0.5;
			//winStyleOptions["top"] = 50;
		}
		if (typeof (winStyleOptions.left) == "undefined") {
			winStyleOptions["left"] = (window.screen.width - winStyleOptions["width"]) * 0.5;
			//winStyleOptions["left"] = 100;
		}

		this.GetSubWinDiv().window(winStyleOptions);
		this.GetSubWinDiv().window('open');
	},
	CloseSubWin : function() {
		this.GetSubWinDiv().window('close');
	},
	GetCurrTabWin : function(tabsId) {
		var currWin = null;
		if (typeof (tabsId) == "undefined" || null == tabsId) {
			tabsId = "#mainPanel";
		}
		var currTab = $(tabsId).tabs('getSelected');

		if (currTab && currTab.find('iframe').length > 0) {
			currWin = currTab.find('iframe')[0].contentWindow;
		}

		return currWin;

	},
	ChkDgIsHaveChecked : function(dgId) {
		if (typeof (dgId) == "undefined" || null == dgId) {
			console.log("【Globals.utilJS.ChkDgIsHaveChecked】dataGrid对象ID未设置!");
			return false;
		}

		var checkedItems = $(dgId).datagrid('getChecked');

		if (checkedItems == null || checkedItems.length == 0) {
			// 没有数据被选中时
			Globals.msgboxJS.AlertError("MSG_00001");
			return false;
		}

		return true;
	},
	ChkDgIsHaveChecked : function(dgId) {
		if (typeof (dgId) == "undefined" || null == dgId) {
			console.log("【Globals.utilJS.ChkDgIsHaveChecked】dataGrid对象ID未设置!");
			return false;
		}

		var checkedItems = $(dgId).datagrid('getChecked');

		if (checkedItems == null || checkedItems.length == 0) {
			// 没有数据被选中时
			return false;
		}

		return checkedItems;
	},
	ShowValChkErrMsg:function(jsonMsg){
		if (typeof (jsonMsg) == "undefined" || null == jsonMsg || jsonMsg.length == 0)
			return;

		var message = "";
		for(var i=0;i<jsonMsg.length;i++)
		{
			message += jsonMsg[i] + "</br>";
		}

		$("#errMsgDiv").html(message);
		$("#errMsgDiv").show();
	},
	/*******************设置年度下拉框***************/
	GetFyYear:function(){
		  //获取当前时间
		  var dd = new Date();
		  //获取月份(月份是0-11)
		  var Month = dd.getMonth();
		  var currentYear;
		  //3月31号前是去年，4月1号之后今年
		  if(Month > 2){
			   currentYear = dd.getFullYear()+1;
		  }else{
			   currentYear = dd.getFullYear();
		  }
		  //获取前后年与今年，3个年度
          var size =3;
          for( var i=0; i<size; i++ ){
              var yearOld = currentYear-i;
              $("#fyYear").append($("<option value="+yearOld+">"+yearOld+"</option>"));
          }
	},
	/*******************课题分类下拉框***************/
	GetProjectTypeList:function(project_type){
		var ajaxOptions = {
				type: "post",
				url : 'parm/projectType',// 地址
				dataType: 'json',//返回数据类型
				success : function(data) {
					var size = data.listData.length;
					for( var i=0;i<size;i++){
						$("#projectType").append($("<option value="+data.listData[i].value+">"+data.listData[i].dispName+"</option>"));
					}
						if(project_type!=undefined){
						   	var projectType = project_type.val();
						   	if(projectType != ''&&projectType.length!=0){
						   		$("#projectType").find("option[value="+projectType+"]").attr("selected",true);
						   	}
						}
				}// 请求成功时处理
			};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptions);
	},

	/*******************设备使用类别下拉框***************/
	GetUserTypeList:function(use_Type,typeId){
		var ajaxOption = {
				type: "post",
				url : 'parm/userType',// 地址
				dataType: 'json',//返回数据类型
				success : function(data) {
					var size = data.listData.length;
					for( var i=0;i<size;i++){
						$(typeId).append($("<option value="+data.listData[i].value+">"+data.listData[i].dispName+"</option>"));
					}
					if(use_Type!=undefined){
					   	var useType = use_Type.val();
					   	if(useType != ''&&useType.length!=0){
					   		$(typeId).find("option[value="+useType+"]").attr("selected",true);
					   	}
					}
				}// 请求成功时处理
			};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOption);
	},

	/*******************设备使用状态下拉框***************/
	GetUseStateList:function(use_State,stateId){
		var ajaxOption = {
				type: "post",
				url : 'parm/useState',// 地址
				dataType: 'json',//返回数据类型
				success : function(data) {
					var size = data.listData.length;
					for( var i=0;i<size;i++){
						$(stateId).append($("<option value="+data.listData[i].value+">"+data.listData[i].dispName+"</option>"));
					}

					if(use_State!=undefined){
					   	var useState = use_State.val();
					   	if(useState != ""&&useState.length!=0){
					   		$(stateId).find("option[value="+useState+"]").attr("selected",true);
					   	}
					}
				}// 请求成功时处理
			};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOption);
	},

	/*******************设备使用位置下拉框***************/
	GetUsePlaceList:function(eq_place,locationId){
		var ajaxOptionn = {
				type: "post",
				url : 'parm/usePlace',// 地址
				dataType: 'json',//返回数据类型
				success : function(data) {
					var size = data.listData.length;
					for( var i=0;i<size;i++){
						$(locationId).append($("<option value="+data.listData[i].value+">"+data.listData[i].dispName+"</option>"));
					}
					if(eq_place!=undefined){
					   	var eqplace = eq_place.val();
					   	if(eqplace != ""&&eqplace.length!=0){
					   		$(locationId).find("option[value="+eqplace+"]").attr("selected",true);
					   	}
					}
				}// 请求成功时处理
			};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptionn);
	},
	/*******************设备使用位置下拉框(单参数)***************/
	GetUserPlaceList:function(locationId,locationn){
		var ajaxOptionn = {
				type: "post",
				url : 'parm/usePlace',// 地址
				dataType: 'json',//返回数据类型
				success : function(data) {
					var size = data.listData.length;
					for( var i=0;i<size;i++){
						$(locationId).append($("<option value="+data.listData[i].value+">"+data.listData[i].dispName+"</option>"));
					}
				   	if(locationn != ""&&locationn.length!=0){
				   		$(locationId).find("option[value="+locationn+"]").attr("selected",true);
				   	}

				}// 请求成功时处理
			};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOptionn);
	},

	/*******************数据项类型下拉框***************/
	GetDateTypeList:function(date_Type,document_Type,documentItemTypeId){
		var ajaxOption = {
				type: "post",
				url : 'parm/docItemType',// 地址
				dataType: 'json',//返回数据类型
				success : function(data) {
					var size = data.listData.length;
					for( var i=0;i<size;i++){
						$(documentItemTypeId).append($("<option value="+data.listData[i].value+">"+data.listData[i].dispName+"</option>"));
					}
					   	var dateType = date_Type.val();
					   	if(dateType== '1'){
					   		$(documentItemTypeId).find("option[value="+dateType+"]").attr("selected",true);
					   	}
					   	if(document_Type!=undefined){
						   	var documentType = document_Type.val();
						   	if(documentType != ""){
						   		$(documentItemTypeId).find("option[value="+documentType+"]").attr("selected",true);
						   	}
						}
				}// 请求成功时处理
			};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOption);
	},

	/*******************法人类型下拉框***************/
	GetLegalTypeList:function(date_Type,legalId){
		var ajaxOption = {
				type: "post",
				url : 'parm/legalType',// 地址
				dataType: 'json',//返回数据类型
				success : function(data) {
					var size = data.listData.length;
					for( var i=0;i<size;i++){
						$(legalId).append($("<option value="+data.listData[i].type1+">"+data.listData[i].type1Name+"</option>"));
					}

				}// 请求成功时处理
			};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOption);
	},
	/*******************操作内容下拉框***************/
	GetOprContentList:function(oprId){
		var ajaxOption = {
				type: "post",
				url : 'parm/oprContent',// 地址
				dataType: 'json',//返回数据类型
				success : function(data) {
					var size = data.listData.length;
					for( var i=0;i<size;i++){
						$(oprId).append($("<option value="+data.listData[i].value+">"+data.listData[i].dispName+"</option>"));
					}

				}// 请求成功时处理
			};
		var comAjax = new Globals.comAjaxJS();
		comAjax.sendAjax(ajaxOption);
	},
	/*******************设置年月下拉框 (范围：前一年本月，到后一年本月)***************/
	YearMonthSelect:function (id){
		//id为 select控件 id

		//年月选择框的数据
		function year_month() {
           	var result = [];
           	//获取当前年月
           	var nowy = new Date();
           	var nowyear = nowy.getFullYear();
           	var nowmonth = nowy.getMonth()+1;
           	//后一年
           	var hy = new Date();
           	for(var a =1; a <=nowmonth; a++){
           		a = a < 10 ? "0" + a : a;
           		result.push(nowyear+1 + "年" + a + "月");
           	}
			//倒序
       	 	result = result.reverse();
       	 	//当前年月
       	 	for(var b =12; b >=1; b--){
       	 		b = b < 10 ? "0" + b : b;
       	 		result.push(nowyear + "年" + b + "月");
       	 	}

       	 	//前一年
       	 	var qy = new Date();
       	 	for(var c =12;c >= nowmonth; c--){
       	 		c = c < 10 ? "0" + c : c;
       	 		result.push(nowyear-1 + "年" + c + "月");
       	 	}

           	return result;
       	}

	   	var allinfo = year_month();
		//生成前后12个月日期下拉框
		for(allinfo , i = 0; i < allinfo.length; i++) {
		    $(id).append("<option value='" + allinfo[i] + "'>" + allinfo[i] + "</option>");
		}
		//当前月为默认选中
		if(id == '#fy_yeay'){
			$(id).val("")
		}else{
			$(id).val(allinfo[12]);
		}

	},
	/*******************设置年月下拉框 (范围：前一年本月，到后一年本月)结束***************/

	/*******************ajax请求成功后 通用api错误msg***************/
	Globals_API_ERR_MSG:function(code){
		switch(code){
			case ConstCode.RTN_CODE_NOT_LOGIN()://未登录
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_NOT_LOGIN());
				break;
			case ConstCode.RTN_CODE_CONNECT_TIMEOUT()://连接超时
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_CONNECT_TIMEOUT());
				break;
			case ConstCode.RTN_CODE_UNKNOWN()://未知错误发生
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
				break;
			case ConstCode.RTN_CODE_SESSION_TIMEOUT()://登录超时(session timeout)
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_SESSION_TIMEOUT());
				break;
			case ConstCode.RTN_CODE_NOT_PERMITTED()://无权限
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_NOT_PERMITTED());
				break;
			case ConstCode.RTN_CODE_API_NOT_FOUND()://API接口未找到
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_API_NOT_FOUND());
				break;
			case ConstCode.RTN_CODE_RESULT_NULL()://返回结果NULL错误
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_RESULT_NULL());
				break;
			case ConstCode.RTN_CODE_INVALID_PARAMS()://无效的接口参数
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_INVALID_PARAMS());
				break;
			case ConstCode.RTN_CODE_ILLEGAL_REQUEST()://非法请求客户端
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_ILLEGAL_REQUEST());
				break;
			case ConstCode.RTN_CODE_DATA_PARSER_ERROR()://服务器端返回的数据格式错误
				Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_DATA_PARSER_ERROR());
				break;
			default:
				break;
		}
	},
	/*******************ajax请求成功后 通用api错误msg结束***************/


	/*******************数组去重***********************************/
	ArrayUnique:function (arr) {
	    var result = [], hash = {};
	    for (var i = 0, elem; (elem = arr[i]) != null; i++) {
	        if (!hash[elem]) {
	            result.push(elem);
	            hash[elem] = true;
	        }
	    }
	    return result;
	},
	/*******************数组去重结束***********************************/


	/*******************设置年月下拉框 (范围：本年  年月)***************/
	ThisYearMonthSelect : function (id){
		moment.locale('zh-cn'); // 中文
		//id为 select控件 id
		var months = moment.monthsShort();

		for(var i=0;i<months.length;i++){
			var m = i+1 < 10 ? "0" + months[i]:months[i];
			$(id).append("<option value='" +  moment().format("YYYY年")+months[i] + "'>" + moment().format("YYYY年")+m + "</option>");
		}
		//当前月为默认选中
		$(id).get(0).selectedIndex =  parseInt(moment().format("M"))-1;
	},
	/*******************设置年月下拉框 (范围：本年  年月)结束***************/

	/*******************返回首页***************/
	ReturnHomePage:function(){
	   var pathName = document.location.pathname;
	   var index = pathName.substr(1).indexOf("/");
	   var result = pathName.substr(0,index+1);
		window.location=result+'/top/init'
	},

	/*******************获取cookie值***************/
	getCookie : function(name){
		var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
		if(arr=document.cookie.match(reg)){
			if(unescape(arr[2])=='-1'){
				return -1;
			}else{
				return unescape(arr[2]);
			}
		}else{
			return 5;
		}
	},

	/*******************按钮权限设定***************/
	ActionSetting : function(){
		var sysActions =$("#sysactions").val().replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
		var allActions =$("#allactions").val().replace(/\[|]/g,'').replace(/\s+/g,"").split(",");
		var clone = allActions.slice(0);
	    for(var i = 0; i < sysActions.length; i ++) {
	        var temp = sysActions[i];
	        for(var j = 0; j < clone.length; j ++) {
	            if(temp === clone[j]) {
	                //remove clone[j]
	                clone.splice(j,1);
	            }
	        }
	    }
		$(":button,a").each(function(){
			 var id = $(this).attr('name');
			 if(clone.indexOf(id)>=0){
				 $(this).remove();
			 }
		})
	},
	/*******************路径获取***************/
	getContextPath : function() {
	    var pathName = document.location.pathname;
	    var index = pathName.substr(1).indexOf("/");
	    var result = pathName.substr(0,index+1);
	    return result;
	  }
};