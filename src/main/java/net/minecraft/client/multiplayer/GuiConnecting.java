package net.minecraft.client.multiplayer;

import java.io.IOException;

import net.lax1dude.eaglercraft.v1_8.internal.EnumEaglerConnectionState;
import net.lax1dude.eaglercraft.v1_8.internal.EnumServerRateLimit;
import net.lax1dude.eaglercraft.v1_8.internal.PlatformNetworking;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.socket.AddressResolver;
import net.lax1dude.eaglercraft.v1_8.socket.ConnectionHandshake;
import net.lax1dude.eaglercraft.v1_8.socket.EaglercraftNetworkManager;
import net.lax1dude.eaglercraft.v1_8.socket.RateLimitTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.util.ChatComponentText;

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
public class GuiConnecting extends GuiScreen {
	private static final Logger logger = LogManager.getLogger();
	private EaglercraftNetworkManager networkManager;
	private String currentAddress;
	private String currentPassword;
	private boolean allowPlaintext;
	private boolean cancel;
	private boolean hasOpened;
	private final GuiScreen previousGuiScreen;
	private int timer = 0;

	public GuiConnecting(GuiScreen parGuiScreen, Minecraft mcIn, ServerData parServerData) {
		this(parGuiScreen, mcIn, parServerData, false);
	}

	public GuiConnecting(GuiScreen parGuiScreen, Minecraft mcIn, ServerData parServerData, boolean allowPlaintext) {
		this(parGuiScreen, mcIn, parServerData, null, allowPlaintext);
	}

	public GuiConnecting(GuiScreen parGuiScreen, Minecraft mcIn, ServerData parServerData, String password) {
		this(parGuiScreen, mcIn, parServerData, password, false);
	}

	public GuiConnecting(GuiScreen parGuiScreen, Minecraft mcIn, ServerData parServerData, String password,
			boolean allowPlaintext) {
		this.mc = mcIn;
		this.previousGuiScreen = parGuiScreen;
		String serveraddress = AddressResolver.resolveURI(parServerData);
		mcIn.loadWorld((WorldClient) null);
		mcIn.setServerData(parServerData);
		if (RateLimitTracker.isLockedOut(serveraddress)) {
			logger.error("Server locked this client out on a previous connection, will not attempt to reconnect");
		} else {
			this.connect(serveraddress, password, allowPlaintext);
		}
	}

	public GuiConnecting(GuiScreen parGuiScreen, Minecraft mcIn, String hostName, int port) {
		this(parGuiScreen, mcIn, hostName, port, false);
	}

	public GuiConnecting(GuiScreen parGuiScreen, Minecraft mcIn, String hostName, int port, boolean allowPlaintext) {
		this(parGuiScreen, mcIn, hostName, port, null, allowPlaintext);
	}

	public GuiConnecting(GuiScreen parGuiScreen, Minecraft mcIn, String hostName, int port, String password) {
		this(parGuiScreen, mcIn, hostName, port, password, false);
	}

	public GuiConnecting(GuiScreen parGuiScreen, Minecraft mcIn, String hostName, int port, String password,
			boolean allowPlaintext) {
		this.mc = mcIn;
		this.previousGuiScreen = parGuiScreen;
		mcIn.loadWorld((WorldClient) null);
		this.connect(hostName, password, allowPlaintext);
	}

	public GuiConnecting(GuiConnecting previous, String password) {
		this(previous, password, false);
	}

	public GuiConnecting(GuiConnecting previous, String password, boolean allowPlaintext) {
		this.mc = previous.mc;
		this.previousGuiScreen = previous.previousGuiScreen;
		this.connect(previous.currentAddress, password, allowPlaintext);
	}

	private void connect(String ip, String password, boolean allowPlaintext) {
		this.currentAddress = ip;
		this.currentPassword = password;
		this.allowPlaintext = allowPlaintext;
	}

