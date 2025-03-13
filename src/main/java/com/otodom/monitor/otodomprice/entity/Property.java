package com.otodom.monitor.otodomprice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private Integer lastPrice;
    private Long chatId;

    public Property() {
    }

    public Property(String url, Integer lastPrice, Long chatId) {
        this.url = url;
        this.lastPrice = lastPrice;
        this.chatId = chatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Integer lastPrice) {
        this.lastPrice = lastPrice;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", lastPrice=" + lastPrice +
                ", chatId=" + chatId +
                '}';
    }


}
