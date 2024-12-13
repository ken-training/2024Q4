<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<style>
.required{
	color:red;
}

.error {
    color: #ff0000;
}
.button {
    display: inline-block;
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    text-align: center;
    text-decoration: none;
    border-radius: 5px;
    font-size: 16px;
    transition: background-color 0.3s ease;
}
.button:hover {
    background-color: #0056b3;
}
</style>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<div class="container">

    <div style="display: inline-block;">
        <h1>ログイン</h1>

        <form:form modelAttribute="loginFormModel">
            <div class="error">${message}</div>
            <table>
                <tr>
                    <td> メールアドレス<span class="required">*必須</span></td>
                    <td>
                    <br>
                        <form:input path="mailaddress"/>
                    </td>
                </tr>
                <tr>
                   <td>パスワード<span class="required">*必須</span></td>
                    <td>
                    <br>
                        <form:password path="password"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:errors path="mailaddress" element="div" cssClass="error"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:errors path="password" element="span" cssClass="error" />
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        ${error}
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="ログインする"/>
                    </td>
                </tr>

            </table>
        </form:form>
    </div>

    <div style="display: inline-block;">
        <h2>初めてご利用の方</h2>

        <table>
            <tr>
                <td>お買い物には会員登録が必要です</td>
            </tr>
            <tr>
                <td>
                    <p><a href="regist" class="button">新規登録</a></p>
                </td>
            </tr>
        </table>
        <jsp:include page="footer.jsp"></jsp:include>
    </div>

</div>

</body>
</html>
