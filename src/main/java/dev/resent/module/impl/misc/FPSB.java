package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
import net.minecraft.util.MathHelper;


@Module(name = "Fast math", category = Category.MISC)
public class FPSB extends Mod {
    public void onEnable() { MathHelper.fastMath = true; }
    public void onDisable() { MathHelper.fastMath = false; }

}
