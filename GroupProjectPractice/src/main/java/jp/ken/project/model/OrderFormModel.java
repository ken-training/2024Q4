package jp.ken.project.model;

import java.io.Serializable;

public class OrderFormModel implements Serializable {

//	@NotEmpty(message = "必須入力です")
	private String shipName;


//	@Size(max = 12,message = "電話番号が無効です")
	private String shipPhonetic;

//	@NotEmpty(message = "必須入力です")
//	@Size(min = 2, max = 4,message = "電話番号が無効です")
	private String shipPhone1;

//	@NotEmpty(message = "必須入力です")
//	@Size(min = 2, max = 4,message = "電話番号が無効です")
	private String shipPhone2;

//	@NotEmpty(message = "必須入力です")
//	@Size(min = 4, max = 4,message = "電話番号が無効です")
	private String shipPhone3;

//	@NotEmpty(message = "必須入力です")
//	@Size(min = 3, max = 3,message = "郵便番号が無効です")
	private String shipZip1;

//	@NotEmpty(message = "必須入力です")
//	@Size(min = 4, max = 4,message = "郵便番号が無効です")
	private String shipZip2;

//	@NotEmpty(message = "必須入力です")
//	@Pattern(regexp = "^[^\\s]*$", message = "空白を含まないよう入力してください")
	private String shipPrefecture;

//	@NotEmpty(message = "必須入力です")
//	@Pattern(regexp = "^[^\\s]*$", message = "空白を含まないよう入力してください")
	private String shipCity;

//	@NotEmpty(message = "必須入力です")
//	@Pattern(regexp = "^[^\\s]*$", message = "空白を含まないよう入力してください")
	private String shipBlock;

//	@Pattern(regexp = "^[^\\s]*$", message = "空白を含まないよう入力してください")
	private String shipBuilding;


	private String pay;

//	@Size(min = 2, max = 4,message = "カード番号が無効です")
	private String creditNum1;

//	@Size(min = 2, max = 4,message = "カード番号が無効です")
	private String creditNum2;

//	@Size(min = 2, max = 4,message = "カード番号が無効です")
	private String creditNum3;

//	@Size(min = 2, max = 4,message = "カード番号が無効です")
	private String creditNum4;

	private String creditExpM;
	private String creditExpY;

	//有効期限チェック用
//	@CreditExpiration
	private String creditExp_ym = creditExpY + creditExpM;


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
	public String getShipPhone1() {
		return shipPhone1;
	}
	public void setShipPhone1(String shipPhone1) {
		this.shipPhone1 = shipPhone1;
	}
	public String getShipPhone2() {
		return shipPhone2;
	}
	public void setShipPhone2(String shipPhone2) {
		this.shipPhone2 = shipPhone2;
	}
	public String getShipPhone3() {
		return shipPhone3;
	}
	public void setShipPhone3(String shipPhone3) {
		this.shipPhone3 = shipPhone3;
	}
	public String getShipZip1() {
		return shipZip1;
	}
	public void setShipZip1(String shipZip1) {
		this.shipZip1 = shipZip1;
	}
	public String getShipZip2() {
		return shipZip2;
	}
	public void setShipZip2(String shipZip2) {
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
	public String getCreditNum1() {
		return creditNum1;
	}
	public void setCreditNum1(String creditNum1) {
		this.creditNum1 = creditNum1;
	}
	public String getCreditNum2() {
		return creditNum2;
	}
	public void setCreditNum2(String creditNum2) {
		this.creditNum2 = creditNum2;
	}
	public String getCreditNum3() {
		return creditNum3;
	}
	public void setCreditNum3(String creditNum3) {
		this.creditNum3 = creditNum3;
	}
	public String getCreditNum4() {
		return creditNum4;
	}
	public void setCreditNum4(String creditNum4) {
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
