package net.minecraft.client.renderer.chunk;

import java.util.BitSet;
import java.util.Set;

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
public class SetVisibility {
	private static final int COUNT_FACES = EnumFacing.values().length;
	private final BitSet bitSet;

	public SetVisibility() {
		this.bitSet = new BitSet(COUNT_FACES * COUNT_FACES);
	}

	public void setManyVisible(Set<EnumFacing> parSet) {
		for (EnumFacing enumfacing : parSet) {
			for (EnumFacing enumfacing1 : parSet) {
				this.setVisible(enumfacing, enumfacing1, true);
			}
		}

	}

	public void setVisible(EnumFacing facing, EnumFacing facing2, boolean parFlag) {
		this.bitSet.set(facing.ordinal() + facing2.ordinal() * COUNT_FACES, parFlag);
		this.bitSet.set(facing2.ordinal() + facing.ordinal() * COUNT_FACES, parFlag);
	}

	public void setAllVisible(boolean visible) {
		this.bitSet.set(0, this.bitSet.size(), visible);
	}

	public boolean isVisible(EnumFacing facing, EnumFacing facing2) {
		return this.bitSet.get(facing.ordinal() + facing2.ordinal() * COUNT_FACES);
	}

	public String toString() {
		StringBuilder stringbuilder = new StringBuilder();
		stringbuilder.append(' ');

		for (EnumFacing enumfacing : EnumFacing.values()) {
			stringbuilder.append(' ').append(enumfacing.toString().toUpperCase().charAt(0));
		}

		stringbuilder.append('\n');

		for (EnumFacing enumfacing2 : EnumFacing.values()) {
			stringbuilder.append(enumfacing2.toString().toUpperCase().charAt(0));

			for (EnumFacing enumfacing1 : EnumFacing.values()) {
				if (enumfacing2 == enumfacing1) {
					stringbuilder.append("  ");
				} else {
					boolean flag = this.isVisible(enumfacing2, enumfacing1);
					stringbuilder.append(' ').append((char) (flag ? 'Y' : 'n'));
				}
			}

			stringbuilder.append('\n');
		}

		return stringbuilder.toString();
	}
}