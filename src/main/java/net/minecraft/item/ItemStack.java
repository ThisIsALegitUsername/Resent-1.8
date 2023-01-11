package net.minecraft.item;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.lax1dude.eaglercraft.v1_8.HString;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public final class ItemStack {
	public static final DecimalFormat DECIMALFORMAT = new DecimalFormat("#.###");
	public int stackSize;
	public int animationsToGo;
	private Item item;
	private NBTTagCompound stackTagCompound;
	private int itemDamage;
	private EntityItemFrame itemFrame;
	private Block canDestroyCacheBlock;
	private boolean canDestroyCacheResult;
	private Block canPlaceOnCacheBlock;
	private boolean canPlaceOnCacheResult;

	public ItemStack(Block blockIn) {
		this((Block) blockIn, 1);
	}

	public ItemStack(Block blockIn, int amount) {
		this((Block) blockIn, amount, 0);
	}

	public ItemStack(Block blockIn, int amount, int meta) {
		this(Item.getItemFromBlock(blockIn), amount, meta);
	}

	public ItemStack(Item itemIn) {
		this((Item) itemIn, 1);
	}

	public ItemStack(Item itemIn, int amount) {
		this((Item) itemIn, amount, 0);
	}

	public ItemStack(Item itemIn, int amount, int meta) {
		this.canDestroyCacheBlock = null;
		this.canDestroyCacheResult = false;
		this.canPlaceOnCacheBlock = null;
		this.canPlaceOnCacheResult = false;
		this.item = itemIn;
		this.stackSize = amount;
		this.itemDamage = meta;
		if (this.itemDamage < 0) {
			this.itemDamage = 0;
		}

	}

	public static ItemStack loadItemStackFromNBT(NBTTagCompound nbt) {
		ItemStack itemstack = new ItemStack();
		itemstack.readFromNBT(nbt);
		return itemstack.getItem() != null ? itemstack : null;
	}

	private ItemStack() {
		this.canDestroyCacheBlock = null;
		this.canDestroyCacheResult = false;
		this.canPlaceOnCacheBlock = null;
		this.canPlaceOnCacheResult = false;
	}

	/**+
	 * Splits off a stack of the given amount of this stack and
	 * reduces this stack by the amount.
	 */
	public ItemStack splitStack(int amount) {
		ItemStack itemstack = new ItemStack(this.item, amount, this.itemDamage);
		if (this.stackTagCompound != null) {
			itemstack.stackTagCompound = (NBTTagCompound) this.stackTagCompound.copy();
		}

		this.stackSize -= amount;
		return itemstack;
	}

	/**+
	 * Returns the object corresponding to the stack.
	 */
	public Item getItem() {
		return this.item;
	}

	/**+
	 * Called when the player uses this ItemStack on a Block
	 * (right-click). Places blocks, etc. (Legacy name:
	 * tryPlaceItemIntoWorld)
	 */
	public boolean onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ) {
		boolean flag = this.getItem().onItemUse(this, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
		if (flag) {
			playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
		}

		return flag;
	}

	public float getStrVsBlock(Block blockIn) {
		return this.getItem().getStrVsBlock(this, blockIn);
	}

	/**+
	 * Called whenever this item stack is equipped and right
	 * clicked. Returns the new item stack to put in the position
	 * where this item is. Args: world, player
	 */
	public ItemStack useItemRightClick(World worldIn, EntityPlayer playerIn) {
		return this.getItem().onItemRightClick(this, worldIn, playerIn);
	}

	/**+
	 * Called when the item in use count reach 0, e.g. item food
	 * eaten. Return the new ItemStack. Args : world, entity
	 */
	public ItemStack onItemUseFinish(World worldIn, EntityPlayer playerIn) {
		return this.getItem().onItemUseFinish(this, worldIn, playerIn);
	}

	/**+
	 * Write the stack fields to a NBT object. Return the new NBT
	 * object.
	 */
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		ResourceLocation resourcelocation = (ResourceLocation) Item.itemRegistry.getNameForObject(this.item);
		nbt.setString("id", resourcelocation == null ? "minecraft:air" : resourcelocation.toString());
		nbt.setByte("Count", (byte) this.stackSize);
		nbt.setShort("Damage", (short) this.itemDamage);
		if (this.stackTagCompound != null) {
			nbt.setTag("tag", this.stackTagCompound);
		}

		return nbt;
	}

	/**+
	 * Read the stack fields from a NBT object.
	 */
	public void readFromNBT(NBTTagCompound nbt) {
		if (nbt.hasKey("id", 8)) {
			this.item = Item.getByNameOrId(nbt.getString("id"));
		} else {
			this.item = Item.getItemById(nbt.getShort("id"));
		}

		this.stackSize = nbt.getByte("Count");
		this.itemDamage = nbt.getShort("Damage");
		if (this.itemDamage < 0) {
			this.itemDamage = 0;
		}

		if (nbt.hasKey("tag", 10)) {
			this.stackTagCompound = nbt.getCompoundTag("tag");
			if (this.item != null) {
				this.item.updateItemStackNBT(this.stackTagCompound);
			}
		}

	}

	/**+
	 * Returns maximum size of the stack.
	 */
	public int getMaxStackSize() {
		return this.getItem().getItemStackLimit();
	}

	/**+
	 * Returns true if the ItemStack can hold 2 or more units of the
	 * item.
	 */
	public boolean isStackable() {
		return this.getMaxStackSize() > 1 && (!this.isItemStackDamageable() || !this.isItemDamaged());
	}

	/**+
	 * true if this itemStack is damageable
	 */
	public boolean isItemStackDamageable() {
		return this.item == null ? false
				: (this.item.getMaxDamage() <= 0 ? false
						: !this.hasTagCompound() || !this.getTagCompound().getBoolean("Unbreakable"));
	}

	public boolean getHasSubtypes() {
		return this.item.getHasSubtypes();
	}

	/**+
	 * returns true when a damageable item is damaged
	 */
	public boolean isItemDamaged() {
		return this.isItemStackDamageable() && this.itemDamage > 0;
	}

	public int getItemDamage() {
		return this.itemDamage;
	}

	public int getMetadata() {
		return this.itemDamage;
	}

	public void setItemDamage(int meta) {
		this.itemDamage = meta;
		if (this.itemDamage < 0) {
			this.itemDamage = 0;
		}

	}

	/**+
	 * Returns the max damage an item in the stack can take.
	 */
	public int getMaxDamage() {
		return this.item.getMaxDamage();
	}

	/**+
	 * Attempts to damage the ItemStack with par1 amount of damage,
	 * If the ItemStack has the Unbreaking enchantment there is a
	 * chance for each point of damage to be negated. Returns true
	 * if it takes more damage than getMaxDamage(). Returns false
	 * otherwise or if the ItemStack can't be damaged or if all
	 * points of damage are negated.
	 */
	public boolean attemptDamageItem(int amount, EaglercraftRandom rand) {
		if (!this.isItemStackDamageable()) {
			return false;
		} else {
			if (amount > 0) {
				int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, this);
				int j = 0;

				for (int k = 0; i > 0 && k < amount; ++k) {
					if (EnchantmentDurability.negateDamage(this, i, rand)) {
						++j;
					}
				}

				amount -= j;
				if (amount <= 0) {
					return false;
				}
			}

			this.itemDamage += amount;
			return this.itemDamage > this.getMaxDamage();
		}
	}

	/**+
	 * Damages the item in the ItemStack
	 */
	public void damageItem(int amount, EntityLivingBase entityIn) {
		if (!(entityIn instanceof EntityPlayer) || !((EntityPlayer) entityIn).capabilities.isCreativeMode) {
			if (this.isItemStackDamageable()) {
				if (this.attemptDamageItem(amount, entityIn.getRNG())) {
					entityIn.renderBrokenItemStack(this);
					--this.stackSize;
					if (entityIn instanceof EntityPlayer) {
						EntityPlayer entityplayer = (EntityPlayer) entityIn;
						entityplayer.triggerAchievement(StatList.objectBreakStats[Item.getIdFromItem(this.item)]);
						if (this.stackSize == 0 && this.getItem() instanceof ItemBow) {
							entityplayer.destroyCurrentEquippedItem();
						}
					}

					if (this.stackSize < 0) {
						this.stackSize = 0;
					}

					this.itemDamage = 0;
				}

			}
		}
	}

	/**+
	 * Calls the corresponding fct in di
	 */
	public void hitEntity(EntityLivingBase entityIn, EntityPlayer playerIn) {
		boolean flag = this.item.hitEntity(this, entityIn, playerIn);
		if (flag) {
			playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
		}

	}

	/**+
	 * Called when a Block is destroyed using this ItemStack
	 */
	public void onBlockDestroyed(World worldIn, Block blockIn, BlockPos pos, EntityPlayer playerIn) {
		boolean flag = this.item.onBlockDestroyed(this, worldIn, blockIn, pos, playerIn);
		if (flag) {
			playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this.item)]);
		}

	}

	/**+
	 * Check whether the given Block can be harvested using this
	 * ItemStack.
	 */
	public boolean canHarvestBlock(Block blockIn) {
		return this.item.canHarvestBlock(blockIn);
	}

	public boolean interactWithEntity(EntityPlayer playerIn, EntityLivingBase entityIn) {
		return this.item.itemInteractionForEntity(this, playerIn, entityIn);
	}

	/**+
	 * Returns a new stack with the same properties.
	 */
	public ItemStack copy() {
		ItemStack itemstack = new ItemStack(this.item, this.stackSize, this.itemDamage);
		if (this.stackTagCompound != null) {
			itemstack.stackTagCompound = (NBTTagCompound) this.stackTagCompound.copy();
		}

		return itemstack;
	}

	public static boolean areItemStackTagsEqual(ItemStack stackA, ItemStack stackB) {
		return stackA == null
				&& stackB == null
						? true
						: (stackA != null && stackB != null
								? (stackA.stackTagCompound == null && stackB.stackTagCompound != null ? false
										: stackA.stackTagCompound == null
												|| stackA.stackTagCompound.equals(stackB.stackTagCompound))
								: false);
	}

	/**+
	 * compares ItemStack argument1 with ItemStack argument2;
	 * returns true if both ItemStacks are equal
	 */
	public static boolean areItemStacksEqual(ItemStack stackA, ItemStack stackB) {
		return stackA == null && stackB == null ? true
				: (stackA != null && stackB != null ? stackA.isItemStackEqual(stackB) : false);
	}

	/**+
	 * compares ItemStack argument to the instance ItemStack;
	 * returns true if both ItemStacks are equal
	 */
	private boolean isItemStackEqual(ItemStack other) {
		return this.stackSize != other.stackSize ? false
				: (this.item != other.item ? false
						: (this.itemDamage != other.itemDamage ? false
								: (this.stackTagCompound == null && other.stackTagCompound != null ? false
										: this.stackTagCompound == null
												|| this.stackTagCompound.equals(other.stackTagCompound))));
	}

	/**+
	 * Compares Item and damage value of the two stacks
	 */
	public static boolean areItemsEqual(ItemStack stackA, ItemStack stackB) {
		return stackA == null && stackB == null ? true
				: (stackA != null && stackB != null ? stackA.isItemEqual(stackB) : false);
	}

	/**+
	 * compares ItemStack argument to the instance ItemStack;
	 * returns true if the Items contained in both ItemStacks are
	 * equal
	 */
	public boolean isItemEqual(ItemStack other) {
		return other != null && this.item == other.item && this.itemDamage == other.itemDamage;
	}

	public String getUnlocalizedName() {
		return this.item.getUnlocalizedName(this);
	}

	/**+
	 * Creates a copy of a ItemStack, a null parameters will return
	 * a null.
	 */
	public static ItemStack copyItemStack(ItemStack stack) {
		return stack == null ? null : stack.copy();
	}

	public String toString() {
		return this.stackSize + "x" + this.item.getUnlocalizedName() + "@" + this.itemDamage;
	}

	/**+
	 * Called each tick as long the ItemStack in on player
	 * inventory. Used to progress the pickup animation and update
	 * maps.
	 */
	public void updateAnimation(World worldIn, Entity entityIn, int inventorySlot, boolean isCurrentItem) {
		if (this.animationsToGo > 0) {
			--this.animationsToGo;
		}

		this.item.onUpdate(this, worldIn, entityIn, inventorySlot, isCurrentItem);
	}

	public void onCrafting(World worldIn, EntityPlayer playerIn, int amount) {
		playerIn.addStat(StatList.objectCraftStats[Item.getIdFromItem(this.item)], amount);
		this.item.onCreated(this, worldIn, playerIn);
	}

	public boolean getIsItemStackEqual(ItemStack parItemStack) {
		return this.isItemStackEqual(parItemStack);
	}

	public int getMaxItemUseDuration() {
		return this.getItem().getMaxItemUseDuration(this);
	}

	public EnumAction getItemUseAction() {
		return this.getItem().getItemUseAction(this);
	}

	/**+
	 * Called when the player releases the use item button. Args:
	 * world, entityplayer, itemInUseCount
	 */
	public void onPlayerStoppedUsing(World worldIn, EntityPlayer playerIn, int timeLeft) {
		this.getItem().onPlayerStoppedUsing(this, worldIn, playerIn, timeLeft);
	}

	/**+
	 * Returns true if the ItemStack has an NBTTagCompound.
	 * Currently used to store enchantments.
	 */
	public boolean hasTagCompound() {
		return this.stackTagCompound != null;
	}

	/**+
	 * Returns the NBTTagCompound of the ItemStack.
	 */
	public NBTTagCompound getTagCompound() {
		return this.stackTagCompound;
	}

	/**+
	 * Get an NBTTagCompound from this stack's NBT data.
	 */
	public NBTTagCompound getSubCompound(String key, boolean create) {
		if (this.stackTagCompound != null && this.stackTagCompound.hasKey(key, 10)) {
			return this.stackTagCompound.getCompoundTag(key);
		} else if (create) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			this.setTagInfo(key, nbttagcompound);
			return nbttagcompound;
		} else {
			return null;
		}
	}

	public NBTTagList getEnchantmentTagList() {
		return this.stackTagCompound == null ? null : this.stackTagCompound.getTagList("ench", 10);
	}

	/**+
	 * Assigns a NBTTagCompound to the ItemStack, minecraft
	 * validates that only non-stackable items can have it.
	 */
	public void setTagCompound(NBTTagCompound nbt) {
		this.stackTagCompound = nbt;
	}

	/**+
	 * returns the display name of the itemstack
	 */
	public String getDisplayName() {
		String s = this.getItem().getItemStackDisplayName(this);
		if (this.stackTagCompound != null && this.stackTagCompound.hasKey("display", 10)) {
			NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
			if (nbttagcompound.hasKey("Name", 8)) {
				s = nbttagcompound.getString("Name");
			}
		}

		return s;
	}

	public ItemStack setStackDisplayName(String displayName) {
		if (this.stackTagCompound == null) {
			this.stackTagCompound = new NBTTagCompound();
		}

		if (!this.stackTagCompound.hasKey("display", 10)) {
			this.stackTagCompound.setTag("display", new NBTTagCompound());
		}

		this.stackTagCompound.getCompoundTag("display").setString("Name", displayName);
		return this;
	}

	/**+
	 * Clear any custom name set for this ItemStack
	 */
	public void clearCustomName() {
		if (this.stackTagCompound != null) {
			if (this.stackTagCompound.hasKey("display", 10)) {
				NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
				nbttagcompound.removeTag("Name");
				if (nbttagcompound.hasNoTags()) {
					this.stackTagCompound.removeTag("display");
					if (this.stackTagCompound.hasNoTags()) {
						this.setTagCompound((NBTTagCompound) null);
					}
				}

			}
		}
	}

	/**+
	 * Returns true if the itemstack has a display name
	 */
	public boolean hasDisplayName() {
		return this.stackTagCompound == null ? false
				: (!this.stackTagCompound.hasKey("display", 10) ? false
						: this.stackTagCompound.getCompoundTag("display").hasKey("Name", 8));
	}

	/**+
	 * Return a list of strings containing information about the
	 * item
	 */
	public List<String> getTooltip(EntityPlayer playerIn, boolean advanced) {
		ArrayList arraylist = Lists.newArrayList();
		String s = this.getDisplayName();
		if (this.hasDisplayName()) {
			s = EnumChatFormatting.ITALIC + s;
		}

		s = s + EnumChatFormatting.RESET;
		if (advanced) {
			String s1 = "";
			if (s.length() > 0) {
				s = s + " (";
				s1 = ")";
			}

			int i = Item.getIdFromItem(this.item);
			if (this.getHasSubtypes()) {
				s = s + HString.format("#%04d/%d%s",
						new Object[] { Integer.valueOf(i), Integer.valueOf(this.itemDamage), s1 });
			} else {
				s = s + HString.format("#%04d%s", new Object[] { Integer.valueOf(i), s1 });
			}
		} else if (!this.hasDisplayName() && this.item == Items.filled_map) {
			s = s + " #" + this.itemDamage;
		}

		arraylist.add(s);
		int k = 0;
		if (this.hasTagCompound() && this.stackTagCompound.hasKey("HideFlags", 99)) {
			k = this.stackTagCompound.getInteger("HideFlags");
		}

		if ((k & 32) == 0) {
			this.item.addInformation(this, playerIn, arraylist, advanced);
		}

		if (this.hasTagCompound()) {
			if ((k & 1) == 0) {
				NBTTagList nbttaglist = this.getEnchantmentTagList();
				if (nbttaglist != null) {
					for (int j = 0; j < nbttaglist.tagCount(); ++j) {
						short short1 = nbttaglist.getCompoundTagAt(j).getShort("id");
						short short2 = nbttaglist.getCompoundTagAt(j).getShort("lvl");
						if (Enchantment.getEnchantmentById(short1) != null) {
							arraylist.add(Enchantment.getEnchantmentById(short1).getTranslatedName(short2));
						}
					}
				}
			}

			if (this.stackTagCompound.hasKey("display", 10)) {
				NBTTagCompound nbttagcompound = this.stackTagCompound.getCompoundTag("display");
				if (nbttagcompound.hasKey("color", 3)) {
					if (advanced) {
						arraylist.add(
								"Color: #" + Integer.toHexString(nbttagcompound.getInteger("color")).toUpperCase());
					} else {
						arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("item.dyed"));
					}
				}

				if (nbttagcompound.getTagId("Lore") == 9) {
					NBTTagList nbttaglist1 = nbttagcompound.getTagList("Lore", 8);
					if (nbttaglist1.tagCount() > 0) {
						for (int l = 0; l < nbttaglist1.tagCount(); ++l) {
							arraylist.add(EnumChatFormatting.DARK_PURPLE + "" + EnumChatFormatting.ITALIC
									+ nbttaglist1.getStringTagAt(l));
						}
					}
				}
			}
		}

		Multimap multimap = this.getAttributeModifiers();
		if (!multimap.isEmpty() && (k & 2) == 0) {
			arraylist.add("");

			for (Entry entry : (Set<Entry>) multimap.entries()) {
				AttributeModifier attributemodifier = (AttributeModifier) entry.getValue();
				double d0 = attributemodifier.getAmount();
				if (attributemodifier.getID() == Item.itemModifierUUID) {
					d0 += (double) EnchantmentHelper.func_152377_a(this, EnumCreatureAttribute.UNDEFINED);
				}

				double d1;
				if (attributemodifier.getOperation() != 1 && attributemodifier.getOperation() != 2) {
					d1 = d0;
				} else {
					d1 = d0 * 100.0D;
				}

				if (d0 > 0.0D) {
					arraylist.add(EnumChatFormatting.BLUE + StatCollector.translateToLocalFormatted(
							"attribute.modifier.plus." + attributemodifier.getOperation(),
							new Object[] { DECIMALFORMAT.format(d1),
									StatCollector.translateToLocal("attribute.name." + (String) entry.getKey()) }));
				} else if (d0 < 0.0D) {
					d1 = d1 * -1.0D;
					arraylist.add(EnumChatFormatting.RED + StatCollector.translateToLocalFormatted(
							"attribute.modifier.take." + attributemodifier.getOperation(),
							new Object[] { DECIMALFORMAT.format(d1),
									StatCollector.translateToLocal("attribute.name." + (String) entry.getKey()) }));
				}
			}
		}

		if (this.hasTagCompound() && this.getTagCompound().getBoolean("Unbreakable") && (k & 4) == 0) {
			arraylist.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("item.unbreakable"));
		}

		if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9) && (k & 8) == 0) {
			NBTTagList nbttaglist2 = this.stackTagCompound.getTagList("CanDestroy", 8);
			if (nbttaglist2.tagCount() > 0) {
				arraylist.add("");
				arraylist.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canBreak"));

				for (int i1 = 0; i1 < nbttaglist2.tagCount(); ++i1) {
					Block block = Block.getBlockFromName(nbttaglist2.getStringTagAt(i1));
					if (block != null) {
						arraylist.add(EnumChatFormatting.DARK_GRAY + block.getLocalizedName());
					} else {
						arraylist.add(EnumChatFormatting.DARK_GRAY + "missingno");
					}
				}
			}
		}

		if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9) && (k & 16) == 0) {
			NBTTagList nbttaglist3 = this.stackTagCompound.getTagList("CanPlaceOn", 8);
			if (nbttaglist3.tagCount() > 0) {
				arraylist.add("");
				arraylist.add(EnumChatFormatting.GRAY + StatCollector.translateToLocal("item.canPlace"));

				for (int j1 = 0; j1 < nbttaglist3.tagCount(); ++j1) {
					Block block1 = Block.getBlockFromName(nbttaglist3.getStringTagAt(j1));
					if (block1 != null) {
						arraylist.add(EnumChatFormatting.DARK_GRAY + block1.getLocalizedName());
					} else {
						arraylist.add(EnumChatFormatting.DARK_GRAY + "missingno");
					}
				}
			}
		}

		if (advanced) {
			if (this.isItemDamaged()) {
				arraylist.add(
						"Durability: " + (this.getMaxDamage() - this.getItemDamage()) + " / " + this.getMaxDamage());
			}

			arraylist.add(EnumChatFormatting.DARK_GRAY
					+ ((ResourceLocation) Item.itemRegistry.getNameForObject(this.item)).toString());
			if (this.hasTagCompound()) {
				arraylist.add(
						EnumChatFormatting.DARK_GRAY + "NBT: " + this.getTagCompound().getKeySet().size() + " tag(s)");
			}
		}

		return arraylist;
	}

	public boolean hasEffect() {
		return this.getItem().hasEffect(this);
	}

	public EnumRarity getRarity() {
		return this.getItem().getRarity(this);
	}

	/**+
	 * True if it is a tool and has no enchantments to begin with
	 */
	public boolean isItemEnchantable() {
		return !this.getItem().isItemTool(this) ? false : !this.isItemEnchanted();
	}

	/**+
	 * Adds an enchantment with a desired level on the ItemStack.
	 */
	public void addEnchantment(Enchantment ench, int level) {
		if (this.stackTagCompound == null) {
			this.setTagCompound(new NBTTagCompound());
		}

		if (!this.stackTagCompound.hasKey("ench", 9)) {
			this.stackTagCompound.setTag("ench", new NBTTagList());
		}

		NBTTagList nbttaglist = this.stackTagCompound.getTagList("ench", 10);
		NBTTagCompound nbttagcompound = new NBTTagCompound();
		nbttagcompound.setShort("id", (short) ench.effectId);
		nbttagcompound.setShort("lvl", (short) ((byte) level));
		nbttaglist.appendTag(nbttagcompound);
	}

	/**+
	 * True if the item has enchantment data
	 */
	public boolean isItemEnchanted() {
		return this.stackTagCompound != null && this.stackTagCompound.hasKey("ench", 9);
	}

	public void setTagInfo(String key, NBTBase value) {
		if (this.stackTagCompound == null) {
			this.setTagCompound(new NBTTagCompound());
		}

		this.stackTagCompound.setTag(key, value);
	}

	public boolean canEditBlocks() {
		return this.getItem().canItemEditBlocks();
	}

	/**+
	 * Return whether this stack is on an item frame.
	 */
	public boolean isOnItemFrame() {
		return this.itemFrame != null;
	}

	/**+
	 * Set the item frame this stack is on.
	 */
	public void setItemFrame(EntityItemFrame frame) {
		this.itemFrame = frame;
	}

	/**+
	 * Return the item frame this stack is on. Returns null if not
	 * on an item frame.
	 */
	public EntityItemFrame getItemFrame() {
		return this.itemFrame;
	}

	/**+
	 * Get this stack's repair cost, or 0 if no repair cost is
	 * defined.
	 */
	public int getRepairCost() {
		return this.hasTagCompound() && this.stackTagCompound.hasKey("RepairCost", 3)
				? this.stackTagCompound.getInteger("RepairCost")
				: 0;
	}

	/**+
	 * Set this stack's repair cost.
	 */
	public void setRepairCost(int cost) {
		if (!this.hasTagCompound()) {
			this.stackTagCompound = new NBTTagCompound();
		}

		this.stackTagCompound.setInteger("RepairCost", cost);
	}

	public Multimap<String, AttributeModifier> getAttributeModifiers() {
		Object object;
		if (this.hasTagCompound() && this.stackTagCompound.hasKey("AttributeModifiers", 9)) {
			object = HashMultimap.create();
			NBTTagList nbttaglist = this.stackTagCompound.getTagList("AttributeModifiers", 10);

			for (int i = 0; i < nbttaglist.tagCount(); ++i) {
				NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
				AttributeModifier attributemodifier = SharedMonsterAttributes
						.readAttributeModifierFromNBT(nbttagcompound);
				if (attributemodifier != null && attributemodifier.getID().getLeastSignificantBits() != 0L
						&& attributemodifier.getID().getMostSignificantBits() != 0L) {
					((Multimap) object).put(nbttagcompound.getString("AttributeName"), attributemodifier);
				}
			}
		} else {
			object = this.getItem().getItemAttributeModifiers();
		}

		return (Multimap<String, AttributeModifier>) object;
	}

	public void setItem(Item newItem) {
		this.item = newItem;
	}

	/**+
	 * Get a ChatComponent for this Item's display name that shows
	 * this Item on hover
	 */
	public IChatComponent getChatComponent() {
		ChatComponentText chatcomponenttext = new ChatComponentText(this.getDisplayName());
		if (this.hasDisplayName()) {
			chatcomponenttext.getChatStyle().setItalic(Boolean.valueOf(true));
		}

		IChatComponent ichatcomponent = (new ChatComponentText("[")).appendSibling(chatcomponenttext).appendText("]");
		if (this.item != null) {
			NBTTagCompound nbttagcompound = new NBTTagCompound();
			this.writeToNBT(nbttagcompound);
			ichatcomponent.getChatStyle().setChatHoverEvent(
					new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ChatComponentText(nbttagcompound.toString())));
			ichatcomponent.getChatStyle().setColor(this.getRarity().rarityColor);
		}

		return ichatcomponent;
	}

	public boolean canDestroy(Block blockIn) {
		if (blockIn == this.canDestroyCacheBlock) {
			return this.canDestroyCacheResult;
		} else {
			this.canDestroyCacheBlock = blockIn;
			if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanDestroy", 9)) {
				NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanDestroy", 8);

				for (int i = 0; i < nbttaglist.tagCount(); ++i) {
					Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
					if (block == blockIn) {
						this.canDestroyCacheResult = true;
						return true;
					}
				}
			}

			this.canDestroyCacheResult = false;
			return false;
		}
	}

	public boolean canPlaceOn(Block blockIn) {
		if (blockIn == this.canPlaceOnCacheBlock) {
			return this.canPlaceOnCacheResult;
		} else {
			this.canPlaceOnCacheBlock = blockIn;
			if (this.hasTagCompound() && this.stackTagCompound.hasKey("CanPlaceOn", 9)) {
				NBTTagList nbttaglist = this.stackTagCompound.getTagList("CanPlaceOn", 8);

				for (int i = 0; i < nbttaglist.tagCount(); ++i) {
					Block block = Block.getBlockFromName(nbttaglist.getStringTagAt(i));
					if (block == blockIn) {
						this.canPlaceOnCacheResult = true;
						return true;
					}
				}
			}

			this.canPlaceOnCacheResult = false;
			return false;
		}
	}
}