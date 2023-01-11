package net.minecraft.block;

import java.util.List;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

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
public class BlockPlanks extends Block {
	public static PropertyEnum<BlockPlanks.EnumType> VARIANT;

	public BlockPlanks() {
		super(Material.wood);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks.EnumType.OAK));
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	public static void bootstrapStates() {
		VARIANT = PropertyEnum.<BlockPlanks.EnumType>create("variant", BlockPlanks.EnumType.class);
	}

	/**+
	 * Gets the metadata of the item this Block can drop. This
	 * method is called when the block gets destroyed. It returns
	 * the metadata of the dropped item based on the old metadata of
	 * the block.
	 */
	public int damageDropped(IBlockState iblockstate) {
		return ((BlockPlanks.EnumType) iblockstate.getValue(VARIANT)).getMetadata();
	}

	/**+
	 * returns a list of blocks with the same ID, but different meta
	 * (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs var2, List<ItemStack> list) {
		for (BlockPlanks.EnumType blockplanks$enumtype : BlockPlanks.EnumType.values()) {
			list.add(new ItemStack(item, 1, blockplanks$enumtype.getMetadata()));
		}

	}

	/**+
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int i) {
		return this.getDefaultState().withProperty(VARIANT, BlockPlanks.EnumType.byMetadata(i));
	}

	/**+
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(IBlockState iblockstate) {
		return ((BlockPlanks.EnumType) iblockstate.getValue(VARIANT)).func_181070_c();
	}

	/**+
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState iblockstate) {
		return ((BlockPlanks.EnumType) iblockstate.getValue(VARIANT)).getMetadata();
	}

	protected BlockState createBlockState() {
		return new BlockState(this, new IProperty[] { VARIANT });
	}

	public static enum EnumType implements IStringSerializable {
		OAK(0, "oak", MapColor.woodColor), SPRUCE(1, "spruce", MapColor.obsidianColor),
		BIRCH(2, "birch", MapColor.sandColor), JUNGLE(3, "jungle", MapColor.dirtColor),
		ACACIA(4, "acacia", MapColor.adobeColor), DARK_OAK(5, "dark_oak", "big_oak", MapColor.brownColor);

		private static final BlockPlanks.EnumType[] META_LOOKUP = new BlockPlanks.EnumType[values().length];
		private final int meta;
		private final String name;
		private final String unlocalizedName;
		private final MapColor field_181071_k;

		private EnumType(int parInt2, String parString2, MapColor parMapColor) {
			this(parInt2, parString2, parString2, parMapColor);
		}

		private EnumType(int parInt2, String parString2, String parString3, MapColor parMapColor) {
			this.meta = parInt2;
			this.name = parString2;
			this.unlocalizedName = parString3;
			this.field_181071_k = parMapColor;
		}

		public int getMetadata() {
			return this.meta;
		}

		public MapColor func_181070_c() {
			return this.field_181071_k;
		}

		public String toString() {
			return this.name;
		}

		public static BlockPlanks.EnumType byMetadata(int meta) {
			if (meta < 0 || meta >= META_LOOKUP.length) {
				meta = 0;
			}

			return META_LOOKUP[meta];
		}

		public String getName() {
			return this.name;
		}

		public String getUnlocalizedName() {
			return this.unlocalizedName;
		}

		static {
			for (BlockPlanks.EnumType blockplanks$enumtype : values()) {
				META_LOOKUP[blockplanks$enumtype.getMetadata()] = blockplanks$enumtype;
			}

		}
	}
}