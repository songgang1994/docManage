Namespace.register("Globals.docFormDefaultJS");
/*
 * <%-- 系统名：NSK-NRDC业务系统 --%> <%-- 作成人：曾雷 --%> <%-- 模块名：文档查询默认条件设置 --%>
 */
Globals.docFormDefaultJS = (function() {
	// ========================================================
	// 私有属性区域
	// User类实例，回调函数中使用，不可直接用this
	var _this = null;
	// 获取路径
	var path = Globals.utilJS.getContextPath();

	var option0 = $("#modal_matching option[index='0']")[0];
	var option1 = $("#modal_matching option[index='1']")[0];
	var option2 = $("#modal_matching option[index='2']")[0];
	var option3 = $("#modal_matching option[index='3']")[0];
	var option4 = $("#modal_matching option[index='4']")[0];
	function _initPage() {

		// 设置拖拽框高度
		$('#sort2').height($('#sort1').height());
		$('#sort3').height($('#sort1').height());

		_InitDocEnterJS();

		_initFormValidation();

		// 拖拽初始化
		sortInit();

		// 详细监听
		$('.a_sort').on(
				'click',
				function() {
					// 空白防止点击
					if ($(this).closest('.sort-item').find(
							'[name="documentItemCode"]').val() == ConstText
							.DOCUMENT_ITEM_F00000()) {
						return;
					}
					modalInit(this);
					modalConfirm(this);
				});

		// 表单提交
		$("#define_form_submit").on("click", function() {
			formSubmit();
		});

		// modal show监听
		$('#detailSetting').on('show.bs.modal', function() {
			//
			$('#modal_matching option').each(function() {
				if ($(this).css('display') == 'block') {
					$(this).prop('selected', true);
					return false;
				}
			})

		})

		// modal hidden监听
		$('#detailSetting').on('hidden.bs.modal', function() {
			// 清空一览项目
			$('#modal_list').prop('checked', false)
			// 清空查询项目
			$('#modal_search').prop('checked', false)
			// 清空check记录
			var validator = $( ".modal-default-form" ).validate();
			validator.resetForm();

		})

		// 返回按钮
		$('#ret').on('click', function() {
			var type = $('#locationType').val();

			if (type == ConstText.RET_TYPE_INDEX()) {
				Globals.utilJS.ReturnHomePage();
			} else {
				location.href = path + '/doc/search'
			}
		})

		// 悬浮tips提示
		// 鼠标滑过事件的效果
		$(".item-type-popup-from-value").bind("mouseover",
				function(event, view) {
					var tipName = $(this).val();
					if (tipName == '') {
						return false;
					}
					handle = setTimeout(function() {
						var top = event.pageY - 10;
						var left = event.pageX + 10;
						$('#tip').css({
							'position' : 'absolute',
							'top' : top + 'px',
							'left' : left + 'px',
							'z-index' : '9999',
						});
						// 内容赋值
						$('#tipName').text(tipName);
						// 显示
						$('#tip').fadeIn('slow');
					}, 200)
				})
		// 鼠标滑出事件的效果
		$(".item-type-popup-from-value").bind("mouseout",
				function(event, view) {
					// 清空计时器
					if ($(this).val() == "") {
						return false;
					}
					clearTimeout(handle);
					$("#tip").hide();
				})
	}

	// 拖拽
	function sortInit() {
		// 可拖拽
		$("#sort2, #sort3").sortable({
			connectWith : ".connectedSortable",
			// cursor: "move",
			opacity : 0.6,
			revert : 300,

		}).disableSelection();

		// 数据项拖拽
		$("#sort1 .sort-item").draggable({
			connectToSortable : "#sort2, #sort3",
			helper : "clone",
			revert : "invalid",

		});

		$("#sort1 .sort-item")
				.on(
						"dragstop",
						function(event, ui) {
							// 获取拖拽div对象
							var div = ui.helper;
							// 判断是否从第一列拖拽
							var span = $(div).find('span').length;
							if (span == 0) {// 第一列
								var span = '';
								if ($(div).find('[name="documentItemCode"]')
										.val() == ConstText
										.DOCUMENT_ITEM_F00000()) {// 空白项目取消详细
									span = "<span style='cursor: pointer;' class='removeLine'>x</span>";
								} else {
									span = "<span style='cursor: pointer;' class='removeLine'>x</span><span style='cursor: pointer;float:right;padding-right:25px'><a href-void class='a_sort'>详细</a></span>";
								}
								$(div).append(span);
								$(div).css("width", $('#td_sort').width());
								$(div).css("height", $('.sort-item').height());
								modalMatchingSetting($(div).find('.a_sort'));
								$($(div).find('.a_sort')).closest('div').find('input[name="matching"]').val($("#modal_matching").val());
								// 解除拖拽附加监听
								offTableEventListener();
								// 添加拖拽附加监听
								onTableEventListener();
							}
						});
	}

	// 解除拖拽附加监听
	function offTableEventListener() {
		// 解除删除监听
		$('.removeLine').off('click');
		// 解除详细点击监听
		$('.a_sort').off('click');
	}

	// 添加拖拽附加监听
	function onTableEventListener() {
		// 删除监听
		$('.removeLine').on('click', function() {
			if ($(this).closest('td').attr('id') != '#sort1') {
				$(this).closest('div').remove();
			}
		});
		// 点击详细监听
		$('.a_sort').on(
				'click',
				function() {
					// 空白防止点击
					if ($(this).closest('.sort-item').find(
							'[name="documentItemCode"]').val() == ConstText
							.DOCUMENT_ITEM_F00000()) {
						return;
					}
					modalInit(this);
					modalConfirm(this);
				});

	}

	function modalConfirm(me) {
		$('#docDefaultConfirm').off('click');
		// modal 确定按钮
		$('#docDefaultConfirm')
				.on(
						'click',
						function(event) {
							event.preventDefault();
							if ($(".modal-default-form").valid() == false) {
								return;
							}

							// 获取并设置对应参数
							var modal_list = '0', modal_search = '0', modal_default = "", modal_docNo = "0";
							if ($(this).closest('.modal-body').find(
									'#modal_list').prop('checked')) {
								modal_list = '1';
							}
							;
							if ($(this).closest('.modal-body').find(
									'#modal_search').prop('checked')) {
								modal_search = '1';
							}
							var modal_matching = $(this).closest('.modal-body')
									.find('#modal_matching').val();

							// 获取数据项类型
							var itemType = $(me).closest('div').find(
									'input[name="documentItemType"]').val();
							// fromTo获取
							var fromTo = $(me).closest('div').find(
									'input[name="isFromToItem"]').val();

							if (fromTo == "1") {
								modal_docNo = '0#1';
							}

							// defaultVal 获取
							modal_default = getDefault(fromTo, itemType);

							// 设置选择参数
							$(me).closest('div').find(
									'input[name="isListItem"]').val(modal_list);
							$(me).closest('div').find(
									'input[name="isSearchItem"]').val(
									modal_search);
							$(me).closest('div').find('input[name="matching"]')
									.val(modal_matching);
							$(me).closest('div').find('input[name="default"]')
									.val(modal_default);
							$(me).closest('div').find(
									'input[name="documentItemNo"]').val(
									modal_docNo);

							// modal隐藏
							$('#detailSetting').modal('hide');
							$(".modal-default-form").resetForm()
						})

		$('#docDefaultClose').off('click');
		$('#docDefaultClose').on('click', function() {
			// modal隐藏
			$('#detailSetting').modal('hide');
			$(".modal-default-form").resetForm()
		})

	}

	// defaultVal获取
	function getDefault(fromTo, itemType) {
		var defaultVal;
		switch (itemType) {
		case ConstText.ITEM_TYPE_TEXT():
			defaultVal = $('.item-type-text-from').val();
			break;
		case ConstText.ITEM_TYPE_AREA():
			defaultVal = $('.item-type-multiline-from').val();
			break;
		case ConstText.ITEM_TYPE_INT():
			defaultVal = $('.item-type-integer-from').val();
			if (fromTo == '1') {
				if (defaultVal != '') {
					defaultVal = defaultVal + '#'
							+ $('.item-type-integer-to').val();
				}

			}
			break;
		case ConstText.ITEM_TYPE_MONEY():
			defaultVal = $('.item-type-currency-from').val();
			if (fromTo == '1') {
				if (defaultVal != '') {
					defaultVal = defaultVal + '#'
							+ $('.item-type-currency-to').val();
				}

			}
			break;
		case ConstText.ITEM_TYPE_FLOAT():
			defaultVal = $('.item-type-decimal-from').val();
			if (fromTo == '1') {
				if (defaultVal != '') {
					defaultVal = defaultVal + '#'
							+ $('.item-type-decimal-to').val();
				}

			}
			break;
		case ConstText.ITEM_TYPE_DATE():
			defaultVal = $('.item-type-date-from').val();
			if (fromTo == '1') {
				if (defaultVal != '') {
					defaultVal = defaultVal + '#'
							+ $('.item-type-date-to').val();
				}
			}
			break;
		case ConstText.ITEM_TYPE_SELECT():
			defaultVal = $('.item-type-selectMul-from').val();
			break;
		case ConstText.ITEM_TYPE_CHECKBOX():
			defaultVal = $('.item-type-selectMul-from').val();
			break;
		case ConstText.ITEM_TYPE_RADIO():
			defaultVal = $('.item-type-select-from').val();
			break;
		case ConstText.ITEM_TYPE_FILE_SINGLE():
			defaultVal = $('.item-type-text-from').val();
			break;
		case ConstText.ITEM_TYPE_FILE_MULT():
			defaultVal = $('.item-type-text-from').val();
			break;
		case ConstText.ITEM_TYPE_POP_USER():
			defaultVal = $('.item-type-popup-from-default').val();
			break;
		case ConstText.ITEM_TYPE_POP_DEPT():
			defaultVal = $('.item-type-popup-from-default').val();
			break;
		case ConstText.ITEM_TYPE_POP_USER_MULT():
			defaultVal = $('.item-type-popup-from-default').val();
			break;
		case ConstText.ITEM_TYPE_POP_DEPT_MULT():
			defaultVal = $('.item-type-popup-from-default').val();
			break;
		}
		return defaultVal;
	}

	function modalInit(me) {

		// 获取数据项名称
		var itemName = $(me).closest('div').find('label').text();
		// 获取数据项类型
		var itemType = $(me).closest('div').find(
				'input[name="documentItemType"]').val();
		// 获取数据项一览
		var itemList = $(me).closest('div').find('input[name="isListItem"]')
				.val();
		// 获取数据项查询
		var itemSearch = $(me).closest('div')
				.find('input[name="isSearchItem"]').val();
		// 获取数据项匹配方式
		var matching = $(me).closest('div').find('input[name="matching"]')
				.val();
		// fromTo获取
		var fromTo = $(me).closest('div').find('input[name="isFromToItem"]')
				.val();

		// 设置数据项名称
		$('#modal_itemName').val(itemName);
		// 设置一览项目
		if ("1" == itemList) {
			$('#modal_list').prop('checked', true)
		}
		// 设置查询项目
		if ("1" == itemSearch) {
			$('#modal_search').prop('checked', true)
		}
		// 设置隐藏默认值

		// 设置匹配方式
		modalMatchingSetting(me);
		// 设置默认值
		modalDefaultSetting(me);
		// modal 显示
		$('#detailSetting').modal('show');

	}

	function modalMatchingSetting(me) {
		// 全部隐藏
		$("#modal_matching option").remove();

		// 获取数据项类型
		var itemType = $(me).closest('div').find(
				'input[name="documentItemType"]').val();
		// fromTo获取
		var fromTo = $(me).closest('div').find('input[name="isFromToItem"]')
				.val();

		if ('1' != fromTo) {// 非fromTo项目
			if (itemType != ConstText.ITEM_TYPE_CHECKBOX()
					&& itemType != ConstText.ITEM_TYPE_POP_USER_MULT()
					&& itemType != ConstText.ITEM_TYPE_POP_DEPT_MULT()) {
				$("#modal_matching").append(option0);

			}
			if (itemType == ConstText.ITEM_TYPE_TEXT()
					|| itemType == ConstText.ITEM_TYPE_AREA()
					|| itemType == ConstText.ITEM_TYPE_FILE_SINGLE()
					|| itemType == ConstText.ITEM_TYPE_FILE_MULT()) {

				$("#modal_matching").append(option1);
			}
			if (itemType == ConstText.ITEM_TYPE_INT()
					|| itemType == ConstText.ITEM_TYPE_MONEY()
					|| itemType == ConstText.ITEM_TYPE_FLOAT()
					|| itemType == ConstText.ITEM_TYPE_DATE()) {
				$("#modal_matching").append(option2);
				$("#modal_matching").append(option3);
			}
			if (itemType == ConstText.ITEM_TYPE_SELECT()
					|| itemType == ConstText.ITEM_TYPE_CHECKBOX()
					|| itemType == ConstText.ITEM_TYPE_POP_USER_MULT()
					|| itemType == ConstText.ITEM_TYPE_POP_DEPT_MULT()) {
				$("#modal_matching").append(option4);
			}
		} else {
			$("#modal_matching").val('');
		}

		// 设置下拉第一项为默认选中项目
		$("#modal_matching option:first").prop("selected", 'selected');
		var that = me;
		if (itemType == ConstText.ITEM_TYPE_SELECT()) {
			$("#modal_matching").off('change');
			$("#modal_matching").on('change', function() {
				modalDefaultSetting(that);
			});
		}
	}

	function modalDefaultSetting(me) {

		// 隐藏
		// 文本
		$('.item-type-text-from').css('display', 'none');
		// 多行文本
		$('.item-type-multiline-from').css('display', 'none');
		// 整型
		$('.item-type-integer-from').css('display', 'none');
		$('.item-type-integer-span').css('display', 'none');
		$('.item-type-integer-to').css('display', 'none');
		// 金额
		$('.item-type-currency-from').css('display', 'none');
		$('.item-type-currency-span').css('display', 'none');
		$('.item-type-currency-to').css('display', 'none');
		// 小数
		$('.item-type-decimal-from').css('display', 'none');
		$('.item-type-decimal-span').css('display', 'none');
		$('.item-type-decimal-to').css('display', 'none');
		// 日期
		$('.item-type-date-from').css('display', 'none');
		$('.item-type-date-span').css('display', 'none');
		$('.item-type-date-to').css('display', 'none');
		// 单选
		$('.item-type-select-from').css('display', 'none');
		// 复选
		$('.item-type-selectMul-from').css('display', 'none');
		// popup
		$('.item-type-popup-from').css('display', 'none');

		// 显示默认值
		showDefault( me);

	}

	// 显示默认值和最大长度
	function showDefault( me) {
		// 获取数据项类型
		var itemType = $(me).closest('div').find(
				'input[name="documentItemType"]').val();
		// fromTo获取
		var fromTo = $(me).closest('div').find('input[name="isFromToItem"]')
				.val();
		// 匹配方式
		var matching = $('#modal_matching option:selected').val();
		// 获取默认值
		var defaultVal = $(me).closest('.sort-item').find('[name="default"]')
				.val();
		// 获取最大长度
		var maxlen = $(me).closest('.sort-item').find('[name="maxLength"]').val();
		if(maxlen ==null || maxlen == '' || maxlen == '0'){
			maxlen = ConstText.DEFAULT_VALUE_LENGTH();
		}
		switch (itemType) {
		case ConstText.ITEM_TYPE_TEXT():
			$('.item-type-text-from').val(defaultVal);
			$('.item-type-text-from').attr('data-rule-maxlength',maxlen);
			$('.item-type-text-from').css('display', 'block');
			break;
		case ConstText.ITEM_TYPE_AREA():
			$('.item-type-multiline-from').val(defaultVal);
			$('.item-type-multiline-from').attr('data-rule-maxlength',maxlen);
			$('.item-type-multiline-from').css('display', 'block');
			break;
		case ConstText.ITEM_TYPE_INT():
			if (fromTo == '1') {
				$('.item-type-integer-from').css('display', 'block');
				$('.item-type-integer-from').attr('data-rule-maxlength',maxlen);
				$('.item-type-integer-from').val(
						defaultVal.substring(0, defaultVal.indexOf('#')));
				$('.item-type-integer-span').css('display', 'block');
				$('.item-type-integer-to').css('display', 'block');
				$('.item-type-integer-to').attr('data-rule-maxlength',maxlen);
				$('.item-type-integer-to').val(
						defaultVal.substring(defaultVal.indexOf('#') + 1,
								defaultVal.length));
			} else {
				$('.item-type-integer-from').css('display', 'block');
				$('.item-type-integer-from').attr('data-rule-maxlength',maxlen);
				$('.item-type-integer-from').val(defaultVal);
			}
			break;
		case ConstText.ITEM_TYPE_MONEY():

			if (fromTo == '1') {
				$('.item-type-currency-from').css('display', 'block');
				$('.item-type-currency-from').attr('data-rule-maxlength',maxlen);
				$('.item-type-currency-from').val(
						defaultVal.substring(0, defaultVal.indexOf('#')));
				$('.item-type-currency-span').css('display', 'block');
				$('.item-type-currency-to').css('display', 'block');
				$('.item-type-currency-to').attr('data-rule-maxlength',maxlen);
				$('.item-type-currency-to').val(
						defaultVal.substring(defaultVal.indexOf('#') + 1,
								defaultVal.length));
			} else {
				$('.item-type-currency-from').css('display', 'block');
				$('.item-type-currency-from').attr('data-rule-maxlength',maxlen);
				$('.item-type-currency-from').val(defaultVal);
			}
			break;
		case ConstText.ITEM_TYPE_FLOAT():
			if (fromTo == '1') {
				$('.item-type-decimal-from').css('display', 'block');
				$('.item-type-decimal-from').attr('data-rule-maxlength',maxlen);
				$('.item-type-decimal-from').val(
						defaultVal.substring(0, defaultVal.indexOf('#')));
				$('.item-type-decimal-span').css('display', 'block');
				$('.item-type-decimal-to').css('display', 'block');
				$('.item-type-decimal-to').attr('data-rule-maxlength',maxlen);
				$('.item-type-decimal-to').val(
						defaultVal.substring(defaultVal.indexOf('#') + 1,
								defaultVal.length));
			} else {
				$('.item-type-decimal-from').css('display', 'block');
				$('.item-type-decimal-from').attr('data-rule-maxlength',maxlen);
				$('.item-type-decimal-from').val(defaultVal);
			}
			break;
		case ConstText.ITEM_TYPE_DATE():
			if (fromTo == '1') {
				$('.item-type-date-from').css('display', 'block');
				$('.item-type-date-from').attr('data-rule-maxlength',maxlen);
				$('.item-type-date-from').val(
						defaultVal.substring(0, defaultVal.indexOf('#')));
				$('.item-type-date-span').css('display', 'block');
				$('.item-type-date-to').css('display', 'block');
				$('.item-type-date-to').attr('data-rule-maxlength',maxlen);
				$('.item-type-date-to').val(
						defaultVal.substring(defaultVal.indexOf('#') + 1,
								defaultVal.length));
			} else {
				$('.item-type-date-from').css('display', 'block');
				$('.item-type-date-from').attr('data-rule-maxlength',maxlen);
				$('.item-type-date-from').val(defaultVal);
			}
			break;
		case ConstText.ITEM_TYPE_SELECT():

			if (matching == ConstText.MATCHING_TYPE_EQ()) {
				$('.item-type-select-from').css('display', 'block');

				var docCode = $(me).closest('div').find(
						'input[name="documentItemCode"]').val();
				if (docCode == ConstText.DOCUMENT_ITEM_F00008()) {// 区分mst固定项
					selectExistFixedItem($(me), 'item-type-select-from',
							defaultVal);
				} else {
					selectNotExistFixedItem($(me), 'item-type-select-from',
							defaultVal);
				}
			} else {
				$('.item-type-selectMul-from').css('display', 'block');
				var docCode = $(me).closest('div').find(
						'input[name="documentItemCode"]').val();
				if (docCode == ConstText.DOCUMENT_ITEM_F00008()) {// 区分mst固定项
					selectExistFixedItem($(me), 'item-type-selectMul-from',
							defaultVal);
				} else {
					selectNotExistFixedItem($(me), 'item-type-selectMul-from',
							defaultVal);
				}
			}
			break;
		case ConstText.ITEM_TYPE_CHECKBOX():
			$('.item-type-selectMul-from').css('display', 'block');
			selectNotExistFixedItem($(me), 'item-type-selectMul-from',
					defaultVal);

			break;
		case ConstText.ITEM_TYPE_RADIO():
			$('.item-type-select-from').css('display', 'block');
			var docCode = $(me).closest('div').find(
					'input[name="documentItemCode"]').val();
			if (docCode == ConstText.DOCUMENT_ITEM_F00008()) {// 区分mst固定项
				selectExistFixedItem($(me), 'item-type-select-from', defaultVal);
			} else {
				selectNotExistFixedItem($(me), 'item-type-select-from',
						defaultVal);
			}

			break;
		case ConstText.ITEM_TYPE_FILE_SINGLE():
			$('.item-type-text-from').css('display', 'block');
			$('.item-type-text-from').val(defaultVal);
			break;
		case ConstText.ITEM_TYPE_FILE_MULT():
			$('.item-type-text-from').css('display', 'block');
			$('.item-type-text-from').val(defaultVal);
			break;
		case ConstText.ITEM_TYPE_POP_USER():
			$(".item-type-popup-from").off('click');
			// 弹出画面 - 人员检索
			$(".item-type-popup-from").click(
					function() {
						var staff = new Globals.staffSearchPopupJS();
						staff.ShowModal('radio',
								function(data) {
									if (data.length > ConstText
											.HTML_MSG_NUM_ZERO()) {
										var userIds = data.map(function(x) {
											return x[0].userId
										});
										var userNames = data.map(function(x) {
											return x[0].userName
										});

										$('.item-type-popup-from-value').val(
												userNames);
										$('.item-type-popup-from-default').val(
												userIds + '#' + userNames);
									}
								});
					});
			$('.item-type-popup-from').css('display', 'block');
			$('.item-type-popup-from-default').attr('data-rule-maxlength',maxlen);
			$('.item-type-popup-from-default').val(defaultVal);
			$('.item-type-popup-from-value').val(
					defaultVal.split(',').map(function(x) {
						return x.substring(x.indexOf('#') + 1, x.length)
					}).join(','));
			break;
		case ConstText.ITEM_TYPE_POP_DEPT():
			$(".item-type-popup-from").off('click');
			// 弹出画面 - 部门检索
			$(".item-type-popup-from").click(
					function() {
						var depart = new Globals.departSearchPopupJS();
						depart.ShowModal('radio', function(data) {
							if (data.length > ConstText.HTML_MSG_NUM_ZERO()) {
								var departIds = data.map(function(x) {
									return x[0].deptId
								});
								var departNames = data.map(function(x) {
									return x[0].deptName
								});

								$('.item-type-popup-from-value').val(
										departNames);
								$('.item-type-popup-from-default').val(
										departIds + '#' + departNames);
							}
						});
					});

			$('.item-type-popup-from').css('display', 'block');
			$('.item-type-popup-from-default').attr('data-rule-maxlength',maxlen);
			$('.item-type-popup-from-default').val(defaultVal);
			$('.item-type-popup-from-value').val(
					defaultVal.split(',').map(function(x) {
						return x.substring(x.indexOf('#') + 1, x.length)
					}).join(','));
			break;
		case ConstText.ITEM_TYPE_POP_USER_MULT():
			$(".item-type-popup-from").off('click');
			// 弹出画面 - 人员检索
			$(".item-type-popup-from").click(function() {
				var staff = new Globals.staffSearchPopupJS();
				staff.ShowModal('checkbox', function(data) {
					if (data.length > ConstText.HTML_MSG_NUM_ZERO()) {
						var userNames = data.map(function(x) {
							return x[0].userName
						}).join(',');
						var userIds = data.map(function(x) {
							return x[0].userId + '#' + x[0].userName
						}).join(',');
						$('.item-type-popup-from-value').val(userNames);
						$('.item-type-popup-from-default').val(userIds);
					}
				});
			});

			$('.item-type-popup-from').css('display', 'block');
			$('.item-type-popup-from-default').attr('data-rule-maxlength',maxlen);
			$('.item-type-popup-from-default').val(defaultVal);
			$('.item-type-popup-from-value').val(
					defaultVal.split(',').map(function(x) {
						return x.substring(x.indexOf('#') + 1, x.length)
					}).join(','));
			break;
		case ConstText.ITEM_TYPE_POP_DEPT_MULT():
			$(".item-type-popup-from").off('click');
			// 弹出画面 - 部门检索
			$(".item-type-popup-from").click(function() {
				var depart = new Globals.departSearchPopupJS();
				depart.ShowModal('checkbox', function(data) {
					if (data.length > 0) {
						var departNames = data.map(function(x) {
							return x[0].deptName
						}).join(',');
						var departIds = data.map(function(x) {
							return x[0].deptId + '#' + x[0].deptName
						}).join(',');
						$('.item-type-popup-from-value').val(departNames);
						$('.item-type-popup-from-default').val(departIds);
					}
				});
			});

			$('.item-type-popup-from').css('display', 'block');
			$('.item-type-popup-from-default').attr('data-rule-maxlength',maxlen);
			$('.item-type-popup-from-value').val(
					defaultVal.split(',').map(function(x) {
						return x.substring(x.indexOf('#') + 1, x.length)
					}).join(','));
			break;
		}
	}

	// 下拉框（除固定项目）数据
	function selectNotExistFixedItem(me, selectClass, defaultVal) {
		// ajax请求
		var ajaxOptions = {
			type : "post",
			url : 'docmain/getdocsource',// 地址
			async : false,
			data : {
				docCode : $(me).closest('div').find(
						'input[name="documentItemCode"]').val(),
				docNo : $(me).closest('div').find(
						'input[name="documentItemNo"]').val()
			},
			success : function(data) {
				$('.' + selectClass + ' option').remove();
				var options = "<option value=''> </option>";
				for (var i = 0; i < data.listData.length; i++) {
					options += '<option value="' + data.listData[i].itemValue
							+ '">' + data.listData[i].itemName + '</option>';
				}
				$('.' + selectClass).append(options);
				// 获取默认值，设置默认选中
				$('.' + selectClass + ' option').each(function() {
					if (defaultVal.indexOf($(this).val()) != -1) {
						$(this).attr("selected", true);
					}
				})
			} // 请求成功时处理
		};

		_this.sendAjax(ajaxOptions);
	}
	// 下拉框（固定项目）数据
	function selectExistFixedItem(me, selectClass, defaultVal) {
		// ajax请求
		var ajaxOptions = {
			type : "post",
			url : 'docmain/getdocstatus',// 地址
			async : false,
			success : function(data) {
				$('.' + selectClass + ' option').remove();
				var options = "<option value=''> </option>";
				for (var i = 0; i < data.listData.length; i++) {
					options += '<option value="' + data.listData[i].value
							+ '">' + data.listData[i].dispName + '</option>';
				}
				$('.' + selectClass).append(options);
				// 获取默认值，设置默认选中
				$('.' + selectClass + ' option').each(function() {
					if (defaultVal.indexOf($(this).val()) != -1) {
						$(this).attr("selected", true);
					}
				})
			} // 请求成功时处理
		};

		_this.sendAjax(ajaxOptions);
	}
	function formSubmit() {
		// 获取页面参数

		var docItemCode = [], docItemNo = [], isListItem = [], isFromToItem = [], isSearchItem = [], matching = [], defaultVal = [], row = [], colu = [], docType = [];
		$("#sort2").find(".sort-item")
				.each(
						function(index) {
							docItemCode.push($(this).find(
									"input[name='documentItemCode']").val());
							if ($(this).find("input[name='isFromToItem']")
									.val() == '1') {
								docItemNo.push('0#1');
							} else {
								docItemNo.push('0');
							}
							docType.push($(this).find(
									"input[name='documentItemType']").val());
							isListItem.push($(this).find(
									"input[name='isListItem']").val());
							isSearchItem.push($(this).find(
									"input[name='isSearchItem']").val());
							isFromToItem.push($(this).find(
									"input[name='isFromToItem']").val());
							matching.push($(this)
									.find("input[name='matching']").val());
							defaultVal.push($(this).find(
									"input[name='default']").val());
							row.push(index + 1);
							colu.push('1');
						});
		$("#sort3").find(".sort-item")
				.each(
						function(index) {
							docItemCode.push($(this).find(
									"input[name='documentItemCode']").val());
							if ($(this).find("input[name='isFromToItem']")
									.val() == '1') {
								docItemNo.push('0#1');
							} else {
								docItemNo.push('0');
							}
							docType.push($(this).find(
									"input[name='documentItemType']").val());
							isListItem.push($(this).find(
									"input[name='isListItem']").val());
							isSearchItem.push($(this).find(
									"input[name='isSearchItem']").val());
							isFromToItem.push($(this).find(
									"input[name='isFromToItem']").val());
							matching.push($(this)
									.find("input[name='matching']").val());
							defaultVal.push($(this).find(
									"input[name='default']").val());
							row.push(index + 1);
							colu.push('2');
						});

		if (docItemCode.length == 0) {
			Globals.msgboxJS.ToastrWarning(ConstMsg
					.HTML_MSG_SEARCH_ITEM_DONOT_STTING());
			return;
		}

		// 数据处理
		isListItem = isListItem.map(function(t) {
			return t == '' ? '0' : t;
		});
		isSearchItem = isSearchItem.map(function(t) {
			return t == '' ? '0' : t;
		});

		// 设置ajax数据
		data = {
			documentItemCode : docItemCode,
			documentItemNo : docItemNo,
			documentItemType : docType,
			layoutRow : row,
			layoutCol : colu,
			isFromToItem : isFromToItem,
			isListItem : isListItem,
			isSearchItem : isSearchItem,
			matching : matching,
			defaultVal : defaultVal,
			locationType:$('#locationType').val()
		};

		// ajax请求
		var ajaxOptions = {
			type : "post",
			url : 'docmain/defaultsubmit',// 地址
			contentType : "application/json",
			data : JSON.stringify(data),
			success : function(data) {
				if (data.rtnCode == ConstCode.RTN_CODE_SUCCESS()) {
					if (data.data.bizCode == ConstCode.BIZ_CODE_SUCCESS()) {
						Globals.msgboxJS.ToastrSuccess(ConstMsg
								.HTML_MSG_DOC_SEARCH_DEFAULT_SETTING_SUCCESS());

						setTimeout(function(){//两秒后跳转
							location.reload();
			             },ConstText.HTML_MSG_NUM_MS());
					}
				} else {
					Globals.msgboxJS.ToastrError(ConstMsg.RTN_CODE_UNKNOWN());
				}

			} // 请求成功时处理
		};

		_this.sendAjax(ajaxOptions);

	}
	;

	function _InitDocEnterJS() {
		// 时间选择器
		$.datepicker.regional['zh-CN'] = {
			changeMonth: true,
			changeYear: true,
			clearText: '清除',
			clearStatus: '清除已选日期',
			closeText: '关闭',
			closeStatus: '不改变当前选择',
			prevText: '<上月',
			prevStatus: '显示上月',
			prevBigText: '<<',
			prevBigStatus: '显示上一年',
			nextText: '下月>',
			nextStatus: '显示下月',
			nextBigText: '>>',
			nextBigStatus: '显示下一年',
			currentText: '今天',
			currentStatus: '显示本月',
			monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
			monthNamesShort: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
			monthStatus: '选择月份',
			yearStatus: '选择年份',
			weekHeader: '周',
			weekStatus: '年内周次',
			dayNames: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
			dayNamesShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
			dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
			dayStatus: '设置 DD 为一周起始',
			dateStatus: '选择 m月 d日, DD',
			dateFormat: 'yy-mm-dd',
			firstDay: 1,
			initStatus: '请选择日期',
			isRTL: false
		};
		$.datepicker.setDefaults($.datepicker.regional['zh-CN']);
		// 日期
		$(".item-type-date-from, .item-type-date-to")
				.datepicker({
					changeMonth : true,
					dateFormat : "yy/mm/dd",
				});

	}

	//  表单验证
	function _initFormValidation() {

		var rules = {};
		var messages = {};

		$.validator.addMethod('integer', function(value, elemetnt, params) {
			if ($(elemetnt).css("display") == 'block') {
				if (value != '') {
					return /^\+?[1-9][0-9]*$/.test(value);
				} else {
					return true;
				}
			} else {
				return true;
			}
		}, "请输入整数");
		$.validator
				.addMethod(
						'currency',
						function(value, elemetnt, params) {
							if ($(elemetnt).css("display") == 'block') {
								if (value != '') {
									return /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/
											.test(value);
								} else {
									return true;
								}
							} else {
								return true;
							}

						}, "请输入金额");
		$.validator.addMethod('decimal', function(value, elemetnt, params) {
			if ($(elemetnt).css("display") == 'block') {
				if (value != '') {
					// 只能输入数字、逗号分隔符

					return /^[\d,.]+$/.test(value);
				} else {
					// 格式验证
					return true;
				}
			} else {
				return true;
			}
		}, "请输入小数");
		$.validator.addMethod('fromTo',
				function(value, elemetnt, params) {
					if ($(elemetnt).css("display") == 'block') {
						if (value != '') {
							if ($(elemetnt).prop('class').indexOf(
									'item-type-integer') != -1) {// 整数
								var to = $(elemetnt).prev().prev().val();
								if (to != '') {
									return parseInt(value) >= parseInt(to);
								}

							} else if ($(elemetnt).prop('class').indexOf(
									'item-type-currency') != -1) {// 金额
								var to = $(elemetnt).prev().prev().val();
								if (to != '') {
									return parseInt(value) >= parseInt(to);
								}

							} else if ($(elemetnt).prop('class').indexOf(
									'item-type-decimal') != -1) {// 小数
								var to = $(elemetnt).prev().prev().val();
								if (to != '') {
									return parseInt(value) >= parseInt(to);
								}

							} else if ($(elemetnt).prop('class').indexOf(
									'item-type-date') != -1) {// 日期
								var to = $(elemetnt).prev().prev().val();
								if (to != '') {
									return parseInt(value) >= parseInt(to);
								}

							}
						} else {
							return true;
						}
					} else {
						return true;
					}
				}, "from项目不得大于to项目");

		$(".modal-default-form").validate({
			errorElement : 'span', // default input error message container
			errorClass : 'help-block col-md-5 no-padding', // default input error message
												// class
			focusInvalid : false, // do not focus the last invalid input
			ignore : "",
			onfocusout : false,
			onclick : false,
			focusCleanup : true,
			onkeyup : false,
			invalidHandler : function(event, validator) {
				// display error alert on form submit
			},
			highlight : function(element) { // hightlight error inputs
				$(element).parent().next().addClass('has-error');

			},
			errorPlacement : function(error, element) {
				error.appendTo('#modalDefault');
			},
			success : function(label) {
				label.parent().next().removeClass(' has-error ');
				label.remove();
			}
		});

	}

	// 构造方法
	function constructor() {
		_this = this;
		this.InitPage = _initPage;
	}

	return constructor;
})();

// 继承公共AJAX类
Globals.docFormDefaultJS.prototype = new Globals.comAjaxJS();