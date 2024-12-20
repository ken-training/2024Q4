<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SearchResult</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>
		<main>
			<div class="top-container">
				<div style=" width: 620px;">
					<div style="text-align: center;">
						<div style="text-align: start; margin-top: 10px;">
							<a href="/project/top" style="text-decoration: underline;">TOP</a> /
							<c:choose>
								<c:when test="${category == 'ALL'}">"全選択"</c:when>
								<c:when test="${category == 'c001'}">"テーブル"</c:when>
								<c:when test="${category == 'c002'}">"椅子"</c:when>
								<c:when test="${category == 'c003'}">"本棚"</c:when>
								<c:when test="${category == 'c004'}">"ベッド"</c:when>
								<c:when test="${category == 'c005'}">"テーブル"</c:when>
								<c:when test="${category == 'c006'}">"ソファ"</c:when>
							</c:choose>
						</div>
						<c:if test="${not empty keyword}">
							<span><span style="font-weight: bold; font-size: 1.5rem">"<c:out value="${keyword}" />"</span>の検索結果</span>
						</c:if>
					</div>
					<form:form action="/product" method="GET" >
					<c:if test="${message}">
						<p><c:out value="${message}"/></p>
					</c:if>
					<!-- 検索結果が空の場合の処理 -->
                        <c:if test="${empty productsList}">
                            <p style="text-align: center;">申し訳ありません。”<c:out value="${keyword}" />”に該当する商品は見つかりませんでした。</p>
                        </c:if>

                        <!-- 検索結果がある場合の表示 -->
                        <c:if test="${not empty productsList}">
                            <table>
                                <c:forEach var="product" items="${productsList}" varStatus="status">
                                    <c:if test="${status.index % 2 == 0}">
                                        <tr> <!-- 行の開始 -->
							</c:if>

							<td width="300px" style="border: none">
								<c:if test="${!empty productsList}">
									<a href="${pageContext.request.contextPath}/product?product_id=${product.product_id}">
										<div class="product_link">
											<img alt="商品画像" src="resources/img/${product.image}.png" class="item-img">
											<div id="price" data-discount-rate="${product.discnt_rate}" data-price="${product.price}">
												<p><c:out value="${product.product_name}" /><br>
													<span class="product-price">
													\ <fmt:formatNumber value="${product.price}" type="number" pattern="#,###" /> (税抜)
													</span>
													<span class="discount-price"></span>
												</p>
											</div>
										</div>
									</a>
								</c:if>
							</td>
							<!-- 2つ目の商品を並べる -->
							<c:if test="${status.index % 2 != 0}">
								</tr> <!-- 2つ目の商品が表示されたら、行を閉じる -->
							</c:if>
						</c:forEach>

						<!-- 最後に奇数個の商品が残った場合 -->
						<c:if test="${productsList.size() % 2 != 0}">
							<td></td> <!-- 空のセルを追加して行を閉じる -->
						</c:if>
					</table>
					</c:if>
					</form:form>
					</div>
				</div>
			</main>
			<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>