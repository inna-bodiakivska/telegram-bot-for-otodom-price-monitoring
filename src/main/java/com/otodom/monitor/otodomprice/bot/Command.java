package com.otodom.monitor.otodomprice.bot;

public enum Command {
    START("/start"),
    ALL("/all"),
    LINK("https://www.otodom.pl/"),
    REMOVE("/remove"),
    INVALID("-");
    private String value;

    Command(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Command getCommand(String text) {
        for (Command command : Command.values()) {
            if (text.startsWith(command.getValue())) {
                return command;
            }
        }
        return INVALID;
    }
}
