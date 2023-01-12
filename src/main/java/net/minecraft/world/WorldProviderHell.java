package net.minecraft.world;

import net.minecraft.util.Vec3;
import net.minecraft.world.border.WorldBorder;

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
public class WorldProviderHell extends WorldProvider {
	/**+
	 * creates a new world chunk manager for WorldProvider
	 */
	public void registerWorldChunkManager() {
		this.isHellWorld = true;
		this.hasNoSky = true;
		this.dimensionId = -1;
	}

	/**+
	 * Return Vec3D with biome specific fog color
	 */
	public Vec3 getFogColor(float var1, float var2) {
		return new Vec3(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
	}

	/**+
	 * Creates the light to brightness table
	 */
	protected void generateLightBrightnessTable() {
		float f = 0.1F;

		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - (float) i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}

	}

	/**+
	 * Returns 'true' if in the "main surface world", but 'false' if
	 * in the Nether or End dimensions.
	 */
	public boolean isSurfaceWorld() {
		return false;
	}

	/**+
	 * Will check if the x, z position specified is alright to be
	 * set as the map spawn point
	 */
	public boolean canCoordinateBeSpawn(int var1, int var2) {
		return false;
	}

	/**+
	 * Calculates the angle of sun and moon in the sky relative to a
	 * specified time (usually worldTime)
	 */
	public float calculateCelestialAngle(long var1, float var3) {
		return 0.5F;
	}

	/**+
	 * True if the player can respawn in this dimension (true =
	 * overworld, false = nether).
	 */
	public boolean canRespawnHere() {
		return false;
	}

	/**+
	 * Returns true if the given X,Z coordinate should show
	 * environmental fog.
	 */
	public boolean doesXZShowFog(int var1, int var2) {
		return true;
	}

	/**+
	 * Returns the dimension's name, e.g. "The End", "Nether", or
	 * "Overworld".
	 */
	public String getDimensionName() {
		return "Nether";
	}

	public String getInternalNameSuffix() {
		return "_nether";
	}

	public WorldBorder getWorldBorder() {
		return new WorldBorder() {
			public double getCenterX() {
				return super.getCenterX() / 8.0D;
			}

			public double getCenterZ() {
				return super.getCenterZ() / 8.0D;
			}
		};
	}
}