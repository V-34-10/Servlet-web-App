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
    <title><mytag:message key="employees" bundle="message"/></title>
</head>
<body>
<script>
  window.history.replaceState({}, document.title, "/employee");
</script>

<div class="container">
    <div class="row justify-content-md-center">
        <div class="col-md-auto">
            <h1><mytag:message key="list_of_employees" bundle="message"/></h1>
        </div>
    </div>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col"><mytag:message key="index" bundle="message"/></th>
            <th scope="col"><mytag:message key="last_name" bundle="message"/></th>
            <th scope="col"><mytag:message key="first_name" bundle="message"/></th>
            <th scope="col"><mytag:message key="position" bundle="message"/></th>
            <th scope="col"><mytag:message key="education_level" bundle="message"/></th>
            <c:if test="${sessionScope.user.permissionName == 'admin'}">
                <th scope="col" colspan=2><mytag:message key="action" bundle="message"/></th>
            </c:if>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="employee" varStatus="loop">
            <tr>
                <th scope="row"><c:out value="${loop.count}"/></th>
                <td><c:out value="${employee.lastName}"/></td>
                <td><c:out value="${employee.firstName}"/></td>
                <td><c:out value="${employee.positionName}"/></td>
                <td><c:out value="${employee.educationLevelName}"/></td>
                <c:if test="${sessionScope.user.permissionName == 'admin'}">
                    <td><a href="/employee?action=edit&id=<c:out value="${employee.employeeId}"/>">
                        <mytag:message key="update" bundle="message"/></a>
                    </td>
                    <td>
                        <a href="/employee?action=delete&id=<c:out value="${employee.employeeId}"/>">
                            <mytag:message key="delete" bundle="message"/></a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${sessionScope.user.permissionName == 'admin'}">
        <form class="col-6" method="GET" action='/employee' id="frmInsertEmployee"
              name="frmInsertEmployee"
              accept-charset="utf-8">
            <input type="hidden" name="action" value="insert">
        </form>
    </c:if>
    <form class="col-6" action="/profile" method="get" id="frmBack" name="frmBack">
    </form>
    <div style="float:left">
        <button type="submit" form="frmBack" class="btn btn-primary">
            <mytag:message key="back" bundle="message"/>
        </button>
        <c:if test="${sessionScope.user.permissionName == 'admin'}">
            <button type="submit" form="frmInsertEmployee" class="btn btn-primary">
                <mytag:message key="add_employee" bundle="message"/>
            </button>
        </c:if>
    </div>
</div>
</body>
</html>