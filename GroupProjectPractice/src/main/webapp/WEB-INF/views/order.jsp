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
	.error-message {
        color: red;
        font-size: 12px;
    }
</style>
<script>
function check(){
	var elem = document.fm.elements;
	var str = "";
	var cnt = 0;

	// チェック対象のインデックスを指定(名前、電話番号、郵便番号、住所)
    var checkNullIndices = [0, 2, 3, 4, 5, 6, 7, 8, 9];
	var checkName = ["shipName-notnull", "shipPhone-notnull", "shipZip-notnull", "shipPrefecture-notnull"
						, "shipCity-notnull", "shipBlock-notnull", "shipPhone-error", "shipZip-error" ];
	console.log(checkName.toString());

	// ラジオボタンがクレジットカードを選択している場合、チェック対象のインデックスを追加 (カード番号、有効期限)
	var selectedRadio = document.querySelector('input[name="pay"]:checked');
	if(selectedRadio && selectedRadio.value === 'credit') {
		for (var i = 13; i <= 18; i++){
			checkNullIndices.push(i);
		}
		checkName.push("creditNum-notnull");
		checkName.push("creditNum-error");
		checkName.push("creditExp-error");
		console.log(checkName.toString());
	}

	// エラーメッセージをリセット
	for (var i = 0; i < checkName.length; i++){
		document.getElementById(checkName[i]).textContent = '';
	}
	console.log(checkNullIndices.toString());

	for (var i= 0; i < elem.length ; i++){
		console.log("elem[" + i + "]:" + elem[i].value);

		// チェック対象インデックスに該当する場合、未入力チェック
        if (checkNullIndices.includes(i) && elem[i].value == "") {
        	switch(i){
   	    	case 0:	// 氏名
				document.getElementById(checkName[0]).textContent = '必須入力です';
      			cnt++;
				break;
       		case 2:	// 電話番号
       		case 3:	// 電話番号
       		case 4:	// 電話番号
				document.getElementById(checkName[1]).textContent = '必須入力です';
       			cnt++;
				break;
       		case 5:	// 郵便番号
       		case 6:	// 郵便番号
				document.getElementById(checkName[2]).textContent = '必須入力です';
       			cnt++;
				break;
       		case 7:	// 都道府県
				document.getElementById(checkName[3]).textContent = '必須入力です';
       			cnt++;
				break;
       		case 8:	// 市区町村
				document.getElementById(checkName[4]).textContent = '必須入力です';
       			cnt++;
				break;
       		case 9:	// 番地
				document.getElementById(checkName[5]).textContent = '必須入力です';
        		cnt++;
				break;
        	case 13:	// クレジットカード番号
        	case 14:	// クレジットカード番号
        	case 15:	// クレジットカード番号
        	case 16:	// クレジットカード番号
				document.getElementById(checkName[8]).textContent = '必須入力です';
        		cnt++;
				break;
        	}
        }
	}

	// 電話番号の数字、文字数チェック
	if( !elem[2].value.match(/^\d{2,4}$/) || !elem[3].value.match(/^\d{2,4}$/) || !elem[4].value.match(/^\d{4}$/)
			|| elem[2].value.length + elem[3].value.length + elem[4].value.length >= 12){
		document.getElementById(checkName[6]).textContent = '電話番号が無効です';
		cnt++;
	}

	// 郵便番号の数字、文字数チェック
	if( !elem[5].value.match(/^\d{3}$/) || !elem[6].value.match(/^\d{4}$/)){
		document.getElementById(checkName[7]).textContent = '郵便番号が無効です';
		cnt++;
	}

	if(selectedRadio && selectedRadio.value === 'credit') {
		// クレジットカード番号の数字、文字数チェック
        if( !elem[13].value.match(/^\d{4}$/) || !elem[14].value.match(/^\d{4}$/)
    			|| !elem[15].value.match(/^\d{4}$/) || !elem[16].value.match(/^\d{4}$/)) {
			document.getElementById(checkName[9]).textContent = 'カード番号が無効です';
			cnt++;
        }

		// クレジットカード有効期限チェック
		//現在の日付を取得
        var today = new Date();
        // 現在の年を取得
        var year = today.getFullYear(); // 年（4桁）
		var month = today.getMonth() + 1; // 月（0から始まるので1を足す）

        // 例）2024 + 12 →  202412
        var nowYm = "" + year + month;

		// 入力された有効期限を取得
		var value = elem[18].value
		if(elem[17].value < 10){
			value += "0" + elem[17].value;
		} else {
			value += elem[17].value;
		}
		console.log("value" + value);

		/* 現在と入力値文字列を大小比較し、
		現在 >= 入力値 であるなら errorメッセージ を表示	*/
		if(parseInt(nowYm) > parseInt(value)){
			document.getElementById(checkName[10]).textContent = '有効期限が切れています';
			cnt++;
		}
	}

	// 住所の空白チェック
	if( elem[7].value.match(/\s|\u3000/)){
		document.getElementById(checkName[3]).textContent = '空白を含まないよう入力してください';
		cnt++;
	}
	if( elem[8].value.match(/\s|\u3000/)){
		document.getElementById(checkName[4]).textContent = '空白を含まないよう入力してください';
		cnt++;
	}
	if( elem[9].value.match(/\s|\u3000/)){
		document.getElementById(checkName[5]).textContent = '空白を含まないよう入力してください';
		cnt++;
	}

	if(cnt > 0) {
		return false;
	} else {
		return true;
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
				<form:form modelAttribute="orderFormModel" name="fm" onsubmit="return check()">
					<div class="form-item">
						<div>氏名<span>*必須</span></div>
						<div>
							<form:input path="shipName" class="form-control" />
							<span id="shipName-notnull" class="error-message"></span>
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
							<span id="shipPhone-notnull" class="error-message"></span>
							<span id="shipPhone-error" class="error-message"></span>
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
							<span id="shipZip-notnull" class="error-message"></span>
							<span id="shipZip-error" class="error-message"></span>
							<form:errors path="shipZip1" element="span" cssClass="error"/>
							<form:errors path="shipZip2" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<div>都道府県<span>*必須</span></div>
						<div>
							<form:input path="shipPrefecture" class="form-control" />
							<span id="shipPrefecture-notnull" class="error-message"></span>
							<form:errors path="shipPrefecture" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<div>市区町村<span>*必須</span></div>
						<div>
							<form:input path="shipCity" class="form-control" />
							<span id="shipCity-notnull" class="error-message"></span>
							<form:errors path="shipCity" element="span" cssClass="error"/>
						</div>
					</div>
					<div class="form-item">
						<div>番地<span>*必須</span></div>
						<div>
							<form:input path="shipBlock" class="form-control" />
							<span id="shipBlock-notnull" class="error-message"></span>
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
							<span id="creditNum-notnull" class="error-message"></span>
							<span id="creditNum-error" class="error-message"></span>
						</div>
						<div>クレジットカード有効期限<span>*必須</span></div>
						<div class="flex-center">
							<form:select path="creditExpM" items="${creditExpMList }" class="form-control" />
							&ensp;/&ensp;
							<form:select path="creditExpY" items="${creditExpYList }" class="form-control" />
							<span id="creditExp-error" class="error-message"></span>
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