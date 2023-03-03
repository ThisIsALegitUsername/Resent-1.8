package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.CustomRectSettingDraw;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.Timer;

@Module(name = "FPS Options", category = Category.MISC, hasSetting = true)
public class FPSOptions extends Mod{

    public BooleanSetting batchRendering = new BooleanSetting("Batch rendering", "", false);
    public CustomRectSettingDraw minSetting = new CustomRectSettingDraw("Minimal settings", ""){
        @Override
        public void onChange(){
        GameSettings gameSettings = mc.gameSettings;
        Timer timer = mc.timer;

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
        }
    };
    //public BooleanSetting delay = new BooleanSetting("Chunk delay", "", false);

    public FPSOptions(){
        addSetting(batchRendering, minSetting);
    }
    
}
