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
<li id="save"><a href="#" onclick="formSubmit('/basicinfo/factoryAction_save','_self');this.blur();">保存</a></li>
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
修改生产厂家
    </div> 
    </div>
    </div>

    <div>
		<table class="commonTable" cellspacing="1">
	        <tr>
	            <td class="columnTitle">厂家类型：</td>
	            <td class="tableContent">
	            	<s:select list="ctypeList" name="ctype"
	            		listKey="id" listValue="name"
	            		headerKey="" headerValue="--请选择--"
	            	>
	            	</s:select>
				</td>
	        </tr>
	        <tr>
	            <td class="columnTitle">厂家全称：</td>
	            <td class="tableContent"><input type="text" name="fullName" value="${fullName}"/></td>
	            <td class="columnTitle">厂家简称：</td>
	            <td class="tableContent"><input type="text" name="factoryName" value="${factoryName}"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">联&nbsp;系&nbsp;人：</td>
	            <td class="tableContent"><input type="text" name="contactor" value="${contactor}"/></td>
	            <td class="columnTitle">联系电话：</td>
	            <td class="tableContent"><input type="text" name="phone" value="${phone}"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">联系手机：</td>
	            <td class="tableContent"><input type="text" name="mobile" value="${mobile}"/></td>
	            <td class="columnTitle">传真号码：</td>
	            <td class="tableContent"><input type="text" name="fax" value="${fax}"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">验&nbsp;货&nbsp;员：</td>
	            <td class="tableContent"><input type="text" name="inspector" value="${inspector}"/></td>
	            <td class="columnTitle">排&nbsp;序&nbsp;号：</td>
	            <td class="tableContent"><input type="text" name="orderNo" value="${orderNo}"/></td>
	        </tr>
	        <tr>
	            <td class="columnTitle">说明信息：</td>
	            <td class="tableContent" colspan="3"><input type="text" name="cnote" value="${cnote}"/></td>
	        </tr>
		</table>
	</div>
</div>

 
</form>
</body>
</html>

