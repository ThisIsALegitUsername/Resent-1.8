package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

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
public interface ICommandSender {
	/**+
	 * Gets the name of this command sender (usually username, but
	 * possibly "Rcon")
	 */
	String getName();

	/**+
	 * Get the formatted ChatComponent that will be used for the
	 * sender's username in chat
	 */
	IChatComponent getDisplayName();

	/**+
	 * Send a chat message to the CommandSender
	 */
	void addChatMessage(IChatComponent var1);

	/**+
	 * Returns {@code true} if the CommandSender is allowed to
	 * execute the command, {@code false} if not
	 */
	boolean canCommandSenderUseCommand(int var1, String var2);

	/**+
	 * Get the position in the world. <b>{@code null} is not
	 * allowed!</b> If you are not an entity in the world, return
	 * the coordinates 0, 0, 0
	 */
	BlockPos getPosition();

	/**+
	 * Get the position vector. <b>{@code null} is not allowed!</b>
	 * If you are not an entity in the world, return 0.0D, 0.0D,
	 * 0.0D
	 */
	Vec3 getPositionVector();

	/**+
	 * Get the world, if available. <b>{@code null} is not
	 * allowed!</b> If you are not an entity in the world, return
	 * the overworld
	 */
	World getEntityWorld();

	/**+
	 * Returns the entity associated with the command sender. MAY BE
	 * NULL!
	 */
	Entity getCommandSenderEntity();

	/**+
	 * Returns true if the command sender should be sent feedback
	 * about executed commands
	 */
	boolean sendCommandFeedback();

}