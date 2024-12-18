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
			@RequestParam(value = "total_cnt", required = false) Integer total_cnt,
			@RequestParam(value = "error", required = false) String error
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

		// messageを受け取っている場合
		if(error != null) {
			model.addAttribute("message", error);
		}

		// カート内の商品の個数を踏まえて、プルダウンで選択できる商品個数の上限値を計算
		int limit_num = pmodel.getStock_qty();
		@SuppressWarnings("unchecked")
		List<CartModel> cartList = (List<CartModel>)session.getAttribute("cartList");
		if(cartList != null) {
			for (CartModel cartModel : cartList) {
				// 一致する商品があった場合
				if (cartModel.getProduct_id() == product_id) {
					limit_num -= cartModel.getCount();
				}
			}
			limit_num = limit_num > 1 ? limit_num : 1;
		}
		model.addAttribute("limit_num", limit_num);

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
	    if(partOfUrl.equals("/top") || partOfUrl.equals("/search") || partOfUrl.equals("/cart")) {
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

		// プルダウンで選ばれた数量
		int	quantity = Integer.parseInt(request.getParameter("quantity"));
		// 商品ID
		int	product_id = Integer.parseInt(request.getParameter("product_id"));
		redirectAttributes.addAttribute("product_id", product_id);

		if(quantity == 0) { // 個数を0個でカートに追加を押した時（0を選択できないようにしたため、不要かも）
			return "redirect:/product";
		}else {
			boolean productFound = false;  // 一致する商品があったかどうかのフラグ
			int total_cnt = 0;  // 合計商品数

			if(cartList != null) {
				for (CartModel cartModel : cartList) {
					// 一致する商品があった場合
					if (cartModel.getProduct_id() == product_id) {
						ProductModel pmodel = ProductDao.getProductById(product_id);
						int after_qty = cartModel.getCount() + quantity;
						// 商品ごとの数量が10を超えた場合はカートを更新せずにエラーを出力する
						if(after_qty > pmodel.getStock_qty()) {
							redirectAttributes.addAttribute("error", "大変申し訳ありませんが在庫切れとなります。");
							return "redirect:/product";
						}
						cartModel.setStock_qty(pmodel.getStock_qty());  // 在庫数セット
						cartModel.setCount(after_qty);  // 注文数セット
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
				addCartModel.setStock_qty(pmodel.getStock_qty());

				total_cnt += addCartModel.getCount();

				cartList.add(addCartModel);
			}

			session.setAttribute("cartList", cartList);

//				ProductModel pmodel = ProductDao.getProductById(product_id);

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
