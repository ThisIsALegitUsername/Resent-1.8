package dev.resent.module.impl.hud;

import java.text.DecimalFormat;

import dev.resent.Resent;
import dev.resent.event.impl.EventAttack;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import net.minecraft.src.Vec3;

public class ReachDisplay extends RenderModule {

    public static final DecimalFormat df2 = new DecimalFormat("0.00");
    public static double range;
    public ReachDisplay() {
        super("ReachDisplay", Category.HUD, 4, 34);

        Resent.INSTANCE.events().subscribe(EventAttack.class, event -> {
            if (this.isEnabled()) {
                Vec3 vec3 = this.mc.renderViewEntity.getPosition(1.0f);
                range = range <= 3.0f
                        ? ((range >= 0.8) ? this.mc.objectMouseOver.hitVec.distanceTo(vec3) - 0.8
                                : mc.objectMouseOver.hitVec.distanceTo(vec3))
                        : 3.0;
                if (mc.playerController.isInCreativeMode() && this.mc.objectMouseOver.hitVec.distanceTo(vec3) >= 6.0f) {
                    range = 6.0f;
                }
                if (range > 3.0f && !mc.playerController.isInCreativeMode()) {
                    range = 3.0f;
                }
            }
        });
    }

    public int getWidth(){ return mc.fontRenderer.getStringWidth("[" + df2.format(range) + " Blocks]")+4; }
    public int getHeight(){ return mc.fontRenderer.FONT_HEIGHT + 4; }

    @Override
    public void draw() {
        mc.fontRenderer.drawStringWithShadow("[" + df2.format(range) + " Blocks]", this.x + 2, this.y + 2, -1);
    }
    
}
