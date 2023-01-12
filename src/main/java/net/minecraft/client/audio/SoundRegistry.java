package net.minecraft.client.audio;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.RegistrySimple;
import net.minecraft.util.ResourceLocation;

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
public class SoundRegistry extends RegistrySimple<ResourceLocation, SoundEventAccessorComposite> {
	private Map<ResourceLocation, SoundEventAccessorComposite> soundRegistry;

	protected Map<ResourceLocation, SoundEventAccessorComposite> createUnderlyingMap() {
		this.soundRegistry = Maps.newHashMap();
		return this.soundRegistry;
	}

	public void registerSound(SoundEventAccessorComposite parSoundEventAccessorComposite) {
		this.putObject(parSoundEventAccessorComposite.getSoundEventLocation(), parSoundEventAccessorComposite);
	}

	/**+
	 * Reset the underlying sound map (Called on resource manager
	 * reload)
	 */
	public void clearMap() {
		this.soundRegistry.clear();
	}
}