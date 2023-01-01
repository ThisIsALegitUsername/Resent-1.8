package dev.resent.module.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dev.resent.setting.Setting;
import net.minecraft.client.Minecraft;

public class Mod {

	public Minecraft mc = Minecraft.getMinecraft();
	public int keyCode;

	public String name;
	public Category category;
	public boolean enabled = false;
	public boolean hasSetting;

	public List<Setting> settings = new ArrayList<>();

	public Mod(String name, Category cat) {
		this.name = name;
		this.category = cat;
	}

	public Mod(String name, Category cat, boolean hasSetting) {
		this.name = name;
		this.category = cat;
		this.hasSetting = hasSetting;
	}

	public void addSetting(Setting... settings) {
		this.settings.addAll(Arrays.asList(settings));
	}

	public void onEnable() {
	}

	public void onDisable() {
	}

	public void toggle() {
		this.enabled = !this.enabled;
		if (this.enabled){
			onEnable();
		}else{
			onDisable();
		}
	}

	public void setEnabled(boolean state) {
		this.enabled = state;
		if (this.enabled)
			onEnable();
		else
			onDisable();
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getName() {
		return name;
	}
}
