package net.minecraft.client.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import net.lax1dude.eaglercraft.v1_8.vfs.SYS;
import org.json.JSONException;
import org.json.JSONObject;

import net.lax1dude.eaglercraft.v1_8.IOUtils;
import net.lax1dude.eaglercraft.v1_8.HString;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public abstract class AbstractResourcePack implements IResourcePack {
	private static final Logger resourceLog = LogManager.getLogger();
	protected final String resourcePackFile;

	public AbstractResourcePack(String resourcePackFileIn) {
		this.resourcePackFile = resourcePackFileIn;
	}

	private static String locationToName(ResourceLocation location) {
		return HString.format("%s/%s/%s",
				new Object[] { "assets", location.getResourceDomain(), location.getResourcePath() });
	}

	public InputStream getInputStream(ResourceLocation location) throws IOException {
		return this.getInputStreamByName(locationToName(location));
	}

	public boolean resourceExists(ResourceLocation location) {
		return this.hasResourceName(locationToName(location));
	}

	protected abstract InputStream getInputStreamByName(String var1) throws IOException;

	protected abstract boolean hasResourceName(String var1);

	protected void logNameNotLowercase(String parString1) {
		resourceLog.warn("ResourcePack: ignored non-lowercase namespace: %s in %s",
				new Object[] { parString1, this.resourcePackFile });
	}

	public <T extends IMetadataSection> T getPackMetadata(IMetadataSerializer parIMetadataSerializer, String parString1)
			throws IOException {
		try {
			return readMetadata(parIMetadataSerializer, this.getInputStreamByName("pack.mcmeta"), parString1);
		} catch (JSONException e) {
			if (SYS.VFS != null) {
				SYS.deleteResourcePack(this.resourcePackFile);
			}
			throw e;
		}
	}

	static <T extends IMetadataSection> T readMetadata(IMetadataSerializer parIMetadataSerializer,
			InputStream parInputStream, String parString1) {
		JSONObject jsonobject = null;

		try {
			jsonobject = new JSONObject(IOUtils.inputStreamToString(parInputStream, StandardCharsets.UTF_8));
		} catch (RuntimeException | IOException runtimeexception) {
			throw new JSONException(runtimeexception);
		} finally {
			IOUtils.closeQuietly(parInputStream);
		}

		return parIMetadataSerializer.parseMetadataSection(parString1, jsonobject);
	}

	public ImageData getPackImage() throws IOException {
		return TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png"));
	}

	public String getPackName() {
		return this.resourcePackFile;
	}
}