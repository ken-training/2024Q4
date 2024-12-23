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
	color: #494949;
    width: 80%;        /* 幅の設定 */
    margin: 0 auto;    /* 中央寄せ */
    display:flex;
    justify-content: center;
}
/*商品名と金額を右寄せ*/
.right-align {
    text-align: right;
    margin-left: 20px;
}
.product_message{
margin-bottom: 30px;
}
/*発送情報変更と注文を確定するボタン*/
button{
    padding: 15px 20px;
    background-color: #575757;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
    display: inline-block;
}

button:hover {
    background-color: #4a4a4a;
}
input[type="submit"]{
padding: 10px 80px;
    background-color: #575757;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
    display: inline-block;
}
table{
	border-collapse: collapse;

}

td{
	height: 200px;
}
tr:not(:last-child){
	border-bottom: 1.5px solid #ccc;
}
</style>
<title>注文確認画面</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<main style="background: #efefef;">
		<div class="container">
		<form action="${pageContext.request.contextPath}/confirm" method="post" style="width:70%;text-align: center;">
		<br>
			<h2>ご注文内容の確認</h2>
	<div style="text-align: left;">
	<p>${message }</p>
	<p>${error }</p>
	<p>商品合計数 : ${total_qty }点</p>
	<p>税額 : ￥ <fmt:formatNumber value="${total_amount * 0.1}" pattern="#,###"></fmt:formatNumber></p>
	<p>商品の小計 : ￥ <fmt:formatNumber value="${total_amount * 1.1}" pattern="#,###"></fmt:formatNumber></p>
	<p>配送料 :<span style="color: #a2343b;">WEBサイト開設キャンペーンにつき、送料無料!!</span></p>
	<p><span style="bold-large:font-weight: bold; font-size: 1.8em;">ご請求額 : ￥ <fmt:formatNumber value="${total_amount * 1.1}" pattern="#,###"></fmt:formatNumber></span></p>
<hr>

	<p>お支払い情報 : ${pay }</p>
	<p>お届け先 : ${orderFormModel.shipName } 様</p>
	<p><span style="padding-left: 80px;">〒&nbsp;${orderFormModel.shipZip1 }-${orderFormModel.shipZip2 }</span></p>
	<p>
		<span style="padding-left: 80px;">${orderFormModel.shipPrefecture }&nbsp;${orderFormModel.shipCity }
		${orderFormModel.shipBlock }&nbsp;${orderFormModel.shipBuilding }</span>
	</p>
	</div>
	<div style="text-align: right;">
		<button type="button" onclick="window.location.href='/project/order'"class="button">発送情報を変更する</button>
	</div>
		<br>
<hr>
	<div style="display:flex; flex-direction:column; align-items: center;">
	<table>
		<c:forEach var="item" items="${ cartList }">
			<tr style="text-align: center;">
				<td style="width: 130px; text-align: center;">
				<img src="resources/img/${item.image}.png" alt="${item.product_name }"
				style="width: 130px; height: 120px; display:block; margin: 0 auto;">
				</td>
				<td style="text-align: center;">
					<p class="right-align">${item.product_name }</p>
					<c:if test="${item.getDiscnt_is_valid() == 0 }">
						<p class="right-align">\ <fmt:formatNumber value="${item.price }" pattern="#,###" /><small>(税抜)</small>
					</c:if>
					<c:if test="${item.getDiscnt_is_valid() == 1 }">
                          <div class="right-align">
                              <p class="product_message"  id="price" data-discount-rate="${item.getDiscnt_rate()}" data-price="${item.price}">
                                  <del>¥ <fmt:formatNumber value="${item.price}" pattern="#,###" /> <small>(税抜)</small></del>

                                  <span class="discount-price" style="display:block; text-align:right; margin-top:-15px;"></span>


                              </p>
                          </div>
					</c:if>
					<p class="right-align">数量 : ${item.getCount()}</p>
				</td>
			</tr>
		</c:forEach>
	</table>
		<br>
		<p class="error-message"><c:out value="${errorDb}"/></p>
		<input type="hidden" name="orderFormModel" value="${orderFormModel }">
		<input type="submit" value="注文を確定する" style="margin: 10px 0 50px 0;" class="button">
	</div>


		</form>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	</main>
</body>
</html>