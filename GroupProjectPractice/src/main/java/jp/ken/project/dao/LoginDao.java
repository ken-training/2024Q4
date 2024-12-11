package jp.ken.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import jp.ken.project.model.CustomerModel;

@Component
public class LoginDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<CustomerModel> customerMapper = new BeanPropertyRowMapper<CustomerModel>(CustomerModel.class);

    //Customerテーブルからすべてを取得
    public List<CustomerModel> getList() {
        String sql = "SELECT * FROM Customer";
        return jdbcTemplate.query(sql, customerMapper);
    }

    // メールアドレスに一致する顧客を取得するメソッド
    public CustomerModel getCustomerByMail(String mail) { //メソッドの引数をStringに変更(LoginFormModel.getMailAddress)
        String sql = "SELECT * FROM t_customers WHERE mail = ?";
        Object[] parameters = { mail };
        try {
        	CustomerModel customerModel = jdbcTemplate.queryForObject(sql, parameters, customerMapper);
        	return customerModel;
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;  // 見つからない場合は null を返す
        }
    }
}

