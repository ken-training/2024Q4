package jp.ken.project.mail;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("yanxiuyong39@gmail.com");
        mailSender.setPassword("yehxukeyjcryearw");
        mailSender.setProtocol("smtp");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");  // 認証を有効にする
        props.put("mail.smtp.starttls.enable", "true");  // STARTTLSを有効にする
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");  // SMTPサーバーを信頼

        return mailSender;
    }
}
