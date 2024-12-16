<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マイページ</title>
<style>
body{
      margin: 0;
      padding: 0;
}
h1{
	font-size: 30px;
      margin-bottom: 20px;
      text-align: center;
}
.account-info{
	width: 80%;
    max-width: 1000px;
    margin: 0 auto; /* 左右中央寄せ */
    padding: 20px; /* 内側の余白 */
    text-align: left;
}
.account-info table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
    border: 1px solid #ddd;
}
.account-info table td{
   	padding: 12px;
   	border-collapse: collapse;
   	margin-bottom: 20px;
   	border-bottom: none;
   	text-align: left;
}
.account-info table td:first-child {
    width: 200px;
    font-weight: bold;
    background-color: #f9f9f9;
    text-align: center;
}
.account-info table td:not(:first-child) {
   	padding-left: 35px; /* 文字の始まりを右にずらすため、左側に余白を追加*/
}
.account-info table tr{
   	border-bottom: 1px solid #ddd;
}
.account-info input[type="submit"] {
    padding: 10px 20px;
    margin: 10px 5px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
    background-color: #494949;
    color: #ffffff;
    display: block;
    margin-left: auto;
    margin-right: auto;
}
.account-info input[type="submit"]:focus {
    outline: none;
}
.button{
	display: flex;
	justify-content: center; /* ボタンを中央に配置 */
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<main>
		<div class="account-info">
			<h1>会員情報</h1>
				<form:form modelAttribute="customerModel">
				<table>
					<tr>
						<td>氏名</td>
						<td>${ customerModel.customer_name }</td>
					</tr>
					<tr>
						<td>フリガナ</td>
						<td>${customerModel.customer_phonetic }</td>
					</tr>
					<tr>
						<td>メールアドレス</td>
						<td>${customerModel.mail }</td>
					</tr>
					<tr>
						<td>郵便番号</td>
						<td>${customerModel.zip }</td>
					</tr>
					<tr>
						<td>住所</td>
						<td>${customerModel.address }</td>
					</tr>
					<tr>
						<td>電話番号</td>
						<td>${customerModel.phone }</td>
					</tr>
					<tr>
						<td>生年月日</td>
						<td>${customerModel.birthday }</td>
					</tr>
					<tr>
						<td>クレジットカード番号</td>
						<td>${customerModel.masked_creditcard_num }</td>
					</tr>
					<tr>
						<td>カード有効期限</td>
						<td>${customerModel.creditcard_exp }</td>
					</tr>
				</table>
				<div class="button">
					<input type="submit" name="action" value="会員情報変更" /><input type="submit" name="action" value="退会する" />
				</div>
				</form:form>
		</div>
	</main>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>