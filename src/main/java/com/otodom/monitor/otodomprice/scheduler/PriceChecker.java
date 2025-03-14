package com.otodom.monitor.otodomprice.scheduler;

import com.otodom.monitor.otodomprice.bot.TelegramBot;
import com.otodom.monitor.otodomprice.dao.PropertyRepository;
import com.otodom.monitor.otodomprice.entity.Property;
import com.otodom.monitor.otodomprice.parser.PriceParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PriceChecker {


    private final PropertyRepository propertyRepository;
    private final PriceParser priceParser;
    private final TelegramBot telegramBot;


    public PriceChecker(PropertyRepository propertyRepository, PriceParser priceParser, TelegramBot telegramBot) {
        this.propertyRepository = propertyRepository;
        this.priceParser = priceParser;
        this.telegramBot = telegramBot;
    }

    @Scheduled(fixedRate = 3600000) // Every Hour
    public void checkPrices() {
        List<Property> properties = propertyRepository.findAll();

        for (Property property : properties) {
            String url = property.getUrl();
            Integer newPrice = priceParser.getPrice(url);

            if (ifPriceHaveChanged(property, newPrice)) {
                property.setLastPrice(newPrice);
                propertyRepository.save(property);

                String message = "The price have changed! New price: " + newPrice + " PLN\n" + url;
                String chatId = property.getChatId().toString();
                telegramBot.sendTextMessage(chatId, message);
                log.info("Notifying '{}' that price have changed to {} for {} ", chatId, newPrice, url);
            }
        }
    }

    private static boolean ifPriceHaveChanged(Property property, Integer newPrice) {
        return newPrice != null && !newPrice.equals(property.getLastPrice());
    }
}
