<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<p>
    <a href="${pageContext.request.contextPath}/user"><fmt:message key="my_page"/> </a>
</p>
<p>
    <a href="${pageContext.request.contextPath}/user/settings/"><fmt:message key="update"/> </a>
</p>
<p style="padding-top: 42px">
    <a href="${pageContext.request.contextPath}/user/delete/"><fmt:message key="delete"/></a>
</p>
<p>
    <a href="${pageContext.request.contextPath}/exit"><fmt:message key="exit"/></a>
</p>

</body>
</html>
