<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>WebSocket示例</title>
    <script type="text/javascript" src="/resources/js/jquery.js"></script>
</head>
<body>


<form action="login" method="post">
    用户名:
    <input name="userId" type="text"><br>
    密码:
    <input name="password" type="text" value="123456">
    <input type="submit" value="登录">
</form>


注册：
<form action="register" method="post">
    用户名:
    <input name="userId" type="text"><br>
    密码:
    <input name="password" type="password"><br>
    真实名字
    <input name="realName" type="text"><br>
    昵称
    <input name="nickName" type="text"><br>
    学号
    <input name="stuId" type="text"><br>
    <%--电话--%>
    <%--<input name="phone" type="text"><br>--%>
    专业
    <input name="major" type="text">


    <input type="submit" value="登录">
</form>

<form action="msg/login" method="post">
    用户名:
    <select name="id">
        <option value="1">张三</option>
        <option value="2">李四</option>
    </select><br>
    密码:
    <input name="password" type="text" value="123456">
    <input type="submit" value="登录">
</form>


<div align="center">
    <form action="/society/logo" method="post" enctype="multipart/form-data">
        <br/>
        上传社团头像:<input type="file" name="logo"/>
        社团Id:<input type="text" name="societyId"/>
        <input type="submit" value="upload"/>
    </form>
</div>
<a href="push.jsp"><button>push</button></a>
</body>

</html>
