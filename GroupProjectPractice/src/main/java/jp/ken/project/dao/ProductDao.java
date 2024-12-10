package jp.ken.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import jp.ken.project.model.ProductModel;

public class ProductDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<ProductModel> productMapper = new BeanPropertyRowMapper<ProductModel>(ProductModel.class);

	public List<ProductModel> getList(){
		String sql = "SELECT discnt_rate FROM t_products WHERE discnt_is_valid = ?";
		Object[] parameters = {'1'};
		List<ProductModel> membersList = jdbcTemplate.query(sql,parameters,productMapper);
		return membersList;
	}
}
