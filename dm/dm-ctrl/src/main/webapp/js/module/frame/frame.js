/**
 * -------------------------------------------------------
 * @模块描述:框架公共函数类
 * @作者:顾伟
 * @修改履历:新作成 2016/6/14
 * -------------------------------------------------------
 */ 
Namespace.register("Globals.frameJS");

Globals.frameJS = {
		AddTab:function(title, url){
			if ($('#mainPanel').tabs('exists', title)) {
				$('#mainPanel').tabs('select', title);
			} else {
				var content = '<iframe frameborder="0" src="' + url
						+ '" style="width:100%;height:100%;"></iframe>';
				$('#mainPanel').tabs('add', {
					title : title,
					content : content,
					closable : true,
					cache:false
				});
			}
		}
};
