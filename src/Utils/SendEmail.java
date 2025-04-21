package Utils;


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

    public static void sendEmail(String toEmail, String subject, String body) {
        final String fromEmail = "appshoesjava@gmail.com"; // Email gửi
        final String password = "hyxz cgkm puzq kmha";         // Mật khẩu ứng dụng Gmail
        
        String content="Chào bạn,\n\n" +
              "Bạn vừa yêu cầu đặt lại mật khẩu cho tài khoản trên [Shoes Management App].\n\n" +
              "Mã xác thực (OTP) của bạn là: " + body + "\n\n" +
              "Lưu ý:\n" +
              "- Không chia sẻ mã này với bất kỳ ai để bảo mật tài khoản của bạn.\n\n" +
              "Nếu bạn không yêu cầu đặt lại mật khẩu, hãy bỏ qua email này.\n\n" +
              "Trân trọng,\n" +
              "Shoes Management App Team";
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server
        props.put("mail.smtp.port", "587");            // TLS port
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.starttls.enable", "true"); // Bật TLS

        // Tạo phiên gửi mail
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));         // From
            message.setRecipients(Message.RecipientType.TO,          // To
                    InternetAddress.parse(toEmail));
            message.setSubject(subject);                             // Tiêu đề
            message.setText(content);                                   // Nội dung

            Transport.send(message); // Gửi mail
            System.out.println("Gửi email thành công!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        sendEmail("cuong75201@gmail.com","Xác nhận mật khẩu","501274");
    }
}