package dev.resent.cape;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.internal.FileChooserResult;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class CapeUi extends GuiScreen {

    public void initGui() {
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 148, "Back"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 6 + 168, "Choose cape"));
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        mc.gameSettings.saveOptions();
    }

    public void drawScreen(int mx, int my, float par3) {
        super.drawScreen(mx, my, par3);
    }

    public void updateScreen(){
        if (EagRuntime.fileChooserHasResult()) {
            CapeManager.free();
            FileChooserResult result = EagRuntime.getFileChooserResult();
            if (result != null) {
                ImageData loadedCape = ImageData.loadImageFile(result.fileData);
                if(loadedCape != null){
                    CapeManager.capeLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("uploadedcape", new DynamicTexture(loadedCape));
                    //Minecraft.getMinecraft().getTextureManager().bindTexture(CapeManager.capeLocation);
                } else {
                    EagRuntime.showPopup("The selected file '" + result.fileName + "' is not a PNG file!");
                }
            }
        }
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(null);
        }else if(par1GuiButton.id == 1){
            CapeManager.displayChooser();
        }
    }
}
