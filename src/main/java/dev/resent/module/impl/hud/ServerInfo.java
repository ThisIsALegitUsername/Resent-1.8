package dev.resent.module.impl.hud;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;

public class ServerInfo extends RenderModule{
    public ServerInfo(){
        super("Server info", Category.HUD, 4, 84, true);
        addSetting(tshadow);
    }

    public BooleanSetting tshadow = new BooleanSetting("Text shadow", "", true);

    public int getWidth(){
        return mc.fontRendererObj.getStringWidth(getText())+4;
    }

    public int getHeight(){
        return mc.fontRendererObj.FONT_HEIGHT+4;
    }
    public void draw(){
        mc.fontRendererObj.drawString(getText(), this.x+2, this.y+2, -1, tshadow.getValue());
    }

    public String getText(){
        if(mc.getCurrentServerData() != null){
            return "[Playing on: " + mc.getCurrentServerData().serverIP + "]";
        }
        return "[Playing on: Not connected]";
    }
    
}
