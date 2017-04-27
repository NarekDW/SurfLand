<%--
  Created by IntelliJ IDEA.
  User: Narek
  Date: 23.03.2017
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <div style="width: 100%; height: 100%; text-align: center; padding-top: 15%; font-size: 24px;
                text-decoration: none">
        <p style="color: red;">Page not found.</p>
        <p>
            <a href="${pageContext.request.contextPath}/user">Go to my page
            ${sessionScope.currentUser.firstName} ${sessionScope.currentUser.lastName}</a>
        </p>
    </div>


</body>
</html>
