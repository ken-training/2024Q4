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

import jp.ken.project.dao.LoginDao;
import jp.ken.project.group.GroupOrder;
import jp.ken.project.model.CustomerModel;
import jp.ken.project.model.LoginFormModel;

@Controller
public class LoginController {
	@Autowired
	private LoginDao loginDao;

    // @ModelAttributeを使ってLoginFormModelをビューに渡す
    @ModelAttribute("loginFormModel")
    public LoginFormModel setupLoginFormModel() {
        return new LoginFormModel();
    }

    // GETリクエスト時にログイン画面を表示
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request, HttpSession session) {
        // リファラ（遷移元URL）を取得してセッションに保存
        String referer = request.getHeader("Referer");
        if (referer != null) {
            session.setAttribute("login_referer", referer);
        }
        return "login";
    }

    // POSTリクエスト時にログイン処理を行う
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Validated (GroupOrder.class) @ModelAttribute("loginFormModel") LoginFormModel loginForm,
                        BindingResult bindingResult, Model model, HttpSession session) {
       // エラーがあれば再度ログイン画面を表示
        if (bindingResult.hasErrors()) {
           return "login";
       }

        CustomerModel customerModel = loginDao.getCustomerByMail(loginForm.getMailaddress());

      //退会したユーザーがログインしたとき
        if(customerModel == null) {
        	model.addAttribute("error", "メールアドレスまたはパスワードが間違っています");
        	return "login";
        }

       // ユーザー認証処理
       if (loginForm.getPassword().equals(customerModel.getPassword())) {
           // ログイン成功時、トップページに遷移
    	   session.setAttribute("customerModel", customerModel);
    	   String referer = (String) session.getAttribute("login_referer");
    	   System.out.println("referer : "+referer);
    	   String[] parts = referer.split("/");
    	   // 遷移元のURLの最後が"cart"だったら"order"に飛ばしたい
    	   if(parts[parts.length - 1].equals("cart")) {
    		   return "redirect:/order";
    	   }else {
    		   // それ以外の通常時はtopへ
    		   return "redirect:/top";
    	   }


       } else {
           // パスワードが間違っている場合はエラーメッセージを表示
            model.addAttribute("error", "メールアドレスまたはパスワードが間違っています");
           return "login";
           }
        }


    // ログアウト処理
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public String toLogout(HttpSession session) {
        session.removeAttribute("cartList"); // カート
        session.removeAttribute("customerModel");  //会員情報
        session.removeAttribute("previousUrl");  // 商品詳細の遷移元URL
       return "redirect:/top";  // ログアウト後トップページへ
   }
}
