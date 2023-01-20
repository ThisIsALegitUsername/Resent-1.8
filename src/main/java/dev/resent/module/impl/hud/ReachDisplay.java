package dev.resent.module.impl.hud;

import java.text.DecimalFormat;

import dev.resent.event.impl.Event;
import dev.resent.event.impl.EventAttack;
import dev.resent.module.Theme;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class ReachDisplay extends RenderModule {

    public static final DecimalFormat df2 = new DecimalFormat("0.00");
    public double range;

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
        mc.fontRendererObj.drawStringWithShadow("[" + df2.format(range) + " Blocks]", this.x + 2, this.y + 2, Theme.getFontColor(Theme.getId()));
    }

    @Override
    public void onEvent(Event e) {
        if(e instanceof EventAttack){
        if (this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY && this.isEnabled() && this.mc.objectMouseOver.entityHit.getEntityId() == ((EventAttack)e).target.getEntityId()) {
            final Vec3 vec3 = this.mc.getRenderViewEntity().getPositionEyes(1.0f);
            this.range = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
        }
        }
    }
}
