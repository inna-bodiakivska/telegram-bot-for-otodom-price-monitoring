package com.otodom.monitor.otodomprice.dao;
import com.otodom.monitor.otodomprice.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByChatId(Long chatId);
    Property findByUrl(String url);
}
