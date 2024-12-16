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
</script>
</head>
<body onload="setDay()">
	<main>
		<jsp:include page="header.jsp"></jsp:include>
		<div>
			<h1>会員情報変更</h1>
				<form:form modelAttribute="updateFormModel" name="fm">
				<table>
					<tr>
						<td>・氏名</td>
						<td>
							<form:input path="customer_name"/>
						</td>
					</tr>
					<tr>
						<td>・フリガナ</td>
						<td>
							<form:input path="customer_phonetic" />
						</td>
					</tr>
					<tr>
						<td>・メールアドレス</td>
						<td>
							<form:input path="mail" />
						</td>
					</tr>
					<tr>
						<td>・パスワード</td>
						<td>
 							<form:input path="password"  />
 						</td>
					</tr>
					<tr>
						<td>・郵便番号</td>
						<td>
							<form:input path="zip1" /> - <form:input path="zip2" />
						</td>
					</tr>
					<tr>
						<td>・住所</td>
					</tr>
					<tr>
						<td>都道府県</td>
						<td>
							<form:input path="prefecture" />
						</td>
					</tr>
					<tr>
						<td>市区町村</td>
						<td>
							<form:input path="city" />
						</td>
					</tr>
					<tr>
						<td>番地</td>
						<td>
							<form:input path="block" />
						</td>
					</tr>
					<tr>
						<td>建物名・部屋番号名</td>
						<td>
							<form:input path="building" />
						</td>
					</tr>
					<tr>
						<td>・電話番号</td>
						<td>
							<form:input path="phone1" /> - <form:input path="phone2" /> - <form:input path="phone3" />
						</td>
					</tr>
					<tr>
						<td>・生年月日</td>
						<td>
 							<form:select path="birthYear" items="${birthYearList }" onchange="setDay()"/> 年
 							<form:select path="birthMonth" items="${birthMonthList }"  onchange="setDay()"/> 月
 							<form:select path="birthDay" /> 日
 						</td>
					</tr>
					<tr>
						<td>・クレジットカード番号</td>
						<td>
 							<form:input path="creditNum1" size="5" placeholder="0000"/> -
 							<form:input path="creditNum2" size="5" placeholder="0000"/> -
 							<form:input path="creditNum3" size="5" placeholder="0000"/> -
 							<form:input path="creditNum4" size="5" placeholder="0000"/>
 						</td>
					</tr>
					<tr>
						<td>・カード有効期限</td>
						<td>
 							<form:select path="creditExpM" items="${creditExpMList }" /> /
 							<form:select path="creditExpY" items="${creditExpYList }" />
 						</td>
					</tr>
				</table>
				<input type="submit" value="変更" />
				</form:form>
		</div>
	</main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>