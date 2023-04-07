package dev.resent.module.base.setting;

import java.util.Arrays;
import java.util.List;

public final class ModeSetting extends Setting {

    public int index;
    public List<String> modes;

    public ModeSetting(final String name, final String defaultMode, final String... modes) {
        super(name, "");
        this.modes = Arrays.asList(modes);
        index = this.modes.indexOf(defaultMode);
    }

    public String getValue() {
        return modes.get(index);
    }

    public void setValue(final String mode) {
        if (modes.contains(mode)) index = this.modes.indexOf(mode);
    }

    public boolean is(String mode) {
        return index == modes.indexOf(mode);
    }

    public void cycle(boolean forwards) {
        if (forwards) {
            if (index < modes.size() - 1) {
                index++;
            } else {
                index = 0;
            }
        }
        if (!forwards) {
            if (index > 0) {
                index--;
            } else {
                index = modes.size() - 1;
            }
        }
    }
}
