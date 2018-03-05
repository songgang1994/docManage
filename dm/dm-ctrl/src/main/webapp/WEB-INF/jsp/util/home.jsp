<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
<%
	if(session.getAttribute("documentCode")!=null&&session.getAttribute("mode")!=null){
		String docCode = session.getAttribute("documentCode").toString();
		String mode = session.getAttribute("mode").toString();
		if(docCode.equals("")){
			pageContext.setAttribute("src", "top/init");
		}else{
			pageContext.setAttribute("src", "doc/enter?mode="+mode+"&documentCode="+docCode);
		}
	} else{
		pageContext.setAttribute("src", "top/init");
	}
%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		<%@include file="header.jsp"%>
		<%@include file="menu.jsp"%>

		<div class="content-wrapper main-header">

			<iframe
				style="border: 0px; min-height: inherit; width: 100%; height: 100%"
				name="mainFrame" id="mainFrame" src=${src }></iframe>
		</div>
		<%@include file="footer.jsp"%>
	</div>
	<script type="text/javascript">
		function setContent(url) {
			$("#mainFrame").attr("src", url);
		}
	</script>
	<%@include file="../include/inc_footer.jsp"%>
