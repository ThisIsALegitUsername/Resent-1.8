package net.minecraft.block.state.pattern;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.block.state.BlockWorldState;

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
public class FactoryBlockPattern {
	private static final Joiner COMMA_JOIN = Joiner.on(",");
	private final List<String[]> depth = Lists.newArrayList();
	private final Map<Character, Predicate<BlockWorldState>> symbolMap = Maps.newHashMap();
	private int aisleHeight;
	private int rowWidth;

	private FactoryBlockPattern() {
		this.symbolMap.put(Character.valueOf(' '), Predicates.alwaysTrue());
	}

	public FactoryBlockPattern aisle(String... aisle) {
		if (aisle.length > 0 && !StringUtils.isEmpty(aisle[0])) {
			if (this.depth.isEmpty()) {
				this.aisleHeight = aisle.length;
				this.rowWidth = aisle[0].length();
			}

			if (aisle.length != this.aisleHeight) {
				throw new IllegalArgumentException("Expected aisle with height of " + this.aisleHeight
						+ ", but was given one with a height of " + aisle.length + ")");
			} else {
				for (String s : aisle) {
					if (s.length() != this.rowWidth) {
						throw new IllegalArgumentException(
								"Not all rows in the given aisle are the correct width (expected " + this.rowWidth
										+ ", found one with " + s.length() + ")");
					}

					for (char c0 : s.toCharArray()) {
						if (!this.symbolMap.containsKey(Character.valueOf(c0))) {
							this.symbolMap.put(Character.valueOf(c0), (Predicate<BlockWorldState>) null);
						}
					}
				}

				this.depth.add(aisle);
				return this;
			}
		} else {
			throw new IllegalArgumentException("Empty pattern for aisle");
		}
	}

	public static FactoryBlockPattern start() {
		return new FactoryBlockPattern();
	}

	public FactoryBlockPattern where(char symbol, Predicate<BlockWorldState> blockMatcher) {
		this.symbolMap.put(Character.valueOf(symbol), blockMatcher);
		return this;
	}

	public BlockPattern build() {
		return new BlockPattern(this.makePredicateArray());
	}

	private Predicate<BlockWorldState>[][][] makePredicateArray() {
		this.checkMissingPredicates();
		Predicate[][][] apredicate = (Predicate[][][]) ((Predicate[][][]) Array.newInstance(Predicate.class,
				new int[] { this.depth.size(), this.aisleHeight, this.rowWidth }));

		for (int i = 0; i < this.depth.size(); ++i) {
			for (int j = 0; j < this.aisleHeight; ++j) {
				for (int k = 0; k < this.rowWidth; ++k) {
					apredicate[i][j][k] = (Predicate) this.symbolMap
							.get(Character.valueOf(((String[]) this.depth.get(i))[j].charAt(k)));
				}
			}
		}

		return apredicate;
	}

	private void checkMissingPredicates() {
		ArrayList arraylist = Lists.newArrayList();

		for (Entry entry : this.symbolMap.entrySet()) {
			if (entry.getValue() == null) {
				arraylist.add(entry.getKey());
			}
		}

		if (!arraylist.isEmpty()) {
			throw new IllegalStateException(
					"Predicates for character(s) " + COMMA_JOIN.join(arraylist) + " are missing");
		}
	}
}