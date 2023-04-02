package dev.resent.visual.crosshair;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.internal.FileChooserResult;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.DynamicTexture;

public class CrosshairUi extends GuiScreen {

    public void initGui() {
        buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 128, "Close"));
        buttonList.add(new GuiButton(1, width / 2 - 100, height / 6 + 150, "Choose crosshair"));
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
            CrosshairManager.free();
            FileChooserResult result = EagRuntime.getFileChooserResult();
            if (result != null) {
                ImageData loadedCrosshair = ImageData.loadImageFile(result.fileData);
                if(loadedCrosshair != null){
                    for(int i = 0; 1 > i; i++){
                        CrosshairManager.write(result.fileData);
                    }
                    CrosshairManager.crosshairLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("uploadedcrosshair", new DynamicTexture(loadedCrosshair));
                    Minecraft.getMinecraft().displayGuiScreen(null);
                    //Minecraft.getMinecraft().getTextureManager().bindTexture(CrosshairManager.crosshairLocation);
                } else {
                    EagRuntime.showPopup("The selected file '" + result.fileName + "' is not an image file!");
                }
            }
        }
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 200) {
            mc.displayGuiScreen(null);
        }else if(par1GuiButton.id == 1){
            CrosshairManager.displayChooser();
        }
    }
}
