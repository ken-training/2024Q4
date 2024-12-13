package jp.ken.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
	public String postRegist(Model model,  @Validated(GroupOrder.class) @ModelAttribute CustomerModel customer,BindingResult result) {
	// バリデーションエラーがある場合
		if (result.hasErrors()) {
			return "regist";  // エラーがあれば再度入力画面を表示
		} else {
			// customerModelからcustomerModelにデータをコピー
			CustomerModel customerModel = new CustomerModel();
			customerModel.setCustomer_name(customer.getCustomer_name());
			customerModel.setMail(customer.getMail());
			customerModel.setPassword(customer.getPassword());

				// 顧客情報をデータベースに登録
				int numberOfRow = customerDao.registCustomer(customerModel);
				if (numberOfRow == 1) {
					// 登録が成功した場合
					return "redirect:/top";  // 成功時に遷移するビュー
				}
				model.addAttribute("error", "このメールアドレスは既に登録されています");
				return "regist";  // 登録に失敗した場合、再度エラーメッセージを表示
			}
		}
}