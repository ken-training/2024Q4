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
	padding: 10px;
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
.cart-items table th{
    font-weight: bold;
    text-align: center;
    border-top: 2px solid #2c2c2c;
    border-bottom: 2px solid #2c2c2c;

}
.cart-items table tr {
    font-weight: bold;
    text-align: center;
    border-bottom: 1px solid #ccc;
}
.cart-items table tr:last-child{
	border: none;
}
.cart-items table td {
    padding: 20px;
}
.cart-items table td.product-name {
    text-align: left;  /* 商品名だけ左寄せに */
}
/* 右上に固定されるカート関連のボタン */
.cart-summary {
    position: fixed;
    top: 175px;      /* ヘッダーより少し下に配置 */
    right: 10px;     /* 右端に配置 */
    z-index: 9999;   /* 最前面に表示 */
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
    width: 20vw;
    font-size: 16px;
    text-align: center;
}

.cart-summary .button {
    width: 75%;
}
.cart-summary .button,
.cart-summary p {
    margin: 15px;
}
.cart-summary p {
    text-align: left;
}

/* マウスを置いた時に文字色を赤に変更 */
.product-name:hover {
    color: #007bff;
}

</style>
<title>買い物かご</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	<form action="/project/cart" method="post">
		<div class="cart-items">
			<h2>カート</h2>
		<c:if test="${ !empty(cartList)}">
			<table>
				<tr>
					<th colspan="2">商品名</th>
					<th>単価(税抜)</th>
					<th>数量</th>
				</tr>
				<c:forEach var="item" items="${ cartList }">
					<tr>
						<td><a href="/project/product?product_id=${item.product_id }"><img src="resources/img/${item.image}.png" alt="${item.product_name }"
								  width="130"></a></td>
						<td class="product-name">
							<a href="/project/product?product_id=${item.product_id }"><c:out value="${item.product_name }"/></a>
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
							<select name="quantity" class="numselect">
								<c:forEach var="n" begin="0" end="${item.stock_qty }">
									<option value="${n}" <c:if test="${n == item.count}">selected</c:if>>
									<c:out value="${n}"/>
									</option>
								</c:forEach>
							</select>&nbsp;点
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${empty(cartList)}">
			<p><c:out value="${message }"></c:out>
			</p>
		</c:if>
		</div>


		<c:if test="${!empty(cartList)}">
		<div class="cart-summary">
	 		<input type="submit" value="更新" class="button">
	 		<div style="margin: 10% 0; border-bottom:1px solid #ccc;">
	 			<p>商品合計数 :  <c:out value="${total_qty }"/>点</p>
				<p>合計金額(税抜) : ￥ <fmt:formatNumber value="${total_amount }" pattern="#,###" /></p>
	 		</div>

			<a href="/project/order">
    			<button type="button" class="button">
        		購入に進む
    			</button>
			</a>
			<button type="button" class="button" onclick="window.location.href='/project/cart/empty'">カートを空にする</button>
		</div>
		</c:if>
		</div>
	</form>
	<jsp:include page="footer.jsp"/>
</body>
</html>
