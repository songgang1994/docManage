<!--
	NSK-NRDC业务系统
	作成人：李辉
	课题一览画面
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="../include/inc_header.jsp"%>
<fieldset>
<legend>首页</legend>
<div class="wapper" name="detail">
	<div class="content-wapper">
		<input id="topUserId" type="hidden" value="${topUserId}"/>
		<input id="topdeptIds" type="hidden" value="${topdeptIds}"/>
			<!-- 我的待处理文档 : start -->
			<div class="row-fluid table-content"
				style="margin-left: 15px; margin-right: 15px;">
				<lable>我的待处理文档</lable>
				<table id="toTreatDoc"
					style="margin-bottom: 5px; table-layout: fixed;"
					class="table table-bordered table-hover" width="100%">
					<thead>
						<tr>
							<th class='text-center'
								style="vertical-align: middle; width: 15%;">日期</th>
							<th class='text-center'
								style="vertical-align: middle; width: 15%">文档编号</th>
							<th class='text-center'
								style="vertical-align: middle; width: 25%">文件名称</th>
							<th class='text-center'
								style="vertical-align: middle; width: 15%">审核人</th>
							<th class='text-center'
								style="vertical-align: middle; width: 15%">状态</th>
							<th class='text-center'
								style="vertical-align: middle; width: 15%">操作</th>
						</tr>
					</thead>
					<c:choose>
						<c:when test="${not empty willHandleDocList }">
							<c:forEach items="${willHandleDocList}" var="entity"
								varStatus="status">
							<tr>
								<td style="vertical-align: middle;">
									<div>
										<fmt:formatDate value="${entity.createDt}"
											pattern="yyyy/MM/dd " />
									</div>
								</td>
								<td style="vertical-align: middle;">
									<div>${entity.documentCode}</div>
								</td>
								<td style="vertical-align: middle;">
									<div class="overflow-custom" title="${entity.fileContent}">${entity.fileContent}</div>
								</td>

								<td style="vertical-align: middle;">
									<div>${entity.userName}</div>
								</td>
								<td style="vertical-align: middle;">
									<div>${entity.dispName}</div>
								</td>
								<td style="vertical-align: middle;">
										<a documentCode="${entity.documentCode}" class="btn-upd">处理</a>
								</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6">
									<div class="row-fluid" align="center">无查询结果！</div>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
				<div class="text-right">
					<a id="toTreatDocSubmit">查看更多</a>
				</div>
			</div>
			<!-- 我的待处理文档 : end -->
			<!-- 待审核文档 : start -->
			<c:choose>
				<c:when test="${leaderFlg == '1'}">
					<div name="toCheckDoc" class="row-fluid table-content" style="margin: 15px;">
						<lable>待审核文档</lable>
						<table id="toCheckDoc"
							style="margin-bottom: 5px; table-layout: fixed;"
							class="table table-bordered table-hover" width="100%">
							<thead>
								<tr>
									<th class='text-center'
										style="vertical-align: middle; width: 15%">日期</th>
									<th class='text-center'
										style="vertical-align: middle; width: 15%">文档编号</th>
									<th
										style="vertical-align: middle; width: 25%">文件名称</th>
									<th class='text-center'
										style="vertical-align: middle; width: 15%">提交人</th>
									<th class='text-center'
										style="vertical-align: middle; width: 15%">状态</th>
									<th class='text-center'
										style="vertical-align: middle; width: 15%">操作</th>
								</tr>
							</thead>
							<c:choose>
								<c:when test="${not empty willApproveDocList }">
									<c:forEach items="${willApproveDocList}" var="entity"
										varStatus="status">
									<tr>
										<td style="vertical-align: middle;">
											<div>
												<fmt:formatDate value="${entity.updateDt}"
													pattern="yyyy/MM/dd " />
											</div>
										</td>
										<td style="vertical-align: middle;">
											<div>${entity.documentCode}</div>
										</td>
										<td style="vertical-align: middle;">
											<div class="overflow-custom" title="${entity.fileContent}">${entity.fileContent}</div>
										</td>
										<td style="vertical-align: middle;">
											<div>${entity.userName}</div>
										</td>

										<td style="vertical-align: middle;">
											<div>${entity.dispName}</div>
										</td>
										<td style="vertical-align: middle;">
												<a documentCode="${entity.documentCode}"class="btn-upd">审核</a>
										</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="6">
											<div class="row-fluid" align="center">无查询结果！</div>
										</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
						<div class="text-right">
							<a id="toCheckDocSubmit">查看更多</a>
						</div>
					</div>
				</c:when>
				<c:otherwise>
				</c:otherwise>
			</c:choose>
			<!-- 待审核文档 : end -->
			<!-- 我的当月工时 : start -->
			<div class="row-fluid table-content" style="margin: 15px;">
				<lable>我的当月工时</lable>
				<input id="workingTimeWrite" type="button" class='btn btn-primary'
					value="工时填写" style="float: right">
				<table id="monthlyHour" style="margin-bottom: 5px; margin-top: 20px;table-layout: fixed;"
					class="table table-bordered table-hover" width="100%">
					<thead>
						<tr>
							<th class='text-center'
								style="vertical-align: middle; width: 10%">FY月度</th>
							<c:forEach items="${monthHourList.projectNos}" var="entity"
								varStatus="status">
								<th class='text-center' style="vertical-align: middle;">
								<div class="overflow-custom" title="${entity}">${entity}</div></th>
							</c:forEach>
							<th class='text-center'
								style="vertical-align: middle; width: 10%">合计(小时)</th>
						</tr>
					</thead>
					<c:choose>
						<c:when test="${not empty monthHourList.monthHour }">
							<tr>
								<td style="vertical-align: middle;">
									<div>
										${monthHourList.monthHour.year}年${monthHourList.monthHour.month}月
									</div>
								</td>
								<c:forEach items="${monthHourList.monthHour.monthHour}"
									var="entity" varStatus="status">
									<td style="vertical-align: middle;">
										<div class="overflow-custom" title="${entity.workingtimes}">${entity.workingtimes}</div>
									</td>
								</c:forEach>
								<td style="vertical-align: middle;">
									<div>${monthHourList.monthHour.timesTotal}</div>
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="2">
									<div class="row-fluid" align="center">无查询结果！</div>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
				<div class="text-right">
					<a id="toWorkingTimeSubmit">查看更多</a>
				</div>
			</div>
			<!-- 我的当月工时 : end -->
			<!-- 我的已预约设备 : start -->
			<div class="row-fluid table-content" style="margin: 15px;">
				<lable>我的已预约设备</lable>
				<table id="deviceReserve"
					style="margin-bottom: 5px; table-layout: fixed;"
					class="table table-bordered table-hover" width="100%">
					<thead>
						<tr>
							<th class='text-center'
								style="vertical-align: middle; width: 15%">设备编号</th>
							<th class='text-center'
								style="vertical-align: middle; width: 15%">设备名称</th>
							<th class='text-center'
								style="vertical-align: middle; width: 25%">开始时间</th>
							<th class='text-center'
								style="vertical-align: middle; width: 25%">结束时间</th>
							<th class='text-center'
								style="vertical-align: middle; width: 15%">操作</th>
						</tr>
					</thead>
					<c:choose>
						<c:when test="${not empty deviceReserveInfoList }">
							<c:forEach items="${deviceReserveInfoList}" var="entity"
								varStatus="status">
							<tr>
								<td style="vertical-align: middle;">
									<div class="overflow-custom" title="${entity.deviceNo}">${entity.deviceNo}</div>
								</td>
								<td style="vertical-align: middle;">
									<div class="overflow-custom" title="${entity.deviceName}">${entity.deviceName}</div>
								</td>
								<td style="vertical-align: middle;">
									<div>
										<fmt:formatDate value="${entity.startDt}"
											pattern="yyyy/MM/dd HH:mm" />
									</div>
								</td>
								<td style="vertical-align: middle;">
									<div>
										<fmt:formatDate value="${entity.endDt}"
											pattern="yyyy/MM/dd HH:mm" />
									</div>
								</td>
								<td style="vertical-align: middle;">
										<a deviceNo="${entity.deviceNo}" reserveNo="${entity.reserveNo}" class="btn-upd">修改预约</a>
								</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5">
									<div class="row-fluid" align="center">无查询结果！</div>
								</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
				<div class="text-right">
					<a id="deviceReserveSubmit">查看更多</a>
				</div>
			</div>
			<!-- 我的已预约设备 : end -->
		</div>
	</div>
</fieldset>
<!-- 结果栏 end-->
<!--动态包含 pop画面-->
<jsp:include page="../device/DeviceReservePopup.jsp"/>
<div class="theme-popover"></div>
<div class="theme-popover-mask"></div>
<div class="html-temp"></div>
<script src="js/module/top/top.js"></script>
	<script>
		jQuery(document).ready(function() {
			//类实例化,局部变量定义区域
			var topJS = new Globals.topJS();
			topJS.InitTop();
		});
</script>
<%@include file="../include/inc_footer.jsp"%>