package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.CustomRectSettingDraw;
import dev.resent.visual.crosshair.CrosshairUi;

@Module(name = "Crosshair", category = Category.MISC, hasSetting = true)
public class Crosshair extends Mod {

    public CustomRectSettingDraw open = new CustomRectSettingDraw("Choose crosshair", "Select which crosshair you want to use") {
        @Override
        public void onPress() {
            mc.displayGuiScreen(new CrosshairUi());
        }
    };

    public Crosshair() {
        addSetting(open);
    }
}
