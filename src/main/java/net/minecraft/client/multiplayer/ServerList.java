package net.minecraft.client.multiplayer;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.EagRuntime;
import net.lax1dude.eaglercraft.v1_8.EaglerInputStream;
import net.lax1dude.eaglercraft.v1_8.internal.EnumServerRateLimit;
import net.lax1dude.eaglercraft.v1_8.internal.IClientConfigAdapter.DefaultServer;
import net.lax1dude.eaglercraft.v1_8.internal.QueryResponse;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.socket.AddressResolver;
import net.lax1dude.eaglercraft.v1_8.socket.RateLimitTracker;
import net.lax1dude.eaglercraft.v1_8.socket.ServerQueryDispatch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;

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
public class ServerList {
	private static final Logger logger = LogManager.getLogger();
	private final Minecraft mc;
	private final List<ServerData> allServers = Lists.newArrayList();
	/**+
	 * List of ServerData instances.
	 */
	private final List<ServerData> servers = Lists.newArrayList();

	private static ServerList instance = null;

	private ServerList(Minecraft mcIn) {
		this.mc = mcIn;
		this.loadServerList();
	}

	public static void initServerList(Minecraft mc) {
		instance = new ServerList(mc);
	}

	public static ServerList getServerList() {
		return instance;
	}

	/**+
	 * Loads a list of servers from servers.dat, by running
	 * ServerData.getServerDataFromNBTCompound on each NBT compound
	 * found in the "servers" tag list.
	 */
	public void loadServerList() {
		try {
			freeServerIcons();

			this.allServers.clear();
			for (DefaultServer srv : EagRuntime.getConfiguration().getDefaultServerList()) {
				ServerData dat = new ServerData(srv.name, srv.addr, true);
				dat.isDefault = true;
				this.allServers.add(dat);
			}

			byte[] localStorage = EagRuntime.getStorage("s");

			if (localStorage != null) {
				NBTTagCompound nbttagcompound = CompressedStreamTools
						.readCompressed(new EaglerInputStream(localStorage));
				if (nbttagcompound == null) {
					return;
				}

				NBTTagList nbttaglist = nbttagcompound.getTagList("servers", 10);

				for (int i = 0; i < nbttaglist.tagCount(); ++i) {
					ServerData srv = ServerData.getServerDataFromNBTCompound(nbttaglist.getCompoundTagAt(i));
					this.allServers.add(srv);
				}
			}

		} catch (Exception exception) {
			logger.error("Couldn\'t load server list", exception);
		} finally {
			refreshServerPing();
		}

	}

	/**+
	 * Runs getNBTCompound on each ServerData instance, puts
	 * everything into a "servers" NBT list and writes it to
	 * servers.dat.
	 */
	public void saveServerList() {
		try {
			NBTTagList nbttaglist = new NBTTagList();

			for (ServerData serverdata : this.servers) {
				if (!serverdata.isDefault) {
					nbttaglist.appendTag(serverdata.getNBTCompound());
				}
			}

			NBTTagCompound nbttagcompound = new NBTTagCompound();
			nbttagcompound.setTag("servers", nbttaglist);

			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			CompressedStreamTools.writeCompressed(nbttagcompound, bao);
			EagRuntime.setStorage("s", bao.toByteArray());

		} catch (Exception exception) {
			logger.error("Couldn\'t save server list", exception);
		}

	}

	/**+
	 * Gets the ServerData instance stored for the given index in
	 * the list.
	 */
	public ServerData getServerData(int parInt1) {
		return (ServerData) this.servers.get(parInt1);
	}

	/**+
	 * Removes the ServerData instance stored for the given index in
	 * the list.
	 */
	public void removeServerData(int parInt1) {
		ServerData data = this.servers.remove(parInt1);
		if (data != null && data.iconTextureObject != null) {
			mc.getTextureManager().deleteTexture(data.iconResourceLocation);
			data.iconTextureObject = null;
		}
	}

	/**+
	 * Adds the given ServerData instance to the list.
	 */
	public void addServerData(ServerData parServerData) {
		this.servers.add(parServerData);
	}

	/**+
	 * Counts the number of ServerData instances in the list.
	 */
	public int countServers() {
		return this.servers.size();
	}

	/**+
	 * Takes two list indexes, and swaps their order around.
	 */
	public void swapServers(int parInt1, int parInt2) {
		ServerData serverdata = this.getServerData(parInt1);
		this.servers.set(parInt1, this.getServerData(parInt2));
		this.servers.set(parInt2, serverdata);
		this.saveServerList();
	}

	public void func_147413_a(int parInt1, ServerData parServerData) {
		this.servers.set(parInt1, parServerData);
	}

