<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
	<link rel ="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
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
						<div class="category-select-box">
							<select name="category" class="category-select">
								<option value="ALL">全選択</option>
								<option value="c001">テーブル</option>
								<option value="c002">椅子</option>
								<option value="c003">本棚</option>
								<option value="c004">ベッド</option>
								<option value="c005">テーブルランプ</option>
								<option value="c006">ソファ</option>
							</select>
							<i class="fas fa-chevron-down"></i>
						</div>


						<!-- 商品名検索欄 -->
						<input type="text" class="search-input" name="keyword" autocomplete="off" />

						<!-- 検索ボタン -->
						<button class="search-button" type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>

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
					<div class="nav-box">
						<a href="${pageContext.request.contextPath}/login" class="navbar-link"><i class="fas fa-sign-in-alt"></i>
							<div class="nav-message">ログイン</div>
						</a>
					</div>
				</c:if>
				<c:if test="${not empty customerModel}">
					<div class="nav-box">
						<a href="${pageContext.request.contextPath}/logout" class="navbar-link"><i class="fas fa-sign-out-alt"></i>
							<div class="nav-message">ログアウト</div>
						</a>
					</div>
					<div class="nav-box">
						<a href="${pageContext.request.contextPath}/account" class="navbar-link"><i class="fa fa-user-circle" aria-hidden="true"></i>
							<div class="nav-message">マイページ</div>
						</a>
					</div>
				</c:if>
				<div class="nav-box">
					<a href="${pageContext.request.contextPath}/cart" class="navbar-link"><i class="fas fa-shopping-cart"></i>
						<div class="nav-message">カート</div>
					</a>
				</div>
			</nav>
		</c:if>
	</div>
</header>