package com.ite.notificationservice.event.mesages;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LockAccountNotification {
    private String email;
    private String name;
}
