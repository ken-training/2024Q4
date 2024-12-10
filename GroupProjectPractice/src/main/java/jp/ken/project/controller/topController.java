package jp.ken.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ken.project.dao.ProductDao;
import jp.ken.project.model.ProductModel;

@Controller
public class topController{
	@Autowired
	private ProductDao productDao;

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String toTop(Model model){
	    List<ProductModel> allProducts = productDao.getAllProductList();
	    model.addAttribute("allProductsList", allProducts);

	    return "top";
	}

}
