package net.minecraft.client.audio;

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
public class SoundPoolEntry {
	private final ResourceLocation location;
	private final boolean streamingSound;
	private double pitch;
	private double volume;

	public SoundPoolEntry(ResourceLocation locationIn, double pitchIn, double volumeIn, boolean streamingSoundIn) {
		this.location = locationIn;
		this.pitch = pitchIn;
		this.volume = volumeIn;
		this.streamingSound = streamingSoundIn;
	}

	public SoundPoolEntry(SoundPoolEntry locationIn) {
		this.location = locationIn.location;
		this.pitch = locationIn.pitch;
		this.volume = locationIn.volume;
		this.streamingSound = locationIn.streamingSound;
	}

	public ResourceLocation getSoundPoolEntryLocation() {
		return this.location;
	}

	public double getPitch() {
		return this.pitch;
	}

	public void setPitch(double pitchIn) {
		this.pitch = pitchIn;
	}

	public double getVolume() {
		return this.volume;
	}

	public void setVolume(double volumeIn) {
		this.volume = volumeIn;
	}

	public boolean isStreamingSound() {
		return this.streamingSound;
	}
}