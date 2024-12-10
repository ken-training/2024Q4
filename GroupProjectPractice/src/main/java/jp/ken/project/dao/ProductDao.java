package jp.ken.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jp.ken.project.model.ProductModel;

@Repository
public class ProductDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<ProductModel> productMapper = new BeanPropertyRowMapper<ProductModel>(ProductModel.class);

	//商品一覧（引数なし）
	public List<ProductModel> getAllProductList(){
		String sql = "SELECT * FROM t_products";
		List<ProductModel> allProducts = jdbcTemplate.query(sql,productMapper);
		return allProducts;
	}
}
