package jp.ken.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import jp.ken.project.model.ProductModel;

@Component
public class ProductDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<ProductModel> productMapper = new BeanPropertyRowMapper<ProductModel>(ProductModel.class);



	public List<ProductModel> getAllProductsList(String category, String keyword){
		/*　カテゴリ、キーワードが空かどうかで分岐させる　*/

		//入力判定
		boolean categoryIsEmpty = category.isEmpty();
		boolean keywordIsEmpty = keyword.isEmpty();

		//条件どちらも未入力
		if(categoryIsEmpty && keywordIsEmpty){
			return null;								//ほんまですか
		//カテゴリのみ指定
		}else if(!categoryIsEmpty && keywordIsEmpty){
			String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NULL AND stock_qty >= 1 AND sale_is_valid = 1 AND genre_id = ? ORDER BY discnt_is_valid DESC, product_id ASC";
			Object[] parameters = {category};
			List<ProductModel> productsList = jdbcTemplate.query(sql, parameters, productMapper);
			return productsList;
		//キーワードのみ指定
		}else if(categoryIsEmpty && !keywordIsEmpty){
			String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NULL AND stock_qty >= 1 AND sale_is_valid = 1 AND product_name LIKE ? ORDER BY discnt_is_valid DESC, product_id ASC";
			keyword = keyword.replace("%", "\\%").replace("_", "\\_");
			keyword = "%" + keyword + "%";
			Object[] parameters = {keyword};
			List<ProductModel> productsList = jdbcTemplate.query(sql, parameters, productMapper);
			return productsList;
		//条件どちらも入力あり
		}else{
			String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NULL AND stock_qty >= 1 AND sale_is_valid = 1 AND discnt_valid = 1 AND genre_id = ? AND product_name LIKE ?";
			keyword = keyword.replace("%", "\\%").replace("_", "\\_");
			keyword = "%" + keyword + "%";
			Object[] parameters = {category, keyword};
			List<ProductModel> productsList = jdbcTemplate.query(sql, parameters, productMapper);
			return productsList;
		}
	}
}
