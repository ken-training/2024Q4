package jp.ken.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.project.dao.ProductDao;
import jp.ken.project.model.CartModel;
import jp.ken.project.model.ProductModel;

@Controller
@SessionAttributes({"cartList"})
public class topController{
	@Autowired
	private ProductDao productDao;

	@ModelAttribute("cartList")
	public List<CartModel> setupCartList(){
		return new ArrayList<CartModel>();
	}

	@RequestMapping(value = "/top", method = RequestMethod.GET)
	public String toTop(Model model, HttpSession session){
		try {
			List<ProductModel> AllProducts = productDao.getAllProductList();
			List<ProductModel> SaleProducts = new ArrayList<ProductModel>();
			List<ProductModel> Products = new ArrayList<ProductModel>();

			for (ProductModel productModel : AllProducts) {
				String saleFlag = productModel.getDiscnt_is_valid();
				if (saleFlag.equals("1")) {
					SaleProducts.add(productModel);
				}else {
					Products.add(productModel);
				}
			}

			System.out.println(Products);
		    model.addAttribute("allProductsList", Products);
		    model.addAttribute("saleProductsList", SaleProducts);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// ------------------確認用-------------------------
		Object cartList = session.getAttribute("cartList");
		System.out.println("Cart List: " + cartList);
		Object customerModel = session.getAttribute("customerModel");
		System.out.println("customerModel: " + customerModel);
		// ------------------確認用-------------------------

	    return "top";
	}

}
