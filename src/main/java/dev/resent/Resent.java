package dev.resent;

import java.util.ArrayList;
import java.util.List;

import dev.resent.event.impl.Event;
import dev.resent.module.base.ModManager;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import dev.resent.setting.ModeSetting;
import net.kyori.event.EventBus;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;

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


    /* Save & load settings */

    public void saveSettings(NBTTagCompound yee) {
        for (int i = 0; i < modManager.modules.size(); i++) {

            List<RenderModule> renderModules = new ArrayList<>();
            if (modManager.modules.get(i) instanceof RenderModule) {
                renderModules.add((RenderModule) modManager.modules.get(i));
            }

            yee.setBoolean(modManager.modules.get(i).name + "_isEnabled", modManager.modules.get(i).enabled);

            for (int o = 0; o < renderModules.size(); o++) {
                yee.setInteger(renderModules.get(o).name + "_x", renderModules.get(o).x);
                yee.setInteger(renderModules.get(o).name + "_y", renderModules.get(o).y);
                yee.setInteger(renderModules.get(o).name + "_lastX", renderModules.get(o).lastX);
                yee.setInteger(renderModules.get(o).name + "_lastY", renderModules.get(o).lastY);
            }

            for (int x = 0; x < modManager.modules.get(i).settings.size(); x++) {
                /*if (s instanceof NumberSetting) {
                    yee.setFloat(modManager.modules.get(i).name + "_NumberSetting_" + s.name, ((NumberSetting) s).getValue());
                }*/
                if(modManager.modules.get(i).settings.get(x) instanceof ModeSetting){
                    yee.setString(modManager.modules.get(i).name + "_ModeSetting_" + modManager.modules.get(i).settings.get(x).name, ((ModeSetting) modManager.modules.get(i).settings.get(x)).getValue());
                }
                if (modManager.modules.get(i).settings.get(x) instanceof BooleanSetting) {
                    yee.setBoolean(modManager.modules.get(i).name + "_BoolSetting_" + modManager.modules.get(i).settings.get(x).name, ((BooleanSetting) modManager.modules.get(i).settings.get(x)).getValue());
                }
            }
        }
    }

    public void loadSettings(NBTTagCompound yee) {
        
        modManager = new ModManager();
        for (int i = 0; i < modManager.modules.size(); i++) {

            List<RenderModule> renderModules = new ArrayList<>();
            if (modManager.modules.get(i) instanceof RenderModule) {
                renderModules.add((RenderModule) modManager.modules.get(i));
            }

            for (int r = 0; r < renderModules.size(); r++) {
                if (yee.hasKey(renderModules.get(r).name + "_x")) 
                    renderModules.get(r).x = yee.getInteger(renderModules.get(r).name + "_x");
                if(yee.hasKey(renderModules.get(r).name + "_y"))
                renderModules.get(r).y = yee.getInteger(renderModules.get(r).name + "_y");
                if(yee.hasKey(renderModules.get(r).name + "lastX"))
                renderModules.get(r).lastX = yee.getInteger(renderModules.get(r).name + "_lastX");
                if(yee.hasKey(renderModules.get(r).name + "lastY"))
                renderModules.get(r).lastY = yee.getInteger(renderModules.get(r).name + "_lastY");
            }
            if (yee.hasKey(modManager.modules.get(i).name + "_isEnabled")) modManager.modules.get(i).setEnabled(yee.getBoolean(modManager.modules.get(i).name + "_isEnabled"));
            for (int x = 0; x < modManager.modules.get(i).settings.size(); x++) {
                /*if (s instanceof NumberSetting) {
                    if (yee.hasKey(modManager.modules.get(i).name + "_NumberSetting_" + s.name))
                        ((NumberSetting) s).setValue(yee.getFloat(modManager.modules.get(i).name + "_NumberSetting_" + s.name));
                }*/
                if (modManager.modules.get(i).settings.get(x) instanceof ModeSetting) {
                    if (yee.hasKey(modManager.modules.get(i).name + "_ModeSetting_" + modManager.modules.get(i).settings.get(x).name))
                        ((ModeSetting) modManager.modules.get(i).settings.get(x))
                                .setValue(yee.getString(modManager.modules.get(i).name + "_ModeSetting_"
                                        + modManager.modules.get(i).settings.get(x).name));
                }
                if (modManager.modules.get(i).settings.get(x) instanceof BooleanSetting) {
                    if (yee.hasKey(modManager.modules.get(i).name + "_BoolSetting_" + modManager.modules.get(i).settings.get(x).name))
                        ((BooleanSetting) modManager.modules.get(i).settings.get(x)).setValue(yee.getBoolean(modManager.modules.get(i).name + "_BoolSetting_" + modManager.modules.get(i).settings.get(x).name));
                }
            }
        }
    }

    public EventBus<Event> events() {
        return eventBus;
    }
    
}
