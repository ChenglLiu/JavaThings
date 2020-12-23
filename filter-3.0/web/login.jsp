<%--
  Created by IntelliJ IDEA.
  User: LIuChenglong
  Date: 2020/12/22
  Time: 21:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/servlet/login" method="post">
    用户名:<input type="text" name="username"> <br>
    密码:<input type="text" name="password"> <br>
    <input type="submit">
</form>
</body>
</html>
