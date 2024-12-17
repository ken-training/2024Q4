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

/* ヘッダーのスタイル */
.header {
	top: 0;
	left: 0;
	width: 100%;
	text-align: center;
	padding: 10px 0;
	z-index: 10; /* フッターより前面に表示 */
}

/* フッターのスタイル */
.footer {
	bottom: 0;
	left: 0;
	width: 100%;
	text-align: center;
	padding: 10px 0;
}

/* フォームコンテナ */
.regist_container {
	display: flex;
	flex-direction: column;
	align-items: center; /* 中央揃え */
	justify-content: center;
	border: 2px solid #dedede;
	padding: 30px;
	width: 80%;
	margin: 30px auto 10px; /* 上に70pxのマージン（ヘッダー分）と、下に80pxのマージン（フッター分） */
	background-color: white;
	border-radius: 8px;
	flex-grow: 1;
}

/* フォーム項目間の余白 */
.regist_container div {
	margin-bottom: 15px;
	display: flex;
	flex-direction: row;
	align-items: center;
	position: relative; /* 相対位置を指定してエラーメッセージの位置を固定 */
}

/* ラベルのスタイル */
.regist_container label {
	width: 150px;
	margin-right: 10px;
	text-align: right;
}

/* 入力フィールドのスタイル */
.regist_container input,
.regist_container password {
	padding: 10px;
	width: 250px;
}

.error {
	margin-top: 5px;
	color: #ff0000;
	font-size: 12px;
}

.form-error {
	margin-top: 5px;
	color: #ff0000;
	font-size: 12px;
}

/* ボタンのスタイル */
input[type="submit"] {
	display: inline-block;
	padding: 10px 20px;
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

.regist_container table{
	width: 100%;
	text-align:left;
}

.regist_container td {
    text-align: left; /* 各セルの内容を左寄せ */
    padding: 10px; /* セル内の余白を調整 */
}

.regist_container p {
    margin: 0; /* <p>タグのデフォルトの余白をなくす */
    padding: 1px 0; /* 内側の余白を調整 */
    line-height: 1; /* 行間を調整 */
}

.regist_container h1 {
    width: 100%;  /* 幅を100%に設定 */
    text-align: center;  /* 中央寄せ */
    font-size: 24px; /* フォントサイズを大きくして見栄えを調整 */
}

.regist_message{
	text-align: center;  /* 中央寄せ */
}

.required {
	color: red;  /* 赤色に設定 */
	font-size: 14px;  /* 必要に応じてフォントサイズを調整 */
}

</style>
</head>
<body>

<!-- ヘッダーのインクルード -->
	<div class="header">
		<jsp:include page="header.jsp"></jsp:include>
	</div>

	<div class="regist_container">
		<h1>新規会員登録</h1>
		<form:form modelAttribute="customerModel">

		<table>
			<tr>
				<td>
					<p>　　　　　氏名：<p>
					<br>
				</td>
				<td>
					<p><form:input path="customer_name" size="20" /><span class="required">*必須</span></p>
					<form:errors path="customer_name" element="div" cssClass="form-error" />
				</td>
			</tr>

			<tr>
				<td>
					<p>メールアドレス：</p>
				<br>
				</td>
				<td>
					<p><form:input path="mail" size="40" /><span class="required">*必須</span></p>
					<p><form:errors path="mail" element="div" cssClass="form-error" /></p>
					<p class="error"><c:out value="${error}"/></p>
				</td>
			</tr>

			<tr>
				<td>
					<p>　　パスワード：</p>
					<br>
				</td>
				<td>
					<p><form:password path="password" size="30" /><span class="required">*必須</span></p>
					<p><form:errors path="password" element="div" cssClass="form-error" /></p>
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
