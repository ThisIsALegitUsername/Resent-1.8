package net.minecraft.world;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.border.WorldBorder;

public abstract class WorldProvider {
	public static final float[] moonPhaseFactors = new float[] { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
	protected World worldObj;
	private WorldType terrainType;
	private String generatorSettings;
	protected boolean isHellWorld;
	protected boolean hasNoSky;
	/**+
	 * Light to brightness conversion table
	 */
	protected final float[] lightBrightnessTable = new float[16];
	protected int dimensionId;
	/**+
	 * Array for sunrise/sunset colors (RGBA)
	 */
	private final float[] colorsSunriseSunset = new float[4];

	/**+
	 * associate an existing world with a World provider, and setup
	 * its lightbrightness table
	 */
	public final void registerWorld(World worldIn) {
		this.worldObj = worldIn;
		this.terrainType = worldIn.getWorldInfo().getTerrainType();
		this.generatorSettings = worldIn.getWorldInfo().getGeneratorOptions();
		this.registerWorldChunkManager();
		this.generateLightBrightnessTable();
	}

	/**+
	 * Creates the light to brightness table
	 */
	protected void generateLightBrightnessTable() {
		float f = 0.0F;

		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - (float) i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}

	}

	/**+
	 * creates a new world chunk manager for WorldProvider
	 */
	protected void registerWorldChunkManager() {

	}

	/**+
	 * Will check if the x, z position specified is alright to be
	 * set as the map spawn point
	 */
	public boolean canCoordinateBeSpawn(int x, int z) {
		return this.worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)) == Blocks.grass;
	}

	/**+
	 * Calculates the angle of sun and moon in the sky relative to a
	 * specified time (usually worldTime)
	 */
	public float calculateCelestialAngle(long parLong1, float parFloat1) {
		int i = (int) (parLong1 % 24000L);
		float f = ((float) i + parFloat1) / 24000.0F - 0.25F;
		if (f < 0.0F) {
			++f;
		}

		if (f > 1.0F) {
			--f;
		}

		f = 1.0F - (float) ((Math.cos((double) f * 3.141592653589793D) + 1.0D) / 2.0D);
		f = f + (f - f) / 3.0F;
		return f;
	}

	public int getMoonPhase(long parLong1) {
		return (int) (parLong1 / 24000L % 8L + 8L) % 8;
	}

	/**+
	 * Returns 'true' if in the "main surface world", but 'false' if
	 * in the Nether or End dimensions.
	 */
	public boolean isSurfaceWorld() {
		return true;
	}

	/**+
	 * Returns array with sunrise/sunset colors
	 */
	public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
		float f = 0.4F;
		float f1 = MathHelper.cos(celestialAngle * 3.1415927F * 2.0F) - 0.0F;
		float f2 = 0.0F;
		if (f1 >= f2 - f && f1 <= f2 + f) {
			float f3 = (f1 - f2) / f * 0.5F + 0.5F;
			float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * 3.1415927F)) * 0.99F;
			f4 = f4 * f4;
			this.colorsSunriseSunset[0] = f3 * 0.3F + 0.7F;
			this.colorsSunriseSunset[1] = f3 * f3 * 0.7F + 0.2F;
			this.colorsSunriseSunset[2] = f3 * f3 * 0.0F + 0.2F;
			this.colorsSunriseSunset[3] = f4;
			return this.colorsSunriseSunset;
		} else {
			return null;
		}
	}

	/**+
	 * Return Vec3D with biome specific fog color
	 */
	public Vec3 getFogColor(float parFloat1, float parFloat2) {
		float f = MathHelper.cos(parFloat1 * 3.1415927F * 2.0F) * 2.0F + 0.5F;
		f = MathHelper.clamp_float(f, 0.0F, 1.0F);
		float f1 = 0.7529412F;
		float f2 = 0.84705883F;
		float f3 = 1.0F;
		f1 = f1 * (f * 0.94F + 0.06F);
		f2 = f2 * (f * 0.94F + 0.06F);
		f3 = f3 * (f * 0.91F + 0.09F);
		return new Vec3((double) f1, (double) f2, (double) f3);
	}

	/**+
	 * True if the player can respawn in this dimension (true =
	 * overworld, false = nether).
	 */
	public boolean canRespawnHere() {
		return true;
	}

	public static WorldProvider getProviderForDimension(int dimension) {
		return (WorldProvider) (dimension == -1 ? new WorldProviderHell()
				: (dimension == 0 ? new WorldProviderSurface() : (dimension == 1 ? new WorldProviderEnd() : null)));
	}

	/**+
	 * the y level at which clouds are rendered.
	 */
	public float getCloudHeight() {
		return 128.0F;
	}

	public boolean isSkyColored() {
		return true;
	}

	public BlockPos getSpawnCoordinate() {
		return null;
	}

	public int getAverageGroundLevel() {
		return this.terrainType == WorldType.FLAT ? 4 : this.worldObj.func_181545_F() + 1;
	}

	/**+
	 * Returns a double value representing the Y value relative to
	 * the top of the map at which void fog is at its maximum. The
	 * default factor of 0.03125 relative to 256, for example, means
	 * the void fog will be at its maximum at (256*0.03125), or 8.
	 */
	public double getVoidFogYFactor() {
		return this.terrainType == WorldType.FLAT ? 1.0D : 0.03125D;
	}

	/**+
	 * Returns true if the given X,Z coordinate should show
	 * environmental fog.
	 */
	public boolean doesXZShowFog(int x, int z) {
		return false;
	}

	public abstract String getDimensionName();

	public abstract String getInternalNameSuffix();

	public boolean doesWaterVaporize() {
		return this.isHellWorld;
	}

	public boolean getHasNoSky() {
		return this.hasNoSky;
	}

	public float[] getLightBrightnessTable() {
		return this.lightBrightnessTable;
	}

	/**+
	 * Gets the dimension of the provider
	 */
	public int getDimensionId() {
		return this.dimensionId;
	}

	public WorldBorder getWorldBorder() {
		return new WorldBorder();
	}
}