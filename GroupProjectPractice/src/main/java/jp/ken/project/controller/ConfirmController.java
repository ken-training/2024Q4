package jp.ken.project.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
	public String toConfirm(HttpSession session, Model model, HttpServletRequest request
//			, @ModelAttribute OrderFormModel orderFormModel
			) {
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

		OrderFormModel orderFormModel = (OrderFormModel) session.getAttribute("orderFormModel");
		CustomerModel customerModel = (CustomerModel) session.getAttribute("customerModel");
		if(customerModel == null) {
			System.out.println("customerModelがnull");
		}else {
//			System.out.println(customerModel.getMail());
//			System.out.println(customerModel.getCustomer_id());
//			System.out.println(customerModel.getPassword());
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
			// sessionをクリア
	        session.removeAttribute("cartList");  // カート
	        session.removeAttribute("orderFormModel");  // 発注情報
			return "orderSuccess";
//			return "redirect:/cart"; // complete.jspができるまでカートに遷移する
		}else {
			model.addAttribute("error", "エラーが発生しました");
			return "redirect:/confirm";

		}


//		return "complete";
	}
}
