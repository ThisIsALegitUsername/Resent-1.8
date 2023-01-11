package net.minecraft.client.renderer.block.statemap;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

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
public class StateMap extends StateMapperBase {
	private final IProperty<?> name;
	private final String suffix;
	private final List<IProperty<?>> ignored;

	private StateMap(IProperty<?> name, String suffix, List<IProperty<?>> ignored) {
		this.name = name;
		this.suffix = suffix;
		this.ignored = ignored;
	}

	protected ModelResourceLocation getModelResourceLocation(IBlockState iblockstate) {
		LinkedHashMap<IProperty, Comparable> linkedhashmap = Maps.newLinkedHashMap(iblockstate.getProperties());
		String s;
		if (this.name == null) {
			s = ((ResourceLocation) Block.blockRegistry.getNameForObject(iblockstate.getBlock())).toString();
		} else {
			s = this.name.getName(linkedhashmap.remove(this.name));
		}

		if (this.suffix != null) {
			s = s + this.suffix;
		}

		for (IProperty iproperty : this.ignored) {
			linkedhashmap.remove(iproperty);
		}

		return new ModelResourceLocation(s, this.getPropertyString(linkedhashmap));
	}

	public static class Builder {
		private IProperty<?> name;
		private String suffix;
		private final List<IProperty<?>> ignored = Lists.newArrayList();

		public StateMap.Builder withName(IProperty<?> builderPropertyIn) {
			this.name = builderPropertyIn;
			return this;
		}

		public StateMap.Builder withSuffix(String builderSuffixIn) {
			this.suffix = builderSuffixIn;
			return this;
		}

		public StateMap.Builder ignore(IProperty<?>... parArrayOfIProperty) {
			Collections.addAll(this.ignored, parArrayOfIProperty);
			return this;
		}

		public StateMap build() {
			return new StateMap(this.name, this.suffix, this.ignored);
		}
	}
}