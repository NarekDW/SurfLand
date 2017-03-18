<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; utf-8">
    <title>SL settings</title>
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
    <form style="text-align: center; line-height: 40px" action="/update" method="post">
        <p>First name: <input type="text" name="first_name" value="${param.first_name}"/></p>
        <p>Last name: <input type="text" name="last_name" value="${param.last_name}"/></p>

        <p>Country: <input type="text" name="country" value="${param.country}"/></p>
        <p>City: <input type="text" name="city" value="${param.city}"/></p>
        <p>Next trip: <input type="text" name="next_trip" value="${param.next_trip}"/></p>

        <p>Date of birth: <input type="date" name="date_of_birth" value="${param.date_of_birth}"/></p>
        <p>Sex:
            <select name="sex">
                <option></option>
                <option value="male" ${param.sex == "male"  ? 'selected="selected"' : ''}>Male</option>
                <option value="female" ${param.sex == "female"  ? 'selected="selected"' : ''}>Female</option>
            </select><br/>
        </p>

        <p><input type="submit" value="Update"></p>
    </form>

    <%--@elvariable id="errors" type="java.util.List"--%>
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
