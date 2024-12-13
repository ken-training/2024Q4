package jp.ken.project.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.project.model.CustomerModel;
import jp.ken.project.model.OrderFormModel;

@Controller
public class OrderController {

	// プルダウン用の数字のリストを作成するメソッド
	private List<String> getNumberList(int start, int end){
		List<String> numberList = new ArrayList<String>();

		// start～endまでの数字をリストの中に格納する
		for(int i = start; i<= end; i++) {
			numberList.add(Integer.toString(i));
		}

		return numberList;
	}

	// 発注情報入力画面へ遷移
	@RequestMapping(value = "/order", method = RequestMethod.GET)
	public String get(Model model, HttpSession session){
		// セッションから会員モデルを取得
		CustomerModel customerModel = (CustomerModel) session.getAttribute("customerModel");
		// セッションのcustomerModelがnullならログインに飛ばす
		if(customerModel==null) {
			return "redirect:/login";
		}

		OrderFormModel orderFormModel = new OrderFormModel();

		// 会員モデルから氏名、電話番号、郵便番号、住所を取得しOrderFormModelに格納
		// 氏名
		orderFormModel.setShipName(customerModel.getCustomer_name());
		// 電話番号
		if (customerModel.getPhone() != null) {
			String[] words = customerModel.getPhone().split("-");
			orderFormModel.setShipPhone1(words[0]);
			orderFormModel.setShipPhone2(words[1]);
			orderFormModel.setShipPhone3(words[2]);
		}
		// 郵便番号
		if (customerModel.getZip() != null) {
			String[] words = customerModel.getZip().split("-");
			orderFormModel.setShipZip1(words[0]);
			orderFormModel.setShipZip2(words[1]);
		}
		// 住所
		if (customerModel.getAddress() != null) {
			String[] words = customerModel.getAddress().split(" ");
			System.out.println(words[0]);
			orderFormModel.setShipPrefecture(words[0]);
			orderFormModel.setShipCity(words[1]);
			orderFormModel.setShipBlock(words[2]);
			if (words.length == 4) {	// 建物名が入力されていた場合
				orderFormModel.setShipBuilding(words[3]);
			}
		}
		// クレジットカード番号
		if (customerModel.getCreditcard_num() != null) {	// クレジットカード番号
			String[] words = customerModel.getMasked_creditcard_num().split("-");
			orderFormModel.setCreditNum1(words[0]);
			orderFormModel.setCreditNum2(words[1]);
			orderFormModel.setCreditNum3(words[2]);
			orderFormModel.setCreditNum4(words[3]);
		}
		// クレジットカード有効期限
		if (customerModel.getCreditcard_exp() != null) {	// クレジットカード有効期限
			String[] words = customerModel.getCreditcard_exp().split("/");
			orderFormModel.setCreditExpM(words[0]);
			orderFormModel.setCreditExpY(words[1]);
		}

		// 今年が何年かを取得
		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);

		// プルダウンで表示するようのリストをモデルに紐づけ
		model.addAttribute("creditExpMList", getNumberList(1, 12));
		model.addAttribute("creditExpYList", getNumberList(thisYear, thisYear +10));

		// 入力項目を格納するモデルを紐づけ
		model.addAttribute("orderFormModel", orderFormModel);
		return "order";
	}

	// 発注情報入力画面へ遷移
	@RequestMapping(value = "/order", method = RequestMethod.POST)
	public String post(@ModelAttribute OrderFormModel orderFormModel ,@RequestParam("action") String action,
			Model model, HttpSession session){

		if ("注文確認".equals(action)) {
            // 注文確認画面へ遷移する処理
//            return "confirm";
			session.setAttribute("orderFormModel", orderFormModel);
            return "redirect:/confirm";  // ConfirmのControllerができたら書き換える

        } else if ("戻る".equals(action)) {
            // 商品詳細画面へ遷移する処理
            return "cart";
//            return "redirect:/cart";  // CartのControllerができたら書き換える

        } else {
            // 未知のアクション

        	// エラーメッセージを表示
        	model.addAttribute("error", "エラーが発生しました。");

            return "order";  // 発送情報画面へ遷移
        }
	}


}
