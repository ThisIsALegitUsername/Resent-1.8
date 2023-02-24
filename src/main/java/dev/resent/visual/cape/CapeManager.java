package dev.resent.visual.cape;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.util.ResourceLocation;

public class CapeManager {

    public static ResourceLocation capeLocation;
    
    public static void displayChooser(){
        EagRuntime.displayFileChooser("image/png", "png");
    }

    public static ResourceLocation getCapeLocation(){
        return capeLocation;
    }

    public static void free(){
        Minecraft.getMinecraft().getTextureManager().deleteTexture(capeLocation);
        capeLocation = null;
    }

    public static boolean shouldRender(AbstractClientPlayer player){
        if(player == Minecraft.getMinecraft().thePlayer){
            return true;
        }
        
        return false;
    }

}
