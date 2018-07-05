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
<h2>Edit meal</h2>
<form method="post" action="meals" name="Add User">
    Meal id: <input type="text" readonly="readonly" name="id" value="${meal.id}"/><br/>
    Date and time: <input type="datetime-local" name="dateTime" value="${meal.dateTime}"/><br/>
    Description: <input type="text" name="description" value="${meal.description}"/><br/>
    Calories: <input type="text" name="calories" value="${meal.calories}"/><br/>
    <input type="submit" value="submit"/>
</form>
</body>
</html>
