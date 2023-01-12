package net.minecraft.util;

import com.google.common.base.Predicate;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public final class EntitySelectors {
	public static final Predicate<Entity> selectAnything = new Predicate<Entity>() {
		public boolean apply(Entity entity) {
			return entity.isEntityAlive();
		}
	};
	/**+
	 * Selects only entities which are neither ridden by anything
	 * nor ride on anything
	 */
	public static final Predicate<Entity> IS_STANDALONE = new Predicate<Entity>() {
		public boolean apply(Entity entity) {
			return entity.isEntityAlive() && entity.riddenByEntity == null && entity.ridingEntity == null;
		}
	};
	public static final Predicate<Entity> selectInventories = new Predicate<Entity>() {
		public boolean apply(Entity entity) {
			return entity instanceof IInventory && entity.isEntityAlive();
		}
	};
	/**+
	 * Selects entities which are either not players or players that
	 * are not spectating
	 */
	public static final Predicate<Entity> NOT_SPECTATING = new Predicate<Entity>() {
		public boolean apply(Entity entity) {
			return !(entity instanceof EntityPlayer) || !((EntityPlayer) entity).isSpectator();
		}
	};

	public static class ArmoredMob implements Predicate<Entity> {
		private final ItemStack armor;

		public ArmoredMob(ItemStack armor) {
			this.armor = armor;
		}

		public boolean apply(Entity entity) {
			if (!entity.isEntityAlive()) {
				return false;
			} else if (!(entity instanceof EntityLivingBase)) {
				return false;
			} else {
				EntityLivingBase entitylivingbase = (EntityLivingBase) entity;
				return entitylivingbase.getEquipmentInSlot(EntityLiving.getArmorPosition(this.armor)) != null ? false
						: (entitylivingbase instanceof EntityLiving ? ((EntityLiving) entitylivingbase).canPickUpLoot()
								: (entitylivingbase instanceof EntityArmorStand ? true
										: entitylivingbase instanceof EntityPlayer));
			}
		}
	}
}