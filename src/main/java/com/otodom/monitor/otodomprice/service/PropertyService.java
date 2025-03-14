package com.otodom.monitor.otodomprice.service;

import com.otodom.monitor.otodomprice.entity.Property;

import java.util.List;

public interface PropertyService {

    void saveProperty(String url, Long chatId);

    List<Property> getPropertiesByChatId(Long chatId);

    boolean removeProperty(String url, Long chatId);
}
