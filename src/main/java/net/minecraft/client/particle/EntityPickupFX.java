package net.minecraft.client.particle;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.OpenGlHelper;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
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
public class EntityPickupFX extends EntityFX {
	private Entity field_174840_a;
	private Entity field_174843_ax;
	private int age;
	private int maxAge;
	private float field_174841_aA;
	private RenderManager field_174842_aB = Minecraft.getMinecraft().getRenderManager();

	public EntityPickupFX(World worldIn, Entity parEntity, Entity parEntity2, float parFloat1) {
		super(worldIn, parEntity.posX, parEntity.posY, parEntity.posZ, parEntity.motionX, parEntity.motionY,
				parEntity.motionZ);
		this.field_174840_a = parEntity;
		this.field_174843_ax = parEntity2;
		this.maxAge = 3;
		this.field_174841_aA = parFloat1;
	}

	/**+
	 * Renders the particle
	 */
	public void renderParticle(WorldRenderer var1, Entity var2, float f, float var4, float var5, float var6, float var7,
			float var8) {
		float f1 = ((float) this.age + f) / (float) this.maxAge;
		f1 = f1 * f1;
		double d0 = this.field_174840_a.posX;
		double d1 = this.field_174840_a.posY;
		double d2 = this.field_174840_a.posZ;
		double d3 = this.field_174843_ax.lastTickPosX
				+ (this.field_174843_ax.posX - this.field_174843_ax.lastTickPosX) * (double) f;
		double d4 = this.field_174843_ax.lastTickPosY
				+ (this.field_174843_ax.posY - this.field_174843_ax.lastTickPosY) * (double) f
				+ (double) this.field_174841_aA;
		double d5 = this.field_174843_ax.lastTickPosZ
				+ (this.field_174843_ax.posZ - this.field_174843_ax.lastTickPosZ) * (double) f;
		double d6 = d0 + (d3 - d0) * (double) f1;
		double d7 = d1 + (d4 - d1) * (double) f1;
		double d8 = d2 + (d5 - d2) * (double) f1;
		int i = this.getBrightnessForRender(f);
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j / 1.0F, (float) k / 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		d6 = d6 - interpPosX;
		d7 = d7 - interpPosY;
		d8 = d8 - interpPosZ;
		this.field_174842_aB.renderEntityWithPosYaw(this.field_174840_a, (double) ((float) d6), (double) ((float) d7),
				(double) ((float) d8), this.field_174840_a.rotationYaw, f);
	}

	/**+
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		++this.age;
		if (this.age == this.maxAge) {
			this.setDead();
		}

	}

	public int getFXLayer() {
		return 3;
	}
}