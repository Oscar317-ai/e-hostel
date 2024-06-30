package com.guava.E_HOSTELS.communications.notifications.chat;

import com.guava.E_HOSTELS.communications.notifications.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService
                .findChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow();  // make my custom exception

         chatMessage.setChatId(chatId);
         messageRepository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.findChatRoomId(senderId,recipientId,false);
        return chatId.map(messageRepository::findByChatId).orElse(new ArrayList<>());
    }
}
