<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html >
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>発送情報入力画面</title>
<style>
    span {
		font-size: small;
		color: red;
	}
</style>
<script>
function check(){
	var elem = document.fm.element;
	var str = "";

	for (var i= 0; i < 6; i++){
		if(elem[i].value != ""){}
	}
}
</script>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
 	<main>
	 	<div class="order-container">
	 		<div class="orderForm-container">
				<h2>注文フォーム</h2>
				<form:form modelAttribute="orderFormModel" name="fm">
					<div class="form-item">
						<div>氏名<span>*必須</span></div>
						<div>
							<form:input path="shipName" class="form-control" />
							<form:errors path="shipName" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<div>フリガナ</div>
						<div>
							<form:input path="shipPhonetic" class="form-control" />
						</div>
					</div>
					<div class="form-item">
						<div>電話番号<span>*必須</span></div>
						<div class="flex-center">
							<form:input path="shipPhone1" size="5" placeholder="2～4桁" class="form-control" />
							&nbsp－&nbsp
							<form:input path="shipPhone2" size="5" placeholder="2～4桁" class="form-control" />
							&nbsp－&nbsp
							<form:input path="shipPhone3" size="5" placeholder="4桁" class="form-control" />
							<form:errors path="shipPhone1" element="span" cssClass="error"/>
							<form:errors path="shipPhone2" element="span" cssClass="error"/>
							<form:errors path="shipPhone3" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item" style="width: 200px">
						<div>郵便番号<span>*必須</span></div>
						<div class="flex-center">
							<form:input path="shipZip1" size="5" placeholder="3桁" class="form-control" style="text-align: center" />
							&nbsp－&nbsp
							<form:input path="shipZip2" size="5" placeholder="4桁" class="form-control" style="text-align: center" />
							<form:errors path="shipZip1" element="span" cssClass="error"/>
							<form:errors path="shipZip2" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<div>都道府県<span>*必須</span></div>
						<div>
							<form:input path="shipPrefecture" class="form-control" />
							<form:errors path="shipPrefecture" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<div>市区町村<span>*必須</span></div>
						<div>
							<form:input path="shipCity" class="form-control" />
							<form:errors path="shipCity" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<div>番地<span>*必須</span></div>
						<div>
							<form:input path="shipBlock" class="form-control" />
							<form:errors path="shipBlock" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<div>マンション名</div>
						<div>
							<form:input path="shipBuilding" class="form-control" />
						</div>
					</div>
					<div class="form-item">
						<div>お支払方法</div>
						<div>
							<form:radiobutton path="pay" value="cash" checked="true"
								onclick="showDiv('cash')" />
							代金引換(現金)
							<form:radiobutton path="pay" value="credit"
								onclick="showDiv('credit')" />
							クレジットカード
						</div>
					</div>
					<div id="credit">
						<div>クレジットカード番号<span>*必須</span></div>
						<div class="flex-center">
							<form:input path="creditNum1" size="5" placeholder="0000" class="form-control" />
							&nbsp－&nbsp
							<form:input path="creditNum2" size="5" placeholder="0000" class="form-control" />
							&nbsp－&nbsp
							<form:input path="creditNum3" size="5" placeholder="0000" class="form-control" />
							&nbsp－&nbsp
							<form:input path="creditNum4" size="5" placeholder="0000" class="form-control" />
						</div>
						<div>クレジットカード有効期限<span>*必須</span></div>
						<div class="flex-center">
							<form:select path="creditExpM" items="${creditExpMList }" class="form-control" />
							&ensp;/&ensp;
							<form:select path="creditExpY" items="${creditExpYList }" class="form-control" />
						</div>
					</div>
					<div>
						<div>
							<input type="submit" name="action" value="注文確認" class="button">
							<input type="submit" name="action" value="戻る" class="button">
						</div>
					</div>
				</form:form>
			</div>
			<div id="fixedDiv">
				<div class="side-contents">
					<!-- カートの中身を表示 -->
					<h3>注文商品一覧</h3>
					<c:if test="${ !empty(cartList)}">
						<table>
							<c:forEach var="item" items="${ cartList }">
								<tr>
									<td><img src="resources/img/${item.image}.png"
										alt="${item.product_name }" class="cart-img"></td>
									<td>
										<div>${item.product_name }</div>
										<div>${item.count}個</div>
									</td>
								</tr>
							</c:forEach>
							<tr>
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