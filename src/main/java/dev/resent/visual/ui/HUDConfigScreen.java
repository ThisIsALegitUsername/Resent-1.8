package dev.resent.visual.ui;

import dev.resent.client.Resent;
import dev.resent.module.base.RenderMod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class HUDConfigScreen extends GuiScreen {

    public void initGui() {
        this.buttonList.add(new GuiButton(200, width / 2 - 100, height / 6 + 148, "Back"));
    }

    public void onGuiClosed() {
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
