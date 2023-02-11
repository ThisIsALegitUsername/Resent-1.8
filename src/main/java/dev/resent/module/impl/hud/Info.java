package dev.resent.module.impl.hud;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.ui.Theme;
import dev.resent.util.render.Color;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

@RenderModule(name = "Info", category = Category.HUD, x = 140, y = 50, hasSetting = true)
public class Info extends RenderMod {

    public Info(){
        addSetting(direction);
    }

    public BooleanSetting direction = new BooleanSetting("Direction", "", true);
    public static final String[] directionsF = new String[]{"\u00A79S\u00A7r", "\u00A72W\u00A7r", "\u00A74N\u00A7r", "\u00A76E\u00A7r"};

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("X: -99999999       +   ");
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT * 7;
    }

    @Override
    public void draw() {
        int px = (int) mc.thePlayer.posX;
        int py = (int) mc.thePlayer.posY;
        int pz = (int) mc.thePlayer.posZ;
        int rot = MathHelper.floor_double(this.mc.thePlayer.rotationYaw*4/360+0.5) & 3;
        if (mc.thePlayer != null) {
            drawRect(this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight(), new Color(0, 0, 0, 200).getRGB());
            drawString(" X: " + px, this.x + 5, this.y + 14, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            drawString(" Y: " + py, this.x + 5, this.y + 24, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            drawString(" Z: " + pz, this.x + 5, this.y + 34, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());

            if (direction.getValue())
            drawString(" Dir: " + directionsF[rot], this.x+5+mc.fontRendererObj.getStringWidth(" X:  " + px), this.y + 14, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            drawString(" Biome: " + mc.theWorld.getBiomeGenForCoords(new BlockPos(px, py, pz)).biomeName, this.x + 5, this.y + 44, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());

        }
    }
}
