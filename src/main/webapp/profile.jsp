<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/mytaglib.tld" prefix="mytag" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="libs/bootstrap-4.1.3/css/bootstrap.min.css">
    <mytag:setLocale value="${sessionScope.locale}"/>
    <mytag:setBundle basename="i18n/messages" var="message"/>
    <title><mytag:message key="user_profile" bundle="message"/></title>
</head>
<body>
<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-md-auto">
            <h1><mytag:message key="user_profile" bundle="message"/><br/></h1>
        </div>
    </div>
    <div class="col-6">
        <c:if test="${not empty sessionScope.user}">
            <c:set var="user" value="${sessionScope.user}"/>
            <div class="row">
                <div class="col-6">
                    <h5><mytag:message key="name" bundle="message"/>:</h5>
                </div>
                <div class="col-6">
                    <h3>${user.login}</h3>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <h5><mytag:message key="permission" bundle="message"/>:</h5>
                </div>
                <div class="col-6">
                    <h3>${user.permissionName}</h3>
                </div>
            </div>

            <div class="row">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a class="nav-link active" href="/employee">
                            <mytag:message key="employees" bundle="message"/></a>
                    </li>
                    <c:if test="${sessionScope.user.permissionName == 'personnel department'}">
                        <li class="nav-item">
                            <a class="nav-link active" href="/education-level">
                                <mytag:message key="education_level" bundle="message"/></a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </c:if>
    </div>
    <br/>
    <form class="col-6" method="GET" action='/login' id="frmLogout" name="frmLogout"
          accept-charset="utf-8">
        <button type="submit" class="btn btn-primary">
            <mytag:message key="Logout" bundle="message"/>
        </button>
    </form>
</div>

</body>
</html>
