<%@ page language="java" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>物流综合管理平台</title>
    <link href="/skin/default/css/login.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="js/common.js"></script>
</head>

<body>
<form id="login_main" action="/home/loginAction_login" method="post">
<div id="warpbox">
    <div class="main">
         <div class="zck"><div class="error_message" style="color: red;">${loginMessage}</div>
          <div class="zc">
                <div class="zc_line">用户名：
                <input type="text" value="" name="userName" id="userName"
                 onFocus="this.select();"
                 autocomplete="off" title="请您输入用户名" />
                 </div>
                <div class="zc_line">密　码：
                <input type="password" value="" name="password" id="password"
                 onfocus="$('#ts').css('display','none');this.select();"
                 onKeyDown="javascript:if(event.keyCode==13){ submitFind(); }"
                 title="请您输入密码"/></div>
          </div>
            <div class="dl">
                <input  class="dl_img" value="" type="button" onclick="login();"
                  onmouseover="this.style.background='url(/skin/default/images/login/dl_h.jpg) no-repeat'" 
                  onmouseout="this.style.background='url(/skin/default/images/login/dl_a.jpg) no-repeat'"
                />
                <input class="reset_img" value="" type="reset"   
                  onmouseover="this.style.background='url(/skin/default/images/login/reset_h.jpg) no-repeat'" 
                  onmouseout="this.style.background='url(/skin/default/images/login/reset_a.jpg) no-repeat'"
                />
            </div>
        </div></div>
        <div class="bqxx" style="text-align:right;margin-top:0px;">
        <a href="#">系统帮助</a> | <a href="#" onclick="bookmarkit();">加入收藏</a>
        </div>

</div>
</form>
<script type="text/JavaScript">
    document.getElementById('login_main').userName.focus();
    function login(){
        var userName = document.getElementById('userName').value
        var passwor = document.getElementById('password').value
        if (userName == "" || password == "") {
            alert("用户名或密码不能为空！");
        } else {
            document.getElementById('login_main').submit();
        }
    }
</script>
</body>
</html>


