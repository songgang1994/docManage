/**
 * -------------------------------------------------------
 * 
 * @模块描述:jquery扩展
 * @作者:顾伟
 * @修改履历:新作成 2016/8/12 -------------------------------------------------------
 */
Namespace.register("Globals.jqueryExtJS");

Globals.jqueryExtJS = (function() {
	$.extend($.fn.combobox.defaults, {
		//重写filter方法，实现拼音过滤功能
		filter : function(q, row) {
			if (null == q || "" == q)
				return false;

			var opts = $(this).combobox("options");
			var optionText = row[opts.textField];
			var inputPinyin = pinyin.getFullChars(q).toLowerCase();
			var fullPinyin = pinyin.getFullChars(optionText).toLowerCase();
			var camelPinyin = pinyin.getCamelChars(optionText).toLowerCase();
			var rtn = false;

			if (fullPinyin.indexOf(inputPinyin) > -1
					|| camelPinyin.indexOf(inputPinyin) > -1) {
				rtn = true;
			}

			return rtn;
			//return row[opts.textField].indexOf(q) > -1;// 将从头位置匹配改为任意匹配
		}
	});

})();