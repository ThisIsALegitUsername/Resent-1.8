package dev.resent.module.impl.hud;

import java.util.Collection;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.resources.I18n;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("all")
public class PotionHUD extends RenderModule{

    int i2 = 16;

    public PotionHUD() {
        super("PotionHUD", Category.HUD, 4, 350);
    }

    public int getWidth() {
        return 100;
    }
    
    public int getHeight() {
        return i2+10;
    }

    public void draw() {
        Collection<PotionEffect> collection = mc.thePlayer.getActivePotionEffects();
        if (!collection.isEmpty()) {
            int l = 33;
            if (collection.size() > 5)
                l = 132 / (collection.size() - 1); 
            for (PotionEffect potioneffect : mc.thePlayer.getActivePotionEffects()) {

                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	            GlStateManager.disableLighting();
			    GlStateManager.enableAlpha();

                if (potion.hasStatusIcon()) {
                    GuiIngame guiIngame = new GuiIngame(mc);
                    mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                    int i3 = potion.getStatusIconIndex();
                    //GlStateManager.tryBlendFuncSeparate(1, 1, 1, 0);
                    guiIngame.drawTexturedModalRect(getX() + 1, getY() + i2 - 14, 0 + i3 % 8 * 18, 198 + i3 / 8 * 18, 18, 18);
                } 

                String s1 = I18n.format(potion.getName(), new Object[0]);
                if (potioneffect.getAmplifier() == 1) {
                    s1 = s1 + I18n.format("enchantment.level.2", new Object[0]);
                } else if (potioneffect.getAmplifier() == 2) {
                    s1 = s1 + I18n.format("enchantment.level.3", new Object[0]);
                } else if (potioneffect.getAmplifier() == 3) {
                    s1 = s1 + I18n.format("enchantment.level.4", new Object[0]);
                } 

                mc.fontRendererObj.drawString(s1, (getX() + 21), (getY() + i2 - 14), -1, true);
                String s2 = Potion.getDurationString(potioneffect);
                mc.fontRendererObj.drawString(s2, (getX() + 21), (getY() + i2 + 10 - 14), -1, true);
                i2 += l;
            } 
        } 
    }

    
}
