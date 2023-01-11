package net.minecraft.client.resources;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.IOUtils;
import net.lax1dude.eaglercraft.v1_8.vfs.FolderResourcePack;
import net.lax1dude.eaglercraft.v1_8.vfs.SYS;
import net.lax1dude.eaglercraft.v1_8.futures.ListenableFuture;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
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
public class ResourcePackRepository {
	private static final Logger logger = LogManager.getLogger();
	public final IResourcePack rprDefaultResourcePack;
	public final IMetadataSerializer rprMetadataSerializer;
	private IResourcePack resourcePackInstance;
	private ListenableFuture<Object> field_177322_i;
	private List<ResourcePackRepository.Entry> repositoryEntriesAll = Lists.newArrayList();
	private List<ResourcePackRepository.Entry> repositoryEntries = Lists.newArrayList();

	public ResourcePackRepository(IResourcePack rprDefaultResourcePackIn, IMetadataSerializer rprMetadataSerializerIn,
			GameSettings settings) {
		this.rprDefaultResourcePack = rprDefaultResourcePackIn;
		this.rprMetadataSerializer = rprMetadataSerializerIn;
		this.updateRepositoryEntriesAll();
		Iterator iterator = settings.resourcePacks.iterator();

		while (iterator.hasNext()) {
			String s = (String) iterator.next();

			for (ResourcePackRepository.Entry resourcepackrepository$entry : this.repositoryEntriesAll) {
				if (resourcepackrepository$entry.getResourcePackName().equals(s)) {
					if (resourcepackrepository$entry.func_183027_f() == 1
							|| settings.field_183018_l.contains(resourcepackrepository$entry.getResourcePackName())) {
						this.repositoryEntries.add(resourcepackrepository$entry);
						break;
					}

					iterator.remove();
					logger.warn("Removed selected resource pack {} because it\'s no longer compatible",
							new Object[] { resourcepackrepository$entry.getResourcePackName() });
				}
			}
		}

	}

	public void updateRepositoryEntriesAll() {
		if (SYS.VFS == null)
			return;

		List<ResourcePackRepository.Entry> list = Lists.<ResourcePackRepository.Entry>newArrayList();

		for (String file1 : SYS.getResourcePackNames()) {
			ResourcePackRepository.Entry resourcepackrepository$entry = new ResourcePackRepository.Entry(file1);

			if (!this.repositoryEntriesAll.contains(resourcepackrepository$entry)) {
				try {
					resourcepackrepository$entry.updateResourcePack();
					list.add(resourcepackrepository$entry);
				} catch (Exception var6) {
					logger.error("Failed to call \"updateResourcePack\" for resource pack \"{}\"",
							resourcepackrepository$entry.resourcePackFile);
					logger.error(var6);
					list.remove(resourcepackrepository$entry);
				}
			} else {
				int i = this.repositoryEntriesAll.indexOf(resourcepackrepository$entry);

				if (i > -1 && i < this.repositoryEntriesAll.size()) {
					list.add(this.repositoryEntriesAll.get(i));
				}
			}
		}

		this.repositoryEntriesAll.removeAll(list);

		for (ResourcePackRepository.Entry resourcepackrepository$entry1 : this.repositoryEntriesAll) {
			resourcepackrepository$entry1.closeResourcePack();
		}

		this.repositoryEntriesAll = list;
	}

	public List<ResourcePackRepository.Entry> getRepositoryEntriesAll() {
		return ImmutableList.copyOf(this.repositoryEntriesAll);
	}

	public List<ResourcePackRepository.Entry> getRepositoryEntries() {
		return ImmutableList.copyOf(this.repositoryEntries);
	}

	public void setRepositories(List<ResourcePackRepository.Entry> parList) {
		this.repositoryEntries.clear();
		this.repositoryEntries.addAll(parList);
	}

	public void downloadResourcePack(String s1, String s2, Consumer<Boolean> cb) {
		SYS.loadRemoteResourcePack(s1, s2, res -> {
			if (res != null) {
				ResourcePackRepository.this.resourcePackInstance = new FolderResourcePack(res, "srp/");
				Minecraft.getMinecraft().scheduleResourcesRefresh();
				cb.accept(true);
				return;
			}
			cb.accept(false);
		}, runnable -> {
			Minecraft.getMinecraft().addScheduledTask(runnable);
		}, () -> {
			Minecraft.getMinecraft().loadingScreen.eaglerShow(I18n.format("resourcePack.load.loading"),
					"Server resource pack");
		});
	}

	/**+
	 * Getter for the IResourcePack instance associated with this
	 * ResourcePackRepository
	 */
	public IResourcePack getResourcePackInstance() {
		return this.resourcePackInstance;
	}

	public void func_148529_f() {
		if (this.resourcePackInstance != null) {
			this.resourcePackInstance = null;
			Minecraft.getMinecraft().scheduleResourcesRefresh();
		}
	}

	public class Entry {
		private final String resourcePackFile;
		private IResourcePack reResourcePack;
		private PackMetadataSection rePackMetadataSection;
		private ImageData texturePackIcon;
		private ResourceLocation locationTexturePackIcon;
		private TextureManager iconTextureManager;

		private Entry(String resourcePackFileIn) {
			this.resourcePackFile = resourcePackFileIn;
		}

		public void updateResourcePack() throws IOException {
			if (SYS.VFS == null)
				return;
			this.reResourcePack = (IResourcePack) new FolderResourcePack(this.resourcePackFile, "resourcepacks/");
			this.rePackMetadataSection = (PackMetadataSection) this.reResourcePack
					.getPackMetadata(ResourcePackRepository.this.rprMetadataSerializer, "pack");

			try {
				this.texturePackIcon = this.reResourcePack.getPackImage();
			} catch (IOException var2) {
				logger.error("Failed to load resource pack icon for \"{}\"!", resourcePackFile);
				logger.error(var2);
			}

			if (this.texturePackIcon == null) {
				this.texturePackIcon = ResourcePackRepository.this.rprDefaultResourcePack.getPackImage();
			}

			this.closeResourcePack();
		}

		public void bindTexturePackIcon(TextureManager textureManagerIn) {
			if (this.locationTexturePackIcon == null) {
				this.iconTextureManager = textureManagerIn;
				this.locationTexturePackIcon = textureManagerIn.getDynamicTextureLocation("texturepackicon",
						new DynamicTexture(this.texturePackIcon));
			}

			textureManagerIn.bindTexture(this.locationTexturePackIcon);
		}

		public void closeResourcePack() {
			if (this.locationTexturePackIcon != null) {
				this.iconTextureManager.deleteTexture(this.locationTexturePackIcon);
				this.locationTexturePackIcon = null;
			}
			if (this.reResourcePack instanceof Closeable) {
				IOUtils.closeQuietly((Closeable) this.reResourcePack);
			}

		}

		public IResourcePack getResourcePack() {
			return this.reResourcePack;
		}

		public String getResourcePackName() {
			return this.reResourcePack.getPackName();
		}

		public String getTexturePackDescription() {
			return this.rePackMetadataSection == null
					? EnumChatFormatting.RED + "Invalid pack.mcmeta (or missing \'pack\' section)"
					: this.rePackMetadataSection.getPackDescription().getFormattedText();
		}

		public int func_183027_f() {
			return this.rePackMetadataSection.getPackFormat();
		}

		public boolean equals(Object object) {
			return this == object ? true
					: (object instanceof ResourcePackRepository.Entry ? this.toString().equals(object.toString())
							: false);
		}

		public int hashCode() {
			return this.toString().hashCode();
		}

		public String toString() {
			return this.resourcePackFile;
		}
	}
}