<%--
  Created by IntelliJ IDEA.
  User: bob
  Date: 2020/6/6
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="utf-8" %>
<html>
<head>
    <title>Title</title>
    <%--    https://blog.csdn.net/ningbufan728903/article/details/55670552 --%>
    <script>
        var count = 3;
        var t;

        function writeTip() {
            document.getElementById("d").innerHTML = "<a href='/'>页面将在" + (count--) + "秒内跳转到登录页面</a>";
            if (count === 0) {
                window.clearInterval(t);
                window.location = "/";
            }
        }

        t = window.setInterval("writeTip()", 1000);
    </script>
</head>
<body>
操作失败了：
<p id="d">
    页面将在3秒内跳转到登录页面
</p>
</body>
</html>
