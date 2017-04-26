<%--
  Created by IntelliJ IDEA.
  User: zojian
  Date: 2017/4/26
  Time: 上午10:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin-index</title>
</head>
<body>
<div align="center">
    <form action="/admin/schools" method="post">
        <br/>
        上传学校文件:<input type="file" name="school_excel"/>
        <input type="submit" value="upload"/>
    </form>

    <form action="/admin/schools" method="post">
    <br/>
    学校名称:<input type="text" name="name"/>
    <input type="submit" value="upload"/>
    </form>
</div>
</body>
</html>
