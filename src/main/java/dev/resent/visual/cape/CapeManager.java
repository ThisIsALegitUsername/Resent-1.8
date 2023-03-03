package dev.resent.visual.cape;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.DynamicTexture;
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

    public static void write(byte[] texture) {
        EagRuntime.setStorage("cape", texture);
    }

    public static byte[] read(){
        if(EagRuntime.getStorage("cape") != null){
            return EagRuntime.getStorage("cape");
        }

        return null;
    }

    public static void forceLoad(byte[] texture){
        try{
            ImageData loadedCape = ImageData.loadImageFile(texture);
            if(loadedCape != null){
                CapeManager.capeLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("uploadedcape", new DynamicTexture(loadedCape));
                Minecraft.getMinecraft().displayGuiScreen(null);
                //Minecraft.getMinecraft().getTextureManager().bindTexture(CapeManager.capeLocation);
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
