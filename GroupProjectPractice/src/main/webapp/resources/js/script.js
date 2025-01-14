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

			if (delPrice) {
				delPrice.innerHTML = "<del>" + delPrice.innerHTML + "</del>";
			}

			// もし discountElement が存在する場合
			if (discountElement) {
				// 割引価格を表示
				discountElement.innerHTML = `
	                    <br>¥ ${discountPrice.toLocaleString()} (税抜)
	                     (${Math.round(discountRate * 100)}% OFF)
	                `;
			}
		}
	});
	if(document.querySelector('.radio-buttons input[type="radio"]:checked')){
		showDiv(document.querySelector('.radio-buttons input[type="radio"]:checked').value);
	}
	errorDesign();
});

	//クレジットカードフォーム表示（発送情報入力）
	function showDiv(option) {
		// 現金を選択したら非表示
		document.getElementById("creditDiv").style.display = "none";

		// クレジットの場合表示する
		if (option == "credit") {
			document.getElementById("creditDiv").style.display = "block";
		}
	}

	//クレジットカードフォーム表示（発送情報入力）
	function showCreditFormDiv(option) {
		// 「登録済みクレジットカードを利用する」を選択したら非表示
		document.getElementById("creditFormDiv").style.display = "none";
		document.getElementById("creditFormDiv2").style.display = "block";

		// 「クレジットカード番号を入力する」を選択した場合
	   if (option == "creditForm") {
    	    document.getElementById("creditFormDiv").style.display = "block";
			document.getElementById("creditFormDiv2").style.display = "none";
    	}
	}

	//ラジオボタンのクリック時に状態を確認する
