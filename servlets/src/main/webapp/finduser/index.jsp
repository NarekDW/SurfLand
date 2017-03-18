<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Users</title>
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
            border: double;
        }

        .txt {
            width: 35%;
            height: 98%;
            float: left;
            border: double;
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
        <img src="${pageContext.request.contextPath}/_images/header.jpg" alt="ocean" width="100%" height="100%">
    </div>

    <div class="left">
        <form action="${pageContext.request.contextPath}/search" method="get">
            <div class="formstyle">
                <%--param -дает доступ к предыдущему значению поля по имени из той же jsp  --%>
                <p>Поиск по имени:
                <input type="text" placeholder="name" name="name" value="${param.name}"></p>
                <p>Страна:
                <input type="text" placeholder="country" name="country" value="${param.country}"></p>
                <p>Город:
                <input type="text" placeholder="city" name="city" value="${param.city}"></p>
                <p>Пол:
                <select style="width: 85%; height: 6%; padding-left: 32%" name="sex">
                    <option value="none" ${param.sex == "none"  ? 'selected="selected"' : ''}></option>
                    <option value="male" ${param.sex == "male"  ? 'selected="selected"' : ''}>Male</option>
                    <option value="female" ${param.sex == "female"  ? 'selected="selected"' : ''}>Female</option>
                </select>
                </p>
                <p>Следующая поездка:
                <input type="text" placeholder="next trip" name="next_trip" value="${param.next_trip}"></p>

                <p>Возраст:<br/>
                <input style="width: 20%" type="number" placeholder="от" name="agefrom" value="${param.agefrom}"> -
                <input style="width: 20%" type="number" placeholder="до" name="ageto" value="${param.ageto}"></p>

                <p>Сортировка по:
                    <select style="width: 85%; height: 6%; padding-left: 32%" name="sort">
                        <option value="namesort" ${param.sort == "namesort"  ? 'selected="selected"' : ''}>Имени</option>
                        <option value="agesort" ${param.sort == "agesort"  ? 'selected="selected"' : ''}>Возрасту</option>
                    </select>

            </div>
            <input type="submit" value="Найти">
        </form>
    </div>


    <div class="centr">

        <%--@elvariable id="searchResult" type="java.util.List"--%>
        <c:forEach var="user" items="${searchResult}">
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

</div>

</body>
</html>
