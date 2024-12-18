<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>従業員メニュー</title>
<style>

/* フッターのスタイル */
.footer {
	bottom: 0;
	left: 0;
	width: 100%;
	text-align: center;
	padding: 10px 0;
}
/* フォームコンテナ */
.menu_container {
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

</style>
</head>
<body>
	<div class="menu_container">
		<h1>メニュー</h1>
			<c:if test="${not empty employeeLoginModel}">
				<p>ようこそ、${employeeLoginModel.emp_name} さん</p>
			</c:if>
			<a href="/project/orderlog/unshipped">注文履歴(未発送分)</a>
			<a href="/project/orderlog/shipped">注文履歴(発送済み分,キャンセル分)</a>
			<a href="/project/emplogout">ログアウト</a>
	</div>

	<div class="footer">
		<jsp:include page="footer.jsp" />
	</div>

</body>
</html>