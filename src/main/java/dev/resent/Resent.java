package dev.resent;

import dev.resent.module.base.ModManager;

public class Resent {

    static {
        INSTANCE = new Resent();
    }

    public static final String NAME = "Resent", VERSION = "3.4";
    public static final Resent INSTANCE;
    public ModManager modManager;

    public void init() {
        Resent.INSTANCE.modManager = new ModManager();
    }
}
