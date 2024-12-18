<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>商品情報詳細ページ</title>
<style>

/* 商品情報全体を中央寄せ */
.product-info-wrapper {
    display: flex; /* 横並びのためのフレックスボックス */
    justify-content: center; /* 水平方向に中央揃え */
    padding: 20px;
}

/* 商品情報全体を横並びに配置するためのフレックスボックス */
.product-info {
    display: flex;/*横並び*/
    justify-content: space-between;/*container内を水平方向に配置する*/
    align-items: flex-start; /* 垂直方向の位置を上寄せ */
    padding: 20px;
    position: relative; /*位置固定*/
}

/* 商品詳細（商品名から発売開始日まで） */
.product-detail {
    flex: 1;  /* 残りのスペースを占める */
    padding-left: 50px;  /* 左に余白 */
    text-align: left; /*左側に寄せる*/
    display: flex;
    flex-direction: column; /*商品詳細と数量選択を縦並びにする*/
    order: 2; /*商品詳細を右側に表示させるため順番変更*/
    width: 650px;
}

/* 画像のスタイル */
.product-image {
    flex: 0 0 auto;  /* 商品画像の幅 */
    text-align: center;  /* 画像真ん中 */

}

.product-image img {
    width: 150%;  /* 親要素に合わせて画像が広がる */
    max-width: 500px;  /* 最大幅500px */
    height: auto;
}
/* 値段部分の表示 */
.price-value {
    font-size: 24px;
    font-weight: bold; /* 太字 */
}

/* 発売開始日の表示 */
.release-date {
    font-size: 14px;
}


/* 数量選択,カートに追加,一覧に戻る*/
.quantity-select,add-to-cart,.back {
    margin-top: 20px;
}

/* カートに追加ボタンとメッセージ */
.add-to-cart-message {
    display: flex;
    align-items: center;  /* 垂直方向で中央揃え */
    gap: 10px;  /* ボタンとメッセージの間隔 */
}

/* <p>タグmessage */
.message-text {
    font-size: 16px;
    background-color: #2f4f4f;
    color: #f0f8ff;
    padding: 5px 10px;  /* 内側の余白を追加 */
    margin: 0;

}

/* カートに追加ボタンや戻るリンクのスタイル */
input[type="submit"] {
    padding: 10px 20px;
    background-color: #eee;
    cursor: pointer;
    border: none;
}
.back{
	text-decoration: underline; /* 常にアンダーライン表示 */
    color: #000;
}

</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<form action="/project/product" method="post">
  <div class="product-info-wrapper">
	<div class="product-info">

	<!-- 左側に商品画像 -->
	<div class="product-image">
		<img src="resources/img/${productModel.getImage()}.png" alt="${productModel.getProduct_name() }"
								  width="200" >
	</div>

	<!-- 右側に商品詳細（発売開始日） -->
	<div class="product-detail">
		<h3><c:out value="${productModel.getProduct_name() }"/></h3>

	<!-- 商品説明 -->
		<p id="text"><c:out value="${productModel.getProduct_detail() }"/></p>
		 	<script>
		 	 //。の後に改行を追加
	        var textElement = document.getElementById('text');
	        var text = textElement.innerText;
	        textElement.innerHTML = text.replace(/。/g, '。<br>');
    		</script>

		<c:if test="${productModel.getDiscnt_is_valid() == 0 }">
			<p>値段: <span class="price-value">￥ <fmt:formatNumber value="${productModel.getPrice() }" pattern="#,###" /></span>(税抜)</p>
		</c:if>
		<c:if test="${productModel.getDiscnt_is_valid() == 1 }">
			<div>
			    <p class="product_message"  id="price" data-discount-rate="${productModel.getDiscnt_rate()}"
			    data-price="${productModel.price}">
			        値段:<del> ￥ <fmt:formatNumber value="${productModel.price}" pattern="#,###" /> <small>(税抜)</small></del>
			        <span class="discount-price"></span>
			    </p>
			</div>
		</c:if>
		<p class="release-date">発売開始日: <fmt:formatDate value="${productModel.sale_start_date}" pattern="yyyy/MM/dd" /></p>


		<!--数量、カートに追加、一覧に戻る -->
	<div class="quantity-select">
		<p>数量:
			<select name="quantity">
				<c:forEach var="n" begin="1" end="${limit_num }">
					<option value="${n}">
					<c:out value="${n}"/>
					</option>
				</c:forEach>
			</select>
		</p>
	</div>


	<div class="add-to-cart-message">
		<input type="hidden" name="product_id" value="${productModel.getProduct_id() }">
		<input type="submit" value="カートに追加">
		<c:if test="${not empty message}">
		<p class="message-text"><strong><c:out value="${message}"/></strong></p>
		</c:if>

	</div>

	<div class="back">
	<p>
	<!-- 	<a href="#" onclick="goBack(); return false;">一覧に戻る</a> -->
	<a href='${previousUrl }'>一覧に戻る</a>
	</p>
	</div>
	</div>
	</div>
  </div>
	</form>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>