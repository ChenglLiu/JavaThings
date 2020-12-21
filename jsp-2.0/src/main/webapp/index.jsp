<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
    <head>
        <title>$Title$</title>
    </head>
<body>
    <%-- JSP表达式 --%>
    <%= new java.util.Date()%>

    <%-- jsp脚本片段 --%>
    <%
        int sum = 0;
        for (int i=0; i<=100; i++) {
            sum += i;
        }
        out.println("<h2>Sum: " + sum + "</h2>");
    %>
    <%
        out.println("测试结束..." + sum);
    %>
    <%
        for (int i=0; i<5; i++) {
    %>
    <h2>输出5次</h2>
    <%
            out.print("这是第" + (i+1) + "次");
        }
    %>

    <%-- JSP声明 --%>
    <%!
        static {
            System.out.println("Loading servlet....");
        }

        private int global = 0;

        public void test() {
            System.out.println("step into test method...");
        }
    %>
</body>
</html>
