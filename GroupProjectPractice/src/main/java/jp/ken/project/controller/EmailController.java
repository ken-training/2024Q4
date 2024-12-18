package jp.ken.project.controller;

import java.util.Properties;

import javax.mail.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.ken.project.mail.EmailService;

@Controller
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "/send-email", method = RequestMethod.GET)
    public String sendEmail() {
    	 // デバッグ用のプロパティを設定
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.debug", "true"); // ログ出力を有効にする

        Session session = Session.getInstance(properties);
        session.setDebug(true); // デバッグ情報をコンソールに出力


        try {
        	String to = "nagoyakenost@gmail.com";
        	String subject = "test";
        	String body = "おためし";
            emailService.sendEmail(to, subject, body);
            return "redirect:/top";
//        } catch (MessagingException e) {
  //      	return "エラー";
        } catch (MailSendException e) {
        	return "meilsendエラー";
        } catch (MailException e) {
        	return "エラー";
        } catch (Exception e) {
            return "redirect:/regist";
        }
    }
}
