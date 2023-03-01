package net.lax1dude.eaglercraft.v1_8;

import dev.resent.client.ClientInfo;

public class EaglercraftVersion {

    //////////////////////////////////////////////////////////////////////

    /// Customize these to fit your fork:

    public static final String projectForkName = ClientInfo.name;
    public static final String projectForkVersion = ClientInfo.version;
    public static final String projectForkVendor = ClientInfo.author;

    public static final String projectForkURL = "https://github.com/ThisIsALegitUsername/Resent-web";

    //////////////////////////////////////////////////////////////////////

    // Do not change these, they must stay as credit to lax1dude's
    // original repository for maintaining the project:

    public static final String projectOriginName = "EaglercraftX";
    public static final String projectOriginAuthor = "lax1dude";
    public static final String projectOriginRevision = "1.8";
    public static final String projectOriginVersion = "u16";

    public static final String projectOriginURL = "https://gitlab.com/lax1dude/eaglercraftx-1.8";

    // Miscellaneous variables:

    public static final String mainMenuStringA = "Minecraft 1.8.8 - " + projectForkName;
    public static final String mainMenuStringB = projectOriginName + " " + projectOriginRevision + "-" + projectOriginVersion;
    public static final String mainMenuStringC = "Rewritten by " + projectOriginAuthor;
    public static final String mainMenuStringD = "Resources Copyright Mojang AB";

    public static final String mainMenuStringE = projectForkName + " " + projectForkVersion;
    public static final String mainMenuStringF = "Made by " + projectForkVendor;

    public static final boolean mainMenuEnableGithubButton = true;
}
