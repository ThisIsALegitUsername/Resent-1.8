package dev.resent.module;

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

    public static int getId(){
        switch(HUD.theme.getValue()){
            case "Classic":
                return 1;
            case "Rainbow":
                return 50;

        }
        return -1;
    }

    public static boolean getRounded(){
        return HUD.round.getValue();
    }
    
}
