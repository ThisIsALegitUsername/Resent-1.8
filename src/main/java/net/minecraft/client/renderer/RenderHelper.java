package net.minecraft.client.renderer;

import net.lax1dude.eaglercraft.v1_8.internal.buffer.FloatBuffer;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.minecraft.util.Vec3;

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
public class RenderHelper {
	/**+
	 * Float buffer used to set OpenGL material colors
	 */
	private static FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
	private static final Vec3 LIGHT0_POS = (new Vec3(0.20000000298023224D, 1.0D, -0.699999988079071D)).normalize();
	private static final Vec3 LIGHT1_POS = (new Vec3(-0.20000000298023224D, 1.0D, 0.699999988079071D)).normalize();

	/**+
	 * Disables the OpenGL lighting properties enabled by
	 * enableStandardItemLighting
	 */
	public static void disableStandardItemLighting() {
		if (!DeferredStateManager.isInDeferredPass()) {
			GlStateManager.disableLighting();
			GlStateManager.disableMCLight(0);
			GlStateManager.disableMCLight(1);
			GlStateManager.disableColorMaterial();
		}
	}

	/**+
	 * Sets the OpenGL lighting properties to the values used when
	 * rendering blocks as items
	 */
	public static void enableStandardItemLighting() {
		if (!DeferredStateManager.isInDeferredPass()) {
			GlStateManager.enableLighting();
			GlStateManager.enableMCLight(0, 0.6f, LIGHT0_POS.xCoord, LIGHT0_POS.yCoord, LIGHT0_POS.zCoord, 0.0D);
			GlStateManager.enableMCLight(1, 0.6f, LIGHT1_POS.xCoord, LIGHT1_POS.yCoord, LIGHT1_POS.zCoord, 0.0D);
			GlStateManager.setMCLightAmbient(0.4f, 0.4f, 0.4f);
			GlStateManager.enableColorMaterial();
		}
	}

	/**+
	 * Update and return colorBuffer with the RGBA values passed as
	 * arguments
	 */
	private static FloatBuffer setColorBuffer(double parDouble1, double parDouble2, double parDouble3,
			double parDouble4) {
		/**+
		 * Update and return colorBuffer with the RGBA values passed as
		 * arguments
		 */
		return setColorBuffer((float) parDouble1, (float) parDouble2, (float) parDouble3, (float) parDouble4);
	}

	/**+
	 * Update and return colorBuffer with the RGBA values passed as
	 * arguments
	 */
	private static FloatBuffer setColorBuffer(float parFloat1, float parFloat2, float parFloat3, float parFloat4) {
		colorBuffer.clear();
		colorBuffer.put(parFloat1).put(parFloat2).put(parFloat3).put(parFloat4);
		colorBuffer.flip();
		return colorBuffer;
	}

	/**+
	 * Sets OpenGL lighting for rendering blocks as items inside GUI
	 * screens (such as containers).
	 */
	public static void enableGUIStandardItemLighting() {
		if (!DeferredStateManager.isInDeferredPass()) {
			GlStateManager.pushMatrix();
			GlStateManager.rotate(-30.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(165.0F, 1.0F, 0.0F, 0.0F);
			enableStandardItemLighting();
			GlStateManager.popMatrix();
		}
	}
}