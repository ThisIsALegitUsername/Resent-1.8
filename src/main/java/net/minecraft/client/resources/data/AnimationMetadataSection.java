package net.minecraft.client.resources.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;

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
public class AnimationMetadataSection implements IMetadataSection {
	private final List<AnimationFrame> animationFrames;
	private final int frameWidth;
	private final int frameHeight;
	private final int frameTime;
	private final boolean interpolate;

	public AnimationMetadataSection(List<AnimationFrame> parList, int parInt1, int parInt2, int parInt3,
			boolean parFlag) {
		this.animationFrames = parList;
		this.frameWidth = parInt1;
		this.frameHeight = parInt2;
		this.frameTime = parInt3;
		this.interpolate = parFlag;
	}

	public int getFrameHeight() {
		return this.frameHeight;
	}

	public int getFrameWidth() {
		return this.frameWidth;
	}

	public int getFrameCount() {
		return this.animationFrames.size();
	}

	public int getFrameTime() {
		return this.frameTime;
	}

	public boolean isInterpolate() {
		return this.interpolate;
	}

	private AnimationFrame getAnimationFrame(int parInt1) {
		return (AnimationFrame) this.animationFrames.get(parInt1);
	}

	public int getFrameTimeSingle(int parInt1) {
		AnimationFrame animationframe = this.getAnimationFrame(parInt1);
		return animationframe.hasNoTime() ? this.frameTime : animationframe.getFrameTime();
	}

	public boolean frameHasTime(int parInt1) {
		return !((AnimationFrame) this.animationFrames.get(parInt1)).hasNoTime();
	}

	public int getFrameIndex(int parInt1) {
		return ((AnimationFrame) this.animationFrames.get(parInt1)).getFrameIndex();
	}

	public Set<Integer> getFrameIndexSet() {
		HashSet hashset = Sets.newHashSet();

		for (AnimationFrame animationframe : this.animationFrames) {
			hashset.add(Integer.valueOf(animationframe.getFrameIndex()));
		}

		return hashset;
	}
}