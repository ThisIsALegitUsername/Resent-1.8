package net.minecraft.client.renderer.culling;

import net.minecraft.util.AxisAlignedBB;

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
public class Frustum implements ICamera {
	private ClippingHelper clippingHelper;
	private double xPosition;
	private double yPosition;
	private double zPosition;

	public Frustum() {
		this(ClippingHelperImpl.getInstance());
	}

	public Frustum(ClippingHelper parClippingHelper) {
		this.clippingHelper = parClippingHelper;
	}

	public void setPosition(double d0, double d1, double d2) {
		this.xPosition = d0;
		this.yPosition = d1;
		this.zPosition = d2;
	}

	/**+
	 * Calls the clipping helper. Returns true if the box is inside
	 * all 6 clipping planes, otherwise returns false.
	 */
	public boolean isBoxInFrustum(double parDouble1, double parDouble2, double parDouble3, double parDouble4,
			double parDouble5, double parDouble6) {
		return this.clippingHelper.isBoxInFrustum(parDouble1 - this.xPosition, parDouble2 - this.yPosition,
				parDouble3 - this.zPosition, parDouble4 - this.xPosition, parDouble5 - this.yPosition,
				parDouble6 - this.zPosition);
	}

	/**+
	 * Returns true if the bounding box is inside all 6 clipping
	 * planes, otherwise returns false.
	 */
	public boolean isBoundingBoxInFrustum(AxisAlignedBB axisalignedbb) {
		return this.isBoxInFrustum(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX,
				axisalignedbb.maxY, axisalignedbb.maxZ);
	}
}