document.addEventListener("DOMContentLoaded", function() {
    var saleItems = document.querySelectorAll('.sale-item');

    saleItems.forEach(function(item) {
        var productId = item.dataset.productId;
        var originalPrice = parseFloat(item.dataset.price);
        var discountRate = parseFloat(item.dataset.discountRate);

        if (discountRate) {
            // 割引後の価格を計算
            var discountPrice = originalPrice * (1 - discountRate / 100);

            // 割引後の価格を表示
            var discountElement = item.querySelector('.discount-price');

            // もし discountElement が存在する場合
            if (discountElement) {
                // 割引価格を表示
                discountElement.innerHTML = `
                    ¥ ${discountPrice.toLocaleString()} (税抜)
                     (${Math.round(discountRate * 100)}% OFF)
                `;
            }
        }
    });
});
