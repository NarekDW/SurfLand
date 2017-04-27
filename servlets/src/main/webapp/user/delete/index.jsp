<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; utf-8">
    <title>SL Delete</title>
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css">
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
    <form style="text-align: center; line-height: 40px" action="/delete" method="post">
        <p><fmt:message key="delete_choose"/> :</p>

        <p><fmt:message key="country"/> : <input type="checkbox" name="country"/></p>
        <p><fmt:message key="city"/> : <input type="checkbox" name="city"/></p>
        <p><fmt:message key="date_of_birth"/>: <input type="checkbox" name="date_of_birth"/></p>
        <p><fmt:message key="next_trip"/> : <input type="checkbox" name="next_trip"/></p>

        <p><input type="submit" value="Delete parametr"></p>
    </form>
    <form style="text-align: center; padding-top: 42px" action="/delete" method="post">
        <p><input type="submit" name="delete_account" value="Delete account"></p>
    </form>

    <c:forEach var="error" items="${errors}">
        <p style="color: red; text-align: center">${error}<br/></p>
    </c:forEach>

</div>

<div class="right">
    <%@ include file="/WEB-INF/const/right.jsp"%>
</div>

</div>

</body>
</html>
