//window.onloadは画像まで読み込まれたら、
//DOMContentLoadedはHTMLの構造が読み込まれた段階で処理が開始(こっちのが処理早い)

document.addEventListener("DOMContentLoaded", function() {

	var saleItems = document.querySelectorAll('#price');

	    saleItems.forEach(function(item) {
	        var originalPrice = parseFloat(item.dataset.price);
	        var discountRate = parseFloat(item.dataset.discountRate);

	        if (discountRate) {
	            // 割引後の価格を計算
	            var discountPrice = originalPrice * (1 - discountRate);

	            // 割引後の価格を表示
	            var discountElement = item.querySelector('.discount-price');

				//取り消し線追加
				var delPrice = item.querySelector('.product-price');
				var del = document.createElement('del');

				if(delPrice){
					delPrice.innerHTML = "<del>" + delPrice.innerHTML + "</del>";
				}

	            // もし discountElement が存在する場合
	            if (discountElement) {
	                // 割引価格を表示
	                discountElement.innerHTML = `
	                    <br>↓<br>¥ ${discountPrice.toLocaleString()} (税抜)
	                     (${Math.round(discountRate * 100)}% OFF)
	                `;
	            }
	        }
	    });
});
