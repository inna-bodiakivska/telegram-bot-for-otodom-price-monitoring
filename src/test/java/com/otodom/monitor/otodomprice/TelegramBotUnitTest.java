package com.otodom.monitor.otodomprice;

import com.otodom.monitor.otodomprice.bot.Command;
import com.otodom.monitor.otodomprice.bot.TelegramBot;
import com.otodom.monitor.otodomprice.service.PropertyService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class TelegramBotUnitTest {

    private static final Long CHAT_ID = 123L;
    private static final String URL = "https://www.otodom.pl/example-listing";

    private final PropertyService propertyService = Mockito.mock(PropertyService.class);

    private TelegramBot bot = new TelegramBot(propertyService);

    @Test
    public void onUpdateReceived_startCommand() {
        Update update = mockUpdate(Command.START.getValue());

        bot.onUpdateReceived(update);

        assertDoesNotThrow(() -> bot.onUpdateReceived(update));
        verifyNoInteractions(propertyService);
    }

    @Test
    public void onUpdateReceived_addLinkCommand() {
        Update update = mockUpdate(URL);

        bot.onUpdateReceived(update);

        verify(propertyService).saveProperty(URL,CHAT_ID);
    }

    @Test
    public void onUpdateReceived_invalidCommand() {
        Update update = mockUpdate("/unknown");

        assertDoesNotThrow(() -> bot.onUpdateReceived(update));
        verifyNoInteractions(propertyService);
    }

    @Test
    public void onUpdateReceived_removeLink() {
        Update update = mockUpdate(Command.REMOVE.getValue()+ " " + URL);

        bot.onUpdateReceived(update);

        verify(propertyService).removeProperty(URL, CHAT_ID);
    }

    @Test
    public void onUpdateReceived_removeLink_invalidFormat() {
        Update update = mockUpdate(Command.REMOVE.getValue()+ "-" + URL);

        bot.onUpdateReceived(update);

        verify(propertyService, never()).removeProperty(URL, CHAT_ID);
    }

    @Test
    public void onUpdateReceived_allLinks(){
        Update update  = mockUpdate(Command.ALL.getValue());

        bot.onUpdateReceived(update);

        verify(propertyService).getPropertiesByChatId(CHAT_ID);

    }

    private Update mockUpdate(String text) {
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Mockito.when(update.hasMessage()).thenReturn(true);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getText()).thenReturn(text);
        Mockito.when(message.hasText()).thenReturn(true);
        Mockito.when(message.getChatId()).thenReturn(CHAT_ID);
        return update;
    }
}

