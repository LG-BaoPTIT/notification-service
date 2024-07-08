package com.ite.notificationservice.config;


import com.ite.notificationservice.constants.JobQueue;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class RabbitQueueDefine {
//    @Autowired
//    @Qualifier("amqpAdmin")
//    private AmqpAdmin rabbitAdminMain;
//
//    @Autowired
//    private DirectExchange emailExchange;
//
//    @Bean
//    public Queue emailApproveAccountQueue() {
//        Queue queue = new Queue(JobQueue.APPROVED_ACCOUNT_QUEUE, true, false, false);
//        rabbitAdminMain.declareQueue(queue);
//        return queue;
//    }
//
//    @Bean
//    public Binding emailApproveAccountQueueBinding(DirectExchange emailExchange, Queue emailApproveAccountQueue) {
//        return BindingBuilder.bind(emailApproveAccountQueue).to(emailExchange).with("approveAccount.routing.key");
//    }

}
