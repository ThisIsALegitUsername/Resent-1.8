package dev.resent.module.impl.hud;

import dev.resent.event.impl.Event;
import dev.resent.event.impl.EventAttack;
import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import net.minecraft.network.play.server.S19PacketEntityStatus;

public class ComboCounter extends RenderModule {

    public static boolean attacked = false;
    public static int combo = 0;
    public static BooleanSetting tshadow = new BooleanSetting("Text shadow", "", true);

    public ComboCounter() {
        super("ComboCounter", Category.HUD, 4, 24, true);
        addSetting(tshadow);
    }

    public void onEvent(Event e) {
        if (e instanceof EventAttack && isEnabled()) {
            attacked = true;
        }
    }

    public void onEntityHit(S19PacketEntityStatus event) {
        if (this.isEnabled() && attacked && event.logicOpcode == 2) {
            combo++;
            attacked = false;
        }
    }

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth(getText()) + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    private String getText(){
        return "["+combo+" Combo]";
    }

    @Override
    public void draw() {
        mc.fontRendererObj.drawString(getText(), this.x + 2, this.y + 2, Theme.getFontColor(Theme.getId()), tshadow.getValue());
    }
}
