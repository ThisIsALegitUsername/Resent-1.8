package net.minecraft.client.audio;

import java.util.List;

import com.google.common.collect.Lists;

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
public class SoundList {
	private final List<SoundList.SoundEntry> soundList = Lists.newArrayList();
	private boolean replaceExisting;
	private SoundCategory category;

	public List<SoundList.SoundEntry> getSoundList() {
		return this.soundList;
	}

	public boolean canReplaceExisting() {
		return this.replaceExisting;
	}

	public void setReplaceExisting(boolean parFlag) {
		this.replaceExisting = parFlag;
	}

	public SoundCategory getSoundCategory() {
		return this.category;
	}

	public void setSoundCategory(SoundCategory soundCat) {
		this.category = soundCat;
	}

	public static class SoundEntry {
		private String name;
		private float volume = 1.0F;
		private float pitch = 1.0F;
		private int weight = 1;
		private SoundList.SoundEntry.Type type = SoundList.SoundEntry.Type.FILE;
		private boolean streaming = false;

		public String getSoundEntryName() {
			return this.name;
		}

		public void setSoundEntryName(String nameIn) {
			this.name = nameIn;
		}

		public float getSoundEntryVolume() {
			return this.volume;
		}

		public void setSoundEntryVolume(float volumeIn) {
			this.volume = volumeIn;
		}

		public float getSoundEntryPitch() {
			return this.pitch;
		}

		public void setSoundEntryPitch(float pitchIn) {
			this.pitch = pitchIn;
		}

		public int getSoundEntryWeight() {
			return this.weight;
		}

		public void setSoundEntryWeight(int weightIn) {
			this.weight = weightIn;
		}

		public SoundList.SoundEntry.Type getSoundEntryType() {
			return this.type;
		}

		public void setSoundEntryType(SoundList.SoundEntry.Type typeIn) {
			this.type = typeIn;
		}

		public boolean isStreaming() {
			return this.streaming;
		}

		public void setStreaming(boolean isStreaming) {
			this.streaming = isStreaming;
		}

		public static enum Type {
			FILE("file"), SOUND_EVENT("event");

			private final String field_148583_c;

			private Type(String parString2) {
				this.field_148583_c = parString2;
			}

			public static SoundList.SoundEntry.Type getType(String parString1) {
				for (SoundList.SoundEntry.Type soundlist$soundentry$type : values()) {
					if (soundlist$soundentry$type.field_148583_c.equals(parString1)) {
						return soundlist$soundentry$type;
					}
				}

				return null;
			}
		}
	}
}