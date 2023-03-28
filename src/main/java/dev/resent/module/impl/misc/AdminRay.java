package dev.resent.module.impl.misc;

import java.util.ArrayList;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.BooleanSetting;
import net.lax1dude.eaglercraft.v1_8.ArrayUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

@Module(name = "Ray", category = Category.MISC, hasSetting = true)
public class AdminRay extends Mod{

    public static ArrayList<Block> rayBlocks = new ArrayList<>();

	public static BooleanSetting iron = new BooleanSetting("Iron", "", false);
	public static BooleanSetting diamond = new BooleanSetting("Diamond", "", true);
	public static BooleanSetting gold = new BooleanSetting("Gold", "", false);
	public static BooleanSetting redstone = new BooleanSetting("Redstone", "", false);
	public static BooleanSetting lapis = new BooleanSetting("Lapis", "", false);
	public static BooleanSetting quartz = new BooleanSetting("Quartz", "", false);
	public static BooleanSetting water = new BooleanSetting("Water", "", false);

	public AdminRay(){
		addSetting(iron, diamond, gold, redstone, lapis, quartz, water);
	}

	@Override
	public boolean isAdmin(){
		return true;
	}

    public static boolean shouldRender(Block block){
        return rayBlocks.contains(block);
    }

    public static void start() {
		rayBlocks.clear();

		if(iron.getValue())
			rayBlocks.add(Block.getBlockFromName("iron_ore"));
		if(gold.getValue())
			rayBlocks.add(Block.getBlockFromName("gold_ore"));
		if(redstone.getValue()){
			rayBlocks.add(Block.getBlockFromName("redstone_ore"));
			rayBlocks.add(Block.getBlockById(74));
		}
		if(lapis.getValue())
			rayBlocks.add(Block.getBlockFromName("lapis_ore"));
		if(diamond.getValue())
			rayBlocks.add(Block.getBlockFromName("diamond_ore"));
		if(quartz.getValue())
			rayBlocks.add(Block.getBlockFromName("quartz_ore"));
		if(water.getValue()){
			rayBlocks.add(Block.getBlockById(8));
			rayBlocks.add(Block.getBlockById(9));
		}

		rayBlocks.add(Block.getBlockById(10));
		rayBlocks.add(Block.getBlockById(11));
		rayBlocks.add(Block.getBlockFromName("fire"));
		rayBlocks.add(Block.getBlockFromName("mob_spawner"));
		rayBlocks.add(Block.getBlockFromName("end_portal_frame"));
		rayBlocks.add(Block.getBlockFromName("enchanting_table"));
		rayBlocks.add(Block.getBlockFromName("bookshelf"));
	}

	public static void updateGameSetting(){
		Minecraft mc = Minecraft.getMinecraft();
		mc.gameSettings.keyBindings =
		(KeyBinding[]) ArrayUtils.addAll(
			new KeyBinding[] {
				mc.gameSettings.keyBindAttack,
				mc.gameSettings.keyBindUseItem,
				mc.gameSettings.keyBindForward,
				mc.gameSettings.keyBindLeft,
				mc.gameSettings.keyBindBack,
				mc.gameSettings.keyBindRight,
				mc.gameSettings.keyBindJump,
				mc.gameSettings.keyBindSneak,
				mc.gameSettings.keyBindSprint,
				mc.gameSettings.keyBindDrop,
				mc.gameSettings.keyBindInventory,
				mc.gameSettings.keyBindChat,
				mc.gameSettings.keyBindPlayerList,
				mc.gameSettings.keyBindPickBlock,
				mc.gameSettings.keyBindCommand,
				mc.gameSettings.keyBindScreenshot,
				mc.gameSettings.keyBindTogglePerspective,
				mc.gameSettings.keyBindSmoothCamera,
				mc.gameSettings.keyBindZoomCamera,
				mc.gameSettings.keyBindFunction,
				mc.gameSettings.keyBindClose,
				mc.gameSettings.keyBindClickGui,
				mc.gameSettings.keyBindFreelook,
				mc.gameSettings.keyBindAdminX
			},
			mc.gameSettings.keyBindsHotbar
		);
	}
}
