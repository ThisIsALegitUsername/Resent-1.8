package dev.resent.module.impl.hud;

import java.util.ArrayList;
import java.util.List;

import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import dev.resent.util.misc.FuncUtils;
import net.lax1dude.eaglercraft.v1_8.Keyboard;

public class CPS extends RenderModule {

    private List<Long> clicksLMB = new ArrayList<Long>();
    private List<Long> clicksRMB = new ArrayList<Long>();
    private boolean wasPressedLMB;
    private long lastPressedLMB;
    private boolean wasPressedRMB;
    private long lastPressedRMB;
    
    public CPS() {
        super("CPS", Category.HUD, 4, 84, true);
        addSetting(tshadow);
    }

    public BooleanSetting tshadow = new BooleanSetting("Text shadow", "", true);

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("[00 | 00]") + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    @Override
    public void draw() {

        boolean pressedLMB = Keyboard.isKeyDown(mc.gameSettings.keyBindAttack.getKeyCode());
        if(pressedLMB != this.wasPressedLMB) {
            this.lastPressedLMB = System.currentTimeMillis();
            this.wasPressedLMB = pressedLMB;
            if(pressedLMB) {
                this.clicksLMB.add(this.lastPressedLMB);
            }
        }
        
        boolean pressedRMB = Keyboard.isKeyDown(mc.gameSettings.keyBindUseItem.getKeyCode());
        
        if(pressedRMB != this.wasPressedRMB) {
            this.lastPressedRMB = System.currentTimeMillis();
            this.wasPressedRMB = pressedRMB;
            if(pressedRMB){
                this.clicksRMB.add(this.lastPressedRMB);
            }
        }

        final long time = System.currentTimeMillis();
        FuncUtils.removeIf(clicksRMB, aLong -> aLong + 1000 < time);
        FuncUtils.removeIf(clicksLMB, aLong -> aLong + 1000 < time);
        mc.fontRendererObj.drawString(getText(), this.x + 2, this.y + 2, Theme.getFontColor(Theme.getId()), tshadow.getValue());
    }

    public String getText(){
        return "[ " + getLMB() + " | " + getRMB() + " ]";
    }

    public int getLMB() {
        return this.clicksLMB.size();
    }
    
    public int getRMB() {
        return this.clicksRMB.size();
    }

}
