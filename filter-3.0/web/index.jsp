<%--
  Created by IntelliJ IDEA.
  User: LIuChenglong
  Date: 2020/12/21
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <h2>
      当前有
      <span style="color: pink"><%=this.getServletConfig().getServletContext().getAttribute("count")%></span>
      人在线
    </h2>
  </body>
</html>
