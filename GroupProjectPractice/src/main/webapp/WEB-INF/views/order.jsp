<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html >
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>発送情報入力画面</title>
<style type="text/css">
footer{
	background:#efefef;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
 	<main>
	 	<div class="order-container">
	 		<div class="orderForm-container">
				<h2>発送情報入力フォーム</h2>
				<form:form modelAttribute="orderFormModel" name="fm" onsubmit="return check()">
					<div class="form-item">
						<label>氏名<span class="required">*必須</span></label>
						<div>
							<form:input path="shipName" class="form-control" />
							<p id="shipName-notnull" class="error-message"></p>
							<form:errors path="shipName" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<label>フリガナ</label>
						<div>
							<form:input path="shipPhonetic" class="form-control" />
						</div>
					</div>
					<div class="form-item">
						<label>電話番号<span class="required">*必須</span></label>
						<div class="flex-center">
							<form:input path="shipPhone1" size="5" placeholder="2～4桁" class="form-control" style="text-align: center;" />
							&nbsp－&nbsp
							<form:input path="shipPhone2" size="5" placeholder="2～4桁" class="form-control" style="text-align: center;" />
							&nbsp－&nbsp
							<form:input path="shipPhone3" size="5" placeholder="4桁" class="form-control" style="text-align: center;" />
						</div>
						<p id="shipPhone-notnull" class="error-message"></p>
						<p id="shipPhone-error" class="error-message"></p>
						<form:errors path="shipPhone1" element="span" cssClass="error"/>
						<form:errors path="shipPhone2" element="span" cssClass="error"/>
						<form:errors path="shipPhone3" element="span" cssClass="error"/>
					</div>
					<div class="form-item" style="width: 200px">
						<label>郵便番号<span class="required">*必須</span></label>
						<div class="flex-center">
							<form:input path="shipZip1" size="5" placeholder="3桁" class="form-control" style="text-align: center" />
							&nbsp－&nbsp
							<form:input path="shipZip2" size="5" placeholder="4桁" class="form-control" style="text-align: center" />
						</div>
						<p id="shipZip-notnull" class="error-message"></p>
						<p id="shipZip-error" class="error-message"></p>
						<form:errors path="shipZip1" element="span" cssClass="error"/>
						<form:errors path="shipZip2" element="span" cssClass="error"/>
					</div>
					<div class="form-item">
						<label>都道府県<span class="required">*必須</span></label>
						<div>
							<form:input path="shipPrefecture" class="form-control" />
							<p id="shipPrefecture-notnull" class="error-message"></p>
							<form:errors path="shipPrefecture" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<label>市区町村<span class="required">*必須</span></label>
						<div>
							<form:input path="shipCity" class="form-control" />
							<p id="shipCity-notnull" class="error-message"></p>
							<form:errors path="shipCity" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<label>番地<span class="required">*必須</span></label>
						<div>
							<form:input path="shipBlock" class="form-control" />
							<p id="shipBlock-notnull" class="error-message"></p>
							<form:errors path="shipBlock" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<label>建物名・部屋番号名</label>
						<div>
							<form:input path="shipBuilding" class="form-control" />
						</div>
					</div>
					<div class="form-item">
						<label>お支払方法</label>
						<div class="radio-buttons">
							<!-- 代金引換(現金)のラジオボタン -->
							<form:radiobutton path="pay" value="cash" id="cash"
								checked="true" onclick="showDiv('cash')" />
							<label for="cash" class="radio-label"> 代金引換(現金) </label>

							<!-- クレジットカードのラジオボタン -->
							<form:radiobutton path="pay" value="credit" id="credit"
								onclick="showDiv('credit')" />
							<label for="credit" class="radio-label"> クレジットカード </label>
						</div>
					</div>
					<div id="creditDiv">
						<div class="form-item">
							<label>クレジットカード番号<span class="required">*必須</span></label>
							<div class="flex-center">
								<form:input path="creditNum1" size="5" placeholder="0000"
									class="form-control" style="text-align: center;" />
								&nbsp－&nbsp
								<form:input path="creditNum2" size="5" placeholder="0000"
									class="form-control" style="text-align: center;" />
								&nbsp－&nbsp
								<form:input path="creditNum3" size="5" placeholder="0000"
									class="form-control" style="text-align: center;" />
								&nbsp－&nbsp
								<form:input path="creditNum4" size="5" placeholder="0000"
									class="form-control" style="text-align: center;" />
							</div>
							<p id="creditNum-notnull" class="error-message"></p>
							<p id="creditNum-error" class="error-message"></p>
						</div>
						<div class="form-item">
							<label>クレジットカード有効期限<span class="required">*必須</span></label>
							<div class="flex-center" style="width: 235px">
								<form:select path="creditExpM" items="${creditExpMList }" class="form-control" />
								&ensp;/&ensp;
								<form:select path="creditExpY" items="${creditExpYList }" class="form-control" />
							</div>
							<p id="creditExp-error" class="error-message"></p>
						</div>
					</div>
					<div>
						<div class="button-box">
							<input type="submit" name="action" value="注文確認" class="button" style="width: 35%;">
							<input type="submit" name="action" value="戻る" class="button" style="width: 20%;">
						</div>
					</div>
				</form:form>
			</div>
			<div id="fixedDiv">
				<div class="side-contents">
					<!-- カートの中身を表示 -->
					<h3 class="title">注文商品一覧</h3>
					<c:if test="${ !empty(cartList)}">
						<table>
							<c:forEach var="item" items="${ cartList }">
								<tr>
									<td><img src="resources/img/${item.image}.png"
										alt="${item.product_name }" class="cart-img"></td>
									<td>
										<div class="side-message">
											<b>${item.product_name }<br></b>
											数量：${item.count}
										</div>
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td colspan="2" style="text-align: left;">
									<p>商品合計数 :  <c:out value="${total_qty}" />点</p>
									<p>合計金額(税抜) : ￥ <fmt:formatNumber value="${total_amount}" pattern="#,###" /></p>
								</td>
							</tr>
						</table>
					</c:if>
				</div>
			</div>
	 	</div>
	</main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>