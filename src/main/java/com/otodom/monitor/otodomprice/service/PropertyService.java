package com.otodom.monitor.otodomprice.service;

import com.otodom.monitor.otodomprice.dao.PropertyRepository;
import com.otodom.monitor.otodomprice.entity.Property;
import com.otodom.monitor.otodomprice.parser.PriceParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {
    private static final Logger LOG = LoggerFactory.getLogger(PropertyService.class);

    private final PropertyRepository propertyRepository;
    private final PriceParser priceParser;

    public PropertyService(PropertyRepository propertyRepository, PriceParser priceParser) {
        this.propertyRepository = propertyRepository;
        this.priceParser = priceParser;
    }

    public void saveProperty(String url, Long chatId) {
        Property existingProperty = propertyRepository.findByUrl(url);
        if (existingProperty == null) {
            Integer price = priceParser.getPrice(url);
            Property property = new Property(url, price, chatId);
            propertyRepository.save(property);
            LOG.info("Persisted property {}", property);
        } else {
            LOG.debug("Already monitoring price for'{}' of {}", chatId, url);
        }
    }

    public List<Property> getPropertiesByChatId(Long chatId) {
        return propertyRepository.findByChatId(chatId);
    }
}

