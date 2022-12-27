package dev.resent.module.impl.hud;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import net.minecraft.src.ItemPotion;
import net.minecraft.src.ItemStack;

public class PotCounter extends RenderModule{
    public PotCounter(){
        super("PotCounter", Category.HUD, 4, 64, true);
		addSetting(tshadow);
    }

    public int potinv = 0;
	public BooleanSetting tshadow = new BooleanSetting("Text Shadow", "", true);
    
	public int getWidth(){ return mc.fontRenderer.getStringWidth("[" + potinv + " Pots]")+4;}
	public int getHeight(){ return mc.fontRenderer.FONT_HEIGHT+4; }
    @Override
    public void draw(){

        ItemStack potion = new ItemStack(ItemPotion.potion, 1, 16421);

		potinv = 0;
		for(int i = 0; i < mc.thePlayer.inventory.getSizeInventory(); i++) {
			if(mc.thePlayer.inventory.getStackInSlot(i) != null && ItemStack.areItemStacksEqual(mc.thePlayer.inventory.getStackInSlot(i), potion)) {
				potinv++;
			}
		}

		mc.fontRenderer.drawString("[" + potinv + " Pots]", this.x+2, this.y+2, -1, tshadow.getValue());
    }
}
