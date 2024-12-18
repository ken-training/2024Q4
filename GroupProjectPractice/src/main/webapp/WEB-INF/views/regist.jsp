<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>新規登録</title>
<style>
/* ページ全体を中央寄せにする */
body {
	display: flex;
	flex-direction: column; /* 縦に配置 */
	justify-content: flex-start; /* 上から順に配置 */
	margin: 0;
}


/* フォームコンテナ */
.regist_container {
	display: flex;
	flex-direction: column;
	align-items: center; /* 中央揃え */
	justify-content: center;
	border: 2px solid #dedede;
	padding: 30px;
	width: 60%;
	margin: 30px auto 10px; /* 上に70pxのマージン（ヘッダー分）と、下に80pxのマージン（フッター分） */
	background-color: white;
	border-radius: 8px;
	flex-grow: 1;
}


/* ラベルのスタイル */
.regist_container label {
	width: 150px;
	margin-right: 10px;
	text-align: right;
}

.form-error {
	margin-top: 5px;
	color: #ff0000;
	font-size: 12px;
}

/* ボタンのスタイル */
input[type="submit"] {
	display: inline-block;
	padding: 10px 35px;
	background-color: #494949;
	color: #eee;
	border-radius: 5px;
	text-align: center;
	font-size: 16px;
	cursor: pointer;
	border: none;
}

/* ボタンの中央配置 */
.regist_margin {
	width: 100%; /* 幅を100%に設定 */
	display: flex; /* flexbox を使用 */
	justify-content: center; /* 横方向に中央揃え */
	margin-top: 30px; /* 上部に余白 */
}

.regist_container h1 {
    width: 100%;  /* 幅を100%に設定 */
    text-align: center;  /* 中央寄せ */
    font-size: 24px; /* フォントサイズを大きくして見栄えを調整 */
}

.regist_message{
	text-align: center;  /* 中央寄せ */
}

</style>
</head>
<body>

<!-- ヘッダーのインクルード -->
	<jsp:include page="header.jsp"></jsp:include>

	<div class="regist_container">
		<h1>新規会員登録</h1>
		<form:form modelAttribute="customerModel">
		<table class="flex-col-center">
			<tr>
				<td>
					<div class="form-item">
						<label>氏名<span class="required">*必須</span></label>
						<form:input path="customer_name" size="20" class="form-control" />
						<form:errors path="customer_name" element="label" cssClass="error-message" />
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-item">
						<label>メールアドレス<span class="required">*必須</span></label>
						<form:input path="mail" size="40" class="form-control" />
						<form:errors path="mail" element="label" cssClass="error-message" />
						<div><c:out value="${error}"/></div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="form-item">
						<label>パスワード<span class="required">*必須</span></label>
						<form:password path="password" size="30" class="form-control" />
						<form:errors path="password" element="label" cssClass="error-message" />
					</div>
				</td>
			</tr>
		</table>

		<!-- ボタンの配置 -->
		<div class="regist_margin">
			<p><input type="submit" value="登録する" /></p>
		</div>

		<!-- その他のメッセージ -->
		<div class="regist_message">
			<p>※郵便番号、住所、電話番号、生年月日、<br>クレジットカード番号、有効期限はあとでマイページから登録できます</p>
		</div>

	</form:form>
</div>

<jsp:include page="footer.jsp" />
</body>
</html>
