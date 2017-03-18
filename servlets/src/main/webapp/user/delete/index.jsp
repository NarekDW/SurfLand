<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; utf-8">
    <title>SL Delete</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="header">
    <%--suppress CheckImageSize --%>
    <img src="${pageContext.request.contextPath}/_images/header.jpg" alt="ocean" width="100%" height="100%">
</div>

<div class="left">
    <h3><a href="${pageContext.request.contextPath}/finduser/" style="color: blue; text-decoration: none">Search users</a></h3>
    <h3><a href="${pageContext.request.contextPath}/myfriends" style="color: blue; text-decoration: none">My friends</a></h3>
    <h3><a href="${pageContext.request.contextPath}/follow" style="color: blue; text-decoration: none">I follow</a></h3>
    <h3><a href="${pageContext.request.contextPath}/followers" style="color: blue; text-decoration: none">My followers</a></h3>
</div>

<div class="centr">
    <form style="text-align: center; line-height: 40px" action="/delete" method="post">
        <p>Choose parameter's, which you want to delete:</p>

        <p>Country: <input type="checkbox" name="country"/></p>
        <p>City: <input type="checkbox" name="city"/></p>
        <p>Date of birth: <input type="checkbox" name="date_of_birth"/></p>
        <p>Next trip: <input type="checkbox" name="next_trip"/></p>

        <p><input type="submit" value="Delete parametr"></p>
    </form>
    <form style="text-align: center; padding-top: 42px" action="/delete" method="post">
        <p><input type="submit" name="delete_account" value="Delete account"></p>
    </form>

    <c:forEach var="error" items="${errors}">
        <p style="color: red; text-align: center">${error}<br/></p>
    </c:forEach>

</div>

<div class="right">
    <p>
        <a href="${pageContext.request.contextPath}/mypage">My page</a>
    </p>
    <p>
        <a href="${pageContext.request.contextPath}/user/settings/">Update</a>
    </p>
    <p style="padding-top: 42px">
        <a href="${pageContext.request.contextPath}/user/delete/">Delete</a>
    </p>
    <p>
        <a href="${pageContext.request.contextPath}/exit">Exit</a>
    </p>
</div>

</div>

</body>
</html>
