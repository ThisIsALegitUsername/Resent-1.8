package dev.resent.module.impl.misc;

import dev.resent.Resent;
import dev.resent.event.impl.EventUpdate;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;

public class AutoRespawn extends Mod{
    public AutoRespawn() {
        super("AutoRespawn", Category.MISC);
        Resent.INSTANCE.events().subscribe(EventUpdate.class, event -> {
            if(this.isEnabled()){
                if (mc.thePlayer.isDead) {
                    mc.thePlayer.respawnPlayer();
                }
            }
        });
    }

}