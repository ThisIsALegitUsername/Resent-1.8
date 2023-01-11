package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.Collection;

import com.google.common.collect.Lists;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;

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
public class S3EPacketTeams implements Packet<INetHandlerPlayClient> {
	private String field_149320_a = "";
	private String field_149318_b = "";
	private String field_149319_c = "";
	private String field_149316_d = "";
	private String field_179816_e;
	private int field_179815_f;
	private Collection<String> field_149317_e;
	private int field_149314_f;
	private int field_149315_g;

	public S3EPacketTeams() {
		this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
		this.field_179815_f = -1;
		this.field_149317_e = Lists.newArrayList();
	}

	public S3EPacketTeams(ScorePlayerTeam parScorePlayerTeam, int parInt1) {
		this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
		this.field_179815_f = -1;
		this.field_149317_e = Lists.newArrayList();
		this.field_149320_a = parScorePlayerTeam.getRegisteredName();
		this.field_149314_f = parInt1;
		if (parInt1 == 0 || parInt1 == 2) {
			this.field_149318_b = parScorePlayerTeam.getTeamName();
			this.field_149319_c = parScorePlayerTeam.getColorPrefix();
			this.field_149316_d = parScorePlayerTeam.getColorSuffix();
			this.field_149315_g = parScorePlayerTeam.func_98299_i();
			this.field_179816_e = parScorePlayerTeam.getNameTagVisibility().field_178830_e;
			this.field_179815_f = parScorePlayerTeam.getChatFormat().getColorIndex();
		}

		if (parInt1 == 0) {
			this.field_149317_e.addAll(parScorePlayerTeam.getMembershipCollection());
		}

	}

	public S3EPacketTeams(ScorePlayerTeam parScorePlayerTeam, Collection<String> parCollection, int parInt1) {
		this.field_179816_e = Team.EnumVisible.ALWAYS.field_178830_e;
		this.field_179815_f = -1;
		this.field_149317_e = Lists.newArrayList();
		if (parInt1 != 3 && parInt1 != 4) {
			throw new IllegalArgumentException("Method must be join or leave for player constructor");
		} else if (parCollection != null && !parCollection.isEmpty()) {
			this.field_149314_f = parInt1;
			this.field_149320_a = parScorePlayerTeam.getRegisteredName();
			this.field_149317_e.addAll(parCollection);
		} else {
			throw new IllegalArgumentException("Players cannot be null/empty");
		}
	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.field_149320_a = parPacketBuffer.readStringFromBuffer(16);
		this.field_149314_f = parPacketBuffer.readByte();
		if (this.field_149314_f == 0 || this.field_149314_f == 2) {
			this.field_149318_b = parPacketBuffer.readStringFromBuffer(32);
			this.field_149319_c = parPacketBuffer.readStringFromBuffer(16);
			this.field_149316_d = parPacketBuffer.readStringFromBuffer(16);
			this.field_149315_g = parPacketBuffer.readByte();
			this.field_179816_e = parPacketBuffer.readStringFromBuffer(32);
			this.field_179815_f = parPacketBuffer.readByte();
		}

		if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
			int i = parPacketBuffer.readVarIntFromBuffer();

			for (int j = 0; j < i; ++j) {
				this.field_149317_e.add(parPacketBuffer.readStringFromBuffer(40));
			}
		}

	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeString(this.field_149320_a);
		parPacketBuffer.writeByte(this.field_149314_f);
		if (this.field_149314_f == 0 || this.field_149314_f == 2) {
			parPacketBuffer.writeString(this.field_149318_b);
			parPacketBuffer.writeString(this.field_149319_c);
			parPacketBuffer.writeString(this.field_149316_d);
			parPacketBuffer.writeByte(this.field_149315_g);
			parPacketBuffer.writeString(this.field_179816_e);
			parPacketBuffer.writeByte(this.field_179815_f);
		}

		if (this.field_149314_f == 0 || this.field_149314_f == 3 || this.field_149314_f == 4) {
			parPacketBuffer.writeVarIntToBuffer(this.field_149317_e.size());

			for (String s : this.field_149317_e) {
				parPacketBuffer.writeString(s);
			}
		}

	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerPlayClient inethandlerplayclient) {
		inethandlerplayclient.handleTeams(this);
	}

	public String func_149312_c() {
		return this.field_149320_a;
	}

	public String func_149306_d() {
		return this.field_149318_b;
	}

	public String func_149311_e() {
		return this.field_149319_c;
	}

	public String func_149309_f() {
		return this.field_149316_d;
	}

	public Collection<String> func_149310_g() {
		return this.field_149317_e;
	}

	public int func_149307_h() {
		return this.field_149314_f;
	}

	public int func_149308_i() {
		return this.field_149315_g;
	}

	public int func_179813_h() {
		return this.field_179815_f;
	}

	public String func_179814_i() {
		return this.field_179816_e;
	}
}