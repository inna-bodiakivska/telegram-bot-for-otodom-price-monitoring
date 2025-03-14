package com.otodom.monitor.otodomprice.service;

import com.otodom.monitor.otodomprice.dao.PropertyRepository;
import com.otodom.monitor.otodomprice.entity.Property;
import com.otodom.monitor.otodomprice.parser.PriceParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final PriceParser priceParser;

    public PropertyServiceImpl(PropertyRepository propertyRepository, PriceParser priceParser) {
        this.propertyRepository = propertyRepository;
        this.priceParser = priceParser;
    }

    @Transactional
    @Override
    public void saveProperty(String url, Long chatId) {
        Property existingProperty = propertyRepository.findByUrl(url);
        if (existingProperty == null) {
            Integer price = priceParser.getPrice(url);
            Property property = new Property(url, price, chatId);
            propertyRepository.save(property);
            log.info("Persisted property {}", property);
        } else {
            log.debug("Already monitoring price for'{}' of {}", chatId, url);
        }
    }

    @Override
    public List<Property> getPropertiesByChatId(Long chatId) {
        return propertyRepository.findByChatId(chatId);
    }


    @Transactional
    @Override
    public boolean removeProperty(String url, Long chatId) {
        Optional<Property> property = propertyRepository.findByUrlAndChatId(url, chatId);
        if (property.isPresent()) {
            propertyRepository.delete(property.get());
            log.info("Removed property {}", property);
            return true;
        }
        log.debug("Failed to find property for removal chatId: {}, url: {}", chatId, url);
        return false;
    }
}

