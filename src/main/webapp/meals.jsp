<%--
  Created by IntelliJ IDEA.
  User: tweek
  Date: 10.02.2021
  Time: 19:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="javatime" uri="http://sargue.net/jsptags/time" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
<table border="3" >
    <tr align="center">
        <td>Date</td>
        <td>Description</td>
        <td>Calories</td>
    </tr>
    <c:forEach items="${mealList}" var="meal">
    <c:choose>
        <c:when test="${meal.excess}">
            <tr align="center" style="color:#DC143C">
                <td><javatime:format value="${meal.dateTime}" style="MS" /></td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:when>
        <c:otherwise>
            <tr align="center" style="color:#32CD32">
                <td><javatime:format value="${meal.dateTime}" style="MS" /></td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>

        </c:otherwise>
    </c:choose>
    </c:forEach>
</table>
</body>
</html>
