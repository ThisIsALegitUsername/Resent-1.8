package net.minecraft.block.state;

import java.util.Collection;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

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
public interface IBlockState {
	/**+
	 * Get the names of all properties defined for this BlockState
	 */
	Collection<IProperty> getPropertyNames();

	<T extends Comparable<T>> T getValue(IProperty<T> var1);

	<T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> var1, V var2);

	<T extends Comparable<T>> IBlockState cycleProperty(IProperty<T> var1);

	ImmutableMap<IProperty, Comparable> getProperties();

	Block getBlock();
}