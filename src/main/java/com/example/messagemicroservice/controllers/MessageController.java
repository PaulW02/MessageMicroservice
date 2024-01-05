package com.example.messagemicroservice.controllers;

import com.example.messagemicroservice.apis.LoginMicroserviceClient;
import com.example.messagemicroservice.dtos.CreateMessageDTO;
import com.example.messagemicroservice.dtos.MessageDTO;
import com.example.messagemicroservice.entities.Message;
import com.example.messagemicroservice.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    private final WebClient userClient;

    @Autowired
    public MessageController(WebClient.Builder webClientBuilder) {
        this.userClient = webClientBuilder.baseUrl("http://login-microservice-service:5001/user").build();
    }

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        List<MessageDTO> messageDTOS = messages.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(messageDTOS);
    }

    @GetMapping("/conversations/{userId}")
    public ResponseEntity<Map<String, List<MessageDTO>>> getMessagesByUser(@PathVariable String userId) {
        // Make a GET request to check if the user exists
        String userExists = userClient.get()
                .uri("/checkUser/{userId}", userId)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        // If the user exists, proceed with fetching messages
        if (userExists != null && !userExists.isEmpty()) {
            return ResponseEntity.ok(messageService.getMessagesByUser(userId));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<MessageDTO> getMessageById(@PathVariable Long id) {
        Message message = messageService.getMessageById(id);
        if (message != null) {
            return ResponseEntity.ok(convertToDTO(message));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody CreateMessageDTO createMessageDTO) {
        Message createdMessage = messageService.createMessage(createMessageDTO.getInfo(), LocalDate.now(), createMessageDTO.getSenderId(), createMessageDTO.getReceiverId());
        return ResponseEntity.ok(convertToDTO(createdMessage));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageDTO> updateMessage(@PathVariable Long id, @RequestBody MessageDTO updatedMessageDTO) {
        Message updatedMessage = convertToEntity(updatedMessageDTO);
        Message updated = messageService.updateMessage(id, updatedMessage);
        if (updated != null) {
            return ResponseEntity.ok(convertToDTO(updated));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        boolean deleted = messageService.deleteMessage(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private MessageDTO convertToDTO(Message message) {
        MessageDTO dto = new MessageDTO(message.getId(), message.getReceiver(), message.getSender(),message.getDate(), message.getInfo());
        return dto;
    }

    private Message convertToEntity(MessageDTO messageDTO) {
        Message message = new Message(messageDTO.getInfo(), messageDTO.getDate(), messageDTO.getSender(), messageDTO.getReceiver());
        return message;
    }

}
