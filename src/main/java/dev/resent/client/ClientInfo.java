package dev.resent.client;

import dev.resent.util.render.Color;

public class ClientInfo {

    public static final String name = "Resent";
    public static final String version = "3.6";
    public static final String author = "Nitwit";
    public static final String release = Release.STABLE.name;

    public static final int primaryColor = new Color(66, 66, 66).getRGB();
    public static final int secondaryColor = new Color(117, 117, 117).getRGB();

    public enum Release {
        BETA("Beta"),
        STABLE("Stable");

        String name;

        Release(String name) {
            this.name = name;
        }
    }
}
