package net.minecraft.event;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.IChatComponent;

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
public class HoverEvent {
	private final HoverEvent.Action action;
	private final IChatComponent value;

	public HoverEvent(HoverEvent.Action actionIn, IChatComponent valueIn) {
		this.action = actionIn;
		this.value = valueIn;
	}

	/**+
	 * Gets the action to perform when this event is raised.
	 */
	public HoverEvent.Action getAction() {
		return this.action;
	}

	/**+
	 * Gets the value to perform the action on when this event is
	 * raised. For example, if the action is "show item", this would
	 * be the item to show.
	 */
	public IChatComponent getValue() {
		return this.value;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			HoverEvent hoverevent = (HoverEvent) object;
			if (this.action != hoverevent.action) {
				return false;
			} else {
				if (this.value != null) {
					if (!this.value.equals(hoverevent.value)) {
						return false;
					}
				} else if (hoverevent.value != null) {
					return false;
				}

				return true;
			}
		} else {
			return false;
		}
	}

	public String toString() {
		return "HoverEvent{action=" + this.action + ", value=\'" + this.value + '\'' + '}';
	}

	public int hashCode() {
		int i = this.action.hashCode();
		i = 31 * i + (this.value != null ? this.value.hashCode() : 0);
		return i;
	}

	public static enum Action {
		SHOW_TEXT("show_text", true), SHOW_ACHIEVEMENT("show_achievement", true), SHOW_ITEM("show_item", true),
		SHOW_ENTITY("show_entity", true);

		private static final Map<String, HoverEvent.Action> nameMapping = Maps.newHashMap();
		private final boolean allowedInChat;
		private final String canonicalName;

		private Action(String canonicalNameIn, boolean allowedInChatIn) {
			this.canonicalName = canonicalNameIn;
			this.allowedInChat = allowedInChatIn;
		}

		public boolean shouldAllowInChat() {
			return this.allowedInChat;
		}

		public String getCanonicalName() {
			return this.canonicalName;
		}

		public static HoverEvent.Action getValueByCanonicalName(String canonicalNameIn) {
			return (HoverEvent.Action) nameMapping.get(canonicalNameIn);
		}

		static {
			for (HoverEvent.Action hoverevent$action : values()) {
				nameMapping.put(hoverevent$action.getCanonicalName(), hoverevent$action);
			}

		}
	}
}