package jp.ken.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jp.ken.project.model.CartModel;

@Controller
@RequestMapping("cart")
@SessionAttributes({"cartList"})
public class CartController {

	@RequestMapping(method = RequestMethod.GET)
	public String toCart(HttpSession session, Model model) {
		//セッションからCartオブジェクト取得
		@SuppressWarnings("unchecked")
		List<CartModel> cartList = (List<CartModel>)session.getAttribute("cartList");

		if (cartList != null && cartList.size() != 0) {
			int total_amount = 0;
			int total_qty = 0;
		    for(CartModel cartmodel : cartList) {
		    	total_amount += cartmodel.getPrice() * cartmodel.getCount();
		    	total_qty += cartmodel.getCount();
		    }
		    model.addAttribute("total_amount", total_amount);
		    model.addAttribute("total_qty", total_qty);

		} else {
			model.addAttribute("message", "カートは空です");
		}

		return "cart";
	}

	@RequestMapping(method = RequestMethod.POST)
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
		    for(CartModel cartmodel : cartList) {
		    	if(update_quantity[cnt] == 0) {
		    		dellist.add(cnt);
		    	}else if(update_productId[cnt] == cartmodel.getProduct_id()) {
		    		cartmodel.setCount(update_quantity[cnt]);
		    		total_amount += cartmodel.getPrice() * cartmodel.getCount();
		    		total_qty += cartmodel.getCount();
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
		}

		return "cart";
	}
}
