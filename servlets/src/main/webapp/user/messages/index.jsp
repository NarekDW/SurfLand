<%--@elvariable id="user" type="model.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SL Messages</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
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

        <div class="txt" style="text-align: center">
            <p>Write your message to <a href="user?id=${user.id}">${user.id} ${user.firstName} ${user.lastName}</a></p>

            <form action="handler.php">
                <p>Chat</p>
                <p><textarea name="comment" readonly style="resize: none"
                             maxlength="20000" rows="20" cols="105"></textarea></p>
            </form>


            <form action="/message" method="post">
                <input type="hidden" name="user_to" value="${user.id}">
                <p><textarea name="msg"
                             style="resize: none; " maxlength="2000" rows="5" cols="105"></textarea></p>
                <p><input type="submit" value="Send" style="height: 35px; width: 150px;"></p>
            </form>
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
