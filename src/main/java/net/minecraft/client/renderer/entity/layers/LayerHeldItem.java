package net.minecraft.client.renderer.entity.layers;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

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
public class LayerHeldItem implements LayerRenderer<EntityLivingBase> {
	private final RendererLivingEntity<?> livingEntityRenderer;

	public LayerHeldItem(RendererLivingEntity<?> livingEntityRendererIn) {
		this.livingEntityRenderer = livingEntityRendererIn;
	}

	public void doRenderLayer(EntityLivingBase entitylivingbase, float var2, float var3, float var4, float var5,
			float var6, float var7, float var8) {
		ItemStack itemstack = entitylivingbase.getHeldItem();
		if (itemstack != null) {
			GlStateManager.pushMatrix();
			if (this.livingEntityRenderer.getMainModel().isChild) {
				float f = 0.5F;
				GlStateManager.translate(0.0F, 0.625F, 0.0F);
				GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
				GlStateManager.scale(f, f, f);
			}

			((ModelBiped) this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F);
			GlStateManager.translate(-0.0625F, 0.4375F, 0.0625F);
			if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer) entitylivingbase).fishEntity != null) {
				itemstack = new ItemStack(Items.fishing_rod, 0);
			}

			Item item = itemstack.getItem();
			Minecraft minecraft = Minecraft.getMinecraft();
			if (item instanceof ItemBlock && Block.getBlockFromItem(item).getRenderType() == 2) {
				GlStateManager.translate(0.0F, 0.1875F, -0.3125F);
				GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
				float f1 = 0.375F;
				GlStateManager.scale(-f1, -f1, f1);
			}

			if (entitylivingbase.isSneaking()) {
				GlStateManager.translate(0.0F, 0.203125F, 0.0F);
			}

			minecraft.getItemRenderer().renderItem(entitylivingbase, itemstack,
					ItemCameraTransforms.TransformType.THIRD_PERSON);
			GlStateManager.popMatrix();
		}
	}

	public boolean shouldCombineTextures() {
		return false;
	}
}