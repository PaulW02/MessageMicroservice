package com.example.messagemicroservice.repositories;

import com.example.messagemicroservice.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long>
{
    List<Message> findAllBySenderOrReceiver(String senderId, String receiverId);
}
