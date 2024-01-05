package com.example.messagemicroservice.dtos;

import java.time.LocalDate;

public class MessageDTO {
    private Long id;
    private String receiver;
    private String sender;
    private LocalDate date;
    private String info;
    private String otherUserName;

    public MessageDTO(Long id, String receiver, String sender, LocalDate date, String info) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.date = date;
        this.info = info;
    }

    public MessageDTO(Long id, String receiver, String sender, LocalDate date, String info, String otherUserName) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.date = date;
        this.info = info;
        this.otherUserName = otherUserName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }
}

