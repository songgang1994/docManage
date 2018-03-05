/*
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：曾雷 --%>
 * <%-- 模块名：工时填写 --%>
 */
Namespace.register("Globals.workingTimesJS");

Globals.workingTimesJS = (function() {
	var _this = null;
	//添加行号变量
	var maxNum = ConstText.HTML_MSG_NUM_ZERO();
	var nowNum = ConstText.HTML_MSG_NUM_ZERO();
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	// 初始化
	var _initPage = function() {

		// 日期初始化
		_datetimepickerInit();
		// 表单验证初始化
		formValid();

		// 表单提交
		$("#hour_form_submit").on("click", function() {
			formValid();
			_formSubmit();
		});

		$("workingTimes").on("click",function(){
			formValid();
			_formworkingTimes();
		})

		// TODO: 从popup选择之后，在执行课题删除
		$("#projectTable").on("click", "tbody tr td button", function() {
				var tr = $(this).closest('tr');
				var deleteName = $(this).parent().prev().prev().text()
				var user = ConstMsg.BIZ_CODE_SHUR_DELETE()+deleteName+"?";
				 Globals.msgboxJS.Confirm(user,function(){
				tr.remove();
			 })
		});

		//删除所在行的课题
		$(".dede").on("click",function(){
			var tr = $(this).closest('tr');
			var deleteName = $(this).parent().prev().prev().prev().text()
			var user = ConstMsg.BIZ_CODE_SHUR_DELETE()+deleteName+"?";
			 Globals.msgboxJS.Confirm(user,function(){
			tr.remove();
			 })
		})

		// 添加课题
		$("#addProject").on('click', function() {
			_addProject();
		});

		// 工时填写判断
		_workingTime();

		// 回退按钮
		$("#ret").on('click', function() {
			_ret();

		})

		//改变日期查询工时信息
		$("#selectDate").on("change",function(){
			var time=$("#selectDate").val();
			var userId=$("#userId").val();
			var  ajaxOptionNo = {
					async:false,
		            url:"outlay/hourSearch",
		            type:"post",
		            data:{
		            	time:time,
		            	userId:userId
		            },
		            success:function(data){
		            	//判断查询结果是否为空
		            	if(data.listData.length==ConstText.HTML_MSG_NUM_ZERO()){
		            		//查询结果为空
		            		$(".body").remove();
		            		$("#new").val(0)
		            	}else{
		            		//查询结果不为空
		            		$(".body").remove();
		            		$("#new").val(0)
		            		var newi = 0;
		            		var newnum = $("#new").val();
		            		var all=""
		            		for(var i =0;i<data.listData.length;i++){
		            			newi = parseInt(i+1)
		            			all+="<tr class='body'>"+
		            							"<td id='projectNo' class='projectNo' style='display:none'>" +
		            								data.listData[i].projectNo+
		            							"</td>"+
		            							"<td width='10%' align='left'>" +
	            									(++newnum)+
	            								"</td>"+
		            							"<td class='fyYear' width='20%' align='left'>" +
		            								data.listData[i].fyYear+
		            							"</td><input type='hidden' class='number'>"+
		            							"<td  width='40%' align='left'>" +
		            								data.listData[i].projectName+
		            							"</td>"+
		            							"<td  width='20%' align='left'>" +
		            								"<div class='form-group'>"+
		            									"<input type='text' name='workingTimes"+newi+"' class='workingTimes"+newi+"' value='"+data.listData[i].workingTimes+"'/>"+
		            									"<input  type='hidden' class='workingTimesi' name='workingTimesi' value='"+newi+"' />"+
		            									"</div>"+
		            							"</td>"+
		            							"<td  width='10%'>" +
		            									"<button class='btn dede'>" +
		            										"x&nbsp;&nbsp;删" +
		            									"</button>" +
		            							"</td>"+
		            					"</tr>"
		            		}
		            		$("#tbodyAll").append(all)
		            		var num=$(".number").length;
		            		$("#new").val(num);
		            		formValid();
		            		for(var i=1;i<=newi;i++){
		            			 $("input[name = 'workingTimes"+i+"']").rules("add", {
		            				 required: true,
		          		     	   number : true,
		          		     	   decimal : true,
		          		     	   messages: {
		          						required : ConstMsg.HTML_MSG_MUSTINPUT_WORKINGTIME(),
		          						number : ConstMsg.HTML_MSG_MUSTINPUT_NUMBER(),
		          						decimal : ConstMsg.HTML_MSG_MAXLENGTH_WORKINGTIME()
		          		     	   }
		          			   });
		          			 $("#maxNum").val(newi);
		            		}
		            	}
		            }
			};//发送ajax请求
		    var comAjax = new Globals.comAjaxJS();
		    comAjax.sendAjax(ajaxOptionNo);
		})

	};

	/**
	 * ************************************* 自定义方法
	 * ******************************************
	 */

	var _formworkingTimes = function() {
		formValid();
		 $(".workingTimesi").each(function(){
			 var i = $(this).val();
			 $("input[name = 'workingTimes"+i+"']").rules("add", {
				 required: true,
		     	   number : true,
		     	   decimal : true,
		     	   messages: {
						required : ConstMsg.HTML_MSG_MUSTINPUT_WORKINGTIME(),
						number : ConstMsg.HTML_MSG_MUSTINPUT_NUMBER(),
						decimal : ConstMsg.HTML_MSG_MAXLENGTH_WORKINGTIME()
		     	   }
			   });
			 maxNum = i;
			 $("#maxNum").val(i);

		 })



	}



	// 查询
	var _formSubmit = function() {

		// 防止表单默认提交
		event.preventDefault();
		// 调用表单验证
		if ($("#hour_form").valid() == false) {
			return;
		}
		// 表单参数获取
		var projectNo = [];
		var workingtimes = [];
		var fyYear = [];

		var dateTm = $("#selectDate").val();
		$('#projectTable').find('tbody tr').each(function() {
			projectNo.push($(this).find("td").eq(0).text());
			fyYear.push($(this).find("td").eq(2).text());
			workingtimes.push($(this).find("td").find("input").val());

		});
		var userId=$("#userId").val();
		var flug = $("#flug").val()
		var newflug = parseInt(flug)
		if(newflug != ConstText.HTML_MSG_NUM_ONE() ){
			newflug = ConstText.HTML_MSG_NUM_ZERO()
		}

		var ajaxOptions = {
			type : "post",
			url : 'outlay/hourentersub',// 地址
			traditional : true,
			data : {
				workingtimes : workingtimes,
				projectNo : projectNo,
				fyYear : fyYear,
				dateTm : dateTm,
				flug : newflug,
				userId : userId
			},
			success : function(data) {
				_cb_formSubmit_successFun(data);

			},
			error : function(data) {
				Globals.msgboxJS.ToastrError(ConstMsg.HTML_MSG_ERROR_SYSTEM())
			}
		};

		_this.sendAjax(ajaxOptions);
	};

	// 回调
	function _cb_formSubmit_successFun(data) {
		switch (data.data.bizCode) {
		case ConstCode.BIZ_CODE_SUCCESS():
			if($("#flug").val()==""){
				Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_SUCCESS());
				//刷新页面
				timeID=setTimeout(function(){
					clearTimeout(timeID);
					window.location.reload()
				},ConstText.HTML_MSG_NUM_MS());
			}else{
				 Globals.msgboxJS.ToastrSuccess(ConstMsg.BIZ_CODE_SUCCESS());
				 _cancel();
			}
			break;
		case 107:
			Globals.msgboxJS.ToastrError(ConstMsg.HTML_MSG_FILLIN_FAIL())
			break;
		}

	}

	//返回函数
	function _cancel(){
		var deptNames=$("#deptNames1").val();
		var fyYear=$("#fyYear1").val();
		var deptId=$("#deptId1").val();
		var projectType=$("#projectType1").val();
		var projectName=$("#projectName1").val();
		timeID=setTimeout(function(){
			clearTimeout(timeID);
			window.location.href =path+"/outlay/list?fyYear="+fyYear+"&deptNames="+encodeURI(encodeURI(deptNames))+"&deptId="+deptId+"&projectName="+encodeURI(encodeURI(projectName))+"&projectType="+projectType;
		},ConstText.HTML_MSG_NUM_MS());
	}

	// 添加课题
	var _addProject = function() {
		var project = new Globals.projectSearchPopupJS();
		project.ShowModal('checkbox',function(data) {
		var newnum = $("#new").val();
		if($("#new").val()==0){
			$(".body").remove();
		}
			// 生成table
			var tr = "";
			var allNamemsg = "";
			var tatel = ConstText.HTML_MSG_NUM_ZERO();
			nowNum = parseInt($("#maxNum").val());
			var u = ConstText.HTML_MSG_NUM_ZERO();
			maxNum = $("#maxNum").val();
			for (var i = 0; i < data.length; i++) {
				var projectNo = data[i][0].projectNo
				var fyYear = data[i][0].fyYear
				var allName = data[i][0].projectName
				var count = ConstText.HTML_MSG_NUM_ZERO()
				var newflug = parseInt($("#flug").val())
				var newug = parseInt($("#toflug").val())
				if(newflug == 1 ){
					if($(".toprojectNo").length>ConstText.HTML_MSG_NUM_ZERO()){
						$(".hidbody").each(function(){
							var toprojectNo = $(this).eq(0).find(".toprojectNo").val();
							var tofyYear = $(this).eq(0).find(".tofyYear").val();
							if(toprojectNo == projectNo && tofyYear == fyYear){
								count++;
								allNamemsg += allName + ","
							}
						})

						$(".body").each(function(){
	                		var newprojectNo = $(this).eq(0).find(".projectNo").text();
	                		var newfyYear = $(this).eq(0).find(".fyYear").text();
	                		if(newprojectNo == projectNo && newfyYear == fyYear){
	                			count++;
                		}
                	})

						if(count == ConstText.HTML_MSG_NUM_ZERO()){
							var nu = ++maxNum
							tr += '<tr class="body"><td id="projectNo" class="projectNo" style="display:none">'
	    						+ data[i][0].projectNo
	    						+'</td><td width="10%" align="left">'
	    						+ (++newnum)
	    						+ '</td><td class="fyYear" width="20%" align="left">'
	    						+ data[i][0].fyYear
	    						+ '</td><input type="hidden" class="number"><td width="40%" align="left">'
	    						+ data[i][0].projectName
	    						+ '</td><td width="20%" align="left"><div class="form-group"><input type="text" name="workingTimes'+nu+'" class="workingTimes" value=""></div></td><td width="10%"><button class="btn dede">x&nbsp;&nbsp;删</button></td></tr>'
						}
				}else{
					var nu = ++maxNum
						tr += '<tr class="body"><td id="projectNo" class="projectNo" style="display:none">'
								+ data[i][0].projectNo
								+'</td><td width="10%" align="left">'
								+ (++newnum)
								+ '</td><td class="fyYear" width="20%" align="left">'
								+ data[i][0].fyYear
								+ '</td><input type="hidden" class="number"><td  width="40%" align="left">'
								+ data[i][0].projectName
								+ '</td><td  width="20%" align="left"><div class="form-group"><input type="text" name="workingTimes'+nu+'" class="workingTimes" value=""></div></td><td  width="10%"><button class="btn dede">x&nbsp;&nbsp;删</button></td></tr>'

				}
				}else if(newug == 2){
					if($(".toprojectNo").length>ConstText.HTML_MSG_NUM_ZERO()){
						$(".hidbody").each(function(){
							var toprojectNo = $(this).eq(0).find(".toprojectNo").val();
							var tofyYear = $(this).eq(0).find(".tofyYear").val();
							if(toprojectNo == projectNo && tofyYear == fyYear){
								count++;
								allNamemsg += allName + ","
							}
						})

						$(".body").each(function(){
	                		var newprojectNo = $(this).eq(0).find(".projectNo").text();
	                		var newfyYear = $(this).eq(0).find(".fyYear").text();
	                		if(newprojectNo == projectNo && newfyYear == fyYear){
	                			count++;
                		}
                	})

						if(count == ConstText.HTML_MSG_NUM_ZERO()){
							var nu = ++maxNum
							tr += '<tr class="body"><td id="projectNo" class="projectNo" style="display:none">'
	    						+ data[i][0].projectNo
	    						+'</td><td  width="10%" align="left">'
	    						+ (++newnum)
	    						+ '</td><td class="fyYear" width="20%" align="left">'
	    						+ data[i][0].fyYear
	    						+ '</td><input type="hidden" class="number"><td  width="40%" align="left">'
	    						+ data[i][0].projectName
	    						+ '</td><td  width="20%" align="left"><div class="form-group"><input type="text" name="workingTimes'+nu+'" class="workingTimes" value=""></div></td><td  width="10%" ><button class="btn dede">x&nbsp;&nbsp;删</button></td></tr>'
						}
				}else{
					var nu = ++maxNum
						tr += '<tr class="body"><td id="projectNo" class="projectNo" style="display:none">'
								+ data[i][0].projectNo
								+'</td><td  width="10%" align="left">'
								+ (++newnum)
								+ '</td><td class="fyYear" width="20%" align="left">'
								+ data[i][0].fyYear
								+ '</td><input type="hidden" class="number"><td  width="40%" align="left">'
								+ data[i][0].projectName
								+ '</td><td  width="20%" align="left"><div class="form-group"><input type="text" name="workingTimes'+nu+'" class="workingTimes" value=""></div></td><td  width="10%"><button class="btn dede">x&nbsp;&nbsp;删</button></td></tr>'

				}
				}else{
					if($(".projectNo").length>ConstText.HTML_MSG_NUM_ZERO()){
						$(".body").each(function(){
		                		var newprojectNo = $(this).eq(0).find(".projectNo").text();
		                		var newfyYear = $(this).eq(0).find(".fyYear").text();
		                		if(newprojectNo == projectNo && newfyYear == fyYear){
		                			count++;
		                			tatel++;
		                			allNamemsg += allName + ","
		                		}
		                	})
		                	if(count == ConstText.HTML_MSG_NUM_ZERO()){
		    					var nu = ++maxNum
		                		tr += '<tr class="body"><td id="projectNo" class="projectNo" style="display:none">'
		    						+ data[i][0].projectNo
		    						+'</td><td  width="10%" align="left">'
		    						+ (++newnum)
		    						+ '</td><td class="fyYear" width="20%" align="left">'
		    						+ data[i][0].fyYear
		    						+ '</td><input type="hidden" class="number"><td  width="40%" align="left">'
		    						+ data[i][0].projectName
		    						+ '</td><td  width="20%" align="left"><div class="form-group"><input type="text" name="workingTimes'+nu+'" class="workingTimes" value=""></div></td><td  width="10%"><button class="btn dede">x&nbsp;&nbsp;删</button></td></tr>'
		                	}
						}else{
							var nu = ++maxNum
							tr += '<tr class="body"><td id="projectNo" class="projectNo" style="display:none">'
								+ data[i][0].projectNo
								+'</td><td  width="10%" align="left">'
								+ (++newnum)
								+ '</td><td class="fyYear" width="20%" align="left">'
								+ data[i][0].fyYear
								+ '</td><input type="hidden" class="number"><td  width="40%" align="left">'
								+ data[i][0].projectName
								+ '</td><td  width="20%" align="left"><div class="form-group"><input type="text" name="workingTimes'+nu+'" class="workingTimes" value=""></div></td><td  width="10%"><button class="btn dede">x&nbsp;&nbsp;删</button></td></tr>'

							}

				}
				$("#maxNum").val(maxNum)
			}



			var number = parseInt($("#new").val())+parseInt(data.length-tatel);
			$("#new").val(number);
			$('#projectTable tbody').append(tr);

			// 工时填写判断
			_workingTime();

			if(allNamemsg!=""){
        		Globals.msgboxJS.ToastrError(allNamemsg+ConstMsg.HTML_MSG_DEPART_ESXIT())

        	}

			formValid();
			for(var i= nowNum + 1; i <= maxNum; i++){
				$("input[name = 'workingTimes"+i+"']").rules("add", {
			     	   required: true,
			     	   number : true,
			     	   decimal : true,
			     	   messages: {
							required : ConstMsg.HTML_MSG_MUSTINPUT_WORKINGTIME(),
							number : ConstMsg.HTML_MSG_MUSTINPUT_NUMBER(),
							decimal : ConstMsg.HTML_MSG_MAXLENGTH_WORKINGTIME()
			     	   }
				   });
			}

		});


	};

	// 回退按钮
	var _ret = function() {
		if($("#flug").val()==1){
			var deptNames=$("#deptNames1").val();
			var fyYear=$("#fyYear1").val();
			var deptId=$("#deptId1").val();
			var projectType=$("#projectType1").val();
			var projectName=$("#projectName1").val();
			window.location.href =path+"/outlay/list?fyYear="+fyYear+"&deptNames="+encodeURI(encodeURI(deptNames))+"&deptId="+deptId+"&projectName="+encodeURI(encodeURI(projectName))+"&projectType="+projectType;
		}else if($("#toflug").val()==2){
			window.history.back();
		}else{
			//返回上一页
			Globals.utilJS.ReturnHomePage();
		}

	};

	// 工时填写判断
	var _workingTime = function() {
		var myDate = new Date();
		var selectDate = $("#selectDate").val();

		var myYear = myDate.getFullYear();
		var myMonth = myDate.getMonth() + 1;
		var myDate = myDate.getDate();

		// 选择条件日期
		var sYear = parseInt(selectDate.substring(0, 4));
		var sMonth = parseInt(selectDate.substring(5, 7));
		var sDate = parseInt(selectDate.substring(9, 11));

		// 当前系统日期大于5号 不能对之前月份课题操作
		if(myYear > sYear){
			// TODO: 当前日期不能操作当前日期以后的课题
			if(myDate < ConstText.HTML_MSG_NUM_FIRE() && myYear - ConstText.HTML_MSG_NUM_ONE() == sYear && sMonth == ConstText.HTML_MSG_NUM_TER() && myMonth == ConstText.HTML_MSG_NUM_ONE()){
				$(".workingTimes").attr("readonly", false)
			}else{
				$(".workingTimes").attr("readonly", "readonly")
			}
		}

		if(myYear <= sYear){
			if(myDate >= ConstText.HTML_MSG_NUM_FIRE() && myMonth > sMonth){
				$(".workingTimes").attr("readonly", "readonly")
			}else if(myDate < ConstText.HTML_MSG_NUM_FIRE() && myMonth - ConstText.HTML_MSG_NUM_ONE() >sMonth){
				$(".workingTimes").attr("readonly", "readonly")
			}else{
				$(".workingTimes").attr("readonly", false)
			}
		}

	};

	// 选择时间
	function _datetimepickerInit() {
		$.datepicker.regional['zh-CN'] = {
			changeMonth : true,
			changeYear : true,
			clearText : '清除',
			clearStatus : '清除已选日期',
			closeText : '关闭',
			closeStatus : '不改变当前选择',
			prevText : '<上月',
			prevStatus : '显示上月',
			prevBigText : '<<',
			prevBigStatus : '显示上一年',
			nextText : '下月>',
			nextStatus : '显示下月',
			nextBigText : '>>',
			nextBigStatus : '显示下一年',
			currentText : '今天',
			currentStatus : '显示本月',
			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月',
					'九月', '十月', '十一月', '十二月' ],
			monthNamesShort : [ '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'10', '11', '12' ],
			monthStatus : '选择月份',
			yearStatus : '选择年份',
			weekHeader : '周',
			weekStatus : '年内周次',
			dayNames : [ '星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六' ],
			dayNamesShort : [ '周日', '周一', '周二', '周三', '周四', '周五', '周六' ],
			dayNamesMin : [ '日', '一', '二', '三', '四', '五', '六' ],
			dayStatus : '设置 DD 为一周起始',
			dateStatus : '选择 m月 d日, DD',
			dateFormat : 'yy-mm-dd',
			firstDay : 1,
			initStatus : '请选择日期',
			isRTL : false
		};
		$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
		$("#selectDate").prop("readonly", true).datepicker({
			changeMonth : true,
			dateFormat : "yy/mm/dd",
			onClose : function(selectedDate) {
			}
		});
		if($(".totime").val().length == 10){
			var time = $(".totime").val()
			$("#selectDate").val(time);
		}else{
			$("#selectDate").val(getFormatDate());
		}
		// 设置默认时间

	}

	// 获取当前日期
	function getFormatDate() {
		var nowDate = new Date();
		var year = nowDate.getFullYear();
		var month = nowDate.getMonth() + 1 < 10 ? "0"
				+ (nowDate.getMonth() + 1) : nowDate.getMonth() + 1;
		var date = nowDate.getDate() < 10 ? "0" + nowDate.getDate() : nowDate
				.getDate();
		return year + "/" + month + "/" + date;
	}

	//判断工时格式是否为## 或#.## 或##.## 或#.# 或##.#
	$.validator.addMethod("decimal",function(value,element,params){
		if(value.indexOf(".") ==0){
			return false;
		}else{
			if(value.indexOf(".") ==1 || value.indexOf(".") ==2){
				if(value.length <= 5){
					return true;
				}else{
					return false;
				}

			}else{
				if(value.length >0 &&value.length <=2){
					return true;
				}else{
					return false;
				}
			}
		}
	})
	// 表单验证
	function formValid() {


		$('#hour_form').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			ignore : "",
			rules : {
				workingTimes : {
					required : true,
					number : true,
					decimal: true,
				}
			},
			messages : {
				workingTimes : {
					required : ConstMsg.HTML_MSG_MUSTINPUT_WORKINGTIME(),
					number : ConstMsg.HTML_MSG_MUSTINPUT_NUMBER(),
					decimal : ConstMsg.HTML_MSG_MAXLENGTH_WORKINGTIME()
				}
			},
			invalidHandler : function(event, validator) {

			},

			highlight : function(element) { // hightlight error inputs
				$(element).closest('.form-group').addClass('has-error');

			},

			success : function(label) {
				label.closest('.form-group').removeClass('has-error');
				label.remove();

			}
		});
	}

	var GetConst = function(BizCode) {
		var bizCode = new Globals.constJS();
		return bizCode.GetCode(BizCode);
	};

	// 构造方法
	function constructor() {
		_this = this;
		this.InitPage = _initPage;
	}

	return constructor;

})();
// 继承公共AJAX类
Globals.workingTimesJS.prototype = new Globals.comAjaxJS();
