package dev.resent.module.impl.hud;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.ui.Theme;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S19PacketEntityStatus;

@RenderModule(name = "ComboCounter", category = Category.HUD, x = 4, y = 14)
public class ComboCounter extends RenderMod {

    public static boolean attacked = false;
    public static int combo = 0;

    public void onAttack(Entity e) {
        if (this.isEnabled()) {
            attacked = true;
        }
    }

    public void onEntityHit(S19PacketEntityStatus event) {
        if (this.isEnabled() && attacked && event.logicOpcode == 2) {
            combo++;
            attacked = false;
        }
    }

    public int getWidth() { return Minecraft.getMinecraft().fontRendererObj.getStringWidth(getText()) + 4; }
    public int getHeight() { return Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 4; }

    private String getText(){
        return "["+combo+" Combo]";
    }

    @Override
    public void draw() {
        if(Minecraft.getMinecraft().thePlayer.hurtTime > 3 && this.enabled){
            combo = 0;
        }
        Minecraft.getMinecraft().fontRendererObj.drawString("["+combo+" Combo]", this.x + 2, this.y + 2, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
    }
}
