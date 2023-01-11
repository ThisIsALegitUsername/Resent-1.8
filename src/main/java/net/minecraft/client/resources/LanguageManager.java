package net.minecraft.client.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.LanguageMetadataSection;
import net.minecraft.util.StringTranslate;

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
public class LanguageManager implements IResourceManagerReloadListener {
	private static final Logger logger = LogManager.getLogger();
	private final IMetadataSerializer theMetadataSerializer;
	private String currentLanguage;
	protected static final Locale currentLocale = new Locale();
	private Map<String, Language> languageMap = Maps.newHashMap();

	public LanguageManager(IMetadataSerializer theMetadataSerializerIn, String currentLanguageIn) {
		this.theMetadataSerializer = theMetadataSerializerIn;
		this.currentLanguage = currentLanguageIn;
		I18n.setLocale(currentLocale);
	}

	public void parseLanguageMetadata(List<IResourcePack> parList) {
		this.languageMap.clear();

		for (IResourcePack iresourcepack : parList) {
			try {
				LanguageMetadataSection languagemetadatasection = (LanguageMetadataSection) iresourcepack
						.getPackMetadata(this.theMetadataSerializer, "language");
				if (languagemetadatasection != null) {
					for (Language language : languagemetadatasection.getLanguages()) {
						if (!this.languageMap.containsKey(language.getLanguageCode())) {
							this.languageMap.put(language.getLanguageCode(), language);
						}
					}
				}
			} catch (RuntimeException runtimeexception) {
				logger.warn("Unable to parse metadata section of resourcepack: " + iresourcepack.getPackName(),
						runtimeexception);
			} catch (IOException ioexception) {
				logger.warn("Unable to parse metadata section of resourcepack: " + iresourcepack.getPackName(),
						ioexception);
			}
		}

	}

	public void onResourceManagerReload(IResourceManager iresourcemanager) {
		ArrayList arraylist = Lists.newArrayList(new String[] { "en_US" });
		if (!"en_US".equals(this.currentLanguage)) {
			arraylist.add(this.currentLanguage);
		}

		currentLocale.loadLocaleDataFiles(iresourcemanager, arraylist);
		StringTranslate.replaceWith(currentLocale.properties);
	}

	public boolean isCurrentLocaleUnicode() {
		return currentLocale.isUnicode();
	}

	public boolean isCurrentLanguageBidirectional() {
		return this.getCurrentLanguage() != null && this.getCurrentLanguage().isBidirectional();
	}

	public void setCurrentLanguage(Language currentLanguageIn) {
		this.currentLanguage = currentLanguageIn.getLanguageCode();
	}

	public Language getCurrentLanguage() {
		return this.languageMap.containsKey(this.currentLanguage)
				? (Language) this.languageMap.get(this.currentLanguage)
				: (Language) this.languageMap.get("en_US");
	}

	public SortedSet<Language> getLanguages() {
		return Sets.newTreeSet(this.languageMap.values());
	}
}