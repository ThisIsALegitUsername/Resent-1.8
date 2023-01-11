package net.minecraft.util;

import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.EntityNotFoundException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.Entity;

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
public class ChatComponentProcessor {
	public static IChatComponent processComponent(ICommandSender commandSender, IChatComponent component,
			Entity entityIn) throws CommandException {
		Object object = null;
		if (component instanceof ChatComponentScore) {
			ChatComponentScore chatcomponentscore = (ChatComponentScore) component;
			String s = chatcomponentscore.getName();
			if (PlayerSelector.hasArguments(s)) {
				List list = PlayerSelector.matchEntities(commandSender, s, Entity.class);
				if (list.size() != 1) {
					throw new EntityNotFoundException();
				}

				s = ((Entity) list.get(0)).getName();
			}

			object = entityIn != null && s.equals("*")
					? new ChatComponentScore(entityIn.getName(), chatcomponentscore.getObjective())
					: new ChatComponentScore(s, chatcomponentscore.getObjective());
			((ChatComponentScore) object).setValue(chatcomponentscore.getUnformattedTextForChat());
		} else if (component instanceof ChatComponentSelector) {
			String s1 = ((ChatComponentSelector) component).getSelector();
			object = PlayerSelector.matchEntitiesToChatComponent(commandSender, s1);
			if (object == null) {
				object = new ChatComponentText("");
			}
		} else if (component instanceof ChatComponentText) {
			object = new ChatComponentText(((ChatComponentText) component).getChatComponentText_TextValue());
		} else {
			if (!(component instanceof ChatComponentTranslation)) {
				return component;
			}

			Object[] aobject = ((ChatComponentTranslation) component).getFormatArgs();

			for (int i = 0; i < aobject.length; ++i) {
				Object object1 = aobject[i];
				if (object1 instanceof IChatComponent) {
					aobject[i] = processComponent(commandSender, (IChatComponent) object1, entityIn);
				}
			}

			object = new ChatComponentTranslation(((ChatComponentTranslation) component).getKey(), aobject);
		}

		ChatStyle chatstyle = component.getChatStyle();
		if (chatstyle != null) {
			((IChatComponent) object).setChatStyle(chatstyle.createShallowCopy());
		}

		for (IChatComponent ichatcomponent : component.getSiblings()) {
			((IChatComponent) object).appendSibling(processComponent(commandSender, ichatcomponent, entityIn));
		}

		return (IChatComponent) object;
	}
}