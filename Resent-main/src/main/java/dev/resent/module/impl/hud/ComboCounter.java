package dev.resent.module.impl.hud;

import dev.resent.Resent;
import dev.resent.event.impl.EntityStatusEvent;
import dev.resent.event.impl.EventAttack;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;

public class ComboCounter extends RenderModule {

    private long lastAttack;
    public static boolean attacked = false;
    public int combo = 0;
    public BooleanSetting tshadow = new BooleanSetting("Text Shadow", "", true);
    
    public ComboCounter() {
        super("ComboCounter", Category.HUD, 4, 4, true);
        addSetting(tshadow);
        Resent.INSTANCE.events().subscribe(EntityStatusEvent.class, event -> {
            if(this.isEnabled() && attacked && event.status == 2){
                combo++;
                attacked = false;
            }
        }); 

        Resent.INSTANCE.events().subscribe(EventAttack.class, event -> {
            if(this.isEnabled()){
                attacked = true;
                lastAttack = System.nanoTime();
            }
        });
    }

    public int getWidth(){ return mc.fontRenderer.getStringWidth("[0 Combo]") + 4; }
    public int getHeight(){ return mc.fontRenderer.FONT_HEIGHT + 4; }

    @Override
    public void draw() {
        if(mc.thePlayer.hurtTime > 3 || System.nanoTime() - lastAttack >= 3.0E9 && this.enabled){
            combo = 0;
        }

        mc.fontRenderer.drawString(combo + " Combo", this.x + 2, this.y + 2, -1, tshadow.getValue());
    }

}
