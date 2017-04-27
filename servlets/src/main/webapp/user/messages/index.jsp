<%--@elvariable id="user" type="model.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="querry" uri="/WEB-INF/surfland.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
<html>
<head>
    <title>SL Messages</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <style>
        .layer {
            overflow: auto;
            width: 725px;
            resize: none;
            height: 325px;
            padding: 17px;
            border: solid 1px black;
            margin: auto;
            word-wrap: break-word;
        }
        .rmsg {
            width: 46%;
            height: auto;
            float: right;
            clear: both;
            font-size: 14px;
            text-align: right;
        }
        .lmsg{
            width: 46%;
            height: auto;
            float: left;
            clear: both;
            font-size: 14px;
            text-align: left;
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


    <div class="centr">

        <c:choose>
            <c:when test="${user != null}">

                <div class="txt" style="text-align: center">
                    <p><fmt:message key="your_chat_with"/> <a href="user?id=${user.id}">${user.firstName} ${user.lastName}</a></p>



                    <div class="layer">
                        <querry:message user="${user}"/>
                    </div>

                    <form action="/message" method="post">
                        <input type="hidden" name="user_to" value="${user.id}">
                        <p><textarea name="msg"
                                     style="resize: none; " maxlength="2000" rows="5" cols="105"></textarea></p>
                        <p><input type="submit" name="submit" value="Send" style="height: 35px; width: 150px;">
                            <input type="submit" name="submit" value="Update" style="height: 35px; width: 150px; float: right;"></p>
                    </form>
                </div>

            </c:when>

            <c:when test="${user == null}">
                    <h3 style="color: red; text-align: center"><fmt:message key="acc_not_found"/> </h3>
            </c:when>

        </c:choose>



    </div>


    <div class="right">
        <%@ include file="/WEB-INF/const/right.jsp"%>
    </div>



</div>
</body>
</html>
