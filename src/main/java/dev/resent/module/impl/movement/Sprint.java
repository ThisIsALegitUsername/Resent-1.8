package dev.resent.module.impl.movement;

import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import net.minecraft.client.gui.FontRenderer;

public class Sprint extends RenderModule {

    public BooleanSetting drawn = new BooleanSetting("Text Drawn", "", true);
    public FontRenderer fr;

    public Sprint() {
        super("ToggleSprint", Category.MOVEMENT, 4, 94, true);
        addSetting(drawn);
    }

    public int lastKeyHeldTicks = 0;
    public int keyHeldTicks = 0;
    public boolean toggled = false;
    String text = "";

    @Override
    public int getWidth() {
        return fr.getStringWidth(text)+2;
    }

    @Override
    public void draw() {
        this.fr = mc.fontRendererObj;

        if (mc.thePlayer.capabilities.isFlying) {
            text = "               [Flying]";
        }

        if (mc.gameSettings.keyBindSprint.isKeyDown()) {
            keyHeldTicks += 1;
            text = "[Sprinting (Key Held)] ";
        }
        
        if (!mc.gameSettings.keyBindSprint.isKeyDown()) {
            keyHeldTicks = 0;
        }

        if (keyHeldTicks > 0) {
            toggled = !toggled;
        }

        if (toggled) {
            if (!mc.thePlayer.isUsingItem()) mc.thePlayer.setSprinting(true);
            text = "[Sprinting (Toggled)]";
        }

        if (drawn.getValue())
        fr.drawStringWithShadow(text, x + 2, y + 2, Theme.getFontColor(Theme.getId()));
    }

    @Override
    public int getHeight() {
        return fr.FONT_HEIGHT + 2;
    }
}
