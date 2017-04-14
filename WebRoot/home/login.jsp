<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en">
<!--<![endif]-->
<head>
  <%-- <link type="image/x-icon" href="${pageContext.request.contextPath}/style/login/favicon.ico" rel="icon"> --%>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>OA System| Login Page</title>
  <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport" />
  <meta content="" name="description" />
  <meta content="" name="author" />
  
  <!-- ================== BEGIN BASE CSS STYLE ================== -->
  <link href="${pageContext.request.contextPath}/style/login/jquery-ui.min.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/style/login/bootstrap.min.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/style/login/font-awesome.min.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/style/login/animate.min.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/style/login/style.min.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/style/login/style-responsive.min.css" rel="stylesheet" />
  <link href="${pageContext.request.contextPath}/style/login/default.css" rel="stylesheet" id="theme" />
  <!-- ================== END BASE CSS STYLE ================== -->
  
  <!-- ================== BEGIN BASE JS ================== -->
  <script src="${pageContext.request.contextPath}/style/login/pace.min.js"></script>
  <%-- <script type="text/javascript">
     if(window.parent!=window){
       window.parent.location.href=window.location.href;
     }
  </script> --%>
  <!-- ================== END BASE JS ================== -->
</head>
<body class="pace-top">
  <!-- begin #page-loader -->
  <div id="page-loader" class="fade in"><span class="spinner"></span></div>
  <!-- end #page-loader -->
  
  <div class="login-cover">
      <div class="login-cover-image"><img src="${pageContext.request.contextPath}/style/login/bg-2.jpg" data-id="login-cover-image" alt="" /></div>
      <div class="login-cover-bg"></div>
  </div>
  <!-- begin #page-container -->
  <div id="page-container" class="fade">
      <!-- begin login -->
        <div class="login login-v2" data-pageload-addclass="animated fadeIn">
            <!-- begin brand -->
            <div class="login-header" style="padding-left: 0px;">
            <%-- <div align="center">
                <img alt="" src="${pageContext.request.contextPath}/style/login/site-logo.png">
            </div> --%>
            </div>
            <!-- end brand -->
            <div class="login-content">
                <s:form action="loginoutAction_login.do"  autocomplete="off" method="post" name="formLogin" id="formLogin">
                   <div class="form-group m-b-20" style="color:black" >
                        <s:fielderror></s:fielderror>
                    </div>
                    <div class="form-group m-b-20">
                        <s:textfield  name="loginName" cssClass="form-control input-lg" placeholder="Email Address" />
                    </div>
                    <div class="form-group m-b-20">
                        <s:password  name="password" showPassword="false" cssClass="form-control input-lg" placeholder="Password" />
                    </div>
                    <div class="checkbox m-b-20">
                        <label>
                            <input type="checkbox" /> remerber me
                        </label>
                    </div>
                    <div class="login-buttons">
                        <button type="submit" class="btn btn-success btn-block btn-lg">login</button>
                    </div>
                </s:form>
            </div>
        </div>
        <!-- end login -->
        
        <ul class="login-bg-list">
            <li><a href="#" data-click="change-bg"><img src="${pageContext.request.contextPath}/style/login/bg-1.jpg" alt="" /></a></li>
            <li class="active"><a href="#" data-click="change-bg"><img src="${pageContext.request.contextPath}/style/login/bg-2.jpg" alt="" /></a></li>
            <li><a href="#" data-click="change-bg"><img src="${pageContext.request.contextPath}/style/login/bg-3.jpg" alt="" /></a></li>
            <li><a href="#" data-click="change-bg"><img src="${pageContext.request.contextPath}/style/login/bg-4.jpg" alt="" /></a></li>
            <li><a href="#" data-click="change-bg"><img src="${pageContext.request.contextPath}/style/login/bg-5.jpg" alt="" /></a></li>
            <li><a href="#" data-click="change-bg"><img src="${pageContext.request.contextPath}/style/login/bg-6.jpg" alt="" /></a></li>
        </ul>
       
  </div>
  <!-- end page container -->
  
  <!-- ================== BEGIN BASE JS ================== -->
  <script src="${pageContext.request.contextPath}/style/login/jquery-1.9.1.min.js"></script>
  <script src="${pageContext.request.contextPath}/style/login/jquery-migrate-1.1.0.min.js"></script>
  <script src="${pageContext.request.contextPath}/style/login/jquery-ui.min.js"></script>
  <script src="${pageContext.request.contextPath}/style/login/bootstrap.min.js"></script>
  <!--[if lt IE 9]>
    <script src="${pageContext.request.contextPath}/static/assets/crossbrowserjs/html5shiv.js"></script>
    <script src="${pageContext.request.contextPath}/static/assets/crossbrowserjs/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/assets/crossbrowserjs/excanvas.min.js"></script>
  <![endif]-->
  <script src="${pageContext.request.contextPath}/style/login/jquery.slimscroll.min.js"></script>
  <script src="${pageContext.request.contextPath}/style/login/jquery.cookie.js"></script>
  <!-- ================== END BASE JS ================== -->
  
  <!-- ================== BEGIN PAGE LEVEL JS ================== -->
  <script src="${pageContext.request.contextPath}/style/login/login-v2.demo.min.js"></script>
  <script src="${pageContext.request.contextPath}/style/login/apps.min.js"></script>
  <!-- ================== END PAGE LEVEL JS ================== -->

  <script>
    $(document).ready(function() {
      App.init();
      LoginV2.init();
    });
  </script>
</body>
</html>
