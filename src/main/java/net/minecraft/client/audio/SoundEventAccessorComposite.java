package net.minecraft.client.audio;

import java.util.List;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;

import com.google.common.collect.Lists;

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
public class SoundEventAccessorComposite implements ISoundEventAccessor<SoundPoolEntry> {
	/**+
	 * A composite (List) of ISoundEventAccessors
	 */
	private final List<ISoundEventAccessor<SoundPoolEntry>> soundPool = Lists.newArrayList();
	private final EaglercraftRandom rnd = new EaglercraftRandom();
	private final ResourceLocation soundLocation;
	private final SoundCategory category;
	private double eventPitch;
	private double eventVolume;

	public SoundEventAccessorComposite(ResourceLocation soundLocation, double pitch, double volume,
			SoundCategory category) {
		this.soundLocation = soundLocation;
		this.eventVolume = volume;
		this.eventPitch = pitch;
		this.category = category;
	}

	public int getWeight() {
		int i = 0;

		for (ISoundEventAccessor isoundeventaccessor : this.soundPool) {
			i += isoundeventaccessor.getWeight();
		}

		return i;
	}

	public SoundPoolEntry cloneEntry() {
		int i = this.getWeight();
		if (!this.soundPool.isEmpty() && i != 0) {
			int j = this.rnd.nextInt(i);

			for (ISoundEventAccessor isoundeventaccessor : this.soundPool) {
				j -= isoundeventaccessor.getWeight();
				if (j < 0) {
					SoundPoolEntry soundpoolentry = (SoundPoolEntry) isoundeventaccessor.cloneEntry();
					soundpoolentry.setPitch(soundpoolentry.getPitch() * this.eventPitch);
					soundpoolentry.setVolume(soundpoolentry.getVolume() * this.eventVolume);
					return soundpoolentry;
				}
			}

			return SoundHandler.missing_sound;
		} else {
			return SoundHandler.missing_sound;
		}
	}

	public void addSoundToEventPool(ISoundEventAccessor<SoundPoolEntry> sound) {
		this.soundPool.add(sound);
	}

	public ResourceLocation getSoundEventLocation() {
		return this.soundLocation;
	}

	public SoundCategory getSoundCategory() {
		return this.category;
	}
}