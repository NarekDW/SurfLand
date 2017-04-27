<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
<html>
<head>
    <title>News</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <style>
        .newsuser {
            width: 60%;
            float: left;
            padding-left: 70px;
            font-style: italic;
            padding-top: 50px;
        }
        .newsmsg{
            width: 100%;
            text-align: center;
            float: left;
            font-style: italic;
            font-size: 20px;
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

        <form style="width: 100%" action="/allnews">
            <input style="float: left; width: 35%; text-align: center;
                          margin-right: 40px;" type="text" placeholder="news" name="news"/>
            <input style="float: left; width: 20%"
                   type="submit" name="Search" value="Search"/>
        </form>
        <%--@elvariable id="resultNews" type="java.util.List<services.UserAndNews>"--%>
        <c:forEach var="current" items="${resultNews}">

            <div class="newsuser">
                <a style="font-size: 19px;" href="user?id=${current.user.id}">
                        ${current.user.firstName} ${current.user.lastName}</a>
                <label style="color: red;"> ${current.news.date} ${current.news.time} </label>
                <c:if test="${current.user.address.country != null}">
                    <h4 style="height: 10px; font-size: 13px;">Country: ${current.user.address.country}</h4>
                </c:if>
                <c:if test="${current.user.address.city != null}">
                    <h4 style="height: 15px; font-size: 13px;">City: ${current.user.address.city}</h4>
                </c:if>
            </div>

            <div class="newsmsg">
                    ${current.news.message}
                <hr/>
            </div>


        </c:forEach>
    </div>


    <div class="right">
        <%@ include file="/WEB-INF/const/right.jsp"%>
    </div>

</div>

</body>
</html>
