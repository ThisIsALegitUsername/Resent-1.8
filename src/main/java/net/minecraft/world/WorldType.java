package net.minecraft.world;

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
public class WorldType {
	/**+
	 * List of world types.
	 */
	public static final WorldType[] worldTypes = new WorldType[16];
	/**+
	 * Default world type.
	 */
	public static final WorldType DEFAULT = (new WorldType(0, "default", 1)).setVersioned();
	/**+
	 * Flat world type.
	 */
	public static final WorldType FLAT = new WorldType(1, "flat");
	/**+
	 * Large Biome world Type.
	 */
	public static final WorldType LARGE_BIOMES = new WorldType(2, "largeBiomes");
	/**+
	 * amplified world type
	 */
	public static final WorldType AMPLIFIED = (new WorldType(3, "amplified")).setNotificationData();
	public static final WorldType CUSTOMIZED = new WorldType(4, "customized");
	/**+
	 * Default (1.1) world type.
	 */
	public static final WorldType DEFAULT_1_1 = (new WorldType(8, "default_1_1", 0)).setCanBeCreated(false);
	private final int worldTypeId;
	private final String worldType;
	private final int generatorVersion;
	private boolean canBeCreated;
	private boolean isWorldTypeVersioned;
	private boolean hasNotificationData;

	private WorldType(int id, String name) {
		this(id, name, 0);
	}

	private WorldType(int id, String name, int version) {
		this.worldType = name;
		this.generatorVersion = version;
		this.canBeCreated = true;
		this.worldTypeId = id;
		worldTypes[id] = this;
	}

	public String getWorldTypeName() {
		return this.worldType;
	}

	/**+
	 * Gets the translation key for the name of this world type.
	 */
	public String getTranslateName() {
		return "generator." + this.worldType;
	}

	public String func_151359_c() {
		return this.getTranslateName() + ".info";
	}

	/**+
	 * Returns generatorVersion.
	 */
	public int getGeneratorVersion() {
		return this.generatorVersion;
	}

	public WorldType getWorldTypeForGeneratorVersion(int version) {
		return this == DEFAULT && version == 0 ? DEFAULT_1_1 : this;
	}

	/**+
	 * Sets canBeCreated to the provided value, and returns this.
	 */
	private WorldType setCanBeCreated(boolean enable) {
		this.canBeCreated = enable;
		return this;
	}

	/**+
	 * Gets whether this WorldType can be used to generate a new
	 * world.
	 */
	public boolean getCanBeCreated() {
		return this.canBeCreated;
	}

	/**+
	 * Flags this world type as having an associated version.
	 */
	private WorldType setVersioned() {
		this.isWorldTypeVersioned = true;
		return this;
	}

	/**+
	 * Returns true if this world Type has a version associated with
	 * it.
	 */
	public boolean isVersioned() {
		return this.isWorldTypeVersioned;
	}

	public static WorldType parseWorldType(String type) {
		for (int i = 0; i < worldTypes.length; ++i) {
			if (worldTypes[i] != null && worldTypes[i].worldType.equalsIgnoreCase(type)) {
				return worldTypes[i];
			}
		}

		return null;
	}

	public int getWorldTypeID() {
		return this.worldTypeId;
	}

	/**+
	 * returns true if selecting this worldtype from the customize
	 * menu should display the generator.[worldtype].info message
	 */
	public boolean showWorldInfoNotice() {
		return this.hasNotificationData;
	}

	/**+
	 * enables the display of generator.[worldtype].info message on
	 * the customize world menu
	 */
	private WorldType setNotificationData() {
		this.hasNotificationData = true;
		return this;
	}
}