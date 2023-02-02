package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;

@Module(name = "NoDynamicFOV", category = Category.MISC)
public class DynamicFOV extends Mod {

    public DynamicFOV() {
        super("NoDynamicFOV", Category.MISC);
    }
}
