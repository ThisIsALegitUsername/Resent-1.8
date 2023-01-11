package net.minecraft.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
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
public class ItemMonsterPlacer extends Item {
	public ItemMonsterPlacer() {
		this.setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMisc);
	}

	public String getItemStackDisplayName(ItemStack itemstack) {
		String s = ("" + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name")).trim();
		String s1 = EntityList.getStringFromID(itemstack.getMetadata());
		if (s1 != null) {
			s = s + " " + StatCollector.translateToLocal("entity." + s1 + ".name");
		}

		return s;
	}

	public int getColorFromItemStack(ItemStack itemstack, int i) {
		EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo) EntityList.entityEggs
				.get(Integer.valueOf(itemstack.getMetadata()));
		return entitylist$entityegginfo != null
				? (i == 0 ? entitylist$entityegginfo.primaryColor : entitylist$entityegginfo.secondaryColor)
				: 16777215;
	}

	/**+
	 * Called when a Block is right-clicked with this Item
	 */
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, BlockPos blockpos,
			EnumFacing enumfacing, float var6, float var7, float var8) {
		return true;
	}

	/**+
	 * Spawns the creature specified by the egg's type in the
	 * location specified by the last three parameters. Parameters:
	 * world, entityID, x, y, z.
	 */
	public static Entity spawnCreature(World worldIn, int entityID, double x, double y, double z) {
		if (!EntityList.entityEggs.containsKey(Integer.valueOf(entityID))) {
			return null;
		} else {
			Entity entity = null;

			for (int i = 0; i < 1; ++i) {
				entity = EntityList.createEntityByID(entityID, worldIn);
				if (entity instanceof EntityLivingBase) {
					EntityLiving entityliving = (EntityLiving) entity;
					entity.setLocationAndAngles(x, y, z,
							MathHelper.wrapAngleTo180_float(worldIn.rand.nextFloat() * 360.0F), 0.0F);
					entityliving.rotationYawHead = entityliving.rotationYaw;
					entityliving.renderYawOffset = entityliving.rotationYaw;
					entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)),
							(IEntityLivingData) null);
					worldIn.spawnEntityInWorld(entity);
					entityliving.playLivingSound();
				}
			}

			return entity;
		}
	}

	/**+
	 * returns a list of items with the same ID, but different meta
	 * (eg: dye returns 16 items)
	 */
	public void getSubItems(Item item, CreativeTabs var2, List<ItemStack> list) {
		for (EntityList.EntityEggInfo entitylist$entityegginfo : EntityList.entityEggs.values()) {
			list.add(new ItemStack(item, 1, entitylist$entityegginfo.spawnedID));
		}

	}
}