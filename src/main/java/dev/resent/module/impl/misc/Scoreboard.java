package dev.resent.module.impl.misc;

import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import dev.resent.setting.BooleanSetting;

public class Scoreboard extends Mod {

    public Scoreboard() {
        super("Scoreboard", Category.MISC, true);
        addSetting(numbers);
    }

    public BooleanSetting numbers = new BooleanSetting("Numbers", "", false);
}
