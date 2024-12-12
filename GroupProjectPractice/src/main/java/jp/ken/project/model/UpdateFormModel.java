package jp.ken.project.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import jp.ken.project.group.Group1;
import jp.ken.project.group.Group2;

public class UpdateFormModel implements Serializable {

	// 氏名
	@NotEmpty(message = "必須入力です")
	private String customer_name;

	// フリガナ
	private String customer_phonetic;

	// メールアドレス
	@NotEmpty(message = "必須入力です")
	@Size(min=3,max=32,message ="3文字以上32文字以下で入力しください", groups = Group1.class)
	@Email(message = "Emailを正しく入力してください", groups = Group2.class)
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
	private int birthYear;
	private int birthMonth;
	private int birthDay;

	// クレジットカード番号
	private int creditNum1;
	private int creditNum2;
	private int creditNum3;
	private int creditNum4;

	// カード有効期限
	private String creditExpM;
	private String creditExpY;

	// パスワード
	@NotEmpty(message = "必須入力です")
	@Size(min=6,max=15 ,message ="6文字以上15文字以下で入力しください", groups = Group1.class)
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

	public int getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(int birthYear) {
		this.birthYear = birthYear;
	}

	public int getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(int birthMonth) {
		this.birthMonth = birthMonth;
	}

	public int getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(int birthDay) {
		this.birthDay = birthDay;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
