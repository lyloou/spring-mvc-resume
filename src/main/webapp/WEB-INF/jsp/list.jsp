<%--
  Created by IntelliJ IDEA.
  User: bob
  Date: 2020/6/6
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.lyloou.practice.model.Resume" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>用户列表</title>
    <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
</head>
<body>

<script>
    function deleteById(_id) {
        let data = {
            id: _id
        };
        $.ajax({
            url: '${pageContext.request.contextPath}/deleteResume',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            success: doSuccess,
            error: doError
        })
    }

    function updateById(_id) {
        let name = document.getElementById("name_" + _id);
        let phone = document.getElementById("phone_" + _id);
        let address = document.getElementById("address_" + _id);

        let data = {
            id: _id,
            name: name.value,
            phone: phone.value,
            address: address.value
        };
        $.ajax({
            url: '${pageContext.request.contextPath}/updateResume',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            success: doSuccess,
            error: doError
        })
    }

    function doSuccess(data) {
        if (data === 0) {
            window.location.reload()
        } else {
            alert("操作失败")
        }
    }

    function doError(err) {
        console.log(err);
        alert("操作失败")
    }

    function addResume() {
        let name = document.getElementById("name");
        let phone = document.getElementById("phone");
        let address = document.getElementById("address");
        let data = {
            name: name.value,
            phone: phone.value,
            address: address.value
        };
        $.ajax({
            url: '${pageContext.request.contextPath}/addResume',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: 'application/json;charset=utf-8',
            dataType: 'json',
            success: doSuccess,
            error: doError
        })
    }
</script>

<table>
    <tr>
        <th>ID</th>
        <th>用户名</th>
        <th>电话</th>
        <th>地址</th>
    </tr>
    <%
        if (request.getAttribute("resumeList") != null) {
    %>
    <%for (Resume u : (List<Resume>) request.getAttribute("resumeList")) {%>
    <tr>
        <td><%=u.getId()%>
        </td>
        <td>
            <input name="name" id="name_<%=u.getId()%>" type="text" tabindex="1"
                   value="<%=u.getName()==null?"":u.getName()%>"/>

        </td>
        <td>
            <input name="phone" id="phone_<%=u.getId()%>" type="number" tabindex="1"
                   value="<%=u.getPhone()==null?"":u.getPhone()%>"/>
        </td>
        <td>
            <input name="address" id="address_<%=u.getId()%>" type="text" tabindex="1"
                   value="<%=u.getAddress()==null?"":u.getAddress()%>"/>
        </td>
        <td>
            <a href="javascript:updateById(<%=u.getId()%>)">更新</a>
            <a href="javascript:deleteById(<%=u.getId()%>)">删除</a>
        </td>
    </tr>
    <%}%>
    <%}%>
</table>

<table>
    <tr>
        <td>
            新
        </td>
        <td>
            <input name="name" id="name" type="text" tabindex="1" value=""/>
        </td>
        <td>
            <input name="phone" id="phone" type="number" tabindex="1" value=""/>
        </td>
        <td>
            <input name="address" id="address" type="text" tabindex="1" value=""/>
        </td>
        <td>
            <a href="javascript:addResume()">添加用户</a>
        </td>
    </tr>
</table>


</body>
</html>
