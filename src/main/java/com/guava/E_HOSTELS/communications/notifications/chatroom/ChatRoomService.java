package com.guava.E_HOSTELS.communications.notifications.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatroomRepository chatroomRepository;

    public Optional<String> findChatRoomId(String senderId,
                                           String recipientId,
                                           boolean createNewChatRoomIfNotExists) {
       return chatroomRepository.findBySenderIdAndRecipientId(senderId,recipientId)
               .map(ChatRoom::getChatId)
               .or(() -> {
                       if(createNewChatRoomIfNotExists) {
                           var chatId = createChatId(senderId, recipientId);
                           return Optional.of(chatId);
                       }
                           return Optional.empty();
               });
    }

    private String createChatId(String senderId, String recipientId) {

        var chatId = String.format("%s_%s",senderId,recipientId);

        ChatRoom senderRecipient = ChatRoom.builder()
                .chatId(chatId)
                .senderId(senderId)
                .recipientId(recipientId)
                .build();

        ChatRoom recipientSender = ChatRoom.builder()
                .chatId(chatId)
                .senderId(recipientId)
                .recipientId(senderId)
                .build();

        chatroomRepository.save(senderRecipient);
        chatroomRepository.save(recipientSender);
        return chatId;
    }
}

