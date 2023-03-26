package dev.resent.module.impl.misc;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.module.base.setting.BooleanSetting;

@RenderModule(name = "ToggleSprint", category = Category.MISC, x = 4, y = 122, hasSetting = true)
public class Sprint extends RenderMod {

    public BooleanSetting drawn = new BooleanSetting("Text Drawn", "", true);

    public Sprint() {
        addSetting(drawn);
    }

    public int lastKeyHeldTicks = 0;
    public int keyHeldTicks = 0;
    public boolean toggled = false;
    public boolean clickDebounce = false;

    private String getText() {
        String text = "";
        boolean definitive = false;
        if (mc.thePlayer.capabilities.isFlying) {
            text = "               [Flying]";
        }

        if (mc.gameSettings.keyBindSprint.isKeyDown()) {
            keyHeldTicks += 1;
            definitive = true;
            text = "[Sprinting (Key Held)] ";
        } else if (!mc.gameSettings.keyBindSprint.isKeyDown()) {
            keyHeldTicks = 0;
        }
        if (keyHeldTicks > 0) {
            toggled = !toggled;
        }
        if (toggled) {
            if (mc.gameSettings.keyBindForward.pressed && !mc.thePlayer.isUsingItem()) mc.thePlayer.setSprinting(true);
            text = definitive ? text : "[Sprinting (Toggled)]";
        }

        lastKeyHeldTicks = keyHeldTicks;
        return text;
    }

    @Override
    public int getWidth() {
        return mc.fontRendererObj.getStringWidth(getText());
    }

    @Override
    public void draw() {
        if (drawn.getValue()) drawString(getText(), x + 2, y + 2);
    }

    @Override
    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 2;
    }
}
