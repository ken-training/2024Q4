<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>ログイン画面</title>
<style>
	/*大枠*/
	.container {
		display: flex;
		justify-content: center;
		align-items: center;
	}
	/* ログインセクションのスタイル */
	.login-container {
		display: inline-block;
		border: 2px solid #dedede;
		padding: 30px;
		margin: 30px;
		width: 400px; /* 幅 */
		height: 500px;
		box-sizing: border-box;
		text-align: center;
	}

	/* メールアドレス、パスワードのラベルと入力フォーム間の行間を狭める */
	.login-container p {
		margin: 0; /* <p>タグのデフォルトの余白をなくす */
		padding: 2px 0; /* 内側の余白を調整 */
		line-height: 1.2; /* 行間を調整 */
	}

	/* ログインタイトルを広げる */
	.login-container h1 {
		width: 100%; /* 幅を100%に設定 */
		text-align: center; /* 中央寄せ */
		font-size: 24px; /* フォントサイズを大きくして見栄えを調整 */
	}

	.login-container table {
		width: 100%;
		text-align: left;
	}

	.login-container td {
		text-align: left; /* 各セルの内容を左寄せ */
		padding: 1px; /* セル内の余白を調整 */
	}
	/* form:inputとform:errorsの間の余白を小さくする */
	.login-container input[type="text"], .login-container input[type="password"]
		{
		width: 80%;
		margin-top: 5; /*フォームの高さ*/
		margin-bottom: 1px; /* 入力フォームとエラーメッセージの間の余白 */
		padding: 8px; /* フォーム内の余白を調整 */
	}

	/* エラーメッセージを囲んでいるform:errorsの余白調整 */
	.form-error {
		margin-top: 0;
		margin-bottom: 0;
		padding: 0;
	}

	/*ログインボタン*/
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
	/*ボタンをmarginするためのクラス*/
	.margin {
		margin-top: 60px;
	}
	/* 新規登録セクションのスタイル */
	.signup-container {
		display: flex;
		align-items: center;
		justify-content: center; /*だいぶ中央に寄る*/
		border: 2px solid #dedede;
		padding: 50px;
		margin: 50px;
		width: 400px; /* 新規登録セクションの幅 */
		height: 500px; /*高さ*/
		box-sizing: border-box;
		text-align: center;
	}

	/*新規登録ボタン*/
	.button {
		display: inline-block;
		padding: 10px 20px;
		background-color: #494949;
		color: #eee;
		text-align: center;
		text-decoration: none;
		border-radius: 5px;
		font-size: 16px;
		transition: background-color 0.3s ease;
	}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<div class="container">
		<!-- ログインセクション -->
		<div class="login-container">
			<h1>ログイン</h1>
			<form:form modelAttribute="loginFormModel">
				<div class="error">
					<c:out value="${message}" />
				</div>

				<table>
					<tr>
						<td>
							<div class="form-item">
								<label>メールアドレス<span class="required">*必須</span></label>
								<form:input path="mailaddress" class="form-control" />
								<form:errors path="mailaddress" element="p" cssClass="error-message" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<div class="form-item">
								<label>パスワード<span class="required">*必須</span></label>
								<form:password path="password" class="form-control" />
								<form:errors path="password" element="p" cssClass="error-message" />
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<p class="error-message">
								<c:out value="${error}" />
							</p>
							<!--  errorクラス -->
						</td>
					</tr>


				</table>
				<div class="margin">
					<input type="submit" value="ログインする" />
				</div>
			</form:form>
		</div>

		<!-- 初めてご利用の方セクション -->
		<div class="signup-container">
			<div>
				<h2>初めてご利用の方</h2>
				<p>お買い物には会員登録が必要です</p>
				<div class="margin">
					<p>
						<a href="regist" class="button">新規登録</a>
					</p>
				</div>
			</div>

		</div>
	</div>
	<jsp:include page="footer.jsp" />
</body>
</html>

