package dev.resent.visual.crosshair;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class CrosshairManager {

    public static ResourceLocation crosshairLocation;
    
    public static void displayChooser(){
        EagRuntime.displayFileChooser("image/png", "png");
    }

    public static ResourceLocation getCrosshairLocation(){
        return crosshairLocation;
    }

    public static void free(){
        Minecraft.getMinecraft().getTextureManager().deleteTexture(crosshairLocation);
        crosshairLocation = null;
    }

    public static void write(byte[] texture) {
        EagRuntime.setStorage("crosshair", texture);
    }

    public static byte[] read(){
        if(EagRuntime.getStorage("crosshair") != null){
            return EagRuntime.getStorage("crosshair");
        }

        return null;
    }

    public static void forceLoad(byte[] texture){
        try{
            ImageData loadedCrosshair = ImageData.loadImageFile(texture);
            if(loadedCrosshair != null){
                CrosshairManager.crosshairLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("uploadedcrosshair", new DynamicTexture(loadedCrosshair));
                Minecraft.getMinecraft().displayGuiScreen(null);
                //Minecraft.getMinecraft().getTextureManager().bindTexture(CrosshairManager.crosshairLocation);
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static boolean shouldRender(AbstractClientPlayer player){
        if(player == Minecraft.getMinecraft().thePlayer){
            return true;
        }
        
        return false;
    }

}
