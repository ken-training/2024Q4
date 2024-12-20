<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>TOPページ</title>
</head>
<body>
    <jsp:include page="header.jsp"></jsp:include>
    <main>
        <div class="top-container">
            <div>
				<c:if test="${message}">
					<p><c:out value="${message}" /></p>
				</c:if>
				<h2 class="item-label">SALE ITEMS</h2>
				<table id="sale-items-table">
                    <c:forEach var="saleProduct" items="${saleProductsList}" varStatus="status">
                        <c:if test="${status.index % 2 == 0}">
                            <tr> <!-- 2つの商品を1行にするために新しい行を開始 -->
                        </c:if>
                        <td>
                            <a href="${pageContext.request.contextPath}/product?product_id=${saleProduct.product_id}">
                                <div class="product_link">
                                    <img alt="セール品" src="resources/img/${saleProduct.image}.png" class="item-img">
                                    <div>
                                        <p class="product_message"  id="price" data-discount-rate="${saleProduct.discnt_rate}" data-price="${saleProduct.price}">
                                            <c:out value="${saleProduct.product_name}" /><br>
                                            <del>¥ <fmt:formatNumber value="${saleProduct.price}" pattern="#,###" /> <small>(税抜)</small></del>
                                            <span class="discount-price"></span>
                                        </p>
                                    </div>
                                </div>
                            </a>
                        </td>
                        <c:if test="${status.index % 2 != 0 || status.last}">
                            </tr> <!-- 2つの商品を1行にした後、行を閉じる -->
                        </c:if>
                    </c:forEach>
                </table>
            </div>

            <div>
                <h2 class="item-label">ITEMS</h2>
                <table>
                    <c:forEach var="allProduct" items="${allProductsList}" varStatus="status">
                        <c:if test="${status.index % 2 == 0}">
                            <tr> <!-- 2つの商品を1行にするために新しい行を開始 -->
                        </c:if>
                        <td>
                            <a href="${pageContext.request.contextPath}/product?product_id=${allProduct.product_id}">
                                <div class="product_link">
                                    <img alt="商品" src="resources/img/${allProduct.image}.png" class="item-img">
                                    <div>
                                        <p class="product_message">
                                            <c:out value="${allProduct.product_name}" /><br>
                                            ¥ <fmt:formatNumber value="${allProduct.price}" pattern="#,###" /> <small>(税抜)</small>
                                        </p>
                                    </div>
                                </div>
                            </a>
                        </td>
                        <c:if test="${status.index % 2 != 0 || status.last}">
                            </tr> <!-- 2つの商品を1行にした後、行を閉じる -->
                        </c:if>
                    </c:forEach>
                </table>
            </div>
        </div>
    </main>
    <jsp:include page="footer.jsp"></jsp:include>
</body>
</html>
