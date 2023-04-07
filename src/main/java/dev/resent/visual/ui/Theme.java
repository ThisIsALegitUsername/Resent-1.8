package dev.resent.visual.ui;

import dev.resent.module.impl.misc.HUD;
import dev.resent.util.render.RenderUtils;
import dev.resent.visual.ui.animation.Animation;
import dev.resent.visual.ui.animation.impl.DecelerateAnimation;
import dev.resent.visual.ui.animation.impl.EaseBackIn;
import dev.resent.visual.ui.animation.impl.EaseInOutQuad;
import dev.resent.visual.ui.animation.impl.ElasticAnimation;
import dev.resent.visual.ui.animation.impl.SmoothStepAnimation;

public class Theme {

    public static int getFontColor() {
        switch (HUD.fontTheme.getValue()) {
            case "Classic":
                return -1;
            case "Rainbow":
                return RenderUtils.getRainbow(4f, 0.8f, 0.85f);
            case "Chroma":
                return 6942069;
        }
        return -1;
    }

    public static Animation getAnimation(int ms, int endpoint, float easeAmount, float elasticity, float smooth, boolean moreElasticity) {
        switch (HUD.animationTheme.getValue()) {
            case "Ease back in":
                return new EaseBackIn(ms, endpoint, easeAmount);
            case "Elastic":
                return new ElasticAnimation(ms, endpoint, elasticity, smooth, moreElasticity);
            case "Ease in out quad":
                return new EaseInOutQuad(ms, endpoint);
            case "Decelerate":
                return new DecelerateAnimation(ms, endpoint);
            case "Smooth step":
                return new SmoothStepAnimation(ms, endpoint);
        }

        return null;
    }

    public static boolean getRounded() {
        return HUD.round.getValue();
    }

    public static boolean getTextShadow() {
        return HUD.tshadow.getValue();
    }
}
