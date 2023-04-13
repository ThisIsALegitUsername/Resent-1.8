package dev.resent.module.impl.misc;

import dev.resent.annotation.Module;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.setting.CustomRectSettingDraw;
import net.lax1dude.eaglercraft.v1_8.internal.PlatformRuntime;

@Module(name = "Tab Settings", category = Category.MISC, hasSetting = true, description = "Change the icon or title of the tab you're on!")
public class TabSettings extends Mod {
	public CustomRectSettingDraw addTabTitle = new CustomRectSettingDraw("Set Tab Title...", "") {
		@Override
        public void onPress() {
			PlatformRuntime.changeTitle();
		}
		
	};
	public CustomRectSettingDraw addTabIcon = new CustomRectSettingDraw("Set Tab Icon...", "") {
		@Override
        public void onPress() {
			PlatformRuntime.changeFavicon();
		}
	};
	
	public void onEnable() {
		PlatformRuntime.toggleTabTitleMod(true);
	}
	
	public void onDisable() {
		PlatformRuntime.toggleTabTitleMod(false);
	}
	
	public TabSettings() {
		addSetting(addTabTitle, addTabIcon);
	}
	
}
