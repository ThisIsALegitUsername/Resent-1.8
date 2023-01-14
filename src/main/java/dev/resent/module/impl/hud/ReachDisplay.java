package dev.resent.module.impl.hud;

import dev.resent.event.impl.Event;
import dev.resent.event.impl.EventAttack;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import java.text.DecimalFormat;
import net.minecraft.util.Vec3;

public class ReachDisplay extends RenderModule {

    public static final DecimalFormat df2 = new DecimalFormat("0.00");
    public static double range;

    public ReachDisplay() {
        super("ReachDisplay", Category.HUD, 4, 34);
    }

    public int getWidth() {
        return mc.fontRendererObj.getStringWidth("[" + df2.format(range) + " Blocks]") + 4;
    }

    public int getHeight() {
        return mc.fontRendererObj.FONT_HEIGHT + 4;
    }

    @Override
    public void draw() {
        mc.fontRendererObj.drawStringWithShadow("[" + df2.format(range) + " Blocks]", this.x + 2, this.y + 2, -1);
    }

    @Override
    public void onEvent(Event e) {
        if (e instanceof EventAttack && e.isPre() && isEnabled()) {
            Vec3 vec3 = this.mc.getRenderViewEntity().getPositionEyes(1.0f);
            range = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
            if (range > 3.0f && !mc.playerController.isInCreativeMode()) {
                range = 3.0f;
            }
        }
    }
}
