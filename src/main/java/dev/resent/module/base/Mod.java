package dev.resent.module.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.resent.module.Theme;
import dev.resent.module.setting.Setting;
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
        if(Theme.getRounded()){
            RenderUtils.drawRoundedRect(left, top, right, bottom, 4, color);
        }else {
            Gui.drawRect(left, top, right, bottom, color);
        }
    }

    protected int drawString(String text, int x, int y, int color, boolean idk){
        if(color == 6942069){
            RenderUtils.drawChromaString(text, x, y, idk);
        }else {
            Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color, idk);
        }
        
        return x;
    }

    public void setEnabled(boolean state) {
        this.enabled = state;
        if (this.enabled) onEnable(); else onDisable();
    }



    public boolean isEnabled() { return enabled; }
    public String getName() { return name; }

}
