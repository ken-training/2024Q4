<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規会員登録</title>
<style>
h1 {
	font-size: 24px;
}
h2 {
	font-size: 18px;
}
h3 {
	display: inline;
	color: red;
}
.errors{
	color:red;
	font-size: 0.9em;
}
form {
	margin: 20px;
	padding: 10px;
	border: 1px solid #ddd;
	border-radius: 5px;
}
label {
	font-weight: bold;
}
input {
	margin: 5px 0 10px;
}
.regist_container {
	width: 100%;
	max-width: 1200px; /* 最大幅 */
	margin: 0 auto;    /* 自動で左右のマージンを調整して中央揃え */
}
.regist_test{
	display: flex;               /* フレックスボックスを有効にする */
	/* flex-direction: column;       子要素を縦に並べる */
	align-items: center;         /* 水平方向に中央揃え */
	justify-content: center;     /* 垂直方向に中央揃え */
	text-align: center;          /* テキストを中央揃え */
}
</style>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <div class="regist_container">
        <form:form modelAttribute="customerModel">
            <div class ="regist_test">
				<h1>新規会員登録</h1>
                <label>氏名：</label>
                <form:input path="customer_name" size="20" /><span><h3>*必須</h3></span><br>
                <form:errors path="customer_name" element="span" cssClass="errors" />
            </div>
            <div class ="regist_test">
                <label>メールアドレス：</label>
                <form:input path="mail" size="40" /><span><h3>*必須</h3></span><br>
                <form:errors path="mail" element="span" cssClass="errors" />
            </div>
            <div class ="regist_test" >
                <label>パスワード：</label>
                <form:input path="password" size="30" /><span><h3>*必須</h3></span><br>
                <form:errors path="password" element="span" cssClass="errors" />
            </div>
            <div class ="regist_test">
                <p><input type="submit" value="登録する" /></p>
            </div>
            <div class ="regist_test" >
            	<h2>※郵便番号、住所、電話番号、生年月日、<br>クレジットカード番号、有効期限はあとでマイページから登録できます</h2>
            </div>
        </form:form>
    </div>
    <div></div>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>