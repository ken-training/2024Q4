package jp.ken.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.ken.project.model.CustomerModel;


@Controller
@RequestMapping("/account")
public class AccountController {

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
	public String delete(Model model){
		return "redirect:/top";
	}

	// 会員情報変更処理
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateGet(Model model){
		return "accountUpdate";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePost(Model model){
		return "accountUpdate";
	}

}
