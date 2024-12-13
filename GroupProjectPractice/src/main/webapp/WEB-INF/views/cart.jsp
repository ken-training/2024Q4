<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
.container {
    width: 80%;        /* 幅の設定 */
    margin: 0 auto;    /* 中央寄せ */

}

</style>
<title>買い物かご</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	<form action="${pageContext.request.contextPath}/cart" method="post">
		<div class="cart-items">
			<h3>カートに入れた商品</h3>
		<c:if test="${ !empty(cartList)}">
			<table>
				<tr>
					<th></th>
					<th>商品名</th>
					<th>単価(税抜)</th>
					<th>数量</th>
				</tr>
				<c:forEach var="item" items="${ cartList }">
					<tr>
						<td><img src="resources/img/${item.image}.png" alt="${item.product_name }"
								  width="130" height="100"></td>
						<td>
						${item.product_name }
						</td>
						<td>\ <fmt:formatNumber value="${item.price }" pattern="#,###" /></td>
						<td>
							<input type="hidden" name="productId" value="${item.product_id }">
							<select name="quantity">
								<c:forEach var="n" begin="0" end="10">
									<option value="${n}" <c:if test="${n == item.count}">selected</c:if>>
									${n}
									</option>
								</c:forEach>
							</select>個
						</td>
					</tr>
				</c:forEach>
				<tr>
				</tr>
			</table>

		</c:if>
		<c:if test="${empty(cartList)}">
			<p><c:out value="${message }"></c:out>
			</p>
		</c:if>
		</div>
			<input type="submit" value="更新">
	</form>
		<div class="cart-summary">
			<p>商品合計数 :  ${total_qty }</p>
			<p>合計金額(税抜) : ￥ <fmt:formatNumber value="${total_amount }" pattern="#,###" /></p>
			<button onclick="window.location.href='${pageContext.request.contextPath}/order'">購入に進む</button>
		</div>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>
