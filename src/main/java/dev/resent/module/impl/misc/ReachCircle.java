package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.util.render.Color;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@Module(name = "Reach circle", category = Category.MISC)
public class ReachCircle extends Mod {

    public void uwu(float partialTicks) {
        GlStateManager.pushMatrix();
        mc.entityRenderer.disableLightmap();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableDepth();
        //PlatformOpenGL._wglEnable(2848);
        GlStateManager.depthMask(false);

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (((EntityLivingBase) entity).canEntityBeSeen(mc.thePlayer) && !entity.isInvisible() && entity instanceof EntityPlayer) {
                double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
                double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
                double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

                this.circle(posX, posY, posZ, mc.playerController.isInCreativeMode() ? 4.7D : 3.4D);
            }
        }

        GlStateManager.depthMask(true);
        //PlatformOpenGL._wglDisable(2848);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        mc.entityRenderer.enableLightmap();
        GlStateManager.popMatrix();
    }

    public void circle(double x, double y, double z, double rad) {
        GlStateManager.pushMatrix();
        Color color = new Color(255, 0, 0);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();

        EaglercraftGPU.glLineWidth(2);
        setColor(color.getRGB(), (color.getRGB() >> 24 & 255) / 255.0F);
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);

        for (int i = 0; i <= 90; ++i) {
            setColor(color.getRGB(), 40);
            worldrenderer.pos(x + rad * Math.cos((double) i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin((double) i * 6.283185307179586D / 45.0D)).endVertex();
        }

        tessellator.draw();
        GlStateManager.popMatrix();
    }

    public static void setColor(int color, float alpha) {
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        GlStateManager.color(r, g, b, alpha);
    }
}
