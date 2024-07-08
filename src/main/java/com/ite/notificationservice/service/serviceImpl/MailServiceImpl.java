package com.ite.notificationservice.service.serviceImpl;

import com.ite.notificationservice.event.mesages.LockAccountNotification;
import com.ite.notificationservice.event.mesages.ResetPasswordMessage;
import com.ite.notificationservice.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }


    @Override
    public void sendMailApproveAccountWithQrCode(String to, String name, String qr) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("ACCOUNT HAS BEEN APPROVED");

            // HTML content for the email
            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>QR Code Image</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "   <div\n" +
                    "      style=\"\n" +
                    "        width: 100%;\n" +
                    "        max-width: 600px;\n" +
                    "        margin: 0 auto;\n" +
                    "        font-family: Arial, sans-serif;\n" +
                    "      \"\n" +
                    "    >\n" +
                    "      <div style=\"text-align: center\">\n" +
                    "        <img\n" +
                    "          src=\"https://cdn0684.cdn4s.com/media/logo.png\"\n" +
                    "          alt=\"LOGO\"\n" +
                    "          style=\"\n" +
                    "            width: 100%;\n" +
                    "            max-width: 200px;\n" +
                    "            margin: 0 auto;\n" +
                    "            display: block;\n" +
                    "          \"\n" +
                    "        />\n" +
                    "      </div>\n" +
                    "      <div style=\"padding: 18px 10px; line-height: 24px; text-align: justify\">\n" +
                    "        <div style=\"font-size: 14px\">\n" +
                    "          <strong>Xin chào " + name + " !</strong>\n" +
                    "        </div>\n" +
                    "        <div>Chúng tôi vừa xác nhận tài khoản của bạn.</div>\n" +
                    "\n" +
                    "        <div>\n" +
                    "          <p style=\"font-weight: bold\">\n" +
                    "            Để tăng cường bảo mật cho tài khoản, hãy thực hiện thêm bước xác\n" +
                    "            minh 2 lớp bằng Google Authenticator:\n" +
                    "          </p>\n" +
                    "          <ol>\n" +
                    "            <li style=\"margin-bottom: 10px\">\n" +
                    "              Cài đặt ứng dụng Google Authenticator trên điện thoại thông minh\n" +
                    "              của bạn (nếu chưa có).\n" +
                    "            </li>\n" +
                    "            <li style=\"margin-bottom: 10px\">\n" +
                    "              Sử dụng ứng dụng để quét mã QR sau:\n" +
                    "            </li>\n" +
                    "            <img\n" +
                    "              src=\"cid:qrCodeImage\"\n" +
                    "              alt=\"QR Code\"\n" +
                    "              style=\"\n" +
                    "                width: 100%;\n" +
                    "                max-width: 200px;\n" +
                    "                margin: 0 auto;\n" +
                    "                display: block;\n" +
                    "              \"\n" +
                    "            />\n" +
                    "            <li>\n" +
                    "              Nhập mã 6 chữ số được hiển thị trong ứng dụng Google Authenticator\n" +
                    "              vào trang đăng nhập.\n" +
                    "            </li>\n" +
                    "          </ol>\n" +
                    "        </div>\n" +
                    "      </div>\n" +
                    "    </div>  \n" +
                    "</body>\n" +
                    "</html>\n";

            // Set the email content as HTML
            helper.setText(htmlContent, true);
            String base64Code = qr.split(",")[1];
            // Embed the QR code image with Content-ID
            byte[] qrCodeImageBytes = java.util.Base64.getDecoder().decode(base64Code); // Decode base64 string
            ByteArrayResource qrCodeImageResource = new ByteArrayResource(qrCodeImageBytes);
            helper.addInline("qrCodeImage", qrCodeImageResource, "image/png");

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailLockAccount(LockAccountNotification notification) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(notification.getEmail());
            helper.setSubject("ACCOUNT LOCKED");

            String htmlContent = "<div\n" +
                    "      style=\"margin: 0; padding: 0; background-color: #f3f3f3\"\n" +
                    "    >\n" +
                    "      <div\n" +
                    "        style=\"\n" +
                    "          background: #f3f3f3;\n" +
                    "          color: #444;\n" +
                    "          font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\n" +
                    "          font-size: 14px;\n" +
                    "          min-height: 100% !important;\n" +
                    "          line-height: 1.5em;\n" +
                    "          margin: 0;\n" +
                    "          padding: 0;\n" +
                    "          width: 100% !important;\n" +
                    "        \"\n" +
                    "      >\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "          <tbody>\n" +
                    "            <tr>\n" +
                    "              <td\n" +
                    "                align=\"center\"\n" +
                    "                bgcolor=\"#f3f3f3\"\n" +
                    "                valign=\"top\"\n" +
                    "                style=\"background: #f3f3f3; color: #000000; padding: 30px 0px\"\n" +
                    "              >\n" +
                    "                <h1 style=\"margin: 0\">\n" +
                    "                  <a href=\"#m_1705471391692128818_\">\n" +
                    "                    <img\n" +
                    "                      src=\"https://ci3.googleusercontent.com/meips/ADKq_NYu0iabDRjcHfY4qf4PS9zYx92OuUnMORdfmJTFHAWpfXRygVsF6eA_Sxs6VdtQ-dVeFfrpQ7MVwAMz79Lv=s0-d-e1-ft#https://cdn0684.cdn4s.com/media/logo.png\"\n" +
                    "                      width=\"150\"\n" +
                    "                      alt=\"cci.vn\"\n" +
                    "                      style=\"border: 0\"\n" +
                    "                      class=\"CToWUd\"\n" +
                    "                      data-bit=\"iit\"\n" +
                    "                    />\n" +
                    "                  </a>\n" +
                    "                </h1>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td align=\"center\">\n" +
                    "                <table\n" +
                    "                  cellpadding=\"0\"\n" +
                    "                  cellspacing=\"0\"\n" +
                    "                  width=\"100%\"\n" +
                    "                  style=\"max-width: 600px\"\n" +
                    "                >\n" +
                    "                  <tbody>\n" +
                    "                    <tr>\n" +
                    "                      <td\n" +
                    "                        style=\"padding: 20px; border-bottom: 2px solid #dddddd\"\n" +
                    "                        bgcolor=\"#ffffff\"\n" +
                    "                      >\n" +
                    "                        <h3>Tải khoản của bạn đã bị khóa</h3>\n" +
                    "                        <p>\n" +
                    "                          Vì tài khoản của bạn đăng nhập sai quá 5 lần, nên tài khoản của bạn đã bị khóa <br> Vui lòng liên hệ \n" +
                    "                          <a\n" +
                    "                            href=\"mailto:partner@cci.vn\"\n" +
                    "                            title=\"Quản lý CV\"\n" +
                    "                            style=\"color: #009dda; text-decoration: underline\"\n" +
                    "                            target=\"_blank\"\n" +
                    "                            >hotro@cci.vn</a\n" +
                    "                          >\n" +
                    "                          hoặc\n" +
                    "                          <a\n" +
                    "                          href=\"mailto:partner@cci.vn\"\n" +
                    "                          title=\"Quản lý CV\"\n" +
                    "                          style=\"color: #009dda; text-decoration: underline\"\n" +
                    "                          target=\"_blank\"\n" +
                    "                          >0837482734</a\n" +
                    "                        >\n" +
                    "                          để biết thêm chi tiết và nhận được hỗ trợ.\n" +
                    "                        </p>\n" +
                    "                        <p>ITE Connect</p>\n" +
                    "                        <span\n" +
                    "                          style=\"\n" +
                    "                            font-size: 11px;\n" +
                    "                            font-style: italic;\n" +
                    "                            text-align: center;\n" +
                    "                            display: block;\n" +
                    "                            color: #888;\n" +
                    "                          \"\n" +
                    "                          >-- Đây là email tự động. Xin bạn vui lòng không gửi\n" +
                    "                          phản hồi vào hộp thư này --</span\n" +
                    "                        >\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td align=\"center\">\n" +
                    "                <table\n" +
                    "                  border=\"0\"\n" +
                    "                  cellpadding=\"0\"\n" +
                    "                  cellspacing=\"0\"\n" +
                    "                  width=\"100%\"\n" +
                    "                  style=\"max-width: 600px\"\n" +
                    "                >\n" +
                    "                  <tbody>\n" +
                    "                    <tr>\n" +
                    "                      <td\n" +
                    "                        style=\"\n" +
                    "                          padding: 10px;\n" +
                    "                          font-size: 12px;\n" +
                    "                          color: #888;\n" +
                    "                          text-align: center;\n" +
                    "                        \"\n" +
                    "                        bgcolor=\"#f3f3f3\"\n" +
                    "                        align=\"center\"\n" +
                    "                      >\n" +
                    "                        <p>\n" +
                    "                          &amp;copy 2023 ITE Connect.,JSC. All rights reserved.\n" +
                    "                        </p>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "          </tbody>\n" +
                    "        </table>\n" +
                    "      </div>\n" +
                    "      <img\n" +
                    "        width=\"1px\"\n" +
                    "        height=\"1px\"\n" +
                    "        alt=\"\"\n" +
                    "        src=\"https://ci5.googleusercontent.com/proxy/P18x1AnCTuCWQu631wMmoqn0WRnhGFac525AjBccl4kimV5E-5PneKJY_2kiqUEJIyFIYu8Z67JpEgeb4mk1vGRV2xmVJry65aFIFlppa88HyLsjgU-pJ8OfWvlcLnCNdu_hLCKjEea9K1t1A-uaBnIyehE6ANDPH8n60r4Gnbnr8zE7MD_Fhqt3P8E-s2KueMKMcBuaaLF9jkGPamog2ocMkYvKTqQLnGmxdipoNrQcmX2f6CLHhyi94BYjlTWWTpNimsfgZYy7LHxlKZKLiTaFHc9l8fIilqJQFbw=s0-d-e1-ft#http://email.mg.topcv.vn/o/eJwEwFHOgyAMAODTyCMptBT7wFn-FCr-JjqNOrfj77OiApbMTSWwUMqcKbn_Qsq9o1gLmRtm5KDVemWw2iVCc0uJEBEkICAlSj6PY02GLArQFWQg2GZ_70d7_PNyZ1m_9m4xAw4E86bL6tu-ubuc0zXdf4de12c_7RcAAP__PrUqrg\"\n" +
                    "        class=\"CToWUd\"\n" +
                    "        data-bit=\"iit\"\n" +
                    "      />\n" +
                    "      <div class=\"yj6qo\"></div>\n" +
                    "      <div class=\"adL\"></div>\n" +
                    "    </div>";

            // Set the email content as HTML
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMailResetPassword(ResetPasswordMessage resetPasswordMessage) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setTo(resetPasswordMessage.getEmail());
            helper.setSubject("RESET PASSWORD");

            String htmlContent = "    <div\n" +
                    "      style=\"margin: 0; padding: 0; background-color: #f3f3f3\"\n" +
                    "    >\n" +
                    "      <div\n" +
                    "        style=\"\n" +
                    "          background: #f3f3f3;\n" +
                    "          color: #444;\n" +
                    "          font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;\n" +
                    "          font-size: 14px;\n" +
                    "          min-height: 100% !important;\n" +
                    "          line-height: 1.5em;\n" +
                    "          margin: 0;\n" +
                    "          padding: 0;\n" +
                    "          width: 100% !important;\n" +
                    "        \"\n" +
                    "      >\n" +
                    "        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                    "          <tbody>\n" +
                    "            <tr>\n" +
                    "              <td\n" +
                    "                align=\"center\"\n" +
                    "                bgcolor=\"#f3f3f3\"\n" +
                    "                valign=\"top\"\n" +
                    "                style=\"background: #f3f3f3; color: #000000; padding: 30px 0px\"\n" +
                    "              >\n" +
                    "                <h1 style=\"margin: 0\">\n" +
                    "                  <a href=\"#m_1705471391692128818_\">\n" +
                    "                    <img\n" +
                    "                      src=\"https://ci3.googleusercontent.com/meips/ADKq_NYu0iabDRjcHfY4qf4PS9zYx92OuUnMORdfmJTFHAWpfXRygVsF6eA_Sxs6VdtQ-dVeFfrpQ7MVwAMz79Lv=s0-d-e1-ft#https://cdn0684.cdn4s.com/media/logo.png\"\n" +
                    "                      width=\"150\"\n" +
                    "                      alt=\"cci.vn\"\n" +
                    "                      style=\"border: 0\"\n" +
                    "                      class=\"CToWUd\"\n" +
                    "                      data-bit=\"iit\"\n" +
                    "                    />\n" +
                    "                  </a>\n" +
                    "                </h1>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td align=\"center\">\n" +
                    "                <table\n" +
                    "                  cellpadding=\"0\"\n" +
                    "                  cellspacing=\"0\"\n" +
                    "                  width=\"100%\"\n" +
                    "                  style=\"max-width: 600px\"\n" +
                    "                >\n" +
                    "                  <tbody>\n" +
                    "                    <tr>\n" +
                    "                      <td\n" +
                    "                        style=\"padding: 20px; border-bottom: 2px solid #dddddd\"\n" +
                    "                        bgcolor=\"#ffffff\"\n" +
                    "                      >\n" +
                    "                        <h3>Bạn vừa gửi yêu cầu reset mật khẩu?</h3>\n" +
                    "                        <a\n" +
                    "                          href= "+ resetPasswordMessage.getResetLink()+ "\n" +
                    "                          title=\"Reset Password\"\n" +
                    "                          style=\"color: #009dda; text-decoration: underline\"\n" +
                    "                          target=\"_blank\"\n" +
                    "                          data-saferedirecturl=\"https://www.google.com/url?q=http://210.211.99.111:15060/reset-password?token%3DCfDJ8EIvMGliN8dAl1yrWrXw/DXobkZqLMOsRpK7R0PnrYBROia8kgIL1a5CBUy5SYR2NUYu7f96xh3es4JAZLvuK4Di7gynAC4r9XkRh%2B2Rnxev3BCAcZKt/y3h9LXURffCV35gAbXNe3nfAJJyVtmcclB1oWgWV6RJDBOrYlcUgAbHz0gXbr8STmh/sNqZMXLHa1ACWXwJ3GLLk1ccjPq%2Bq3Oyj9Car83D/bAawweEPz%2BD%26email%3Dnguyenhai20112003@gmail.com&amp;source=gmail&amp;ust=1710489379233000&amp;usg=AOvVaw1F_O44cA0FmD3_GBHQHUTK\"\n" +
                    "                          >Click vào link để reset lại mật khẩu.</a\n" +
                    "                        >\n" +
                    "                        <p>\n" +
                    "                          Nếu không phải bạn đã gửi yêu cầu reset mật khẩu, xin\n" +
                    "                          hãy bỏ qua email này.<br />Nếu có bất kì thắc mắc nào,\n" +
                    "                          vui lòng liên hệ\n" +
                    "                          <a\n" +
                    "                            href=\"mailto:partner@cci.vn\"\n" +
                    "                            title=\"Quản lý CV\"\n" +
                    "                            style=\"color: #009dda; text-decoration: underline\"\n" +
                    "                            target=\"_blank\"\n" +
                    "                            >mailto:hotro@cci.vn</a\n" +
                    "                          >\n" +
                    "                          để nhận được hỗ trợ.\n" +
                    "                        </p>\n" +
                    "                        <p>Cảm ơn bạn đã sử dụng dịch vụ.</p>\n" +
                    "                        <p>ITE Connect</p>\n" +
                    "                        <span\n" +
                    "                          style=\"\n" +
                    "                            font-size: 11px;\n" +
                    "                            font-style: italic;\n" +
                    "                            text-align: center;\n" +
                    "                            display: block;\n" +
                    "                            color: #888;\n" +
                    "                          \"\n" +
                    "                          >-- Đây là email tự động. Xin bạn vui lòng không gửi\n" +
                    "                          phản hồi vào hộp thư này --</span\n" +
                    "                        >\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "              <td align=\"center\">\n" +
                    "                <table\n" +
                    "                  border=\"0\"\n" +
                    "                  cellpadding=\"0\"\n" +
                    "                  cellspacing=\"0\"\n" +
                    "                  width=\"100%\"\n" +
                    "                  style=\"max-width: 600px\"\n" +
                    "                >\n" +
                    "                  <tbody>\n" +
                    "                    <tr>\n" +
                    "                      <td\n" +
                    "                        style=\"\n" +
                    "                          padding: 10px;\n" +
                    "                          font-size: 12px;\n" +
                    "                          color: #888;\n" +
                    "                          text-align: center;\n" +
                    "                        \"\n" +
                    "                        bgcolor=\"#f3f3f3\"\n" +
                    "                        align=\"center\"\n" +
                    "                      >\n" +
                    "                        <p>\n" +
                    "                          &amp;copy 2023 ITE Connect.,JSC. All rights reserved.\n" +
                    "                        </p>\n" +
                    "                      </td>\n" +
                    "                    </tr>\n" +
                    "                  </tbody>\n" +
                    "                </table>\n" +
                    "              </td>\n" +
                    "            </tr>\n" +
                    "          </tbody>\n" +
                    "        </table>\n" +
                    "      </div>\n" +
                    "      <img\n" +
                    "        width=\"1px\"\n" +
                    "        height=\"1px\"\n" +
                    "        alt=\"\"\n" +
                    "        src=\"https://ci5.googleusercontent.com/proxy/P18x1AnCTuCWQu631wMmoqn0WRnhGFac525AjBccl4kimV5E-5PneKJY_2kiqUEJIyFIYu8Z67JpEgeb4mk1vGRV2xmVJry65aFIFlppa88HyLsjgU-pJ8OfWvlcLnCNdu_hLCKjEea9K1t1A-uaBnIyehE6ANDPH8n60r4Gnbnr8zE7MD_Fhqt3P8E-s2KueMKMcBuaaLF9jkGPamog2ocMkYvKTqQLnGmxdipoNrQcmX2f6CLHhyi94BYjlTWWTpNimsfgZYy7LHxlKZKLiTaFHc9l8fIilqJQFbw=s0-d-e1-ft#http://email.mg.topcv.vn/o/eJwEwFHOgyAMAODTyCMptBT7wFn-FCr-JjqNOrfj77OiApbMTSWwUMqcKbn_Qsq9o1gLmRtm5KDVemWw2iVCc0uJEBEkICAlSj6PY02GLArQFWQg2GZ_70d7_PNyZ1m_9m4xAw4E86bL6tu-ubuc0zXdf4de12c_7RcAAP__PrUqrg\"\n" +
                    "        class=\"CToWUd\"\n" +
                    "        data-bit=\"iit\"\n" +
                    "      />\n" +
                    "      <div class=\"yj6qo\"></div>\n" +
                    "      <div class=\"adL\"></div>\n" +
                    "    </div>";
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
