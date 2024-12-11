<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html >
<html lang="ja">
<head>
<meta charset="UTF-8">
<title>発送情報入力画面</title>
<style>
    span {
		font-size: small;
		color: red;
	}
	#credit {
		display: none
	}

	/* 固定されるdivのスタイル */
    #fixedDiv {
    	width: 25vw;  /* ウィンドウの幅の25% */
        position: fixed;
        top: 20px;  /* 上から20px */
        right: 20px;  /* 右から20px */
        background-color: lightblue;
        padding: 15px;
        border: 1px solid #000;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }
</style>
<script>
	//divを再表示する関数
	function showDiv(option) {
		// 現金を選択したら非表示
  		document.getElementById("credit").style.display = "none";

		// クレジットの場合表示する
  		if (option == "credit") {
  			document.getElementById("credit").style.display = "block";
		}
	}
</script>
</head>
<body>

 	<main>
 		<div id="fixedDiv">
 			<!-- カートの中身を表示 -->
    		カートの中身<br>
    		<div>
    			<p>画像１</p>
    			<p>商品名１</p>
    			<p>数量</p>
    		</div>
    		<div>
    			<p>画像２</p>
    			<p>商品名２</p>
    			<p>数量</p>
    		</div>

		</div>
 		<form:form modelAttribute="orderFormModel">
 			<div>
	 			<p>・氏名<span>*必須</span></p>
 				<p><form:input path="shipName"/></p>
 			</div>
 			<div>
	 			<p>・フリガナ</p>
 				<p><form:input path="shipPhonetic"/></p>
 			</div>
 			<div>
	 			<p>・電話番号<span>*必須</span></p>
 				<p>
 					<form:input path="shipPhone1" size="5" placeholder="2～4桁"/> -
 					<form:input path="shipPhone2" size="5" placeholder="2～4桁"/> -
 					<form:input path="shipPhone3" size="5" placeholder="4桁"/>
 				</p>
 			</div>
 			<div>
	 			<p>・郵便番号<span>*必須</span></p>
 				<p>
 					<form:input path="shipZip1" size="5" placeholder="3桁"/> -
 					<form:input path="shipZip2" size="5" placeholder="4桁"/>
 				</p>
 			</div>
 			<div>
	 			<p>・都道府県<span>*必須</span></p>
 				<p><form:input path="shipPrefecture"/></p>
 			</div>
 			<div>
	 			<p>・市区町村<span>*必須</span></p>
 				<p><form:input path="shipCity"/></p>
 			</div>
 			<div>
	 			<p>・番地<span>*必須</span></p>
 				<p><form:input path="shipBlock"/></p>
 			</div>
 			<div>
	 			<p>・マンション名</p>
 				<p><form:input path="shipBuilding"/></p>
 			</div>
 			<div>
	 			<p>お支払方法</p>
 				<p>
 					<form:radiobutton path="pay" value="cash" checked="true" onclick="showDiv('cash')"  />代金引換(現金)
 					<form:radiobutton path="pay" value="credit" onclick="showDiv('credit')"/>クレジットカード
 				</p>
 			</div>
 			<div id="credit">
	 			<p>・クレジットカード番号<span>*必須</span></p>
 				<p>
 					<form:input path="creditNum1" size="5" placeholder="0000"/> -
 					<form:input path="creditNum2" size="5" placeholder="0000"/> -
 					<form:input path="creditNum3" size="5" placeholder="0000"/> -
 					<form:input path="creditNum4" size="5" placeholder="0000"/>
 				</p>
	 			<p>・クレジットカード有効期限<span>*必須</span></p>
 				<p>
 					<form:select path="creditExpM" items="${creditExpMList }" /> /
 					<form:select path="creditExpY" items="${creditExpYList }" />
 				</p>
 			</div>
 			<div>
 				<p>
 					<input type="submit"  name="action" value="注文確認">
 					<input type="submit" name="action" value="戻る">
 				</p>
 			</div>
 		</form:form>
 	</main>

</body>
</html>