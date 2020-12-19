<%-- 处理JSP中文乱码 --%>
<%@ page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>JavaWeb没有中文乱码</title>
    <%-- 处理JSP中文乱码 --%>
<%--    <meta http-equiv="Content-Type" content="text/html charset=gb2312">--%>
</head>
<body>
<h2>Hello World!</h2>

<%-- 提交路径需要找到项目路径 --%>
<%-- ${pageContext.request.contextPath} 代表当前项目 --%>
<form action="${pageContext.request.contextPath}/login" method="get">
    用户名:<input type="text" name="username"> <br>
    密码:<input type="text" name="password"> <br>
    <input type="submit" name="提交">
</form>
</body>
</html>
