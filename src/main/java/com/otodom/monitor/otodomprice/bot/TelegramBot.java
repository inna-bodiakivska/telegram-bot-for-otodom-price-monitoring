package com.otodom.monitor.otodomprice.bot;

import com.otodom.monitor.otodomprice.entity.Property;
import com.otodom.monitor.otodomprice.service.PropertyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger LOG = LoggerFactory.getLogger(TelegramBot.class);

    private final PropertyService propertyService;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    public TelegramBot(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!isTextAdded(update)) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        LOG.info("For chat: '{}' text is added '{}' ", chatId, text);

        if (text.startsWith("/start")) {
            LOG.info("Started chat: '{}' ", chatId);
            sendTextMessage(chatId, "Hello! Please send me the link to Otodom.pl for price monitoring.");
        } else if (text.startsWith("https://www.otodom.pl/")) {
            LOG.info("In chat: '{}' otodom.pl link was added '{}' ", chatId, text);
            propertyService.saveProperty(text, Long.parseLong(chatId));
            sendTextMessage(chatId, "Your request was accepted. Started monitoring the price for you.");
        } else if (text.startsWith("/all")) {
            LOG.info("Retrieving list of all monitored links for: '{}' ", chatId);
            sendAllTrackedLinks(chatId);
        } else {
            LOG.info("In chat: '{}' incorrect text was added '{}' ", chatId, text);
            sendTextMessage(chatId, "Please provide the link to otodom.pl for price monitoring or /all for retrieving a list of currently monitored links.");
        }


    }

    private static boolean isTextAdded(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    public void sendTextMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendAllTrackedLinks(String chatId) {
        List<Property> properties = propertyService.getPropertiesByChatId(Long.parseLong(chatId));
        if (properties.isEmpty()) {
            sendTextMessage(chatId, "Currently you have no link for price monitoring");
            return;
        }

        String message = properties.stream()
                .map(p -> "🔗 " + p.getUrl() + (p.getLastPrice() != null ? " - " + p.getLastPrice() + " PLN" : ""))
                .collect(Collectors.joining("\n"));

        sendTextMessage(chatId, "List of monitored links:\n" + message);
    }
}

