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
public class ChatComponentText extends ChatComponentStyle {
	private final String text;

	public ChatComponentText(String msg) {
		this.text = msg;
	}

	/**+
	 * Gets the text value of this ChatComponentText. TODO: what are
	 * getUnformattedText and getUnformattedTextForChat missing that
	 * made someone decide to create a third equivalent method that
	 * only ChatComponentText can implement?
	 */
	public String getChatComponentText_TextValue() {
		return this.text;
	}

	/**+
	 * Gets the text of this component, without any special
	 * formatting codes added, for chat. TODO: why is this two
	 * different methods?
	 */
	public String getUnformattedTextForChat() {
		return this.text;
	}

	/**+
	 * Creates a copy of this component. Almost a deep copy, except
	 * the style is shallow-copied.
	 */
	public ChatComponentText createCopy() {
		ChatComponentText chatcomponenttext = new ChatComponentText(this.text);
		chatcomponenttext.setChatStyle(this.getChatStyle().createShallowCopy());

		for (IChatComponent ichatcomponent : this.getSiblings()) {
			chatcomponenttext.appendSibling(ichatcomponent.createCopy());
		}

		return chatcomponenttext;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (!(object instanceof ChatComponentText)) {
			return false;
		} else {
			ChatComponentText chatcomponenttext = (ChatComponentText) object;
			return this.text.equals(chatcomponenttext.getChatComponentText_TextValue()) && super.equals(object);
		}
	}

	public String toString() {
		return "TextComponent{text=\'" + this.text + '\'' + ", siblings=" + this.siblings + ", style="
				+ this.getChatStyle() + '}';
	}
}