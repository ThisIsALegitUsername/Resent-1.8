package net.minecraft.scoreboard;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

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
public class ScoreHealthCriteria extends ScoreDummyCriteria {
	public ScoreHealthCriteria(String name) {
		super(name);
	}

	public int func_96635_a(List<EntityPlayer> list) {
		float f = 0.0F;

		for (EntityPlayer entityplayer : list) {
			f += entityplayer.getHealth() + entityplayer.getAbsorptionAmount();
		}

		if (list.size() > 0) {
			f /= (float) list.size();
		}

		return MathHelper.ceiling_float_int(f);
	}

	public boolean isReadOnly() {
		return true;
	}

	public IScoreObjectiveCriteria.EnumRenderType getRenderType() {
		return IScoreObjectiveCriteria.EnumRenderType.HEARTS;
	}
}