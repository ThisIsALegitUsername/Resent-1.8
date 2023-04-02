package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

@Module(name = "ToggleSprint", category = Category.MISC)
public class Sprint extends Mod {

    public static void onUpdate(){
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), true);    
    }

    public void onDisable() {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
	}

}
