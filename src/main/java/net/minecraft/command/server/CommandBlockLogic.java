package net.minecraft.command.server;

import java.text.SimpleDateFormat;

import net.lax1dude.eaglercraft.v1_8.netty.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public abstract class CommandBlockLogic {
	/**+
	 * The formatting for the timestamp on commands run.
	 */
	private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("HH:mm:ss");
	private int successCount;
	private boolean trackOutput = true;
	/**+
	 * The previously run command.
	 */
	private IChatComponent lastOutput = null;
	/**+
	 * The command stored in the command block.
	 */
	private String commandStored = "";
	/**+
	 * The custom name of the command block. (defaults to "@")
	 */
	private String customName = "@";

	/**+
	 * returns the successCount int.
	 */
	public int getSuccessCount() {
		return this.successCount;
	}

	/**+
	 * Returns the lastOutput.
	 */
	public IChatComponent getLastOutput() {
		return this.lastOutput;
	}

	/**+
	 * Stores data to NBT format.
	 */
	public void writeDataToNBT(NBTTagCompound tagCompound) {
		tagCompound.setString("Command", this.commandStored);
		tagCompound.setInteger("SuccessCount", this.successCount);
		tagCompound.setString("CustomName", this.customName);
		tagCompound.setBoolean("TrackOutput", this.trackOutput);
		if (this.lastOutput != null && this.trackOutput) {
			tagCompound.setString("LastOutput", IChatComponent.Serializer.componentToJson(this.lastOutput));
		}
	}

	/**+
	 * Reads NBT formatting and stored data into variables.
	 */
	public void readDataFromNBT(NBTTagCompound nbt) {
		this.commandStored = nbt.getString("Command");
		this.successCount = nbt.getInteger("SuccessCount");
		if (nbt.hasKey("CustomName", 8)) {
			this.customName = nbt.getString("CustomName");
		}

		if (nbt.hasKey("TrackOutput", 1)) {
			this.trackOutput = nbt.getBoolean("TrackOutput");
		}

		if (nbt.hasKey("LastOutput", 8) && this.trackOutput) {
			this.lastOutput = IChatComponent.Serializer.jsonToComponent(nbt.getString("LastOutput"));
		}
	}

	/**+
	 * Returns {@code true} if the CommandSender is allowed to
	 * execute the command, {@code false} if not
	 */
	public boolean canCommandSenderUseCommand(int i, String var2) {
		return i <= 2;
	}

	/**+
	 * Sets the command.
	 */
	public void setCommand(String command) {
		this.commandStored = command;
		this.successCount = 0;
	}

	/**+
	 * Returns the command of the command block.
	 */
	public String getCommand() {
		return this.commandStored;
	}

	public void trigger(World worldIn) {

	}

	/**+
	 * Gets the name of this command sender (usually username, but
	 * possibly "Rcon")
	 */
	public String getName() {
		return this.customName;
	}

	/**+
	 * Get the formatted ChatComponent that will be used for the
	 * sender's username in chat
	 */
	public IChatComponent getDisplayName() {
		return new ChatComponentText(this.getName());
	}

	public void setName(String parString1) {
		this.customName = parString1;
	}

	/**+
	 * Send a chat message to the CommandSender
	 */
	public void addChatMessage(IChatComponent ichatcomponent) {

	}

	/**+
	 * Returns true if the command sender should be sent feedback
	 * about executed commands
	 */
	public boolean sendCommandFeedback() {
		return true;
	}

	public abstract void updateCommand();

	public abstract int func_145751_f();

	public abstract void func_145757_a(ByteBuf var1);

	public void setLastOutput(IChatComponent lastOutputMessage) {
		this.lastOutput = lastOutputMessage;
	}

	public void setTrackOutput(boolean shouldTrackOutput) {
		this.trackOutput = shouldTrackOutput;
	}

	public boolean shouldTrackOutput() {
		return this.trackOutput;
	}

	public boolean tryOpenEditCommandBlock(EntityPlayer playerIn) {
		if (!playerIn.capabilities.isCreativeMode) {
			return false;
		} else {
			playerIn.openEditCommandBlock(this);
			return true;
		}
	}
}