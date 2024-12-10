<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="form" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<style>
.error{
    color:#ff0000;
}
table{
    border-collapse: separate;
    border-spacing: 10px;
}
</style>
</head>
<body>

    <h1>ログイン</h1>
    <hr/>
    <form:form modelAttribute="loginModel">
    <div class="error">${message}</div>
    <table>
    <tr>
        <td>メールアドレス *必須</td>
        <td>
            <form:input path="mailaddress" />
        </td>
        <td>
            <form:errors path="mailaddress" element="div" cssClass="error"/>
        </td>
    </tr>
    <tr>
        <td>パスワード *必須</td>
        <td>
            <form:password path="password"/>
        </td>
        <td>
            <form:errors path="password" element="div" cssClass="error"/>
        </td>
    </tr>
    <tr>
        <td colspan="3">
            <input type="submit" value="ログインする"/>
        </td>
    </tr>

    <h2>初めてご利用の方</h2>

    <td>お買い物には会員登録が必要です</td>

 			<input type="submit" value="新規登録"/>

    </table>
    </form:form>
    <hr/>

</body>
</html>
