<%-- ����JSP�������� --%>
<%@ page contentType="text/html; charset=gb2312"%>
<html>
<head>
    <title>JavaWebû����������</title>
    <%-- ����JSP�������� --%>
<%--    <meta http-equiv="Content-Type" content="text/html charset=gb2312">--%>
</head>
<body>
<h2>Hello World!</h2>

<%-- �ύ·����Ҫ�ҵ���Ŀ·�� --%>
<%-- ${pageContext.request.contextPath} ����ǰ��Ŀ --%>
<form action="${pageContext.request.contextPath}/login" method="get">
    �û���:<input type="text" name="username"> <br>
    ����:<input type="text" name="password"> <br>
    <input type="submit" name="�ύ">
</form>
</body>
</html>
