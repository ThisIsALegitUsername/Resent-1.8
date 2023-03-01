package dev.resent.module.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.resent.annotation.Module;
import dev.resent.module.base.setting.Setting;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.Theme;
import dev.resent.visual.ui.animation.SimpleAnimation;
import net.minecraft.client.Minecraft;

public abstract class Mod {

    protected Minecraft mc = Minecraft.getMinecraft();
    private String name;
    private Category category;
    private boolean enabled;
    private boolean hasSetting;
    public boolean expanded;
	public int index;
	public SimpleAnimation toggleAnimation = new SimpleAnimation(0);

    public List<Setting> settings = new ArrayList<>();

    public Mod() {
        Module modInfo;
        if (getClass().isAnnotationPresent(Module.class)) {
            modInfo = getClass().getAnnotation(Module.class);
            this.setName(modInfo.name());
            this.setCategory(modInfo.category());
            this.setHasSetting(modInfo.hasSetting());
        }
    }

    public void addSetting(final Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public void onEnable() {}

    public void onDisable() {}

    public void toggle() {
        this.enabled = !this.enabled;
        onChange();
    }

    private void onChange() {
        if (enabled) onEnable(); else onDisable();
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        onChange();
    }

    protected void drawRect(final int left, final int top, final int right, final int bottom, final int color) {
        RenderUtils.drawRoundedRect(left, top, right, bottom, 4, color);
    }

    protected int drawString(final String text, final int x, final int y, final int color, final boolean idk) {
        if (Theme.getFontColor(Theme.getFontId()) == 6942069) {
            RenderUtils.drawChromaString(text, x, y, idk);
        } else {
            Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
        }

        return x;
    }

    public enum Category {
        HUD("Hud"),
        MISC("Misc");

        public final String name;
        public int i;

        Category(final String name) {
            this.name = name;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isHasSetting() {
        return hasSetting;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setHasSetting(boolean hasSetting) {
        this.hasSetting = hasSetting;
    }
}
