<%--@elvariable id="user" type="model.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="querry" uri="/WEB-INF/surfland.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; utf-8">
    <title>SL</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <style>
        .user_info {
            float: left;
            padding-left: 9%;
            width: 204px;
            text-align: center;
        }

        .avatar {
            width: 25%;
            height: 40%;
            /*border: double;*/
            float: left;
        }

        .msg {
            width: 100%;
            height: auto;
            clear: both;
            font-size: 22px;
            text-align: center;
            font-style: italic;
        }
    </style>
</head>
<body>
<div class="main">

    <div class="header">
        <%--suppress CheckImageSize --%>
        <img src="${pageContext.request.contextPath}/_images/op.jpg" alt="ocean" width="100%" height="100%">
    </div>

    <div class="left">
        <%@ include file="/WEB-INF/const/left.jsp"%>
    </div>


    <c:choose>
        <c:when test="${user != null}">

            <div class="centr">
                <div class="avatar">
                    <img src="/_images/avatar.png" alt="Avatar"  width="100%" height="100%">
                </div>

                <div class="user_info">
                    <h3>${user.firstName} ${user.lastName}</h3>
                    <c:if test="${user.dateOfBirth != null}">
                        <h4><fmt:message key="date_of_birth"/> : ${user.dateOfBirth}</h4>
                    </c:if>
                    <h4><fmt:message key="sex"/> : ${user.sex}</h4>
                    <c:if test="${user.address.country != null}">
                        <h4><fmt:message key="country"/>: ${user.address.country}</h4>
                    </c:if>
                    <c:if test="${user.address.city != null}">
                        <h4><fmt:message key="city"/> : ${user.address.city}</h4>
                    </c:if>
                    <c:if test="${user.nextTrip != null}">
                        <h4><fmt:message key="next_trip"/> : ${user.nextTrip}</h4>
                    </c:if>
                </div>

                <div class="user_info">
                    <querry:friend user="${user}"/>
                </div>

                <querry:news user="${user}"/>

            </div>
        </c:when>

        <c:when test="${user == null}">
            <div class="centr">
                <h3 style="color: red; text-align: center"><fmt:message key="acc_not_found"/> </h3>
            </div>
        </c:when>

    </c:choose>


    <div class="right">
       <%@ include file="/WEB-INF/const/right.jsp"%>
    </div>


</div>
</body>
</html>
