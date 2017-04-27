<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; utf-8">
    <title>Registration SL</title>
    <link href="../css/style.css" rel="stylesheet" type="text/css">
    <style>
        p {
            width: 100%;
            text-align: center;
            color: black;

        }

        .button {
            text-align: center;
            float: left;
            width: 345px;
            padding-left: 109px;
            margin: -99px;
            margin-top: -69px;
        }
    </style>
</head>


<body>
<div class="main">
    <div style="height: 25%">
        <p style="font-size: 22px; font-weight: bold"><fmt:message key="r1"/></p>
        <p style="font-size: 22px; font-weight: bold"><fmt:message key="r2"/></p>
        <p style="color: red"><fmt:message key="r3"/></p>

        <%--required pattern="[a-zA-Z-]+--%>

    </div>

    <%--PROGRAMMING--%>
    <form action="/authorization" method="post">
        <div style="line-height: 40px" class="registerf1">
            <p style="color: red"><fmt:message key="first_name"/>:
                <input type="text" name="first_name" value="${param.first_name}" required/></p>
            <p style="color: red"><fmt:message key="last_name"/>:
                <input type="text" name="last_name" value="${param.last_name}" required/></p>

            <p style="color: red"><fmt:message key="login"/>
                <input type="email" name="j_username" value="${param.j_username}" required/></p>

            <p style="color: red"><fmt:message key="password"/>
                <input type="password" name="j_password" required /></div>


        <div style="line-height: 40px" class="registerf1">
            <p><fmt:message key="country"/>:
                <input type="text" name="country" value="${param.country}"/></p>
            <p><fmt:message key="city"/>:
                <input type="text" name="city" value="${param.city}"/></p>

            <p><fmt:message key="next_trip"/>:
                <input type="text" name="next_trip" value="${param.next_trip}"/></p>

            <p style="color: red"><fmt:message key="sex"/>:
                <select name="sex" required>
                    <option></option>
                    <option value="male" ${param.sex == "male"  ? 'selected="selected"' : ''}>Male</option>
                    <option value="female" ${param.sex == "female"  ? 'selected="selected"' : ''}>Female</option>
                </select><br/>
            </p>

            <p><fmt:message key="date_of_birth"/>:
                <input type="date" name="date_of_birth" value="${param.date_of_birth}"/></p>
        </div>
        <div class="small_picture">
            <img src="../_images/title2.jpg" alt="surfer" width="470" height="350">
        </div>

        <div class="button">
            <input style="height: 35px; width: 150px;" type="submit" value="Register">
        </div>

        <div style="padding-left: 428px; padding-top: 40px;text-align: center; float: left; height: 100%">
                <%--@elvariable id="errors" type="java.util.List"--%>
                <c:forEach var="error" items="${errors}">
                    <h3 style="color: red">${error}<br/></h3>
                </c:forEach>
        </div>

    </form>

</div>
</body>
</html>
