package dev.resent.module.impl.hud;

import java.text.DecimalFormat;

import dev.resent.annotation.RenderModule;
import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

@RenderModule(name = "ReachDisplay", category = Category.HUD, x = 4, y = 86)
public class ReachDisplay extends RenderMod {

    public static final DecimalFormat df2 = new DecimalFormat("0.00");
    public double range;

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("[" + df2.format(range) + " Blocks]") + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    @Override
    public void draw() {
        drawString("[" + df2.format(range) + " Blocks]", this.x + 2, this.y + 2, Theme.getFontColor(Theme.getFontId()), Theme.getTextShadow());
    }

    public void onAttack(Entity e){
        if(this.isEnabled()){
            final Vec3 vec3 = this.mc.getRenderViewEntity().getPositionEyes(1.0f);
            this.range = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
        }
    }
}
