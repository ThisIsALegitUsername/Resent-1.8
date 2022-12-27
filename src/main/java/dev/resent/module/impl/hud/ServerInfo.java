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

    public void draw(){
        this.setWidth(mc.fontRendererObj.getStringWidth(getText())+4);
        this.setHeight(mc.fontRendererObj.FONT_HEIGHT+4);
        mc.fontRendererObj.drawString(getText(), this.x+2, this.y+2, -1, tshadow.getValue());
    }

    public String getText(){
        if(mc.getNetHandler() != null){
        return "[Playing on: " + mc.getNetHandler().getNetManager().getServerURI() + "]";
        }
        return "[Playing on: Not connected]";
    }
    
}
