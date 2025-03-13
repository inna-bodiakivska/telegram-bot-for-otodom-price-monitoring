package com.otodom.monitor.otodomprice.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PriceParser {
    private static final Logger LOG = LoggerFactory.getLogger(PriceParser.class);
    private static final String PRICE_XPATH = "//*[@data-cy='adPageHeaderPrice']";

    public Integer getPrice(String url) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements priceElement = doc.selectXpath(PRICE_XPATH);

            if (priceElement != null) {
                String priceText = priceElement.text().replaceAll("[^0-9]", "");
                LOG.debug("Price is: {} PLN for '{}'", priceText, url);
                return Integer.parseInt(priceText);
            }
            LOG.debug("Failed to find the price for '{}'", url);
        } catch (IOException e) {
            LOG.error("An error occurred while parsing price: ", e);
        }
        return null;
    }
}
