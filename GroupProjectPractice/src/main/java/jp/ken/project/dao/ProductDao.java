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

		// category と keyword が null の場合
	    category = (category == null) ? "" : category;
	    keyword = (keyword == null) ? "" : keyword;

		//入力判定
		boolean categoryIsEmpty = category.isEmpty();
		boolean keywordIsEmpty = keyword.isEmpty();

		//条件どちらも未入力
		if(categoryIsEmpty && keywordIsEmpty){

			return null;								//ほんまですか productsList = nullとかで渡さんでええですか
		//カテゴリのみ指定
		}else if(!categoryIsEmpty && keywordIsEmpty){
			String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NOT NULL AND stock_qty >= 1 AND sale_is_valid = '1' AND genre_id = ? ORDER BY discnt_is_valid DESC, product_id ASC";
			Object[] parameters = {category};
			List<ProductModel> productsList = jdbcTemplate.query(sql, parameters, productMapper);
			return productsList;
		//キーワードのみ指定
		}else if(categoryIsEmpty && !keywordIsEmpty){
			String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NOT NULL AND stock_qty >= 1 AND sale_is_valid = '1' AND product_name LIKE ? ORDER BY discnt_is_valid DESC, product_id ASC";
			keyword = keyword.replace("%", "\\%").replace("_", "\\_");
			keyword = "%" + keyword + "%";
			Object[] parameters = {keyword};
			List<ProductModel> productsList = jdbcTemplate.query(sql, parameters, productMapper);
			return productsList;
		//条件どちらも入力あり
		}else{
			String sql = "SELECT product_id, product_name, price, image, discnt_rate FROM t_products WHERE sale_start_date IS NOT NULL AND stock_qty >= 1 AND sale_is_valid = '1' AND genre_id = ? AND product_name LIKE ? ORDER BY discnt_is_valid DESC, product_id ASC";
			keyword = keyword.replace("%", "\\%").replace("_", "\\_");
			keyword = "%" + keyword + "%";
			Object[] parameters = {category, keyword};
			List<ProductModel> productsList = jdbcTemplate.query(sql, parameters, productMapper);
			return productsList;
		}
	}
}
