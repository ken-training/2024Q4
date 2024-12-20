package jp.ken.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ken.project.dao.EmployeeLoginDao;
import jp.ken.project.group.GroupOrder;
import jp.ken.project.model.EmployeeLoginFormModel;
import jp.ken.project.model.EmployeeLoginModel;

@Controller
public class EmployeeLoginController {

	@Autowired
	private EmployeeLoginDao employeeLoginDao;

	// @ModelAttributeを使ってEmployeeLoginFormModelをビューに渡す
	@ModelAttribute("employeeLoginFormModel")
	public EmployeeLoginFormModel setupEmployeeLoginFormModel () {
    	return new EmployeeLoginFormModel ();
	}

	// GETリクエスト時に従業員ログイン画面を表示
	@RequestMapping(value = "/emplogin", method = RequestMethod.GET)
	public String toEmpLogin(HttpServletRequest request, HttpSession session) {
		// リファラ（遷移元URL）を取得してセッションに保存
		String referer = request.getHeader("Referer");
		if (referer != null) {
			session.setAttribute("EmployeeLogin_referer", referer);
		}
		return "EmployeeLogin";
    }

    // POSTリクエスト時に従業員ログイン処理を行う
	@RequestMapping(value = "/emplogin", method = RequestMethod.POST)
	public String empLogin(@Validated (GroupOrder.class) @ModelAttribute("employeeLoginFormModel") EmployeeLoginFormModel employeeLoginForm,
			BindingResult result, Model model, HttpSession session) {
		// エラーがあれば再度ログイン画面を表示
		if (result.hasErrors()) {
			return "EmployeeLogin";
			}
		EmployeeLoginModel employeeLoginModel = employeeLoginDao.getEmployeeLoginByMail(employeeLoginForm.getMailaddress());
		  //退会したユーザーがログインしたとき
		if(employeeLoginModel == null) {
			model.addAttribute("error", "メールアドレスまたはパスワードが間違っています");
			return "EmployeeLogin";
		}
		session.setAttribute("employeeLoginModel", employeeLoginModel);
		return "redirect:/empmenu";
	}


	@RequestMapping(value = "/empmenu",method = RequestMethod.GET)
	public String toEmpMenu(HttpSession session) {
		EmployeeLoginModel employeeLoginModel = (EmployeeLoginModel) session.getAttribute("employeeLoginModel");
		// 従業員がログインしてない場合はログインページへ
		if(employeeLoginModel == null) {
			return "redirect:/emplogin";
		}
		return "empMenu";
	}


	// ログアウト処理
	@RequestMapping(value = "/emplogout",method = RequestMethod.GET)
	public String toEmpLogout(HttpSession session) {
		session.removeAttribute("employeeLoginModel");  //従業員情報
		return "redirect:/emplogin";  // ログアウト後従業員ログインページへ
	}

}
