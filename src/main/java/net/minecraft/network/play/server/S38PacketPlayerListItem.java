package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.mojang.authlib.GameProfile;
import net.lax1dude.eaglercraft.v1_8.mojang.authlib.Property;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.IChatComponent;
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
public class S38PacketPlayerListItem implements Packet<INetHandlerPlayClient> {
	private S38PacketPlayerListItem.Action action;
	private final List<S38PacketPlayerListItem.AddPlayerData> players = Lists.newArrayList();

	public S38PacketPlayerListItem() {
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.action = (S38PacketPlayerListItem.Action) parPacketBuffer
				.readEnumValue(S38PacketPlayerListItem.Action.class);
		int i = parPacketBuffer.readVarIntFromBuffer();

		for (int j = 0; j < i; ++j) {
			GameProfile gameprofile = null;
			int k = 0;
			WorldSettings.GameType worldsettings$gametype = null;
			IChatComponent ichatcomponent = null;
			switch (this.action) {
			case ADD_PLAYER:
				gameprofile = new GameProfile(parPacketBuffer.readUuid(), parPacketBuffer.readStringFromBuffer(16));
				int l = parPacketBuffer.readVarIntFromBuffer();
				int i1 = 0;

				for (; i1 < l; ++i1) {
					String s = parPacketBuffer.readStringFromBuffer(32767);
					String s1 = parPacketBuffer.readStringFromBuffer(32767);
					if (parPacketBuffer.readBoolean()) {
						gameprofile.getProperties().put(s,
								new Property(s, s1, parPacketBuffer.readStringFromBuffer(32767)));
					} else {
						gameprofile.getProperties().put(s, new Property(s, s1));
					}
				}

				worldsettings$gametype = WorldSettings.GameType.getByID(parPacketBuffer.readVarIntFromBuffer());
				k = parPacketBuffer.readVarIntFromBuffer();
				if (parPacketBuffer.readBoolean()) {
					ichatcomponent = parPacketBuffer.readChatComponent();
				}
				break;
			case UPDATE_GAME_MODE:
				gameprofile = new GameProfile(parPacketBuffer.readUuid(), (String) null);
				worldsettings$gametype = WorldSettings.GameType.getByID(parPacketBuffer.readVarIntFromBuffer());
				break;
			case UPDATE_LATENCY:
				gameprofile = new GameProfile(parPacketBuffer.readUuid(), (String) null);
				k = parPacketBuffer.readVarIntFromBuffer();
				break;
			case UPDATE_DISPLAY_NAME:
				gameprofile = new GameProfile(parPacketBuffer.readUuid(), (String) null);
				if (parPacketBuffer.readBoolean()) {
					ichatcomponent = parPacketBuffer.readChatComponent();
				}
				break;
			case REMOVE_PLAYER:
				gameprofile = new GameProfile(parPacketBuffer.readUuid(), (String) null);
			}

			this.players.add(
					new S38PacketPlayerListItem.AddPlayerData(gameprofile, k, worldsettings$gametype, ichatcomponent));
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		// server only
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handlePlayerListItem(this);
	}

	public List<S38PacketPlayerListItem.AddPlayerData> func_179767_a() {
		return this.players;
	}

	public S38PacketPlayerListItem.Action func_179768_b() {
		return this.action;
	}

	public String toString() {
		return Objects.toStringHelper(this).add("action", this.action).add("entries", this.players).toString();
	}

	public static enum Action {
		ADD_PLAYER, UPDATE_GAME_MODE, UPDATE_LATENCY, UPDATE_DISPLAY_NAME, REMOVE_PLAYER;
	}

	public class AddPlayerData {
		private final int ping;
		private final WorldSettings.GameType gamemode;
		private final GameProfile profile;
		private final IChatComponent displayName;

		public AddPlayerData(GameProfile profile, int pingIn, WorldSettings.GameType gamemodeIn,
				IChatComponent displayNameIn) {
			this.profile = profile;
			this.ping = pingIn;
			this.gamemode = gamemodeIn;
			this.displayName = displayNameIn;
		}

		public GameProfile getProfile() {
			return this.profile;
		}

		public int getPing() {
			return this.ping;
		}

		public WorldSettings.GameType getGameMode() {
			return this.gamemode;
		}

		public IChatComponent getDisplayName() {
			return this.displayName;
		}

		public String toString() {
			return Objects.toStringHelper(this).add("latency", this.ping).add("gameMode", this.gamemode)
					.add("profile", this.profile).add("displayName", this.displayName == null ? null
							: IChatComponent.Serializer.componentToJson(this.displayName))
					.toString();
		}
	}
}