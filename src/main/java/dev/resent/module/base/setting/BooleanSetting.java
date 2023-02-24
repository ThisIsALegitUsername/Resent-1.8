package dev.resent.module.base.setting;

public class BooleanSetting extends Setting {

    public boolean value;

    public BooleanSetting(String name, String description, boolean value) {
        super(name, description);
        this.value = value;
        this.gameSetting = false;
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public void toggle() {
        this.value = !this.value;
    }
}
