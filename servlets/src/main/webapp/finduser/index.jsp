<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="querry" uri="/WEB-INF/surfland.tld" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
<html>
<head>
    <title>All Users</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <style>
        .userscope {
            width: 100%;
            height: 28%;
            float: left;
        }

        .pic {
            width: 18%;
            height: 98%;
            float: left;
        }

        .txt {
            width: 35%;
            height: 98%;
            float: left;
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
        <form action="${pageContext.request.contextPath}/search" method="get">
            <div class="formstyle">
                <input type="hidden" name="do_search" value="do_search">
                <%--param -дает доступ к предыдущему значению поля по имени из той же jsp  --%>
                <p><fmt:message key="search_by_name"/> :
                    <input type="text" placeholder="name" name="name" value="${param.name}"></p>
                <p><fmt:message key="country"/> :
                    <input type="text" placeholder="country" name="country" value="${param.country}"></p>
                <p><fmt:message key="city"/> :
                    <input type="text" placeholder="city" name="city" value="${param.city}"></p>
                <p><fmt:message key="sex"/> :
                    <select style="width: 85%; height: 6%; padding-left: 32%" name="sex">
                        <option value="none" ${param.sex == "none"  ? 'selected="selected"' : ''}></option>
                        <option value="male" ${param.sex == "male"  ? 'selected="selected"' : ''}>Male</option>
                        <option value="female" ${param.sex == "female"  ? 'selected="selected"' : ''}>Female</option>
                    </select>
                </p>
                <p><fmt:message key="next_trip"/> :
                    <input type="text" placeholder="next trip" name="next_trip" value="${param.next_trip}"></p>

                <p><fmt:message key="age"/> :<br/>
                    <input style="width: 20%" type="number" placeholder="от" name="agefrom" value="${param.agefrom}"> -
                    <input style="width: 20%" type="number" placeholder="до" name="ageto" value="${param.ageto}"></p>

                <p><fmt:message key="sort_by"/> :
                    <select style="width: 85%; height: 6%; padding-left: 32%" name="sort">
                        <option value="namesort" ${param.sort == "namesort"  ? 'selected="selected"' : ''}>Name
                        </option>
                        <option value="agesort" ${param.sort == "agesort"  ? 'selected="selected"' : ''}>Age
                        </option>
                    </select>

            </div>
            <input type="submit" value="Search">
        </form>
    </div>


    <div class="centr">

        <%--@elvariable id="searchResult" type="java.util.List"--%>
        <%--@elvariable id="page" type="java.lang.Integer"--%>
        <%--@elvariable id="totalItems" type="java.lang.Integer"--%>
        <%--@elvariable id="postfix" type="java.lang.String"--%>

            <div style="width: 100%; height: 10%; text-align: center;">
            ${querry:paggination(page, totalItems, 2, postfix)}
        </div>

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
                        <h4><fmt:message key="country"/> : ${user.address.city}</h4>
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

</div>

</body>
</html>
