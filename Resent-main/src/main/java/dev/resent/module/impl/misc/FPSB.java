package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;

public class FPSB extends Mod{

    public boolean yes = false;

    public FPSB(){
        super("Fps Boost", Category.MISC);
    }

    @Override
    public void onEnable(){
        yes = true;
    }

    @Override
    public void onDisable(){
        yes = false;
    }
}
