package dev.resent.module.impl.hud;

import dev.resent.Resent;
import dev.resent.event.impl.EventKey;
import dev.resent.event.impl.EventUpdate;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.util.misc.W;
import net.lax1dude.eaglercraft.EaglerAdapter;
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

            cameraYaw = mc.thePlayer.rotationYaw;
            cameraPitch = mc.thePlayer.rotationPitch;

            if (perspectiveToggled && W.freelook().isEnabled()) {
                previousePrespective = mc.gameSettings.thirdPersonView;
                mc.gameSettings.thirdPersonView = 1;
            } else {
                mc.gameSettings.thirdPersonView = previousePrespective;
            }

    if (EaglerAdapter.getEventKey() == 6 && mc.gameSettings.keyBindFunction.pressed) {
        perspectiveToggled = false;
    }
}

    public float getCameraYaw() {
        return perspectiveToggled ? cameraYaw : mc.thePlayer.rotationYaw;
    }

    public float getCameraPitch() {
        return perspectiveToggled ? cameraPitch : mc.thePlayer.rotationPitch;
    }

    public boolean overriderMouse() {
        if (mc.inGameHasFocus) {
            if (!perspectiveToggled)
                return true;
            mc.mouseHelper.mouseXYChange();
            float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8.0F;
            float f3 = mc.mouseHelper.deltaX * f2;
            float f4 = mc.mouseHelper.deltaY * f2;
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
