package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.ModManager;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.CustomRectSettingDraw;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums;
import net.minecraft.client.settings.GameSettings;

@Module(name = "FPS Options", category = Category.MISC, hasSetting = true)
public class FPSOptions extends Mod{

    public BooleanSetting batchRendering = new BooleanSetting("Batch rendering", "", true);
    public BooleanSetting blockEffects = new BooleanSetting("Remove block effects", "", true);
    public BooleanSetting limit = new BooleanSetting("Limit particles", "", true);
    public BooleanSetting lowTick = new BooleanSetting("Low animation", "", true);
    public BooleanSetting lightUpdates = new BooleanSetting("No light updates", "", true);
    public BooleanSetting noArmSwing = new BooleanSetting("No limb swing", "", false);
    public BooleanSetting reducedWater = new BooleanSetting("Reduced water lag", "", true);
    public CustomRectSettingDraw minSetting = new CustomRectSettingDraw("Minimal settings", ""){
        @Override
        public void onPress(){
            GameSettings gameSettings = mc.gameSettings;

            GlStateManager.enableTexture2D();
            GlStateManager.shadeModel(RealOpenGLEnums.GL_SMOOTH);
            GlStateManager.clearDepth(1.0f);
            GlStateManager.disableDepth();
            GlStateManager.enableAlpha();
            GlStateManager.alphaFunc(RealOpenGLEnums.GL_GREATER, 0.1F);

            gameSettings.limitFramerate = 120;
            gameSettings.enableVsync = false;
            gameSettings.fancyGraphics = false;
            gameSettings.clouds = 0;
            gameSettings.particleSetting = 2;
            gameSettings.viewBobbing = false;
            gameSettings.mipmapLevels = 0;
            gameSettings.renderDistanceChunks = 1;
            gameSettings.ambientOcclusion = 0;
            gameSettings.snooperEnabled = false;
            gameSettings.fog = false;
            gameSettings.allowBlockAlternatives = false;
            gameSettings.fxaa = 0;
            gameSettings.field_181151_V = false;

            ModManager.noParticles.setEnabled(true);
            ModManager.fpsb.setEnabled(true);
        }
    };
    //public BooleanSetting delay = new BooleanSetting("Chunk delay", "", false);

    public FPSOptions(){
        addSetting(batchRendering, blockEffects, limit, lowTick, lightUpdates, noArmSwing, reducedWater, minSetting);
    }
    
}
