/*
 * <%-- 系统名：NSK-NRDC业务系统 --%>
 * <%-- 作成人：曾雷 --%>
 * <%-- 模块名：个人月度工时 --%>
 */
Namespace.register("Globals.personnelHourJS");

Globals.personnelHourJS = (function() {
	var _this = null;
	//获取路径
	var path =  Globals.utilJS.getContextPath();
	var _initPage = function() {
		// 初始化Fy年月
		Globals.utilJS.YearMonthSelect("#fy_yeay");
		// 设置前画面传过来的fy年月
		$("#fy_yeay").val($("#fyYear").val());

		// 初始化表格信息
		_queryPersonalMonthHours();

		/** *************************** 监听 ************************************ */
		// Fy年月变化监听
		$("#fy_yeay").on('change', function() {
			_queryPersonalMonthHours();
		});

		// 工时填写跳转
		$("#addWorkingTimes").on('click',function(){
			$("#mSubjectQueryForm").submit();
		});

		// 回退按钮
		$("#cancel").on('click', function(e) {
			_cancel(e);

		})
	};

	// 回退按钮
	function _cancel(e){
		//防止表单默认提交
		e.preventDefault();
		e.stopPropagation();
		//返回至上一页面
		var fyYear = $("#fy_yeay").val();
		var deptId = $("#deptId").val();
		var deptName = $("#deptName").val();
		window.location.href =path+"/outlay/monthlyhour?departId="+deptId+"&fyYear="+fyYear+"&deptName="+encodeURI(encodeURI(deptName));

	};
	// 查询个人月度工时
	var _queryPersonalMonthHours = function() {

		// 获取参数
		var userId = $("#userId").val();
		var fyYear = $("#fy_yeay").val();
		var deptId = $("#deptId").val();

		var ajaxOptions = {
			url : "outlay/querypersonnelhour",
			type : "POST",
			dataType : "json",
			data : {
				userId : userId,
				fyYear : fyYear,
				deptId : deptId
			},
			success : function(data) {
				// 清空数据
				$("#queryTable").empty();
				// 数据量>0 显示导出按钮和table，否则不显示导出按钮
				if (data.listData.length > ConstText.HTML_MSG_NUM_ZERO()) {// 存在数据，显示表格
					$("#queryTable").css('display', 'block');
					$("#noResult").css('display', 'none');
					_cb_queryMonthHours_success(data.listData);
				} else {// 无数据，显示无数据信息
					$("#noResult").css('display', 'block');
					$("#queryTable").css('display', 'none');
				}

			},
			error : function(data) {
				alert(ConstMsg.HTML_MSG_ERROR_SYSTEM());
			}
		};

		_this.sendAjax(ajaxOptions);

	};

	// 数据处理
	var _cb_queryMonthHours_success = function(data) {
		var lines = data;
		var lineLength = lines.length;
		var userId = $("#userId").val();
		var userName = $("#userName").val();
		var thead = "<table id='tableAjax' class='table table-bordered table-hover' style='width:100%;'>";
		thead += "<thead><tr style='text-align:center'><th>日期</th><th >课题编号</th><th>课题名称</th><th>所属部门</th><th>工时数</th><th>操作</th>";
		var tbody = "<tbody>";
		var monthTotal = ConstText.HTML_MSG_NUM_ZERO();
		var year = $("#fy_yeay").val().substring(0,4)
		for (var i = 0; i < lineLength; i++) {
			var tatetime = year+'/' + lines[i].dateTm;
			var deptName = "";
			var deptlines = lines[i].department;
			tbody += "<tr><td style='text-align:left;'>" + lines[i].dateTm + "</td><td style='text-align:left;'>"
					+ lines[i].projectNo + "</td><td style='text-align:left;'>" + lines[i].projectName
					+ "</td><td style='text-align:left;'>" + lines[i].deptName + "</td><td style='text-align:left;'>"
					+ lines[i].workingtimes + "</td><td style='text-align:left;'>"
					+"<a href='outlay/hourenterEdit?dateTm="+tatetime
					+ "&userId="+userId
					+"'name='' class='btn-edit ' data-device-no='10'>工时修改</a></td>";

			// 当月合计
			monthTotal = parseFloat(monthTotal) + parseFloat(lines[i].workingtimes);		}
		// last line
		tbody += "<tr><td style='text-align:left;'>月合计</td><td></td><td></td><td></td><td style='text-align:left;'>" + monthTotal
				+ "<td></td></td>";
		table = thead + tbody;

		// 显示表格div
		$("#queryTableDiv").css('display', 'block');
		$("#queryTable").append(table);

		// 设置 样式
		$("#tableAjax").addClass(
				'table table-bordered table-hover dataTable no-footer');
		$("#tableAjax tr th").addClass('text-center');
		$("#tableAjax tr th").css('vertical-align', 'middle');
		$("#tableAjax tr td").addClass('text-center');
		$("#tableAjax tr td").css('vertical-align', 'middle');

		// 合并单元格
		table_rowspan('tableAjax',1);
	};

	/**
	* @ function：合并指定表格列（表格id为table_id）指定列（列数为table_colnum）的相同文本的相邻单元格
	* @ param：table_id 为需要进行合并单元格的表格的id。如在HTMl中指定表格 id="data" ，此参数应为 #data
	* @ param：table_colnum 为需要合并单元格的所在列。为数字，从最左边第一列为1开始算起。
	*/
	function table_rowspan(table_id, table_colnum) {
	    table_firsttd = "";
	    table_currenttd = "";
	    table_connect="";table_connect_first="";
	    table_SpanNum = 0;
	    table_Obj = $("#"+table_id + " tr td:nth-child(" + table_colnum + ")");

	    table_Obj.each(function (i) {
	        if (i == ConstText.HTML_MSG_NUM_ZERO()) {
	            table_firsttd = $(this);
	            table_connect_first =  $(this).closest('tr').find('td:last-child');
	            table_SpanNum = 1;
	        } else {
	            table_currenttd = $(this);
	            table_connect =  $(this).closest('tr').find('td:last-child');
	            if (table_firsttd.text() == table_currenttd.text()) { //这边注意不是val（）属性，而是text（）属性
	                //td内容为空的不合并
	                if(table_firsttd.text() !=""){
	                    table_SpanNum++;
	                    table_currenttd.hide(); //remove();
	                    table_connect.hide();
	                    table_firsttd.attr("rowSpan", table_SpanNum);
	                    table_connect_first.attr("rowSpan", table_SpanNum);
	                }
	            } else {
	                table_firsttd = $(this);
	                table_connect_first =  $(this).closest('tr').find('td:last-child');
	                table_SpanNum = 1;
	            }
	        }
	    });
	};



	// 构造方法
	function constructor() {
		_this = this;

		this.InitPage = _initPage;
	}

	return constructor;

})();
// 继承公共AJAX类
Globals.personnelHourJS.prototype = new Globals.comAjaxJS();
