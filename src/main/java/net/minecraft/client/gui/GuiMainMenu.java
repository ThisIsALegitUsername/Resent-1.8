package net.minecraft.client.gui;

import dev.resent.ui.ClientButton;
import dev.resent.util.misc.GlUtils;
import net.lax1dude.eaglercraft.v1_8.profile.GuiScreenEditProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("all")
public class GuiMainMenu extends GuiScreen {

    @Override
    public void initGui() {
        this.buttonList.add(new ClientButton(2, this.width / 2 - 50, this.height / 2, 98, 16, "Multiplayer"));
        this.buttonList.add(new ClientButton(3, this.width / 2 - 50, this.height / 2 + 17, 98, 16, "Options"));
        this.buttonList.add(new ClientButton(4, this.width/2-50, this.height/2+17*2, 98, 16, "Edit profile"));
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("eagler:gui/background.webp"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0f, 0.0f, this.width, this.height, this.width + 21, this.height + 50);
        final String s1 = "Copyright " + EnumChatFormatting.RED + EnumChatFormatting.BOLD + "M" + EnumChatFormatting.RESET + "ojang AB";
        this.drawString(Minecraft.getMinecraft().fontRendererObj, s1, this.width - Minecraft.getMinecraft().fontRendererObj.getStringWidth(s1) - 2, this.height - 10, -1);
        GlUtils.drawCenteredScaledString("" + EnumChatFormatting.AQUA + EnumChatFormatting.BOLD + "Resent", this.width / 2, this.height / 2 - 50, -1, 3f);
        super.drawScreen(mouseX, mouseY, partialTicks);
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
            case 4: {
                this.mc.displayGuiScreen(new GuiScreenEditProfile(this));
                break;
            }
        }
        super.actionPerformed(button);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }
}