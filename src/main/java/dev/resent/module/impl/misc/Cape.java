package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.cape.CapeUi;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.CustomRectSettingDraw;
import dev.resent.module.setting.ModeSetting;

@Module(name = "Cape", category = Category.MISC, hasSetting = true)
public class Cape extends Mod{
    public CustomRectSettingDraw open = new CustomRectSettingDraw("Choose cape", "Select which cape you want to use"){
        @Override
        public void onChange(){
            mc.displayGuiScreen(new CapeUi());
        }
    };
    public BooleanSetting ratio = new BooleanSetting("qwiehgioqwhe", "", true);
    public ModeSetting amogus = new ModeSetting("amogus", "", "asdfasdf", "asdfasdf 1");

    public Cape(){
        addSetting(open, ratio, amogus);
    }
}
