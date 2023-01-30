package dev.resent.module;

import dev.resent.animation.Animation;
import dev.resent.animation.impl.DecelerateAnimation;
import dev.resent.animation.impl.EaseBackIn;
import dev.resent.animation.impl.EaseInOutQuad;
import dev.resent.animation.impl.ElasticAnimation;
import dev.resent.animation.impl.SmoothStepAnimation;
import dev.resent.module.impl.misc.HUD;
import dev.resent.util.render.RainbowUtil;

public class Theme {

    public static int getFontColor(int id){
        return getFontColor(id, 255);
    }

    public static int getFontColor(int id, int opacity){
        switch(id){
            case 1:
                return -1;
            case 50:
                return RainbowUtil.getRainbow(4f, 0.8f, 0.85f);
        }
        return -1;
    }

    public static Animation getAnimation(int id, int ms, int endpoint, float... etc){
        switch(id){
            case 1:
                return new EaseBackIn(ms, endpoint, etc[0]);
            case 2:
                return new ElasticAnimation(ms, endpoint, etc[0], etc[1], false);
            case 3:
                return new EaseInOutQuad(ms, endpoint);
            case 4:
                return new DecelerateAnimation(ms, endpoint);
            case 5:
                return new SmoothStepAnimation(ms, endpoint);
        }

        return null;
    }

    public static int getFontId(){
        switch(HUD.fontTheme.getValue()){
            case "Classic":
                return 1;
            case "Rainbow":
                return 50;

        }
        return -1;
    }

    public static int getAnimationId(){
        switch(HUD.animationTheme.getValue()){
            case "Ease back in":
                return 1;
            case "Elastic":
                return 2;
            case "Ease in out quad":
                return 3;
            case "Decelerate":
                return 4;
            case "Smooth step":
                return 5;
        }
        return -1;
    }

    public static boolean getRounded(){
        return HUD.round.getValue();
    }
    
}
