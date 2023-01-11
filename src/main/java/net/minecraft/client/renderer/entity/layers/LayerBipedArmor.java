package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

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
public class LayerBipedArmor extends LayerArmorBase<ModelBiped> {
	public LayerBipedArmor(RendererLivingEntity<?> rendererIn) {
		super(rendererIn);
	}

	protected void initArmor() {
		this.field_177189_c = new ModelBiped(0.5F);
		this.field_177186_d = new ModelBiped(1.0F);
	}

	protected void func_177179_a(ModelBiped modelbiped, int i) {
		this.func_177194_a(modelbiped);
		switch (i) {
		case 1:
			modelbiped.bipedRightLeg.showModel = true;
			modelbiped.bipedLeftLeg.showModel = true;
			break;
		case 2:
			modelbiped.bipedBody.showModel = true;
			modelbiped.bipedRightLeg.showModel = true;
			modelbiped.bipedLeftLeg.showModel = true;
			break;
		case 3:
			modelbiped.bipedBody.showModel = true;
			modelbiped.bipedRightArm.showModel = true;
			modelbiped.bipedLeftArm.showModel = true;
			break;
		case 4:
			modelbiped.bipedHead.showModel = true;
			modelbiped.bipedHeadwear.showModel = true;
		}

	}

	protected void func_177194_a(ModelBiped parModelBiped) {
		parModelBiped.setInvisible(false);
	}
}