<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr>
        <th>Description</th>
        <th>Date and time</th>
        <th>Calories</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr <c:if test="${meal.exceed}">style="color: red"</c:if>>
            <td>${meal.description}"</td>
            <td><c:set var="dateTime" value="${meal.dateTime}"/>${fn:replace(dateTime, 'T', ' ')}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
