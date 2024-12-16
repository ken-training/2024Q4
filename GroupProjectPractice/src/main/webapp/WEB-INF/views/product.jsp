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
    align-items: center; /* 垂直方向に中央揃え */
    padding: 20px;
}

/* 商品詳細（商品名から発売開始日まで） */
.product-detail {
    flex: 1;  /* 残りのスペースを占める */
    padding-right: 20px;  /* 右側に余白 */
}

/* 画像のスタイル */
.product-image {
    flex: 2;  /* 商品画像は2倍 */
    text-align: center;  /* 画像は真ん中 */
}

.product-image img {
    width: 100%;  /* 親要素に合わせて画像が広がる */
    max-width: 500px;  /* 最大幅500px */
    height: auto;
}

/* 数量選択とカートに追加ボタン*/
.quantity-select {
    flex: 1;
    text-align: left;
    margin-top: 20px;


}

/* カートに追加ボタンや戻るリンクのスタイル */
input[type="submit"] {
    margin-top: 20px;
    padding: 10px 20px;
    background-color: #eee;
    cursor: pointer;
    border: none;
}

input[type="submit"]:hover {
    cursor: pointer;
    border: black;

}

a {
    color: #007BFF;
    text-decoration: none;
    margin-top: 50px;
}

</style>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
	<form action="${pageContext.request.contextPath}/product" method="post">
  <div class="product-info-wrapper">
	<div class="product-info" style="width:90%;">

	<!-- 左側に商品詳細（発売開始日） -->
	<div class="product-detail">
	<p><strong>${message }</strong></p>
		<h3>${productModel.getProduct_name() }</h3>
		<p>${productModel.getProduct_detail() }</p>
		<c:if test="${productModel.getDiscnt_is_valid() == 0 }">
			<p>値段: ￥ <fmt:formatNumber value="${productModel.getPrice() }" pattern="#,###" />- (税抜)</p>
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
		<p>発売開始日: <fmt:formatDate value="${productModel.sale_start_date}" pattern="yyyy/MM/dd" /></p>
	</div>

		<!-- 中央に商品画像 -->
	<div class="product-image">
		<img src="resources/img/${productModel.getImage()}.png" alt="${productModel.getProduct_name() }"
								  width="200" >
	</div>
		<!-- 右側に数量選択 -->
	<div class="quantity-select">
		<p>数量:
			<select name="quantity">
				<c:forEach var="n" begin="1" end="10">
					<option value="${n}">
					${n}
					</option>
				</c:forEach>
			</select>
		</p>
		<input type="hidden" name="product_id" value="${productModel.getProduct_id() }">
		<input type="submit" value="カートに追加">
		<p>
	<!-- 	<a href="#" onclick="goBack(); return false;">一覧に戻る</a> -->
	<a href="${previousUrl }">一覧に戻る</a>
	</p>
		</div>
		</div>
		</div>
	</form>

	<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>