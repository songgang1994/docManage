/**
 * <%-- 系统名：NSK-NRDC业务系统 --%> <%-- 作成人：曾雷 --%> <%-- 模块名：文档查询 --%>
 */
Namespace.register("Globals.docSearchJS");

Globals.docSearchJS = (function() {

	var _this = null;
	var dt = $('#docSearchTable');
	// 获取路径
	var path = Globals.utilJS.getContextPath();
	function _InitPage() {

		_query();

		// 显示隐藏监听
		$('#queryHide').on('click', function() {
			var text = $(this).text();

			if (text == ConstText.HTML_MSG_STR_HIDDEN()) {
				$(this).text(ConstText.HTML_MSG_STR_DISPLAY());
				$('#query').css('display', 'none');
			} else {
				$(this).text(ConstText.HTML_MSG_STR_HIDDEN());
				$('#query').css('display', 'block');
			}

		});

		$('#queryDoc').on('click', function() {
			_query();
		});

		$('#expartData').on('click', function() {
			_export();
		})
		// 返回事件
		$("#DocSearchCancel").bind("click", function() {
			Globals.utilJS.ReturnHomePage();
		});
	}

	// 查询操作
	var _query = function() {
		var ajaxOptions = {
			type : "post",
			url : 'doc/searchCustomTable',// 地址
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if (data.data.table.length > 0) {// 存在数据，显示表格
						$('#queryTable').css('display', 'block');
						$('#expartDataDiv').css('display', 'block');
						$('#noResult').css('display', 'none');
						$('#expartData').show();
						$("#docSearchTable tbody").empty();
						_queryCustom_Success(data.data);
					} else {
						$('#queryTable').css('display', 'none');
						$('#expartDataDiv').css('display', 'none');
						$('#noResult').css('display', 'block');
						$('#expartData').hide();
					}
				} else {
					Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
				}

			}
		};

		_this.sendAjax(ajaxOptions);
	}

	function _queryCustom_Success(data) {

		// 获取后台数据
		var userId = data.userId;
		var depts = data.depts;
		var linesKey = data.tableKey;
		var lines = data.table;

		var tr = "";
		for (var i = 0; i < lines.length; i++) {

			// true;显示下划线；false：不显示
			var underlineFlag = true;

			// 查询结果．责任人
			var directorId = linesKey[i][2];
			// 查询结果．文档可查看部门
			var docReadDepts = linesKey[i][3];
			// 审核状态
			var docStatus = linesKey[i][1];

			var button = "";
			if (userId == directorId) {

				if (docStatus == '1') {// 做成中
					button = '<a href-void name="delete" >删除&nbsp</a><a href-void name="edit" >修改&nbsp</a>';
				} else if (docStatus == '3') {// 已完成
					button = '<a href-void name="edit" >修改&nbsp</a>';
				}
			} else if (deptId_In_doc(depts, docReadDepts)) {

			} else {
				underlineFlag = false;
			}

			// 画面渲染
			tr += "<tr>";
			for (var j = 0; j < lines[i].length; j++) {

				if (lines[i][j] == null) {
					lines[i][j] = '';
				} else if (lines[i][j] == '') {
					lines[i][j] = '-'
				}
				switch (j) {
				case 0:
					tr += "<input type='hidden' class='docItemCode' value='"
							+ lines[i][j] + "' />";
					break;
				case 1:
					tr += "<input type='hidden' class='docStatus' value='"
							+ docStatus + "' />";
					break;
				case 2:
					tr += "<input type='hidden' class='directorId' value='"
							+ directorId + "' />";
					break;
				case 3:
					tr += "<input type='hidden' class='docReadDept' value='"
							+ docReadDepts + "' />";
					break;
				default:
					var hr = linesKey[i][j];
					switch (hr) {
					case "DOCUMENT_CODE":
						//if (underlineFlag) { // 显示LINK
							tr += "<td ><a href-void name='doc_code'>"
									+ lines[i][j] + "</a></td>";
//						} else {
//							tr += "<td >" + lines[i][j] + "</td>";
//						}
						break;
					case "PARENT_FILE":
						var pFile = lines[i][j].split(',');
						var p0 = pFile[0] == null ? '' : pFile[0];
						var p1 = pFile[1] == null ? '' : pFile[1];
						if (underlineFlag) { // 显示LINK
							tr += "<td ><a href-void name='parent_file'><input type='hidden' value='"
									+ p0 + "'/>" + p1 + "</a></td>";
						} else {
							tr += "<td >" + p1 + "</td>";
						}
						break;
					case "CHILD_FILE":
						var cFiles = lines[i][j].substring(1,
								lines[i][j].length - 1).split('][');
						tr += "<td>";
						for (var z = 0; z < cFiles.length; z++) {
							if (z != 0) {
								tr += "<br/>";
							}
							var cf = cFiles[z].split(",");
							var c0 = cf[0] == null ? '' : cf[0];
							var c1 = cf[1] == null ? '' : cf[1];
							if (underlineFlag) {
								tr += "	<a href-void name='child_file'><input type='hidden' value='"
										+ c0 + "'/>" + c1 + "</a>";
							} else {
								tr += "" + c1;
							}
						}
						tr += "</td>";
						break;
					default:

						if (lines[i][j].indexOf('][') != -1) {// [][]格式数据
							var c = lines[i][j].substring(1,
									lines[i][j].length - 1).split('][');

							tr += "<td>";
							for (var z = 0; z < c.length; z++) {
								if (z != 0) {
									tr += "<br/>";
								}
								var cf = c[z].split(",");
								tr += cf[1];

							}
							tr += "</td>";
						} else if (lines[i][j].indexOf('#') != -1) { // xx#xx,xx#xx
							// 格式数据
							var c = lines[i][j].split(',');
							tr += "<td>";
							for (var z = 0; z < c.length; z++) {
								if (z != 0) {
									tr += "<br/>";
								}
								var cf = c[z].split("#");
								tr += cf[1];

							}
							tr += "</td>";
						} else if (lines[i][j].indexOf(',') != -1) {
							var c = lines[i][j].split(',');
							tr += "<td>";
							for (var z = 0; z < c.length; z++) {
								if (z != 0) {
									tr += "<br/>";
								}
								tr += c[z];
							}
							tr += "</td>";
						} else {

							tr += "<td >" + lines[i][j] + "</td>";
						}
						break;
					}

					break;
				}
			}

			tr += "<td>" + button + "</td></tr>";
		}
		$("#docSearchTable tbody").append(tr);
		$("#docSearchTable tbody td").css('white-space', 'nowrap');
		$("#docSearchTable tbody td").addClass('text-left')

		// 设置 dataTable
		_initDataTable();

		// 解除监听
		_offTableEventListen();
		// 添加监听
		_onTableEventListen();
		// 监听select事件
		$("select[name = 'docSearchTable_length']").change(
				function() {
					page_length = $(this).children('option:selected').val()
					document.cookie = ConstText.DOCSEARCH_PAGE_LENGTH() + "="
							+ page_length + ";path=/";
				});
	}

	function _offTableEventListen() {
		$('#docSearchTable tbody ').off('click', 'a');
	}

	function _onTableEventListen() {
		$('#docSearchTable tbody').on(
				'click',
				'a',
				function() {
					var name = event.target.name;
					// 获取dataTable数据
					var line = $(this).closest('tbody').find("tr").index(
							$(this).parent().parent()[0])
					switch (name) {
					case "edit":// 编辑
						_handle_edit(line);
						break;
					case "delete":// 删除
						_handle_delete(line);
						break;
					case "doc_code":// 文档编号跳转
						_handle_doc_code(line);
						break;
					case "parent_file":// 主文件跳转
						_handle_file(line, this, 1);
						break;
					case "child_file":// 子文件跳转
						_handle_file(line, this, 2);
						break;
					default:
						break;
					}
				});
	}

	function _handle_edit(line) {
		var ajaxOptions = {
			type : "post",
			url : 'doc/handleedit',// 地址
			data : {
				line : line
			},
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if (data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()) {
						location.href = path + "/doc/enter?mode="
								+ data.data.mode + "&documentCode="
								+ data.data.docCode + "&locationType="
								+ ConstText.BACK_TO_DOC_SEARCH();
					} else {
						Globals.msgboxJS.ToastrError(ConstMsg
								.HTML_MSG_NO_AUTHORITY_EDIT_DOC());
					}
				} else {
					Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
				}

			}
		};

		_this.sendAjax(ajaxOptions);
	}

	function _handle_delete(line) {
		var ajaxOptions = {
			type : "post",
			url : 'doc/handledel',// 地址
			data : {
				line : line
			},
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if (data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()) {// 存在数据，显示表格
						Globals.msgboxJS.ToastrSuccess(ConstMsg
								.HTML_MSG_DOC_DELETE_SUCCESS());
						_query();
					} else {
						Globals.msgboxJS.ToastrError(ConstMsg
								.HTML_MSG_NO_AUTHORITY_DELETE_DOC());
					}
				} else {
					Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
				}
			}
		};

		_this.sendAjax(ajaxOptions);
	}

	function _handle_doc_code(line) {
		var ajaxOptions = {
			type : "post",
			url : 'doc/handledoccode',// 地址
			data : {
				line : line
			},
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if (data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()) {// 存在数据，显示表格
						location.href = path + "/doc/enter?mode="
								+ data.data.mode + "&documentCode="
								+ data.data.docCode + "&locationType="
								+ ConstText.BACK_TO_DOC_SEARCH();
					} else {
						Globals.msgboxJS.ToastrError(ConstMsg
								.HTML_MSG_NO_AUTHORITY_READ_DOC());
					}
				} else {
					Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
				}
			}
		};

		_this.sendAjax(ajaxOptions);
	}

	function _handle_file(line, me, type) {
		var fileNo = $(me).closest('a').find('input').val();

		var ajaxOptions = {
			type : "post",
			url : 'doc/handlefile',// 地址
			data : {
				line : line,
				fileNo : fileNo
			},
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if (data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()) {//
						location.href = path
								+ "/doc/download/"
								+ $(me).closest('tr').find('.docItemCode')
										.val() + "/" + type + "?fileNo="
								+ fileNo;
					} else {
						Globals.msgboxJS.ToastrError(ConstMsg
								.HTML_MSG_NO_AUTHORITY_READ_DOC());
					}
				} else {
					Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
				}

			}
		};

		_this.sendAjax(ajaxOptions);
	}

	function _export() {
		var ajaxOptions = {
			type : "post",
			url : 'doc/handleexport',// 地址
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if (data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()) {// 存在数据，显示表格
						// alert('导出成功')
						location.href = path + "/doc/downloadcsv/"
								+ data.data.fileName
					} else {
						// alert('导出失败')
					}
				} else {
					Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
				}
			}
		};

		_this.sendAjax(ajaxOptions);
	}

	function _initDataTable() {
		var pageLength = Globals.utilJS.getCookie(ConstText
				.DOCSEARCH_PAGE_LENGTH());
		$('#docSearchTable').dataTable({
			dom : 't<lp <"col-xs-12 text-right" i>><"clear">',
			scrollX : true,
			destroy : true,
			autoWidth : false,
			searching : false,// 不显示搜索器
			ordering : false,// 不可排序
			lengthMenu : [ [ 5, 10, 15, 20, -1 ], [ 5, 10, 15, 20, "全部" ] ],// 选择每页显示多少条
			pageLength : pageLength,// 每页显示多少条
			pagingType : "bootstrap_full_number",// 分页按钮显示选项
			language : {// 汉化
				info : "第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
				emptyTable : "无可用数据",
				infoEmpty : "没有记录可以显示  ",
				lengthMenu : "_MENU_ ",
				paginate : {
					previous : "上一页",
					next : "下一页",
					last : "最后一页",
					first : "第一页"
				}
			},

			columnDefs : [ {
				"width" : "40px",
				"targets" : "_all"
			} ]
		});
	}

	function deptId_In_doc(depts, docs) {
		for ( var d in depts) {
			if (docs.indexOf(depts[d].deptId) != -1) {
				return true;
			}
		}
		return false;

	}
	// 构造方法
	function constructor() {
		_this = this;
		this.InitPage = _InitPage;

	}

	return constructor;
})();

// 继承公共AJAX类
Globals.docSearchJS.prototype = new Globals.comAjaxJS();