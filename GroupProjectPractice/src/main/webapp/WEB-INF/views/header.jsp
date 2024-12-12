<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<header>
	<div class="header-container">
		<!-- 企業ロゴ -->
		<div class="logo">
			<a href="${pageContext.request.contextPath}/top" class="logo-link">
				<img src="${pageContext.request.contextPath}/resources/img/logo/logo_SS.png" alt="企業ロゴ" class="logo-image">
			</a>
		</div>
		<!-- ログインと新規画面はアイコン以外を非表示 -->
		<c:if test="${pageContext.request.requestURI != '/project/WEB-INF/views/login.jsp'
			&& pageContext.request.requestURI != '/project/WEB-INF/views/regist.jsp'}">

			<!-- 会員情報関連は検索バー非表示 -->
			<c:if test="${pageContext.request.requestURI != '/project/WEB-INF/views/account.jsp'
				&& pageContext.request.requestURI != '/project/WEB-INF/views/accountUpdate.jsp'}">

				<!-- 検索バー -->
				<form:form modelAttribute="searchFormModel" action="${pageContext.request.contextPath}/search" method="get">
					<div class="search-bar">
						<!-- カテゴリ選択プルダウン -->
						<select name="category" class="category-select">
							<option value="ALL">全選択</option>
							<option value="c001">テーブル</option>
							<option value="c002">椅子</option>
							<option value="c003">本棚</option>
							<option value="c004">ベッド</option>
							<option value="c005">テーブルランプ</option>
							<option value="c006">ソファ</option>
						</select>

						<!-- 商品名検索欄 -->
						<input type="text" class="search-input" name="keyword" />

						<!-- 検索ボタン -->
						<button class="search-button" type="submit">検索</button>
					</div>
				</form:form>
			</c:if>

			<!-- ナビゲーション -->
			<nav class="navbar">
				<!-- こんにちは -->
				<p>こんにちは<br>
					<c:if test="${empty customerModel}">
			        	<b>ゲスト</b>さん
					</c:if>
					<c:if test="${not empty customerModel}">
						<b>${ customerModel.customer_name }</b>さん
			    	</c:if>
				</p>
				<c:if test="${empty customerModel}">
					<a href="${pageContext.request.contextPath}/login" class="navbar-link">ログイン</a>
				</c:if>
				<c:if test="${not empty customerModel}">
					<a href="${pageContext.request.contextPath}/logout" class="navbar-link">ログアウト</a>
			    	<a href="${pageContext.request.contextPath}/account" class="navbar-link">マイページ</a>
				</c:if>
				<a href="${pageContext.request.contextPath}/cart" class="navbar-link">カート</a>
			</nav>
		</c:if>
	</div>
</header>