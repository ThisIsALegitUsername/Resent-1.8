package dev.resent.module.base;

public enum Category {
    HUD("Hud"),
    MOVEMENT("Movement"),
    MISC("Misc");

    public final String name;
    Category(String name){
        this.name = name;
    }
}
