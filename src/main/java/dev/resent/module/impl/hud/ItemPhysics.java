package dev.resent.module.impl.hud;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.NumberSetting;

@Module(name = "ItemPhysics", category = Category.MISC, hasSetting = true, description = "Give items physics!")
public class ItemPhysics extends Mod{
    public static NumberSetting speed = new NumberSetting("Speed", "", 2, 1, 8, 1, 1);

    public ItemPhysics(){
        addSetting(speed);
    }
    
}
