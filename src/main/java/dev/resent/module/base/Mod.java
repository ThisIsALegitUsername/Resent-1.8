package dev.resent.module.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.resent.annotation.Module;
import dev.resent.module.setting.Setting;
import dev.resent.ui.Theme;
import dev.resent.util.render.RenderUtils;
import net.minecraft.client.Minecraft;

public abstract class Mod {

    public Minecraft mc = Minecraft.getMinecraft();
    public int keyCode;

    public String name;
    public Category category;
    public boolean enabled = false;
    public boolean hasSetting;

    public List<Setting> settings = new ArrayList<>();

    public Mod(){
        Module modInfo;
        if(getClass().isAnnotationPresent(Module.class)){
            modInfo = getClass().getAnnotation(Module.class);
            this.name = modInfo.name();
            this.category = modInfo.category();
            this.hasSetting = modInfo.hasSetting();
        }
    }

    public void addSetting(final Setting... settings) {
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

    protected void drawRect(final int left, final int top, final int right, final int bottom, final int color){
        RenderUtils.drawRoundedRect(left, top, right, bottom, 4, color, Theme.getRounded());
    }

    protected int drawString(final String text, final int x, final int y, final int color, final boolean idk){
        if(color == 6942069){
            RenderUtils.drawChromaString(text, x, y, idk);
        }else {
            Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, color, idk);
        }
        
        return x;
    }

    public void setEnabled(final boolean state) {
        this.enabled = state;
        if (this.enabled) onEnable(); else onDisable();
    }

    public enum Category {
        HUD("Hud"),
        MOVEMENT("Movement"),
        MISC("Misc");
    
        public final String name;
    
        Category(final String name) {
            this.name = name;
        }
    }

    public boolean isEnabled() { return enabled; }
    public String getName() { return name; }

}
