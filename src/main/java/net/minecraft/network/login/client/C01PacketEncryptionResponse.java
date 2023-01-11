package net.minecraft.network.login.client;

import java.io.IOException;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.INetHandlerLoginServer;

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
public class C01PacketEncryptionResponse implements Packet<INetHandlerLoginServer> {
	private byte[] secretKeyEncrypted = new byte[0];
	private byte[] verifyTokenEncrypted = new byte[0];

	public C01PacketEncryptionResponse() {
	}

//	public C01PacketEncryptionResponse(SecretKey secretKey, PublicKey publicKey, byte[] verifyToken) {
//		this.secretKeyEncrypted = CryptManager.encryptData(publicKey, secretKey.getEncoded());
//		this.verifyTokenEncrypted = CryptManager.encryptData(publicKey, verifyToken);
//	}

	/**+
	 * Reads the raw packet data from the data stream.
	 */
	public void readPacketData(PacketBuffer parPacketBuffer) throws IOException {
		this.secretKeyEncrypted = parPacketBuffer.readByteArray();
		this.verifyTokenEncrypted = parPacketBuffer.readByteArray();
	}

	/**+
	 * Writes the raw packet data to the data stream.
	 */
	public void writePacketData(PacketBuffer parPacketBuffer) throws IOException {
		parPacketBuffer.writeByteArray(this.secretKeyEncrypted);
		parPacketBuffer.writeByteArray(this.verifyTokenEncrypted);
	}

	/**+
	 * Passes this Packet on to the NetHandler for processing.
	 */
	public void processPacket(INetHandlerLoginServer inethandlerloginserver) {
		inethandlerloginserver.processEncryptionResponse(this);
	}

//	public SecretKey getSecretKey(PrivateKey key) {
//		return CryptManager.decryptSharedKey(key, this.secretKeyEncrypted);
//	}

//	public byte[] getVerifyToken(PrivateKey key) {
//		return key == null ? this.verifyTokenEncrypted : CryptManager.decryptData(key, this.verifyTokenEncrypted);
//	}
}