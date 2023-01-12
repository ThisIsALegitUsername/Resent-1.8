package net.minecraft.client.renderer.block.statemap;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;

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
public class BlockStateMapper {
	private Map<Block, IStateMapper> blockStateMap = Maps.newIdentityHashMap();
	private Set<Block> setBuiltInBlocks = Sets.newIdentityHashSet();

	public void registerBlockStateMapper(Block parBlock, IStateMapper parIStateMapper) {
		this.blockStateMap.put(parBlock, parIStateMapper);
	}

	public void registerBuiltInBlocks(Block... parArrayOfBlock) {
		Collections.addAll(this.setBuiltInBlocks, parArrayOfBlock);
	}

	public Map<IBlockState, ModelResourceLocation> putAllStateModelLocations() {
		IdentityHashMap identityhashmap = Maps.newIdentityHashMap();

		for (Block block : Block.blockRegistry) {
			if (!this.setBuiltInBlocks.contains(block)) {
				identityhashmap.putAll(
						((IStateMapper) Objects.firstNonNull(this.blockStateMap.get(block), new DefaultStateMapper()))
								.putStateModelLocations(block));
			}
		}

		return identityhashmap;
	}
}