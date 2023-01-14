package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.setting.BooleanSetting;

public class AutoGG extends Mod {

    public AutoGG() {
        super("AutoGG", Category.MISC, true);
        addSetting(rep, onLose, onWin);
    }

    public static BooleanSetting rep = new BooleanSetting("Repetition bypass", "", true);
    public static BooleanSetting onLose = new BooleanSetting("On Lose", "", true);
    public static BooleanSetting onWin = new BooleanSetting("On Win", "", true);
}
