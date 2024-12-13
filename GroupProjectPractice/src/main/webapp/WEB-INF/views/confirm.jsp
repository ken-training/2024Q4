<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<style type="text/css">
.container {
    width: 80%;        /* 幅の設定 */
    margin: 0 auto;    /* 中央寄せ */

}

</style>
<title>注文確認画面</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<div class="container">
<form action="${pageContext.request.contextPath}/confirm_test" method="post">
	注文確認画面
	<p>${message }</p>
	<p>商品合計数 : ${total_qty }</p>
	<p>税額 : ${total_amount * 0.1 }</p>
	<p>商品の小計 : ${total_amount * 1.1 }</p>
	<p>配送料 : WEBサイト開設キャンペーンにつき、送料無料!!</p>
	<p>ご請求額 : ${total_amount * 1.1 }</p>
	<hr>
	<p>お支払い情報 : ${orderFormModel.pay }</p>
	<p>お届け先 : ${orderFormModel.shipPrefecture }${orderFormModel.shipCity }
	${orderFormModel.shipBlock }${orderFormModel.shipBuilding }</p>
	<button onclick="window.location.href='${pageContext.request.contextPath}/order'">発送情報を変更する</button>
	<hr>
	<table>
		<c:forEach var="item" items="${ cartList }">
			<tr>
				<td><img src="resources/img/${item.image}.png" alt="${item.product_name }"
						  width="130" height="100"></td>
				<td>
				${item.product_name } <br>
				単価(税抜) : \ <fmt:formatNumber value="${item.price }" pattern="#,###" /><br>
				数量 : ${item.count } 個
				</td>
			</tr>
		</c:forEach>
		<tr>
		</tr>
	</table>
	<input type="hidden" name="orderFormModel" value="${orderFormModel }">
	<input type="submit" value="注文を確定する">
</form>

	<jsp:include page="footer.jsp"></jsp:include>
</div>
</body>
</html>