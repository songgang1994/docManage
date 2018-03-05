/**
 * <%-- 系统名：NSK-NRDC业务系统 --%> <%-- 作成人：曾雷 --%> <%-- 模块名：文档责任人修改 --%>
 */
Namespace.register("Globals.personResponsibleEditJS");

Globals.personResponsibleEditJS = (function() {

	var _this = null;

	// 初始化人员检索pop
	function _InitPage() {
		var person = new Globals.staffSearchPopupJS();

		// 审核人查询按钮点击
		$('#queryDocPerson').on('click', function() {
			var depts = [];
			$('input[name=deptIds]').each(function() {
				depts.push($(this).val());
			});
			var data = {
				deptIds : depts,
				leaderFlg : true
			};
			personEdit(data, person, "docPersonHidden", "docPerson");
		});

		// 指定审核人查询按钮点击
		$('#queryDocAppointPerson').on('click',function() {
			var depts = [];
			$('input[name=deptIds]').each(function() {
				depts.push($(this).val());
			});
			var data = {
				deptIds : depts,
				leaderFlg : true
			};
			personEdit(data, person, "docAppointPersonHidden","docAppointPerson");
		});

		// 查询按钮点击事件
		$('#queryDoc').on('click', function() {
			// 获取参数
			var personId = $('#docPersonHidden').val();
			var docType = $('#docType').val();
			var docStatus = $('#docStatus').val();

			//参数验证
			if(personId == ''){
				alert(ConstMsg.HTML_TXT_IS_INPUT_RESPONSE());
				return;
			}
			var ajaxOptions = {
				type : "post",
				url : 'doc/query',// 地址
				dataType : 'json',
				data : {
					personId : personId,
					docType : docType,
					docStatus : docStatus
				},
				success : function(data) {
					// alert(111)
					$('#queryTableBody').empty();
					_cb_query_success(data.listData);
				} // 请求成功时处理
			};

			_this.sendAjax(ajaxOptions);
		});

		// 提交按钮点击事件
		$('#submit').on('click', function() {
			submit(this);
		});
	}

	// 确定提交
	function submit(me) {
		// 获取参数
		var docCode = [];
		$('input[name=checkbox]:checked').each(function() {
			docCode.push($(this).val());
		});

		var personId = $('#docPersonHidden').val();
		var appointPersonId = $('#docAppointPersonHidden').val();

		//参数验证
		if(docCode.length == 0){
			alert(ConstMsg.HTML_MSG_CHOOSE_ITEM());
			return;
		}

		if(appointPersonId == ""){
			alert(ConstMsg.HTML_MSG_MUSTINPUT_LIABLE());
			return;
		}

		if(personId == appointPersonId){
			alert(ConstMsg.HTML_MSG_INPUT_NEWLIABLE());
			return;
		}

		//ajax提交
		var ajaxOptions = {
			type : "post",
			url : 'doc/reseditsubmit',// 地址
			traditional:true,
			data : {
				docCode:docCode,
				appointPersonId:appointPersonId
			},
			success : function(data) {
				alert(ConstMsg.HTML_TXT_IS_RESPONSCHANGE_SUCCESS())
			} // 请求成功时处理
		};

		_this.sendAjax(ajaxOptions);

	}

	// 查询结果回调
	function _cb_query_success(data) {
		// 查询信息>0
		if (data.length > 0) {
			var tr = "", approve = "0", approvalStatus = "";
			for (var i = 0; i < data.length; i++) {
				if (data[i].approvalStatus == "1") {
					approvalStatus = ConstMsg.BIZ_CODE_SUCCESSING_DOCUMENT()
				} else if (data[i].approvalStatus == "2") {
					approvalStatus = ConstMsg.BIZ_CODE_EXAMINEING_DOCUMENT()
				} else {
					approvalStatus = ConstMsg.BIZ_CODE_SUCCESS_DOCUMENT()
				}
				// 显示一览数据
				tr += '<tr><td class="text-center"><input type="checkbox" name="checkbox" value="'
						+ data[i].documentCode
						+ '"/></td><td class="text-center"><form action="doc/enter" method="post"><a class="enterLocation" href-void>'
						+ data[i].documentCode
						+ '</a><input type="hidden" name="documentCode" value="'
						+ data[i].documentCode
						+ '" /><input type="hidden" name="mode" value="'
						+ approve
						+ '" /></form></td><td class="text-center">'
						+ data[i].documentType
						+ '</td><td class="text-center">'
						+ data[i].fileContent
						+ '</td><td class="text-center"><a href-void class="fileDown">'
						+ data[i].fileName
						+ '</a></td><td class="text-center"><a class="childFile" href-void >'
						+ data[i].childFileName
						+ '</a></td><td class="text-center">'
						+ approvalStatus
						+ '</td></tr>';
			}
			$('#queryTableBody').append(tr);

			// 解除监听
			offTableEventListen();
			// 添加监听
			onTableEventListen(data);

			// 显示指派责任人和确定按钮
			$('#docAppointPersonRow').css('display', 'block');
			$('#btnSure').css('display', 'block');
		} else {
			var tr = '<tr class="text-center"><td colspan="9"><span>未找到符合条件的数据。</span></td></tr>';
			$('#queryTableBody').append(tr);
		}
	}

	function offTableEventListen(data) {
		$('.enterLocation').off('click');
		$('.fileDown').off('click');
		$('.childFile').off('click');
	}

	// 事件监听
	function onTableEventListen(data) {
		var trs = $("#docPersonEditTable tr");

		// 文档编号跳转
		$('.enterLocation').on('click', function() {
			$(this).closest('form')[0].submit();
		});

		// 主文件下载
		$('.fileDown').on(
				'click',
				function() {
					// 获取文件信息
					var docCode = $(this).closest('tr').find(
							'input[name="documentCode"]').val();

					downloadFile(docCode, null, 1);
				});

		// 子文件下载
		$('.childFile').on('click', function() {
			// 获取点击的子文件行号
			var index = trs.index($(this).closest("tr")) - 1;

			// 获取子文件数据
			drawChildFile(data[index].docChildList);
		});

		// checkbox全选
		selectAll();

	}

	// 子文件modal
	function drawChildFile(data) {
		$('.childFileDown').off('click');
		$('#childFilesModalBody').empty();
		var ul = "<ul>";
		for (var i = 0; i < data.length; i++) {
			ul += '<li><a class="childFileDown" href-void >' + data[i].fileName
					+ '<input type="hidden" name="documentCode" value="'
					+ data[i].documentCode
					+ '"><input type="hidden" name="fileNo" value="'
					+ data[i].fileNo + '"></li>';
		}
		ul += "</ul>";
		$('#childFilesModalBody').append(ul);
		// 显示modal
		$("#childFiles").modal('show');
		// 子文件下载监听
		$('.childFileDown').on(
				'click',
				function() {
					var docCode = $(this).parent('li').find(
							'input[name="documentCode"]').val();
					var fileNo = $(this).parent('li').find(
							'input[name="fileNo"]').val();

					// 下载请求
					downloadFile(docCode, fileNo, 2);
				});
	}
	;

	// 全选
	function selectAll() {
		$('#selectAll').change(function(event) {
			var input = $('input:checkbox[name="checkbox"]');
			if (input.prop("checked")) {
				input.prop("checked", false);
			} else {
				input.prop("checked", true);
			}
		});
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
				alert(ConstMsg.HTML_TXT_IS_DOWNLOAD_SUCCESS());
			} // 请求成功时处理
		};

		_this.sendAjax(ajaxOptions);
	}
	;

	// modal显示
	function personEdit(data, person, hiddenId, id) {
		person.ShowModal('radio', data, function(data) {
			$('#' + hiddenId).val(data[0].userId);
			$('#' + id).val(data[0].userName);
		});

	}

	// 构造方法
	function constructor() {
		_this = this;
		this.InitPage = _InitPage;

	}

	return constructor;
})();

// 继承公共AJAX类
Globals.personResponsibleEditJS.prototype = new Globals.comAjaxJS();