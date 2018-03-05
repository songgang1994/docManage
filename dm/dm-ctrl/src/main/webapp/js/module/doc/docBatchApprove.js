Namespace.register("Globals.docBatchApproveJS");
/*
 * <%-- 系统名：NSK-NRDC业务系统 --%> <%-- 作成人：曾雷 --%> <%-- 模块名：批量审核 --%>
 */
Globals.docBatchApproveJS = (function() {

	var _this = null;
	var dt = $('#batchCheckTable');
	var mid = [],updateTime=[];
	function _initPage() {

		// 初始化表格数据
		query();

		// 初始化审核意见
		formValid();

		// 查询表格数据
		// $("#query").on('click', function() {
		// query();
		// });
		// 返回事件
		$("#DocBatchCancel").bind("click", function() {
			Globals.utilJS.ReturnHomePage();
		});
	}

	// 查询表格数据
	function query() {

		// 获取参数
		var docCode = $('#docCode').val();
		var docType = $('#docType').val();
		var deptId = $('#Dept').val();
		var docStatus = $('#docStatus').val();
		var director = $('#directorId').val();

		var ajaxOptions = {
			type : "post",
			url : 'doc/querybatchtable',// 地址
			dataType : 'json',
			data : {
				docCode : docCode,
				docType : docType,
				deptId : deptId,
				docStatus : docStatus,
				director : director
			},
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if (data.listData.length > 0) {
						$('#queryTable').css('display', 'block');
						$('#doc_comment_form_div').css('display', 'block');
						$('#noResultq').css('display', 'none');
						$('#form_batch_submit').show();
						$('#queryTable').empty();

						_cb_queryBatch_success(data.listData);
					} else {
						$('#queryTable').css('display', 'none');
						$('#doc_comment_form_div').css('display', 'none');
						$('#noResultq').css('display', 'block');
						$('#form_batch_submit').hide();
					}
				}
			} // 请求成功时处理
		};

		_this.sendAjax(ajaxOptions);
	}
	;

	// 查询成功回调处理
	function _cb_queryBatch_success(data) {

		var tr = '<table class="table table-bordered table-hover" id="batchCheckTable">'
				+ '<thead><tr>'
				+ '<th class="text-center" ><input type="checkbox" id="selectAll" /></th>'
				+ '<th class="text-center" >文件编号</th>'
				+ '<th class="text-center" >文档类型</th>'
				+ '<th class="text-center" >所属部门</th>'
				+ '<th class="text-center" >主文件</th>'
				+ '<th class="text-center" >子文件</th>'
				+ '<th class="text-center" >责任人</th>'
				+ '<th class="text-center" >状态</th>'
				+ '</tr></thead><tbody id="queryTableBody">';
		for (var i = 0; i < data.length; i++) {
			var approve = "", disabled = "", time = "";
			// 判断显示模式,责任人设置
			if (data[i].approve == "0") {// 详细模式
				approve = "0";
				disabled = 'disabled="true"';
			} else if (data[i].approve == "1") {// 审核模式
				approve = "3"
			}

			if (data[i].approvalComment == null) {
				data[i].approvalComment = '';
			}

			var fileList = '', childFiles = data[i].docChildList;
			if (data[i].fileName == null) {
				data[i].fileName = ''
			}
			if (data[i].deptName == null) {
				data[i].deptName = ''
			}
			if (childFiles.length != 0) {
				for (j in childFiles) {
					fileList += '<a class="childFile" href="doc/download/'
							+ childFiles[j].documentCode + '/2?fileNo='
							+ childFiles[j].fileNo + '" >'
							+ childFiles[j].fileName + '</a><br/>';
				}
			}
			if (data[i].updateDt == null) {
				time = data[i].createDt;
			} else {
				time = data[i].updateDt;
			}

			tr += '<tr><input type="hidden" name="updateTime" value="'
					+ time
					+ '"/><input type="hidden" '
					+ disabled
					+ ' name="documentCode" value="'
					+ data[i].documentCode
					+ '"><td class="text-center"><input type="checkbox" name="checkbox" '
					+ disabled + '/></td><td ><a href="doc/enter?documentCode='
					+ data[i].documentCode + '&mode=' + approve
					+ '&locationType=' + ConstText.BACK_TO_BATCH_CHECK() + '">'
					+ data[i].documentCode + '</a>' + '</td><td>'
					+ data[i].itemName + '</td><td >' + data[i].deptName
					+ '</td><td ><a href="doc/download/' + data[i].documentCode
					+ '/1" class="fileDown">' + data[i].fileName
					+ '</a></td><td >' + fileList + '</a></td><td >'
					+ data[i].userName
					+ '<input type="hidden" name="directorId" value="'
					+ data[i].director + '"/></td><td>' + data[i].dispName
					+ '</td></tr>'
		}
		tr += '</tbody></table>'
		var pageLength = Globals.utilJS.getCookie(ConstText
				.DOCBATCH_PAGE_LENGTH());
		$('#queryTable').append(tr);
		$('#batchCheckTable').dataTable({
			dom : 't<lp <"col-xs-12 text-right" i>><"clear">',
			destroy : true,
			searching : false,// 不显示搜索器
			ordering : false,// 不可排序
			autoWidth : true,
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

		$('#batchCheckTable thead tr th').eq(0).css('width', '25px')

		// 解除监听
		offTableEventListen();
		// 添加监听
		onTableEventListen(data);
		// 监听select事件
		$("select[name = 'batchCheckTable_length']").change(
				function() {
					var page_length = null;
					page_length = $(this).children('option:selected').val()
					document.cookie = ConstText.DOCBATCH_PAGE_LENGTH() + "="
							+ page_length + ";path=/";
				});
	}
	;

	function offTableEventListen(data) {
		$('#batchCheckTable tbody .enterLocation').off('click');
		$('#form_batch_submit').off('click');
	}

	// 事件监听
	function onTableEventListen(data) {
		var trs = $("#batchCheckTable tr");

		// 文档编号跳转
		$('#batchCheckTable tbody .enterLocation').on('click', function() {
			$(this).closest('form')[0].submit();
		});

		// checkbox全选
		selectAll();

		$('#form_batch_submit').on('click', function() {
			// / 审核意见
			var comment = $('#approve_comment').val();
			formSubmit(1, comment);
		})

	}
	;

	function formSubmit(flag, comment) {

		if($('#doc_comment_form').valid() == false){
			return ;
		}

		if (mid.length == 0) {
			Globals.msgboxJS.ToastrError(ConstMsg.HTML_MSG_CHOOSE_ITEM());
			return;
		}

		var ajaxOptions = {
			type : "post",
			url : 'doc/passorreturn',// 地址
			dataType : 'json',
			traditional : true,
			data : {
				docCode : mid,
				updateTime:updateTime,
				flag : flag,
				comment : comment
			},
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if(data.data.bizCode == ConstCode.BIZ_CODE_DOC_UPDATE_BY_OTHER()){
						var msg = "";
						for (i in data.data.bizExeMsgs) {
							msg += data.data.bizExeMsgs[i] + "<br/>";
						}
						Globals.msgboxJS.ToastrError(msg);
					}else{
						Globals.msgboxJS.ToastrSuccess(ConstMsg
								.HTML_MSG_DOC_EXAMINE_SUCCESS());
						// query();
						setTimeout(function() {
							location.reload();
						}, 2000)
					}
				} else {

				}
			}
		};

		_this.sendAjax(ajaxOptions);

	}

	function selectAll() {
		// 全选
		$('#selectAll').change(
				function(event) {
					var input = $('input:checkbox[name="checkbox"]');
					var docCode,upTime;
					if ($('#selectAll').prop("checked")) {
						input.prop("checked", true);
						$('input:checkbox[name="checkbox"]').each(
								function() {
									if (!$(this).prop("disabled")) {
										docCode = $(this).closest('tr').find(
												'[name="documentCode"]').val();
										upTime = $(this).closest('tr').find(
										'[name="updateTime"]').val();
										if (!checkMidExist(docCode, mid)) {
											mid.push(docCode);
											updateTime.push(upTime)
										} else {
											mid.splice($.inArray(docCode, mid),
													1);
											updateTime.splice($.inArray(upTime, updateTime),
													1);
										}
									}

								})
					} else {
						input.prop("checked", false);
						$('input:checkbox[name="checkbox"]').each(
								function() {
									if (!$(this).prop("disabled")) {
										$(this).closest('tr').removeClass(
												'selected');
										docCode = $(this).closest('tr').find(
												'[name="documentCode"]').val();
										upTime = $(this).closest('tr').find(
										'[name="updateTime"]').val();
										if (!checkMidExist(docCode, mid)) {

											mid.push(docCode);
											updateTime.push(upTime)
										} else {
											mid.splice($.inArray(docCode, mid),
													1);
											updateTime.splice($.inArray(upTime, updateTime),
													1);
										}
									}

								})
					}
				});
		// 反选
		$('#batchCheckTable tbody')
				.on(
						'click',
						'input:checkbox',
						function() {

							if ($("input[name='checkbox']:checked").length == $("input[name='checkbox']").length) {
								$('#selectAll').prop("checked", true);
							} else {
								$('#selectAll').prop("checked", false);
							}

							var docCode = $(this).closest('tr').find(
									'[name="documentCode"]').val();
							var upTime = $(this).closest('tr').find(
							'[name="updateTime"]').val();
							if (!checkMidExist(docCode, mid)) {

								mid.push(docCode);
								updateTime.push(upTime)
							} else {
								mid.splice($.inArray(docCode, mid), 1);
								updateTime.splice($.inArray(upTime, updateTime),
										1);
							}
						})

	}
	;

	// 文件下载
	function downloadFile(docCode, fileNo, fileType) {
		var ajaxOptions = {
			type : "post",
			url : 'doc/download',// 地址
			dataType : 'json',
			data : {
				docCode : docCode,
				fileType : fileType,
				fileNo : fileNo
			},
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if (data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()) {// 存在数据，显示表格
						// alert('下载成功');
					}
				}
			} // 请求成功时处理

		};

		_this.sendAjax(ajaxOptions);
	}
	;

	// 表单验证
	function formValid() {

		$.validator.addMethod('notEqual', function(value, elemetnt, params) {
			return value == $('#directorId').val();
		}, ConstMsg.HTML_MSG_INPUT_NEWLIABLE());

		$('#doc_comment_form').validate({
			errorElement : 'span',
			errorClass : 'help-block',
			focusInvalid : false,
			ignore : "",
			rules : {
				directorId : {
					require : true
				}
			},
			messages : {
				directorId : {
					require : ConstMsg.HTML_MSG_MUSTINPUT_LIABLE()
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

	//
	function checkMidExist(str, array) {
		for ( var i in array) {
			if (array[i] == str) {
				return true;
			}
		}
		return false;
	}

	// 构造方法
	function constructor() {
		_this = this;
		this.InitPage = _initPage;

	}

	return constructor;
})();

// 继承公共AJAX类
Globals.docBatchApproveJS.prototype = new Globals.comAjaxJS();