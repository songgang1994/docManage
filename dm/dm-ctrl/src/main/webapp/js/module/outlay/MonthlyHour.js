  /*
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：曾雷 --%>
 * <%-- 模块名：月度工时 --%>
 */
Namespace.register("Globals.monthlyHourJS");



Globals.monthlyHourJS = (function() {
	var _this = null;

	var _initPage = function() {

		_YearMonthSelect();

		//初始化表单验证
		_initformvaild();

		// 月度工时查询监听
		$("#queryMonthHours").on("click", function() {
			_queryMonthHours();
		});

		// 导出按钮监听
		$("#export").on("click", function() {
			_exportMonthHour();
		});
		//返回按钮
		$('#cancel').on('click', function() {
			Globals.utilJS.ReturnHomePage();
		});
		//返回后查询
		_ProjectInit();
		//部门检索popup
		$("#directorSearch").click(function(){
			var project = new Globals.departSearchPopupJS();
		    project.ShowModal('combox',function(data){
		    		$("#depart").val(data[0][0].deptId);
		    		$("#departName").val(data[0][0].deptName);
		    		$("#departName-error").remove();
		    })
		})
	};
//==================================表单验证=========================================
	function _initformvaild(){
		$('#manage-form').validate({
    	    errorElement: 'span', //default input error message container
    	    errorClass: 'help-block', // default input error message class
    	    focusInvalid: false, // do not focus the last invalid input
    	    ignore: "",
    	    rules:{
    	    	departName:{
		            required: true,
	        	},
    	    	fyYear:
    	    	{
		            required: true
	        	}

    	    }, //验证表单是否为空
        messages:{
        	departName:{
	   		 	required: ConstMsg.MONTHLY_HOUR_CHOOSE_DEPT(),
    		},
	    	fyYear:
	    	{
	    		required: ConstMsg.MONTHLY_HOUR_CHOOSE_FYYEAR(),
        	}
        },
        invalidHandler : function(event, validator) {

		},
		errorPlacement: function(error, element) {
		    if(element.attr("name") == "departName" ) {
		    	error = $("<p />").append(error);
		    	error.addClass('form-group');
		    	error.addClass('has-error');
		    	error.insertAfter("#directorSearch");
		    } else {
		      error.insertAfter(element);
		    }
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
//=================================表单验证结束=======================================

	// 设置Fy年度
	function _YearMonthSelect(){
		 var fyYear = $("#yearmo").val();
		 if(fyYear==""||fyYear==null){
			 Globals.utilJS.YearMonthSelect("#fy_yeay");
		 }else{
			 Globals.utilJS.YearMonthSelect("#fy_yeay");
			 $("#fy_yeay").find("option[value="+fyYear+"]").attr("selected",true);
		 }
	}

	//返回后查询
	function _ProjectInit(){
		   var deptId = $("#depart").val();
		   var fyYear = $("#fy_yeay").val();
		   if(deptId!=""&&deptId!=null){
			   $("#queryMonthHours").click();
		   }
	}

	// 月度工时查询
	var _queryMonthHours = function() {
		if($("#manage-form").valid() == false){
			return;
		}
		// 获取参数
		var departId = $("#depart").val();
		var fyYear = $("#fy_yeay").val();
		var ajaxOptions = {
			url : "outlay/querymonthlyhour",
			type : "POST",
			dataType : "json",
			data : {
				departId : departId,
				fyYear : fyYear
			},
			success : function(data) {
				// 清空数据
				$("#queryTable").empty();
				// 数据量>0 显示导出按钮和table，否则不显示导出按钮
				if(data.listData.length > ConstText.HTML_MSG_NUM_TWO()){// 存在数据，显示表格
					$("#queryTable").css('display','block');
					$("#appen").empty();
					_cb_queryMonthHours_success(data.listData);
				}else{// 无数据，显示无数据信息
					$("#queryTable").css('display','none');
					//清空div内容
					$("#appen").empty();
					//隐藏导出按钮
					$("#export").hide();
					$("#appen").append("<div align='center' id='noResult' >无查询结果！</div></br>&nbsp;")
				}

			}
		};

		_this.sendAjax(ajaxOptions);

	};

	// 数据处理
	var _cb_queryMonthHours_success = function(data) {
		var lines = data;
		var columnLength = lines[0].columnsVal.length;
		var lineLength = lines.length;

		var thead = "<table id='tableAjax' class='table  table-bordered table-hover dataTable no-footer'><thead><tr>";
		for(var i=0;i<columnLength;i++){
			thead += "<th noWrap>"+String(lines[0].columnsVal[i])+"</th>";
		}
		thead +="</tr></thead>";

		// tbody
		var tbody = "<tbody>";
		for(var i=1;i<lineLength -1;i++){
			// 第一列
			tbody += "<tr><td  noWrap><form action='outlay/personnelhour' id='toPersonHourForm"+lines[i].lineKey+"' method='post'><a href-void class='toPersonHour'><input type='hidden' name='userId' value='"+lines[i].lineKey+"'><input type='hidden' name='fyYear' value=''><input type='hidden' name='deptId' value=''><input type='hidden' name='userName' value='"+lines[i].columnsVal[0]+"'><input type='hidden' name='deptN' value=''>" + lines[i].columnsVal[0] + "<input type='hidden' name='deptName' value=''></a></form></td>";// 姓名
			for(var j=1;j<columnLength;j++){
				tbody += "<td>"+lines[i].columnsVal[j]+"</td>"

			}
			tbody +="</tr>"
		}

		// tfoot
		var tfoot = "<tr>";
		for(var i=0;i<columnLength;i++){
			tfoot += "<td  noWrap>"+lines[lineLength-1].columnsVal[i]+"</td>";
		}
		tfoot += "</tr></tbody></table>";

		table = thead + tbody + tfoot;

		// 显示表格div
		$("#queryTableDiv").css('display','block');
		$("#queryTable").append(table);

		// 计算部门小计
		for (var i = 1; i <= columnLength+1; i++) {// 列
			var departTotal = ConstText.HTML_MSG_NUM_ZERO();
			for(var j = 1; j <= lineLength+1; j++){// 行
				var res = $("#tableAjax tr:eq("+j+") td:eq("+i+")").text();
				if(res == ""){
					res = "0";
				}
				departTotal += parseFloat(res);
			}
			// console.log(departTotal);
			$("#lastLine"+i).html(parseFloat(departTotal).toFixed(1));
		}

		// 设置 样式
		$("#tableAjax").addClass('table table-bordered table-hover dataTable no-footer');
		$("#tableAjax tr th").addClass('text-center');
		$("#tableAjax tr th").css('vertical-align','middle');
		$("#tableAjax tr td").addClass('text-left');
		$("#tableAjax tr td").css('vertical-align','middle');

		// 表格存在数据，显示导出按钮
		$("#export").show();
		//返回按钮显示
		$("#cancel").show();
		// 表格姓名列跳转监听
		$(".toPersonHour").on('click',function(){
			// 获取id标志
			var id  = $(this).parent().find("input[name='userId']").val();
			var departId = $("#depart").val();
			var fyYear = $("#fy_yeay").val();
			var departName = $("#departName").val();
			$(this).parent().find("input[name='fyYear']").val(fyYear);
			$(this).parent().find("input[name='deptId']").val(departId);
			$(this).parent().find("input[name='deptName']").val(departName);
			$('#toPersonHourForm'+id).submit();
		});



	}

	// 导出表格信息
	var _exportMonthHour = function(){
		var confirm = ConstMsg.HTML_TXT_IS_GIVEUP_EXPORT_HOUR_DATA();
		Globals.msgboxJS.Confirm(confirm,function(){
		var ajaxOptions = {
				url : "outlay/exportmonthlyhour",
				type : "POST",
				dataType : "json",
				data : {
					deptName : $("#departName").val(),
					fyYear : $("#fy_yeay").val()
				},
				success : function(data) {
					alert(ConstMsg.HTML_MSG_EXPORT_SUCCESS());
				},
				error : function(data) {
					alert(ConstMsg.HTML_MSG_ERROR_SYSTEM());
				}
			};

		_this.sendAjax(ajaxOptions);

		});

	}

	// 默认时间
	function _getCurrentYearMonth(){
		Date.prototype.Format = function (fmt) {
		    var o = {
		        "M+": this.getMonth() + 1, // 月份
		        "d+": this.getDate(), // 日
		        "H+": this.getHours(), // 小时
		        "m+": this.getMinutes(), // 分
		        "s+": this.getSeconds(), // 秒
		        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
		        "S": this.getMilliseconds() // 毫秒
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		}
	}


	// 构造方法
	function constructor() {
		_this = this;
		this.InitPage = _initPage;
	}

	return constructor;

})();
// 继承公共AJAX类
Globals.monthlyHourJS.prototype = new Globals.comAjaxJS();
