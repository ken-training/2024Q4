package jp.ken.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import jp.ken.project.model.EmployeeLoginModel;

@Component
public class EmployeeLoginDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<EmployeeLoginModel> employeeLoginMapper = new BeanPropertyRowMapper<EmployeeLoginModel>(EmployeeLoginModel.class);

	//t_employeeテーブルからすべてを取得
	public List<EmployeeLoginModel> getList() {
		String sql = "SELECT * FROM t_employees";
		return jdbcTemplate.query(sql, employeeLoginMapper);
    }

	// メールアドレスに一致する顧客を取得するメソッド
	public EmployeeLoginModel getEmployeeLoginByMail(String mail) { //メソッドの引数をStringに変更(LoginFormModel.getMailAddress)
		String sql = "SELECT * FROM t_employees WHERE mail = ? AND is_deleted = '0' ";
		Object[] parameters = { mail };
		try {
			EmployeeLoginModel employeeLoginModel = jdbcTemplate.queryForObject(sql, parameters, employeeLoginMapper);
			return employeeLoginModel;
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;  // 見つからない場合は null を返す
		}
	}

}
