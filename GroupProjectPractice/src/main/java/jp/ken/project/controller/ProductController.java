package jp.ken.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ken.project.dao.ProductDao;
import jp.ken.project.model.ProductModel;

@Controller
@RequestMapping("product")
//@SessionAttributes({"cartList"})
public class ProductController {
	@Autowired
	private ProductDao ProductDao;

	@RequestMapping(method = RequestMethod.GET)
	public String toProduct(HttpSession session, Model model, HttpServletRequest request) {
//		int product_id = (Integer)request.getAttribute("product_id");

		// -------ひとまずテストデータで入力----------
		int product_id = 4000001;
		// -------------------------------------------

		ProductModel pmodel = ProductDao.getProductById(product_id);
		model.addAttribute("productModel", pmodel);

		return "product";
	}
}
