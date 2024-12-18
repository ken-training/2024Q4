<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>未発送注文履歴</title>
</head>
<body>
<p><c:out value="${message }"/></p>
<h3>未発送注文一覧</h3>
<a href="/project/empmenu">メニューに戻る</a>
<p><a href="/project/orderlog/shipped">発送済/注文取消注文履歴</a></p>
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
				<th>会員ID</th>
				<th>会員名</th>
				<th>発注情報ID</th>
				<th>お届け先氏名</th>
				<th>お届け先住所</th>
				<th>支払い方法</th>
			<!-- 	<th>発送日</th> -->
				<th>ステータス</th>
				<th></th>
			</tr>
			<c:forEach var="item" items="${ orderlogList }">
				<form action="/project/orderlog/edit" method="post">
					<tr>
						<td><c:out value="${item.order_detail_id }"/></td>
						<td><c:out value="${item.order_id }"/></td>
						<td><c:out value="${item.order_date }"/></td>
						<td><c:out value="${item.product_id }"/></td>
						<td><c:out value="${item.product_name }"/></td>
						<td><c:out value="${item.product_num }"/></td>
						<td><c:out value="${item.temp_amount }"/></td>
						<td><c:out value="${item.use_points }"/></td>
						<td><c:out value="${item.customer_id }"/></td>
						<td><c:out value="${item.customer_name }"/></td>
						<td><c:out value="${item.ship_id }"/></td>
						<td><c:out value="${item.ship_name }"/></td>
						<td><c:out value="${item.ship_address }"/></td>
						<td><c:out value="${item.payment_methods }"/></td>
						<%-- <td><c:out value="${item.ship_date }"/></td> --%>
						<td>
							<select name="status">
								<c:forEach var="s" items="${statusList }">
									<option value="${s}"
										<c:if test="${s.equals('未発送')}">selected</c:if>>
										<c:out value="${s}"/>
									</option>
								</c:forEach>
							</select>
						</td>
						<td>
								<input type="hidden" name="order_detail_id" value="${item.order_detail_id }">
								<input type="submit" value="確定">
						</td>
					</tr>
				</form>
			</c:forEach>
			<tr>
			</tr>
		</table>
	</c:if>
</body>
</html>