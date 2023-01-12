package net.minecraft.util;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.futures.ExecutionException;
import net.lax1dude.eaglercraft.v1_8.futures.FutureTask;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;

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
public class Util {
	public static Util.EnumOS getOSType() {
		return EagRuntime.getPlatformOS().getMinecraftEnum();
	}

	public static <V> V func_181617_a(FutureTask<V> parFutureTask, Logger parLogger) {
		try {
			parFutureTask.run();
			return (V) parFutureTask.get();
		} catch (ExecutionException executionexception) {
			parLogger.fatal("Error executing task", executionexception);
		} catch (InterruptedException interruptedexception) {
			parLogger.fatal("Error executing task", interruptedexception);
		}

		return (V) null;
	}

	public static enum EnumOS {
		LINUX, SOLARIS, WINDOWS, OSX, UNKNOWN;
	}
}