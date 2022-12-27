package dev.resent.util.misc;

import dev.resent.Resent;
import dev.resent.module.base.ModManager;
import dev.resent.module.impl.hud.ComboCounter;
import dev.resent.module.impl.hud.Freelook;
import dev.resent.module.impl.hud.Hitboxes;
import dev.resent.module.impl.misc.AutoGG;
import dev.resent.module.impl.misc.ClearChat;
import dev.resent.module.impl.misc.DynamicFOV;
import dev.resent.module.impl.misc.FPSB;
import dev.resent.module.impl.misc.NoHurtCam;
import dev.resent.module.impl.misc.NoParticles;
import dev.resent.module.impl.misc.NoRain;
import dev.resent.module.impl.misc.Scoreboard;
import dev.resent.module.impl.misc.ScoreboardNumbers;
import dev.resent.module.impl.misc.SelfNametag;

public class W {
   
   public static NoRain noRain(){ return ModManager.noRain; }
   public static DynamicFOV dynamicFOV(){ return ModManager.dynamicFOV; }
   public static NoHurtCam noHurtCam(){ return Resent.INSTANCE.modManager.noHurtCam; }
   public static AutoGG autoGG() { return ModManager.autoGG; }
   public static Freelook freelook(){ return ModManager.freelook; }
   public static ComboCounter comboCounter(){ return ModManager.comboCounter; }
   public static Hitboxes hitboxes(){ return ModManager.hitboxes; }
   public static NoParticles noParticles(){ return ModManager.noParticles; }
   public static ScoreboardNumbers scoreboardNumbers(){ return ModManager.scoreboardNumbers; }
   public static Scoreboard scoreboard(){ return ModManager.scoreboard; }
   public static SelfNametag selfNametag(){ return ModManager.selfNametag; }
   public static ClearChat clearChat(){ return ModManager.clearChat; }
   public static FPSB fpsb() { return Resent.INSTANCE.modManager.fpsb; }

}
