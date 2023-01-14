/*package dev.resent.ui.mods;

import java.util.ArrayList;

import dev.resent.Resent;
import dev.resent.module.base.Mod;
import dev.resent.setting.BooleanSetting;
import dev.resent.setting.Setting;
import dev.resent.util.misc.Keyboard;
GlStateManager.popMatrix();
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;
import net.minecraft.src.MathHelper;

@SuppressWarnings("all")
public class GuiScreenModule extends GuiScreen {

    protected String screenTitle = "Resent";
    public int mcount = 0;
    public ArrayList<Mod> modules = new ArrayList<>();
    public boolean watchingMod = false;
    public Mod modWatching = null;
    public String searchString = "";
    public GuiTextField search;
    public int offset = 0;

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int par3) {
        if (watchingMod == false && modWatching == null) {
            this.search.mouseClicked(mouseX, mouseY, par3);
            int i = 0;
            for (int z = 0; z < Resent.INSTANCE.modManager.modules.size(); z++) {
                Mod m = Resent.INSTANCE.modManager.modules.get(z);
                if (m.name.toLowerCase().contains(this.searchString.toLowerCase())) {
                    int x = 10;
                    int y = (10 + fontRenderer.FONT_HEIGHT * 2 + 15 + 10 + 10) + 60 * i + 10 - offset;
                    int width = MathHelper.clamp_int(this.width / 4, 200, this.width / 4) - 10 - x;
                    int height = (10 + fontRenderer.FONT_HEIGHT * 2 + 15 + 10 + 10) + 60 * i + 60 - offset - y;
                    if ((mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height && par3 == 0)
                            && !(mouseX >= x + 5 && mouseX <= x + width - 5 && mouseY >= y + height - 5 - 15
                                    && mouseY <= y + height - 5))
                        m.toggle();
                    if (par3 == 0 && mouseX >= x + 5 && mouseX <= x + width - 5 && mouseY >= y + height - 5 - 15
                            && mouseY <= y + height - 5) {
                        this.modWatching = m;
                        this.watchingMod = true;
                    }
                    i++;
                }
            }
        } else {
            Mod m1 = this.modWatching;
            if (par3 == 0 && mouseX >= (10 + fontRenderer.FONT_HEIGHT + 6) / 2 / 2
                    && mouseX <= (10 + fontRenderer.FONT_HEIGHT + 6) / 2 / 2 + (10 + fontRenderer.FONT_HEIGHT + 6) / 2
                    && mouseY >= (10 + fontRenderer.FONT_HEIGHT + 6) / 2 / 2
                    && mouseY <= (10 + fontRenderer.FONT_HEIGHT + 6) / 2 / 2
                            + (10 + fontRenderer.FONT_HEIGHT + 6) / 2) {
                watchingMod = false;
                modWatching = null;
            }else {
                int i = 0;
                Mod m = this.modWatching;
                for (int amongus = 0; amongus < m.settings.size(); amongus++) {
                    Setting s = m.settings.get(amongus);
                    BooleanSetting bS = null;
                    if (s instanceof BooleanSetting) {
                        bS = (BooleanSetting) s;
                    }

                    int x = 10;
                    int y = (fontRenderer.FONT_HEIGHT * 2 + 45) + 60 * i + 10 - offset;
                    int width = MathHelper.clamp_int(this.width / 4, 200, this.width / 4) - 10 - x;
                    int height = (fontRenderer.FONT_HEIGHT * 2 + 45) + 60 * i + 60 - offset - y;
                    if (isMouseInside(mouseX, mouseY, 10, (fontRenderer.FONT_HEIGHT*2+45)+60*i+10-offset,
                            MathHelper.clamp_int(this.width / 4, 200, this.width / 4) - 10,
                            (10 + fontRenderer.FONT_HEIGHT * 2 + 15 + 10 + 10) + 60 * i + 60 - offset) && par3 == 0) {
                        if (bS != null) {
                            bS.setValue(!bS.getValue());
                        }
                    }
                    i++;
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, par3);
    }

    public void drawScreen(int mx, int my, float par3) {
        offset = MathHelper.clamp_int(MathHelper.clamp_int(offset, 0, getListMaxScroll()), 0, getListMaxScroll());

        Gui.drawRect(0, 0, MathHelper.clamp_int(this.width / 4, 200, this.width / 4), this.height, 0xFF090909);
        if (!this.watchingMod && this.modWatching == null) {
            int i = 0;
        for (int z = 0; z < Resent.INSTANCE.modManager.modules.size(); z++) {
                Mod m = Resent.INSTANCE.modManager.modules.get(z);
                if (m.name.toLowerCase().contains(this.searchString.toLowerCase())) {
                    int frho = (10 + fontRenderer.FONT_HEIGHT * 2 + 15 + 10 + 10 + 60 * i + 60 - offset);
                    int x = 10;
                    int y = (fontRenderer.FONT_HEIGHT*2+45) + 60 * i + 10 - offset;
                    int width = MathHelper.clamp_int(this.width / 4, 200, this.width / 4) - 10 - x;
                    int height = frho - y;
                    Gui.drawRect(10, (10 + fontRenderer.FONT_HEIGHT * 2 + 15 + 10 + 10) + 60 * i + 10 - offset,
                            MathHelper.clamp_int(this.width / 4, 200, this.width / 4) - 10,
                            frho, 0xff1c1c1c);
                    fontRenderer.drawString(m.name, 20,
                            (10 + fontRenderer.FONT_HEIGHT * 2 + 15 + 10 + 10) + 60 * i + 10 - offset + 10, -1);

                    Gui.drawRect(x, y, x + width, y + 1, m.isEnabled() ? 0xff009e00 : 0xff9e0000);
                    Gui.drawRect(x, y, x + 1, y + height, m.isEnabled() ? 0xff009e00 : 0xff9e0000);
                    Gui.drawRect(x + width - 1, y, x + width, y + height, m.isEnabled() ? 0xff009e00 : 0xff9e0000);
                    Gui.drawRect(x, y + height - 1, x + width, y + height, m.isEnabled() ? 0xff009e00 : 0xff9e0000);

                        Gui.drawRect(x + 5, y + height - 5 - 15, x + width - 5, y + height - 5, 0xff333333);
                        fontRenderer.drawString("Settings",
                                (x + width / 2) - fontRenderer.getStringWidth("Settings") / 2,
                                y + height - 5 - 15 + fontRenderer.FONT_HEIGHT / 2, -1);

                    i++;
                }
            }
                Gui.drawRect(0, 0, MathHelper.clamp_int(this.width / 4, 200, this.width / 4),
                        10 + fontRenderer.FONT_HEIGHT * 2 + 15 + 10, 0xFF090909);
                this.search.drawTextBox();
                fontRenderer.drawString(screenTitle + " - ClickGui", this.width / 4 / 2 - fontRenderer.getStringWidth(screenTitle + "") / 2,
                        10,
                        -1);
            } else {
                int frho3 = (10+fontRenderer.FONT_HEIGHT+6)/2/2;
    		Gui.drawRect(frho3, frho3, frho3+(10+fontRenderer.FONT_HEIGHT+6)/2, frho3+(10+fontRenderer.FONT_HEIGHT+6)/2,0xff1c1c1c);
    		fontRenderer.drawString("<", frho3+frho3-fontRenderer.getStringWidth("<")/2, frho3+frho3-fontRenderer.FONT_HEIGHT/2, -1);
    		fontRenderer.drawString(screenTitle + " - "+modWatching.name, MathHelper.clamp_int(this.width/4, 200, this.width/4)/2-fontRenderer.getStringWidth(screenTitle + " - "+modWatching.name)/2, 10, -1);
    		Gui.drawRect(0, 10+fontRenderer.FONT_HEIGHT+6, MathHelper.clamp_int(this.width/4, 200, this.width/4), 10+fontRenderer.FONT_HEIGHT+7, -1);
    		int i = 0;
    		Mod m = this.modWatching;
            for (int amongus = 0; amongus < m.settings.size(); amongus++) {
                Setting s = m.settings.get(amongus);
                BooleanSetting bS = null;
                if (s instanceof BooleanSetting) {
                    bS = (BooleanSetting)s;
                }

                    int x = 10;
                    int y = (fontRenderer.FONT_HEIGHT*2+45)+60*i+10-offset;
                    int frho1 =  (10 + fontRenderer.FONT_HEIGHT * 2 + 15 + 10 + 10);
                    int width = MathHelper.clamp_int(this.width / 4, 200, this.width / 4) - 10 - x;
                    int height = (fontRenderer.FONT_HEIGHT*2+45)+60*i+60-offset-y;
                    Gui.drawRect(10, y,
                            MathHelper.clamp_int(this.width / 4, 200, this.width / 4) - 10,
                           frho1 + 60 * i + 60 - offset, 0xff1c1c1c);
                    fontRenderer.drawString(s.name, 20,frho1 + 60 * i + 10 - offset + 10, -1);

                    if (bS != null) {
                        Gui.drawRect(x, y, x + width, y + 1, bS.getValue() ? 0xff009e00 : 0xff9e0000);
                        Gui.drawRect(x, y, x + 1, y + height, bS.getValue() ? 0xff009e00 : 0xff9e0000);
                        Gui.drawRect(x + width - 1, y, x + width, y + height, bS.getValue() ? 0xff009e00 : 0xff9e0000);
                        Gui.drawRect(x, y + height - 1, x + width, y + height, bS.getValue() ? 0xff009e00 : 0xff9e0000);
                    }
                    i++;
                }
    		}
        super.drawScreen(mx, my, par3);
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 200) {
            this.mc.displayGuiScreen(null);
        }else if (par1GuiButton.id == 5) {
            mc.displayGuiScreen(new HUDConfigScreen());
        }else if (par1GuiButton.id == 2) {
            mc.displayGuiScreen(new ClickGUI());
        }

        if (par1GuiButton.id != 200 && par1GuiButton.id != 5 && par1GuiButton.id != 2) {
            this.mc.displayGuiScreen(new GuiScreenModule());
        }
        super.actionPerformed(par1GuiButton);
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void updateScreen() {
        this.search.updateCursorCounter();
        super.updateScreen();
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.search.textboxKeyTyped(par1, par2);
        searchString = this.search.getText();
        if (par2 == Keyboard.KEY_ESCAPE || par2 == Minecraft.getMinecraft().gameSettings.keyBindClickGui.keyCode)
            mc.displayGuiScreen(null);
        super.keyTyped(par1, par2);
    }

    @Override
    public void handleMouseInput() {
        if (getListMaxScroll() + this.height >= this.height) {
            int wheel = EaglerAdapter.mouseGetEventDWheel();
            if (wheel < 0) {
                for (int i = 0; i < 20; i++) {
                    offset = MathHelper.clamp_int(offset + 1, 0, getListMaxScroll());
                }
            } else if (wheel > 0) {
                for (int i = 0; i < 20; i++) {
                    offset = MathHelper.clamp_int(offset - 1, 0, getListMaxScroll());
                }
            }
            super.handleMouseInput();
        }
        offset = MathHelper.clamp_int(MathHelper.clamp_int(offset, 0, getListMaxScroll()), 0, getListMaxScroll());
    }

    private int getListMaxScroll() {
        int mods = 0;
        int i = 0;
        for (int z = 0; z < Resent.INSTANCE.modManager.modules.size(); z++) {
            Mod m = Resent.INSTANCE.modManager.modules.get(z);
            if (m.name.toLowerCase().contains(this.searchString.toLowerCase())) {
                mods++;
                i++;
            }
        }
        return 60 * mods + 70 - this.height;
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(200, this.width - 200, this.height - 20, 100, 20, "Back"));
        this.buttonList.add(new GuiButton(5, this.width - 100, this.height - 20, 100, 20, "Modify HUD"));
        this.buttonList.add(new GuiButton(2, this.width - 300, this.height - 20, 100, 20, "Test"));
        search = new GuiTextField(fontRenderer, 10, 10 + fontRenderer.FONT_HEIGHT * 2,
                MathHelper.clamp_int(this.width / 4, 200, this.width / 4) - 20, 15);
        this.search.custom = true;
        this.search.setText(searchString);
    }

    public void onGuiClosed() {
        EaglerAdapter.enableRepeatEvents(false);
        mc.gameSettings.saveOptions();
    }

    public boolean isMouseInside(int mouseX, int mouseY, int x, int y, int width, int height) {
        return (mouseX >= x && mouseX <= width) && (mouseY >= y && mouseY <= height);
    }

}*/
