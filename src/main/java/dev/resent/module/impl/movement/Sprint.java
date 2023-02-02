package dev.resent.module.impl.movement;

import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.module.setting.BooleanSetting;

public class Sprint extends RenderMod {

    public BooleanSetting drawn = new BooleanSetting("Text Drawn", "", true);

    public Sprint() {
        super("ToggleSprint", Category.MOVEMENT, 4, 94, true);
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
            if (mc.gameSettings.keyBindForward.pressed && !mc.thePlayer.isUsingItem()) 
            mc.thePlayer.setSprinting(true);
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
        if (drawn.getValue())
        drawString(getText(), x + 2, y + 2, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
    }

    @Override
    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 2;
    }
}
