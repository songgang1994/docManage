<%@page import="com.dm.dto.UserDeptRoleDto"%>
<%@page import="com.dm.dto.RoleDto"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
<!-- 调用部门的popup框 -->
<jsp:include page="../com/DepartSearchPopup.jsp"/>
<!-- multiple-select -->
<link rel="stylesheet" href="js/thirdParty/multiple-select/multiple-select.css" />
<script src="js/thirdParty/multiple-select/multiple-select.js" type="text/javascript" ></script>
<!--
	NSK-NRDC业务系统
	作成人：张丽
	人员维护
 -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

</head>
<fieldset>
<%String flag = (String)request.getAttribute("flag");%>
<%if(flag.equals("2")){%>
	 <legend>用户信息新增</legend>
<%}else{%>
	<legend>用户信息修改</legend>
<%}%>
   <!--员工维护使用参数隐藏 -->
      <input type="hidden"  id="userName1" value="${userName1}">
      <input type="hidden"  id="userId1" value="${userId1}">
      <input type="hidden"  id="email1" value="${email1}">
      <input type="hidden"  id="roleId1" value="${roleId1}">
      <input type="hidden"  id="roleName1" value="${roleName1}">
      <!-- 员工维护使用参数隐藏结束 -->
	<div class="row " name="detail">
    <form id="form-user"  >
		<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<!-- 第一行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>用户ID</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<input type="text" id="userId"  name="userId" value="${userdto.userId}" style="width:100%" disabled="true">
						</div>
					</div>
				</div>
				<!-- 第二行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>账号</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<input type="text" id="userCode" maxLength="50" name="userCode"  value="${userdto.userCode}" style="width:100%">
						</div>
					</div>
				</div>
				<!-- 第三行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>姓名</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<input type="text" id="userName" maxLength="50" name="userName" value="${userdto.userName}" style="width:100%">
						</div>
					</div>
				</div>
				<!-- 第四行 -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>邮箱地址</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<input id="email"  name="email" type="text" name="" value="${userdto.email}"  maxLength="50" style="width:100%"/>
						</div>
					</div>
				</div>
				<!-- 第五行 -->
                 <!-- 获取所有的role信息 -->
 				<%List<RoleDto> list = (List)request.getAttribute("role");%>
 				<!-- 获取指定用户信息 -->
 				<%UserDeptRoleDto dto = (UserDeptRoleDto)request.getAttribute("user");%>
 				<!-- 获取指定用户的角色 -->
 				<%List<UserDeptRoleDto> listRole = (List)request.getAttribute("listRole");%>

				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>角色</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
                         		<select  multiple="multiple"  name="roleName"  id="roleName" style="width:100%"
										data-rule-required="true"
										data-msg-required="角色必须选择">

                                 		<%if(flag.equals("2")){%>
											<%for(int i = 0 ;i<list.size();i++){%>
											<option value=<%=list.get(i).getValue()%>><%=list.get(i).getName()%></option>
											<%}%>
										<%}else{%>
												<%for(int i = 0 ;i<list.size();i++){%>
												<option value=<%=list.get(i).getValue()%>
													<%for(int j = 0;j<listRole.size();j++) {%>
												<%if(Integer.parseInt(list.get(i).getValue())==listRole.get(j).getRoleId()){%>
													selected
												<%}%>
												<%}%>
												><%=list.get(i).getName()%></option>
											<%}%>
										<%}%>
								</select>
						</div>
					</div>
				</div>

				<!-- 第六行 -->
				 <!-- 获取control定义的部门信息 -->
                 <%List<UserDeptRoleDto> dept = (List)request.getAttribute("list");%>

				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>所属部门</span>
						</div>
						<div id="addnew"  class="form-group col col-sm-8 col-md-8 col-lg-8 col-xs-12 ">
	                         <span id="tip"  style="color:#E1484D"></span>
	                         <!-- 当flg=1即为修改模式，读取部门信息 -->
	                         <%if(flag.equals("1")){%>
									<%for(int j = 0;j<dept.size();j++){%>
								  		<div class=" col-sm-12 col-md-12 col-lg-12 col-xs-12  no-padding">
		                                     	<div class=" col-sm-5 col-md-5 col-lg-5 col-xs-5  no-padding">
		                                     	<!-- 判断当该用户没有部门时显示为空 -->
		                                     	 <%if(dept.get(j).getDeptName()!=null) {%>
		                                     		<input type="hidden" class="deptId" name="dept" value=<%=dept.get(j).getDeptId()%>>
			                                     	<input type="hidden"  value=<%=dept.get(j).getDeptName()%>><%=dept.get(j).getDeptName()%>
		                                     	<%}%>
		                                     	</div>
		                                     	<div class=" col-sm-4 col-md-4 col-lg-4 col-xs-4  no-padding">
		                                     	<!-- 判断当该用户没有部门时显示为空 -->
		                                     	<%if(dept.get(j).getLeaderFlg()!=null){%>
		                                     	<input type="checkbox" name="leader" class="leaderFlg" value="<%=dept.get(j).getLeaderFlg()%>"	                                     		<%if(dept.get(j).getLeaderFlg().equals("1")){%>
		                                     	    	 checked="checked"
		                                     	    <%}%>
		                                     	  >负责人
		                                     	  <%}%>
		                                     	  </div>
		                                     	<div class=" col-sm-3 col-md-3 col-lg-3 col-xs-3 no-padding">
		                                     	<!-- 判断当该用户没有部门时显示为空 -->
		                                     	<%if(dept.get(j).getLeaderFlg()!=null){%>
		                                     		<button class="btn btn-default btn-xs delet"  style="width:100%">删除</button>
		                                     	 <%}%>
		                                     	</div>
		                                </div>
							  		<%}%>
							<%}%>
						</div>
						<div class="col-sm-1 col-md-1 col-lg-1 col-xs-1">
                         		<button class="btn btn-default btn-xs"  id="deptt">&nbsp;&nbsp;+   部门&nbsp;&nbsp;</button><br>&nbsp;
                        </div>
						</div>
					</div>

			<div class="buttongroup">
				<div class="row text-center">
			    	  <%if(flag.equals("1")){%>
			    		  	 <button id="btn-update" class="btn btn-primary " name="submit"   >保存</button>
					  		 <button id="btn-back"   name="cancel" class="btn" >返回</button>
			    	  <%}else{%>
			    	  		<button id="btn-save" class="btn btn-primary "  name="submit" >保存</button>
					 	  	<button id="btn-back"  name="cancel" class="btn" >返回</button>
			    	  <%}%>
				</div>
			</div>

		</div>
	</form>
		</div>
</fieldset>
<script src="js/module/system/StaffEdit.js">
</script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var staffEditJS = new Globals.staffEditJS();
		staffEditJS.init();
		 //下拉框问题
		/* $('#roleName').multipleSelect({
			width : '30%'
		}); */
	});
</script>
<%@include file="../include/inc_footer.jsp"%>