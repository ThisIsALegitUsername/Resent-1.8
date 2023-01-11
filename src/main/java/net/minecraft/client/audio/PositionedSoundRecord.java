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
public class PositionedSoundRecord extends PositionedSound {
	public static PositionedSoundRecord create(ResourceLocation soundResource, float pitch) {
		return new PositionedSoundRecord(soundResource, 0.25F, pitch, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F,
				0.0F);
	}

	public static PositionedSoundRecord create(ResourceLocation soundResource) {
		return new PositionedSoundRecord(soundResource, 1.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F,
				0.0F);
	}

	public static PositionedSoundRecord create(ResourceLocation soundResource, float xPosition, float yPosition,
			float zPosition) {
		return new PositionedSoundRecord(soundResource, 4.0F, 1.0F, false, 0, ISound.AttenuationType.LINEAR, xPosition,
				yPosition, zPosition);
	}

	public PositionedSoundRecord(ResourceLocation soundResource, float volume, float pitch, float xPosition,
			float yPosition, float zPosition) {
		this(soundResource, volume, pitch, false, 0, ISound.AttenuationType.LINEAR, xPosition, yPosition, zPosition);
	}

	private PositionedSoundRecord(ResourceLocation soundResource, float volume, float pitch, boolean repeat,
			int repeatDelay, ISound.AttenuationType attenuationType, float xPosition, float yPosition,
			float zPosition) {
		super(soundResource);
		this.volume = volume;
		this.pitch = pitch;
		this.xPosF = xPosition;
		this.yPosF = yPosition;
		this.zPosF = zPosition;
		this.repeat = repeat;
		this.repeatDelay = repeatDelay;
		this.attenuationType = attenuationType;
	}
}