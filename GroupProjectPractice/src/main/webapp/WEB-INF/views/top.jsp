<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>TOP</title>
</head>
<body>
    <main>
       <h2>商品一覧</h2>
		<c:forEach var="allProduct" items="${allProductsList}">
        	<div><c:out value="${allProduct.product_name}"></c:out></div>
    	</c:forEach>
    </main>
</body>
</html>
