<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<!-- head : start -->
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>${system.name}</title>
<base href="<%=basePath %>" />
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<link rel="stylesheet" href="js/thirdParty/bootstrap/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet" href="js/thirdParty/FontAwesome/css/font-awesome.min.css">
<!-- Theme style -->
<link rel="stylesheet" href="js/thirdParty/adminlte/css/AdminLTE.min.css">
<!-- iCheck -->
<link rel="stylesheet" href="js/thirdParty/iCheck/all.css">
</head>
<!-- head : end -->
<!-- body : start -->
<body class="hold-transition login-page layout-top-nav">
    <div class="login-box">
        <div class="login-logo control-label">
            <b>文档管理办公系统</b>
        </div>
        <!-- /.login-logo -->
        <div class="login-box-body">
            <p class="login-box-msg">
                <span class="glyphicon glyphicon-user">系统登录</span>
            </p>
			<!-- form : start -->
            <form id="login-form" action="login/login" method="post"  target="_top">
            	<div id="errMsg" style="color:#dd4b39">${message}</div>
                <div class="form-group has-feedback">
                    <input type="text" class="form-control"
                        placeholder="用户名"
                        name="userCode" value="${logonDto.userCode}"
                        />
                    <span
                        class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control"
                        placeholder="密码" value="${logonDto.password}"
                        name="password"/>
  					<span
                        class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="row">
                    <!-- /.col -->
                    <div class="col-xs-12">
                        <input id="loginSubmit" type="submit" id='loginBut'
                            class="btn btn-primary btn-block btn-flat" value="登录"/>
                    </div>
                    <!-- /.col -->
                </div>
            </form>
            <!-- form : end -->
        </div>
        <!-- /.col -->
    </div>

    <!-- jQuery 2.2.0 -->
    <script src="js/thirdParty/jquery/jQuery-2.2.0.min.js"></script>
    <!-- iCheck -->
    <script src="js/thirdParty/iCheck/icheck.min.js"></script>
    <script src="js/thirdParty/jquery/jquery.validate.min.js"></script>
    <!-- module -->
    <script src="js/namespace.js" type="text/javascript"></script>
    <script src="js/module/common/ajax.js" type="text/javascript"></script>
    <!-- iCheck Init -->
    <script type="text/javascript">
	// 初始化处理
	$(document).ready(function() {
		// 设置默认焦点
		$('#loginBut').focus();
	});

    $(function() {
        $('.icheckbox').iCheck({
            checkboxClass : 'icheckbox_minimal-blue',
            radioClass : 'iradio_minimal-blue'
         });
    });
    </script>
    <script src="js/module/util/login.js"></script>
	<script>
		jQuery(document).ready(function() {
			var loginJS = new Globals.loginJS();
			loginJS.InitPage();
		});
	</script>
</body>
<!-- body : end -->
</html>

