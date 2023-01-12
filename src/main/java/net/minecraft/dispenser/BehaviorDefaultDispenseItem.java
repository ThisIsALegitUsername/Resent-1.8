package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
public class BehaviorDefaultDispenseItem implements IBehaviorDispenseItem {
	/**+
	 * Dispenses the specified ItemStack from a dispenser.
	 */
	public final ItemStack dispense(IBlockSource source, ItemStack stack) {
		ItemStack itemstack = this.dispenseStack(source, stack);
		this.playDispenseSound(source);
		this.spawnDispenseParticles(source, BlockDispenser.getFacing(source.getBlockMetadata()));
		return itemstack;
	}

	/**+
	 * Dispense the specified stack, play the dispense sound and
	 * spawn particles.
	 */
	protected ItemStack dispenseStack(IBlockSource iblocksource, ItemStack itemstack) {
		EnumFacing enumfacing = BlockDispenser.getFacing(iblocksource.getBlockMetadata());
		IPosition iposition = BlockDispenser.getDispensePosition(iblocksource);
		ItemStack itemstack1 = itemstack.splitStack(1);
		doDispense(iblocksource.getWorld(), itemstack1, 6, enumfacing, iposition);
		return itemstack;
	}

	public static void doDispense(World worldIn, ItemStack stack, int speed, EnumFacing facing, IPosition position) {
		double d0 = position.getX();
		double d1 = position.getY();
		double d2 = position.getZ();
		if (facing.getAxis() == EnumFacing.Axis.Y) {
			d1 = d1 - 0.125D;
		} else {
			d1 = d1 - 0.15625D;
		}

		EntityItem entityitem = new EntityItem(worldIn, d0, d1, d2, stack);
		double d3 = worldIn.rand.nextDouble() * 0.1D + 0.2D;
		entityitem.motionX = (double) facing.getFrontOffsetX() * d3;
		entityitem.motionY = 0.20000000298023224D;
		entityitem.motionZ = (double) facing.getFrontOffsetZ() * d3;
		entityitem.motionX += worldIn.rand.nextGaussian() * 0.007499999832361937D * (double) speed;
		entityitem.motionY += worldIn.rand.nextGaussian() * 0.007499999832361937D * (double) speed;
		entityitem.motionZ += worldIn.rand.nextGaussian() * 0.007499999832361937D * (double) speed;
		worldIn.spawnEntityInWorld(entityitem);
	}

	/**+
	 * Play the dispense sound from the specified block.
	 */
	protected void playDispenseSound(IBlockSource iblocksource) {
		iblocksource.getWorld().playAuxSFX(1000, iblocksource.getBlockPos(), 0);
	}

	/**+
	 * Order clients to display dispense particles from the
	 * specified block and facing.
	 */
	protected void spawnDispenseParticles(IBlockSource source, EnumFacing facingIn) {
		source.getWorld().playAuxSFX(2000, source.getBlockPos(), this.func_82488_a(facingIn));
	}

	private int func_82488_a(EnumFacing facingIn) {
		return facingIn.getFrontOffsetX() + 1 + (facingIn.getFrontOffsetZ() + 1) * 3;
	}
}