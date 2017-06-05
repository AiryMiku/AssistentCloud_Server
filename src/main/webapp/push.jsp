<%--
  Created by IntelliJ IDEA.
  User: zojian
  Date: 2017/6/5
  Time: 上午11:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getServerName() + ":"
            + request.getServerPort() + path + "/";
    String basePath2 = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="/resources/js/jquery.js"></script>
</head>
<body>
    <script>
        var path = '<%=basePath%>';
        var websocket = new WebSocket("ws://"+path+"/push");
        websocket.onopen = function (event) {
            console.log(event);
            alert("WebSocket:已连接");
        };
        websocket.onmessage = function (event) {
            var data = JSON.parse(event.data);
            console.log("WebSocket:收到一条消息", data);
        };
        websocket.onerror = function (event) {
            console.log(event);
            alert("WebSocket:发生错误 ");
        };
        websocket.onclose = function (event) {
            console.log(event);
            alert("WebSocket:已关闭");
        }
    </script>
</body>
</html>
