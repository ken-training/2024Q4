<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>従業員ログイン</title>
<style>

.required{
	color:red;
}
/*大枠*/
.container{
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
    width: 100%;  /* 幅を100%に設定 */
    text-align: center;  /* 中央寄せ */
    font-size: 24px; /* フォントサイズを大きくして見栄えを調整 */
}

.login-container table{
	width: 100%;
	text-align:left;
}
.login-container td {
    text-align: left; /* 各セルの内容を左寄せ */
    padding: 1px; /* セル内の余白を調整 */
}
/* form:inputとform:errorsの間の余白を小さくする */
.login-container input[type="text"],
.login-container input[type="password"] {
	width: 80%;
	margin-top:5;/*フォームの高さ*/
    margin-bottom: 1px; /* 入力フォームとエラーメッセージの間の余白 */
    padding: 8px; /* フォーム内の余白を調整 */
}

/* エラーメッセージの上部と下部の余白を調整 */
.error {
    margin-top: 3px; /* エラーメッセージの上部の余白を小さく */
    margin-bottom: 5px; /* エラーメッセージの下部の余白を調整 */
    font-size: 12px; /* エラーメッセージの文字サイズを小さく */
    color: #ff0000; /* 赤色のエラーメッセージ */
}

/*エラー全体*/
.error {
    color: #ff0000;	/*エラー出た場合の色*/
    font-size: 12px; /*文字サイズ*/
    margin-top: 5px; /*フォームから少し余白つけて表示*/
}

/* エラーメッセージを囲んでいるform:errorsの余白調整 */
.form-error {
    margin-top: 0;
    margin-bottom: 0;
    padding: 0;
}

/*ログインボタン*/
input[type="submit"]{
	display:inline-block;
	padding: 10px 20px;
	background-color: #494949;
	color:#eee;
	border-radius: 5px;
	text-align: center;
	font-size: 16px;
	cursor: pointer;
	border: none;
}
/*ボタンをmarginするためのクラス*/
.margin{
margin-top: 60px;

}

/*フッター*/
.footer{
text-align: center;	/*真ん中に寄せる*/
}

</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<!-- ログインセクション -->
		<div class="login-container">
			<h1>従業員ログイン</h1>
				<form:form modelAttribute="employeeLoginFormModel">
				 <div class="error"><c:out value="${message}"/></div>
					<table>
						<tr>
							<td><p>メールアドレス<span class="required">*必須</span></p></td>
						</tr>
						<tr>
							<td><p><form:input path="mailaddress"/></td>
						</tr>
						<tr>
							<td colspan="2">
								<form:errors path="mailaddress" element="div" cssClass="error"/>
							</td>
						</tr>
						<tr>
							<td><p>パスワード<span class="required">*必須</span></p></td>
						</tr>
						<tr>
							<td><p><form:password path="password"/></td>
						</tr>
						<tr>
							<td colspan="2">
								<form:errors path="password" element="span" cssClass="error" />
							</td>
						 </tr>
						<tr>
							<td colspan="2">
								<p class="error"><c:out value="${error}"/></p><!--  errorクラス -->
							</td>
						</tr>
					</table>
						<div class="margin"><input type="submit" value="ログインする"/></div>
				</form:form>
		</div>
	</div>
</body>
</html>