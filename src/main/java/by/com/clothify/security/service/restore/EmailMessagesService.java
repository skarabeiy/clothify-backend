package by.com.clothify.security.service.restore;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailMessagesService {

    private static final String FROM_EMAIL = "clothifyapplication@gmail.com";
    private static final String SEND_CODE_SUBJECT = "Your Password Reset Code – Secure Your Account";
    private static final String COMPANY_NAME = "Clothify";
    private static final String SEND_CODE_TEXT = """
            Hi!<br>
            <br>
            We received a request to reset your password. To complete the process, please use the following verification code:<br>
            <br>
            Verification Code: <b>{0}</b><br>
            <br>
            This code will expire in {1} minutes. If you didn’t request this, please ignore this email or contact our support team immediately.<br>
            <br>
            Stay secure!<br>
            {2}<br>
            <br>
            <b>Need help?</b> Contact us at {3}.""";

    private final JavaMailSender mailSender;

    public boolean sendCodeMessageToEmail(String toEmail, String code, Long codeExpirationTime) {
        try {
            final String text = MessageFormat.format(SEND_CODE_TEXT, code, codeExpirationTime, COMPANY_NAME, FROM_EMAIL);
            sendHtmlMessageToEmail(toEmail, SEND_CODE_SUBJECT, text);
            return true;

        } catch (Exception e) {
            log.error("Failed send code message to email: {}", toEmail);
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public void sendHtmlMessageToEmail(String toEmail, String subject, String htmlText) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(FROM_EMAIL);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlText, true);

        mailSender.send(message);
    }
}
