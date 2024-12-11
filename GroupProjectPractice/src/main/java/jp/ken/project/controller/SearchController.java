package jp.ken.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.project.dao.ProductDao;
import jp.ken.project.model.ProductModel;
import jp.ken.project.model.SearchFormModel;

/********************************/
/******キー名を確認すること******/
/******　例外対応すること　******/
/********************************/

@Controller
public class SearchController {
	@Autowired
	private ProductDao productDao;

	//検索結果表示
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String get(SearchFormModel smodel, Model model) {
		model.addAttribute("category", smodel.getCategory());
		model.addAttribute("keyword", smodel.getKeyword());

		List<ProductModel> productsList = productDao.getAllProductsList(smodel.getCategory(), smodel.getKeyword());
		model.addAttribute("productsList", productsList);

		if(productsList.isEmpty()) {
			model.addAttribute("message", "該当する商品がありませんでした");
		}
		return "search";
	}

	//商品詳細に遷移
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String toProduct(@RequestParam("product_id") int product_id, Model model) {
		ProductModel product = productDao.getProductById(product_id);
		model.addAttribute("product", product);

		return "forward:/product";
	}

}

