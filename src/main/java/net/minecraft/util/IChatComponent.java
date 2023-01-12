package net.minecraft.util;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.lax1dude.eaglercraft.v1_8.json.JSONTypeCodec;
import net.lax1dude.eaglercraft.v1_8.json.JSONTypeProvider;

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
public interface IChatComponent extends Iterable<IChatComponent> {
	IChatComponent setChatStyle(ChatStyle var1);

	ChatStyle getChatStyle();

	/**+
	 * Appends the given text to the end of this component.
	 */
	IChatComponent appendText(String var1);

	/**+
	 * Appends the given component to the end of this one.
	 */
	IChatComponent appendSibling(IChatComponent var1);

	/**+
	 * Gets the text of this component, without any special
	 * formatting codes added, for chat. TODO: why is this two
	 * different methods?
	 */
	String getUnformattedTextForChat();

	/**+
	 * Get the text of this component, <em>and all child
	 * components</em>, with all special formatting codes removed.
	 */
	String getUnformattedText();

	/**+
	 * Gets the text of this component, with formatting codes added
	 * for rendering.
	 */
	String getFormattedText();

	/**+
	 * Gets the sibling components of this one.
	 */
	List<IChatComponent> getSiblings();

	/**+
	 * Creates a copy of this component. Almost a deep copy, except
	 * the style is shallow-copied.
	 */
	IChatComponent createCopy();

	public static class Serializer implements JSONTypeCodec<IChatComponent, Object> {

		public IChatComponent deserialize(Object parJsonElement) throws JSONException {
			if (parJsonElement instanceof String) {
				return new ChatComponentText((String) parJsonElement);
			} else if (!(parJsonElement instanceof JSONObject)) {
				if (parJsonElement instanceof JSONArray) {
					JSONArray jsonarray1 = (JSONArray) parJsonElement;
					IChatComponent ichatcomponent = null;

					for (Object jsonelement : jsonarray1) {
						IChatComponent ichatcomponent1 = this.deserialize(jsonelement);
						if (ichatcomponent == null) {
							ichatcomponent = ichatcomponent1;
						} else {
							ichatcomponent.appendSibling(ichatcomponent1);
						}
					}

					return ichatcomponent;
				} else {
					throw new JSONException("Don\'t know how to turn " + parJsonElement.getClass().getSimpleName()
							+ " into a Component");
				}
			} else {
				JSONObject jsonobject = (JSONObject) parJsonElement;
				Object object;
				if (jsonobject.has("text")) {
					object = new ChatComponentText(jsonobject.getString("text"));
				} else if (jsonobject.has("translate")) {
					String s = jsonobject.getString("translate");
					if (jsonobject.has("with")) {
						JSONArray jsonarray = jsonobject.getJSONArray("with");
						Object[] aobject = new Object[jsonarray.length()];

						for (int i = 0; i < aobject.length; ++i) {
							aobject[i] = this.deserialize(jsonarray.get(i));
							if (aobject[i] instanceof ChatComponentText) {
								ChatComponentText chatcomponenttext = (ChatComponentText) aobject[i];
								if (chatcomponenttext.getChatStyle().isEmpty()
										&& chatcomponenttext.getSiblings().isEmpty()) {
									aobject[i] = chatcomponenttext.getChatComponentText_TextValue();
								}
							}
						}

						object = new ChatComponentTranslation(s, aobject);
					} else {
						object = new ChatComponentTranslation(s, new Object[0]);
					}
				} else if (jsonobject.has("score")) {
					JSONObject jsonobject1 = jsonobject.getJSONObject("score");
					if (!jsonobject1.has("name") || !jsonobject1.has("objective")) {
						throw new JSONException("A score component needs a least a name and an objective");
					}

					object = new ChatComponentScore(jsonobject1.getString("name"), jsonobject1.getString("objective"));
					if (jsonobject1.has("value")) {
						((ChatComponentScore) object).setValue(jsonobject1.getString("value"));
					}
				} else {
					if (!jsonobject.has("selector")) {
						throw new JSONException(
								"Don\'t know how to turn " + parJsonElement.toString() + " into a Component");
					}

					object = new ChatComponentSelector(jsonobject.getString("selector"));
				}

				if (jsonobject.has("extra")) {
					JSONArray jsonarray2 = jsonobject.getJSONArray("extra");
					if (jsonarray2.length() <= 0) {
						throw new JSONException("Unexpected empty array of components");
					}

					for (int j = 0; j < jsonarray2.length(); ++j) {
						((IChatComponent) object).appendSibling(this.deserialize(jsonarray2.get(j)));
					}
				}

				((IChatComponent) object).setChatStyle(JSONTypeProvider.deserialize(parJsonElement, ChatStyle.class));
				return (IChatComponent) object;
			}
		}

