package com.otodom.monitor.otodomprice.bot;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum Command {
    START("/start"),
    ALL("/all"),
    LINK("https://www.otodom.pl/"),
    REMOVE("/remove"),
    INVALID("-");

    @Getter
    @Setter
    private String value;

    Command(String value) {
        this.value = value;
    }

    public static Command getCommand(String text) {
           return Arrays.stream(Command.values())
                    .filter(command -> text.startsWith(command.value))
                    .findFirst()
                    .orElse(INVALID);
    }
}
