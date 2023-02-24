package dev.resent.visual.cosmetic;

import dev.resent.module.base.ModManager;
import dev.resent.module.impl.misc.Cosmetics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;

public class CosmeticController {

    public static boolean renderTopHat(AbstractClientPlayer player) {
        return ModManager.cosmetics.isEnabled() && Cosmetics.show.getValue() && shouldRender(player) && Cosmetics.hat.getValue();
    }

    public static boolean renderCrystalWings(AbstractClientPlayer player) {
        return ModManager.cosmetics.isEnabled() && Cosmetics.show.getValue() && shouldRender(player) && Cosmetics.crystalwings.getValue();
    }

    public static boolean renderGlasses(AbstractClientPlayer player) {
        return ModManager.cosmetics.isEnabled() && Cosmetics.show.getValue() && shouldRender(player) && Cosmetics.glasses.getValue();
    }

    public static boolean renderHalo(AbstractClientPlayer player) {
        return ModManager.cosmetics.isEnabled() && Cosmetics.show.getValue() && shouldRender(player) && Cosmetics.halo.getValue();
    }

    public static float[] getTopHatColor(AbstractClientPlayer player) {
        return new float[] { 1, 0, 0 };
    }

    public static float[] getCrystalWingsColor(AbstractClientPlayer player) {
        return new float[] { 1, 1, 1 };
    }

    public static float[] getDragonWingsColor = new float[] { 1f, 1f, 1f, 1f };

    public static boolean shouldRender(AbstractClientPlayer player) {
        switch (Cosmetics.who.getValue()) {
            case "Only you":
                return player == Minecraft.getMinecraft().thePlayer;
            case "Everyone":
                return true;
            case "Everyone else":
                return player != Minecraft.getMinecraft().thePlayer;
        }
        return false;
    }
}
