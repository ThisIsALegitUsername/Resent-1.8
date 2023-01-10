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

    protected float zLevelFloat;
    

    public PotionHUD() {
        super("PotionHUD", Category.HUD, 4, 350);
    }

    public int getWidth() {
        return 100;
    }
    
    public int getHeight() {
        return 90;
    }

    public void draw() {
        int offsetX = 21;
        int offsetY = 14;
        int i = 80;
        int i2 = 16;
        Collection<PotionEffect> collection = mc.thePlayer.getActivePotionEffects();
        if (!collection.isEmpty()) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableLighting();
            int l = 33;
            if (collection.size() > 5)
                l = 132 / (collection.size() - 1); 
            for (PotionEffect potioneffect : mc.thePlayer.getActivePotionEffects()) {
                Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                if (potion.hasStatusIcon()) {
                    GuiIngame guiIngame = new GuiIngame(mc);
                    GlStateManager.tryBlendFuncSeparate(775, 769, 1, 0);
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/container/inventory.png"));
                    int i3 = potion.getStatusIconIndex();
                    
                    guiIngame.drawTexturedModalRect(getX() + 21 - 20, getY() + i2 - 20, 0 + i3 % 8 * 18, 198 + i3 / 8 * 18, 18, 18);
                } 
                String s1 = I18n.format(potion.getName(), new Object[0]);
                if (potioneffect.getAmplifier() == 1) {
                    s1 = "I";
                } else if (potioneffect.getAmplifier() == 2) {
                    s1 = "II";
                } else if (potioneffect.getAmplifier() == 3) {
                    s1 = "III";
                } else if (potioneffect.getAmplifier() == 4) {
                    s1 = "IV";
                } else if (potioneffect.getAmplifier() == 5) {
                    s1 = "V";
                } else {
                    s1 = ("" + potioneffect.getAmplifier());
                }

                mc.fontRendererObj.drawString(s1, (getX() + 21), (getY() + i2 - 20), 16777215, true);
                String s2 = Potion.getDurationString(potioneffect);
                mc.fontRendererObj.drawString(s2, (getX() + 21), (getY() + i2 + 10 - 20), 8355711, true);
                i2 += l;
            } 
        } 
        super.draw();
    }

    
}
