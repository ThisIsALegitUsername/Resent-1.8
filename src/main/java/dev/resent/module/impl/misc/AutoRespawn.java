package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.Mod;

@Module(name = "AutoRespawn", category = Category.MISC)
public class AutoRespawn extends Mod {

    public void onTick() {
        if (this.isEnabled()) {
            if (mc.thePlayer.isDead) {
                mc.thePlayer.respawnPlayer();
            }
        }
    }
    
}
