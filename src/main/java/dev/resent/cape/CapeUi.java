package dev.resent.cape;

import dev.resent.client.Resent;
import dev.resent.module.base.RenderMod;
import dev.resent.ui.ClickGUI;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class CapeUi extends GuiScreen {

    public void initGui() {
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 148, "Back"));
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        mc.gameSettings.saveOptions();
    }

    public void drawScreen(int mx, int my, float par3) {
        this.drawDefaultBackground();
        Resent.INSTANCE.modManager.modules.stream().filter(m -> m.isEnabled() && m instanceof RenderMod).forEach(rm -> ((RenderMod)rm).renderLayout(mx, my));
        super.drawScreen(mx, my, par3);
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(new ClickGUI());
        }
    }
}
