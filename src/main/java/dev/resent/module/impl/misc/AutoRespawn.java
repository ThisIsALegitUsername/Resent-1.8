package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;

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
