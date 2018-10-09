<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<table border=1>
    <thead>
    <tr>
        <th>Date Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan="2">Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr>
            <c:set var="fontColor" scope="session"
                   value="${meal.exceed ? 'color:red' : 'color:green'}"/>
            <javatime:format value="${meal.dateTime}"
                             pattern="yyyy-MM-dd HH:mm" var="formattedDate"/>
            <form method="post"
                  action='meals/update?id=<c:out value="${meal.id}"/>'
                  name="updateMealForm">
                <td><input style="${fontColor}"
                           type="datetime" name="dateTime"
                           value="<c:out value="${formattedDate}" />"/>
                </td>
                <td><input style="${fontColor}"
                           type="text" name="description"
                           value="<c:out value="${meal.description}" />"/></td>
                <td><input style="${fontColor}"
                           type="text" name="calories"
                           value="<c:out value="${meal.calories}" />"/></td>
                <td><input type="submit" name="update" value="Update"/></td>
            </form>
            <form method="post"
                  action='meals/delete?id=<c:out value="${meal.id}"/>'>
                <td><input type="submit" name="delete" value="Delete"/></td>
            </form>
        </tr>
    </c:forEach>
    <tr>
        <form method="post" action='meals/add' name="addMealForm">
            <td><input type="datetime" name="dateTime"
                       value="<c:out value="${meal.dateTime}" />"/>
            </td>
            <td><input type="text" name="description"
                       value="<c:out value="${meal.description}" />"/></td>
            <td><input type="text" name="calories"
                       value="<c:out value="${meal.calories}" />"/></td>
            <td><input type="submit" name="add" value="Add"/></td>
        </form>
    </tr>
    </tbody>
</table>
</body>
</html>