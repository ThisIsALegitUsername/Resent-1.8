package dev.resent.ui;

import dev.resent.animation.Animation;
import dev.resent.animation.impl.DecelerateAnimation;
import dev.resent.animation.impl.EaseBackIn;
import dev.resent.animation.impl.EaseInOutQuad;
import dev.resent.animation.impl.ElasticAnimation;
import dev.resent.animation.impl.SmoothStepAnimation;
import dev.resent.module.impl.misc.HUD;
import dev.resent.util.render.Color;
import dev.resent.util.render.RenderUtils;

public class Theme {

    public static int getFontColor(int id){
        return getFontColor(id, 255);
    }

    public static int getFontColor(int id, int opacity){
        switch(id){
            case 1:
                return -1;
            case 50:
                return RenderUtils.getRainbow(4f, 0.8f, 0.85f);
            case 6942069:
                return 6942069;
        }
        return -1;
    }

    public static Animation getAnimation(int id, int ms, int endpoint, float easeAmount, float elasticity, float smooth, boolean moreElasticity){
        switch(id){
            case 1:
                return new EaseBackIn(ms, endpoint, easeAmount);
            case 2:
                return new ElasticAnimation(ms, endpoint, elasticity, smooth, moreElasticity);
            case 3:
                return new EaseInOutQuad(ms, endpoint);
            case 4:
                return new DecelerateAnimation(ms, endpoint);
            case 5:
                return new SmoothStepAnimation(ms, endpoint);
        }

        return null;
    }

    public static int getRectColor(int id){
        switch(getRectId()){
            case 1:
                return new Color(0, 0, 0, 200).getRGB();
            case 6942069:
                return 6942069;
        }
        return -1;
    }

    public static int getRectId(){
        switch(HUD.rectTheme.getValue()){
            case "Classic":
                return 1;
            case "Astolfo":
                return 6942069;
        }
        return 1;
    }

    public static int getFontId(){
        switch(HUD.fontTheme.getValue()){
            case "Classic":
                return 1;
            case "Rainbow":
                return 50;
            case "Chroma":
                return 6942069;
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

    public static boolean getTextShadow(){
        return HUD.tshadow.getValue();
    }
    
}