	/**+
	 * Called from the main game loop to update the screen.
	 */
	public void updateScreen() {
		++timer;
		if (timer > 1) {
			if (this.currentAddress == null) {
				mc.displayGuiScreen(GuiDisconnected.createRateLimitKick(previousGuiScreen));
			} else if (this.networkManager == null) {
				logger.info("Connecting to: {}", currentAddress);
				this.networkManager = new EaglercraftNetworkManager(currentAddress);
				this.networkManager.connect();
			} else {
				if (this.networkManager.isChannelOpen()) {
					if (!hasOpened) {
						hasOpened = true;
						logger.info("Logging in: {}", currentAddress);
						if (ConnectionHandshake.attemptHandshake(this.mc, this, previousGuiScreen, currentPassword,
								allowPlaintext)) {
							logger.info("Handshake Success");
							this.networkManager.setConnectionState(EnumConnectionState.PLAY);
							this.networkManager.setNetHandler(new NetHandlerPlayClient(this.mc, previousGuiScreen,
									this.networkManager, this.mc.getSession().getProfile()));
						} else {
							if (mc.currentScreen == this) {
								checkLowLevelRatelimit();
							}
							if (mc.currentScreen == this) {
								logger.info("Handshake Failure");
								mc.getSession().reset();
								mc.displayGuiScreen(
										new GuiDisconnected(previousGuiScreen, "connect.failed", new ChatComponentText(
												"Handshake Failure\n\nAre you sure this is an eagler 1.8 server?")));
							}
							if (!PlatformNetworking.playConnectionState().isClosed()) {
								PlatformNetworking.playDisconnect();
							}
							return;
						}
					}
					try {
						this.networkManager.processReceivedPackets();
					} catch (IOException ex) {
					}
				} else {
					if (PlatformNetworking.playConnectionState() == EnumEaglerConnectionState.FAILED) {
						if (!hasOpened) {
							mc.getSession().reset();
							checkLowLevelRatelimit();
							if (mc.currentScreen == this) {
								if (RateLimitTracker.isProbablyLockedOut(currentAddress)) {
									mc.displayGuiScreen(GuiDisconnected.createRateLimitKick(previousGuiScreen));
								} else {
									mc.displayGuiScreen(new GuiDisconnected(previousGuiScreen, "connect.failed",
											new ChatComponentText("Connection Refused")));
								}
							}
						}
					} else {
						if (this.networkManager.checkDisconnected()) {
							this.mc.getSession().reset();
							checkLowLevelRatelimit();
							if (mc.currentScreen == this) {
								if (RateLimitTracker.isProbablyLockedOut(currentAddress)) {
									mc.displayGuiScreen(GuiDisconnected.createRateLimitKick(previousGuiScreen));
								} else {
									mc.displayGuiScreen(new GuiDisconnected(previousGuiScreen, "connect.failed",
											new ChatComponentText("Connection Refused")));
								}
							}
						}
					}
				}
			}
		}

	}

	/**+
	 * Fired when a key is typed (except F11 which toggles full
	 * screen). This is the equivalent of
	 * KeyListener.keyTyped(KeyEvent e). Args : character (character
	 * on the key), keyCode (lwjgl Keyboard key code)
	 */
	protected void keyTyped(char parChar1, int parInt1) {
	}

	/**+
	 * Adds the buttons (and other controls) to the screen in
	 * question. Called when the GUI is displayed and when the
	 * window resizes, the buttonList is cleared beforehand.
	 */
	public void initGui() {
		this.buttonList.clear();
		this.buttonList.add(
				new GuiButton(0, this.width / 2 - 100, this.height / 2 - 10, I18n.format("gui.cancel", new Object[0])));
	}

	/**+
	 * Called by the controls from the buttonList when activated.
	 * (Mouse pressed for buttons)
	 */
	protected void actionPerformed(GuiButton parGuiButton) {
		if (parGuiButton.id == 0) {
			this.cancel = true;
			if (this.networkManager != null) {
				this.networkManager.closeChannel(new ChatComponentText("Aborted"));
			}

			this.mc.displayGuiScreen(this.previousGuiScreen);
		}

	}

	/**+
	 * Draws the screen and all the components in it. Args : mouseX,
	 * mouseY, renderPartialTicks
	 */
	public void drawScreen(int i, int j, float f) {
		this.drawDefaultBackground();
		if (this.networkManager == null || !this.networkManager.isChannelOpen()) {
			this.drawCenteredString(this.fontRendererObj, I18n.format("connect.connecting", new Object[0]),
					this.width / 2, this.height / 2 - 50, 16777215);
		} else {
			this.drawCenteredString(this.fontRendererObj, I18n.format("connect.authorizing", new Object[0]),
					this.width / 2, this.height / 2 - 50, 16777215);
		}

		super.drawScreen(i, j, f);
	}

	private void checkLowLevelRatelimit() {
		EnumServerRateLimit rateLimit = PlatformNetworking.getRateLimit();
		if (rateLimit == EnumServerRateLimit.BLOCKED) {
			RateLimitTracker.registerBlock(currentAddress);
			mc.displayGuiScreen(GuiDisconnected.createRateLimitKick(previousGuiScreen));
			logger.info("Handshake Failure: Too Many Requests!");
		} else if (rateLimit == EnumServerRateLimit.LOCKED_OUT) {
			RateLimitTracker.registerLockOut(currentAddress);
			mc.displayGuiScreen(GuiDisconnected.createRateLimitKick(previousGuiScreen));
			logger.info("Handshake Failure: Too Many Requests!");
			logger.info("Server has locked this client out");
		}
	}
}