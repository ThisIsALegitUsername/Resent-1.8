package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.module.setting.ModeSetting;

public class HotbarAnimation extends Mod{
    public HotbarAnimation(){
        super("Hotbar", Category.MISC, true);
        addSetting(speed);
    }

    public static ModeSetting speed = new ModeSetting("Speed", "", "Slow", "Fast", "Normal");
    
    public int getSpeed(){
        if(speed.getValue() == "Fast")
            return 20;
        if(speed.getValue() == "Slow")
            return 5;
        return 12;
    }
}
