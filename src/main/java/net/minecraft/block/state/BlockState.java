package net.minecraft.block.state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.util.Cartesian;
import net.minecraft.util.MapPopulator;

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
public class BlockState {
	private static final Joiner COMMA_JOINER = Joiner.on(", ");
	private static final Function<IProperty, String> GET_NAME_FUNC = new Function<IProperty, String>() {
		public String apply(IProperty iproperty) {
			return iproperty == null ? "<NULL>" : iproperty.getName();
		}
	};
	private final Block block;
	private final ImmutableList<IProperty> properties;
	private final ImmutableList<IBlockState> validStates;

	public BlockState(Block blockIn, IProperty... properties) {
		this.block = blockIn;
		Arrays.sort(properties, new Comparator<IProperty>() {
			public int compare(IProperty iproperty, IProperty iproperty1) {
				return iproperty.getName().compareTo(iproperty1.getName());
			}
		});
		this.properties = ImmutableList.copyOf(properties);
		LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();
		ArrayList<BlockState.StateImplementation> arraylist = Lists.newArrayList();

		for (List list : Cartesian.cartesianProduct(this.getAllowedValues())) {
			Map map = MapPopulator.createMap(this.properties, list);
			BlockState.StateImplementation blockstate$stateimplementation = new BlockState.StateImplementation(blockIn,
					ImmutableMap.copyOf(map));
			linkedhashmap.put(map, blockstate$stateimplementation);
			arraylist.add(blockstate$stateimplementation);
		}

		for (BlockState.StateImplementation blockstate$stateimplementation1 : arraylist) {
			blockstate$stateimplementation1.buildPropertyValueTable(linkedhashmap);
		}

		this.validStates = ImmutableList.copyOf(arraylist);
	}

	public ImmutableList<IBlockState> getValidStates() {
		return this.validStates;
	}

	private List<Iterable<Comparable>> getAllowedValues() {
		ArrayList arraylist = Lists.newArrayList();

		for (int i = 0; i < this.properties.size(); ++i) {
			arraylist.add(((IProperty) this.properties.get(i)).getAllowedValues());
		}

		return arraylist;
	}

	public IBlockState getBaseState() {
		return (IBlockState) this.validStates.get(0);
	}

	public Block getBlock() {
		return this.block;
	}

	public Collection<IProperty> getProperties() {
		return this.properties;
	}

	public String toString() {
		return Objects.toStringHelper(this).add("block", Block.blockRegistry.getNameForObject(this.block))
				.add("properties", Iterables.transform(this.properties, GET_NAME_FUNC)).toString();
	}

	static class StateImplementation extends BlockStateBase {
		private final Block block;
		private final ImmutableMap<IProperty, Comparable> properties;
		private ImmutableTable<IProperty, Comparable, IBlockState> propertyValueTable;

		private StateImplementation(Block blockIn, ImmutableMap<IProperty, Comparable> propertiesIn) {
			this.block = blockIn;
			this.properties = propertiesIn;
		}

		public Collection<IProperty> getPropertyNames() {
			return Collections.unmodifiableCollection(this.properties.keySet());
		}

		public <T extends Comparable<T>> T getValue(IProperty<T> iproperty) {
			if (!this.properties.containsKey(iproperty)) {
				throw new IllegalArgumentException(
						"Cannot get property " + iproperty + " as it does not exist in " + this.block.getBlockState());
			} else {
				return (T) ((Comparable) iproperty.getValueClass().cast(this.properties.get(iproperty)));
			}
		}

		public <T extends Comparable<T>, V extends T> IBlockState withProperty(IProperty<T> iproperty, V comparable) {
			if (!this.properties.containsKey(iproperty)) {
				throw new IllegalArgumentException(
						"Cannot set property " + iproperty + " as it does not exist in " + this.block.getBlockState());
			} else if (!iproperty.getAllowedValues().contains(comparable)) {
				throw new IllegalArgumentException(
						"Cannot set property " + iproperty + " to " + comparable + " on block "
								+ Block.blockRegistry.getNameForObject(this.block) + ", it is not an allowed value");
			} else {
				return (IBlockState) (this.properties.get(iproperty) == comparable ? this
						: (IBlockState) this.propertyValueTable.get(iproperty, comparable));
			}
		}

		public ImmutableMap<IProperty, Comparable> getProperties() {
			return this.properties;
		}

		public Block getBlock() {
			return this.block;
		}

		public boolean equals(Object object) {
			return this == object;
		}

		public int hashCode() {
			return this.properties.hashCode();
		}

		public void buildPropertyValueTable(Map<Map<IProperty, Comparable>, BlockState.StateImplementation> map) {
			if (this.propertyValueTable != null) {
				throw new IllegalStateException();
			} else {
				HashBasedTable hashbasedtable = HashBasedTable.create();

				for (IProperty iproperty : this.properties.keySet()) {
					for (Object comparable : iproperty.getAllowedValues()) {
						if (comparable != this.properties.get(iproperty)) {
							hashbasedtable.put(iproperty, comparable,
									map.get(this.getPropertiesWithValue(iproperty, (Comparable) comparable)));
						}
					}
				}

				this.propertyValueTable = ImmutableTable.copyOf(hashbasedtable);
			}
		}

		private Map<IProperty, Comparable> getPropertiesWithValue(IProperty property, Comparable value) {
			HashMap hashmap = Maps.newHashMap(this.properties);
			hashmap.put(property, value);
			return hashmap;
		}
	}
}