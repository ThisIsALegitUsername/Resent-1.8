package dev.resent.ui;

import dev.resent.Resent;
import dev.resent.module.base.RenderModule;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

@SuppressWarnings("all")
public class HUDConfigScreen extends GuiScreen {

    public void initGui() {
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 148, "Back"));
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        mc.gameSettings.saveOptions();
    }

    public void drawScreen(int mx, int my, float par3) {
        this.drawDefaultBackground();
        for (int i = 0; i < Resent.INSTANCE.modManager.modules.size(); i++) {
            if (Resent.INSTANCE.modManager.modules.get(i).isEnabled() && (Resent.INSTANCE.modManager.modules.get(i) instanceof RenderModule)) {
                ((RenderModule) Resent.INSTANCE.modManager.modules.get(i)).renderLayout(mx, my);
            }
        }
        super.drawScreen(mx, my, par3);
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(new ClickGUI());
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }
}
