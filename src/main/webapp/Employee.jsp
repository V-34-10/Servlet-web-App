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
    <title><mytag:message key="employee" bundle="message"/></title>
</head>

<body>
<c:if test="${sessionScope.user.permissionName == 'admin'}">
    <div class="container">
        <div class="row justify-content-md-center">
            <div class="col-md-auto">
                <h1><mytag:message key="employee" bundle="message"/></h1>
            </div>
        </div>
        <form method="POST" action='/employee' name="frmAddEmployee" accept-charset="utf-8">
            <input type="hidden" name="employeeId" value="<c:out value="${employee.employeeId}"/>">
            <input type="hidden" name="action" value="<c:out value="${action}"/>">
            <div class="form-group">
                <label for="firstName">
                    <mytag:message key="first_name" bundle="message"/>
                </label>
                <input type="text" class="form-control" id="firstName" name="firstName"
                       value="<c:out value="${employee.firstName}"/>"
                       placeholder=
                           <mytag:message key="input_first_name" bundle="message" quoted="true"/>
                >
            </div>
            <div class="form-group">
                <label for="lastName">
                    <mytag:message key="last_name" bundle="message"/>
                </label>
                <input type="text" class="form-control" id="lastName" name="lastName"
                       value="<c:out value="${employee.lastName}"/>"
                       placeholder=
                           <mytag:message key="input_last_name" bundle="message" quoted="true"/>
                >
            </div>
            <div class="form-group">
                <label class="mr-sm-2" for="positionId">
                    <mytag:message key="position" bundle="message"/>
                </label>
                <select class="form-control" id="positionId" name="positionId">
                    <c:forEach var="position" items="${positions}">
                        <option value="<c:out value="${position.positionId}"/>"
                                <c:if test="${position.positionId == employee.positionId}"> selected </c:if> >
                            <c:out value="${position.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label class="mr-sm-2" for="educationLevelId">
                    <mytag:message key="education_level" bundle="message"/>
                </label>
                <select class="form-control" id="educationLevelId" name="educationLevelId">
                    <c:forEach var="educationLevel" items="${educationLevels}">
                        <option value="<c:out value="${educationLevel.educationLevelId}"/>"
                                <c:if test="${educationLevel.educationLevelId == employee.educationLevelId}"> selected </c:if> >
                            <c:out value="${educationLevel.name}"/>
                        </option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">
                <mytag:message key="Submit" bundle="message"/>
            </button>
        </form>
    </div>
</c:if>
</body>
</html>