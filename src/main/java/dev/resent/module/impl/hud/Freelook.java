package dev.resent.module.impl.hud;

import dev.resent.Resent;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.util.misc.W;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.minecraft.client.Minecraft;

@SuppressWarnings("all")
public class Freelook extends Mod {

    public static float cameraYaw = 0.0F;
    public static float cameraPitch = 0.0F;
    public static int previousePrespective = 0;
    public static boolean perspectiveToggled = false;
    public static boolean returnOnRelease = false;

    public Freelook() {
        super("FreeLook", Category.HUD);
    }

    public void smh(){
            if(W.freelook().isEnabled())
            perspectiveToggled = !perspectiveToggled;

            cameraYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
            cameraPitch = Minecraft.getMinecraft().thePlayer.rotationPitch;

            if (perspectiveToggled && W.freelook().isEnabled()) {
                previousePrespective = Minecraft.getMinecraft().gameSettings.thirdPersonView;
                Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
            } else {
                Minecraft.getMinecraft().gameSettings.thirdPersonView = previousePrespective;
            }

    if (Keyboard.getEventKey() == 6 && Minecraft.getMinecraft().gameSettings.keyBindFunction.pressed) {
        perspectiveToggled = false;
    }
}

    public float getCameraYaw() {
        return perspectiveToggled ? cameraYaw : Minecraft.getMinecraft().thePlayer.rotationYaw;
    }

    public float getCameraPitch() {
        return perspectiveToggled ? cameraPitch : Minecraft.getMinecraft().thePlayer.rotationPitch;
    }

    public boolean overriderMouse() {
        if (Minecraft.getMinecraft().inGameHasFocus) {
            if (!perspectiveToggled)
                return true;
            Minecraft.getMinecraft().mouseHelper.mouseXYChange();
            float f1 = Minecraft.getMinecraft().gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8.0F;
            float f3 = Minecraft.getMinecraft().mouseHelper.deltaX * f2;
            float f4 = Minecraft.getMinecraft().mouseHelper.deltaY * f2;
            cameraYaw += f3 * 0.15F;
            cameraPitch += f4 * 0.15F;
            if (cameraPitch > 90.0F)
                cameraPitch = -90.0F;
            if (cameraPitch < -90.0F)
                cameraPitch = 90.0F;
        }
        return false;
    }
}
