package com.example.messagemicroservice.services;

import com.example.messagemicroservice.dtos.MessageDTO;
import com.example.messagemicroservice.entities.Message;
import com.example.messagemicroservice.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    private final WebClient userClient;

    @Autowired
    public MessageServiceImpl(WebClient.Builder webClientBuilder) {
        this.userClient = webClientBuilder.baseUrl("http://login-microservice-service:5001/user").build();
    }

    public Map<String, List<MessageDTO>> getMessagesByUser(String userId) {
        // Retrieve messages from the repository based on the user ID
        List<Message> messages = messageRepository.findAllBySenderOrReceiver(userId, userId);

        // Collect unique user IDs from messages
        Set<String> userIds = new HashSet<>();
        for (Message message : messages) {
            userIds.add(getOtherUserId(userId, message));
        }

        // Fetch user details in bulk
        Map<String, String> userNameById = fetchUserNames(new ArrayList<>(userIds));

        // Organize messages into a map where the key is the other user's ID
        Map<String, List<MessageDTO>> messagesByUser = new HashMap<>();

        for (Message message : messages) {
            String otherUserId = getOtherUserId(userId, message);
            String otherUserName = userNameById.get(otherUserId);

            messagesByUser.computeIfAbsent(otherUserId, k -> new ArrayList<>())
                    .add(convertToDTOWithName(message, otherUserName));
        }

        return messagesByUser;
    }

    private Map<String, String> fetchUserNames(List<String> userIds) {
        String userIdsParam = userIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));

        String uri = String.format("/names?userIds=%s", userIdsParam);

        return userClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                .block();
    }
    private MessageDTO convertToDTO(Message message) {
        MessageDTO dto = new MessageDTO(message.getId(), message.getReceiver(), message.getSender(),message.getDate(), message.getInfo());
        return dto;
    }

    private MessageDTO convertToDTOWithName(Message message, String name) {
        MessageDTO dto = new MessageDTO(message.getId(), message.getReceiver(), message.getSender(),message.getDate(), message.getInfo(), name);
        return dto;
    }

    private String getOtherUserId(String userId, Message message) {
        // Determine the ID of the other user in the conversation
        if (userId.equals(message.getSender())) {
            return message.getReceiver();
        } else {
            return message.getSender();
        }
    }

    public Message createMessage(String info, LocalDate date, String senderId, String receiverId) {
        if (info != null && date != null && senderId != null && receiverId != null) {
            Message createdMessage = new Message(info, date, senderId, receiverId);
            return messageRepository.save(createdMessage);
        }
        return null;
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        return message.orElse(null);
    }

    public Message updateMessage(Long id, Message updatedMessage) {
        Optional<Message> existingMessage = messageRepository.findById(id);
        if (existingMessage.isPresent()) {
            updatedMessage.setId(id);
            return messageRepository.save(updatedMessage);
        } else {
            return null;
        }
    }

    public boolean deleteMessage(Long id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            messageRepository.deleteById(id);
            return true;
        }
        return false;
    }



}

