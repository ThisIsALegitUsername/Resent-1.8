package dev.resent.module.impl.misc;

import java.util.ArrayList;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.BooleanSetting;
import net.minecraft.block.Block;

@Module(name = "Xray", category = Category.MISC)
public class AdminXray extends Mod{

    public static ArrayList<Block> xrayBlocks = new ArrayList<>();

	public static BooleanSetting iron = new BooleanSetting("Iron", "", false);
	public static BooleanSetting diamond = new BooleanSetting("Diamond", "", false);
	public static BooleanSetting gold = new BooleanSetting("Gold", "", false);
	public static BooleanSetting redstone = new BooleanSetting("Redstone", "", false);
	public static BooleanSetting lapis = new BooleanSetting("Lapis", "", false);
	public static BooleanSetting quartz = new BooleanSetting("Quartz", "", false);

	public AdminXray(){
		addSetting(iron, diamond, gold, redstone, lapis, quartz);
	}

    public static boolean shouldRender(Block block){
        return xrayBlocks.contains(block);
    }

    public static void initXRayBlocks() {
		xrayBlocks.clear();
		if(iron.getValue())
		xrayBlocks.add(Block.getBlockFromName("iron_ore"));
		if(gold.getValue())
		xrayBlocks.add(Block.getBlockFromName("gold_ore"));
		if(redstone.getValue())
		xrayBlocks.add(Block.getBlockFromName("redstone_ore"));
		xrayBlocks.add(Block.getBlockById(74));
		if(lapis.getValue())
		xrayBlocks.add(Block.getBlockFromName("lapis_ore"));
		if(diamond.getValue())
		xrayBlocks.add(Block.getBlockFromName("diamond_ore"));
		if(quartz.getValue())
		xrayBlocks.add(Block.getBlockFromName("quartz_ore"));
		xrayBlocks.add(Block.getBlockById(8));
		xrayBlocks.add(Block.getBlockById(9));
		xrayBlocks.add(Block.getBlockById(10));
		xrayBlocks.add(Block.getBlockById(11));
		xrayBlocks.add(Block.getBlockFromName("fire"));
		xrayBlocks.add(Block.getBlockFromName("mob_spawner"));
		xrayBlocks.add(Block.getBlockFromName("end_portal_frame"));
		xrayBlocks.add(Block.getBlockFromName("enchanting_table"));
		xrayBlocks.add(Block.getBlockFromName("bookshelf"));
	}
}
