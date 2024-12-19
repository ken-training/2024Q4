<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>注文履歴</title>
<style>
/* グレーアウト用のCSSクラス */
.grayout {
	background-color: #f0f0f0; /* グレーの背景色 */
	color: #a0a0a0; /* グレーの文字色 */
	}
/* マウスを置いた時に文字色を赤に変更 */
.product-name:hover {
    color: #007bff;
}
</style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<p><c:out value="${message }"/></p>
<h3>注文履歴</h3>
<a href="/project/account">マイページに戻る</a>
    <c:if test="${ !empty(orderlogList)}">
		<table>
			<tr>
				<th>注文詳細ID</th>
				<th>注文ID</th>
				<th>注文日</th>
				<th>商品ID</th>
				<th>商品名</th>
				<th>数量</th>
				<th>合計金額</th>
				<th>利用ポイント</th>
				<!-- <th>会員ID</th> -->
				<th>お届け先氏名</th>
				<th>お届け先住所</th>
				<th>支払い方法</th>
				<th>発送日</th>
				<th>ステータス</th>
				<th></th>
			</tr>
			<c:forEach var="item" items="${ orderlogList }">
				<form action="/project/orderlog/edit" method="post">
					<tr class="<c:if test='${item.status == 2}'>grayout</c:if>">
						<td><c:out value="${item.order_detail_id }"/></td>
						<td><c:out value="${item.order_id }"/></td>
						<td><c:out value="${item.order_date }"/></td>
						<td><c:out value="${item.product_id }"/></td>
						<td class="product-name">
							<a href="/project/product?product_id=${item.product_id }">
								<c:out value="${item.product_name }"/>
							</a>
						</td>
						<td><c:out value="${item.product_num }"/></td>
						<td><c:out value="${item.temp_amount }"/></td>
						<td><c:out value="${item.use_points }"/></td>
						<%-- <td><c:out value="${item.customer_id }"/></td> --%>
						<td><c:out value="${item.ship_name }"/></td>
						<td><c:out value="${item.ship_address }"/></td>
						<td><c:out value="${item.payment_methods }"/></td>
						<td><c:out value="${item.ship_date }"/></td>
						<td><c:out value="${statusList[item.status]}"/></td>
						<td>
						<c:if test="${item.status == 0 }">
							<input type="hidden" name="order_detail_id" value="${item.order_detail_id }">
							<input type="hidden" name="status" value="注文取消">
							<input type="submit" value="注文キャンセル">
						</c:if>
						</td>
					</tr>
				</form>
			</c:forEach>
			<tr>
			</tr>
		</table>
	</c:if>
	<jsp:include page="footer.jsp"/>
</body>
</html>
