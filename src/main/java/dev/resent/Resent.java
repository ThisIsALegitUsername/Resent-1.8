package dev.resent;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import dev.resent.module.base.Mod;
import dev.resent.module.base.ModManager;
import dev.resent.module.base.RenderMod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;
import dev.resent.module.setting.Setting;

public class Resent {

    static {
        INSTANCE = new Resent();
    }

    public static final String NAME = "Resent", VERSION = "3.5";
    public static final Resent INSTANCE;
    public ModManager modManager;

    public void init() {
        Resent.INSTANCE.modManager = new ModManager();
    }

    public void save(PrintWriter printwriter){

        Resent.INSTANCE.modManager.modules.stream().forEach( m -> {
                printwriter.println(m.getName() + ":" + m.isEnabled());
                if(m instanceof RenderMod){
                    printwriter.println(m.getName() + "_x:" + ((RenderMod)m).getX());
                    printwriter.println(m.getName() + "_y:" + ((RenderMod)m).getY());
                    printwriter.println(m.getName() + "_lastx:" + ((RenderMod)m).lastX);
                    printwriter.println(m.getName() + "_lasty:" + ((RenderMod)m).lastY);
                }
                m.settings.stream().forEach(s -> {
                    if (s instanceof ModeSetting) {
                        printwriter.println(m.getName() + "_modesetting_" + s.name + ":" + ((ModeSetting) s).getValue());
                    }
                    if (s instanceof BooleanSetting) {
                        printwriter.println(m.getName() + "_boolsetting_" + s.name + ":" + ((BooleanSetting) s).getValue());
                    }
                });
        });
    }

    public void load(String[] astring){
        for (Mod m : Resent.INSTANCE.modManager.modules) {
            if (astring[0].equals(m.getName())) {
                m.setEnabled(astring[1].equals("true"));
            }

            List<RenderMod> rmodules = new ArrayList<>();
            if (m instanceof RenderMod) {
                rmodules.add((RenderMod) m);
            }

            for (RenderMod rmod : rmodules) {
                if (astring[0].equals(rmod.getName() + "_x")) {
                    rmod.setX(Integer.parseInt(astring[1]));
                }
                if (astring[0].equals(rmod.getName() + "_y")) {
                    rmod.setY(Integer.parseInt(astring[1]));
                }
                if (astring[0].equals(rmod.getName() + "_lastx")) {
                    rmod.lastX = Integer.parseInt(astring[1]);
                }
                if (astring[0].equals(rmod.getName() + "_lasty")) {
                    rmod.lastY = Integer.parseInt(astring[1]);
                }
            }

            for (Setting se : m.settings) {
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
            }
        }
    }

    //Legacy code below.

        //private ISound uwu;

        /*for (Mod m : Resent.INSTANCE.modManager.modules) {
            printwriter.println(m.getName() + ":" + m.isEnabled());

            List<RenderMod> rmodules = new ArrayList<>();
            if (m instanceof RenderMod) {
                rmodules.add((RenderMod) m);
            }

            for (RenderMod rmod : rmodules) {
                printwriter.println(rmod.getName() + "_x:" + rmod.getX());
                printwriter.println(rmod.getName() + "_y:" + rmod.getY());
                printwriter.println(rmod.getName() + "_lastx:" + rmod.lastX);
                printwriter.println(rmod.getName() + "_lastx:" + rmod.lastX);
            }

            for (Setting s : m.settings) {
                if (s instanceof ModeSetting) {
                    printwriter.println(m.getName() + "_modesetting_" + s.name + ":" + ((ModeSetting) s).getValue());
                }
                if (s instanceof BooleanSetting) {
                    printwriter.println(m.getName() + "_boolsetting_" + s.name + ":" + ((BooleanSetting) s).getValue());
                }
            }
        }*/

    /*public void playMusic(ResourceLocation loc){
        this.uwu = PositionedSoundRecord.create(loc);
        if(uwu != null){
            Minecraft.getMinecraft().getSoundHandler().stopSounds();
            Minecraft.getMinecraft().getSoundHandler().playSound(uwu);
        }
    }*/
    
}
