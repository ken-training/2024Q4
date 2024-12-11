<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員情報変更</title>
</head>
<body>
	<main>
		<div>
			<h1>会員情報変更</h1>
				<form:form modelAttribute="customerModel">
				<table>
					<tr>
						<td>氏名</td>
					</tr>
					<tr>
						<td>フリガナ</td>
					</tr>
					<tr>
						<td>メールアドレス</td>
					</tr>
					<tr>
						<td>郵便番号</td>
					</tr>
					<tr>
						<td>住所</td>
					</tr>
					<tr>
						<td>電話番号</td>
					</tr>
					<tr>
						<td>生年月日</td>
					</tr>
					<tr>
						<td>クレジットカード番号</td>
					</tr>
					<tr>
						<td>カード有効期限</td>
					</tr>
				</table>
				<input type="submit" name="action" value="会員情報変更" />
				</form:form>
		</div>
	</main>
</body>
</html>