package dev.resent;

import java.io.PrintWriter;

import dev.resent.module.base.ModManager;
import dev.resent.module.base.RenderMod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;

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

        Resent.INSTANCE.modManager.modules.stream().forEach(m -> {
            if (astring[0].equals(m.getName())) {
                m.setEnabled(astring[1].equals("true"));
            }

            if(m instanceof RenderMod){
                if (astring[0].equals(m.getName() + "_x")) {
                    ((RenderMod)m).setX(Integer.parseInt(astring[1]));
                }
                if (astring[0].equals(m.getName() + "_y")) {
                    ((RenderMod)m).setY(Integer.parseInt(astring[1]));
                }
                if (astring[0].equals(m.getName() + "_lastx")) {
                    ((RenderMod)m).lastX = Integer.parseInt(astring[1]);
                }
                if (astring[0].equals(m.getName() + "_lasty")) {
                    ((RenderMod)m).lastY = Integer.parseInt(astring[1]);
                }
            }

            m.settings.stream().forEach(se ->{
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

    //Legacy code below.

    /*public void playMusic(){
        MusicTicker player = Minecraft.getMinecraft().func_181535_r();
        SoundHandler soundhandler = Minecraft.getMinecraft().getSoundHandler();
        player.func_181557_a();
        player.func_181558_a(MusicTicker.MusicType.RES);
        soundhandler.resumeSounds();
    }*/

    public void test(){
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("minecraft:music.res"), 1));
    }

    public void stopMusic(){
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
    }
    
}
