<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="querry" uri="/WEB-INF/surfland.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
<html>
<head>
    <title>My followers</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
    <style>
        .userscope {
            width: 90%;
            height: 28%;
            float: left;
        }
        .txt {
            width: 35%;
            height: 98%;
            float: left;
            /*border: double;*/
            padding-left: 19px;
        }
        .pic {
            width: 18%;
            height: 98%;
            float: left;
            /*border: double;*/
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
   <%--@elvariable id="allfollowes" type="java.util.List<User>"--%>
   <querry:followers users="${allfollowes}"/>
</div>


<div class="right">
    <%@ include file="/WEB-INF/const/right.jsp"%>
</div>


</body>
</html>
