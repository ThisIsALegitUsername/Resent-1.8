package net.minecraft.client.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

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
public class JsonException extends IOException {
	private final List<JsonException.Entry> field_151383_a = Lists.newArrayList();
	private final String field_151382_b;

	public JsonException(String parString1) {
		this.field_151383_a.add(new JsonException.Entry());
		this.field_151382_b = parString1;
	}

	public JsonException(String parString1, Throwable parThrowable) {
		super(parThrowable);
		this.field_151383_a.add(new JsonException.Entry());
		this.field_151382_b = parString1;
	}

	public void func_151380_a(String parString1) {
		((JsonException.Entry) this.field_151383_a.get(0)).func_151373_a(parString1);
	}

	public void func_151381_b(String parString1) {
		((JsonException.Entry) this.field_151383_a.get(0)).field_151376_a = parString1;
		this.field_151383_a.add(0, new JsonException.Entry());
	}

	public String getMessage() {
		return "Invalid " + ((JsonException.Entry) this.field_151383_a.get(this.field_151383_a.size() - 1)).toString()
				+ ": " + this.field_151382_b;
	}

	public static JsonException func_151379_a(Exception parException) {
		if (parException instanceof JsonException) {
			return (JsonException) parException;
		} else {
			String s = parException.getMessage();
			if (parException instanceof FileNotFoundException) {
				s = "File not found";
			}

			return new JsonException(s, parException);
		}
	}

	public static class Entry {
		private String field_151376_a;
		private final List<String> field_151375_b;

		private Entry() {
			this.field_151376_a = null;
			this.field_151375_b = Lists.newArrayList();
		}

		private void func_151373_a(String parString1) {
			this.field_151375_b.add(0, parString1);
		}

		public String func_151372_b() {
			return StringUtils.join(this.field_151375_b, "->");
		}

		public String toString() {
			return this.field_151376_a != null
					? (!this.field_151375_b.isEmpty() ? this.field_151376_a + " " + this.func_151372_b()
							: this.field_151376_a)
					: (!this.field_151375_b.isEmpty() ? "(Unknown file) " + this.func_151372_b() : "(Unknown file)");
		}
	}
}