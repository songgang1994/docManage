<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<div class="modal fade" id="deviceSearchPopup" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-body">
				<fieldset >
			  		<legend>设备检索</legend>
					<!-- 条件栏 -->
					<div class="row">
						<form id="deviceSearchForm">
							<div class="row col col-sm-12 col-md-12 col-lg-12 col-xs-12">
								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12 no-padding">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12">
											<span>设备编号</span>
										</div>
										<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 ">
											<input type="text" id="loddeviceNo" style="width: 100%" />
										</div>
									</div>
									<div class="col col-sm-6 col-md-6 col-lg-6 col-xs-12">
										<div class="col col-sm-12 col-md-3 col-lg-3 col-xs-12 no-padding">
											<span>设备名称</span>
										</div>
										<div class="form-group col col-sm-9 col-md-7 col-lg-7 col-xs-12 no-padding">
											<input type="text" id="loddeviceName" style="width: 100%"/>
										</div>
									</div>
								</div>

								<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
									<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
										<button id="searchdevice" class="btn btn-primary ">
											查询</button>
									</div>
								</div>

							</div>
						</form>
					</div>
					<!-- 条件栏结束 -->

					<!-- 表格 -->
					<div class="wrapper">
					   	<div id="deviceSearchTableDiv"  class="content-wapper" style="display: none">
					    	<div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" >
								<table id="deviceSearchTable" style="margin-bottom:5px;table-layout: fixed;"  class=" text-center table table-bordered table-hover">
									<tr>
									</tr>
								</table>
					       	</div>
					    </div>
					    <div class="col col-sm-12 col-md-12 col-lg-12 col-xs-12" align="center" id="noDeviceResult"  style="display: none">
							无查询结果！
						</div>
						<!-- 按钮组 -->
					    <div  class="col col-sm-12 col-md-12 col-lg-12 col-xs-12">
					       	<div class="buttongroup">
								<button id="deviceConfirm"  class="btn btn-primary">确认</button>
								<button id="deviceClose"  class="btn ">关闭</button>
							</div>
						</div>
					</div>
				</fieldset>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script src="js/module/com/deviceSearchPopup.js"></script>
<script>
	jQuery(document).ready(function() {
		var deviceSearchPopupJS = new Globals.deviceSearchPopupJS();
		deviceSearchPopupJS.InitdeviceSearchPopup();
	});
</script>