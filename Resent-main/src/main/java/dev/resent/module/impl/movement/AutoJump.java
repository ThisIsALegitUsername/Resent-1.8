package dev.resent.module.impl.movement;

import dev.resent.Resent;
import dev.resent.event.impl.EventUpdate;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;

public class AutoJump extends Mod{
    public AutoJump(){
        super("AutoJump", Category.MOVEMENT);
        Resent.INSTANCE.events().subscribe(EventUpdate.class, event -> {
            if(this.isEnabled()){
                mc.gameSettings.keyBindJump.pressed = true;
            }
        });

    }
    
}
