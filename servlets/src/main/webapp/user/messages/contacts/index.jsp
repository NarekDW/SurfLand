<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="querry" uri="/WEB-INF/surfland.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>

<html>
<head>
    <title>Contacts</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">

    <style>
        .userscope {
            width: 100%;
            height: 28%;
            float: left;
            /*border: double;*/
        }

        .pic {
            width: 18%;
            height: 98%;
            float: left;
            /*border: double;*/
        }

        .txt {
            width: 35%;
            height: 98%;
            float: left;
            /*border: double;*/
            padding-left: 19px;
        }

        .formstyle {
            float: left;
            width: 100%;
        }

        input {
            text-align: center;
            padding-top: 12px;
        }
    </style>
</head>
<body>


<div class="main">

    <div class="header">
        <img src="${pageContext.request.contextPath}/_images/op.jpg" alt="ocean" width="100%" height="100%">
    </div>

    <div class="left">
        <%@ include file="/WEB-INF/const/left.jsp"%>
    </div>



    <div class="centr">

        <%--@elvariable id="searchResult" type="java.util.List"--%>
        <c:forEach var="user" items="${searchResult}">
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
                    <%--@elvariable id="contacts" type="java.util.List<MessageDirection>"--%>
                    <querry:contact user="${user}" contacts="${contacts}"/>
                </div>

            </div>

        </c:forEach>
    </div>


    <div class="right">
        <%@ include file="/WEB-INF/const/right.jsp"%>
    </div>

</div>



</body>
</html>
