package net.minecraft.client.network;

import com.google.common.base.Objects;

import net.lax1dude.eaglercraft.v1_8.mojang.authlib.GameProfile;
import net.lax1dude.eaglercraft.v1_8.profile.EaglerProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.WorldSettings;

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
public class NetworkPlayerInfo {
	private final GameProfile gameProfile;
	private WorldSettings.GameType gameType;
	private int responseTime;
	private String skinType;
	private IChatComponent displayName;
	private int field_178873_i = 0;
	private int field_178870_j = 0;
	private long field_178871_k = 0L;
	private long field_178868_l = 0L;
	private long field_178869_m = 0L;

	public NetworkPlayerInfo(GameProfile parGameProfile) {
		this.gameProfile = parGameProfile;
	}

	public NetworkPlayerInfo(S38PacketPlayerListItem.AddPlayerData parAddPlayerData) {
		this.gameProfile = parAddPlayerData.getProfile();
		this.gameType = parAddPlayerData.getGameMode();
		this.responseTime = parAddPlayerData.getPing();
		this.displayName = parAddPlayerData.getDisplayName();
	}

	/**+
	 * Returns the GameProfile for the player represented by this
	 * NetworkPlayerInfo instance
	 */
	public GameProfile getGameProfile() {
		return this.gameProfile;
	}

	public WorldSettings.GameType getGameType() {
		return this.gameType;
	}

	public int getResponseTime() {
		return this.responseTime;
	}

	protected void setGameType(WorldSettings.GameType parGameType) {
		this.gameType = parGameType;
	}

	protected void setResponseTime(int parInt1) {
		this.responseTime = parInt1;
	}

	public boolean hasLocationSkin() {
		return true;
	}

	public String getSkinType() {
		return Minecraft.getMinecraft().getNetHandler().getSkinCache().getSkin(this.gameProfile)
				.getSkinModel().profileSkinType;
	}

	public ResourceLocation getLocationSkin() {
		return Minecraft.getMinecraft().getNetHandler().getSkinCache().getSkin(this.gameProfile).getResourceLocation();
	}

	public ResourceLocation getLocationCape() {
		return null;
	}

	public ScorePlayerTeam getPlayerTeam() {
		return Minecraft.getMinecraft().theWorld.getScoreboard().getPlayersTeam(this.getGameProfile().getName());
	}

	public void setDisplayName(IChatComponent displayNameIn) {
		this.displayName = displayNameIn;
	}

	public IChatComponent getDisplayName() {
		return this.displayName;
	}

	public int func_178835_l() {
		return this.field_178873_i;
	}

	public void func_178836_b(int parInt1) {
		this.field_178873_i = parInt1;
	}

	public int func_178860_m() {
		return this.field_178870_j;
	}

	public void func_178857_c(int parInt1) {
		this.field_178870_j = parInt1;
	}

	public long func_178847_n() {
		return this.field_178871_k;
	}

	public void func_178846_a(long parLong1) {
		this.field_178871_k = parLong1;
	}

	public long func_178858_o() {
		return this.field_178868_l;
	}

	public void func_178844_b(long parLong1) {
		this.field_178868_l = parLong1;
	}

	public long func_178855_p() {
		return this.field_178869_m;
	}

	public void func_178843_c(long parLong1) {
		this.field_178869_m = parLong1;
	}
}