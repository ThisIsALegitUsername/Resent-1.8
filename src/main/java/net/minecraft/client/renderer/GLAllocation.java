package net.minecraft.client.renderer;

import net.lax1dude.eaglercraft.v1_8.internal.buffer.ByteBuffer;
import net.lax1dude.eaglercraft.v1_8.internal.buffer.FloatBuffer;
import net.lax1dude.eaglercraft.v1_8.internal.buffer.IntBuffer;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;

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
public class GLAllocation {
	/**+
	 * Generates the specified number of display lists and returns
	 * the first index.
	 */
	public static int generateDisplayLists() {
		return EaglercraftGPU.glGenLists();
	}

	public static void deleteDisplayLists(int list) {
		EaglercraftGPU.glDeleteLists(list);
	}

	/**+
	 * Creates and returns a direct byte buffer with the specified
	 * capacity. Applies native ordering to speed up access.
	 */
	public static ByteBuffer createDirectByteBuffer(int capacity) {
		return EagRuntime.allocateByteBuffer(capacity);
	}

	/**+
	 * Creates and returns a direct int buffer with the specified
	 * capacity. Applies native ordering to speed up access.
	 */
	public static IntBuffer createDirectIntBuffer(int capacity) {
		return EagRuntime.allocateIntBuffer(capacity);
	}

	/**+
	 * Creates and returns a direct float buffer with the specified
	 * capacity. Applies native ordering to speed up access.
	 */
	public static FloatBuffer createDirectFloatBuffer(int capacity) {
		return EagRuntime.allocateFloatBuffer(capacity);
	}
}