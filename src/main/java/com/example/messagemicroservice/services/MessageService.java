package com.example.messagemicroservice.services;

import com.example.messagemicroservice.dtos.MessageDTO;
import com.example.messagemicroservice.entities.Message;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface MessageService
{
    List<Message> getAllMessages();
    Message getMessageById(Long id);
    Message updateMessage(Long id, Message updatedMessage);
    boolean deleteMessage(Long id);

    Message createMessage(String info, LocalDate date, Long senderId, Long receiverId);

    Map<Long, List<MessageDTO>> getMessagesByUser(Long userId);
}
