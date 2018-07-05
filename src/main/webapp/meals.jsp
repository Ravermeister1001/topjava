<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<a href="meal.jsp">Add meal</a>
<table>
    <tr>
        <th>Id</th>
        <th>Description</th>
        <th>Date and time</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <tr <c:if test="${meal.exceed}">style="color: red"</c:if>>
            <td>${meal.id}</td>
            <td>${meal.description}</td>
            <td><c:set var="dateTime" value="${meal.dateTime}"/>${fn:replace(dateTime, 'T', ' ')}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=delete&id=${meal.id}"/>Delete</td>
            <td><a href="meals?action=edit&id=${meal.id}"/>Edit</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
