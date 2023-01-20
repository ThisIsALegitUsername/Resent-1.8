package dev.resent.module.setting;

public class Setting {

    public String name;
    public boolean gameSetting;
    public String description;

    public Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Setting(String name, String description, boolean gameSetting) {
        this.name = name;
        this.description = description;
        this.gameSetting = gameSetting;
    }
}
