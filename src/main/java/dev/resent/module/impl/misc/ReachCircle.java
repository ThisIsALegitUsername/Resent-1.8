package dev.resent.module.impl.misc;

import java.util.Iterator;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import net.lax1dude.eaglercraft.v1_8.internal.PlatformOpenGL;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

@Module(name = "Reach circle", category = Category.MISC)
public class ReachCircle extends Mod{

    public void uwu(float partialTicks){
        GlStateManager.pushMatrix();
        mc.entityRenderer.disableLightmap();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.disableDepth();
        //PlatformOpenGL._wglEnable(RealOpenGLEnums.GL_LINE_SMOOTH);
        GlStateManager.depthMask(false);
        Iterator<Entity> iterator = mc.theWorld.loadedEntityList.iterator();

        while (iterator.hasNext()) {
            Object o = iterator.next();
            Entity entity = (Entity) o;

            if (entity instanceof EntityLivingBase && !entity.isInvisible() && !entity.isSneaking() && entity != mc.thePlayer && ((EntityLivingBase) entity).canEntityBeSeen(mc.thePlayer) && !entity.isInvisible() && entity instanceof EntityPlayer) {
                double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - mc.getRenderManager().viewerPosX;
                double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - mc.getRenderManager().viewerPosY;
                double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - mc.getRenderManager().viewerPosZ;

                this.circle(posX, posY, posZ, mc.playerController.isInCreativeMode() ? 4.7D : 3.4D);
            }
        }

        GlStateManager.depthMask(true);
        //PlatformOpenGL._wglDisable(RealOpenGLEnums.GL_LINE_SMOOTH);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        mc.entityRenderer.enableLightmap();
        GlStateManager.popMatrix();
	}
    	
    public void circle(double x, double y, double z, double rad) {
        GlStateManager.pushMatrix();

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        
        EaglercraftGPU.glLineWidth(2);
        GlStateManager.color(1, 1, 1);
        worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);

        for (int i = 0; i <= 90; ++i) {
            GlStateManager.color(1, 0, 0);
            worldrenderer.pos(x + rad * Math.cos((double) i * 6.283185307179586D / 45.0D), y, z + rad * Math.sin((double) i * 6.283185307179586D / 45.0D));
        }

        tessellator.draw();
        GlStateManager.popMatrix();
    }
}
