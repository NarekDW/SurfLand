<%--@elvariable id="user" type="model.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="querry" uri="/WEB-INF/surfland.tld" %>
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; utf-8">
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <style>
        .user_info{
            float: left;
            padding-left: 9%;
            width: 204px;
            text-align: center;
        }
        .avatar{
            width: 25%;
            height:40%;
            border: double;
            float: left;
        }
    </style>
</head>
<body>
<div class="main">

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

        <div class="avatar">
            <img src="avatar" alt="Avatar">
        </div>


        <div class="user_info">
            <h3>${user.firstName} ${user.lastName}</h3>
            <c:if test="${user.dateOfBirth != null}">
                <h4>Date of birth: ${user.dateOfBirth}</h4>
            </c:if>
            <h4>Sex: ${user.sex}</h4>
            <c:if test="${user.address.country != null}">
                <h4>Country: ${user.address.country}</h4>
            </c:if>
            <c:if test="${user.address.city != null}">
                <h4>City: ${user.address.city}</h4>
            </c:if>
            <c:if test="${user.nextTrip != null}">
                <h4>Next trip: ${user.nextTrip}</h4>
            </c:if>
        </div>

       <div class="user_info">
           <querry:friend user="${user}"/>
       </div>
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
