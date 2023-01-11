package net.minecraft.util;

import net.lax1dude.eaglercraft.v1_8.Display;
import net.lax1dude.eaglercraft.v1_8.Mouse;

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
public class MouseHelper {
	public int deltaX;
	public int deltaY;

	/**+
	 * Grabs the mouse cursor it doesn't move and isn't seen.
	 */
	public void grabMouseCursor() {
		Mouse.setGrabbed(true);
		this.deltaX = 0;
		this.deltaY = 0;
	}

	/**+
	 * Ungrabs the mouse cursor so it can be moved and set it to the
	 * center of the screen
	 */
	public void ungrabMouseCursor() {
		Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
		Mouse.setGrabbed(false);
	}

	public void mouseXYChange() {
		this.deltaX = Mouse.getDX();
		this.deltaY = Mouse.getDY();
	}
}