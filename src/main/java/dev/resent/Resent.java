package dev.resent;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import dev.resent.event.impl.Event;
import dev.resent.module.base.Mod;
import dev.resent.module.base.ModManager;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import dev.resent.setting.ModeSetting;
import dev.resent.setting.Setting;
import net.kyori.event.EventBus;
import net.minecraft.client.Minecraft;

public class Resent {

    public static String NAME = "Resent";
    public static double VERSION = 3.3;

    public static Minecraft mc = Minecraft.getMinecraft();
    public static EventBus<Event> eventBus;
    public static Resent INSTANCE;

    static {
        INSTANCE = new Resent();
        eventBus = EventBus.create(Event.class);
    }

    public ModManager modManager;

    public void init() {
        //modManager = new ModManager();
    }

    public void saveSettings(PrintWriter printwriter){
        for(Mod m : modManager.modules){

            List<RenderModule> rmodules = new ArrayList<>();
            if(m instanceof RenderModule){ rmodules.add((RenderModule)m); }

            for(RenderModule rmod : rmodules){
                printwriter.println(rmod.name+"x:"+rmod.x);
                printwriter.println(rmod.name+"y:"+rmod.y);
                printwriter.println(rmod.name+"lastx:"+rmod.lastX);
                printwriter.println(rmod.name+"lastx:"+rmod.lastX);
            }

            for(Setting s : m.settings){
                if(s instanceof ModeSetting){
                    printwriter.println(m.name+"modesetting"+s.name+":"+((ModeSetting) s).getValue());
                }
                if(s instanceof BooleanSetting){
                    printwriter.println(m.name+"boolsetting"+s.name+":"+((BooleanSetting) s).getValue());
                }
            }

            printwriter.println(m.name + ":" + m.enabled);
        }
    }

    public void loadSettings(String[] astring){
        modManager = new ModManager();
        for(Mod m : modManager.modules){

            List<RenderModule> rmodules = new ArrayList<>();
            if(m instanceof RenderModule){ rmodules.add((RenderModule)m); }

            for(RenderModule rmod : rmodules){
                if(astring[0].equals(rmod.name+"x")){
                    rmod.x=Integer.parseInt(astring[1]);
                }
                if(astring[0].equals(rmod.name+"y")){
                    rmod.y=Integer.parseInt(astring[1]);
                }
                if(astring[0].equals(rmod.name+"lastx")){
                    rmod.lastX=Integer.parseInt(astring[1]);
                }
                if(astring[0].equals(rmod.name+"lasty")){
                    rmod.lastY=Integer.parseInt(astring[1]);
                }
            }

            for(Setting se : m.settings){
                if(se instanceof ModeSetting){
                    if(astring[0].equals(m.name+"modesetting"+se.name)){
                        ((ModeSetting)se).setValue(astring[1]);
                    }
                    if(astring[0].equals(m.name+"boolsetting"+se.name)){
                        ((BooleanSetting)se).setValue(astring[1].equals("true"));
                    }
                }
            }

            if(astring[0].equals(m.name)){
                m.setEnabled(astring[1].equals("true"));
            }
        }
    }

    public EventBus<Event> events() {
        return eventBus;
    }
    
}
