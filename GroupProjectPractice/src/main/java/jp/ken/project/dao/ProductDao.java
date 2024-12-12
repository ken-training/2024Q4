package jp.ken.project.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	public List<ProductModel> getAllProductsList(String category, String keyword){
		/*　カテゴリ、キーワードが空かどうかで分岐させる　*/

		//keyword が null の場合
	    keyword = (keyword == null) ? "" : keyword;

		//入力判定
		boolean keywordIsEmpty = keyword.isEmpty();

		//キーワード指定なし
		if(keywordIsEmpty){

			//カテゴリが全選択
			if(category == "ALL") {
				String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NOT NULL AND stock_qty >= 1 AND sale_is_valid = '1' ORDER BY discnt_is_valid DESC, product_id ASC";
				List<ProductModel> productsList = jdbcTemplate.query(sql, productMapper);
				return productsList;

			//カテゴリ選択あり
			}else {
				String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NOT NULL AND stock_qty >= 1 AND sale_is_valid = '1' AND genre_id = ? ORDER BY discnt_is_valid DESC, product_id ASC";
				Object[] parameters = {category};
				List<ProductModel> productsList = jdbcTemplate.query(sql, parameters, productMapper);
				return productsList;
			}

		//キーワード指定あり
		}else{
			//カテゴリが全選択
			if(category == "ALL") {
				String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NOT NULL AND stock_qty >= 1 AND sale_is_valid = '1' AND product_name LIKE ? ORDER BY discnt_is_valid DESC, product_id ASC";
				keyword = keyword.replace("%", "\\%").replace("_", "\\_");
				keyword = "%" + keyword + "%";
				Object[] parameters = {keyword};
				List<ProductModel> productsList = jdbcTemplate.query(sql, parameters, productMapper);
				return productsList;
			//カテゴリ選択あり
			}else {
				String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NOT NULL AND stock_qty >= 1 AND sale_is_valid = '1' AND genre_id = ? AND product_name LIKE ? ORDER BY discnt_is_valid DESC, product_id ASC";
				keyword = keyword.replace("%", "\\%").replace("_", "\\_");
				keyword = "%" + keyword + "%";
				Object[] parameters = {category, keyword};
				List<ProductModel> productsList = jdbcTemplate.query(sql, parameters, productMapper);
				return productsList;
			}

		}
	}
}