		private void serializeChatStyle(ChatStyle style, JSONObject object) {
			JSONObject jsonelement = JSONTypeProvider.serialize(style);
			for (String entry : jsonelement.keySet()) {
				object.put(entry, jsonelement.get(entry));
			}
		}

		public Object serialize(IChatComponent ichatcomponent) {
			if (ichatcomponent instanceof ChatComponentText && ichatcomponent.getChatStyle().isEmpty()
					&& ichatcomponent.getSiblings().isEmpty()) {
				return ((ChatComponentText) ichatcomponent).getChatComponentText_TextValue();
			} else {
				JSONObject jsonobject = new JSONObject();
				if (!ichatcomponent.getChatStyle().isEmpty()) {
					this.serializeChatStyle(ichatcomponent.getChatStyle(), jsonobject);
				}

				if (!ichatcomponent.getSiblings().isEmpty()) {
					JSONArray jsonarray = new JSONArray();

					for (IChatComponent ichatcomponent1 : ichatcomponent.getSiblings()) {
						jsonarray.put(this.serialize(ichatcomponent1));
					}

					jsonobject.put("extra", jsonarray);
				}

				if (ichatcomponent instanceof ChatComponentText) {
					jsonobject.put("text", ((ChatComponentText) ichatcomponent).getChatComponentText_TextValue());
				} else if (ichatcomponent instanceof ChatComponentTranslation) {
					ChatComponentTranslation chatcomponenttranslation = (ChatComponentTranslation) ichatcomponent;
					jsonobject.put("translate", chatcomponenttranslation.getKey());
					if (chatcomponenttranslation.getFormatArgs() != null
							&& chatcomponenttranslation.getFormatArgs().length > 0) {
						JSONArray jsonarray1 = new JSONArray();

						for (Object object : chatcomponenttranslation.getFormatArgs()) {
							if (object instanceof IChatComponent) {
								jsonarray1.put(this.serialize((IChatComponent) object));
							} else {
								jsonarray1.put(String.valueOf(object));
							}
						}

						jsonobject.put("with", jsonarray1);
					}
				} else if (ichatcomponent instanceof ChatComponentScore) {
					ChatComponentScore chatcomponentscore = (ChatComponentScore) ichatcomponent;
					JSONObject jsonobject1 = new JSONObject();
					jsonobject1.put("name", chatcomponentscore.getName());
					jsonobject1.put("objective", chatcomponentscore.getObjective());
					jsonobject1.put("value", chatcomponentscore.getUnformattedTextForChat());
					jsonobject.put("score", jsonobject1);
				} else {
					if (!(ichatcomponent instanceof ChatComponentSelector)) {
						throw new IllegalArgumentException(
								"Don\'t know how to serialize " + ichatcomponent + " as a Component");
					}

					ChatComponentSelector chatcomponentselector = (ChatComponentSelector) ichatcomponent;
					jsonobject.put("selector", chatcomponentselector.getSelector());
				}

				return jsonobject;
			}
		}

		/**
		 * So sorry for this implementation
		 */
		public static String componentToJson(IChatComponent component) {
			if (component instanceof ChatComponentText) {
				String escaped = new JSONObject().put("E", component.getUnformattedTextForChat()).toString();
				return escaped.substring(5, escaped.length() - 1);
			} else {
				return JSONTypeProvider.serialize(component).toString();
			}
		}

		public static IChatComponent jsonToComponent(String json) {
			return (IChatComponent) JSONTypeProvider.deserialize(json, IChatComponent.class);
		}
	}

	public static IChatComponent join(List<IChatComponent> components) {
		ChatComponentText chatcomponenttext = new ChatComponentText("");

		for (int i = 0; i < components.size(); ++i) {
			if (i > 0) {
				if (i == components.size() - 1) {
					chatcomponenttext.appendText(" and ");
				} else if (i > 0) {
					chatcomponenttext.appendText(", ");
				}
			}

			chatcomponenttext.appendSibling((IChatComponent) components.get(i));
		}

		return chatcomponenttext;
	}
}