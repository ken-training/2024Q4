<!-- ※確認※　注文確認画面から遷移 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<style type="text/css">
.container{
	display: flex;
	flex-direction: column;
	align-items: center;
}
.to_top{
	text-decoration: underline; /* 常にアンダーライン表示 */
    color: #000;
}
</style>
<title>注文完了</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<h2>注文が完了しました</h2>
		<p>またのご利用をお待ちしております</p>
		<p><div class="to_top">
			<a href="/project/top">トップページに戻る</a>
		</div></p>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>