//	document.querySelectorAll('input[name="creditForm"]').forEach(function(radioButton) {
//    	radioButton.addEventListener('click', function() {
//    	        	console.log(radioButton.value + " is checked: " + radioButton.checked);
//	   	});
//	});


	function check(){
		var elem = document.fm.elements;
		var str = "";
		var cnt = 0;

		// チェック対象のインデックスを指定(名前、電話番号、郵便番号、住所)
	    var checkNullIndices = [0, 2, 3, 4, 5, 6, 7, 8, 9];
		var checkName = ["shipName-notnull", "shipPhone-notnull", "shipZip-notnull", "shipPrefecture-notnull"
							, "shipCity-notnull", "shipBlock-notnull", "shipPhone-error", "shipZip-error" ];
		console.log(checkName.toString());

		// ラジオボタンがクレジットカードを選択している場合、チェック対象のインデックスを追加 (カード番号、有効期限)
		var selectedRadio = document.querySelector('input[name="creditForm"]:checked');
		if(selectedRadio && selectedRadio.value === 'creditForm') {
			for (var i = 15; i <= 20; i++){
				checkNullIndices.push(i);
			}
			checkName.push("creditNum-notnull");
			checkName.push("creditNum-error");
			checkName.push("creditExp-error");
			console.log(checkName.toString());
		}

		// エラーメッセージをリセット
		for (var i = 0; i < checkName.length; i++){
			document.getElementById(checkName[i]).innerHTML  = '';
		}
		// エラーのクラスを削除
		for (var i = 0; i < elem.length; i++){
			elem[i].classList.remove('error');
		}
		console.log(checkNullIndices.toString());

		for (var i= 0; i < elem.length ; i++){
			console.log("elem[" + i + "]:" + elem[i].value);

			// チェック対象インデックスに該当する場合、未入力チェック
	        if (checkNullIndices.includes(i) && elem[i].value == "") {
	        	switch(i){
	   	    	case 0:	// 氏名
					document.getElementById(checkName[0]).innerHTML  = '<i class="fas fa-exclamation-circle"></i>&nbsp;必須入力です';
					elem[i].classList.add('error'); // エラーのクラスを追加
	      			cnt++;
					break;
	       		case 2:	// 電話番号
	       		case 3:	// 電話番号
	       		case 4:	// 電話番号
					document.getElementById(checkName[1]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;必須入力です';
	       			elem[i].classList.add('error'); // エラーのクラスを追加
					cnt++;
					break;
	       		case 5:	// 郵便番号
	       		case 6:	// 郵便番号
					document.getElementById(checkName[2]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;必須入力です';
	       			elem[i].classList.add('error'); // エラーのクラスを追加
					cnt++;
					break;
	       		case 7:	// 都道府県
					document.getElementById(checkName[3]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;必須入力です';
	       			elem[i].classList.add('error'); // エラーのクラスを追加
					cnt++;
					break;
	       		case 8:	// 市区町村
					document.getElementById(checkName[4]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;必須入力です';
	       			elem[i].classList.add('error'); // エラーのクラスを追加
					cnt++;
					break;
	       		case 9:	// 番地
					document.getElementById(checkName[5]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;必須入力です';
	        		elem[i].classList.add('error'); // エラーのクラスを追加
					cnt++;
					break;
	        	case 15:	// クレジットカード番号
	        	case 16:	// クレジットカード番号
	        	case 17:	// クレジットカード番号
	        	case 18:	// クレジットカード番号
					document.getElementById(checkName[8]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;必須入力です';
	        		elem[i].classList.add('error'); // エラーのクラスを追加
	        		cnt++;
					break;
	        	}
	        }
		}

		// 電話番号の数字、文字数チェック
		if( !elem[2].value.match(/^\d{2,4}$/) || !elem[3].value.match(/^\d{2,4}$/) || !elem[4].value.match(/^\d{4}$/)
				|| elem[2].value.length + elem[3].value.length + elem[4].value.length >= 12){
			document.getElementById(checkName[6]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;電話番号が無効です';
			elem[2].classList.add('error');	cnt++;
			elem[3].classList.add('error');
			elem[4].classList.add('error');
			cnt++;
		}

		// 郵便番号の数字、文字数チェック
		if( !elem[5].value.match(/^\d{3}$/) || !elem[6].value.match(/^\d{4}$/)){
			document.getElementById(checkName[7]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;郵便番号が無効です';
			elem[5].classList.add('error');	cnt++;
			elem[6].classList.add('error');
			cnt++;
		}

		if(selectedRadio && selectedRadio.value === 'creditForm') {
			// クレジットカード番号の数字、文字数チェック
			// アスタリスクの前後に半角空白がないとマッチしない
		//	if(!(elem[15].value.match(/^\s*\*\*\*\*\s*$/) && elem[16].value.match(/^\s*\*\*\*\*\s*$/)
		//		&& elem[17].value.match(/^\s*\*\*\*\*\s*$/) && elem[18].value.match(/^\s*\d{4}\s*$/))){
		        if( !elem[15].value.match(/^\d{4}$/) || !elem[16].value.match(/^\d{4}$/)
		    			|| !elem[17].value.match(/^\d{4}$/) || !elem[18].value.match(/^\d{4}$/)) {
					document.getElementById(checkName[9]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;カード番号が無効です';
					cnt++;
		        }
		//    }

			// クレジットカード有効期限チェック
			//現在の日付を取得
	        var today = new Date();
	        // 現在の年を取得
	        var year = today.getFullYear(); // 年（4桁）
			var month = today.getMonth() + 1; // 月（0から始まるので1を足す）

	        // 例）2024 + 12 →  202412
	        var nowYm = "" + year + month;

			// 入力された有効期限を取得
			var value = elem[20].value
			if(elem[19].value < 10){
				value += "0" + elem[19].value;
			} else {
				value += elem[19].value;
			}
			console.log("value" + value);
			if(elem[20].value == '----' || elem[19].value == '--'){
				document.getElementById(checkName[10]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;必須入力です';
				cnt++;
			}else{
				/* 現在と入力値文字列を大小比較し、
				現在 >= 入力値 であるなら errorメッセージ を表示	*/
				if(parseInt(nowYm) > parseInt(value)){
					document.getElementById(checkName[10]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;有効期限が切れています';
					cnt++;
				}
			}
		}

		// 住所の空白チェック
		if( elem[7].value.match(/\s|\u3000/)){
			document.getElementById(checkName[3]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;空白を含まないよう入力してください';
			cnt++;
		}
		if( elem[8].value.match(/\s|\u3000/)){
			document.getElementById(checkName[4]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;空白を含まないよう入力してください';
			cnt++;
		}
		if( elem[9].value.match(/\s|\u3000/)){
			document.getElementById(checkName[5]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;空白を含まないよう入力してください';
			cnt++;
		}

		if(cnt > 0) {
			return false;
		} else {
			return true;
		}
	}



	// 会員情報変更の入力チェック
	function updateCheck() {
	    var elem = document.fm.elements;
	    var cnt = 0;
	    var checkName = ["mail-error", "password-error", "zip-error", "phone-error", "creditNum-error", "creditExp-error",
	    				"prefecture-error", "city-error", "block-error",];
	    // エラーメッセージをリセット
	    for (var i = 0; i < checkName.length; i++) {
	        document.getElementById(checkName[i]).innerHTML = '';
	    }
	    for (var i= 0; i < elem.length ; i++){
			console.log("elem[" + i + "]:" + elem[i].value);
	    }
		// メールアドレスの文字数チェック
	    if(elem[2].value != ""){	// 未入力の場合は除く
			if( !elem[2].value.match(/^[\s\S]{3,32}$/)){
				document.getElementById(checkName[0]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp3文字以上32文字以下で入力してください';
				cnt++;
			}
	    }
		// パスワードの文字数チェック
	    if(elem[3].value != ""){	// 未入力の場合は除く
			if( !elem[3].value.match(/^[\s\S]{6,15}$/)){
				document.getElementById(checkName[1]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp6文字以上15文字以下で入力してください';
				cnt++;
			}
	    }
		// 郵便番号の数字、文字数チェック
	    if(!(elem[4].value == "" && elem[5].value == "")){	// すべて未入力の場合は除く
			if( !(elem[4].value.match(/^\d{3}$/) && elem[5].value.match(/^\d{4}$/)) ){
				document.getElementById(checkName[2]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp郵便番号が無効です';
				cnt++;
			}
		}
		// 住所の空白チェック
	    if(!(elem[6].value == "" && elem[7].value == "" && elem[8].value == "")){	// すべて未入力の場合は除く
			if( elem[6].value.match(/\s|\u3000/) || elem[6].value == ""){
				document.getElementById(checkName[6]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;空白を含まないよう入力してください';
				cnt++;
			} else if (elem[6].value.match(/\s|\u3000/)){
			}
			if( elem[7].value.match(/\s|\u3000/) || elem[7].value == ""){
				document.getElementById(checkName[7]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;空白を含まないよう入力してください';
				cnt++;
			}
			if( elem[8].value.match(/\s|\u3000/) || elem[8].value == ""){
				document.getElementById(checkName[8]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp;空白を含まないよう入力してください';
				cnt++;
			}
		}
	    // 電話番号の数字、文字数チェック
	    if(!(elem[10].value == "" && elem[11].value =="" && elem[12].value == "")){	// すべて未入力の場合は除く
		    if (!elem[10].value.match(/^\d{2,4}$/) ||
		    	!elem[11].value.match(/^\d{2,4}$/) ||
	    		!elem[12].value.match(/^\d{4}$/) ||
	    		elem[10].value.length + elem[11].value.length + elem[12].value.length >= 12) {
		        document.getElementById(checkName[3]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp電話番号が無効です';
		        cnt++;
	    	}
	    }
	 	// クレジットカード番号の数字、文字数チェック
	    if(!(elem[16].value == "" && elem[17].value =="" && elem[18].value == "" && elem[19].value == "")){	// すべて未入力の場合は除く
	       	if( !elem[16].value.match(/^\d{4}$/) || !elem[17].value.match(/^\d{4}$/)
    				|| !elem[18].value.match(/^\d{4}$/) || !elem[19].value.match(/^\d{4}$/)) {
				document.getElementById(checkName[4]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbspカード番号が無効です';
				cnt++;
        	}
        }
	     // クレジットカード有効期限チェック
			//現在の日付を取得
	        var today = new Date();
	        // 現在の年を取得
	        var year = today.getFullYear(); // 年（4桁）
			var month = today.getMonth() + 1; // 月（0から始まるので1を足す）
	        // 例）2024 + 12 →  202412
	        var nowYm = "" + year + (month < 10 ? "0" + month : month);  // 月が1桁の場合は0を追加
			// 入力された有効期限を取得
			var value = elem[21].value
			if(elem[20].value < 10){
				value += "0" + elem[20].value;
			} else {
				value += elem[20].value;
			}
			console.log("value" + value);
			/* 現在と入力値文字列を大小比較し、
			現在 >= 入力値 であるなら errorメッセージ を表示	*/
			if(parseInt(nowYm) > parseInt(value)){
				document.getElementById(checkName[5]).innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp有効期限が切れています';
				cnt++;
			}
	    // エラーがあれば送信を防止
	    if (cnt > 0) {
	        return false;  // フォームの送信をキャンセル
	    } else {
	        return true;   // フォームを送信
	    }
	}


//エラー表記
function errorDesign() {
	var errormess = document.querySelectorAll(".error-message");
	errormess.forEach(function(errorMessage) {
		if (errorMessage.previousElementSibling) {
			errorMessage.previousElementSibling.classList.remove('error');
		}
		// メッセージが空でない場合にアイコンを追加
		if (errorMessage.innerHTML.trim() !== "") {
			errorMessage.innerHTML = '<i class="fas fa-exclamation-circle"></i>&nbsp' + errorMessage.innerHTML;
			errorMessage.previousElementSibling.classList.add('error'); // エラーのクラスを追加
		}
	})
}