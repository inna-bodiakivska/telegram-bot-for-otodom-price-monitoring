package com.otodom.monitor.otodomprice.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class PriceParser {
    private static final String PRICE_XPATH = "//*[@data-cy='adPageHeaderPrice']";

    public Integer getPrice(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements priceElement = doc.selectXpath(PRICE_XPATH);

            if (priceElement != null) {
                String priceText = priceElement.text().replaceAll("[^0-9]", "");
                log.debug("Price is: {} PLN for '{}'", priceText, url);
                return Integer.parseInt(priceText);
            }
            log.debug("Failed to find the price for '{}'", url);
        } catch (IOException e) {
            log.error("An error occurred while parsing price: ", e);
        }
        return null;
    }
}
