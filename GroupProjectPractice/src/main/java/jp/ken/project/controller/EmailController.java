package jp.ken.project.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.ken.project.mail.EmailService;
import jp.ken.project.model.CustomerModel;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/send-email", method = RequestMethod.GET)
    public String sendEmail(Model model, HttpSession session, @RequestParam("mailBody") String mailBody,RedirectAttributes redirectAttributes) {
    	 // デバッグ用のプロパティを設定
//        Properties properties = System.getProperties();
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
//        properties.put("mail.debug", "true"); // ログ出力を有効にする
//
//        Session session = Session.getInstance(properties);
//        session.setDebug(true); // デバッグ情報をコンソールに出力


        try {
       		// セッションからモデルを取得
       		CustomerModel customerModel = (CustomerModel) session.getAttribute("customerModel");

       		String to = customerModel.getMail();
       		String subject = "【KEN Interior Shop】ご注文完了のご案内";
       		String body = mailBody;
       		emailService.sendEmail(to, subject, body);

       		// メール送信成功メッセージをリダイレクト後に表示
            redirectAttributes.addFlashAttribute("message", "注文完了メールを送信しました");
            return "redirect:/complete";

        } catch (MailSendException e) {
            e.printStackTrace();
            // メール送信に失敗した場合
//            redirectAttributes.addFlashAttribute("message", "メール送信に失敗しました。再試行してください。");
            return "redirect:/complete";

        } catch (MailException e) {
            e.printStackTrace();
            // メール送信の一般的なエラー
//            redirectAttributes.addFlashAttribute("message", "メール送信中にエラーが発生しました。");
            return "redirect:/complete";

        } catch (Exception e) {
            e.printStackTrace();
            // その他の予期しないエラー
//            redirectAttributes.addFlashAttribute("message", "予期しないエラーが発生しました。");
            return "redirect:/regist";
        }
    }

    @RequestMapping(value = "/complete", method = RequestMethod.GET)
    public String complete(Model model) {
        // メール送信の結果メッセージを表示
        model.addAttribute("message", model.asMap().get("message"));
        return "orderSuccess";
    }
}
