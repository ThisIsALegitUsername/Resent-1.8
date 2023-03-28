package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.AxisAlignedBB;

@Module(name = "Spawner Find", category = Category.HUD)

public class AdminSpawner extends Mod {

    public void render(){
        for(Object o: mc.theWorld.loadedTileEntityList) {
            if(o instanceof TileEntityMobSpawner) {
                box(((TileEntityMobSpawner)o));
            }
        }
    }

	public static void box(TileEntityMobSpawner entity)
	{
		GlStateManager.blendFunc(770, 771);
		GlStateManager.enableBlend();
		EaglercraftGPU.glLineWidth(4.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.color(1F, 0.5F, 0.5F, 0.5F);
		 Minecraft.getMinecraft().getRenderManager();
		RenderGlobal.func_181561_a(
			new AxisAlignedBB(
entity.getPos().getX()-Minecraft.getMinecraft().getRenderManager().renderPosX+0.1,
entity.getPos().getY()-Minecraft.getMinecraft().getRenderManager().renderPosY+0.1,
entity.getPos().getZ()-Minecraft.getMinecraft().getRenderManager().renderPosZ+0.1,
entity.getPos().getX() -Minecraft.getMinecraft().getRenderManager().renderPosX+0.9,
entity.getPos().getY() -Minecraft.getMinecraft().getRenderManager().renderPosY+0.9,
entity.getPos().getZ()-Minecraft.getMinecraft().getRenderManager().renderPosZ+0.9));
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
	}

	@Override
	public boolean isAdmin(){
		return true;
	}
}