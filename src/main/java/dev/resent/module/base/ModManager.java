package dev.resent.module.base;

import java.util.ArrayList;
import java.util.List;

import dev.resent.module.impl.hud.ArmorHud;
import dev.resent.module.impl.hud.CPS;
import dev.resent.module.impl.hud.ComboCounter;
import dev.resent.module.impl.hud.FPS;
import dev.resent.module.impl.hud.Freelook;
import dev.resent.module.impl.hud.Health;
import dev.resent.module.impl.hud.Hitboxes;
import dev.resent.module.impl.hud.Info;
import dev.resent.module.impl.hud.KeyStrokes;
import dev.resent.module.impl.hud.Ping;
import dev.resent.module.impl.hud.PotCounter;
import dev.resent.module.impl.hud.PotionHUD;
import dev.resent.module.impl.hud.ReachDisplay;
import dev.resent.module.impl.hud.ServerInfo;
import dev.resent.module.impl.hud.Watermark;
import dev.resent.module.impl.misc.Animations;
import dev.resent.module.impl.misc.AutoGG;
import dev.resent.module.impl.misc.AutoRespawn;
import dev.resent.module.impl.misc.ClearChat;
import dev.resent.module.impl.misc.Crosshair;
import dev.resent.module.impl.misc.DynamicFOV;
import dev.resent.module.impl.misc.FPSB;
import dev.resent.module.impl.misc.Fullbright;
import dev.resent.module.impl.misc.MinimalViewBobbing;
import dev.resent.module.impl.misc.NoHurtCam;
import dev.resent.module.impl.misc.NoParticles;
import dev.resent.module.impl.misc.NoRain;
import dev.resent.module.impl.misc.NoSwingDelay;
import dev.resent.module.impl.misc.Scoreboard;
import dev.resent.module.impl.misc.SelfNametag;
import dev.resent.module.impl.misc.Tooltips;
import dev.resent.module.impl.movement.AutoJump;
import dev.resent.module.impl.movement.AutoWalk;
import dev.resent.module.impl.movement.Sprint;
import net.minecraft.client.Minecraft;

public class ModManager {
	
	public List<Mod> modules = new ArrayList<>();
	public Minecraft mc = Minecraft.getMinecraft();

	public static String currentModDragging = null;
	
	public static Sprint sprint;
	public static CPS cps;
	public static KeyStrokes keyStrokes;
	public static Fullbright fullbright;
	public static ArmorHud armorHud;
	public static NoRain noRain = new NoRain();
	public static DynamicFOV dynamicFOV = new DynamicFOV();
	public static PotionHUD potionHud;
	public static NoHurtCam noHurtCam = new NoHurtCam();
	public static Info coordinate;
	public static FPS fps;
	public static ReachDisplay reachDisplay;
	public static AutoGG autoGG;
	public static AutoRespawn autoRespawn;
	public static Freelook freelook;
	public static ComboCounter comboCounter = new ComboCounter();
	public static Hitboxes hitboxes = new Hitboxes();
	public static Health health;
	//public static ChunkBorders chunkBorders;
	public static NoParticles noParticles = new NoParticles();
	public static Scoreboard scoreboard = new Scoreboard();
	public static AutoWalk autoWalk;
	public static AutoJump autoJump;
	public static SelfNametag selfNametag = new SelfNametag();
	public static ClearChat clearChat = new ClearChat();
	public static Tooltips tooltips;
	public static FPSB fpsb = new FPSB();
	public static Animations animations = new Animations();
	public static MinimalViewBobbing minimalViewBobbing = new MinimalViewBobbing();
	public static Watermark watermark;
	public static NoSwingDelay noSwingDelay;
	public static PotCounter potCounter;
	public static Ping ping;
	public static ServerInfo serverInfo;
	public static Crosshair crosshair = new Crosshair();

	public ModManager() {

		//Hud
		register(ping = new Ping());
		register(serverInfo = new ServerInfo());
		register(watermark = new Watermark());
		register(freelook = new Freelook());
		register(fpsb);
		register(keyStrokes = new KeyStrokes());
		register(armorHud = new ArmorHud());
		register(cps = new CPS());
		register(potionHud = new PotionHUD());
		register(reachDisplay = new ReachDisplay());
		register(comboCounter);
		register(coordinate = new Info());
		register(fps = new FPS());
		register(health = new Health());
		register(potCounter = new PotCounter());


		//Mechanic
		register(crosshair);
		register(fullbright = new Fullbright());
		register(noSwingDelay = new NoSwingDelay());
		register(minimalViewBobbing);
		register(noRain);
		register(dynamicFOV);
		register(sprint = new Sprint());
		register(noHurtCam);
		register(autoGG = new AutoGG());
		register(autoRespawn = new AutoRespawn());
		register(hitboxes);
		//register(chunkBorders = new ChunkBorders());
		register(noParticles);
		register(scoreboard );
		register(autoWalk = new AutoWalk());
		register(autoJump = new AutoJump());
		//register(selfNametag = new SelfNametag());
		register(clearChat);
		register(tooltips = new Tooltips());
		register(animations);

	}

	public void register(Mod m) {
		this.modules.add(m);
	}

	public void onKey(int keycode) {
		for (int i = 0; i < modules.size(); i++) {
			if (keycode == modules.get(i).keyCode && keycode != 0) {
				modules.get(i).toggle();
			}
		}
	}

	public List<Mod> getModulesByCategory(Category c) {
		List<Mod> modules1 = new ArrayList<Mod>();
		for (Mod module : modules) {
			if (module.category == c) {
				modules1.add(module);
			}
		}
		return modules1;
	}

}