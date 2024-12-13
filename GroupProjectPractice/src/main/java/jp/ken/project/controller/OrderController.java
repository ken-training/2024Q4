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
		orderFormModel.setShipName(customerModel.getCustomer_name());
		String AllPhone = customerModel.getPhone();
		orderFormModel.setShipPhone1(Integer.parseInt(AllPhone.split("-")[0]));
		orderFormModel.setShipPhone2(Integer.parseInt(AllPhone.split("-")[1]));
		orderFormModel.setShipPhone3(Integer.parseInt(AllPhone.split("-")[2]));
		String AllZip = customerModel.getZip();
		orderFormModel.setShipZip1(Integer.parseInt(AllZip.split("-")[0]));
		orderFormModel.setShipZip2(Integer.parseInt(AllZip.split("-")[1]));
		String AllAddress = customerModel.getAddress();
		if(AllAddress != null && !AllAddress.isEmpty()) {
			// まだ住所に空白を入れて登録してないので、保留
//			orderFormModel.setShipPrefecture(AllAddress.split(" ")[0]);
//			orderFormModel.setShipCity(AllAddress.split(" ")[1]);
//			orderFormModel.setShipBlock(AllAddress.split(" ")[2]);
//			orderFormModel.setShipBuilding(AllAddress.split(" ")[3]);
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
