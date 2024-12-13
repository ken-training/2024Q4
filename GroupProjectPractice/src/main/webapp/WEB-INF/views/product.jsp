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
	<jsp:include page="header.jsp"></jsp:include>
	<div class="container">
	<p><strong>${message }</strong></p>
	<form action="${pageContext.request.contextPath}/product" method="post">
		<h3>${productModel.getProduct_name() }</h3>
		<p>${productModel.getProduct_detail() }</p>
		<p>値段: ￥ <fmt:formatNumber value="${productModel.getPrice() }" pattern="#,###" />- (税抜)</p>
		<p>発売開始日: <fmt:formatDate value="${productModel.sale_start_date}" pattern="yyyy/MM/dd" /></p>
		<img src="resources/img/${productModel.getImage()}.png" alt="${productModel.getProduct_name() }"
								  width="200" >
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
	</form>
	<p>
		<a href="#" onclick="window.history.back(); return false;">一覧に戻る</a>
	</p>
	<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>