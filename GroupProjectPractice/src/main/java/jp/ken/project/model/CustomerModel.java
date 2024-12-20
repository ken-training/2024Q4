package jp.ken.project.model;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import jp.ken.project.group.Group1;
import jp.ken.project.group.Group2;

public class CustomerModel implements Serializable{

	//会員ID
	private int customer_id;

	//氏名
	@NotEmpty(message = "必須入力です")
	@Size(min=2,max=15,message ="2文字以上15文字以下で入力してください", groups = Group1.class)
	@Pattern(regexp = "^[a-zA-Zぁ-んァ-ン一-龯々〆〤]+$", message = "記号や数字、空白文字が含まれている場合は登録できません", groups = Group2.class)
	private String customer_name;

	//フリガナ
	private String customer_phonetic;

	//メールアドレス
	@NotEmpty(message = "必須入力です")
	@Size(min=3,max=32,message ="3文字以上32文字以下で入力してください", groups = Group1.class)
	@Email(message = "Emailを正しく入力してください", groups = Group2.class)
	private String mail;

	//パスワード
	@NotEmpty(message = "必須入力です")
	@Size(min=6,max=15 ,message ="6文字以上15文字以下で入力してください", groups = Group1.class)
	private String password;

	//郵便番号
	private String zip;

	//住所
	private String address;

	//電話番号
	private String phone;

	//生年月日
	private java.sql.Date birthday;

	//クレジットカード番号
	private String creditcard_num;

	//マスク化クレジットカード番号
	private String masked_creditcard_num;

	//クレジットカード有効期限
	private String creditcard_exp;

	//所持ポイント数
	private int points;

	//退会フラグ
	private String is_remove;

	// 顧客の元のメールアドレス（退会時に変更前のメールアドレスを保持）
	private String originalMail;

	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public java.sql.Date getBirthday() {
		return birthday;
	}
	public void setBirthday(java.sql.Date birthday) {
		this.birthday = birthday;
	}
	public String getCreditcard_num() {
		return creditcard_num;
	}
	public void setCreditcard_num(String creditcard_num) {
		this.creditcard_num = creditcard_num;
	}
	public String getCreditcard_exp() {
		return creditcard_exp;
	}
	public void setCreditcard_exp(String creditcard_exp) {
		this.creditcard_exp = creditcard_exp;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public String getIs_remove() {
		return is_remove;
	}
	public void setIs_remove(String is_remove) {
		this.is_remove = is_remove;
	}
	public String getMasked_creditcard_num() {
		return masked_creditcard_num;
	}
	public void setMasked_creditcard_num(String masked_creditcard_num) {
		this.masked_creditcard_num = masked_creditcard_num;
	}
	public String getOriginalMail() {
		return originalMail;
	}
	public void setOriginalMail(String originalMail) {
		this.originalMail = originalMail;
	}
}
