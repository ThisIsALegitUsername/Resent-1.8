package net.minecraft.client.gui;

import dev.resent.ui.mods.ClientButton;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class GuiMainMenu extends GuiScreen
{
    @Override
    public void initGui() {
        this.buttonList.add(new ClientButton(2, this.width / 2 - 50, this.height / 2, 98, 16, "Multiplayer")); //Multiplayer
        this.buttonList.add(new ClientButton(3, this.width / 2 - 50, this.height / 2 + 17, 98, 16, "Options")); //Settings
        //this.buttonList.add(new ClientButton(4, this.width - 23, 6, 17, 17, EnumChatFormatting.BOLD + ""));
        
        super.initGui();
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/background.jpg"));
        Gui.drawModalRectWithCustomSizedTexture(-21, -1 / 90, 0.0f, 0.0f, this.width + 20, this.height + 20, (float)(this.width + 21), (float)(this.height + 20));
        final String s1 = "Copyright " + EnumChatFormatting.RED + EnumChatFormatting.BOLD + "M" + EnumChatFormatting.RESET + "ojang AB";
        this.drawString(this.fontRendererObj, s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2, this.height - 10, -1);
        this.drawCenteredString(this.mc.fontRendererObj, EnumChatFormatting.AQUA + "Resent " + EnumChatFormatting.RESET + "Client", this.width / 2 - 2, this.height / 2 - 30, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
        //mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/exit.png"));
		//Gui.drawModalRectWithCustomSizedTexture(width - 21, 6, 0, 0, 16, 17, 16, 17);
    }
    
    @Override
    protected void actionPerformed(GuiButton button){
        switch (button.id) {
            case 2: {
                this.mc.displayGuiScreen(new GuiMultiplayer(this));
                break;
            }
            case 3: {
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            }
        }
        super.actionPerformed(button);
    }
}