package jp.ken.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.PlatformTransactionManager;

import jp.ken.project.model.ProductModel;


//import jp.ken.jdbc.entity.Members;

@Component
public class ProductDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Autowired
//	private PlatformTransactionManager transactionManager;

	private RowMapper<ProductModel> productMapper = new BeanPropertyRowMapper<ProductModel>(ProductModel.class);


	//product_idから商品情報検索
	public ProductModel getProductById(int id){
		String sql = "SELECT * FROM t_products WHERE product_id=?;";
		Object[] parameters = {id};
		try{
			ProductModel productModel = jdbcTemplate.queryForObject(sql, parameters, productMapper);
			return productModel;
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
}
