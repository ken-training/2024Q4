package jp.ken.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ken.project.dao.CustomerDao;
import jp.ken.project.group.GroupOrder;
import jp.ken.project.model.CustomerModel;

@Controller
public class RegistController {

	@Autowired
	private CustomerDao customerDao;

	@RequestMapping(value ="/regist",method=RequestMethod.GET)
	public String getRegist(Model model) {
		model.addAttribute("customerModel",new CustomerModel());
		return "regist";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public String postRegist(Model model,  @Validated(GroupOrder.class) @ModelAttribute CustomerModel customer,
			BindingResult result, HttpSession session) {

		// エラーがあれば再度入力画面を表示
		if (result.hasErrors()) {
			return "regist";

		} else {
			// customerModelからcustomerModelにデータをコピー
			CustomerModel customerModel = new CustomerModel();
			customerModel.setCustomer_name(customer.getCustomer_name());
			customerModel.setMail(customer.getMail());
			customerModel.setPassword(customer.getPassword());

			// 顧客情報をデータベースに登録
			int customer_id = customerDao.registCustomerAndGetId(customerModel);

			// 正常終了した場合
			if (customer_id > 0) {
				// 取得したカスタマーIDをセット
				customerModel.setCustomer_id(customer_id);

				// パスワードをハッシュ化
				ShaPasswordEncoder encoder = new ShaPasswordEncoder();
				String encodePassword = encoder.encodePassword(customerModel.getPassword(), customerModel.getCustomer_id());
				customerModel.setPassword(encodePassword);
				// DBのパスワードを書き換え
				int numberOfRow = customerDao.registHashPassword(customer_id, encodePassword);
				if (numberOfRow == 1) {
					// 登録が成功した場合
					session.setAttribute("customerModel", customerModel); // セッションに顧客情報を保存
					Integer doOrderFlg = (Integer)session.getAttribute("doOrderFlg");  // 購入へ進むを実行したフラグ
					if(doOrderFlg != null && doOrderFlg == 1) {
						// 購入へ進むを実行した上で新規登録まで進んできた場合
						session.removeAttribute("doOrderFlg");  // ↑のフラグをセッションから削除
						return "redirect:/order";
					}else {
						// その他の場合
						return "redirect:/top";  // 成功時に遷移するビュー
					}
				}

			// データベースにアクセスできたがレコード数が想定外の場合
			} else if (customer_id == -1) {
				model.addAttribute("error", "データベースエラーが発生しました");

			// すでにメールアドレスが登録されている場合
			} else if (customer_id == -2) {
				model.addAttribute("error", "このメールアドレスは既に登録されています");

			// データアクセス例外が発生した場合
			} else if (customer_id == -3) {
				model.addAttribute("error", "データアクセスに失敗しました");

			// トランザクション関連の例外が発生した場合
			} else if (customer_id == -4) {
				model.addAttribute("error", "トランザクションエラーが発生しました");

			// その他例外が発生した場合
			} else if (customer_id == -5) {
				model.addAttribute("error", "エラーが発生しました");
			}

			// 登録に失敗した場合、再度エラーメッセージを表示
			System.out.println("customer_id" + customer_id);
			return "regist";
		}
	}
}