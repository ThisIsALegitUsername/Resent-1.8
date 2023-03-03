package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.BooleanSetting;

@Module(name = "FPS Options", category = Category.MISC, hasSetting = true)
public class FPSOptions extends Mod{

    public BooleanSetting batchRendering = new BooleanSetting("Batch rendering", "", false);

    public FPSOptions(){
        addSetting(batchRendering);
    }
    
}
