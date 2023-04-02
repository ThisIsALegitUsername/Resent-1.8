package dev.resent.module.impl.misc;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Mod.Category;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import dev.resent.module.base.RenderMod;

@RenderModule(name = "ToggleSprint", category = Category.MISC, x = 4, y = 122, hasSetting = true)
public class Sprint extends RenderMod {

    public static void onUpdate(){
        KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindSprint.getKeyCode(), true);    
    }

    public void onDisable() {
		KeyBinding.setKeyBindState(mc.gameSettings.keyBindSprint.getKeyCode(), false);
	}

}
