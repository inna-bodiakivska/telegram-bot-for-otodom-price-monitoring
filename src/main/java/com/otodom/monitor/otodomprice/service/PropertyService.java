package com.otodom.monitor.otodomprice.service;

import com.otodom.monitor.otodomprice.entity.Property;

import java.util.List;

public interface PropertyService {

    public void saveProperty(String url, Long chatId);

    public List<Property> getPropertiesByChatId(Long chatId);

    public boolean removeProperty(String url, Long chatId);
}
