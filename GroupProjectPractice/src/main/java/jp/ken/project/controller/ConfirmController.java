package jp.ken.project.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ken.project.dao.ConfirmDao;
import jp.ken.project.model.CartModel;
import jp.ken.project.model.CustomerModel;
import jp.ken.project.model.OrderFormModel;
import jp.ken.project.model.OrderModel;
import jp.ken.project.model.ShippingModel;

@Controller
//@RequestMapping("confirm")
//@SessionAttributes({"cartList"})
public class ConfirmController {

	@Autowired
	private ConfirmDao ConfirmDao;

	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public String toConfirm(HttpSession session, Model model, HttpServletRequest request, HttpServletResponse response
//			, @ModelAttribute OrderFormModel orderFormModel
			) {
	    // キャッシュを無効化
	    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

		@SuppressWarnings("unchecked")
		List<CartModel> cartList = (List<CartModel>) session.getAttribute("cartList");
		int total_amount = 0;
		int total_qty = 0;

		if (cartList != null && cartList.size() != 0) {
		    for(CartModel cartModel : cartList) {
		    	int amount = cartModel.getPrice() * cartModel.getCount();
		    	total_amount += cartModel.getDiscnt_is_valid().equals("1") ? amount * (1 - cartModel.getDiscnt_rate()) : amount;
		    	total_qty += cartModel.getCount();
		    }
		    model.addAttribute("total_amount", total_amount);
		    model.addAttribute("total_qty", total_qty);
		}else {
			// カートが空の状態で飛んで来たらトップ画面へ
			return "redirect:/top";
		}
//		model.addAttribute("orderFormModel", request.getAttribute("orderFormModel"));

		OrderFormModel orderFormModel = (OrderFormModel) session.getAttribute("orderFormModel");
		if(orderFormModel != null) {
			String pay = orderFormModel.getPay();
			if (pay.equals("cash")) {
				model.addAttribute("pay", "代金引換(現金)");
			}else if(pay.equals("credit")) {
				model.addAttribute("pay", "クレジットカード");
			}
		}


		return "confirm";
	}

	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public String doOrderConfirm(HttpSession session, Model model) {

		// セッションから各種モデル取得
		OrderFormModel orderFormModel = (OrderFormModel) session.getAttribute("orderFormModel");
		CustomerModel customerModel = (CustomerModel) session.getAttribute("customerModel");
		// セッション切れ対策
		if(customerModel == null) {
			System.out.println("session切れ");
			return "redirect:/top";
		}

		@SuppressWarnings("unchecked")
		List<CartModel> cartList = (List<CartModel>)session.getAttribute("cartList");

		// ======発送情報インサート用====================
		// 必要情報をShippingModelに格納
		ShippingModel shippingModel = new ShippingModel();
		shippingModel.setShip_name(orderFormModel.getShipName());
//		System.out.println(orderFormModel.getShipName());
		shippingModel.setShip_phonetic(orderFormModel.getShipPhonetic());
		String ship_zip =  orderFormModel.getShipZip1() + "-" + orderFormModel.getShipZip2();
		shippingModel.setShip_zip(ship_zip);
		String ship_address = orderFormModel.getShipPrefecture() + " " + orderFormModel.getShipCity()
							+ " " + orderFormModel.getShipBlock() + " " + orderFormModel.getShipBuilding();
		shippingModel.setShip_address(ship_address);
		String ship_phone = orderFormModel.getShipPhone1() + "-" + orderFormModel.getShipPhone2()
							+ "-" + orderFormModel.getShipPhone3();
		shippingModel.setShip_phone(ship_phone);

		// ======注文情報インサート用====================
		// 必要情報をOrderModelに格納していく
		OrderModel orderModel = new OrderModel();

        // 今日の日付を取得
		Calendar calendar = Calendar.getInstance();
        // 日付を"yyyy/MM/dd"の形式で取得
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(calendar.getTime());
        System.out.println(formattedDate);
        // LocalDate を java.sql.Date に変換
        Date order_date = Date.valueOf(formattedDate);

        // 合計金額を算出
        int total_amount = 0;
		if (cartList != null && cartList.size() != 0) {
		    for(CartModel cartModel : cartList) {
		    	int amount = cartModel.getPrice() * cartModel.getCount();
		    	total_amount += cartModel.getDiscnt_is_valid().equals("1") ? amount * (1 - cartModel.getDiscnt_rate()) : amount;
		    }
		}
		// OrderModelにセット
		orderModel.setOrder_date(order_date);
		orderModel.setCustomer_id(customerModel.getCustomer_id());
//		System.out.println("customer_id : " + customerModel.getCustomer_id());
//		orderModel.setShip_id(shipping_id);
		orderModel.setTotal_amount(total_amount);
		orderModel.setPayment_methods(orderFormModel.getPay());
//		orderModel.setUse_points(); //現状はデフォ値とする
//		orderModel.setSpecify_delivery();  //現状はデフォ値とする

//		int order_id = ConfirmDao.insertToOrders(orderModel);
//		System.out.println("order_id : " + order_id);


		// (クレジットカードを取得する場合)
		// 登録済みカード番号をしようか入力しているか分岐を確認
//		String creditForm = orderFormModel.getCreditForm();
		// クレジットカード番号を格納する変数を作成
//		String creditCardNum = "";
		// 登録済みクレジットカード番号を利用する場合
//		if (creditForm.equals("db")) {
			// セッションに紐づいている会員のクレジットカード番号を取得
//			creditCardNum = customerModel.getCreditcard_num();
//			System.out.println( "creditCardNum:"+creditCardNum);
		// カード番号を入力する場合
//		}else if(creditForm.equals("creditForm")) {
			// 入力された番号を取得
//			creditCardNum = orderFormModel.getCreditNum1() + orderFormModel.getCreditNum2()
//							+ orderFormModel.getCreditNum3() + orderFormModel.getCreditNum4();
			// 取得したクレジットカード番号をハッシュ化
//			ShaPasswordEncoder encoder = new ShaPasswordEncoder();
//			String encodeCreditCardNum = encoder.encodePassword(creditCardNum, customerModel.getCustomer_id());
//			System.out.println( "encodeCreditCardNum:"+encodeCreditCardNum);
//		}


		// ======注文詳細情報インサート用====================
//
//		int numberOfRow = 0;
//		if (cartList != null && cartList.size() != 0) {
//		    for(CartModel cartmodel : cartList) {
//		    	// 必要情報をOrderModelに格納していく
//		    	OrderDetailModel orderDetailModel = new OrderDetailModel();
//
////		    	orderDetailModel.setOrder_id(order_id);
//		    	orderDetailModel.setProduct_id(cartmodel.getProduct_id());
//		    	orderDetailModel.setProduct_num(cartmodel.getCount());
//		    	orderDetailModel.setTemp_amount(cartmodel.getPrice() * cartmodel.getCount());
//
//		    	numberOfRow += ConfirmDao.insertToOrderDetails(orderDetailModel);
//		    }
//		    System.out.println("numberOfRow : " + numberOfRow);
//		}

		// 各モデルをDaoのメソッドに渡して、返り値はTrue or False
		boolean success = ConfirmDao.insertWithTransaction(shippingModel, orderModel, cartList);

		if(success) {
			// メール内容作成
			String mailBody
					= "\r\n"
					+ customerModel.getCustomer_name() + " 様"
					+ "\r\n"
					+ "\r\n"
					+ "いつもご利用ありがとうございます。\r\n"
					+ "KEN Interior Shopでございます。\r\n"
					+ "\r\n"
					+ "以下の内容にて、ご注文を承りました。\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "----------------\r\n"
					+ "■ご注文情報\r\n"
					+ "----------------\r\n"
					+ "■ご注文番号： \r\n";

			// 商品の個数だけ行追記
			int total_qty = 0;
			// (準備)DecimalFormatを使ってカンマ区切りでフォーマット
			DecimalFormat formatter = new DecimalFormat("#,###");

			if (cartList != null && cartList.size() != 0) {
			    for(CartModel cartmodel : cartList) {
			    	// 必要情報をメール内容に追記していく

			    	// 商品名表示
			    	mailBody += "■" + cartmodel.getProduct_name() + " ";

			    	// 値段を取得
			    	int price = cartmodel.getPrice();
			    	price = (int)(cartmodel.getDiscnt_is_valid().equals("1") ? price * (1 - cartmodel.getDiscnt_rate()) : price);
			    	// DecimalFormatを使ってカンマ区切りでフォーマット
			    	mailBody += "￥" + formatter.format(price) + "(税抜) ";

			    	// 数量表示
			    	mailBody += "数量：" + cartmodel.getCount() + "個\r\n";

			    	// 合計数量計算
			    	total_qty += cartmodel.getCount();
			    }
			}

			mailBody += "\r\n"
					+ "●商品合計数： " + total_qty + "個\r\n"
					+ "●税額： ￥" + formatter.format(total_amount * 0.1) + "\r\n"
					+ "●商品の小計： ￥" + formatter.format(total_amount * 1.1) + "\r\n"
					+ "●配送料：WEBサイト開設キャンペーンにつき、送料無料!!\r\n"
					+ "●ご請求額： ￥" + formatter.format(total_amount * 1.1)+ "\r\n";

			// お支払情報取得
			String pay = "";
			if(orderFormModel != null) {
				pay = orderFormModel.getPay();
				if (pay.equals("cash")) {
					pay = "代金引換(現金)";
				}else if(pay.equals("credit")) {
					pay = "クレジットカード";
				}
			}

			mailBody += "●お支払情報： " + pay + "\r\n"
					+ "\r\n"
					+ "\r\n"
					+ "----------------\r\n"
					+ "■今回のご注文のお届け先\r\n"
					+ "----------------\r\n"
					+ "■お客様名： " + orderFormModel.getShipName() + " 様\r\n"
					+ "■住所： " + orderFormModel.getShipPrefecture() + " "
									+ orderFormModel.getShipCity() + " "
									+ orderFormModel.getShipBlock() + " "
									+ orderFormModel.getShipBuilding() + " \r\n"
					+ "■電話番号： " + orderFormModel.getShipPhone1() + "-"
									+ orderFormModel.getShipPhone2() + "-"
									+ orderFormModel.getShipPhone3() + " \r\n"
									+ "\r\n"
									+ "\r\n"
					+ "----------------\r\n"
					+ "■KEN Interior Shopサイト\r\n"
					+ "http://localhost:8080/project/top\r\n"
					+ "----------------\r\n";


			model.addAttribute("mailBody", mailBody);

			// sessionをクリア
	        session.removeAttribute("cartList");  // カート
	        session.removeAttribute("orderFormModel");  // 発注情報
			return "redirect:/send-email";
//			return "redirect:/cart"; // complete.jspができるまでカートに遷移する
		}else {
			model.addAttribute("error", "エラーが発生しました");
			return "redirect:/confirm";

		}


//		return "complete";
	}
}