<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="querry" uri="/WEB-INF/surfland.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Friends</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <style>
        .userscope {
            width: 100%;
            height: 28%;
            float: left;
            /*border: double;*/
        }
        .txt {
            width: 35%;
            height: 98%;
            float: left;
            border: double;
            padding-left: 19px;
        }
        .pic {
            width: 18%;
            height: 98%;
            float: left;
            border: double;
        }
    </style>
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
    <%--@elvariable id="allFriends" type="java.util.List"--%>
    <c:forEach var="user" items="${allFriends}">
        <div class="userscope">
            <div class="pic">
                <img src="" alt="some img">
            </div>

            <div class="txt">
                <a href="user?id=${user.id}">${user.id} ${user.firstName} ${user.lastName}</a>
                <c:if test="${user.dateOfBirth != null}">
                    <h4>Возраст: ${user.age}</h4>
                </c:if>
                <c:if test="${user.address.country != null}">
                    <h4>Страна: ${user.address.country}</h4>
                </c:if>
                <c:if test="${user.address.city != null}">
                    <h4>Город: ${user.address.city}</h4>
                </c:if>
            </div>

            <div class="txt">
                <h4>Пол: ${user.sex}</h4>
                <c:if test="${user.nextTrip != null}">
                    <h4>Следующая поездка: ${user.nextTrip}</h4>
                </c:if>
            </div>

        </div>

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


</body>
</html>
