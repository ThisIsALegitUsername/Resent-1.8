package dev.resent.module.impl.hud;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.ModeSetting;
import dev.resent.module.base.setting.NumberSetting;

@Module(name = "ClickGUI", category = Category.HUD, hasSetting = true)
public class ClickGui extends Mod{

    public BooleanSetting scroll = new BooleanSetting("Smooth scroll", "", false);
    public ModeSetting guiTheme = new ModeSetting("Gui theme", "New", "New", "Classic revised");
    public NumberSetting test = new NumberSetting("Test, ignore!", "", 50, 1, 255, 5, 5);

    public ClickGui(){
        addSetting(scroll, guiTheme, test);
    }
    
}
