<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>

<%-- 系统名：NSK-NRDC业务系统 --%>
<%-- 作成人：邵林军 --%>
<%-- 模块名：部署年度文档统计 --%>

<%@include file="../include/inc_header.jsp"%>

</head>
<body>
	<fieldset>
		<legend>部署年度文档统计</legend>
		<!-- 条件栏 -->
		<form id="departAnnualStatisticsForm" >
				<!-- 查询条件  第一行 -->
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
					<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
						<span>FY年月</span>
					</div>
					<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12">
						<!-- 年月选择 -->
						<select id="fyYear" name="fyYear" style="width: 50%">
							<option></option>
						</select>
					</div>
				</div>

				<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
					<div  id="checkboxDiv"  class="row col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding">
						<!-- 动态生成文档类型 -->
						<span id="checkboxSpan" ></span>
					</div>
				</div>

				<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
							<button id="searchDepartAnnualStatistics"  class="btn btn-primary">查询</button>
						</div>
					</div>
				</div>

			</div>

		</form>

		<!-- 条件栏结束 -->

		<!-- 表格  start -->
		<div  class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12" id="queryTableDiv">

				<div id="queryTable" style="display: none; width: 98%;" class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				</div>
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noResult" style="display: none" class="">
					无查询结果！
				</div>
			</div>
		<!-- 表格  end -->

		<!-- 底部按钮 start -->
		<div class="row  col-sm-12 col-md-12 col-lg-12 col-xs-12">
			<div class="buttongroup">
				<button id="exportDepartAnnualStatistics" name="exportDepartAnnualStatistics"class="btn btn-primary" style="display:none;">
	   				导出
	    		</button>
	    		<button id="departAnnualStatisticsBack" name="cancel"  class="btn ">
	    			返回
	    		</button>
			</div>
		</div>
		<!-- 底部按钮 end -->

	</fieldset>

	<!--动态包含 pop画面-->



	<!-- 本页面对应的js -->
	<script src="js/module/report/departAnnualStatistics.js"></script>
	<script >
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var departAnnualStatisticsJS = new Globals.departAnnualStatisticsJS();
			departAnnualStatisticsJS.InitDepartAnnualStatistics();
		});
	</script>
<%@include file="../include/inc_footer.jsp"%>