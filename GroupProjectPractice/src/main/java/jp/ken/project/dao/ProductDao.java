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

	//セール品リスト(引数なし）
	public List<ProductModel> getSaleProductList(){
		String sql = "SELECT discnt_rate FROM t_products WHERE discnt_is_valid = ?";
		Object[] parameters = {'1'};
		List<ProductModel> saleProducts = jdbcTemplate.query(sql,parameters,productMapper);
		return saleProducts;
	}

	//通常品リスト(引数なし）
	public List<ProductModel> getNomalProductList(){
		String sql = "SELECT discnt_rate FROM t_products WHERE discnt_is_valid = ?";
		Object[] parameters = {'0'};
		List<ProductModel> nomalProducts = jdbcTemplate.query(sql,parameters,productMapper);
		return nomalProducts;
	}
}
