package jp.ken.project.model;

import java.io.Serializable;

import javax.validation.constraints.Email;

public class UpdateFormModel implements Serializable {

	// 氏名
	private String customer_name;

	// フリガナ
	private String customer_phonetic;

	// メールアドレス
	@Email(message = "Emailを正しく入力してください")
	private String mail;


	// 郵便番号
	private String zip1;
	private String zip2;

	// 住所
	private String prefecture;
	private String city;
	private String block;
	private String building;

	// 電話番号
	private String phone1;
	private String phone2;
	private String phone3;

	// 生年月日
	private String birthYear;
	private String birthMonth;
	private String birthDay;

	// クレジットカード番号
	private String creditNum1;
	private String creditNum2;
	private String creditNum3;
	private String creditNum4;

	// カード有効期限
	private String creditExpM;
	private String creditExpY;

	// パスワード
	private String password;

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_phonetic() {
		return customer_phonetic;
	}

	public void setCustomer_phonetic(String customer_phonetic) {
		this.customer_phonetic = customer_phonetic;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getZip1() {
		return zip1;
	}

	public void setZip1(String zip1) {
		this.zip1 = zip1;
	}

	public String getZip2() {
		return zip2;
	}

	public void setZip2(String zip2) {
		this.zip2 = zip2;
	}

	public String getPrefecture() {
		return prefecture;
	}

	public void setPrefecture(String prefecture) {
		this.prefecture = prefecture;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
