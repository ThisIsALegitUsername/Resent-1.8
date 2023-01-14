package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.lax1dude.eaglercraft.v1_8.HString;
import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerTextureAtlasSprite;
import net.lax1dude.eaglercraft.v1_8.opengl.ImageData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.MathHelper;
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
public class TextureMap extends AbstractTexture implements ITickableTextureObject {

    private static final Logger logger = LogManager.getLogger();
    public static final ResourceLocation LOCATION_MISSING_TEXTURE = new ResourceLocation("missingno");
    public static final ResourceLocation locationBlocksTexture = new ResourceLocation("textures/atlas/blocks.png");
    private final List<EaglerTextureAtlasSprite> listAnimatedSprites;
    private final Map<String, EaglerTextureAtlasSprite> mapRegisteredSprites;
    private final Map<String, EaglerTextureAtlasSprite> mapUploadedSprites;
    private final String basePath;
    private final IIconCreator iconCreator;
    private int mipmapLevels;
    private final EaglerTextureAtlasSprite missingImage;
    private int width;
    private int height;

    public TextureMap(String parString1) {
        this(parString1, (IIconCreator) null);
    }

    public TextureMap(String parString1, IIconCreator iconCreatorIn) {
        this.listAnimatedSprites = Lists.newArrayList();
        this.mapRegisteredSprites = Maps.newHashMap();
        this.mapUploadedSprites = Maps.newHashMap();
        this.missingImage = new EaglerTextureAtlasSprite("missingno");
        this.basePath = parString1;
        this.iconCreator = iconCreatorIn;
    }

    private void initMissingImage() {
        int[] aint = TextureUtil.missingTextureData;
        this.missingImage.setIconWidth(16);
        this.missingImage.setIconHeight(16);
        int[][] aint1 = new int[this.mipmapLevels + 1][];
        aint1[0] = aint;
        this.missingImage.setFramesTextureData(Lists.newArrayList(new int[][][] { aint1 }));
    }

    public void loadTexture(IResourceManager parIResourceManager) throws IOException {
        if (this.iconCreator != null) {
            this.loadSprites(parIResourceManager, this.iconCreator);
        }
    }

    public void loadSprites(IResourceManager resourceManager, IIconCreator parIIconCreator) {
        destroyAnimationCaches();
        this.mapRegisteredSprites.clear();
        parIIconCreator.registerSprites(this);
        this.initMissingImage();
        this.deleteGlTexture();
        this.loadTextureAtlas(resourceManager);
    }

    public void loadTextureAtlas(IResourceManager resourceManager) {
        int i = Minecraft.getGLMaximumTextureSize();
        Stitcher stitcher = new Stitcher(i, i, true, 0, this.mipmapLevels);
        this.mapUploadedSprites.clear();
        this.listAnimatedSprites.clear();
        int j = Integer.MAX_VALUE;
        int k = 1 << this.mipmapLevels;

        for (Entry entry : this.mapRegisteredSprites.entrySet()) {
            EaglerTextureAtlasSprite textureatlassprite = (EaglerTextureAtlasSprite) entry.getValue();
            ResourceLocation resourcelocation = new ResourceLocation(textureatlassprite.getIconName());
            ResourceLocation resourcelocation1 = this.completeResourceLocation(resourcelocation, 0);

            try {
                IResource iresource = resourceManager.getResource(resourcelocation1);
                ImageData[] abufferedimage = new ImageData[1 + this.mipmapLevels];
                abufferedimage[0] = TextureUtil.readBufferedImage(iresource.getInputStream());
                TextureMetadataSection texturemetadatasection = (TextureMetadataSection) iresource.getMetadata("texture");
                if (texturemetadatasection != null) {
                    List list = texturemetadatasection.getListMipmaps();
                    if (!list.isEmpty()) {
                        int l = abufferedimage[0].width;
                        int i1 = abufferedimage[0].height;
                        if (MathHelper.roundUpToPowerOfTwo(l) != l || MathHelper.roundUpToPowerOfTwo(i1) != i1) {
                            throw new RuntimeException("Unable to load extra miplevels, source-texture is not power of two");
                        }
                    }

                    Iterator iterator = list.iterator();

                    while (iterator.hasNext()) {
                        int i2 = ((Integer) iterator.next()).intValue();
                        if (i2 > 0 && i2 < abufferedimage.length - 1 && abufferedimage[i2] == null) {
                            ResourceLocation resourcelocation2 = this.completeResourceLocation(resourcelocation, i2);

                            try {
                                abufferedimage[i2] = TextureUtil.readBufferedImage(resourceManager.getResource(resourcelocation2).getInputStream());
                            } catch (IOException ioexception) {
                                logger.error("Unable to load miplevel {} from: {}", new Object[] { Integer.valueOf(i2), resourcelocation2 });
                                logger.error(ioexception);
                            }
                        }
                    }
                }

                AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection) iresource.getMetadata("animation");
                textureatlassprite.loadSprite(abufferedimage, animationmetadatasection);
            } catch (RuntimeException runtimeexception) {
                logger.error("Unable to parse metadata from " + resourcelocation1);
                logger.error(runtimeexception);
                continue;
            } catch (IOException ioexception1) {
                logger.error("Using missing texture, unable to load " + resourcelocation1);
                logger.error(ioexception1);
                continue;
            }

            j = Math.min(j, Math.min(textureatlassprite.getIconWidth(), textureatlassprite.getIconHeight()));
            int l1 = Math.min(Integer.lowestOneBit(textureatlassprite.getIconWidth()), Integer.lowestOneBit(textureatlassprite.getIconHeight()));
            if (l1 < k) {
                logger.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[] { resourcelocation1, Integer.valueOf(textureatlassprite.getIconWidth()), Integer.valueOf(textureatlassprite.getIconHeight()), Integer.valueOf(MathHelper.calculateLogBaseTwo(k)), Integer.valueOf(MathHelper.calculateLogBaseTwo(l1)) });
                k = l1;
            }

