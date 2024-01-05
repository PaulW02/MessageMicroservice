package com.example.messagemicroservice.dtos;

import java.time.LocalDate;

public class CreateMessageDTO {
    private String receiverId;
    private String senderId;
    private String info;

    public CreateMessageDTO(String receiverId, String senderId, LocalDate date, String info) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.info = info;
    }


    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}

