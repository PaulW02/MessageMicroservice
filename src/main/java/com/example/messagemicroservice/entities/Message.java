package com.example.messagemicroservice.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "messages")
public class Message
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id", nullable = false)
    private String receiver;

    @Column(name = "receiver_id", nullable = false)
    private String sender;

    private LocalDate date;
    private String info;
    public Message(Long id, String receiver, String sender, LocalDate date, String info) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.date = date;
        this.info = info;
    }


    public Message(Long id, String receiver, String sender, String info) {
        this.id = id;
        this.receiver = receiver;
        this.sender = sender;
        this.date = LocalDate.now();
    }

    public Message() {

    }

    public Message(String info, LocalDate date, String sender, String receiver) {
        this.info = info;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }


    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
