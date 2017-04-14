<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <title></title>

	<link rel="stylesheet" rev="stylesheet" type="text/css" href="../../skin/default/css/default.css" media="all" />
	<script type="text/javascript" src="../../js/common.js"></script>
	
</head>
<body>
<form name="icform" method="post">
	<input type="hidden" name="id" value="${id}">

<div id="menubar">
<div id="middleMenubar">
<div id="innerMenubar">
    <div id="navMenubar">
<ul>
<li id="back"><a href="#" onclick="formSubmit('/basicinfo/factoryAction_list','_self');this.blur();">返回</a></li>
</ul>
    </div>
</div>
</div>
</div>
     
<div>
    <div class="textbox-header">
    <div class="textbox-inner-header">
    <div class="textbox-title">
浏览生产厂家
    </div> 
    </div>
    </div>

    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">厂家类型：</td>
	            <td class="tableContent">${typeName}</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">厂家全称：</td>
	            <td class="tableContent">${fullName}</td>
	            <td class="columnTitle">厂家简称：</td>
	            <td class="tableContent">${factoryName}</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">联&nbsp;系&nbsp;人：</td>
	            <td class="tableContent">${contactor}</td>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent">${phone}</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">联系手机：</td>
	            <td class="tableContent">${mobile}</td>
	            <td class="columnTitle">传真号码：</td>
	            <td class="tableContent">${fax}</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">验&nbsp;货&nbsp;员：</td>
	            <td class="tableContent">${inspector}</td>
	            <td class="columnTitle">排&nbsp;序&nbsp;号：</td>
	            <td class="tableContent">${orderNo}</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">说明信息：</td>
	            <td class="tableContent" colspan="3">${cnote}</td>
	        </tr>
		</table>
	</div>
</div>

 
</form>
</body>
</html>

