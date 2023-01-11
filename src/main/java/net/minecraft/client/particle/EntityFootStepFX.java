package net.minecraft.client.particle;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files are (c) 2022-2023 LAX1DUDE. All Rights Reserved.
 * 
 * WITH THE EXCEPTION OF PATCH FILES, MINIFIED JAVASCRIPT, AND ALL FILES
 * NORMALLY FOUND IN AN UNMODIFIED MINECRAFT RESOURCE PACK, YOU ARE NOT ALLOWED
 * TO SHARE, DISTRIBUTE, OR REPURPOSE ANY FILE USED BY OR PRODUCED BY THE
 * SOFTWARE IN THIS REPOSITORY WITHOUT PRIOR PERMISSION FROM THE PROJECT AUTHOR.
 * 
 * NOT FOR COMMERCIAL OR MALICIOUS USE
 * 
 * (please read the 'LICENSE' file this repo's root directory for more info) 
 * 
 */
public class EntityFootStepFX extends EntityFX {
	private static final ResourceLocation FOOTPRINT_TEXTURE = new ResourceLocation("textures/particle/footprint.png");
	private int footstepAge;
	private int footstepMaxAge;
	private TextureManager currentFootSteps;

	protected EntityFootStepFX(TextureManager currentFootStepsIn, World worldIn, double xCoordIn, double yCoordIn,
			double zCoordIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		this.currentFootSteps = currentFootStepsIn;
		this.motionX = this.motionY = this.motionZ = 0.0D;
		this.footstepMaxAge = 200;
	}

	/**+
	 * Renders the particle
	 */
	public void renderParticle(WorldRenderer worldrenderer, Entity var2, float f, float var4, float var5, float var6,
			float var7, float var8) {
		float f1 = ((float) this.footstepAge + f) / (float) this.footstepMaxAge;
		f1 = f1 * f1;
		float f2 = 2.0F - f1 * 2.0F;
		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		f2 = f2 * 0.2F;
		GlStateManager.disableLighting();
		float f3 = 0.125F;
		float f4 = (float) (this.posX - interpPosX);
		float f5 = (float) (this.posY - interpPosY);
		float f6 = (float) (this.posZ - interpPosZ);
		float f7 = this.worldObj.getLightBrightness(new BlockPos(this));
		this.currentFootSteps.bindTexture(FOOTPRINT_TEXTURE);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		worldrenderer.pos((double) (f4 - 0.125F), (double) f5, (double) (f6 + 0.125F)).tex(0.0D, 1.0D)
				.color(f7, f7, f7, f2).endVertex();
		worldrenderer.pos((double) (f4 + 0.125F), (double) f5, (double) (f6 + 0.125F)).tex(1.0D, 1.0D)
				.color(f7, f7, f7, f2).endVertex();
		worldrenderer.pos((double) (f4 + 0.125F), (double) f5, (double) (f6 - 0.125F)).tex(1.0D, 0.0D)
				.color(f7, f7, f7, f2).endVertex();
		worldrenderer.pos((double) (f4 - 0.125F), (double) f5, (double) (f6 - 0.125F)).tex(0.0D, 0.0D)
				.color(f7, f7, f7, f2).endVertex();
		Tessellator.getInstance().draw();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		++this.footstepAge;
		if (this.footstepAge == this.footstepMaxAge) {
			this.setDead();
		}

	}

	public int getFXLayer() {
		return 3;
	}

	public static class Factory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double var9, double var11,
				double var13, int... var15) {
			return new EntityFootStepFX(Minecraft.getMinecraft().getTextureManager(), world, d0, d1, d2);
		}
	}
}