package com.guava.E_HOSTELS.communications.notifications.chatroom;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ChatroomRepository extends MongoRepository<ChatRoom,String> {
     Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
