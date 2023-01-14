package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;

public class AutoRespawn extends Mod {

    public AutoRespawn() {
        super("AutoRespawn", Category.MISC);
    }

    public void onTick() {
        if (this.isEnabled()) {
            if (mc.thePlayer.isDead) {
                mc.thePlayer.respawnPlayer();
            }
        }
    }
}
