package jp.ken.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ken.project.dao.ProductDao;
import jp.ken.project.model.CartModel;
import jp.ken.project.model.ProductModel;

@Controller
@RequestMapping("product")
//@SessionAttributes({"cartList"})
public class ProductController {
	@Autowired
	private ProductDao ProductDao;

//	@Autowired
//	private List<CartModel> cartList;

	@RequestMapping(method = RequestMethod.GET)
	public String toProduct(HttpSession session, Model model, HttpServletRequest request,
			@RequestParam("product_id") int product_id,
			@ModelAttribute("message") String message) {
//		int product_id = (Integer)request.getAttribute("product_id");

		// -------ひとまずテストデータで入力----------
//		int product_id = 4000001;
		// -------------------------------------------

		ProductModel pmodel = ProductDao.getProductById(product_id);
		model.addAttribute("productModel", pmodel);

	    // フラッシュアトリビュートからメッセージを受け取って表示
	    if (message != null) {
	        model.addAttribute("message", message);  // メッセージをモデルに追加
	    }

		return "product";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addProductToCart(HttpSession session, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		@SuppressWarnings("unchecked")
		List<CartModel> cartList = (List<CartModel>)session.getAttribute("cartList");

		if (cartList == null) {
		    cartList = new ArrayList<CartModel>();  // cartList が null の場合、空のリストを作成
		}

		int	quantity = Integer.parseInt(request.getParameter("quantity"));
		int	product_id = Integer.parseInt(request.getParameter("product_id"));

		if(quantity == 0) {
			return "redirect:/product";
		}else {
			boolean productFound = false;  // 一致する商品があったかどうかのフラグ
			int total_cnt = 0;  // 合計商品数

			if(cartList != null) {
				for (CartModel cartModel : cartList) {
				    if (cartModel.getProduct_id() == product_id) {
				        // 一致する商品があった場合
				        int after_qty = cartModel.getCount() + quantity;
				        cartModel.setCount(after_qty);
				        productFound = true;  // 商品が見つかったのでフラグを設定
//				        break;  // 一致する商品が見つかったのでループを抜ける
				    }
				    total_cnt += cartModel.getCount();
				}
			}

			if (!productFound) {
			    // 一致する商品がなかった場合、新しい商品を追加
			    CartModel addCartModel = new CartModel();
			    ProductModel pmodel = ProductDao.getProductById(product_id);
			    addCartModel.setProduct_id(product_id);
			    addCartModel.setProduct_name(pmodel.getProduct_name());
			    addCartModel.setPrice(pmodel.getPrice());
			    addCartModel.setImage(pmodel.getImage());
			    addCartModel.setDiscnt_is_valid(pmodel.getDiscnt_is_valid());
			    addCartModel.setDiscnt_rate(pmodel.getDiscnt_rate());
			    addCartModel.setCount(quantity);

			    total_cnt += addCartModel.getCount();

			    cartList.add(addCartModel);
			}

			session.setAttribute("cartList", cartList);
			redirectAttributes.addFlashAttribute("message", "カートに入れました。カート内の商品は" + total_cnt + "個です。");
			return "redirect:/product?product_id=" + product_id;
		}
	}
}
