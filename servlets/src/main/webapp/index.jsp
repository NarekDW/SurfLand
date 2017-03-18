
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        </div>
        <div class="big_column">
                <form name="Login" method="POST" action="user/">
                    Login:<br/>
                    <input style="line-height: 18px; text-align: center;"
                           type="text" name="j_username" placeholder="e-mail"/>
                    <br/>Password:<br/>
                    <input style="line-height: 18px; text-align: center;"
                           type="password" name="j_password" placeholder="password"/>
                    <br/><br/>
                    <input style="height: 25px; width: 100px;" type="submit" value="Log in"/>
                    <input style="height: 25px; width: 100px;" type="submit" formaction="/registration"
                           value="Registration"/>
                </form>
            <div style="color: maroon">
                <ol>
                    <li>Знакомьтесь с серферами всего мира</li>
                    <li>Делитесь фотографими и эмоциями</li>
                    <li>Путешествуйте вместе</li>
                </ol>
            </div>
        </div>
        <div style="color: red">${errorLogin}</div>
        <div class="footer">Учебная социальная сеть SL</div>
    </div>

</body>
</html>
