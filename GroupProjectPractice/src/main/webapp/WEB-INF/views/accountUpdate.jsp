<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員情報変更</title>

<script>
	// 最後の日を設定する関数
	function monthday(year,month){
		var lastday = new Array('', 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
		if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0){
			lastday[2] = 29;
		}
		return lastday[month];
	}

	// 日にちのリストを設定する関数
	function setDay(){
		var year = document.getElementById("birthYear").value;
		var month = document.getElementById("birthMonth").value;
		var lastday = monthday(year, month);

		var day = document.getElementById("birthDay");

		// 日にちの初期化
			var len = day.length;
	//		console.log(len);
			for (var i = 0; i < len; i++){
				day.options[0] = null;
			}

			// 日にちを最終日まで設定
			for (var i = 1; i <= lastday; i++){
	//			console.log(i);
				day.options[i-1] = new Option(i,i);
			}
	}

	// ----入力チェック----
	// 初期化: エラーメッセージをリセット
	document.addEventListener('DOMContentLoaded', function() {
	    var checkName = ["mail-error", "password-error", "zip-error", "phone-error", "creditNum-error", "creditExp-error"];
	    for (var i = 0; i < checkName.length; i++) {
 	       document.getElementById(checkName[i]).textContent = '';
	    }
	});

	// 入力チェック
	function check() {
	    var elem = document.fm.elements;
	    var cnt = 0;
	    var checkName = ["mail-error", "password-error", "zip-error", "phone-error", "creditNum-error", "creditExp-error"];

	    // エラーメッセージをリセット
	    for (var i = 0; i < checkName.length; i++) {
	        document.getElementById(checkName[i]).textContent = '';
	    }

	    for (var i= 0; i < elem.length ; i++){
			console.log("elem[" + i + "]:" + elem[i].value);
	    }

		// メールアドレスの文字数チェック
	    if(elem[2].value != ""){	// 未入力の場合は除く
			if( !elem[2].value.match(/^[\s\S]{3,32}$/)){
				document.getElementById(checkName[0]).textContent = '3文字以上32文字以下で入力してください';
				cnt++;
			}
	    }

		// パスワードの文字数チェック
	    if(elem[3].value != ""){	// 未入力の場合は除く
			if( !elem[3].value.match(/^[\s\S]{6,15}$/)){
				document.getElementById(checkName[1]).textContent = '6文字以上15文字以下で入力してください';
				cnt++;
			}
	    }

		// 郵便番号の数字、文字数チェック
	    if(!(elem[4].value == "" && elem[5].value == "")){	// すべて未入力の場合は除く
			if( !(elem[4].value.match(/^\d{3}$/) && elem[5].value.match(/^\d{4}$/)) ){
				document.getElementById(checkName[2]).textContent = '郵便番号が無効です';
				cnt++;
			}
	}

	    // 電話番号の数字、文字数チェック
	    if(!(elem[10].value == "" && elem[11].value =="" && elem[12].value == "")){	// すべて未入力の場合は除く
		    if (!elem[10].value.match(/^\d{2,4}$/) ||
		    	!elem[11].value.match(/^\d{2,4}$/) ||
	    		!elem[12].value.match(/^\d{4}$/) ||
	    		elem[10].value.length + elem[11].value.length + elem[12].value.length >= 12) {
		        document.getElementById(checkName[3]).textContent = '電話番号が無効です';
		        cnt++;
	    	}
	    }

	 	// クレジットカード番号の数字、文字数チェック
	    if(!(elem[16].value == "" && elem[17].value =="" && elem[18].value == "" && elem[19].value == "")){	// すべて未入力の場合は除く
	       	if( !elem[16].value.match(/^\d{4}$/) || !elem[17].value.match(/^\d{4}$/)
    				|| !elem[18].value.match(/^\d{4}$/) || !elem[19].value.match(/^\d{4}$/)) {
				document.getElementById(checkName[4]).textContent = 'カード番号が無効です';
				cnt++;
        	}
        }

	     // クレジットカード有効期限チェック
			//現在の日付を取得
	        var today = new Date();
	        // 現在の年を取得
	        var year = today.getFullYear(); // 年（4桁）
			var month = today.getMonth() + 1; // 月（0から始まるので1を足す）

	        // 例）2024 + 12 →  202412
	        var nowYm = "" + year + (month < 10 ? "0" + month : month);  // 月が1桁の場合は0を追加

			// 入力された有効期限を取得
			var value = elem[21].value
			if(elem[20].value < 10){
				value += "0" + elem[20].value;
			} else {
				value += elem[20].value;
			}
			console.log("value" + value);

			/* 現在と入力値文字列を大小比較し、
			現在 >= 入力値 であるなら errorメッセージ を表示	*/
			if(parseInt(nowYm) > parseInt(value)){
				document.getElementById(checkName[5]).textContent = '有効期限が切れています';
				cnt++;
			}

	    // エラーがあれば送信を防止
	    if (cnt > 0) {
	        return false;  // フォームの送信をキャンセル
	    } else {
	        return true;   // フォームを送信
	    }
	}


