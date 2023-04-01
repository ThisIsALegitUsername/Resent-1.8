package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.NumberSetting;

@Module(name = "Particles Mod", category = Category.MISC, hasSetting = true)
public class ParticleMultiplier extends Mod{
    public static BooleanSetting alwaysCrit = new BooleanSetting("Always critical", "", false);
    public static BooleanSetting alwaysSharp = new BooleanSetting("Always sharpness", "", false);
    public static NumberSetting multiplier = new NumberSetting("Multiplier", "", 1, 1, 50, 1, 1);

    public ParticleMultiplier(){
        addSetting(alwaysCrit, alwaysSharp, multiplier);
    }
}
