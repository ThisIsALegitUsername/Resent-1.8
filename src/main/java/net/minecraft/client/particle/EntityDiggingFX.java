package net.minecraft.client.particle;

import net.lax1dude.eaglercraft.v1_8.minecraft.IAcceleratedParticleEngine;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
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
public class EntityDiggingFX extends EntityFX {
	private IBlockState field_174847_a;
	private BlockPos field_181019_az;

	protected EntityDiggingFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn, IBlockState state) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		this.field_174847_a = state;
		this.setParticleIcon(
				Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
		this.particleGravity = state.getBlock().blockParticleGravity;
		this.particleRed = this.particleGreen = this.particleBlue = 0.6F;
		this.particleScale /= 2.0F;
		this.particleAlpha = state.getBlock().getBlockLayer() == EnumWorldBlockLayer.TRANSLUCENT ? 0.999f : 1.0f;
	}

	public EntityDiggingFX func_174846_a(BlockPos pos) {
		this.field_181019_az = pos;
		if (this.field_174847_a.getBlock() == Blocks.grass) {
			return this;
		} else {
			int i = this.field_174847_a.getBlock().colorMultiplier(this.worldObj, pos);
			this.particleRed *= (float) (i >> 16 & 255) / 255.0F;
			this.particleGreen *= (float) (i >> 8 & 255) / 255.0F;
			this.particleBlue *= (float) (i & 255) / 255.0F;
			return this;
		}
	}

	public EntityDiggingFX func_174845_l() {
		this.field_181019_az = new BlockPos(this.posX, this.posY, this.posZ);
		Block block = this.field_174847_a.getBlock();
		if (block == Blocks.grass) {
			return this;
		} else {
			int i = block.getRenderColor(this.field_174847_a);
			this.particleRed *= (float) (i >> 16 & 255) / 255.0F;
			this.particleGreen *= (float) (i >> 8 & 255) / 255.0F;
			this.particleBlue *= (float) (i & 255) / 255.0F;
			return this;
		}
	}

	public int getFXLayer() {
		return 1;
	}

	/**+
	 * Renders the particle
	 */
	public void renderParticle(WorldRenderer worldrenderer, Entity var2, float f, float f1, float f2, float f3,
			float f4, float f5) {
		float f6 = ((float) this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
		float f7 = f6 + 0.015609375F;
		float f8 = ((float) this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
		float f9 = f8 + 0.015609375F;
		float f10 = 0.1F * this.particleScale;
		if (this.particleIcon != null) {
			f6 = this.particleIcon.getInterpolatedU((double) (this.particleTextureJitterX / 4.0F * 16.0F));
			f7 = this.particleIcon.getInterpolatedU((double) ((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
			f8 = this.particleIcon.getInterpolatedV((double) (this.particleTextureJitterY / 4.0F * 16.0F));
			f9 = this.particleIcon.getInterpolatedV((double) ((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
		}

		float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) f - interpPosX);
		float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) f - interpPosY);
		float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) f - interpPosZ);
		int i = this.getBrightnessForRender(f);
		int j = i >> 16 & '\uffff';
		int k = i & '\uffff';
		worldrenderer
				.pos((double) (f11 - f1 * f10 - f4 * f10), (double) (f12 - f2 * f10),
						(double) (f13 - f3 * f10 - f5 * f10))
				.tex((double) f6, (double) f9).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
				.lightmap(j, k).endVertex();
		worldrenderer
				.pos((double) (f11 - f1 * f10 + f4 * f10), (double) (f12 + f2 * f10),
						(double) (f13 - f3 * f10 + f5 * f10))
				.tex((double) f6, (double) f8).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
				.lightmap(j, k).endVertex();
		worldrenderer
				.pos((double) (f11 + f1 * f10 + f4 * f10), (double) (f12 + f2 * f10),
						(double) (f13 + f3 * f10 + f5 * f10))
				.tex((double) f7, (double) f8).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
				.lightmap(j, k).endVertex();
		worldrenderer
				.pos((double) (f11 + f1 * f10 - f4 * f10), (double) (f12 - f2 * f10),
						(double) (f13 + f3 * f10 - f5 * f10))
				.tex((double) f7, (double) f9).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
				.lightmap(j, k).endVertex();
	}

	public boolean renderAccelerated(IAcceleratedParticleEngine accelerator, Entity var2, float f, float f1, float f2,
			float f3, float f4, float f5) {
		int w = this.particleIcon.getIconWidth();
		int h = this.particleIcon.getIconHeight();
		int xOffset = MathHelper.floor_float(w * this.particleTextureJitterX * 4.0f * 0.0625f);
		int yOffset = MathHelper.floor_float(h * this.particleTextureJitterY * 4.0f * 0.0625f);
		int texSize = Math.min(w, h) / 4;
		accelerator.drawParticle(this, this.particleIcon.getOriginX() + xOffset,
				this.particleIcon.getOriginY() + yOffset, getBrightnessForRender(f), texSize, particleScale * 0.1f,
				this.particleRed, this.particleGreen, this.particleBlue, 1.0f);
		return true;
	}

	public int getBrightnessForRender(float f) {
		int i = super.getBrightnessForRender(f);
		int j = 0;
		if (this.worldObj.isBlockLoaded(this.field_181019_az)) {
			j = this.worldObj.getCombinedLight(this.field_181019_az, 0);
		}

		return i == 0 ? j : i;
	}

	public static class Factory implements IParticleFactory {
		public EntityFX getEntityFX(int var1, World world, double d0, double d1, double d2, double d3, double d4,
				double d5, int... aint) {
			return (new EntityDiggingFX(world, d0, d1, d2, d3, d4, d5, Block.getStateById(aint[0]))).func_174845_l();
		}
	}
}