</script>
</head>
<body>
	<main>
		<jsp:include page="header.jsp"></jsp:include>
		<div class="updateForm-container">
			<h1>会員情報変更</h1>
				<form:form modelAttribute="updateFormModel" name="fm" style="text-align: center;" onsubmit="return check()">
				<table class="form-table">
					<tr>
						<td>氏名</td>
						<td>
							<form:input path="customer_name" class="form-control"/>
						</td>
					</tr>
					<tr>
						<td>フリガナ</td>
						<td>
							<form:input path="customer_phonetic" class="form-control" />
						</td>
					</tr>
					<tr>
						<td>メールアドレス</td>
						<td>
							<form:input path="mail" class="form-control" />
							<span id="mail-error" class="error-message"></span>
							<p><form:errors path="mail" element="div" cssClass="form-error" /></p>
							<p class="error"><c:out value="${error}"/></p>
						</td>
					</tr>
					<tr>
						<td>パスワード</td>
						<td>
 							<form:input path="password" class="form-control" />
							<span id="password-error" class="error-message"></span>
 						</td>
					</tr>
					<tr>
						<td>郵便番号</td>
						<td class="flex-center" width="200px">
							<form:input path="zip1" class="form-control" placeholder="3桁" style="text-align: center;" /> &nbsp－&nbsp <form:input path="zip2" class="form-control" placeholder="4桁" style="text-align: center;" />
							<span id="zip-error" class="error-message"></span>

						</td>
					</tr>
					<tr>
						<td>住所</td>
						<td>
							<div class="form-item">
								<label>都道府県</label>
								<form:input path="prefecture" class="form-control" />
							</div>
							<div class="form-item">
								<label>市区町村</label>
								<form:input path="city" class="form-control" />
							</div>
							<div class="form-item">
								<label>番地</label>
								<form:input path="block" class="form-control" />
							</div>
							<div class="form-item">
								<label>建物名・部屋番号名</label>
								<form:input path="building" class="form-control" />
							</div>
						</td>
					</tr>
					<tr>
						<td>電話番号</td>
						<td class="flex-center" width="370px">
							<form:input path="phone1" class="form-control" placeholder="2~4桁" style="text-align: center;" /> &nbsp－&nbsp
							<form:input path="phone2" class="form-control" placeholder="2~4桁" style="text-align: center;" /> &nbsp－&nbsp
							<form:input path="phone3" class="form-control" placeholder="4桁" style="text-align: center;" />
							<span id="phone-error" class="error-message"></span>

						</td>
					</tr>
					<tr>
						<td>生年月日</td>
						<td class="flex-center" width="400px">
 							<form:select path="birthYear" items="${birthYearList }" onchange="setDay()" class="form-control"/>&nbsp年&emsp;
 							<form:select path="birthMonth" items="${birthMonthList }"  onchange="setDay()" class="form-control"/> &nbsp月&emsp;
 							<form:select path="birthDay" items="${birthDayList }" class="form-control"/> &nbsp日&nbsp
 						</td>
					</tr>
					<tr>
						<td>クレジットカード番号</td>
						<td class="flex-center" width="500px">
 							<form:input path="creditNum1" size="5" placeholder="0000" class="form-control" style="text-align: center;"/> &nbsp－&nbsp
 							<form:input path="creditNum2" size="5" placeholder="0000" class="form-control" style="text-align: center;"/> &nbsp－&nbsp
 							<form:input path="creditNum3" size="5" placeholder="0000" class="form-control" style="text-align: center;"/> &nbsp－&nbsp
 							<form:input path="creditNum4" size="5" placeholder="0000" class="form-control" style="text-align: center;"/>
 							<span id="creditNum-error" class="error-message"></span>

 						</td>
					</tr>
					<tr>
						<td>カード有効期限</td>
						<td class="flex-center" width="235px">
 							<form:select path="creditExpM" items="${creditExpMList }" class="form-control" /> &ensp;/&ensp;
 							<form:select path="creditExpY" items="${creditExpYList }" class="form-control" />
 							<span id="creditExp-error" class="error-message"></span>
 						</td>
					</tr>
				</table>
				<input type="submit" value="変更" class="button submit-button" />
				</form:form>
		</div>
	</main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>