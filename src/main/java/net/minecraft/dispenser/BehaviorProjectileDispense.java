package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem {
	/**+
	 * Dispense the specified stack, play the dispense sound and
	 * spawn particles.
	 */
	public ItemStack dispenseStack(IBlockSource iblocksource, ItemStack itemstack) {
		World world = iblocksource.getWorld();
		IPosition iposition = BlockDispenser.getDispensePosition(iblocksource);
		EnumFacing enumfacing = BlockDispenser.getFacing(iblocksource.getBlockMetadata());
		IProjectile iprojectile = this.getProjectileEntity(world, iposition);
		iprojectile.setThrowableHeading((double) enumfacing.getFrontOffsetX(),
				(double) ((float) enumfacing.getFrontOffsetY() + 0.1F), (double) enumfacing.getFrontOffsetZ(),
				this.func_82500_b(), this.func_82498_a());
		world.spawnEntityInWorld((Entity) iprojectile);
		itemstack.splitStack(1);
		return itemstack;
	}

	/**+
	 * Play the dispense sound from the specified block.
	 */
	protected void playDispenseSound(IBlockSource iblocksource) {
		iblocksource.getWorld().playAuxSFX(1002, iblocksource.getBlockPos(), 0);
	}

	protected abstract IProjectile getProjectileEntity(World var1, IPosition var2);

	protected float func_82498_a() {
		return 6.0F;
	}

	protected float func_82500_b() {
		return 1.1F;
	}
}