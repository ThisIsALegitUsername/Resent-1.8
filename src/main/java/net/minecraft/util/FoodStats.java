package net.minecraft.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;

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
public class FoodStats {
	/**+
	 * The player's food level.
	 */
	private int foodLevel = 20;
	/**+
	 * The player's food saturation.
	 */
	private float foodSaturationLevel = 5.0F;
	private float foodExhaustionLevel;
	private int foodTimer;
	private int prevFoodLevel = 20;

	/**+
	 * Add food stats.
	 */
	public void addStats(int foodLevelIn, float foodSaturationModifier) {
		this.foodLevel = Math.min(foodLevelIn + this.foodLevel, 20);
		this.foodSaturationLevel = Math.min(
				this.foodSaturationLevel + (float) foodLevelIn * foodSaturationModifier * 2.0F, (float) this.foodLevel);
	}

	/**+
	 * Add food stats.
	 */
	public void addStats(ItemFood foodItem, ItemStack parItemStack) {
		this.addStats(foodItem.getHealAmount(parItemStack), foodItem.getSaturationModifier(parItemStack));
	}

	/**+
	 * Handles the food game logic.
	 */
	public void onUpdate(EntityPlayer player) {
		EnumDifficulty enumdifficulty = player.worldObj.getDifficulty();
		this.prevFoodLevel = this.foodLevel;
		if (this.foodExhaustionLevel > 4.0F) {
			this.foodExhaustionLevel -= 4.0F;
			if (this.foodSaturationLevel > 0.0F) {
				this.foodSaturationLevel = Math.max(this.foodSaturationLevel - 1.0F, 0.0F);
			} else if (enumdifficulty != EnumDifficulty.PEACEFUL) {
				this.foodLevel = Math.max(this.foodLevel - 1, 0);
			}
		}

		if (player.worldObj.getGameRules().getBoolean("naturalRegeneration") && this.foodLevel >= 18
				&& player.shouldHeal()) {
			++this.foodTimer;
			if (this.foodTimer >= 80) {
				player.heal(1.0F);
				this.addExhaustion(3.0F);
				this.foodTimer = 0;
			}
		} else if (this.foodLevel <= 0) {
			++this.foodTimer;
			if (this.foodTimer >= 80) {
				if (player.getHealth() > 10.0F || enumdifficulty == EnumDifficulty.HARD
						|| player.getHealth() > 1.0F && enumdifficulty == EnumDifficulty.NORMAL) {
					player.attackEntityFrom(DamageSource.starve, 1.0F);
				}

				this.foodTimer = 0;
			}
		} else {
			this.foodTimer = 0;
		}

	}

	/**+
	 * Reads the food data for the player.
	 */
	public void readNBT(NBTTagCompound parNBTTagCompound) {
		if (parNBTTagCompound.hasKey("foodLevel", 99)) {
			this.foodLevel = parNBTTagCompound.getInteger("foodLevel");
			this.foodTimer = parNBTTagCompound.getInteger("foodTickTimer");
			this.foodSaturationLevel = parNBTTagCompound.getFloat("foodSaturationLevel");
			this.foodExhaustionLevel = parNBTTagCompound.getFloat("foodExhaustionLevel");
		}

	}

	/**+
	 * Writes the food data for the player.
	 */
	public void writeNBT(NBTTagCompound parNBTTagCompound) {
		parNBTTagCompound.setInteger("foodLevel", this.foodLevel);
		parNBTTagCompound.setInteger("foodTickTimer", this.foodTimer);
		parNBTTagCompound.setFloat("foodSaturationLevel", this.foodSaturationLevel);
		parNBTTagCompound.setFloat("foodExhaustionLevel", this.foodExhaustionLevel);
	}

	/**+
	 * Get the player's food level.
	 */
	public int getFoodLevel() {
		return this.foodLevel;
	}

	public int getPrevFoodLevel() {
		return this.prevFoodLevel;
	}

	/**+
	 * Get whether the player must eat food.
	 */
	public boolean needFood() {
		return this.foodLevel < 20;
	}

	/**+
	 * adds input to foodExhaustionLevel to a max of 40
	 */
	public void addExhaustion(float parFloat1) {
		this.foodExhaustionLevel = Math.min(this.foodExhaustionLevel + parFloat1, 40.0F);
	}

	/**+
	 * Get the player's food saturation level.
	 */
	public float getSaturationLevel() {
		return this.foodSaturationLevel;
	}

	public void setFoodLevel(int foodLevelIn) {
		this.foodLevel = foodLevelIn;
	}

	public void setFoodSaturationLevel(float foodSaturationLevelIn) {
		this.foodSaturationLevel = foodSaturationLevelIn;
	}
}