package com.otodom.monitor.otodomprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OtodompriceApplication {


    public static void main(String[] args) {
        SpringApplication.run(OtodompriceApplication.class, args);
    }

}
