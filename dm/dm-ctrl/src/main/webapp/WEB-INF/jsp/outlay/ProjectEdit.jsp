<!--
	NSK-NRDC业务系统
	作成人：李辉
	课题新增/编辑画面
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
		<!-- fieldset : start -->
		<fieldset>
			<c:choose>
				<c:when test="${detailFlag == 'add'}">
					<legend>课题新增</legend>
				</c:when>
				<c:otherwise>
					<legend>课题修改</legend>
				</c:otherwise>
			</c:choose>
			<!-- 列表检索隐藏参数 -->
			<input id="deptNames1" type="hidden" value="${deptNames}"/>
			<input id="fyYear1" type="hidden" value="${fyYear}"/>
			<input id="deptId1" type="hidden" value="${deptId}"/>
			<input id="projectType1" type="hidden" value="${projectType}"/>
			<input id="projectName1" type="hidden" value="${projectName}"/>
			<div class="row " name="detail" >
			<!-- 表单 : start -->
			<form id="projectForm" modelAttribute="projectForm" method="post">
			<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
				<!-- 年度:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>年度</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<c:choose>
								<c:when test="${detailFlag == 'upd'}">
									<input id="fy_Year"  name="fyYear"  type="hidden" value="${projectForm.fyYear}" />
									<select id="fyYear" style="width: 50%" disabled="disabled">
								<option></option>
							</select>
								</c:when>
								<c:otherwise>
								<select id="fyYear" name="fyYear"  style="width: 50%">
								<option></option>
							</select>
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				<!-- 年度:end -->
				<!-- 课题编号:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>课题编号</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9  col-xs-12 ">
							<c:choose>
								<c:when test="${detailFlag == 'upd'}">
									<input id="projectNoEdit" name="projectNo" type="text" maxlength="20"
										value="${projectForm.projectNo}" readonly
										style="width: 100%;background-color:#EBECE4" />
								</c:when>
								<c:otherwise>
									<input name="projectNo" type="text" maxlength="20"
										value="${projectForm.projectNo}" style="width: 100%" />
								</c:otherwise>
							</c:choose>
						</div>
					</div>
				</div>
				<!-- 课题编号:end -->
				<!-- 主题:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>主题</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<input name="projectName" type="text" maxlength="100"
								value="${projectForm.projectName}" style="width: 100%" />
						</div>
					</div>
				</div>
				<!-- 主题:end -->
				<!-- 目标:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>目标</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9  col-xs-12 ">
							<input name="projectGoal" type="text" maxlength="100"
								value="${projectForm.projectGoal}" style="width: 100%" />
						</div>
					</div>
				</div>
				<!-- 目标:end -->
				<!-- 所属部署:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>所属部署</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9  col-xs-12 ">
							<div id="deptNames">
								<c:forEach items="${projectForm.departmentInfo}" var="depart"
									varStatus="status">
									<c:if test="${!status.last}">
										<lable>${depart.deptName}</lable>
										<br>
									</c:if>
									<c:if test="${status.last}">
										<lable>${depart.deptName}</lable>
									</c:if>
								</c:forEach>
							</div>
							<input id="deptAdd" type="button" class="btn add" value="+" />
						</div>
					</div>
				</div>
				<!-- 所属部署:end -->
				<!-- 部门Id:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<section class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12"></div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12">
								<input id="deptIds" name="deptIds" type="hidden"
									value="${projectForm.deptIds}" />
						</div>
					</section>
				</div>
				<!-- 部门Id:end -->
				<!-- 计划人工:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>计划人工</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<input name="planTimes" type="text"
								value="${projectForm.planTimes}" maxlength="10" style="width: 50%" />
						</div>
					</div>
				</div>
				<!-- 计划人工:end -->
				<!-- 课题分类:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>课题分类</span>
						</div>
						<div class="form-group col col-sm-6 col-md-6 col-lg-6 col-xs-12 ">
							<input id="project_type" type="hidden"
								value="${projectForm.projectType}" /> <select id="projectType"
								name="projectType" style="width: 50%">
							<option></option>
							</select>
						</div>
					</div>
				</div>
				<!-- 课题分类:end -->
				<!-- 内容:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>内容</span>
						</div>
						<div class="form-group col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<textarea rows="4" id="contents" maxLength="200" name="contents"
								style="width: 100%; resize: none">${projectForm.contents}</textarea>
						</div>
					</div>
				</div>
				<!-- 内容:end -->
				<!-- 法人:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12">
							<span>法人</span>
						</div>
						<div class="col col-sm-9 col-md-9 col-lg-9 col-xs-12 ">
							<div id="subjectLegal">
								<c:forEach items="${projectForm.subjectLegal}" var="legal"
									varStatus="status">
									<div class='col col-sm-12 col-md-12 col-lg-12 col-xs-12 no-padding'>
									<div class='form-group col col-sm-4 col-md-4 col-lg-4 col-xs-12 no-padding'>
										<div class='col col-sm-5 col-md-5 col-lg-5 col-xs-12 no-padding'>
											<lable style="width: 20%">${legal.dispName}</lable>
										</div>
										<lable>比例&nbsp;</lable>
										<input name="subjectlegal${status.index }" type="text" value="${legal.percentage}"
											style="width: 30%"/>
										</div>
										<div class='col-sm-1 col-md-1 col-lg-1 col-xs-12 no-padding'>
										<lable>%</lable>
										</div>
										</div>
								</c:forEach>
							</div>
							<input id="subjectLegalAdd" type="button" class="btn add"
								value="+" />
						</div>
					</div>
				</div>
				<!-- 法人:end -->

				<!-- 法人验证信息:start -->
				<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					<section class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
						<div class="col col-sm-3 col-md-3 col-lg-3 col-xs-12"></div>
						<div class="col col-sm-9 col-md-9 col-lg-9 col-xs-12">
							<div class="form-group">
								<input id="legalIds" name="legalIds" type="hidden"
									value="${projectForm.legalIds}" />
							</div>
							<div class="form-group">
								<input id="percentages" name="percentages" type="hidden"
									value="${projectForm.percentages}" />
							</div>
						</div>
					</section>
				</div>
				<!-- 法人验证信息:end -->
				<!-- 按钮:start -->
				<div class="col-xs-12">
					<div class=" buttongroup">
						<c:choose>
							<c:when test="${detailFlag == 'add'}">
								<button id="projectAdd" type="button"
									class="btn btn-primary  ">保存</button>
							</c:when>
							<c:otherwise>
								<button id="projectEdit" type="button"
									class="btn btn-primary  ">保存</button>
							</c:otherwise>
						</c:choose>
						<button id="projectCancel" type="button" class="btn">返回</button>
					</div>
				</div>
				<!-- 按钮:end -->
				</div>
			</form>
				</div>
			<!-- 表单 : end -->
		</fieldset>
		<!-- fieldset : end -->
<%@include file="../com/DepartSearchPopup.jsp"%>
<%@include file="../com/LegalSearchPopup.jsp"%>
<script src="js/module/outlay/projectEdit.js"></script>
<script>
	jQuery(document).ready(function() {
		//类实例化,局部变量定义区域
		var projectEditJS = new Globals.projectEditJS();
		projectEditJS.InitProjectEdit();
	});
</script>
<%@include file="../include/inc_footer.jsp"%>