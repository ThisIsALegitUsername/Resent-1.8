package net.minecraft.util;

import java.io.OutputStream;
import java.io.PrintStream;

import net.lax1dude.eaglercraft.v1_8.internal.PlatformRuntime;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
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
public class LoggingPrintStream extends PrintStream {
	private final String domain;
	private final Logger logger;
	private final boolean err;

	public LoggingPrintStream(String domainIn, boolean err, OutputStream outStream) {
		super(outStream);
		this.domain = domainIn;
		this.logger = LogManager.getLogger(domainIn);
		this.err = err;
	}

	public void println(String s) {
		this.logString(s);
	}

	public void println(Object parObject) {
		this.logString(String.valueOf(parObject));
	}

	private void logString(String string) {
		String callingClass = PlatformRuntime.getCallingClass(3);
		if (callingClass == null) {
			if (err) {
				logger.error(string);
			} else {
				logger.info(string);
			}
		} else {
			if (err) {
				logger.error("@({}): {}", new Object[] { callingClass, string });
			} else {
				logger.info("@({}): {}", new Object[] { callingClass, string });
			}
		}
	}
}