	public static void func_147414_b(ServerData parServerData) {
		ServerList serverlist = new ServerList(Minecraft.getMinecraft());
		serverlist.loadServerList();

		for (int i = 0; i < serverlist.countServers(); ++i) {
			ServerData serverdata = serverlist.getServerData(i);
			if (serverdata.serverName.equals(parServerData.serverName)
					&& serverdata.serverIP.equals(parServerData.serverIP)) {
				serverlist.func_147413_a(i, parServerData);
				break;
			}
		}

		serverlist.saveServerList();
	}

	public void freeServerIcons() {
		TextureManager mgr = mc.getTextureManager();
		for (int i = 0, l = allServers.size(); i < l; ++i) {
			ServerData server = allServers.get(i);
			if (server.iconTextureObject != null) {
				mgr.deleteTexture(server.iconResourceLocation);
				server.iconTextureObject = null;
			}
		}
	}

	public void refreshServerPing() {
		this.servers.clear();
		this.servers.addAll(this.allServers);
		for (ServerData dat : servers) {
			if (dat.currentQuery != null) {
				if (dat.currentQuery.isOpen()) {
					dat.currentQuery.close();
				}
				dat.currentQuery = null;
			}
			dat.hasPing = false;
			dat.pingSentTime = -1l;
		}
	}

	public void updateServerPing() {
		int total = 0;
		Iterator<ServerData> itr = servers.iterator();
		while (itr.hasNext()) {
			ServerData dat = itr.next();
			if (dat.pingSentTime <= 0l) {
				dat.pingSentTime = System.currentTimeMillis();
				if (RateLimitTracker.isLockedOut(dat.serverIP)) {
					logger.error(
							"Server {} locked this client out on a previous connection, will not attempt to reconnect",
							dat.serverIP);
					dat.serverMOTD = EnumChatFormatting.RED + "Too Many Requests!\nTry again later";
					dat.pingToServer = -1l;
					dat.hasPing = true;
					dat.field_78841_f = true;
				} else {
					dat.pingToServer = -2l;
					String addr = AddressResolver.resolveURI(dat.serverIP);
					dat.currentQuery = ServerQueryDispatch.sendServerQuery(addr, "MOTD");
					if (dat.currentQuery == null) {
						dat.pingToServer = -1l;
						dat.hasPing = true;
						dat.field_78841_f = true;
					} else {
						++total;
					}
				}
			} else if (dat.currentQuery != null) {
				if (!dat.hasPing) {
					++total;
					EnumServerRateLimit rateLimit = dat.currentQuery.getRateLimit();
					if (rateLimit != EnumServerRateLimit.OK) {
						if (rateLimit == EnumServerRateLimit.BLOCKED) {
							RateLimitTracker.registerBlock(dat.serverIP);
						} else if (rateLimit == EnumServerRateLimit.LOCKED_OUT) {
							RateLimitTracker.registerLockOut(dat.serverIP);
						}
						dat.serverMOTD = EnumChatFormatting.RED + "Too Many Requests!\nTry again later";
						dat.pingToServer = -1l;
						dat.hasPing = true;
						return;
					}
				}
				if (dat.currentQuery.responsesAvailable() > 0) {
					QueryResponse pkt;
					do {
						pkt = dat.currentQuery.getResponse();
					} while (dat.currentQuery.responsesAvailable() > 0);
					if (pkt.responseType.equalsIgnoreCase("MOTD") && pkt.isResponseJSON()) {
						dat.setMOTDFromQuery(pkt);
						if (!dat.hasPing) {
							dat.pingToServer = pkt.clientTime - dat.pingSentTime;
							dat.hasPing = true;
						}
					}
				}
				if (dat.currentQuery.binaryResponsesAvailable() > 0) {
					byte[] r;
					do {
						r = dat.currentQuery.getBinaryResponse();
					} while (dat.currentQuery.binaryResponsesAvailable() > 0);
					dat.setIconPacket(r);
				}
				if (!dat.currentQuery.isOpen() && dat.pingSentTime > 0l
						&& (System.currentTimeMillis() - dat.pingSentTime) > 2000l && !dat.hasPing) {
					if (RateLimitTracker.isProbablyLockedOut(dat.serverIP)) {
						logger.error("Server {} ratelimited this client out on a previous connection, assuming lockout",
								dat.serverIP);
						dat.serverMOTD = EnumChatFormatting.RED + "Too Many Requests!\nTry again later";
					}
					dat.pingToServer = -1l;
					dat.hasPing = true;
				}
			}
			if (total >= 4) {
				break;
			}
		}

	}

}