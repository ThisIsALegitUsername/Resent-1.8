package dev.resent.module.base;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.resent.module.base.Mod.Category;
import dev.resent.module.impl.hud.ArmorHud;
import dev.resent.module.impl.hud.BPS;
import dev.resent.module.impl.hud.CPS;
import dev.resent.module.impl.hud.ClickGui;
import dev.resent.module.impl.hud.ComboCounter;
import dev.resent.module.impl.hud.FPS;
import dev.resent.module.impl.hud.Freelook;
import dev.resent.module.impl.hud.Hitboxes;
import dev.resent.module.impl.hud.Info;
import dev.resent.module.impl.hud.ItemPhysics;
import dev.resent.module.impl.hud.KeyStrokes;
import dev.resent.module.impl.hud.PotCounter;
import dev.resent.module.impl.hud.PotionHUD;
import dev.resent.module.impl.hud.ReachDisplay;
import dev.resent.module.impl.misc.AdminRay;
import dev.resent.module.impl.misc.AdminSpawner;
import dev.resent.module.impl.misc.AutoGG;
import dev.resent.module.impl.misc.AutoRespawn;
import dev.resent.module.impl.misc.Cape;
import dev.resent.module.impl.misc.ClearChat;
import dev.resent.module.impl.misc.Cosmetics;
import dev.resent.module.impl.misc.CrystalOptimizer;
import dev.resent.module.impl.misc.DynamicFOV;
import dev.resent.module.impl.misc.FPSOptions;
import dev.resent.module.impl.misc.Fullbright;
import dev.resent.module.impl.misc.HUD;
import dev.resent.module.impl.misc.ParticleMultiplier;
import dev.resent.module.impl.misc.Scoreboard;
import dev.resent.module.impl.misc.Sprint;
import dev.resent.module.impl.setting.Animations;
import dev.resent.module.impl.setting.Hand;
import dev.resent.module.impl.setting.MinimalViewBobbing;
import dev.resent.module.impl.setting.NoParticles;
import dev.resent.module.impl.setting.NoRain;
import dev.resent.module.impl.setting.NoSwingDelay;
import dev.resent.module.impl.setting.SelfNametag;

public class ModManager {

    public List<Mod> modules = new ArrayList<>();

    public static Cosmetics cosmetics = new Cosmetics();
    public static Sprint sprint;
    public static CPS cps;
    public static KeyStrokes keyStrokes;
    public static Fullbright fullbright;
    public static ArmorHud armorHud;
    public static NoRain noRain = new NoRain();
    public static DynamicFOV dynamicFOV = new DynamicFOV();
    public static PotionHUD potionHud;
    public static Info coordinate;
    public static FPS fps;
    public static ReachDisplay reachDisplay;
    public static AutoGG autoGG;
    public static Freelook freelook;
    public static ComboCounter comboCounter = new ComboCounter();
    public static Hitboxes hitboxes = new Hitboxes();
    public static AutoRespawn autoRespawn;
    public static NoParticles noParticles = new NoParticles();
    public static Scoreboard scoreboard = new Scoreboard();
    public static ClearChat clearChat = new ClearChat();
    public static Animations animations = new Animations();
    public static MinimalViewBobbing minimalViewBobbing = new MinimalViewBobbing();
    public static NoSwingDelay noSwingDelay;
    public static PotCounter potCounter;
    public static HUD hud = new HUD();
    public static CrystalOptimizer crystalOptimizer = new CrystalOptimizer();
    public static SelfNametag selfNametag = new SelfNametag();
    public static Cape cape = new Cape();
    public static BPS bps = new BPS();
    public static ClickGui clickGui = new ClickGui();
    public static ItemPhysics itemPhysics = new ItemPhysics();
    public static FPSOptions fpsOptions = new FPSOptions();
    public static AdminRay adminRay = new AdminRay();
    public static AdminSpawner adminSpawner =new AdminSpawner();
    public static ParticleMultiplier particleMultiplier = new ParticleMultiplier();
    public static Hand hand;

    public ModManager() {
        //Hud
        register(hand = new Hand());
        register(adminSpawner);
        register(adminRay);
        register(fpsOptions);
        register(itemPhysics);
        register(clickGui);
        register(bps);
        register(cape);
        register(selfNametag);
        register(cosmetics);
        register(crystalOptimizer);
        register(hud = new HUD());
        register(freelook = new Freelook());
        register(keyStrokes = new KeyStrokes());
        register(armorHud = new ArmorHud());
        register(cps = new CPS());
        register(potionHud = new PotionHUD());
        register(reachDisplay = new ReachDisplay());
        register(comboCounter);
        register(coordinate = new Info());
        register(fps = new FPS());
        register(potCounter = new PotCounter());

        //Mechanic
        register(particleMultiplier);
        register(autoRespawn = new AutoRespawn());
        register(fullbright = new Fullbright());
        register(noSwingDelay = new NoSwingDelay());
        register(minimalViewBobbing);
        register(noRain);
        register(dynamicFOV);
        register(sprint = new Sprint());
        register(autoGG = new AutoGG());
        register(hitboxes);
        register(noParticles);
        register(scoreboard);
        register(clearChat);
        register(animations);
    }

    public ArrayList<Mod> modsInCategory(Category c) {
    	if(c == null) {
    		return (ArrayList<Mod>) modules;
    	}
    	
        ArrayList<Mod> inCat = (ArrayList<Mod>) this.modules.stream().filter(m -> m.getCategory() == c).collect(Collectors.toList());

        return inCat;
    }

    public void register(final Mod m) {
        this.modules.add(m);
    }
}
