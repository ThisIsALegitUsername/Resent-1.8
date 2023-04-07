package dev.resent.visual.ui;

import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiVideoSettings;

public class PreVideo extends GuiScreen {

    public GuiScreen uwu;

    public PreVideo(GuiScreen from) {
        uwu = from;
    }

    @Override
    protected void mouseClicked(int parInt1, int parInt2, int parInt3) {
        if (isMouseInside(parInt1, parInt2, width / 2 - 50, height / 2 + 40, width / 2 + 50, height / 2 + 70)) {
            this.mc.displayGuiScreen(new GuiOptions(uwu, mc.gameSettings));
        } else {
            this.mc.displayGuiScreen(new GuiVideoSettings(this, mc.gameSettings));
        }
    }

    @Override
    public void drawScreen(int i, int j, float var3) {
        this.drawDefaultBackground();
        drawCenteredString(mc.fontRendererObj, "Don't use Auto gui scale! Resent looks best with normal or large gui scales.", width / 2, height / 2, -1);
        drawCenteredString(mc.fontRendererObj, "Press anywhere to continue. Or, go", width / 2, height / 2 + 19, -1);
        RenderUtils.drawRoundedRect(width / 2 - 50, height / 2 + 40, width / 2 + 50, height / 2 + 70, 4, isMouseInside(i, j, width / 2 - 50, height / 2 + 40, width / 2 + 50, height / 2 + 70) ? new Color(40, 40, 40).getRGB() : new Color(21, 21, 21).getRGB());
        drawCenteredString(mc.fontRendererObj, "Back", width / 2, height / 2 + 51, -1);
    }
}
