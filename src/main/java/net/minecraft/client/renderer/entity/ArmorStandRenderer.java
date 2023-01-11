package net.minecraft.client.renderer.entity;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;

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
public class ArmorStandRenderer extends RendererLivingEntity<EntityArmorStand> {
	/**+
	 * A constant instance of the armor stand texture, wrapped
	 * inside a ResourceLocation wrapper.
	 */
	public static final ResourceLocation TEXTURE_ARMOR_STAND = new ResourceLocation(
			"textures/entity/armorstand/wood.png");

	public ArmorStandRenderer(RenderManager parRenderManager) {
		super(parRenderManager, new ModelArmorStand(), 0.0F);
		LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
			protected void initArmor() {
				this.field_177189_c = new ModelArmorStandArmor(0.5F);
				this.field_177186_d = new ModelArmorStandArmor(1.0F);
			}
		};
		this.addLayer(layerbipedarmor);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerCustomHead(this.getMainModel().bipedHead));
	}

	/**+
	 * Returns the location of an entity's texture. Doesn't seem to
	 * be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityArmorStand var1) {
		return TEXTURE_ARMOR_STAND;
	}

	public ModelArmorStand getMainModel() {
		return (ModelArmorStand) super.getMainModel();
	}

	protected void rotateCorpse(EntityArmorStand var1, float var2, float f, float var4) {
		GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);
	}

	protected boolean canRenderName(EntityArmorStand entityarmorstand) {
		return entityarmorstand.getAlwaysRenderNameTag();
	}
}