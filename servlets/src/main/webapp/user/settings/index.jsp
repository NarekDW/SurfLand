<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; utf-8">
    <title>SL settings</title>
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
    <form style="text-align: center; line-height: 40px" action="/update" method="post">
        <p><fmt:message key="first_name"/> : <input type="text" name="first_name" value="${param.first_name}"/></p>
        <p><fmt:message key="last_name"/> : <input type="text" name="last_name" value="${param.last_name}"/></p>

        <p><fmt:message key="country"/> : <input type="text" name="country" value="${param.country}"/></p>
        <p><fmt:message key="city"/> : <input type="text" name="city" value="${param.city}"/></p>
        <p><fmt:message key="next_trip"/> : <input type="text" name="next_trip" value="${param.next_trip}"/></p>

        <p><fmt:message key="date_of_birth"/> : <input type="date" name="date_of_birth" value="${param.date_of_birth}"/></p>
        <p><fmt:message key="sex"/> :
            <select name="sex">
                <option></option>
                <option value="male" ${param.sex == "male"  ? 'selected="selected"' : ''}>Male</option>
                <option value="female" ${param.sex == "female"  ? 'selected="selected"' : ''}>Female</option>
            </select><br/>
        </p>

        <p><input type="submit" value="Update"></p>
    </form>

    <%--@elvariable id="errors" type="java.util.List"--%>
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
