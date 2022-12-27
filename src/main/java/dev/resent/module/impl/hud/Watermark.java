package dev.resent.module.impl.hud;

import dev.resent.Resent;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.util.render.Color;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.gui.FontRenderer;

public class Watermark extends RenderModule{

    public FontRenderer fr;

    public Watermark() {
        super("Watermark", Category.HUD, 300, 4);
    }

    @Override
    public void draw() {

        fr = mc.fontRendererObj;
        this.setHeight(fr.FONT_HEIGHT*2 + 4);
        this.setWidth(fr.getStringWidth(Resent.NAME + " client 3.2 ")*2);

        GlStateManager.pushMatrix();
        GlStateManager.translate(this.x + 1, this.y + 1, 0);
        GlStateManager.translate(-(this.x + 1), -(this.y + 1), 0);
        GlStateManager.scale(2f, 2f, 2f);
        int i = fr.drawString(Resent.NAME + " client", (this.x+1)/2, (this.y+1)/2, Color.RED.getRGB(), true);
        GlStateManager.scale(0.5f, 0.5f, 0.5f);
        fr.drawString(Resent.VERSION + "", (i*2), this.y+(fr.FONT_HEIGHT*2-7), -1, true);
        GlStateManager.popMatrix();

    }
    
}
