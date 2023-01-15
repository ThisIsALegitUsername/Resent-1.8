package dev.resent;

import dev.resent.event.impl.Event;
import dev.resent.module.base.ModManager;
import net.minecraft.client.Minecraft;

public class Resent {
    static {
        INSTANCE = new Resent();
    }

    public static String NAME = "Resent";
    public static String VERSION = "3.3.2";
    public static Minecraft mc = Minecraft.getMinecraft();
    public static Resent INSTANCE;
    public ModManager modManager;

    public void init() {
        Resent.INSTANCE.modManager = new ModManager();
    }

    public static void onEvent(Event e) {}
}
