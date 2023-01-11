package net.minecraft.block.state.pattern;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;

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
public class BlockStateHelper implements Predicate<IBlockState> {
	private final BlockState blockstate;
	private final Map<IProperty, Predicate> propertyPredicates = Maps.newHashMap();

	private BlockStateHelper(BlockState blockStateIn) {
		this.blockstate = blockStateIn;
	}

	public static BlockStateHelper forBlock(Block blockIn) {
		return new BlockStateHelper(blockIn.getBlockState());
	}

	public boolean apply(IBlockState iblockstate) {
		if (iblockstate != null && iblockstate.getBlock().equals(this.blockstate.getBlock())) {
			for (Entry entry : this.propertyPredicates.entrySet()) {
				Comparable comparable = iblockstate.getValue((IProperty) entry.getKey());
				if (!((Predicate) entry.getValue()).apply(comparable)) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}

	public <V extends Comparable<V>> BlockStateHelper where(IProperty<V> property, Predicate<? extends V> is) {
		if (!this.blockstate.getProperties().contains(property)) {
			throw new IllegalArgumentException(this.blockstate + " cannot support property " + property);
		} else {
			this.propertyPredicates.put(property, is);
			return this;
		}
	}
}