package com.ite.notificationservice.service;

import com.ite.notificationservice.event.mesages.LockAccountNotification;
import com.ite.notificationservice.event.mesages.ResetPasswordMessage;

public interface MailService {
    void sendMail(String to, String subject, String body);
    void sendMailApproveAccountWithQrCode(String name, String to, String qr);
    void sendMailLockAccount(LockAccountNotification notification);
    void sendMailResetPassword(ResetPasswordMessage resetPasswordMessage);
}
