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

</script>
</head>
<body>
	<main>
		<jsp:include page="header.jsp"></jsp:include>
		<div class="updateForm-container">
			<h1>会員情報変更</h1>
				<form:form modelAttribute="updateFormModel" name="fm" style="text-align: center;" onsubmit="return updateCheck()">
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
							<p id="mail-error" class="error-message"></p>
							<p><form:errors path="mail" element="div" cssClass="form-error" /></p>
							<p class="error"><c:out value="${error}"/></p>
						</td>
					</tr>
					<tr>
						<td>パスワード</td>
						<td>
 							<form:input path="password" class="form-control" />
							<p id="password-error" class="error-message"></p>
 						</td>
					</tr>
					<tr>
						<td>郵便番号</td>
						<td width="200px">
							<div class="flex-center">
								<form:input path="zip1" class="form-control" placeholder="3桁" style="text-align: center;" /> &nbsp－&nbsp
								<form:input path="zip2" class="form-control" placeholder="4桁" style="text-align: center;" />
							</div>
							<p id="zip-error" class="error-message"></p>
						</td>
					</tr>
					<tr>
						<td>住所</td>
						<td>
							<div class="form-item">
								<label>都道府県</label>
								<form:input path="prefecture" class="form-control" />
								<p id="prefecture-error" class="error-message"></p>
							</div>
							<div class="form-item">
								<label>市区町村</label>
								<form:input path="city" class="form-control" />
								<p id="city-error" class="error-message"></p>
							</div>
							<div class="form-item">
								<label>番地</label>
								<form:input path="block" class="form-control" />
								<p id="block-error" class="error-message"></p>
							</div>
							<div class="form-item">
								<label>建物名・部屋番号名</label>
								<form:input path="building" class="form-control" />
							</div>
						</td>
					</tr>
					<tr>
						<td>電話番号</td>
						<td width="370px">
							<div class="flex-center">
								<form:input path="phone1" class="form-control" placeholder="2~4桁" style="text-align: center;" /> &nbsp－&nbsp
								<form:input path="phone2" class="form-control" placeholder="2~4桁" style="text-align: center;" /> &nbsp－&nbsp
								<form:input path="phone3" class="form-control" placeholder="4桁" style="text-align: center;" />
							</div>
							<p id="phone-error" class="error-message"></p>
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
						<td width="500px">
							<div class="flex-center">
								<form:input path="creditNum1" size="5" placeholder="0000" class="form-control" style="text-align: center;"/> &nbsp－&nbsp
	 							<form:input path="creditNum2" size="5" placeholder="0000" class="form-control" style="text-align: center;"/> &nbsp－&nbsp
	 							<form:input path="creditNum3" size="5" placeholder="0000" class="form-control" style="text-align: center;"/> &nbsp－&nbsp
	 							<form:input path="creditNum4" size="5" placeholder="0000" class="form-control" style="text-align: center;"/>
							</div>
 							<p id="creditNum-error" class="error-message"></p>
 						</td>
					</tr>
					<tr>
						<td>カード有効期限</td>
						<td width="235px">
							<div class="flex-center">
								<form:select path="creditExpM" items="${creditExpMList }" class="form-control" /> &ensp;/&ensp;
		 						<form:select path="creditExpY" items="${creditExpYList }" class="form-control" />
							</div>
 							<p id="creditExp-error" class="error-message"></p>
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