package com.example.messagemicroservice.dtos;

import java.time.LocalDate;

public class MessageDTO {
    private Long id;
    private Long receiver;
    private Long sender;
    private LocalDate date;
    private String info;
    private String otherUserName;

    public MessageDTO(Long id, Long receiver, Long sender, LocalDate date, String info) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.date = date;
        this.info = info;
    }

    public MessageDTO(Long id, Long receiver, Long sender, LocalDate date, String info, String otherUserName) {
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

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver) {
        this.receiver = receiver;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }
}

