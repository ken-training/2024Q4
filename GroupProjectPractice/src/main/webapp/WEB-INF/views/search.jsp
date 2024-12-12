<!-- ----------------------------------- -->
<!-- -------キー名を確認すること-------- -->
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
		<main>

			<p>カテゴリ　：<c:out value="${category}" /><br></p>
			<p>キーワード：<c:out value="${keyword}" /><br></p>

			<form:form action="/product" method="GET" >
			<table>
				<c:forEach var="product" items="${productsList}" varStatus="status">
					<c:if test="${status.index % 2 == 0}">		<!-- 行切り替えにindexを使用 -->
						<tr>
					</c:if>
							<td width="300px" style="border: solid 1px">
								<c:if test="${!empty productsList}">
									<a href="${pageContext.request.contextPath}/product?product_id=${product.product_id}">
										<div style="display: inline-block;">
											<img alt="セール品" src="resources/img/${product.image}.png"style="width: 100px;">
											<p><br><c:out value="${product.product_name}" /><br></p>
											<p><c:out value="${product.price}" /><br></p>
										</div>
									</a>
								</c:if>
							</td>
						</tr>
				</c:forEach>

				<!-- 奇数列終わりの時 -->
				<c:if test="${productsList.size() % 2 != 0}">
            		<td></td> <!-- 空のセルを追加して行を閉じる -->
        		</c:if>
			</table>
			</form:form>
		</main>
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
</body>
</html>
