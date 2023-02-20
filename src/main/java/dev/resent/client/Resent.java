package dev.resent.client;

import java.io.PrintWriter;

import dev.resent.module.base.ModManager;
import dev.resent.module.base.RenderMod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;
import dev.resent.sound.SoundManager;

public class Resent {
    static {
        INSTANCE = new Resent();
    }

    public static final String NAME = "Resent", VERSION = "3.6";
    public static final Resent INSTANCE;
    public SoundManager soundManager;
    public ModManager modManager;

    public void init() {
        Resent.INSTANCE.modManager = new ModManager();
        Resent.INSTANCE.soundManager = new SoundManager();
    }

    public void save(PrintWriter printwriter) {
        Resent.INSTANCE.modManager.modules
            .stream()
            .forEach(m -> {
                printwriter.println(m.getName() + ":" + m.isEnabled());
                if (m instanceof RenderMod) {
                    printwriter.println(m.getName() + "_x:" + ((RenderMod) m).getX());
                    printwriter.println(m.getName() + "_y:" + ((RenderMod) m).getY());
                    printwriter.println(m.getName() + "_lastx:" + ((RenderMod) m).lastX);
                    printwriter.println(m.getName() + "_lasty:" + ((RenderMod) m).lastY);
                }
                m.settings
                    .stream()
                    .forEach(s -> {
                        if (s instanceof ModeSetting) {
                            printwriter.println(m.getName() + "_modesetting_" + s.name + ":" + ((ModeSetting) s).getValue());
                        }
                        if (s instanceof BooleanSetting) {
                            printwriter.println(m.getName() + "_boolsetting_" + s.name + ":" + ((BooleanSetting) s).getValue());
                        }
                    });
            });
    }

    public void load(String[] astring) {
        Resent.INSTANCE.modManager.modules
            .stream()
            .forEach(m -> {
                if (astring[0].equals(m.getName())) {
                    m.setEnabled(astring[1].equals("true"));
                }

                if (m instanceof RenderMod) {
                    if (astring[0].equals(m.getName() + "_x")) {
                        ((RenderMod) m).setX(Integer.parseInt(astring[1]));
                    }
                    if (astring[0].equals(m.getName() + "_y")) {
                        ((RenderMod) m).setY(Integer.parseInt(astring[1]));
                    }
                    if (astring[0].equals(m.getName() + "_lastx")) {
                        ((RenderMod) m).lastX = Integer.parseInt(astring[1]);
                    }
                    if (astring[0].equals(m.getName() + "_lasty")) {
                        ((RenderMod) m).lastY = Integer.parseInt(astring[1]);
                    }
                }

                m.settings
                    .stream()
                    .forEach(se -> {
                        if (se instanceof ModeSetting) {
                            if (astring[0].equals(m.getName() + "_modesetting_" + se.name)) {
                                ((ModeSetting) se).setValue(astring[1]);
                            }
                        }
                        if (se instanceof BooleanSetting) {
                            if (astring[0].equals(m.getName() + "_boolsetting_" + se.name)) {
                                ((BooleanSetting) se).setValue(astring[1].equals("true"));
                            }
                        }
                    });
            });
    }

}
