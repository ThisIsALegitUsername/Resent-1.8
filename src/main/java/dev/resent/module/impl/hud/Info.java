package dev.resent.module.impl.hud;

import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.RenderMod;
import dev.resent.util.render.Color;
import dev.resent.visual.ui.Theme;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

@RenderModule(name = "Info", category = Category.HUD, x = 140, y = 50, hasSetting = true)
public class Info extends RenderMod {

    public Info() {
        addSetting(direction);
    }

    public BooleanSetting direction = new BooleanSetting("Direction", "", true);
    public static final String[] directionsF = new String[] { "\u00A79S\u00A7r", "\u00A72W\u00A7r", "\u00A74N\u00A7r", "\u00A76E\u00A7r" };

    public int[] getPositions(){
        int[] poses = new int[]{
            (int)mc.thePlayer.posX,
            (int)mc.thePlayer.posY,
            (int)mc.thePlayer.posZ, 
        };

        return poses;
    }

    public int getWidth() {
        return 5 + mc.fontRendererObj.getStringWidth(" X:   Biome:" + mc.theWorld.getBiomeGenForCoords(new BlockPos(getPositions()[0], getPositions()[1], getPositions()[2])).biomeName + Math.max(getPositions()[0],  Math.max(getPositions()[1], getPositions()[2])));
    }

    public int getHeight() {
        return 63;
    }

    @Override
    public void draw() {
        int rot = MathHelper.floor_double(this.mc.thePlayer.rotationYaw * 4 / 360 + 0.5) & 3;
        if (mc.thePlayer != null) {
            drawRect(this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight(), new Color(0, 0, 0, 200).getRGB());
            drawString(" X: " + getPositions()[0], this.x + 5, this.y + 14, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            drawString(" Y: " + getPositions()[1], this.x + 5, this.y + 24, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
            drawString(" Z: " + getPositions()[2], this.x + 5, this.y + 34, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());

            if (direction.getValue()){
                drawString(" Dir: ", this.x + 5 + mc.fontRendererObj.getStringWidth(" X:  " + getPositions()[0]), this.y + 14, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
                mc.fontRendererObj.drawString(directionsF[rot], this.x+5+mc.fontRendererObj.getStringWidth(" X:   Dir: " + getPositions()[0]), this.y + 14, -1, Theme.getTextShadow());
            }
            drawString(" Biome: " + mc.theWorld.getBiomeGenForCoords(new BlockPos(getPositions()[0], getPositions()[1], getPositions()[2])).biomeName, this.x + 5, this.y + 44, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
        }
    }
}
