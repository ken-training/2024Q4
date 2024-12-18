package jp.ken.project.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import jp.ken.project.group.Group1;
import jp.ken.project.group.Group2;

public class EmployeeLoginFormModel {

	@NotEmpty(message ="必須入力です")
	@Size(min = 3, max = 32,message = "3文字以上32文字以下で入力してください",groups = Group1.class)
	@Email(message = "Emailを正しく入力してください", groups = Group2.class)
	private String mailaddress;

	@NotEmpty(message = "必須入力です")
	@Size(min = 6, max = 15,message = "6文字以上15文字以下で入力してください",groups = Group1.class)
	private String password;

	public String getMailaddress() {
		return mailaddress;
	}

	public void setMailaddress(String mailaddress) {
		this.mailaddress = mailaddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
