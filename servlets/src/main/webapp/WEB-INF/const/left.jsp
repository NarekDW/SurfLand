<%--
  Created by IntelliJ IDEA.
  User: Narek
  Date: 23.03.2017
  Time: 2:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%--@elvariable id="count" type="services.FollowersFriendsCount"--%>
<%--@elvariable id="msgcount" type="java.lang.Integer"--%>
<h3><a href="${pageContext.request.contextPath}/search" style="color: blue; text-decoration: none">
    <fmt:message key="search_user"/>
</a></h3>
<h3><a href="${pageContext.request.contextPath}/mycontacts" style="color: blue; text-decoration: none">
    <fmt:message key="my_messages"/>
    <c:if test="${msgcount != null and msgcount != 0}">+${msgcount}</c:if></a></h3>
<h3><a href="${pageContext.request.contextPath}/mynews" style="color: blue; text-decoration: none">
    <fmt:message key="my_news"/>
</a></h3>
<h3><a href="${pageContext.request.contextPath}/allnews" style="color: blue; text-decoration: none">
    <fmt:message key="all_news"/>
</a></h3>

<h3><a href="${pageContext.request.contextPath}/myfriends" style="color: blue; text-decoration: none">
    <fmt:message key="my_friends"/>
    <c:if test="${count.friends != null and count.friends != 0}"> ${count.friends}</c:if></a></h3>
<h3><a href="${pageContext.request.contextPath}/followers" style="color: blue; text-decoration: none">
    <fmt:message key="my_followers"/>
    <c:if test="${count.followers != null and count.followers != 0}">+${count.followers}</c:if></a></h3>

<h3><a href="${pageContext.request.contextPath}/follow" style="color: blue; text-decoration: none">
    <fmt:message key="i_follow"/>
</a></h3>

</body>
</html>
