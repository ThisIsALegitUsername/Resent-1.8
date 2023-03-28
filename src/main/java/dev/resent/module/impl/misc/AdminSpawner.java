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
import net.minecraft.util.BlockPos;

@Module(name = "ESP", category = Category.HUD)
public class AdminSpawner extends Mod {

    public void draw(){
        for(Object o: mc.theWorld.loadedTileEntityList) {
            if(o instanceof TileEntityMobSpawner) {
                blockESPBox(((TileEntityMobSpawner)o).getPos());
            }
        }
    }

	@Override
	public boolean isAdmin(){
		return true;
	}

    public static void blockESPBox(BlockPos blockPos) {
		Minecraft mc = Minecraft.getMinecraft();
		double x =
				blockPos.getX()
					- mc.getRenderManager().renderPosX;
		double y =
				blockPos.getY()
					- mc.getRenderManager().renderPosY;
		double z =
				blockPos.getZ()
					- mc.getRenderManager().renderPosZ;
		
        GlStateManager.blendFunc(770, 771);
		GlStateManager.enableBlend();
		EaglercraftGPU.glLineWidth(2.0F);
		GlStateManager.color(0, 0, 1, 0.15F);
		GlStateManager.disableTexture2D();
		GlStateManager.disableAlpha();
		GlStateManager.depthMask(false);
		
		//Box
		GlStateManager.color(0, 0, 1, 0.5F);
		RenderGlobal.func_181561_a(new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0));
		GlStateManager.enableTexture2D();
		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
	}
}