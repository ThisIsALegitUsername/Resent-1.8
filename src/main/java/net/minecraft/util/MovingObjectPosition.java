package net.minecraft.util;

import net.minecraft.entity.Entity;

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
public class MovingObjectPosition {
	private BlockPos blockPos;
	public MovingObjectPosition.MovingObjectType typeOfHit;
	public EnumFacing sideHit;
	public Vec3 hitVec;
	public Entity entityHit;

	public MovingObjectPosition(Vec3 hitVecIn, EnumFacing facing, BlockPos blockPosIn) {
		this(MovingObjectPosition.MovingObjectType.BLOCK, hitVecIn, facing, blockPosIn);
	}

	public MovingObjectPosition(Vec3 parVec3_1, EnumFacing facing) {
		this(MovingObjectPosition.MovingObjectType.BLOCK, parVec3_1, facing, BlockPos.ORIGIN);
	}

	public MovingObjectPosition(Entity parEntity) {
		this(parEntity, new Vec3(parEntity.posX, parEntity.posY, parEntity.posZ));
	}

	public MovingObjectPosition(MovingObjectPosition.MovingObjectType typeOfHitIn, Vec3 hitVecIn, EnumFacing sideHitIn,
			BlockPos blockPosIn) {
		this.typeOfHit = typeOfHitIn;
		this.blockPos = blockPosIn;
		this.sideHit = sideHitIn;
		this.hitVec = new Vec3(hitVecIn.xCoord, hitVecIn.yCoord, hitVecIn.zCoord);
	}

	public MovingObjectPosition(Entity entityHitIn, Vec3 hitVecIn) {
		this.typeOfHit = MovingObjectPosition.MovingObjectType.ENTITY;
		this.entityHit = entityHitIn;
		this.hitVec = hitVecIn;
	}

	public BlockPos getBlockPos() {
		return this.blockPos;
	}

	public String toString() {
		return "HitResult{type=" + this.typeOfHit + ", blockpos=" + this.blockPos + ", f=" + this.sideHit + ", pos="
				+ this.hitVec + ", entity=" + this.entityHit + '}';
	}

	public static enum MovingObjectType {
		MISS, BLOCK, ENTITY;
	}
}