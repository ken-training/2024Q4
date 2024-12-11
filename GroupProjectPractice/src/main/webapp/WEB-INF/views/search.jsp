<!-- ----------------------------------- -->
<!-- -------キー名を確認すること-------- -->
<!-- セール品と他をどうわけるか -->
<!-- ----------------------------------- -->


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SearchResult</title>

</head>
<body>
	<div class="container">
		<!-- <jsp:include page="header.jsp"></jsp:include> -->
		<main>
			<p>カテゴリ　：<c:out value="category" /></p>
			<p>キーワード：<c:out value="keyword" /></p>
			<table>
				<c:forEach var="product" items="${productsList}" varStatus="status">
					<c:if test="${status.index % 2 == 0}">		<!-- 行切り替えにindexを使用 -->
						<tr>
					</c:if>
							<td>
								<c:if test="${!empty productsList}">
									<img src="${product.image}" alt="商品画像">
									<p><c:out value="${product.name}" /><br></p>
									<p><c:out value="${product.price}" /><br></p>
								</c:if>
							</td>
					<!-- 2列目までセットしたら行を閉じる -->
					<c:if test="${status.index % 2 == 1}">
						</tr>
					</c:if>
				</c:forEach>

				<!-- 奇数列終わりの時 -->
				<c:if test="${productList.size() % 2 != 0}">
            		<td></td> <!-- 空のセルを追加して行を閉じる -->
        		</c:if>
			</table>
		</main>
		<!-- <jsp:include page="footer.jsp"></jsp:include> -->
	</div>
</body>
</html>
