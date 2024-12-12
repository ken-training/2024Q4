package jp.ken.project.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.project.dao.CustomerDao;
import jp.ken.project.group.GroupOrder;
import jp.ken.project.model.CustomerModel;
import jp.ken.project.model.UpdateFormModel;


@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private CustomerDao customerDao;

	// マイページ画面へ遷移
	@RequestMapping(method = RequestMethod.GET)
	public String get(Model model){
		CustomerModel cModel = new CustomerModel();

		// (仮)オブジェクトに設定
		cModel.setMail("t.yamada@ken.jp");

		model.addAttribute("customerModel", cModel);

		return "account";
	}

	// submitの内容で次の処理を振り分け
	@RequestMapping(method = RequestMethod.POST)
	public String post(@RequestParam("action") String action,Model model){

		if ("会員情報変更".equals(action)) {
			// 会員情報変更画面へリダイレクト
			return "redirect:/account/update";

		} else if ("退会する".equals(action)) {
			// 退会処理へforward
			return "redirect:/account/delete";

		} else {
			// 未知のアクションの場合マイページへ遷移

			// エラーメッセージを表示
			model.addAttribute("error", "エラーが発生しました。");

			// マイページ画面へ遷移
			return "account";
		}
	}

	// 退会処理
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(Model model, CustomerModel customerModel,HttpSession session){
		customerModel.setMail("tamaoki1@yahoo.co.jp");//セッションで値をとってくる 現状は仮の値を入れている
		int numberOfRow = customerDao.updateIsRemove(customerModel); //退会処理を実行
		if (numberOfRow == 1) {
			session.invalidate();  // セッションを無効化（ログアウト）
			return "redirect:/top";
		}else {
		return "redirect:/top";
		}
	}

	// 会員情報変更処理
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateGet(Model model){
		UpdateFormModel updateFormModel = new UpdateFormModel();

		// 今年が何年かを取得
		Calendar cal = Calendar.getInstance();
		int thisYear = cal.get(Calendar.YEAR);

		// プルダウンで表示するようのリストをモデルに紐づけ
		model.addAttribute("birthYear", thisYear-30);
		model.addAttribute("birthYearList", getNumberList(thisYear-125, thisYear));
		model.addAttribute("birthMonthList", getNumberList(1, 12));

		model.addAttribute("creditExpMList", getNumberList(1, 12));
		model.addAttribute("creditExpYList", getNumberList(thisYear, thisYear +10));


		model.addAttribute("updateFormModel", updateFormModel);
		return "accountUpdate";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePost(@Validated(GroupOrder.class) @ModelAttribute UpdateFormModel updateFormModel,BindingResult result, Model model){
		// バリデーションエラーがある場合
		if (result.hasErrors()) {
					return "accountUpdate";  // エラーがあれば再度入力画面を表示

		// 正常
		} else {

			// セッションに紐づいた会員IDから更新対象の会員を特定

			// updateFormModelからcustomerModelにデータをコピー
			CustomerModel customerModel = new CustomerModel();
			customerModel.setCustomer_name(updateFormModel.getCustomer_name());
			customerModel.setMail(updateFormModel.getMail());
			customerModel.setPassword(updateFormModel.getPassword());

			// 顧客情報をデータベースに登録
			int numberOfRow = customerDao.registCustomer(customerModel);
			if (numberOfRow == 1) {
				// 登録が成功した場合
				return "redirect:/top";  // 成功時に遷移するビュー
			}
			model.addAttribute("error", "このメールアドレスは既に登録されています");
			return "regist";  // 登録に失敗した場合、再度エラーメッセージを表示
		}
//		return "accountUpdate";
	}

	// プルダウン用の数字のリストを作成するメソッド
	private List<String> getNumberList(int start, int end){
		List<String> numberList = new ArrayList<String>();

		// start～endまでの数字をリストの中に格納する
		for(int i = start; i<= end; i++) {
			numberList.add(Integer.toString(i));
		}

		return numberList;
	}

}
