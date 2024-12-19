package jp.ken.project.mail;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
    private JavaMailSender javaMailSender;

	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(String to, String subject, String body)  {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);

            // プレーンテキスト部分
            String plainTextBody = body;

            // HTML部分を作成（改行を<br>に変換）
            String htmlBody = "<!DOCTYPE html>"
            				+ "<html lang=\"ja\">"
            				+ "<head>"
            				+ "<meta charset=\"UTF-8\">"
            				+ "</head>"
            				+ "<body style=\"color: black;\">"
            				+ "<div style=\"color: inherit;\">"
            				+ body.replace("\r\n", "<br>").replace("\n", "<br>")
            				+ "</div>"
            				+ "</body>"
            				+ "</html>";

            // MimeMultipartを使って、alternative型で作成
            MimeMultipart multipart = new MimeMultipart("alternative");

            // プレーンテキストの部分をMimeBodyPartとして追加
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(plainTextBody, "utf-8", "plain");
            multipart.addBodyPart(textPart);

            // HTMLの部分をMimeBodyPartとして追加
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlBody, "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);

            // メールメッセージにマルチパートをセット
            mimeMessage.setContent(multipart);

            javaMailSender.send(mimeMessage);
//        } catch (IOException e) {
//            throw new MailSendException("I/O関連のエラー（ネットワーク接続やファイルアクセスエラーなど）", e);
        } catch (AuthenticationFailedException e) {
        	logger.error("SMTPサーバーへの認証に失敗しました", e);  // ログに詳細を記録
            throw new MailSendException("SMTPサーバーへの認証に失敗しました。ユーザー名またはパスワードの確認をしてください。", e);
        } catch (MailSendException e) {
        	logger.error("メール送信に失敗しました", e);  // ログに詳細を記録
            throw new MailSendException("メッセージの作成や送信に関するエラーが発生しました。メールの構成を確認してください。", e);
        } catch (MessagingException e) {
            throw new MailSendException("メッセージの作成や送信に関するエラーが発生しました。メールの構成を確認してください。", e);
        } catch (MailException e) {
            throw new MailSendException("メール送信中にエラーが発生しました。設定を確認してください。", e);
        } catch (NullPointerException e) {
            throw new IllegalStateException("javaMailSenderがnullの状態で処理が実行されました。依存性注入を確認してください。", e);
        } catch (Exception e) {
            throw new RuntimeException("予期しないエラーが発生しました", e);
        }

    }
}
