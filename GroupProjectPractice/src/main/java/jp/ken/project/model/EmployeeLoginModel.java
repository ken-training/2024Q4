package jp.ken.project.model;

public class EmployeeLoginModel {
	//従業員ID
	private int emp_id;
	//氏名
	private String emp_name;
	//メールアドレス
	private String mail;
	//パスワード
	private String password;
	//管理者フラグ
	private String is_admin;
	//削除フラグ
	private String is_deleted;

	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
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
	public String getIs_admin() {
		return is_admin;
	}
	public void setIs_admin(String is_admin) {
		this.is_admin = is_admin;
	}
	public String getIs_deleted() {
		return is_deleted;
	}
	public void setIs_deleted(String is_deleted) {
		this.is_deleted = is_deleted;
	}

}
