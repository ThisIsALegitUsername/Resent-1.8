package dev.resent.module.impl.hud;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.module.setting.BooleanSetting;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.item.ItemStack;

@RenderModule(name = "ArmorHud", category = Category.HUD, x = 15, y = 4, hasSetting = true)
public class ArmorHud extends RenderMod {

    public ScaledResolution sr;
    public ArmorHud() { addSetting(helm, chestp, leg, boot, item); }

    public static BooleanSetting helm = new BooleanSetting("Helmet", "", true);
    public static BooleanSetting chestp = new BooleanSetting("Chestplate", "", true);
    public static BooleanSetting leg = new BooleanSetting("Leggings", "", true);
    public static BooleanSetting boot = new BooleanSetting("Boots", "", true);
    public static BooleanSetting item = new BooleanSetting("Item", "", true);

    public int getWidth() { return 20; }
    public int getHeight() { return 96; }

    @Override
    public void draw() {
        GlStateManager.enableLighting();
        ItemStack boots = mc.thePlayer.inventory.armorInventory[0];
        ItemStack legs = mc.thePlayer.inventory.armorInventory[1];
        ItemStack chest = mc.thePlayer.inventory.armorInventory[2];
        ItemStack helmet = mc.thePlayer.inventory.armorInventory[3];
        ItemStack hand = mc.thePlayer.inventory.getCurrentItem();
        if (helmet != null && helm.getValue()) {
            ItemStack displayhelmet = helmet.copy();
            displayhelmet.stackSize = 1;
            GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(displayhelmet, this.x + 3, this.y + 2);

            GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, displayhelmet, this.x + 3, this.y + 2, "");
            //16
        }
        if (chest != null && chestp.getValue()) {
            ItemStack displaychest = chest.copy();
            displaychest.stackSize = 1;
            GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(displaychest, this.x + 3, this.y + 18);

            GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, displaychest, this.x + 3, this.y + 18, "");
        }
        if (legs != null && leg.getValue()) {
            ItemStack displaylegs = legs.copy();
            displaylegs.stackSize = 1;
            GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(displaylegs, this.x + 3, this.y + 34);

            GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, displaylegs, this.x + 3, this.y + 34, "");
        }
        if (boots != null && boot.getValue()) {
            ItemStack displayboots = boots.copy();
            displayboots.stackSize = 1;
            GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(displayboots, this.x + 3, this.y + 50);

            GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, displayboots, this.x + 3, this.y + 50, "");
        }
        if (hand != null && item.getValue()) {
            ItemStack displayhand = hand.copy();
            displayhand.stackSize = 1;
            GuiIngame.itemRenderer.renderItemAndEffectIntoGUI(displayhand, this.x + 3, this.y + 66);
            GuiIngame.itemRenderer.renderItemOverlayIntoGUI(mc.fontRendererObj, displayhand, this.x + 3, this.y + 66, "");
        }

        GlStateManager.disableLighting();
    }
}
