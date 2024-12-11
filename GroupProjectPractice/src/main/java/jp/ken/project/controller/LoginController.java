package jp.ken.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jp.ken.project.dao.LoginDao;
import jp.ken.project.model.CustomerModel;
import jp.ken.project.model.LoginFormModel;

@Controller
//@RequestMapping("login")
@SessionAttributes("loginFormModel")//LoginFormModelにセッション置く
public class LoginController {
	@Autowired
	private LoginDao loginDao;

    // @ModelAttributeを使ってLoginFormModelをビューに渡す
    @ModelAttribute("loginFormModel")
    public LoginFormModel setupLoginForm() {
        return new LoginFormModel();
    }

    // GETリクエスト時にログイン画面を表示
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    // POSTリクエスト時にログイン処理を行う
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Validated @ModelAttribute("loginFormModel") LoginFormModel loginForm,
                        BindingResult bindingResult, Model model) {
       // エラーがあれば再度ログイン画面を表示
        if (bindingResult.hasErrors()) {
           return "login";
       }

        CustomerModel customerModel = loginDao.getCustomerByMail(loginForm.getMailaddress());

       // ユーザー認証処理
       if (loginForm.getPassword().equals(customerModel.getPassword())) {
           // ログイン成功時、トップページに遷移
           return "top";

       } else {
           // パスワードが間違っている場合はエラーメッセージを表示
            model.addAttribute("error", "メールアドレスまたはパスワードが間違っています");
           return "login";
           }
        }


    // ログアウト処理
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String toLogout(Model model, SessionStatus status) {
        status.setComplete();
       return "redirect:/top";  // ログアウト後トップページへ
   }
}
