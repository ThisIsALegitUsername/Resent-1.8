package dev.resent.module.impl.hud;

import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PotCounter extends RenderModule {

    public PotCounter() {
        super("PotCounter", Category.HUD, 4, 74, true);
    }

    public int potinv = 0;

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("[" + potinv + " Pots]") + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    @Override
    public void draw() {
        ItemStack potion = new ItemStack(Items.potionitem, 1, 16421);

        potinv = 0;
        for (int i = 0; i < mc.thePlayer.inventory.getSizeInventory(); i++) {
            if (mc.thePlayer.inventory.getStackInSlot(i) != null && ItemStack.areItemStacksEqual(mc.thePlayer.inventory.getStackInSlot(i), potion)) {
                potinv++;
            }
        }

        drawString("[" + potinv + " Pots]", this.x + 2, this.y + 2, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
    }
}
