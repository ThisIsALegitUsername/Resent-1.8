package net.minecraft.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumChatFormatting;
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
public class ItemPotion extends Item {
	private Map<Integer, List<PotionEffect>> effectCache = Maps.newHashMap();
	private static final Map<List<PotionEffect>, Integer> SUB_ITEMS_CACHE = Maps.newLinkedHashMap();

	public ItemPotion() {
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabBrewing);
	}

	/**+
	 * Returns a list of effects for the specified potion damage
	 * value.
	 */
	public List<PotionEffect> getEffects(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("CustomPotionEffects", 9)) {
			ArrayList arraylist = Lists.newArrayList();
			NBTTagList nbttaglist = stack.getTagCompound().getTagList("CustomPotionEffects", 10);

			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
				if (potioneffect != null) {
					arraylist.add(potioneffect);
				}
			}

			return arraylist;
		} else {
			List list = (List) this.effectCache.get(Integer.valueOf(stack.getMetadata()));
			if (list == null) {
				list = PotionHelper.getPotionEffects(stack.getMetadata(), false);
				this.effectCache.put(Integer.valueOf(stack.getMetadata()), list);
			}

			return list;
		}
	}

	/**+
	 * Returns a list of effects for the specified potion damage
	 * value.
	 */
	public List<PotionEffect> getEffects(int meta) {
		List list = (List) this.effectCache.get(Integer.valueOf(meta));
		if (list == null) {
			list = PotionHelper.getPotionEffects(meta, false);
			this.effectCache.put(Integer.valueOf(meta), list);
		}

		return list;
	}

	/**+
	 * Called when the player finishes using this Item (E.g.
	 * finishes eating.). Not called when the player stops using the
	 * Item before the action is complete.
	 */
	public ItemStack onItemUseFinish(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (!entityplayer.capabilities.isCreativeMode) {
			--itemstack.stackSize;
		}

		entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
		if (!entityplayer.capabilities.isCreativeMode) {
			if (itemstack.stackSize <= 0) {
				return new ItemStack(Items.glass_bottle);
			}

			entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.glass_bottle));
		}

		return itemstack;
	}

	/**+
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack var1) {
		return 32;
	}

	/**+
	 * returns the action that specifies what animation to play when
	 * the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack var1) {
		return EnumAction.DRINK;
	}

	/**+
	 * Called whenever this item is equipped and the right mouse
	 * button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
		if (isSplash(itemstack.getMetadata())) {
			if (!entityplayer.capabilities.isCreativeMode) {
				--itemstack.stackSize;
			}

			world.playSoundAtEntity(entityplayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			entityplayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
			return itemstack;
		} else {
			entityplayer.setItemInUse(itemstack, this.getMaxItemUseDuration(itemstack));
			return itemstack;
		}
	}

	/**+
	 * returns wether or not a potion is a throwable splash potion
	 * based on damage value
	 */
	public static boolean isSplash(int meta) {
		return (meta & 16384) != 0;
	}

	public int getColorFromDamage(int meta) {
		return PotionHelper.getLiquidColor(meta, false);
	}

	public int getColorFromItemStack(ItemStack itemstack, int i) {
		return i > 0 ? 16777215 : this.getColorFromDamage(itemstack.getMetadata());
	}

	public boolean isEffectInstant(int meta) {
		List list = this.getEffects(meta);
		if (list != null && !list.isEmpty()) {
			for (PotionEffect potioneffect : (List<PotionEffect>) list) {
				if (Potion.potionTypes[potioneffect.getPotionID()].isInstant()) {
					return true;
				}
			}

			return false;
		} else {
			return false;
		}
	}

	public String getItemStackDisplayName(ItemStack stack) {
		if (stack.getMetadata() == 0) {
			return StatCollector.translateToLocal("item.emptyPotion.name").trim();
		} else {
			String s = "";
			if (isSplash(stack.getMetadata())) {
				s = StatCollector.translateToLocal("potion.prefix.grenade").trim() + " ";
			}

			List list = Items.potionitem.getEffects(stack);
			if (list != null && !list.isEmpty()) {
				String s2 = ((PotionEffect) list.get(0)).getEffectName();
				s2 = s2 + ".postfix";
				return s + StatCollector.translateToLocal(s2).trim();
			} else {
				String s1 = PotionHelper.getPotionPrefix(stack.getMetadata());
				return StatCollector.translateToLocal(s1).trim() + " " + super.getItemStackDisplayName(stack);
			}
		}
	}

	/**+
	 * allows items to add custom lines of information to the
	 * mouseover description
	 */
	public void addInformation(ItemStack itemstack, EntityPlayer var2, List<String> list, boolean var4) {
		if (itemstack.getMetadata() != 0) {
			List list1 = Items.potionitem.getEffects(itemstack);
			HashMultimap hashmultimap = HashMultimap.create();
			if (list1 != null && !list1.isEmpty()) {
				for (PotionEffect potioneffect : (List<PotionEffect>) list1) {
					String s1 = StatCollector.translateToLocal(potioneffect.getEffectName()).trim();
					Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
					Map map = potion.getAttributeModifierMap();
					if (map != null && map.size() > 0) {
						for (Entry entry : (Set<Entry>) map.entrySet()) {
							AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();
							AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(),
									potion.getAttributeModifierAmount(potioneffect.getAmplifier(), attributemodifier),
									attributemodifier.getOperation());
							hashmultimap.put(((IAttribute) entry.getKey()).getAttributeUnlocalizedName(),
									attributemodifier1);
						}
					}

					if (potioneffect.getAmplifier() > 0) {
						s1 = s1 + " " + StatCollector.translateToLocal("potion.potency." + potioneffect.getAmplifier())
								.trim();
					}

					if (potioneffect.getDuration() > 20) {
						s1 = s1 + " (" + Potion.getDurationString(potioneffect) + ")";
					}

					if (potion.isBadEffect()) {
						list.add(EnumChatFormatting.RED + s1);
					} else {
						list.add(EnumChatFormatting.GRAY + s1);
					}
				}
			} else {
				String s = StatCollector.translateToLocal("potion.empty").trim();
				list.add(EnumChatFormatting.GRAY + s);
			}

			if (!hashmultimap.isEmpty()) {
				list.add("");
				list.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("potion.effects.whenDrank"));

				for (Entry entry1 : (Set<Entry>) hashmultimap.entries()) {
					AttributeModifier attributemodifier2 = (AttributeModifier) entry1.getValue();
					double d0 = attributemodifier2.getAmount();
					double d1;
					if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2) {
						d1 = attributemodifier2.getAmount();
					} else {
						d1 = attributemodifier2.getAmount() * 100.0D;
					}

					if (d0 > 0.0D) {
						list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted(
								"attribute.modifier.plus." + attributemodifier2.getOperation(),
								new Object[] { ItemStack.DECIMALFORMAT.format(d1), StatCollector
										.translateToLocal("attribute.name." + (String) entry1.getKey()) }));
					} else if (d0 < 0.0D) {
						d1 = d1 * -1.0D;
						list.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
								"attribute.modifier.take." + attributemodifier2.getOperation(),
								new Object[] { ItemStack.DECIMALFORMAT.format(d1), StatCollector
										.translateToLocal("attribute.name." + (String) entry1.getKey()) }));
					}
				}
			}

		}
	}

	public boolean hasEffect(ItemStack stack) {
		List list = this.getEffects(stack);
		return list != null && !list.isEmpty();
	}

	/**+
	 * returns a list of items with the same ID, but different meta
	 * (eg: dye returns 16 items)
	 */
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		super.getSubItems(itemIn, tab, subItems);
		if (SUB_ITEMS_CACHE.isEmpty()) {
			for (int i = 0; i <= 15; ++i) {
				for (int j = 0; j <= 1; ++j) {
					int k;
					if (j == 0) {
						k = i | 8192;
					} else {
						k = i | 16384;
					}

					for (int l = 0; l <= 2; ++l) {
						int i1 = k;
						if (l != 0) {
							if (l == 1) {
								i1 = k | 32;
							} else if (l == 2) {
								i1 = k | 64;
							}
						}

						List list = PotionHelper.getPotionEffects(i1, false);
						if (list != null && !list.isEmpty()) {
							SUB_ITEMS_CACHE.put(list, Integer.valueOf(i1));
						}
					}
				}
			}
		}

		Iterator iterator = SUB_ITEMS_CACHE.values().iterator();

		while (iterator.hasNext()) {
			int j1 = ((Integer) iterator.next()).intValue();
			subItems.add(new ItemStack(itemIn, 1, j1));
		}

	}
}