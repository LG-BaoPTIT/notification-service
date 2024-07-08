package com.ite.notificationservice.event.mesages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApproveAccountNotification{
    private String email;
    private String name;
    private String qrCode;
}
