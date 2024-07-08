package com.ite.notificationservice.event.eventHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ite.notificationservice.config.SystemLogger;
import com.ite.notificationservice.constants.JobQueue;
import com.ite.notificationservice.constants.LogStepConstant;
import com.ite.notificationservice.event.mesages.ApproveAccountNotification;
import com.ite.notificationservice.event.mesages.LockAccountNotification;
import com.ite.notificationservice.event.mesages.ResetPasswordMessage;
import com.ite.notificationservice.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NotificationEventHandler {
    @Autowired
    private MailService mailService;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SystemLogger logger;

    @RabbitHandler
    @RabbitListener(queues = JobQueue.APPROVED_ACCOUNT_NOTICE_QUEUE)
    public void sendMailApproveAccountWithQrCode(String json) {
        try {

            logger.log(Thread.currentThread().getName(), "Send mail approve account with QR code Authen", LogStepConstant.BEGIN_PROCESS,json);
            ApproveAccountNotification approveAccountNotification = objectMapper.readValue(json, ApproveAccountNotification.class);
            new Thread(() -> {
                mailService.sendMailApproveAccountWithQrCode( approveAccountNotification.getEmail(), approveAccountNotification.getName(), approveAccountNotification.getQrCode());
            }).start();
            logger.log(Thread.currentThread().getName(), "Send mail approve account with QR code Authen", LogStepConstant.END_PROCESS,json);

        } catch (JsonProcessingException e) {
            logger.log(Thread.currentThread().getName(), "Send mail approve account with QR code Authen", LogStepConstant.END_PROCESS,"ERROR");
            log.error("{}",e);
        }
    }

    @RabbitHandler
    @RabbitListener(queues = JobQueue.LOCK_ACCOUNT_NOTICE_QUEUE)
    public void sendMailLockAccount(String json){
        try {
            logger.log(Thread.currentThread().getName(), "Send mail lock account",LogStepConstant.BEGIN_PROCESS, json);

            LockAccountNotification notification = objectMapper.readValue(json,LockAccountNotification.class);

            new Thread(()-> {
                mailService.sendMailLockAccount(notification);
            }).start();
            logger.log(Thread.currentThread().getName(), "Send mail lock account",LogStepConstant.END_PROCESS, notification.getEmail());

        } catch (Exception e) {
            logger.logError(Thread.currentThread().getName(), "Send mail lock account",LogStepConstant.END_PROCESS, "ERROR: " + e.getMessage());
        }
    }

    @RabbitHandler
    @RabbitListener(queues = JobQueue.RESET_PASSWORD_NOTICE_QUEUE)
    public void sendMailResetPassword(String json){
        try {
            logger.log(Thread.currentThread().getName(), "Send mail reset password",LogStepConstant.BEGIN_PROCESS, json);

            ResetPasswordMessage message = objectMapper.readValue(json, ResetPasswordMessage.class);
            new Thread(()->{
                mailService.sendMailResetPassword(message);
            }).start();

            logger.log(Thread.currentThread().getName(), "Send mail reset password",LogStepConstant.END_PROCESS, "OK OK OK");
        } catch (Exception e) {
            logger.logError(Thread.currentThread().getName(), "Send mail reset password",LogStepConstant.END_PROCESS, "ERROR: " + e.getMessage());
        }
    }
}
