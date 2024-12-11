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
</head>
<body>
	<div class="container">
		<h3>${productModel.getProduct_name() }</h3>
		<p>${productModel.getProduct_detail() }</p>
		<p>値段: ￥ <fmt:formatNumber value="${productModel.getPrice() }" pattern="#,###" />(税抜)</p>
		<p>発売開始日: ${productModel.getSale_start_date() }</p>
		<img src="resources/img/${productModel.getImage()}.png" alt="${productModel.getProduct_name() }"
								  width="200" >
		<p>数量:
			<select name="quantity">
				<c:forEach var="n" begin="0" end="10">
					<option value="${n}">
					${n}
					</option>
				</c:forEach>
			</select>
		</p>
		<input type="submit" value="カートに追加">
		<a href="#">商品検索結果に戻る</a>
	</div>
</body>
</html>