/******************************************************************/
//バリデーションのみ追記
//グループを確認すること
/******************************************************************/
package jp.ken.project.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class OrderFormModel implements Serializable {
	//氏名
	@NotEmpty(message = "必須入力です")
	private String shipName;

	//電話番号
	@Size(max = 12,message = "電話番号が無効です")
	private String shipPhonetic;

	//電話番号セル1
	@NotEmpty(message = "必須入力です")
/** カスタマイズ…nullとEmptyを許すヤツ **/
	//@SizeAllowingEmpty(min = 2, max = 4,message = "電話番号が無効です")
	private int shipPhone1;

	//電話番号セル2
	@NotEmpty(message = "必須入力です")
/** カスタマイズ…nullとEmptyを許すヤツ **/
	//@SizeAllowingEmpty(min = 2, max = 4,message = "電話番号が無効です")
	private int shipPhone2;

	//電話番号セル3
	@NotEmpty(message = "必須入力です")
/** カスタマイズ…nullとEmptyを許すヤツ **/
	//@SizeAllowingEmpty(min = 4, max = 4,message = "電話番号が無効です")
	private int shipPhone3;

	//郵便番号セル1
	@NotEmpty(message = "必須入力です")
/** カスタマイズ…nullとEmptyを許すヤツ **/
	//@SizeAllowingEmpty(min = 3, max = 3,message = "郵便番号が無効です")
	private int shipZip1;

	//郵便番号セル2
	@NotEmpty(message = "必須入力です")
/** カスタマイズ…nullとEmptyを許すヤツ **/
	//@SizeAllowingEmpty(min = 4, max = 4,message = "郵便番号が無効です")
	private int shipZip2;

	//都道府県
	@NotEmpty(message = "必須入力です")
	@Pattern(regexp = "^[^\\s]*$", message = "空白を含まないよう入力してください")
	private String shipPrefecture;

	//市区町村
	@NotEmpty(message = "必須入力です")
	@Pattern(regexp = "^[^\\s]*$", message = "空白を含まないよう入力してください")
	private String shipCity;

	//番地
	@NotEmpty(message = "必須入力です")
	@Pattern(regexp = "^[^\\s]*$", message = "空白を含まないよう入力してください")
	private String shipBlock;

	//建物名
	@Pattern(regexp = "^[^\\s]*$", message = "空白を含まないよう入力してください")
	private String shipBuilding;

	//支払方法
	private String pay;

	//クレカ番号セル1
	private int creditNum1;

	//クレカ番号セル2
	private int creditNum2;

	//クレカ番号セル3
	private int creditNum3;

	//クレカ番号セル4
	private int creditNum4;

/*** 有効期限切れチェック ***/
	//クレカ有効期限_月
	private String creditExpM;

	//クレカ有効期限_年
	private String creditExpY;

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getShipPhonetic() {
		return shipPhonetic;
	}

	public void setShipPhonetic(String shipPhonetic) {
		this.shipPhonetic = shipPhonetic;
	}

	public int getShipPhone1() {
		return shipPhone1;
	}

	public void setShipPhone1(int shipPhone1) {
		this.shipPhone1 = shipPhone1;
	}

	public int getShipPhone2() {
		return shipPhone2;
	}

	public void setShipPhone2(int shipPhone2) {
		this.shipPhone2 = shipPhone2;
	}

	public int getShipPhone3() {
		return shipPhone3;
	}

	public void setShipPhone3(int shipPhone3) {
		this.shipPhone3 = shipPhone3;
	}

	public int getShipZip1() {
		return shipZip1;
	}

	public void setShipZip1(int shipZip1) {
		this.shipZip1 = shipZip1;
	}

	public int getShipZip2() {
		return shipZip2;
	}

	public void setShipZip2(int shipZip2) {
		this.shipZip2 = shipZip2;
	}

	public String getShipPrefecture() {
		return shipPrefecture;
	}

	public void setShipPrefecture(String shipPrefecture) {
		this.shipPrefecture = shipPrefecture;
	}

	public String getShipCity() {
		return shipCity;
	}

	public void setShipCity(String shipCity) {
		this.shipCity = shipCity;
	}

	public String getShipBlock() {
		return shipBlock;
	}

	public void setShipBlock(String shipBlock) {
		this.shipBlock = shipBlock;
	}

	public String getShipBuilding() {
		return shipBuilding;
	}

	public void setShipBuilding(String shipBuilding) {
		this.shipBuilding = shipBuilding;
	}

	public String getPay() {
		return pay;
	}

	public void setPay(String pay) {
		this.pay = pay;
	}

	public int getCreditNum1() {
		return creditNum1;
	}

	public void setCreditNum1(int creditNum1) {
		this.creditNum1 = creditNum1;
	}

	public int getCreditNum2() {
		return creditNum2;
	}

	public void setCreditNum2(int creditNum2) {
		this.creditNum2 = creditNum2;
	}

	public int getCreditNum3() {
		return creditNum3;
	}

	public void setCreditNum3(int creditNum3) {
		this.creditNum3 = creditNum3;
	}
	public int getCreditNum4() {
		return creditNum4;
	}

	public void setCreditNum4(int creditNum4) {
		this.creditNum4 = creditNum4;
	}

	public String getCreditExpM() {
		return creditExpM;
	}

	public void setCreditExpM(String creditExpM) {
		this.creditExpM = creditExpM;
	}

	public String getCreditExpY() {
		return creditExpY;
	}

	public void setCreditExpY(String creditExpY) {
		this.creditExpY = creditExpY;
	}
}
