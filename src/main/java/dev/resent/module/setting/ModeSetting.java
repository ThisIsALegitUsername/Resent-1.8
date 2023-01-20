package dev.resent.module.setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModeSetting extends Setting {

    public List<String> modes = new ArrayList<>();

    public String current;

    public int curr = 0;

    public ModeSetting(String name, String description, String... modes) {
        super(name, description);
        Collections.addAll(this.modes, modes);
        if (curr == this.modes.size()) {
            curr = 0;
        }
        current = this.modes.get(curr);
    }

    public void onChange() {}

    public void setValue(String val) {
        if (this.modes.contains(val)) {
            this.current = val;
        }
    }

    public void next() {
        ++curr;
        if (curr >= this.modes.size()) {
            curr = 0;
        }
        current = this.modes.get(curr);
        onChange();
    }

    public void back() {
        --curr;
        if (curr <= this.modes.size()) {
            curr = 0;
        }

        current = this.modes.get(curr);
        onChange();
    }

    public String getValue() {
        return current;
    }
}
