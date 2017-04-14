<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!-- 告诉浏览器本网页符合XHTML1.0过渡型DOCTYPE -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title></title>
 
		<!-- 调用样式表 -->
		<link rel="stylesheet" rev="stylesheet" type="text/css" href="../../skin/default/css/default.css" media="all" />
		<script type="text/javascript" src="../../js/common.js"></script>
		<script type="text/javascript" src="../../js/datepicker/WdatePicker.js"></script>
	</head>
 
	<body>
	
		<form method="post">
			<input type="hidden" name="id" value="${id}" />
 
			<div id="menubar">
				<div id="middleMenubar">
					<div id="innerMenubar">
						<div id="navMenubar">
<ul>
<li id="save"><a href="#" onclick="formSubmit('/outproduct/outProductAction_print','_self');">打印</a></li>
<li id="back"><a href="/cargo/contractAction_list">返回</a></li>
</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="textbox" id="centerTextbox">
 
				<div class="textbox-header">
					<div class="textbox-inner-header">
						<div class="textbox-title">
							出货表月统计
						</div>
					</div>
				</div>
				<div>
 
 
					<div>
						<table class="commonTable" cellspacing="1">
							<tr>
								<td class="columnTitle_mustbe">
									船&nbsp;期：
								</td>
								<td class="tableContent">
									<input type="text" style="width: 90px;" name="inputDate"
										onclick="WdatePicker({el:this,isShowOthers:true,dateFmt:'yyyy-MM'});" />
								</td>
							</tr>
						</table>
					</div>
				</div>
 
 
		</form>
	</body>
</html>

