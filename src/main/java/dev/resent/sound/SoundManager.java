package dev.resent.sound;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class SoundManager {

    FileOutputStream fos = null;
    File temp;

    public void playSound(String base64) throws UnsupportedEncodingException{
        byte[] byteArray = Base64.getDecoder().decode(new String(base64).getBytes("UTF-8"));
        this.playSound(byteArray);
    }

    public void playSound(byte[] bArray) {
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

    // <3
    public void playAva(){
        ISound sound = PositionedSoundRecord.create(new ResourceLocation("minecraft:music.res"), 1);
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
        Minecraft.getMinecraft().getSoundHandler().playSound(sound);
    }

    public void stopMusic() {
        Minecraft.getMinecraft().getSoundHandler().stopSounds();
    }
}
