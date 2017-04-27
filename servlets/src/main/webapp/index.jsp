<%--@elvariable id="errorLogin" type="java.util.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language.loc"/>
<html>
<head>
    <meta http-equiv="CONTENT-TYPE" content="text/html; utf-8">

    <title>SL</title>
    <link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="main">

        <div class="abzac">Welcome to SurfLand</div>
        <div class="small_picture">
<%--suppress CheckImageSize --%>
            <img src="_images/title.jpg" alt="surfer" width="450" height="400">

            <div style="text-align: center">
                <p><a href="/language?lang=russian">Русский</a></p>
                <p><a href="/language?lang=english">English</a></p>
            </div>

        </div>
        <div class="big_column">
                <form name="Login" method="POST" action="/login">

                    <fmt:message key="login"/> <br/>

                    <input style="line-height: 18px; text-align: center;"
                           type="email" name="j_username" placeholder="e-mail" value="${param.j_username}"/>

                    <br/><fmt:message key="password"/> <br/>

                    <input style="line-height: 18px; text-align: center;"
                           type="password" name="j_password" placeholder="password"/>
                    <br/><br/>
                    <input style="height: 25px; width: 100px;" type="submit" value="Log in"/>
                    <input style="height: 25px; width: 100px;" type="submit" formaction="/registration"
                           value="Registration"/>
                </form>
            <div style="color: maroon">
                <ol>
                    <li><fmt:message key="p1"/></li>
                    <li><fmt:message key="p2"/></li>
                    <li><fmt:message key="p3"/></li>
                </ol>
            </div>
            <div style="color: red">${errorLogin}</div>
        </div>
        <div class="footer">Учебная социальная сеть SL</div>
    </div>

</body>
</html>
