package dev.resent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import dev.resent.module.base.ModManager;
import dev.resent.module.base.RenderMod;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
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

    //Legacy code below.

    FileOutputStream fos = null;
    File temp;

    public void playSoundFromByteArray(byte[] bArray) {
        try {
            temp = new File("C:/test").getAbsoluteFile();

            if (!temp.exists()) {
                temp.createNewFile();
            }

            fos = new FileOutputStream(temp);
            fos.write(bArray);
            fos.flush();

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(temp);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error in closing the Stream");
            }
        }
    }

    public void test(){
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("minecraft:music.res"), 1));
    }

    public void stopMusic() {
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
    }
}
