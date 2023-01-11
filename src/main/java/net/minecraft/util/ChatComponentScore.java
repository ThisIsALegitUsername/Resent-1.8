package net.minecraft.util;

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
public class ChatComponentScore extends ChatComponentStyle {
	private final String name;
	private final String objective;
	/**+
	 * The value displayed instead of the real score (may be null)
	 */
	private String value = "";

	public ChatComponentScore(String nameIn, String objectiveIn) {
		this.name = nameIn;
		this.objective = objectiveIn;
	}

	public String getName() {
		return this.name;
	}

	public String getObjective() {
		return this.objective;
	}

	/**+
	 * Sets the value displayed instead of the real score.
	 */
	public void setValue(String valueIn) {
		this.value = valueIn;
	}

	/**+
	 * Gets the text of this component, without any special
	 * formatting codes added, for chat. TODO: why is this two
	 * different methods?
	 */
	public String getUnformattedTextForChat() {
		return this.value;
	}

	/**+
	 * Creates a copy of this component. Almost a deep copy, except
	 * the style is shallow-copied.
	 */
	public ChatComponentScore createCopy() {
		ChatComponentScore chatcomponentscore = new ChatComponentScore(this.name, this.objective);
		chatcomponentscore.setValue(this.value);
		chatcomponentscore.setChatStyle(this.getChatStyle().createShallowCopy());

		for (IChatComponent ichatcomponent : this.getSiblings()) {
			chatcomponentscore.appendSibling(ichatcomponent.createCopy());
		}

		return chatcomponentscore;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof ChatComponentScore)) {
			return false;
		} else {
			ChatComponentScore chatcomponentscore = (ChatComponentScore) object;
			return this.name.equals(chatcomponentscore.name) && this.objective.equals(chatcomponentscore.objective)
					&& super.equals(object);
		}
	}

	public String toString() {
		return "ScoreComponent{name=\'" + this.name + '\'' + "objective=\'" + this.objective + '\'' + ", siblings="
				+ this.siblings + ", style=" + this.getChatStyle() + '}';
	}
}