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
public class ChatComponentSelector extends ChatComponentStyle {
	private final String selector;

	public ChatComponentSelector(String selectorIn) {
		this.selector = selectorIn;
	}

	/**+
	 * Gets the selector of this component, in plain text.
	 */
	public String getSelector() {
		return this.selector;
	}

	/**+
	 * Gets the text of this component, without any special
	 * formatting codes added, for chat. TODO: why is this two
	 * different methods?
	 */
	public String getUnformattedTextForChat() {
		return this.selector;
	}

	/**+
	 * Creates a copy of this component. Almost a deep copy, except
	 * the style is shallow-copied.
	 */
	public ChatComponentSelector createCopy() {
		ChatComponentSelector chatcomponentselector = new ChatComponentSelector(this.selector);
		chatcomponentselector.setChatStyle(this.getChatStyle().createShallowCopy());

		for (IChatComponent ichatcomponent : this.getSiblings()) {
			chatcomponentselector.appendSibling(ichatcomponent.createCopy());
		}

		return chatcomponentselector;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof ChatComponentSelector)) {
			return false;
		} else {
			ChatComponentSelector chatcomponentselector = (ChatComponentSelector) object;
			return this.selector.equals(chatcomponentselector.selector) && super.equals(object);
		}
	}

	public String toString() {
		return "SelectorComponent{pattern=\'" + this.selector + '\'' + ", siblings=" + this.siblings + ", style="
				+ this.getChatStyle() + '}';
	}
}