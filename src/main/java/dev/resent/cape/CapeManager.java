package dev.resent.cape;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.internal.FileChooserResult;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class CapeManager {
    
    public static void displayChooser(){
        EagRuntime.displayFileChooser("image/png", "png");
    }

    public static ResourceLocation capeLocation;

    public static void loadCape(){
        CapeManager.free();
        if (EagRuntime.fileChooserHasResult()) {
            FileChooserResult result = EagRuntime.getFileChooserResult();
            if (result != null) {
                ImageData loadedCape = ImageData.loadImageFile(result.fileData);
                if(loadedCape != null){
                    capeLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("uploadedcape", new DynamicTexture(loadedCape));
                    Minecraft.getMinecraft().getTextureManager().bindTexture(capeLocation);
                } else {
                    EagRuntime.showPopup("The selected file '" + result.fileName + "' is not a PNG file!");
                }
            }
        }
    }

    public static void free(){
        Minecraft.getMinecraft().getTextureManager().deleteTexture(capeLocation);
        capeLocation = null;
    }

}
