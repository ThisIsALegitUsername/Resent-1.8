package net.minecraft.event;

import java.util.Map;

import com.google.common.collect.Maps;

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
public class ClickEvent {
	private final ClickEvent.Action action;
	private final String value;

	public ClickEvent(ClickEvent.Action theAction, String theValue) {
		this.action = theAction;
		this.value = theValue;
	}

	/**+
	 * Gets the action to perform when this event is raised.
	 */
	public ClickEvent.Action getAction() {
		return this.action;
	}

	/**+
	 * Gets the value to perform the action on when this event is
	 * raised. For example, if the action is "open URL", this would
	 * be the URL to open.
	 */
	public String getValue() {
		return this.value;
	}

	public boolean equals(Object object) {
		if (this == object) {
			return true;
		} else if (object != null && this.getClass() == object.getClass()) {
			ClickEvent clickevent = (ClickEvent) object;
			if (this.action != clickevent.action) {
				return false;
			} else {
				if (this.value != null) {
					if (!this.value.equals(clickevent.value)) {
						return false;
					}
				} else if (clickevent.value != null) {
					return false;
				}

				return true;
			}
		} else {
			return false;
		}
	}

	public String toString() {
		return "ClickEvent{action=" + this.action + ", value=\'" + this.value + '\'' + '}';
	}

	public int hashCode() {
		int i = this.action.hashCode();
		i = 31 * i + (this.value != null ? this.value.hashCode() : 0);
		return i;
	}

	public static enum Action {
		OPEN_URL("open_url", true), OPEN_FILE("open_file", false), RUN_COMMAND("run_command", true),
		TWITCH_USER_INFO("twitch_user_info", false), SUGGEST_COMMAND("suggest_command", true),
		CHANGE_PAGE("change_page", true);

		private static final Map<String, ClickEvent.Action> nameMapping = Maps.newHashMap();
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

		public static ClickEvent.Action getValueByCanonicalName(String canonicalNameIn) {
			return (ClickEvent.Action) nameMapping.get(canonicalNameIn);
		}

		static {
			for (ClickEvent.Action clickevent$action : values()) {
				nameMapping.put(clickevent$action.getCanonicalName(), clickevent$action);
			}

		}
	}
}