<%--
  Created by IntelliJ IDEA.
  User: Narek
  Date: 09.03.2017
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<table width="450" border="0" cellpadding="0" cellspacing="15">
    <tr>
        <td colspan="2" align="center"><strong><font color="blue" size="10"> Secret Surf</font> </strong></td>
    </tr>
    <tr>
        <td>
            <img src="/test/Борд2.jpg" width="200" height="200" alt="Это я" title="This is I">
        </td>
        <td>
            <table width="300" border="1" align="left" cellpadding="15" cellspacing="0">
                <tr>
                    <th>Имя</th>
                    <th>Телефон</th>
                </tr>
                <tr>
                    <td>Петр</td>
                    <td>3242341</td>
                </tr>
                <tr>
                    <td>Иван</td>
                    <td>3243541</td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <img src="/test/sun.jpg" width="100" height="100">
        </td>
        <td>
            <ol>
                <li>Перечисление 1</li>
                <li>Перечисление 2</li>
                <li>Перечисление 3</li>
            </ol>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <p align="center">Надпись снизу документа 2017</p>
        </td>
    </tr>
</table>

<%--<p>Hello Surfer Welcome!</p> <hr/>--%>

<%--Это наши users:--%>
<%--<table>--%>
    <%--<tr>--%>
        <%--<th>first</th>--%>
        <%--<th>last</th>--%>
        <%--<th>Date of Birth</th>--%>
        <%--<th>email</th>--%>
        <%--<th>password</th>--%>
    <%--</tr>--%>
    <%--<c:forEach var="elem" items="${list}">--%>
        <%--<tr>--%>
            <%--<td>${elem.firstName}</td>--%>
            <%--<td>${elem.lastName}</td>--%>
            <%--<td>${elem.dateOfBirth}</td>--%>
            <%--<td>${elem.email}</td>--%>
            <%--<td>${elem.passwordHash}</td>--%>
        <%--</tr>--%>
    <%--</c:forEach>--%>
<%--</table>--%>



<%--Depricated--%>
<%--<%--%>
<%--Collection<User> users = (Collection<User>) request.getAttribute(WelcomeController.ALL_Users_KEY);--%>
<%--%>--%>

<%--Это наши users:--%>
<%--<table>--%>
<%--<tr>--%>
<%--<th>first</th>--%>
<%--<th>last</th>--%>
<%--<th>Date of Birth</th>--%>
<%--<th>email</th>--%>
<%--<th>password</th>--%>
<%--</tr>--%>
<%--<%--%>
<%--for (User user : users) {--%>
<%--%>--%>
<%--<tr>--%>
<%--<td><%=user.getFirstName()%></td>--%>
<%--<td><%=user.getLastName()%></td>--%>
<%--<td><%=user.getDateOfBirth()%></td>--%>
<%--<td><%=user.getEmail()%></td>--%>
<%--<td><%=user.getPasswordHash()%></td>--%>
<%--</tr>--%>
<%--<%--%>
<%--}--%>
<%--%>--%>
<%--</table>--%>





</body>
</html>
