<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<main>
		<div>
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
						<td>${customerModel.creditcard_num }</td>
					</tr>
					<tr>
						<td>カード有効期限</td>
						<td>${customerModel.creditcard_exp }</td>
					</tr>
				</table>
				<input type="submit" name="action" value="会員情報変更" /><input type="submit" name="action" value="退会する" />
				</form:form>
		</div>
	</main>
</body>
</html>