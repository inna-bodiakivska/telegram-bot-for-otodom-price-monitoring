package com.otodom.monitor.otodomprice.bot;

import com.otodom.monitor.otodomprice.entity.Property;
import com.otodom.monitor.otodomprice.service.PropertyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.stream.Collectors;

import static com.otodom.monitor.otodomprice.bot.Command.getCommand;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

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
        if (!isMessageAdded(update)) {
            return;
        }

        String chatId = update.getMessage().getChatId().toString();
        String text = update.getMessage().getText();
        log.info("For chat: '{}' message was added '{}' ", chatId, text);
        Command command = getCommand(text);
        switch (command) {
            case START:
                handelStart(chatId);
                break;
            case LINK:
                handelAddingNewLinkForMonitoring(chatId, text);
                break;
            case ALL:
                handelRetrievingAllMonitoredLinks(chatId);
                break;
            case REMOVE:
                handleRemoveCommand(chatId, text);
                break;
            default:
                handleIncorrectCommand(chatId, text);
                break;
        }
    }

    private void handelStart(String chatId) {
        log.info("Started chat: '{}' ", chatId);
        sendTextMessage(chatId, "Hello! Please send me the link to otodom.pl for price monitoring.");
    }

    private void handelAddingNewLinkForMonitoring(String chatId, String text) {
        log.info("In chat: '{}' otodom.pl link was added '{}' ", chatId, text);
        propertyService.saveProperty(text, Long.parseLong(chatId));
        sendTextMessage(chatId, "Your request was accepted. Started monitoring the price for you.");
    }

    private void handleIncorrectCommand(String chatId, String text) {
        log.info("In chat: '{}' incorrect text was added '{}' ", chatId, text);
        sendTextMessage(chatId, "Please provide the link to otodom.pl for price monitoring or /all for retrieving a list of currently monitored links.");
    }

    private boolean isMessageAdded(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    public void sendTextMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("An error occurred while sending a message: ", e);
        }
    }

    private void handelRetrievingAllMonitoredLinks(String chatId) {
        log.info("Retrieving list of all monitored links for: '{}' ", chatId);
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

    private void handleRemoveCommand(String chatId, String text) {
        String[] parts = text.split(" ");
        if (parts.length < 2) {
            sendTextMessage(chatId, "Please use following format: /remove [URL]");
            return;
        }

        String url = parts[1];
        boolean removed = propertyService.removeProperty(url, Long.parseLong(chatId));
        sendTextMessage(chatId, (removed ? "Successfully removed: " : "Failed to remove: ") + url);

    }

}

