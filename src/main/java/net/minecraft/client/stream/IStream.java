package net.minecraft.client.stream;

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
public interface IStream {
	/**+
	 * Shuts down a steam
	 */
	void shutdownStream();

	void func_152935_j();

	void func_152922_k();

	boolean func_152936_l();

	boolean isReadyToBroadcast();

	boolean isBroadcasting();

	boolean isPaused();

	void requestCommercial();

	/**+
	 * pauses a stream
	 */
	void pause();

	/**+
	 * unpauses a stream
	 */
	void unpause();

	void updateStreamVolume();

	void func_152930_t();

	void stopBroadcasting();

	void func_152909_x();

	boolean func_152908_z();

	int func_152920_A();

	boolean func_152927_B();

	String func_152921_C();

	void func_152917_b(String var1);

	boolean func_152928_D();

	boolean func_152913_F();

	/**+
	 * mutes or unmutes the microphone based on the boolean
	 * parameter passed into the method
	 */
	void muteMicrophone(boolean var1);

	boolean func_152929_G();

	IStream.AuthFailureReason func_152918_H();

	public static enum AuthFailureReason {
		ERROR, INVALID_TOKEN;
	}
}