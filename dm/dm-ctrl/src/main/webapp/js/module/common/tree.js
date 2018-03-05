/**
 * 系统名：NSK-NRDC业务系统
 * 作成人：李辉
 * 模块名：树形结构操作类
 */
Namespace.register("Globals.treeJS");

Globals.treeJS = {
	// ========================================================
	// 静态公共方法区域

    //展开所有节点
    ExpandAll : function(treeId) {
    	$('#'+treeId).tree('expandAll');
	},

	//关闭所有节点
	CollapseAll : function(treeId) {
		$('#'+treeId).tree('collapseAll');
	},

	//获取当前选择节点
	GetSelectedNode : function(treeId) {
		return $('#'+treeId).tree('getSelected');
	},

	//关闭当前选择节点
	CollapseSelectedNode : function(treeId) {
		var node = this.GetSelectedNode(treeId);
		$('#'+treeId).tree('collapse', node.target);
	},

	//展开当前选择节点
	ExpandSelectedNode : function(treeId) {
		var node = this.GetSelectedNode(treeId);
		$('#'+treeId).tree('expand', node.target);
	}

};