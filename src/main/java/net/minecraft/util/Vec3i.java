package net.minecraft.util;

import com.google.common.base.Objects;

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
public class Vec3i implements Comparable<Vec3i> {
	/**+
	 * The Null vector constant (0, 0, 0)
	 */
	public static final Vec3i NULL_VECTOR = new Vec3i(0, 0, 0);
	public int x;
	public int y;
	public int z;

	public Vec3i(int xIn, int yIn, int zIn) {
		this.x = xIn;
		this.y = yIn;
		this.z = zIn;
	}

	public Vec3i(double xIn, double yIn, double zIn) {
		this(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof Vec3i)) {
			return false;
		} else {
			Vec3i vec3i = (Vec3i) object;
			return this.getX() != vec3i.getX() ? false
					: (this.getY() != vec3i.getY() ? false : this.getZ() == vec3i.getZ());
		}
	}

	public int hashCode() {
		return (this.getY() + this.getZ() * 31) * 31 + this.getX();
	}

	public int compareTo(Vec3i vec3i) {
		return this.getY() == vec3i.getY()
				? (this.getZ() == vec3i.getZ() ? this.getX() - vec3i.getX() : this.getZ() - vec3i.getZ())
				: this.getY() - vec3i.getY();
	}

	/**+
	 * Get the X coordinate
	 */
	public int getX() {
		return this.x;
	}

	/**+
	 * Get the Y coordinate
	 */
	public int getY() {
		return this.y;
	}

	/**+
	 * Get the Z coordinate
	 */
	public int getZ() {
		return this.z;
	}

	/**+
	 * Calculate the cross product of this and the given Vector
	 */
	public Vec3i crossProduct(Vec3i vec3i) {
		return new Vec3i(this.getY() * vec3i.getZ() - this.getZ() * vec3i.getY(),
				this.getZ() * vec3i.getX() - this.getX() * vec3i.getZ(),
				this.getX() * vec3i.getY() - this.getY() * vec3i.getX());
	}

	/**+
	 * Calculate squared distance to the given coordinates
	 */
	public double distanceSq(double toX, double toY, double toZ) {
		double d0 = (double) this.getX() - toX;
		double d1 = (double) this.getY() - toY;
		double d2 = (double) this.getZ() - toZ;
		return d0 * d0 + d1 * d1 + d2 * d2;
	}

	/**+
	 * Compute square of distance from point x, y, z to center of
	 * this Block
	 */
	public double distanceSqToCenter(double xIn, double yIn, double zIn) {
		double d0 = (double) this.getX() + 0.5D - xIn;
		double d1 = (double) this.getY() + 0.5D - yIn;
		double d2 = (double) this.getZ() + 0.5D - zIn;
		return d0 * d0 + d1 * d1 + d2 * d2;
	}

	/**+
	 * Calculate squared distance to the given coordinates
	 */
	public double distanceSq(Vec3i to) {
		return this.distanceSq((double) to.getX(), (double) to.getY(), (double) to.getZ());
	}

	public String toString() {
		return Objects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ())
				.toString();
	}
}