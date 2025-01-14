package jp.ken.project.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    public String toLogin(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
	    // キャッシュを無効化
	    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", 0);

        // リファラ（遷移元URL）を取得してセッションに保存
        String referer = request.getHeader("Referer");
        if (referer != null) {
            session.setAttribute("login_referer", referer);
        }

        // セッションから会員情報保持しているか確認して、なければトップへ遷移
        CustomerModel customerModel = (CustomerModel) session.getAttribute("customerModel");
        if(customerModel != null) {
        	return "redirect:/top";
        }

        return "login";
    }

    // POSTリクエスト時にログイン処理を行う
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Validated(GroupOrder.class) @ModelAttribute("loginFormModel") LoginFormModel loginForm,
                        BindingResult bindingResult, Model model, HttpSession session) {

        // 入力エラーがあれば、再度ログイン画面に戻す
        if (bindingResult.hasErrors()) {
            return "login";
        }

        // メールアドレスで顧客情報を取得
        CustomerModel customerModel = loginDao.getCustomerByMail(loginForm.getMailaddress());

        // 退会したユーザーや存在しないメールアドレスの場合
        if (customerModel == null) {
            model.addAttribute("error", "メールアドレスまたはパスワードが間違っています");
            return "login";
        }

        // ユーザー認証処理
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String encodedPassword = encoder.encodePassword(loginForm.getPassword(), customerModel.getCustomer_id());

        // パスワードが一致した場合
        if (encodedPassword.equals(customerModel.getPassword())) {
            // ログイン成功時、セッションにユーザー情報を保存
            session.setAttribute("customerModel", customerModel);

            String referer = (String) session.getAttribute("login_referer");

            if (referer != null) {
                String[] parts = referer.split("/");
                session.removeAttribute("login_referer");
                session.removeAttribute("doOrderFlg");

                if (parts[parts.length - 1].equals("cart")) {
                    return "redirect:/order";
                } else {
                    return "redirect:/top";
                }
            } else {
                return "redirect:/top";
            }
        } else {
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
        session.removeAttribute("orderFormModel");  // 商品詳細の遷移元URL
       return "redirect:/top";  // ログアウト後トップページへ
   }
}
