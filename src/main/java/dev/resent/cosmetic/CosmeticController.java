package dev.resent.cosmetic;

import net.minecraft.client.entity.AbstractClientPlayer;

public class CosmeticController {

    public static boolean renderTopHat(AbstractClientPlayer player){ return true; }
    public static boolean renderCrystalWings(AbstractClientPlayer player){ return true; }
    public static float[] getTopHatColor(AbstractClientPlayer player){ return new float[]{1, 0, 0}; }
    public static float[] getCrystalWingsColor(AbstractClientPlayer player){ return new float[]{1, 1, 1}; }
    
}
