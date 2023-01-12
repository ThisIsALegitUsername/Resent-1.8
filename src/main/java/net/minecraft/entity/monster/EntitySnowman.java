package net.minecraft.entity.monster;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

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
public class EntitySnowman extends EntityGolem implements IRangedAttackMob {
	public EntitySnowman(World worldIn) {
		super(worldIn);
		this.setSize(0.7F, 1.9F);
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
	}

	protected Item getDropItem() {
		return Items.snowball;
	}

	/**+
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean var1, int var2) {
		int i = this.rand.nextInt(16);

		for (int j = 0; j < i; ++j) {
			this.dropItem(Items.snowball, 1);
		}

	}

	/**+
	 * Attack the specified entity using a ranged attack.
	 */
	public void attackEntityWithRangedAttack(EntityLivingBase parEntityLivingBase, float parFloat1) {
		EntitySnowball entitysnowball = new EntitySnowball(this.worldObj, this);
		double d0 = parEntityLivingBase.posY + (double) parEntityLivingBase.getEyeHeight() - 1.100000023841858D;
		double d1 = parEntityLivingBase.posX - this.posX;
		double d2 = d0 - entitysnowball.posY;
		double d3 = parEntityLivingBase.posZ - this.posZ;
		float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3) * 0.2F;
		entitysnowball.setThrowableHeading(d1, d2 + (double) f, d3, 1.6F, 12.0F);
		this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.worldObj.spawnEntityInWorld(entitysnowball);
	}

	public float getEyeHeight() {
		return 1.7F;
	}
}