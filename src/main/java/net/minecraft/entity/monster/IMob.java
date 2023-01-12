package net.minecraft.entity.monster;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.IAnimals;

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
public interface IMob extends IAnimals {
	Predicate<Entity> mobSelector = new Predicate<Entity>() {
		public boolean apply(Entity entity) {
			return entity instanceof IMob;
		}
	};
	Predicate<Entity> VISIBLE_MOB_SELECTOR = new Predicate<Entity>() {
		public boolean apply(Entity entity) {
			return entity instanceof IMob && !entity.isInvisible();
		}
	};
}