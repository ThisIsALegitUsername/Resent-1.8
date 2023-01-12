package net.minecraft.entity;

import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
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
public enum EnumCreatureType {
	MONSTER(IMob.class, 70, Material.air, false, false), CREATURE(EntityAnimal.class, 10, Material.air, true, true),
	AMBIENT(EntityAmbientCreature.class, 15, Material.air, true, false),
	WATER_CREATURE(EntityWaterMob.class, 5, Material.water, true, false);

	private final Class<? extends IAnimals> creatureClass;
	private final int maxNumberOfCreature;
	private final Material creatureMaterial;
	private final boolean isPeacefulCreature;
	private final boolean isAnimal;

	private EnumCreatureType(Class<? extends IAnimals> creatureClassIn, int maxNumberOfCreatureIn,
			Material creatureMaterialIn, boolean isPeacefulCreatureIn, boolean isAnimalIn) {
		this.creatureClass = creatureClassIn;
		this.maxNumberOfCreature = maxNumberOfCreatureIn;
		this.creatureMaterial = creatureMaterialIn;
		this.isPeacefulCreature = isPeacefulCreatureIn;
		this.isAnimal = isAnimalIn;
	}

	public Class<? extends IAnimals> getCreatureClass() {
		return this.creatureClass;
	}

	public int getMaxNumberOfCreature() {
		return this.maxNumberOfCreature;
	}

	/**+
	 * Gets whether or not this creature type is peaceful.
	 */
	public boolean getPeacefulCreature() {
		return this.isPeacefulCreature;
	}

	/**+
	 * Return whether this creature type is an animal.
	 */
	public boolean getAnimal() {
		return this.isAnimal;
	}
}