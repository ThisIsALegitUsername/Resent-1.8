package dev.resent.module.impl.hud;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import net.minecraft.client.Minecraft;

public class FPS extends RenderModule{

    public Minecraft mc = Minecraft.getMinecraft();

    public FPS(){
        super("FPS", Category.HUD, 4, 24, true);
        addSetting(tshadow);
    }
    
    public BooleanSetting tshadow = new BooleanSetting("Text Shadow", "", true);
    public int getWidth(){ return mc.fontRendererObj.getStringWidth("[FPS: "+ Minecraft.debugFPS + "]") + 4;}
    public int getHeight(){ return mc.fontRendererObj.FONT_HEIGHT+4;}

    @Override
    public void draw(){
        if (mc.thePlayer != null) {
            if(this.isEnabled()){
                mc.fontRendererObj.drawString("[FPS: " + Minecraft.debugFPS + "]", this.x + 2, this.y + 2, -1, tshadow.getValue());
            }
        }
    }

}
