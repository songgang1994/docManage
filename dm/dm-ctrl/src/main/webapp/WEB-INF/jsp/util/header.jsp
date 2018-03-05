<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<header class="main-header">
    <a class="logo">
		<span class="logo-mini"><b>NRDC</b></span>
		<img src="images/NSK_logo.png"/>
    </a>

    <nav class="navbar navbar-static-top">
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
			<span class="sr-only">Toggle navigation</span>
		</a>
		<span style="font-size:30px;color:white;margin-top:5px;float:left;">恩斯克投资有限公司</span>
		<span class="hello">您好:<span>${user.userName}</span><input id="headerExit" class="btn btn-danger logout" type="button" value="退出" /></span>
    </nav>
</header>
