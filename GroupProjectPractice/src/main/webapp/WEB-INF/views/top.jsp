<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>TOP</title>
</head>
<body>
    <main>
       <div>
	       <h2>セール商品一覧</h2>
	       <table>
	       	<tr>
				<c:forEach var="saleProduct" items="${saleProductsList}">
		       		<td width="300px" style="border: solid 1px">
		       			<div class="product_link" style="cursor: pointer;">
				       		<img alt="セール品" src="resources/img/${saleProduct.image}.png" style="width:100px;">
			       			<div style="display: inline-block;">
			       				<p><c:out value="${saleProduct.product_name}" /></p>
			       				<p>単価(税抜) \<c:out value="${saleProduct.price}" /></p>
			       			</div>
		       			</div>
		       		</td>
		    	</c:forEach>
			</tr>
			</table>
		</div>

		<div>
			<h2>商品一覧</h2>
			<table>
				<c:forEach var="allProduct" items="${allProductsList}">
			       	<td width="300px" style="border: solid 1px" onclick="">
			       		<div class="product_link" style="cursor: pointer;">
				       		<img alt="商品" src="resources/img/${allProduct.image}.png" style="width:100px;">
				       		<div style="display: inline-block;">
				       			<p><c:out value="${allProduct.product_name}" /></p>
				       			<p>単価(税抜) \<c:out value="${allProduct.price}" /></p>
				       		</div>
			       		</div>
			       	</td>
			    </c:forEach>
			</table>
       </div>
    </main>
</body>
</html>
