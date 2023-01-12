package net.minecraft.util;

import java.util.ArrayList;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

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
public enum EnumParticleTypes {
	EXPLOSION_NORMAL("explode", 0, true), EXPLOSION_LARGE("largeexplode", 1, true),
	EXPLOSION_HUGE("hugeexplosion", 2, true), FIREWORKS_SPARK("fireworksSpark", 3, false),
	WATER_BUBBLE("bubble", 4, false), WATER_SPLASH("splash", 5, false), WATER_WAKE("wake", 6, false),
	SUSPENDED("suspended", 7, false), SUSPENDED_DEPTH("depthsuspend", 8, false), CRIT("crit", 9, false),
	CRIT_MAGIC("magicCrit", 10, false), SMOKE_NORMAL("smoke", 11, false), SMOKE_LARGE("largesmoke", 12, false),
	SPELL("spell", 13, false), SPELL_INSTANT("instantSpell", 14, false), SPELL_MOB("mobSpell", 15, false),
	SPELL_MOB_AMBIENT("mobSpellAmbient", 16, false), SPELL_WITCH("witchMagic", 17, false),
	DRIP_WATER("dripWater", 18, false), DRIP_LAVA("dripLava", 19, false), VILLAGER_ANGRY("angryVillager", 20, false),
	VILLAGER_HAPPY("happyVillager", 21, false), TOWN_AURA("townaura", 22, false), NOTE("note", 23, false),
	PORTAL("portal", 24, false), ENCHANTMENT_TABLE("enchantmenttable", 25, false), FLAME("flame", 26, false),
	LAVA("lava", 27, false), FOOTSTEP("footstep", 28, false), CLOUD("cloud", 29, false), REDSTONE("reddust", 30, false),
	SNOWBALL("snowballpoof", 31, false), SNOW_SHOVEL("snowshovel", 32, false), SLIME("slime", 33, false),
	HEART("heart", 34, false), BARRIER("barrier", 35, false), ITEM_CRACK("iconcrack_", 36, false, 2),
	BLOCK_CRACK("blockcrack_", 37, false, 1), BLOCK_DUST("blockdust_", 38, false, 1), WATER_DROP("droplet", 39, false),
	ITEM_TAKE("take", 40, false), MOB_APPEARANCE("mobappearance", 41, true);

	private final String particleName;
	private final int particleID;
	private final boolean shouldIgnoreRange;
	private final int argumentCount;
	private static final Map<Integer, EnumParticleTypes> PARTICLES = Maps.newHashMap();
	private static final String[] PARTICLE_NAMES;

	private EnumParticleTypes(String particleNameIn, int particleIDIn, boolean parFlag, int argumentCountIn) {
		this.particleName = particleNameIn;
		this.particleID = particleIDIn;
		this.shouldIgnoreRange = parFlag;
		this.argumentCount = argumentCountIn;
	}

	private EnumParticleTypes(String particleNameIn, int particleIDIn, boolean parFlag) {
		this(particleNameIn, particleIDIn, parFlag, 0);
	}

	public static String[] getParticleNames() {
		return PARTICLE_NAMES;
	}

	public String getParticleName() {
		return this.particleName;
	}

	public int getParticleID() {
		return this.particleID;
	}

	public int getArgumentCount() {
		return this.argumentCount;
	}

	public boolean getShouldIgnoreRange() {
		return this.shouldIgnoreRange;
	}

	public boolean hasArguments() {
		return this.argumentCount > 0;
	}

	/**+
	 * Gets the relative EnumParticleTypes by id.
	 */
	public static EnumParticleTypes getParticleFromId(int particleId) {
		return (EnumParticleTypes) PARTICLES.get(Integer.valueOf(particleId));
	}

	static {
		ArrayList arraylist = Lists.newArrayList();

		for (EnumParticleTypes enumparticletypes : values()) {
			PARTICLES.put(Integer.valueOf(enumparticletypes.getParticleID()), enumparticletypes);
			if (!enumparticletypes.getParticleName().endsWith("_")) {
				arraylist.add(enumparticletypes.getParticleName());
			}
		}

		PARTICLE_NAMES = (String[]) arraylist.toArray(new String[arraylist.size()]);
	}
}