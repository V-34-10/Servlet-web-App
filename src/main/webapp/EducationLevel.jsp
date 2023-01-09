<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/mytaglib.tld" prefix="mytag" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" type="text/css" href="libs/bootstrap-4.1.3/css/bootstrap.min.css">
    <mytag:setLocale value="${sessionScope.locale}"/>
    <mytag:setBundle basename="i18n/messages" var="message"/>
    <title><mytag:message key="education_level" bundle="message"/></title>
</head>

<body>
<c:if test="${sessionScope.user.permissionName == 'personnel department'}">
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="col-md-auto">
                <h1><mytag:message key="education_level" bundle="message"/></h1>
            </div>
        </div>
        <form method="POST" action='/education-level' name="frmAddLevelId" accept-charset="utf-8">
            <input type="hidden" name="educationLevelId"
                   value="<c:out value="${educationLevel.educationLevelId}"/>">
            <input type="hidden" name="action" value="<c:out value="${action}"/>">
            <div class="form-group">
                <label for="name">
                    <mytag:message key="first_name" bundle="message"/>
                </label>
                <input type="text" class="form-control" id="name" name="name"
                       value="<c:out value="${educationLevel.name}"/>"
                       placeholder=
                           <mytag:message key="input_first_name" bundle="message" quoted="true"/>
                >
            </div>

            <button type="submit" class="btn btn-primary">
                <mytag:message key="Submit" bundle="message"/>
            </button>
        </form>
    </div>
</c:if>
</body>
</html>