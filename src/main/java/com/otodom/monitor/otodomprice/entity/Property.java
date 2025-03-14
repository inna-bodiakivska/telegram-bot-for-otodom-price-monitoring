package com.otodom.monitor.otodomprice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Property {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private Integer lastPrice;

    @Getter
    @Setter
    private Long chatId;

    public Property() {
    }

    public Property(String url, Integer lastPrice, Long chatId) {
        this.url = url;
        this.lastPrice = lastPrice;
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
