package dev.resent.module.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.resent.Resent;
import dev.resent.event.impl.Event;
import dev.resent.module.Theme;
import dev.resent.setting.Setting;
import dev.resent.util.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Mod {

    public Minecraft mc = Minecraft.getMinecraft();
    public int keyCode;

    public String name;
    public Category category;
    public boolean enabled = false;
    public boolean hasSetting;

    public List<Setting> settings = new ArrayList<>();

    public Mod(String name, Category cat) {
        this.name = name;
        this.category = cat;
    }

    public Mod(String name, Category cat, boolean hasSetting) {
        this.name = name;
        this.category = cat;
        this.hasSetting = hasSetting;
    }

    public void addSetting(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public void onEnable() {}
    public void onDisable() {}

    public void toggle() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    protected void drawRect(int left, int top, int right, int bottom, int color){
        switch(Theme.getId()){
            case 1:
                RenderUtils.drawRect(left, top, right, bottom, color);
            case 50:
                Gui.drawRect(left, top, right, bottom, color);
        }
    }

    public void onEvent(Event e) {
        for (int i = 0; i < Resent.INSTANCE.modManager.modules.size(); i++) {
            if (!Resent.INSTANCE.modManager.modules.get(i).isEnabled()) continue;
            Resent.INSTANCE.modManager.modules.get(i).onEvent(e);
        }
    }

    public void setEnabled(boolean state) {
        this.enabled = state;
        if (this.enabled) onEnable(); else onDisable();
    }

    public boolean isEnabled() { return enabled; }
    public String getName() { return name; }

}
