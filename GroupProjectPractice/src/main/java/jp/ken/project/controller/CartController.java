package jp.ken.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.project.model.CartModel;

@Controller
//@RequestMapping("cart")
//@SessionAttributes({"cartList"})
public class CartController {

	@RequestMapping(value = "cart", method = RequestMethod.GET)
	public String toCart(HttpSession session, Model model, HttpServletResponse response) {
	    // キャッシュを無効化
	    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

		//セッションからCartオブジェクト取得
		@SuppressWarnings("unchecked")
		List<CartModel> cartList = (List<CartModel>)session.getAttribute("cartList");

		// カートの中身が空じゃなければ割引金額
		if (cartList != null && cartList.size() != 0) {
			int total_amount = 0;  // 合計金額
			int total_qty = 0;  // 合計数量
		    for(CartModel cartModel : cartList) {
		    	int amount = cartModel.getPrice() * cartModel.getCount();
		    	total_amount += cartModel.getDiscnt_is_valid().equals("1") ? amount * (1 - cartModel.getDiscnt_rate()) : amount;
		    	total_qty += cartModel.getCount();
		    }
		    model.addAttribute("total_amount", total_amount);
		    model.addAttribute("total_qty", total_qty);

		} else {
			model.addAttribute("message", "カートは空です");
			session.removeAttribute("cartList");
		}

		return "cart";
	}

	@RequestMapping(value = "cart", method = RequestMethod.POST)
	public String cartUpdate(@RequestParam("productId") int[] update_productId,
            @RequestParam("quantity") int[] update_quantity,
            HttpSession session, Model model) {

		//セッションからCartオブジェクト取得
		@SuppressWarnings("unchecked")
		List<CartModel> cartList = (List<CartModel>)session.getAttribute("cartList");

		if (cartList != null && cartList.size() != 0) {
			int total_amount = 0;
			int total_qty = 0;
			int cnt = 0;
			ArrayList<Integer> dellist = new ArrayList<Integer>();
		    for(CartModel cartModel : cartList) {
		    	if(update_quantity[cnt] == 0) {
		    		dellist.add(cnt);
		    	}else if(update_productId[cnt] == cartModel.getProduct_id()) {
		    		cartModel.setCount(update_quantity[cnt]);
		    		int amount = cartModel.getPrice() * cartModel.getCount();
		    		total_amount += cartModel.getDiscnt_is_valid().equals("1") ? amount * (1 - cartModel.getDiscnt_rate()) : amount;
		    		total_qty += cartModel.getCount();
		    	}
		    	cnt++;
		    }

		    // 個数が0の商品はカートから削除
		    for (int i = dellist.size() - 1; i >= 0; i--) {
		        cartList.remove((int) dellist.get(i));  // インデックスがずれないように逆順で削除
		    }

		    session.setAttribute("cartList", cartList);
		    model.addAttribute("total_amount", total_amount);
		    model.addAttribute("total_qty", total_qty);

		}
		//削除でカートが空になっている可能性がある
		if(cartList == null || cartList.size() == 0){
			model.addAttribute("message", "カートは空です");
			session.removeAttribute("cartList");
		}

		return "redirect:/cart";
	}

	@RequestMapping(value = "cart/empty", method = RequestMethod.GET)
	public String doEmpty(HttpSession session) {
		System.out.println("カートを空にします");
		session.removeAttribute("cartList");
		return "redirect:/cart";
	}
}
