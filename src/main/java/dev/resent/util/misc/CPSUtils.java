package dev.resent.util.misc;

import java.util.ArrayList;
import java.util.List;

import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.minecraft.client.Minecraft;

public class CPSUtils {

    public static List<Long> clicks = new ArrayList<>();
    public static List<Long> clicks2 = new ArrayList<>();
    public static boolean pressed = Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindAttack.getKeyCode());
    public static boolean rpressed = Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode());
    public static boolean wasPressed;
    public static boolean wasPressed2;
    public static long lastPressed;
    public static long lastPressed2;

    public static int getLeftCPS() {
        updateCPS();
        final long leftTime = System.currentTimeMillis() + 100L;
        FuncUtils.removeIf(clicks, beenLeftTime -> beenLeftTime + 1200L < leftTime + 200L);
        return clicks.size();
    }

    public static int getRightCPS() {
        updateCPS();
        final long rightTime = System.currentTimeMillis() + 100L;
        FuncUtils.removeIf(clicks2, beenRightTime -> beenRightTime + 1200L < rightTime + 200L);
        return clicks2.size();
    }

    public static void updateCPS(){

        if (pressed != wasPressed) {
            lastPressed = System.currentTimeMillis();
            wasPressed = pressed;
                if (pressed) clicks.add(Long.valueOf(lastPressed));
        }

        if (rpressed != wasPressed2) {
            lastPressed2 = System.currentTimeMillis() + 10L;
            wasPressed2 = rpressed;
                if (rpressed) clicks2.add(Long.valueOf(lastPressed2));
        }
    }

}
