package net.minecraft.client.renderer.block.model;

import net.lax1dude.eaglercraft.v1_8.vector.Vector3f;
import net.minecraft.util.EnumFacing;

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
public class BlockPartRotation {
	public final Vector3f origin;
	public final EnumFacing.Axis axis;
	public final float angle;
	public final boolean rescale;

	public BlockPartRotation(Vector3f originIn, EnumFacing.Axis axisIn, float angleIn, boolean rescaleIn) {
		this.origin = originIn;
		this.axis = axisIn;
		this.angle = angleIn;
		this.rescale = rescaleIn;
	}
}