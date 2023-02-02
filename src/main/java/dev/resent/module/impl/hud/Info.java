package dev.resent.module.impl.hud;

import dev.resent.annotation.RenderModule;
import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.util.render.Color;
import net.minecraft.util.BlockPos;

@RenderModule(name = "Info", category = Category.HUD, x = 140, y = 50)
public class Info extends RenderMod {

    public Info() {
        super("Info", Category.HUD, 150, 4);
        //addSetting(direction);
    }

    public BooleanSetting direction = new BooleanSetting("Direction", "", true);
    public static int yes = 6;

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("X: -99999999       +   ");
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT * yes;
    }

    @Override
    public void draw() {
        int px = (int) mc.thePlayer.posX;
        int py = (int) mc.thePlayer.posY;
        int pz = (int) mc.thePlayer.posZ;
        //int rot = MathHelper.floor_double(this.mc.thePlayer.rotationYaw*4/360+0.5) & 3;
        if (mc.thePlayer != null) {
            drawRect(this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight(), new Color(0, 0, 0, 200).getRGB());
            drawString(" X: " + px, this.x + 5, this.y + 14, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            drawString(" Y: " + py, this.x + 5, this.y + 24, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            drawString(" Z: " + pz, this.x + 5, this.y + 34, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            if (!direction.getValue()) yes = 6;
            //if (direction.getVtalue()) {
            //  drawStringWithShadow(" Dir: " + Direction.directionsF[rot], this.x+5+mc.fontRendererObj.getStringWidth(" X:  " + px), this.y + 14, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            drawString(" Biome: " + mc.theWorld.getBiomeGenForCoords(new BlockPos(px, py, pz)).biomeName, this.x + 5, this.y + 44, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            //drawStringWithShadow("  A: " + MathHelper.floor_double((double)mc.thePlayer.rotationYaw>360 || mc.thePlayer.rotationYaw<-360 ? mc.thePlayer.rotationYaw-360 : mc.thePlayer.rotationYaw) + "°", this.x + mc.fontRendererObj.getStringWidth(" D: N  "), this.y + 44, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            yes = 7;
            //}

        }
    }
}
