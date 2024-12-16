<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録</title>
<style>
h1 {

}
h2 {

}
h3 {
	display: inline; /* インライン要素として表示 */
	color: red;
}
.errors{
	color:red;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
		<h1>新規会員登録</h1>
		<form:form modelAttribute="customerModel">
			<div>
				<label>氏名：</label>
				<form:input path="customer_name" size="20" /><span><h3>*必須</h3></span><br>
				</h3><form:errors path="customer_name" element="span" cssClass="errors" /></h3>
			</div>
			<div>
				<label>メールアドレス：</label>
				<form:input path="mail" size="40" /><span><h3>*必須</h3></span><br>
				</h3><form:errors path="mail" element="span" cssClass="errors" /></h3>
				<h3><c:out value="${ error }" /></h3>
			</div>
			<div>
				<label>パスワード：</label>
				<form:input path="password" size="30" /><span><h3>*必須</h3></span><br>
				</h3><form:errors path="password" element="span" cssClass="errors" /></h3>
			</div>
			<div>
				<p><input type="submit" value="登録する" /></p>
			</div>
		</form:form>
	</div>
	<h2>※郵便番号、住所、電話番号、生年月日、<br>クレジットカード番号、有効期限はあとでマイページから登録できます</h2>
	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>