            stitcher.addSprite(textureatlassprite);
        }

        int j1 = Math.min(j, k);
        int k1 = MathHelper.calculateLogBaseTwo(j1);
        if (k1 < this.mipmapLevels) {
            logger.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] { this.basePath, Integer.valueOf(this.mipmapLevels), Integer.valueOf(k1), Integer.valueOf(j1) });
            this.mipmapLevels = k1;
        }

        for (final EaglerTextureAtlasSprite textureatlassprite1 : this.mapRegisteredSprites.values()) {
            try {
                textureatlassprite1.generateMipmaps(this.mipmapLevels);
            } catch (Throwable throwable1) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Applying mipmap");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Sprite being mipmapped");
                crashreportcategory.addCrashSectionCallable(
                    "Sprite name",
                    new Callable<String>() {
                        public String call() throws Exception {
                            return textureatlassprite1.getIconName();
                        }
                    }
                );
                crashreportcategory.addCrashSectionCallable(
                    "Sprite size",
                    new Callable<String>() {
                        public String call() throws Exception {
                            return textureatlassprite1.getIconWidth() + " x " + textureatlassprite1.getIconHeight();
                        }
                    }
                );
                crashreportcategory.addCrashSectionCallable(
                    "Sprite frames",
                    new Callable<String>() {
                        public String call() throws Exception {
                            return textureatlassprite1.getFrameCount() + " frames";
                        }
                    }
                );
                crashreportcategory.addCrashSection("Mipmap levels", Integer.valueOf(this.mipmapLevels));
                throw new ReportedException(crashreport);
            }
        }

        this.missingImage.generateMipmaps(this.mipmapLevels);
        stitcher.addSprite(this.missingImage);

        try {
            stitcher.doStitch();
        } catch (StitcherException stitcherexception) {
            throw stitcherexception;
        }

        logger.info("Created: {}x{} {}-atlas", new Object[] { Integer.valueOf(stitcher.getCurrentWidth()), Integer.valueOf(stitcher.getCurrentHeight()), this.basePath });
        TextureUtil.allocateTextureImpl(this.getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
        HashMap hashmap = Maps.newHashMap(this.mapRegisteredSprites);

        width = stitcher.getCurrentWidth();
        height = stitcher.getCurrentHeight();

        for (EaglerTextureAtlasSprite textureatlassprite2 : stitcher.getStichSlots()) {
            String s = textureatlassprite2.getIconName();
            hashmap.remove(s);
            this.mapUploadedSprites.put(s, textureatlassprite2);

            try {
                TextureUtil.uploadTextureMipmap(textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
            } catch (Throwable throwable) {
                CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Stitching texture atlas");
                CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Texture being stitched together");
                crashreportcategory1.addCrashSection("Atlas path", this.basePath);
                crashreportcategory1.addCrashSection("Sprite", textureatlassprite2);
                throw new ReportedException(crashreport1);
            }

            if (textureatlassprite2.hasAnimationMetadata()) {
                this.listAnimatedSprites.add(textureatlassprite2);
            }
        }

        for (EaglerTextureAtlasSprite textureatlassprite3 : (Collection<EaglerTextureAtlasSprite>) hashmap.values()) {
            textureatlassprite3.copyFrom(this.missingImage);
        }
    }

    private ResourceLocation completeResourceLocation(ResourceLocation location, int parInt1) {
        return parInt1 == 0 ? new ResourceLocation(location.getResourceDomain(), HString.format("%s/%s%s", new Object[] { this.basePath, location.getResourcePath(), ".png" })) : new ResourceLocation(location.getResourceDomain(), HString.format("%s/mipmaps/%s.%d%s", new Object[] { this.basePath, location.getResourcePath(), Integer.valueOf(parInt1), ".png" }));
    }

    public EaglerTextureAtlasSprite getAtlasSprite(String iconName) {
        EaglerTextureAtlasSprite textureatlassprite = (EaglerTextureAtlasSprite) this.mapUploadedSprites.get(iconName);
        if (textureatlassprite == null) {
            textureatlassprite = this.missingImage;
        }

        return textureatlassprite;
    }

    public void updateAnimations() {
        TextureUtil.bindTexture(this.getGlTextureId());

        for (EaglerTextureAtlasSprite textureatlassprite : this.listAnimatedSprites) {
            textureatlassprite.updateAnimation();
        }
    }

    private void destroyAnimationCaches() {
        for (EaglerTextureAtlasSprite textureatlassprite : this.listAnimatedSprites) {
            textureatlassprite.clearFramesTextureData();
        }
    }

    public EaglerTextureAtlasSprite registerSprite(ResourceLocation location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null!");
        } else {
            EaglerTextureAtlasSprite textureatlassprite = (EaglerTextureAtlasSprite) this.mapRegisteredSprites.get(location);
            if (textureatlassprite == null) {
                textureatlassprite = EaglerTextureAtlasSprite.makeAtlasSprite(location);
                this.mapRegisteredSprites.put(location.toString(), textureatlassprite);
            }

            return textureatlassprite;
        }
    }

    public void tick() {
        this.updateAnimations();
    }

    public void setMipmapLevels(int mipmapLevelsIn) {
        this.mipmapLevels = mipmapLevelsIn;
    }

    public EaglerTextureAtlasSprite getMissingSprite() {
        return this.missingImage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
