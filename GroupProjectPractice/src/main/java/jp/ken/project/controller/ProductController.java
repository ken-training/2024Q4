package jp.ken.project.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ken.project.dao.ProductDao;
import jp.ken.project.model.CartModel;
import jp.ken.project.model.ProductModel;

@Controller
@RequestMapping("/product")
//@SessionAttributes({"cartList"})
public class ProductController {
	@Autowired
	private ProductDao ProductDao;

//	@Autowired
//	private List<CartModel> cartList;

	@RequestMapping(method = RequestMethod.GET)
	public String toProduct(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam("product_id") int product_id,
			@RequestParam(value = "total_cnt", required = false) Integer total_cnt
			) {
	    // キャッシュを無効化
	    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

		ProductModel pmodel = ProductDao.getProductById(product_id);
		model.addAttribute("productModel", pmodel);

		// 合計数量が渡されていれば、カートに入れたメッセージをモデルに設定
		if(total_cnt != null) {
			model.addAttribute("message", "カートに入れました。カート内の商品は" + total_cnt + "個です。");
		}

		// 一覧に戻るリンク用のURLを分岐させて設定
	    // 遷移元のURL（Referer）を取得
	    String product_referer = request.getHeader("Referer");
        // 最後の "/" の位置を取得
        int lastSlashIndex = product_referer.lastIndexOf("/");
        // "?" の位置を取得
        int questionMarkIndex = product_referer.indexOf("?", lastSlashIndex);
        // "?" が見つからなかった場合、文字列の終わりまでを取る
        if (questionMarkIndex == -1) {
            questionMarkIndex = product_referer.length();
        }
        // 最後の "/" から "?" または文字列の終わりまでを抜き出す
        String partOfUrl = product_referer.substring(lastSlashIndex, questionMarkIndex);
        // top or searchから飛んできた時はセッションにURL登録
	    if(partOfUrl.equals("/top") || partOfUrl.equals("/search")) {
	    	session.setAttribute("previousUrl", product_referer);
	    }

		return "product";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String addProductToCart(HttpSession session, Model model, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) {


		// キャッシュを無効化
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);

		@SuppressWarnings("unchecked")
		List<CartModel> cartList = (List<CartModel>)session.getAttribute("cartList");

		if (cartList == null) {
			cartList = new ArrayList<CartModel>();  // cartList が null の場合、空のリストを作成
		}

		int	quantity = Integer.parseInt(request.getParameter("quantity"));
		int	product_id = Integer.parseInt(request.getParameter("product_id"));

		if(quantity == 0) { // 個数を0個でカートに追加を押した時（0を選択できないようにしたため、不要かも）
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

//				ProductModel pmodel = ProductDao.getProductById(product_id);
			redirectAttributes.addAttribute("product_id", product_id);
			redirectAttributes.addAttribute("total_cnt", total_cnt);

//			ProductModel pmodel = ProductDao.getProductById(product_id);
//			model.addAttribute("productModel", pmodel);
//	        model.addAttribute("message", "カートに入れました。カート内の商品は" + total_cnt + "個です。");
//
//			return "product";
		}
		return "redirect:/product";
	}
}
