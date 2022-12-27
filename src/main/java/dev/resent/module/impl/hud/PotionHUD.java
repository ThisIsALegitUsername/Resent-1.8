package dev.resent.module.impl.hud;

import java.util.Collection;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import net.lax1dude.eaglercraft.TextureLocation;
import net.lax1dude.eaglercraft.adapter.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.StatCollector;

@SuppressWarnings("all")
public class PotionHUD extends RenderModule{

    public Minecraft mc = Minecraft.getMinecraft();
    public FontRenderer fr;
    public ScaledResolution sr;

    public PotionHUD() {
        super("PotionHUD", Category.HUD, 4, 350);
    }

    @Override
    public void renderLayout(int mouseX, int mouseY) {
        fr = mc.fontRendererObj;
        super.renderLayout(mouseX, mouseY);
        fr.drawStringWithShadow("PotionHUD", getX() + getWidth() / 2 - ((getWidth() - 10) / 2),
                getY() + (getHeight() / 2 - fr.FONT_HEIGHT / 2), -1);
    }

    @Override
    public int getHeight() {
        fr = mc.fontRendererObj;
        return fr.FONT_HEIGHT + 3;
    }



    @Override
    public void draw(){
        this.setHeight(20);
    	this.setWidth(Minecraft.getMinecraft().fontRenderer.getStringWidth("Resistance VII") + 2);
        sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        Collection<PotionEffect> effects = mc.thePlayer.getActivePotionEffects();
        int potcount = 0;

        for (PotionEffect e : effects) {
            if (!effects.isEmpty()) {
                String eName = StatCollector.translateToLocal(e.getEffectName());
                String duration = Potion.getDurationString(e);
                int amp = e.getAmplifier() + 1;
                String ampString = ("" + amp);

                if (amp == 1) {
                    ampString = "I";
                } else if (amp == 2) {
                    ampString = "II";
                } else if (amp == 3) {
                    ampString = "III";
                } else if (amp == 4) {
                    ampString = "IV";
                } else if (amp == 5) {
                    ampString = "V";
                } else {
                    ampString = ("" + amp);
                }

                Potion var8 = Potion.potionTypes[e.getPotionID()];
                /*if (var8.hasStatusIcon()) {
                    int var9 = var8.getStatusIconIndex();
                    guiScreen.drawTexturedModalRect(this.x, this.y+ 4, 0 + var9 % 8 * 18, 198 + var9 / 8 * 18, 18, 18);
                }*/

                fr = Minecraft.getMinecraft().fontRenderer;
                String toDraw = "§4" + eName + "§r §a" + ampString + " §9" + duration;
                fr.drawStringWithShadow(toDraw, this.x + 2, this.y + (potcount * 10) -5, -1);
                this.setHeight((potcount * 10) + 10);
                potcount++;
            }
        }
    }
    
}
