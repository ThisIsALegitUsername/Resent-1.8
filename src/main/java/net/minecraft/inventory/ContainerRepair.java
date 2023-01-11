package net.minecraft.inventory;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
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
public class ContainerRepair extends Container {
	private static final Logger logger = LogManager.getLogger();
	private IInventory outputSlot;
	private IInventory inputSlots;
	private World theWorld;
	private BlockPos selfPosition;
	public int maximumCost;
	private int materialCost;
	private String repairedItemName;
	private final EntityPlayer thePlayer;

	public ContainerRepair(InventoryPlayer playerInventory, World worldIn, EntityPlayer player) {
		this(playerInventory, worldIn, BlockPos.ORIGIN, player);
	}

	public ContainerRepair(InventoryPlayer playerInventory, final World worldIn, final BlockPos blockPosIn,
			EntityPlayer player) {
		this.outputSlot = new InventoryCraftResult();
		this.inputSlots = new InventoryBasic("Repair", true, 2) {
			public void markDirty() {
				super.markDirty();
				ContainerRepair.this.onCraftMatrixChanged(this);
			}
		};
		this.selfPosition = blockPosIn;
		this.theWorld = worldIn;
		this.thePlayer = player;
		this.addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
		this.addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
		this.addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47) {
			public boolean isItemValid(ItemStack var1) {
				return false;
			}

			public boolean canTakeStack(EntityPlayer playerIn) {
				return (playerIn.capabilities.isCreativeMode
						|| playerIn.experienceLevel >= ContainerRepair.this.maximumCost)
						&& ContainerRepair.this.maximumCost > 0 && this.getHasStack();
			}

			public void onPickupFromSlot(EntityPlayer entityplayer, ItemStack var2) {
				if (!entityplayer.capabilities.isCreativeMode) {
					entityplayer.addExperienceLevel(-ContainerRepair.this.maximumCost);
				}

				ContainerRepair.this.inputSlots.setInventorySlotContents(0, (ItemStack) null);
				if (ContainerRepair.this.materialCost > 0) {
					ItemStack itemstack = ContainerRepair.this.inputSlots.getStackInSlot(1);
					if (itemstack != null && itemstack.stackSize > ContainerRepair.this.materialCost) {
						itemstack.stackSize -= ContainerRepair.this.materialCost;
						ContainerRepair.this.inputSlots.setInventorySlotContents(1, itemstack);
					} else {
						ContainerRepair.this.inputSlots.setInventorySlotContents(1, (ItemStack) null);
					}
				} else {
					ContainerRepair.this.inputSlots.setInventorySlotContents(1, (ItemStack) null);
				}

				ContainerRepair.this.maximumCost = 0;

			}
		});

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
		}

	}

	/**+
	 * Callback for when the crafting matrix is changed.
	 */
	public void onCraftMatrixChanged(IInventory iinventory) {
		super.onCraftMatrixChanged(iinventory);
		if (iinventory == this.inputSlots) {
			this.updateRepairOutput();
		}

	}

	/**+
	 * called when the Anvil Input Slot changes, calculates the new
	 * result and puts it in the output slot
	 */
	public void updateRepairOutput() {
		boolean flag = false;
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		boolean flag4 = true;
		boolean flag5 = true;
		boolean flag6 = true;
		ItemStack itemstack = this.inputSlots.getStackInSlot(0);
		this.maximumCost = 1;
		int i = 0;
		int j = 0;
		byte b0 = 0;
		if (itemstack == null) {
			this.outputSlot.setInventorySlotContents(0, (ItemStack) null);
			this.maximumCost = 0;
		} else {
			ItemStack itemstack1 = itemstack.copy();
			ItemStack itemstack2 = this.inputSlots.getStackInSlot(1);
			Map map = EnchantmentHelper.getEnchantments(itemstack1);
			boolean flag7 = false;
			j = j + itemstack.getRepairCost() + (itemstack2 == null ? 0 : itemstack2.getRepairCost());
			this.materialCost = 0;
			if (itemstack2 != null) {
				flag7 = itemstack2.getItem() == Items.enchanted_book
						&& Items.enchanted_book.getEnchantments(itemstack2).tagCount() > 0;
				if (itemstack1.isItemStackDamageable() && itemstack1.getItem().getIsRepairable(itemstack, itemstack2)) {
					int j2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
					if (j2 <= 0) {
						this.outputSlot.setInventorySlotContents(0, (ItemStack) null);
						this.maximumCost = 0;
						return;
					}

					int l2;
					for (l2 = 0; j2 > 0 && l2 < itemstack2.stackSize; ++l2) {
						int j3 = itemstack1.getItemDamage() - j2;
						itemstack1.setItemDamage(j3);
						++i;
						j2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
					}

					this.materialCost = l2;
				} else {
					if (!flag7
							&& (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.isItemStackDamageable())) {
						this.outputSlot.setInventorySlotContents(0, (ItemStack) null);
						this.maximumCost = 0;
						return;
					}

					if (itemstack1.isItemStackDamageable() && !flag7) {
						int k = itemstack.getMaxDamage() - itemstack.getItemDamage();
						int l = itemstack2.getMaxDamage() - itemstack2.getItemDamage();
						int i1 = l + itemstack1.getMaxDamage() * 12 / 100;
						int j1 = k + i1;
						int k1 = itemstack1.getMaxDamage() - j1;
						if (k1 < 0) {
							k1 = 0;
						}

						if (k1 < itemstack1.getMetadata()) {
							itemstack1.setItemDamage(k1);
							i += 2;
						}
					}

					Map map1 = EnchantmentHelper.getEnchantments(itemstack2);
					Iterator iterator1 = map1.keySet().iterator();

					while (iterator1.hasNext()) {
						int i3 = ((Integer) iterator1.next()).intValue();
						Enchantment enchantment = Enchantment.getEnchantmentById(i3);
						if (enchantment != null) {
							int k3 = map.containsKey(Integer.valueOf(i3))
									? ((Integer) map.get(Integer.valueOf(i3))).intValue()
									: 0;
							int l1 = ((Integer) map1.get(Integer.valueOf(i3))).intValue();
							int i4;
							if (k3 == l1) {
								++l1;
								i4 = l1;
							} else {
								i4 = Math.max(l1, k3);
							}

							l1 = i4;
							boolean flag8 = enchantment.canApply(itemstack);
							if (this.thePlayer.capabilities.isCreativeMode
									|| itemstack.getItem() == Items.enchanted_book) {
								flag8 = true;
							}

							Iterator iterator = map.keySet().iterator();

							while (iterator.hasNext()) {
								int i2 = ((Integer) iterator.next()).intValue();
								if (i2 != i3 && !enchantment.canApplyTogether(Enchantment.getEnchantmentById(i2))) {
									flag8 = false;
									++i;
								}
							}

							if (flag8) {
								if (l1 > enchantment.getMaxLevel()) {
									l1 = enchantment.getMaxLevel();
								}

								map.put(Integer.valueOf(i3), Integer.valueOf(l1));
								int l3 = 0;
								switch (enchantment.getWeight()) {
								case 1:
									l3 = 8;
									break;
								case 2:
									l3 = 4;
								case 3:
								case 4:
								case 6:
								case 7:
								case 8:
								case 9:
								default:
									break;
								case 5:
									l3 = 2;
									break;
								case 10:
									l3 = 1;
								}

								if (flag7) {
									l3 = Math.max(1, l3 / 2);
								}

								i += l3 * l1;
							}
						}
					}
				}
			}

			if (StringUtils.isBlank(this.repairedItemName)) {
				if (itemstack.hasDisplayName()) {
					b0 = 1;
					i += b0;
					itemstack1.clearCustomName();
				}
			} else if (!this.repairedItemName.equals(itemstack.getDisplayName())) {
				b0 = 1;
				i += b0;
				itemstack1.setStackDisplayName(this.repairedItemName);
			}

			this.maximumCost = j + i;
			if (i <= 0) {
				itemstack1 = null;
			}

			if (b0 == i && b0 > 0 && this.maximumCost >= 40) {
				this.maximumCost = 39;
			}

			if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode) {
				itemstack1 = null;
			}

			if (itemstack1 != null) {
				int k2 = itemstack1.getRepairCost();
				if (itemstack2 != null && k2 < itemstack2.getRepairCost()) {
					k2 = itemstack2.getRepairCost();
				}

				k2 = k2 * 2 + 1;
				itemstack1.setRepairCost(k2);
				EnchantmentHelper.setEnchantments(map, itemstack1);
			}

			this.outputSlot.setInventorySlotContents(0, itemstack1);
			this.detectAndSendChanges();
		}
	}

	public void onCraftGuiOpened(ICrafting icrafting) {
		super.onCraftGuiOpened(icrafting);
		icrafting.sendProgressBarUpdate(this, 0, this.maximumCost);
	}

	public void updateProgressBar(int i, int j) {
		if (i == 0) {
			this.maximumCost = j;
		}

	}

	public boolean canInteractWith(EntityPlayer entityplayer) {
		return this.theWorld.getBlockState(this.selfPosition).getBlock() != Blocks.anvil ? false
				: entityplayer.getDistanceSq((double) this.selfPosition.getX() + 0.5D,
						(double) this.selfPosition.getY() + 0.5D, (double) this.selfPosition.getZ() + 0.5D) <= 64.0D;
	}

	/**+
	 * Take a stack from the specified inventory slot.
	 */
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(i);
		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			if (i == 2) {
				if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (i != 0 && i != 1) {
				if (i >= 3 && i < 39 && !this.mergeItemStack(itemstack1, 0, 2, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(entityplayer, itemstack1);
		}

		return itemstack;
	}

	/**+
	 * used by the Anvil GUI to update the Item Name being typed by
	 * the player
	 */
	public void updateItemName(String newName) {
		this.repairedItemName = newName;
		if (this.getSlot(2).getHasStack()) {
			ItemStack itemstack = this.getSlot(2).getStack();
			if (StringUtils.isBlank(newName)) {
				itemstack.clearCustomName();
			} else {
				itemstack.setStackDisplayName(this.repairedItemName);
			}
		}

		this.updateRepairOutput();
	}
}