package com.guava.E_HOSTELS.communications.notifications.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotification {

    @Id
    private String id;
    private String senderId;
    private String recipientId;
    private String content;
}
