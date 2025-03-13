package com.otodom.monitor.otodomprice;

import com.otodom.monitor.otodomprice.bot.TelegramBot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@EnableScheduling
public class OtodompriceApplication {

	private static final Logger LOG = LoggerFactory.getLogger(OtodompriceApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(OtodompriceApplication.class, args);

	}

	@Bean
	public TelegramBotsApi telegramBotsApi(TelegramBot telegramBot) throws Exception {
		TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
		botsApi.registerBot(telegramBot);
		LOG.info("Telegram Bot Registered Successfully.");
		return botsApi;
	}

}
