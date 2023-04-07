package dev.resent.util.misc;

public class TimerUtil {

    private static long halfSecond = 500000000;
    private long lastTime;

    private long getDeltaTime() {
        return (System.nanoTime() - lastTime);
    }

    private void updateTime() {
        this.lastTime = System.nanoTime();
    }

    public TimerUtil() {
        this.lastTime = System.nanoTime();
    }

    public boolean hasHalfSecondPassoed() {
        if (getDeltaTime() >= halfSecond) {
            updateTime();
            return true;
        } else return false;
    }
}
