<!DOCTYPE html>

<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/mytaglib.tld" prefix="mytag" %>

<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="libs/bootstrap-4.1.3/css/bootstrap.min.css">
    <mytag:setLocale value="${sessionScope.locale}"/>
    <mytag:setBundle basename="i18n/messages" var="message"/>
    <title><mytag:message key="main_page" bundle="message"/></title>
</head>

<body>
<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-md-auto">
            <h1><mytag:message key="main_page" bundle="message"/></h1>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <div class="row">
                <div class="col-12">
                    <h3><mytag:message key="autorization" bundle="message"/></h3>
                </div>
            </div>
            <div class="row">
                <form class="col-12" action="/login" method="post">
                    <div class="form-group">
                        <label for="inputLogin">
                            <mytag:message key="Login" bundle="message"/>
                        </label>
                        <input name="login" type="text" class="form-control" id="inputLogin"
                               placeholder=
                                   <mytag:message key="input_login" bundle="message"
                                           quoted="true"/>>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">
                            <mytag:message key="password" bundle="message"/>
                        </label>
                        <input name="password" type="password" class="form-control"
                               id="inputPassword"
                               placeholder=
                                   <mytag:message key="input_password" bundle="message"
                                           quoted="true"/>>
                    </div>
                    <button type="submit" class="btn btn-primary">
                        <mytag:message key="loginBtn" bundle="message"/>
                    </button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            <form class="col-12" action="/profile" method="get">
                <button type="submit" class="btn btn-primary">
                    <mytag:message key="look_user_tasks" bundle="message"/>
                </button>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<script type="text/javascript" src="libs/jquery-3.3.1/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="libs/propper-1.11.0/popper.min.js"></script>
<script type="text/javascript" src="libs/bootstrap-4.1.3/js/bootstrap.min.js"></script>

</body>
</html>
