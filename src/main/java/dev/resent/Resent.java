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
        Resent.INSTANCE.modManager = new ModManager();
    }

    public EventBus<Event> events() {
        return eventBus;
    }
    
}
