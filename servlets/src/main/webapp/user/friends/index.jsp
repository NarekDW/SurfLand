<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="querry" uri="/WEB-INF/surfland.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
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
            padding-left: 19px;
        }
        .pic {
            width: 18%;
            height: 98%;
            float: left;
        }
    </style>
</head>
<body>

<div class="header">
    <%--suppress CheckImageSize --%>
    <img src="${pageContext.request.contextPath}/_images/op.jpg" alt="ocean" width="100%" height="100%">
</div>

<div class="left">
    <%@ include file="/WEB-INF/const/left.jsp"%>
</div>



<div class="centr">
    <%--@elvariable id="allFriends" type="java.util.List"--%>
    <c:forEach var="user" items="${allFriends}">
        <div class="userscope">
            <div class="pic">
                <img src="/_images/avatar.png" alt="some img"  width="100%" height="100%">
            </div>

            <div class="txt">
                <a href="user?id=${user.id}">${user.firstName} ${user.lastName}</a>
                <c:if test="${user.dateOfBirth != null}">
                    <h4><fmt:message key="age"/> : ${user.age}</h4>
                </c:if>
                <c:if test="${user.address.country != null}">
                    <h4><fmt:message key="country"/> : ${user.address.country}</h4>
                </c:if>
                <c:if test="${user.address.city != null}">
                    <h4><fmt:message key="city"/> : ${user.address.city}</h4>
                </c:if>
            </div>

            <div class="txt">
                <h4><fmt:message key="sex"/> : ${user.sex}</h4>
                <c:if test="${user.nextTrip != null}">
                    <h4><fmt:message key="next_trip"/> : ${user.nextTrip}</h4>
                </c:if>
            </div>

        </div>

    </c:forEach>
</div>


<div class="right">
    <%@ include file="/WEB-INF/const/right.jsp"%>
</div>


</body>
</html>
