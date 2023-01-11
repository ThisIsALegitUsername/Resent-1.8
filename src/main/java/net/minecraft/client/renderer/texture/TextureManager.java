package net.minecraft.client.renderer.texture;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.lax1dude.eaglercraft.v1_8.HString;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

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
public class TextureManager implements ITickable, IResourceManagerReloadListener {
	private static final Logger logger = LogManager.getLogger();
	private final Map<ResourceLocation, ITextureObject> mapTextureObjects = Maps.newHashMap();
	private final List<ITickable> listTickables = Lists.newArrayList();
	private final Map<String, Integer> mapTextureCounters = Maps.newHashMap();
	private IResourceManager theResourceManager;

	public TextureManager(IResourceManager resourceManager) {
		this.theResourceManager = resourceManager;
	}

	public void bindTexture(ResourceLocation resource) {
		if (resource.cachedPointer != null) {
			TextureUtil.bindTexture(((ITextureObject) resource.cachedPointer).getGlTextureId()); // unsafe, lol
		} else {
			Object object = (ITextureObject) this.mapTextureObjects.get(resource);
			if (object == null) {
				object = new SimpleTexture(resource);
				this.loadTexture(resource, (ITextureObject) object);
			}

			resource.cachedPointer = object;
			TextureUtil.bindTexture(((ITextureObject) object).getGlTextureId());
		}
	}

	public boolean loadTickableTexture(ResourceLocation textureLocation, ITickableTextureObject textureObj) {
		if (this.loadTexture(textureLocation, textureObj)) {
			this.listTickables.add(textureObj);
			return true;
		} else {
			return false;
		}
	}

	public boolean loadTexture(ResourceLocation textureLocation, ITextureObject textureObj) {
		boolean flag = true;

		try {
			((ITextureObject) textureObj).loadTexture(this.theResourceManager);
		} catch (IOException ioexception) {
			logger.warn("Failed to load texture: " + textureLocation, ioexception);
			textureObj = TextureUtil.missingTexture;
			this.mapTextureObjects.put(textureLocation, textureObj);
			flag = false;
		} catch (Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Registering texture");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Resource location being registered");
			crashreportcategory.addCrashSection("Resource location", textureLocation);
			final ITextureObject textureObj2 = textureObj;
			crashreportcategory.addCrashSectionCallable("Texture object class", new Callable<String>() {
				public String call() throws Exception {
					return textureObj2.getClass().getName();
				}
			});
			throw new ReportedException(crashreport);
		}

		textureLocation.cachedPointer = textureObj;
		this.mapTextureObjects.put(textureLocation, textureObj);
		return flag;
	}

	public ITextureObject getTexture(ResourceLocation textureLocation) {
		if (textureLocation.cachedPointer != null) {
			return (ITextureObject) textureLocation.cachedPointer;
		} else {
			return (ITextureObject) (textureLocation.cachedPointer = this.mapTextureObjects.get(textureLocation));
		}
	}

	public ResourceLocation getDynamicTextureLocation(String name, DynamicTexture texture) {
		Integer integer = (Integer) this.mapTextureCounters.get(name);
		if (integer == null) {
			integer = Integer.valueOf(1);
		} else {
			integer = Integer.valueOf(integer.intValue() + 1);
		}

		this.mapTextureCounters.put(name, integer);
		ResourceLocation resourcelocation = new ResourceLocation(
				HString.format("dynamic/%s_%d", new Object[] { name, integer }));
		this.loadTexture(resourcelocation, texture);
		return resourcelocation;
	}

	public void tick() {
		for (ITickable itickable : this.listTickables) {
			itickable.tick();
		}

	}

	public void deleteTexture(ResourceLocation textureLocation) {
		ITextureObject itextureobject = this.mapTextureObjects.remove(textureLocation);
		if (itextureobject != null) {
			TextureUtil.deleteTexture(itextureobject.getGlTextureId());
		}
	}

	public void onResourceManagerReload(IResourceManager var1) {
		for (Entry entry : this.mapTextureObjects.entrySet()) {
			this.loadTexture((ResourceLocation) entry.getKey(), (ITextureObject) entry.getValue());
		}

	}
}