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
header{
	z-index: 1000; /* 最前面に固定 */
}
body{
	margin: 0;
	padding: 0;
}
h1{
	font-size: 30px;
    margin-bottom: 20px;
    text-align: center;
}
.container {
    width: 80%;        /* 幅の設定 */
    max-width: 1000px;
    margin: 0 auto;    /* 中央寄せ */
	padding: 20px;
	margin-top: 10px; /* ヘッダー分の余白 */
}
.cart-items table{
	width:  calc(100% - 200px); /* 300px のボタン幅 + 少し余白を持たせる */
    border-collapse: collapse;
    margin-top: 20px;
    margin-right: 20px; 		/* 右側の余白 */
}
.cart-items table th{
	padding: 10px;
	text-align: center;
	font-weight: bold;
}
.cart-items table tr:first-child {
	width: 200px;
    font-weight: bold;
    text-align: center;
    border-bottom: 1px solid #2c2c2c;

}
.cart-items table tr:not(:first-child) {
	width: 200px;
    font-weight: bold;
    text-align: center;
    margin-top: 10px;
}
.cart-items table td.product-name {
    text-align: left;  /* 商品名だけ左寄せに */
}
/* 右上に固定されるカート関連のボタン */
.cart-summary {
    position: fixed;
    top: 100px;      /* ヘッダーより少し下に配置 */
    right: 10px;     /* 右端に配置 */
    z-index: 9999;   /* 最前面に表示 */
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    width: 250px;
    font-size: 14px;
}

.cart-summary button {
    display: block;
    width: 100%;
    padding: 10px 0;
    background-color: #494949;
    color: white;
    font-size: 16px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}
.cart-summary input[type="submit"],
.cart-summary p {
    margin-bottom: 10px;
}

</style>
<title>買い物かご</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	<form action="/project/cart" method="post">
		<div class="cart-items">
			<h3>カートに入れた商品</h3>
		<c:if test="${ !empty(cartList)}">
			<table>
				<tr>
					<th colspan="2">商品名</th>
					<th>単価(税抜)</th>
					<th>数量</th>
				</tr>
				<c:forEach var="item" items="${ cartList }">
					<tr>
						<td><img src="resources/img/${item.image}.png" alt="${item.product_name }"
								  width="130" height="100"></td>
						<td class="product-name">
							<c:out value="${item.product_name }"/>
						</td>
						<td>
							<c:if test="${item.getDiscnt_is_valid() == 0 }">
								\ <fmt:formatNumber value="${item.price }" pattern="#,###" /><small>(税抜)</small>
							</c:if>
							<c:if test="${item.getDiscnt_is_valid() == 1 }">
	                           <div>
	                               <p class="product_message"  id="price" data-discount-rate="${item.getDiscnt_rate()}" data-price="${item.price}">
	                                   <del>¥ <fmt:formatNumber value="${item.price}" pattern="#,###" /> <small>(税抜)</small></del>
	                                   <span class="discount-price"></span>
	                               </p>
	                           </div>
							</c:if>
						</td>
						<td>
							<input type="hidden" name="productId" value="${item.product_id }">
							<select name="quantity">
								<c:forEach var="n" begin="0" end="${item.stock_qty }">
									<option value="${n}" <c:if test="${n == item.count}">selected</c:if>>
									<c:out value="${n}"/>
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


		<c:if test="${!empty(cartList)}">
		<div class="cart-summary">
	 		<input type="submit" value="更新" style="width: 100%; padding: 10px 0; background-color: #494949; color: white; font-size: 16px; border: none; border-radius: 5px; cursor: pointer;">
			<p>商品合計数 :  <c:out value="${total_qty }"/></p>
			<p>合計金額(税抜) : ￥ <fmt:formatNumber value="${total_amount }" pattern="#,###" /></p>
			<a href="/project/order">
    			<button type="button" style="width: 100%; padding: 10px 0; background-color: #494949; color: white; font-size: 16px; border: none; border-radius: 5px;">
        		購入に進む
    			</button>
			</a>
			<p><button type="button" onclick="window.location.href='/project/cart/empty'">カートを空にする</button></p>
		</div>
		</c:if>
		</div>
	</form>
	<jsp:include page="footer.jsp"/>
</body>
</html>
