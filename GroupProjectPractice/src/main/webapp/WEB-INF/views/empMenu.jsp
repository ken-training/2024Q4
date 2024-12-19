<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
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

    /* メニューリンク */
    .menu_links {
        display: flex;
        justify-content: space-between;
        max-width: 700px;
        margin-top: 40px;
    }

    /* リンクのスタイル */
    .menu_links a {
        display: block;
        align-items: center;
        text-decoration: none;
        color: #494949;
        margin: 15px;
        padding: 20px 5px;
        border-radius: 5px;
        font-size: 16px;
        width: 200px;
        background-color: #eee;
        text-align: center;
    }

    .menu_links a:hover{
    	background: #d4d4d4;
    	transition: 0.5s;
    }

    /* アイコンのスタイル */
    .menu_links i {
        margin-right: 10px;
        font-size: 45px;
    }

    /* フォームコンテナ */
    .menu_container {
        display: flex;
        flex-direction: column;
        align-items: center;
        border: 2px solid #dedede;
        padding: 50px;
        width: 70%;
        margin: 30px auto 10px; /* 上に70pxのマージン（ヘッダー分）と、下に80pxのマージン（フッター分） */
        background-color: white;
        border-radius: 8px;
        flex-grow: 1;
        height: 35vw;
    }
    /*改行入れるため*/
    .break-line {
        display: block;
        margin-top: 5px;
        }

	.small-text {
    font-size: 14px;
    color: #646464;
    display: inline-block;
}

</style>
</head>
<body>
	<div class="menu_container">
		<h1>従業員メニュー</h1>
		<c:if test="${not empty employeeLoginModel}">
			<p style="font-size: 25px;">ようこそ、${employeeLoginModel.emp_name}さん</p>
		</c:if>

		<div class="menu_links">
			<a href="/project/orderlog/unshipped"> <i class="fas fa-history"></i>
				<span class="break-line">注文履歴</span>
				<span class="small-text">(未発送)</span>
			</a>
			<a href="/project/orderlog/shipped"> <i class="fas fa-shipping-fast"></i>
				<span class="break-line">注文履歴</span>
				<span class="small-text">(発送済み,キャンセル)</span>
			</a>
			<a href="/project/emplogout"> <i class="fas fa-sign-out-alt"></i>
				<span class="break-line" style="margin-top: 1em;">ログアウト</span>
			</a>
		</div>
	</div>
</body>
</html>
