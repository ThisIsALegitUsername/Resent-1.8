package net.minecraft.util;

import java.util.Collection;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

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
public class WeightedRandom {
	/**+
	 * Returns the total weight of all items in a collection.
	 */
	public static int getTotalWeight(Collection<? extends WeightedRandom.Item> collection) {
		int i = 0;

		for (WeightedRandom.Item weightedrandom$item : collection) {
			i += weightedrandom$item.itemWeight;
		}

		return i;
	}

	public static <T extends WeightedRandom.Item> T getRandomItem(EaglercraftRandom random, Collection<T> collection,
			int totalWeight) {
		if (totalWeight <= 0) {
			throw new IllegalArgumentException();
		} else {
			int i = random.nextInt(totalWeight);
			/**+
			 * Returns a random choice from the input items.
			 */
			return getRandomItem(collection, i);
		}
	}

	public static <T extends WeightedRandom.Item> T getRandomItem(Collection<T> collection, int weight) {
		for (WeightedRandom.Item weightedrandom$item : collection) {
			weight -= weightedrandom$item.itemWeight;
			if (weight < 0) {
				return (T) weightedrandom$item;
			}
		}

		return (T) null;
	}

	public static <T extends WeightedRandom.Item> T getRandomItem(EaglercraftRandom random, Collection<T> collection) {
		/**+
		 * Returns a random choice from the input items.
		 */
		return getRandomItem(random, collection, getTotalWeight(collection));
	}

	public static class Item {
		protected int itemWeight;

		public Item(int itemWeightIn) {
			this.itemWeight = itemWeightIn;
		}
	}
}