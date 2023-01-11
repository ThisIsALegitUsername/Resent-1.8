package net.minecraft.client.renderer;

import static net.lax1dude.eaglercraft.v1_8.opengl.RealOpenGLEnums.*;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.lax1dude.eaglercraft.v1_8.EaglercraftRandom;
import net.lax1dude.eaglercraft.v1_8.HString;
import java.util.Set;
import java.util.concurrent.Callable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.lax1dude.eaglercraft.v1_8.log4j.LogManager;
import net.lax1dude.eaglercraft.v1_8.log4j.Logger;
import net.lax1dude.eaglercraft.v1_8.minecraft.ChunkUpdateManager;
import net.lax1dude.eaglercraft.v1_8.minecraft.EaglerTextureAtlasSprite;
import net.lax1dude.eaglercraft.v1_8.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.WorldRenderer;
import net.lax1dude.eaglercraft.v1_8.vector.Vector3f;
import net.lax1dude.eaglercraft.v1_8.vector.Vector4f;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Matrix4f;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.util.Vector3d;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;

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
public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener {
	private static final Logger logger = LogManager.getLogger();
	private static final ResourceLocation locationMoonPhasesPng = new ResourceLocation(
			"textures/environment/moon_phases.png");
	private static final ResourceLocation locationSunPng = new ResourceLocation("textures/environment/sun.png");
	private static final ResourceLocation locationCloudsPng = new ResourceLocation("textures/environment/clouds.png");
	private static final ResourceLocation locationEndSkyPng = new ResourceLocation("textures/environment/end_sky.png");
	private static final ResourceLocation locationForcefieldPng = new ResourceLocation("textures/misc/forcefield.png");
	private final Minecraft mc;
	private final TextureManager renderEngine;
	private final RenderManager renderManager;
	private WorldClient theWorld;
	private Set<RenderChunk> chunksToUpdate = Sets.newLinkedHashSet();
	/**+
	 * List of OpenGL lists for the current render pass
	 */
	private List<RenderGlobal.ContainerLocalRenderInformation> renderInfos = Lists.newArrayListWithCapacity(69696);
	private final Set<TileEntity> field_181024_n = Sets.newHashSet();
	private ViewFrustum viewFrustum;
	/**+
	 * The star GL Call list
	 */
	private int starGLCallList = -1;
	/**+
	 * OpenGL sky list
	 */
	private int glSkyList = -1;
	/**+
	 * OpenGL sky list 2
	 */
	private int glSkyList2 = -1;
	private int cloudTickCounter;
	private final Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.newHashMap();
	private final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
	private final EaglerTextureAtlasSprite[] destroyBlockIcons = new EaglerTextureAtlasSprite[10];
	private double frustumUpdatePosX = Double.MIN_VALUE;
	private double frustumUpdatePosY = Double.MIN_VALUE;
	private double frustumUpdatePosZ = Double.MIN_VALUE;
	private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
	private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
	private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
	private double lastViewEntityX = Double.MIN_VALUE;
	private double lastViewEntityY = Double.MIN_VALUE;
	private double lastViewEntityZ = Double.MIN_VALUE;
	private double lastViewEntityPitch = Double.MIN_VALUE;
	private double lastViewEntityYaw = Double.MIN_VALUE;
	private final ChunkUpdateManager renderDispatcher = new ChunkUpdateManager();
	private ChunkRenderContainer renderContainer;
	private int renderDistanceChunks = -1;
	/**+
	 * Render entities startup counter (init value=2)
	 */
	private int renderEntitiesStartupCounter = 2;
	private int countEntitiesTotal;
	private int countEntitiesRendered;
	private int countEntitiesHidden;
	private boolean debugFixTerrainFrustum = false;
	private ClippingHelper debugFixedClippingHelper;
	private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
	private final Vector3d debugTerrainFrustumPosition = new Vector3d();
	private boolean vboEnabled = false;
	IRenderChunkFactory renderChunkFactory;
	private double prevRenderSortX;
	private double prevRenderSortY;
	private double prevRenderSortZ;
	private boolean displayListEntitiesDirty = true;

	public RenderGlobal(Minecraft mcIn) {
		this.mc = mcIn;
		this.renderManager = mcIn.getRenderManager();
		this.renderEngine = mcIn.getTextureManager();
		this.renderEngine.bindTexture(locationForcefieldPng);
		EaglercraftGPU.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		EaglercraftGPU.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		GlStateManager.bindTexture(0);
		this.updateDestroyBlockIcons();
		this.vboEnabled = false;
		this.renderContainer = new RenderList();
		this.renderChunkFactory = new ListChunkFactory();
		this.generateStars();
		this.generateSky();
		this.generateSky2();
	}

	public void onResourceManagerReload(IResourceManager var1) {
		this.updateDestroyBlockIcons();
	}

	private void updateDestroyBlockIcons() {
		TextureMap texturemap = this.mc.getTextureMapBlocks();

		for (int i = 0; i < this.destroyBlockIcons.length; ++i) {
			this.destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
		}

	}

	/**+
	 * Creates the entity outline shader to be stored in
	 * RenderGlobal.entityOutlineShader
	 */
	public void makeEntityOutlineShader() {

	}

	public void renderEntityOutlineFramebuffer() {

	}

	protected boolean isRenderEntityOutlines() {
		return false;
	}

	private void generateSky2() {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		if (this.glSkyList2 >= 0) {
			GLAllocation.deleteDisplayLists(this.glSkyList2);
			this.glSkyList2 = -1;
		}

		this.glSkyList2 = GLAllocation.generateDisplayLists();
		EaglercraftGPU.glNewList(this.glSkyList2, GL_COMPILE);
		this.renderSky(worldrenderer, -16.0F, true);
		tessellator.draw();
		EaglercraftGPU.glEndList();

	}

	private void generateSky() {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		if (this.glSkyList >= 0) {
			GLAllocation.deleteDisplayLists(this.glSkyList);
			this.glSkyList = -1;
		}

		this.glSkyList = GLAllocation.generateDisplayLists();
		EaglercraftGPU.glNewList(this.glSkyList, GL_COMPILE);
		this.renderSky(worldrenderer, 16.0F, false);
		tessellator.draw();
		EaglercraftGPU.glEndList();

	}

	private void renderSky(WorldRenderer worldRendererIn, float parFloat1, boolean parFlag) {
		boolean flag = true;
		boolean flag1 = true;
		worldRendererIn.begin(7, DefaultVertexFormats.POSITION);

		for (int i = -384; i <= 384; i += 64) {
			for (int j = -384; j <= 384; j += 64) {
				float f = (float) i;
				float f1 = (float) (i + 64);
				if (parFlag) {
					f1 = (float) i;
					f = (float) (i + 64);
				}

				worldRendererIn.pos((double) f, (double) parFloat1, (double) j).endVertex();
				worldRendererIn.pos((double) f1, (double) parFloat1, (double) j).endVertex();
				worldRendererIn.pos((double) f1, (double) parFloat1, (double) (j + 64)).endVertex();
				worldRendererIn.pos((double) f, (double) parFloat1, (double) (j + 64)).endVertex();
			}
		}

	}

	private void generateStars() {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		if (this.starGLCallList >= 0) {
			GLAllocation.deleteDisplayLists(this.starGLCallList);
			this.starGLCallList = -1;
		}

		this.starGLCallList = GLAllocation.generateDisplayLists();
		GlStateManager.pushMatrix();
		EaglercraftGPU.glNewList(this.starGLCallList, GL_COMPILE);
		this.renderStars(worldrenderer);
		tessellator.draw();
		EaglercraftGPU.glEndList();
		GlStateManager.popMatrix();

	}

	private void renderStars(WorldRenderer worldRendererIn) {
		EaglercraftRandom random = new EaglercraftRandom(10842L);
		worldRendererIn.begin(7, DefaultVertexFormats.POSITION);

		for (int i = 0; i < 1500; ++i) {
			double d0 = (double) (random.nextFloat() * 2.0F - 1.0F);
			double d1 = (double) (random.nextFloat() * 2.0F - 1.0F);
			double d2 = (double) (random.nextFloat() * 2.0F - 1.0F);
			double d3 = (double) (0.15F + random.nextFloat() * 0.1F);
			double d4 = d0 * d0 + d1 * d1 + d2 * d2;
			if (d4 < 1.0D && d4 > 0.01D) {
				d4 = 1.0D / Math.sqrt(d4);
				d0 = d0 * d4;
				d1 = d1 * d4;
				d2 = d2 * d4;
				double d5 = d0 * 100.0D;
				double d6 = d1 * 100.0D;
				double d7 = d2 * 100.0D;
				double d8 = Math.atan2(d0, d2);
				double d9 = Math.sin(d8);
				double d10 = Math.cos(d8);
				double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
				double d12 = Math.sin(d11);
				double d13 = Math.cos(d11);
				double d14 = random.nextDouble() * 3.141592653589793D * 2.0D;
				double d15 = Math.sin(d14);
				double d16 = Math.cos(d14);

				for (int j = 0; j < 4; ++j) {
					double d17 = 0.0D;
					double d18 = (double) ((j & 2) - 1) * d3;
					double d19 = (double) ((j + 1 & 2) - 1) * d3;
					double d20 = 0.0D;
					double d21 = d18 * d16 - d19 * d15;
					double d22 = d19 * d16 + d18 * d15;
					double d23 = d21 * d12 + 0.0D * d13;
					double d24 = 0.0D * d12 - d21 * d13;
					double d25 = d24 * d9 - d22 * d10;
					double d26 = d22 * d9 + d24 * d10;
					worldRendererIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
				}
			}
		}

	}

	/**+
	 * set null to clear
	 */
	public void setWorldAndLoadRenderers(WorldClient worldClientIn) {
		if (this.theWorld != null) {
			this.theWorld.removeWorldAccess(this);
		}

		this.frustumUpdatePosX = Double.MIN_VALUE;
		this.frustumUpdatePosY = Double.MIN_VALUE;
		this.frustumUpdatePosZ = Double.MIN_VALUE;
		this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
		this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
		this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
		this.renderManager.set(worldClientIn);
		this.theWorld = worldClientIn;
		if (worldClientIn != null) {
			worldClientIn.addWorldAccess(this);
			this.loadRenderers();
		}

	}

	/**+
	 * Loads all the renderers and sets up the basic settings usage
	 */
	public void loadRenderers() {
		if (this.theWorld != null) {
			this.displayListEntitiesDirty = true;
			Blocks.leaves.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
			Blocks.leaves2.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
			this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;

			if (this.viewFrustum != null) {
				this.viewFrustum.deleteGlResources();
			}

			this.stopChunkUpdates();
			synchronized (this.field_181024_n) {
				this.field_181024_n.clear();
			}

			this.viewFrustum = new ViewFrustum(this.theWorld, this.mc.gameSettings.renderDistanceChunks, this,
					this.renderChunkFactory);
			if (this.theWorld != null) {
				Entity entity = this.mc.getRenderViewEntity();
				if (entity != null) {
					this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
				}
			}

			this.renderEntitiesStartupCounter = 2;
		}
	}

	protected void stopChunkUpdates() {
		this.chunksToUpdate.clear();
		this.renderDispatcher.stopChunkUpdates();
	}

	public void createBindEntityOutlineFbs(int parInt1, int parInt2) {

	}

	public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
		if (this.renderEntitiesStartupCounter > 0) {
			--this.renderEntitiesStartupCounter;
		} else {
			double d0 = renderViewEntity.prevPosX
					+ (renderViewEntity.posX - renderViewEntity.prevPosX) * (double) partialTicks;
			double d1 = renderViewEntity.prevPosY
					+ (renderViewEntity.posY - renderViewEntity.prevPosY) * (double) partialTicks;
			double d2 = renderViewEntity.prevPosZ
					+ (renderViewEntity.posZ - renderViewEntity.prevPosZ) * (double) partialTicks;
			this.theWorld.theProfiler.startSection("prepare");
			TileEntityRendererDispatcher.instance.cacheActiveRenderInfo(this.theWorld, this.mc.getTextureManager(),
					this.mc.fontRendererObj, this.mc.getRenderViewEntity(), partialTicks);
			this.renderManager.cacheActiveRenderInfo(this.theWorld, this.mc.fontRendererObj,
					this.mc.getRenderViewEntity(), this.mc.pointedEntity, this.mc.gameSettings, partialTicks);
			this.countEntitiesTotal = 0;
			this.countEntitiesRendered = 0;
			this.countEntitiesHidden = 0;
			Entity entity = this.mc.getRenderViewEntity();
			double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double) partialTicks;
			double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double) partialTicks;
			double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double) partialTicks;
			TileEntityRendererDispatcher.staticPlayerX = d3;
			TileEntityRendererDispatcher.staticPlayerY = d4;
			TileEntityRendererDispatcher.staticPlayerZ = d5;
			this.renderManager.setRenderPosition(d3, d4, d5);
			this.mc.entityRenderer.enableLightmap();
			this.theWorld.theProfiler.endStartSection("global");
			List list = this.theWorld.getLoadedEntityList();
			this.countEntitiesTotal = list.size();

			for (int i = 0; i < this.theWorld.weatherEffects.size(); ++i) {
				Entity entity1 = (Entity) this.theWorld.weatherEffects.get(i);
				++this.countEntitiesRendered;
				if (entity1.isInRangeToRender3d(d0, d1, d2)) {
					this.renderManager.renderEntitySimple(entity1, partialTicks);
				}
			}

			this.theWorld.theProfiler.endStartSection("entities");

			label738: for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
				Chunk chunk = this.theWorld.getChunkFromBlockCoords(
						renderglobal$containerlocalrenderinformation.renderChunk.getPosition());
				ClassInheritanceMultiMap classinheritancemultimap = chunk
						.getEntityLists()[renderglobal$containerlocalrenderinformation.renderChunk.getPosition().getY()
								/ 16];
				if (!classinheritancemultimap.isEmpty()) {
					Iterator iterator = classinheritancemultimap.iterator();

					while (true) {
						Entity entity2;
						boolean flag2;
						while (true) {
							if (!iterator.hasNext()) {
								continue label738;
							}

							entity2 = (Entity) iterator.next();
							flag2 = this.renderManager.shouldRender(entity2, camera, d0, d1, d2)
									|| entity2.riddenByEntity == this.mc.thePlayer;
							if (!flag2) {
								break;
							}

							boolean flag3 = this.mc.getRenderViewEntity() instanceof EntityLivingBase
									? ((EntityLivingBase) this.mc.getRenderViewEntity()).isPlayerSleeping()
									: false;
							if ((entity2 != this.mc.getRenderViewEntity() || this.mc.gameSettings.thirdPersonView != 0
									|| flag3)
									&& (entity2.posY < 0.0D || entity2.posY >= 256.0D
											|| this.theWorld.isBlockLoaded(new BlockPos(entity2)))) {
								++this.countEntitiesRendered;
								this.renderManager.renderEntitySimple(entity2, partialTicks);
								break;
							}
						}

						if (!flag2 && entity2 instanceof EntityWitherSkull) {
							this.mc.getRenderManager().renderWitherSkull(entity2, partialTicks);
						}
					}
				}
			}

			this.theWorld.theProfiler.endStartSection("blockentities");
			RenderHelper.enableStandardItemLighting();

			for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 : this.renderInfos) {
				List list1 = renderglobal$containerlocalrenderinformation1.renderChunk.getCompiledChunk()
						.getTileEntities();
				if (!list1.isEmpty()) {
					for (TileEntity tileentity2 : (List<TileEntity>) list1) {
						TileEntityRendererDispatcher.instance.renderTileEntity(tileentity2, partialTicks, -1);
					}
				}
			}

			synchronized (this.field_181024_n) {
				for (TileEntity tileentity : this.field_181024_n) {
					TileEntityRendererDispatcher.instance.renderTileEntity(tileentity, partialTicks, -1);
				}
			}

			this.preRenderDamagedBlocks();

			for (DestroyBlockProgress destroyblockprogress : this.damagedBlocks.values()) {
				BlockPos blockpos = destroyblockprogress.getPosition();
				TileEntity tileentity1 = this.theWorld.getTileEntity(blockpos);
				if (tileentity1 instanceof TileEntityChest) {
					TileEntityChest tileentitychest = (TileEntityChest) tileentity1;
					if (tileentitychest.adjacentChestXNeg != null) {
						blockpos = blockpos.offset(EnumFacing.WEST);
						tileentity1 = this.theWorld.getTileEntity(blockpos);
					} else if (tileentitychest.adjacentChestZNeg != null) {
						blockpos = blockpos.offset(EnumFacing.NORTH);
						tileentity1 = this.theWorld.getTileEntity(blockpos);
					}
				}

				Block block = this.theWorld.getBlockState(blockpos).getBlock();
				if (tileentity1 != null && (block instanceof BlockChest || block instanceof BlockEnderChest
						|| block instanceof BlockSign || block instanceof BlockSkull)) {
					TileEntityRendererDispatcher.instance.renderTileEntity(tileentity1, partialTicks,
							destroyblockprogress.getPartialBlockDamage());
				}
			}

			this.postRenderDamagedBlocks();
			this.mc.entityRenderer.disableLightmap();
			this.mc.mcProfiler.endSection();
		}
	}

	/**+
	 * Gets the render info for use on the Debug screen
	 */
	public String getDebugInfoRenders() {
		int i = this.viewFrustum.renderChunks.length;
		int j = 0;

		for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
			CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;
			if (compiledchunk != CompiledChunk.DUMMY && !compiledchunk.isEmpty()) {
				++j;
			}
		}

		return HString.format("C: %d/%d %sD: %d, %s",
				new Object[] { Integer.valueOf(j), Integer.valueOf(i), this.mc.renderChunksMany ? "(s) " : "",
						Integer.valueOf(this.renderDistanceChunks), this.renderDispatcher.getDebugInfo() });
	}

	/**+
	 * Gets the entities info for use on the Debug screen
	 */
	public String getDebugInfoEntities() {
		return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden
				+ ", I: " + (this.countEntitiesTotal - this.countEntitiesHidden - this.countEntitiesRendered);
	}

	public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount,
			boolean playerSpectator) {
		if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
			this.loadRenderers();
		}

		this.theWorld.theProfiler.startSection("camera");
		double d0 = viewEntity.posX - this.frustumUpdatePosX;
		double d1 = viewEntity.posY - this.frustumUpdatePosY;
		double d2 = viewEntity.posZ - this.frustumUpdatePosZ;
		if (this.frustumUpdatePosChunkX != viewEntity.chunkCoordX
				|| this.frustumUpdatePosChunkY != viewEntity.chunkCoordY
				|| this.frustumUpdatePosChunkZ != viewEntity.chunkCoordZ || d0 * d0 + d1 * d1 + d2 * d2 > 16.0D) {
			this.frustumUpdatePosX = viewEntity.posX;
			this.frustumUpdatePosY = viewEntity.posY;
			this.frustumUpdatePosZ = viewEntity.posZ;
			this.frustumUpdatePosChunkX = viewEntity.chunkCoordX;
			this.frustumUpdatePosChunkY = viewEntity.chunkCoordY;
			this.frustumUpdatePosChunkZ = viewEntity.chunkCoordZ;
			this.viewFrustum.updateChunkPositions(viewEntity.posX, viewEntity.posZ);
		}

		this.theWorld.theProfiler.endStartSection("renderlistcamera");
		double d3 = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
		double d4 = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
		double d5 = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
		this.renderContainer.initialize(d3, d4, d5);
		this.theWorld.theProfiler.endStartSection("cull");
		if (this.debugFixedClippingHelper != null) {
			Frustum frustum = new Frustum(this.debugFixedClippingHelper);
			frustum.setPosition(this.debugTerrainFrustumPosition.field_181059_a,
					this.debugTerrainFrustumPosition.field_181060_b, this.debugTerrainFrustumPosition.field_181061_c);
			camera = frustum;
		}

		this.mc.mcProfiler.endStartSection("culling");
		BlockPos blockpos1 = new BlockPos(d3, d4 + (double) viewEntity.getEyeHeight(), d5);
		RenderChunk renderchunk = this.viewFrustum.getRenderChunk(blockpos1);
		BlockPos blockpos = new BlockPos(MathHelper.floor_double(d3 / 16.0D) * 16,
				MathHelper.floor_double(d4 / 16.0D) * 16, MathHelper.floor_double(d5 / 16.0D) * 16);
		this.displayListEntitiesDirty = this.displayListEntitiesDirty || !this.chunksToUpdate.isEmpty()
				|| viewEntity.posX != this.lastViewEntityX || viewEntity.posY != this.lastViewEntityY
				|| viewEntity.posZ != this.lastViewEntityZ
				|| (double) viewEntity.rotationPitch != this.lastViewEntityPitch
				|| (double) viewEntity.rotationYaw != this.lastViewEntityYaw;
		this.lastViewEntityX = viewEntity.posX;
		this.lastViewEntityY = viewEntity.posY;
		this.lastViewEntityZ = viewEntity.posZ;
		this.lastViewEntityPitch = (double) viewEntity.rotationPitch;
		this.lastViewEntityYaw = (double) viewEntity.rotationYaw;
		boolean flag = this.debugFixedClippingHelper != null;
		if (!flag && this.displayListEntitiesDirty) {
			this.displayListEntitiesDirty = false;
			this.renderInfos = Lists.newArrayList();
			LinkedList linkedlist = Lists.newLinkedList();
			boolean flag1 = this.mc.renderChunksMany;
			if (renderchunk != null) {
				boolean flag2 = false;
				RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation3 = new RenderGlobal.ContainerLocalRenderInformation(
						renderchunk, (EnumFacing) null, 0);
				Set set1 = this.getVisibleFacings(blockpos1);
				if (set1.size() == 1) {
					Vector3f vector3f = this.getViewVector(viewEntity, partialTicks);
					EnumFacing enumfacing = EnumFacing.getFacingFromVector(vector3f.x, vector3f.y, vector3f.z)
							.getOpposite();
					set1.remove(enumfacing);
				}

				if (set1.isEmpty()) {
					flag2 = true;
				}

				if (flag2 && !playerSpectator) {
					this.renderInfos.add(renderglobal$containerlocalrenderinformation3);
				} else {
					if (playerSpectator && this.theWorld.getBlockState(blockpos1).getBlock().isOpaqueCube()) {
						flag1 = false;
					}

					renderchunk.setFrameIndex(frameCount);
					linkedlist.add(renderglobal$containerlocalrenderinformation3);
				}
			} else {
				int i = blockpos1.getY() > 0 ? 248 : 8;

				for (int j = -this.renderDistanceChunks; j <= this.renderDistanceChunks; ++j) {
					for (int k = -this.renderDistanceChunks; k <= this.renderDistanceChunks; ++k) {
						RenderChunk renderchunk1 = this.viewFrustum
								.getRenderChunk(new BlockPos((j << 4) + 8, i, (k << 4) + 8));
						if (renderchunk1 != null
								&& ((ICamera) camera).isBoundingBoxInFrustum(renderchunk1.boundingBox)) {
							renderchunk1.setFrameIndex(frameCount);
							linkedlist.add(new RenderGlobal.ContainerLocalRenderInformation(renderchunk1,
									(EnumFacing) null, 0));
						}
					}
				}
			}

			while (!linkedlist.isEmpty()) {
				RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 = (RenderGlobal.ContainerLocalRenderInformation) linkedlist
						.poll();
				RenderChunk renderchunk3 = renderglobal$containerlocalrenderinformation1.renderChunk;
				EnumFacing enumfacing2 = renderglobal$containerlocalrenderinformation1.facing;
				BlockPos blockpos2 = renderchunk3.getPosition();
				this.renderInfos.add(renderglobal$containerlocalrenderinformation1);
				for (EnumFacing enumfacing1 : EnumFacing.values()) {
					RenderChunk renderchunk2 = this.func_181562_a(blockpos, renderchunk3, enumfacing1);
					if ((!flag1 || !renderglobal$containerlocalrenderinformation1.setFacing // TODO:
							.contains(enumfacing1.getOpposite()))
							&& (!flag1 || enumfacing2 == null
									|| renderchunk3.getCompiledChunk().isVisible(enumfacing2.getOpposite(),
											enumfacing1))
							&& renderchunk2 != null && renderchunk2.setFrameIndex(frameCount)
							&& ((ICamera) camera).isBoundingBoxInFrustum(renderchunk2.boundingBox)) {
						RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = new RenderGlobal.ContainerLocalRenderInformation(
								renderchunk2, enumfacing1, renderglobal$containerlocalrenderinformation1.counter + 1);
						renderglobal$containerlocalrenderinformation.setFacing
								.addAll(renderglobal$containerlocalrenderinformation1.setFacing);
						renderglobal$containerlocalrenderinformation.setFacing.add(enumfacing1);
						linkedlist.add(renderglobal$containerlocalrenderinformation);
					}
				}
			}
		}

		if (this.debugFixTerrainFrustum) {
			this.fixTerrainFrustum(d3, d4, d5);
			this.debugFixTerrainFrustum = false;
		}

		Set set = this.chunksToUpdate;
		this.chunksToUpdate = Sets.newLinkedHashSet();

		for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation2 : this.renderInfos) {
			RenderChunk renderchunk4 = renderglobal$containerlocalrenderinformation2.renderChunk;
			if (renderchunk4.isNeedsUpdate() || set.contains(renderchunk4)) {
				this.displayListEntitiesDirty = true;
				if (this.mc.gameSettings.chunkFix ? this.isPositionInRenderChunkHack(blockpos1, renderchunk4)
						: this.isPositionInRenderChunk(blockpos, renderchunk4)) {
					this.mc.mcProfiler.startSection("build near");
					this.renderDispatcher.updateChunkNow(renderchunk4);
					renderchunk4.setNeedsUpdate(false);
					this.mc.mcProfiler.endSection();
				} else {
					this.chunksToUpdate.add(renderchunk4);
				}
			}
		}

		this.chunksToUpdate.addAll(set);
		this.mc.mcProfiler.endSection();
	}

	private boolean isPositionInRenderChunk(BlockPos pos, RenderChunk renderChunkIn) {
		BlockPos blockpos = renderChunkIn.getPosition();
		return MathHelper.abs_int(pos.getX() - blockpos.getX()) > 16 ? false
				: (MathHelper.abs_int(pos.getY() - blockpos.getY()) > 16 ? false
						: MathHelper.abs_int(pos.getZ() - blockpos.getZ()) <= 16);
	}

	/**
	 * WARNING: use only in the above "build near" logic
	 */
	private boolean isPositionInRenderChunkHack(BlockPos pos, RenderChunk renderChunkIn) {
		BlockPos blockpos = renderChunkIn.getPosition();
		return MathHelper.abs_int(pos.getX() - blockpos.getX() - 8) > 11 ? false
				: (MathHelper.abs_int(pos.getY() - blockpos.getY() - 8) > 11 ? false
						: MathHelper.abs_int(pos.getZ() - blockpos.getZ() - 8) <= 11);
	}

	private Set<EnumFacing> getVisibleFacings(BlockPos pos) {
		VisGraph visgraph = new VisGraph();
		BlockPos blockpos = new BlockPos(pos.getX() >> 4 << 4, pos.getY() >> 4 << 4, pos.getZ() >> 4 << 4);
		Chunk chunk = this.theWorld.getChunkFromBlockCoords(blockpos);

		for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos,
				blockpos.add(15, 15, 15))) {
			if (chunk.getBlock(blockpos$mutableblockpos).isOpaqueCube()) {
				visgraph.func_178606_a(blockpos$mutableblockpos);
			}
		}

		return visgraph.func_178609_b(pos);
	}

	private RenderChunk func_181562_a(BlockPos parBlockPos, RenderChunk parRenderChunk, EnumFacing parEnumFacing) {
		BlockPos blockpos = parRenderChunk.func_181701_a(parEnumFacing);
		return MathHelper
				.abs_int(parBlockPos.getX() - blockpos.getX()) > this.renderDistanceChunks * 16
						? null
						: (blockpos.getY() >= 0 && blockpos.getY() < 256
								? (MathHelper.abs_int(parBlockPos.getZ() - blockpos.getZ()) > this.renderDistanceChunks
										* 16 ? null : this.viewFrustum.getRenderChunk(blockpos))
								: null);
	}

	private void fixTerrainFrustum(double x, double y, double z) {
		this.debugFixedClippingHelper = new ClippingHelperImpl();
		((ClippingHelperImpl) this.debugFixedClippingHelper).init();
		((ClippingHelperImpl) this.debugFixedClippingHelper).destroy();
		Matrix4f matrix4f = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
		matrix4f.transpose();
		Matrix4f matrix4f1 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
		matrix4f1.transpose();
		Matrix4f matrix4f2 = new Matrix4f();
		Matrix4f.mul(matrix4f1, matrix4f, matrix4f2);
		matrix4f2.invert();
		this.debugTerrainFrustumPosition.field_181059_a = x;
		this.debugTerrainFrustumPosition.field_181060_b = y;
		this.debugTerrainFrustumPosition.field_181061_c = z;
		this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
		this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
		this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
		this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
		this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
		this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
		this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);

		for (int i = 0; i < 8; ++i) {
			Matrix4f.transform(matrix4f2, this.debugTerrainMatrix[i], this.debugTerrainMatrix[i]);
			this.debugTerrainMatrix[i].x /= this.debugTerrainMatrix[i].w;
			this.debugTerrainMatrix[i].y /= this.debugTerrainMatrix[i].w;
			this.debugTerrainMatrix[i].z /= this.debugTerrainMatrix[i].w;
			this.debugTerrainMatrix[i].w = 1.0F;
		}

	}

	protected Vector3f getViewVector(Entity entityIn, double partialTicks) {
		float f = (float) ((double) entityIn.prevRotationPitch
				+ (double) (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks);
		float f1 = (float) ((double) entityIn.prevRotationYaw
				+ (double) (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks);
		if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2) {
			f += 180.0F;
		}

		float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
		float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
		float f4 = -MathHelper.cos(-f * 0.017453292F);
		float f5 = MathHelper.sin(-f * 0.017453292F);
		return new Vector3f(f3 * f4, f5, f2 * f4);
	}

	public int renderBlockLayer(EnumWorldBlockLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
		RenderHelper.disableStandardItemLighting();
		if (blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT) {
			this.mc.mcProfiler.startSection("translucent_sort");
			double d0 = entityIn.posX - this.prevRenderSortX;
			double d1 = entityIn.posY - this.prevRenderSortY;
			double d2 = entityIn.posZ - this.prevRenderSortZ;
			if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0D) {
				this.prevRenderSortX = entityIn.posX;
				this.prevRenderSortY = entityIn.posY;
				this.prevRenderSortZ = entityIn.posZ;
				int k = 0;

				for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
					if (renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk
							.isLayerStarted(blockLayerIn) && k++ < 15) {
						this.renderDispatcher
								.updateTransparencyLater(renderglobal$containerlocalrenderinformation.renderChunk);
					}
				}
			}

			this.mc.mcProfiler.endSection();
		}

		this.mc.mcProfiler.startSection("filterempty");
		int l = 0;
		boolean flag = blockLayerIn == EnumWorldBlockLayer.TRANSLUCENT;
		int i1 = flag ? this.renderInfos.size() - 1 : 0;
		int i = flag ? -1 : this.renderInfos.size();
		int j1 = flag ? -1 : 1;

		for (int j = i1; j != i; j += j1) {
			RenderChunk renderchunk = ((RenderGlobal.ContainerLocalRenderInformation) this.renderInfos
					.get(j)).renderChunk;
			if (!renderchunk.getCompiledChunk().isLayerEmpty(blockLayerIn)) {
				++l;
				this.renderContainer.addRenderChunk(renderchunk, blockLayerIn);
			}
		}

		this.mc.mcProfiler.endStartSection("render_" + blockLayerIn);
		this.renderBlockLayer(blockLayerIn);
		this.mc.mcProfiler.endSection();
		return l;
	}

	private void renderBlockLayer(EnumWorldBlockLayer blockLayerIn) {
		this.mc.entityRenderer.enableLightmap();
		this.renderContainer.renderChunkLayer(blockLayerIn);
		this.mc.entityRenderer.disableLightmap();
	}

	private void cleanupDamagedBlocks(Iterator<DestroyBlockProgress> iteratorIn) {
		while (iteratorIn.hasNext()) {
			DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress) iteratorIn.next();
			int i = destroyblockprogress.getCreationCloudUpdateTick();
			if (this.cloudTickCounter - i > 400) {
				iteratorIn.remove();
			}
		}

	}

	public void updateClouds() {
		++this.cloudTickCounter;
		if (this.cloudTickCounter % 20 == 0) {
			this.cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
		}

	}

	private void renderSkyEnd() {
		GlStateManager.disableFog();
		GlStateManager.disableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.depthMask(false);
		this.renderEngine.bindTexture(locationEndSkyPng);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();

		for (int i = 0; i < 6; ++i) {
			GlStateManager.pushMatrix();
			if (i == 1) {
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
			}

			if (i == 2) {
				GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
			}

			if (i == 3) {
				GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
			}

			if (i == 4) {
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
			}

			if (i == 5) {
				GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
			}

			worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
			worldrenderer.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color(40, 40, 40, 255).endVertex();
			worldrenderer.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color(40, 40, 40, 255).endVertex();
			worldrenderer.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color(40, 40, 40, 255).endVertex();
			worldrenderer.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color(40, 40, 40, 255).endVertex();
			tessellator.draw();
			GlStateManager.popMatrix();
		}

		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.enableAlpha();
	}

	public void renderSky(float partialTicks, int pass) {
		if (this.mc.theWorld.provider.getDimensionId() == 1) {
			this.renderSkyEnd();
		} else if (this.mc.theWorld.provider.isSurfaceWorld()) {
			GlStateManager.disableTexture2D();
			Vec3 vec3 = this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
			float f = (float) vec3.xCoord;
			float f1 = (float) vec3.yCoord;
			float f2 = (float) vec3.zCoord;
			if (pass != 2) {
				float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
				float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
				float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
				f = f3;
				f1 = f4;
				f2 = f5;
			}

			GlStateManager.color(f, f1, f2);
			Tessellator tessellator = Tessellator.getInstance();
			WorldRenderer worldrenderer = tessellator.getWorldRenderer();
			GlStateManager.depthMask(false);
			GlStateManager.enableFog();
			GlStateManager.color(f, f1, f2);
			GlStateManager.callList(this.glSkyList);

			GlStateManager.disableFog();
			GlStateManager.disableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			RenderHelper.disableStandardItemLighting();
			float[] afloat = this.theWorld.provider
					.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(partialTicks), partialTicks);
			if (afloat != null) {
				GlStateManager.disableTexture2D();
				GlStateManager.shadeModel(GL_SMOOTH);
				GlStateManager.pushMatrix();
				GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(
						MathHelper.sin(this.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F,
						0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
				float f6 = afloat[0];
				float f7 = afloat[1];
				float f8 = afloat[2];
				if (pass != 2) {
					float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
					float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
					float f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
					f6 = f9;
					f7 = f10;
					f8 = f11;
				}

				worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
				worldrenderer.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();
				boolean flag = true;

				for (int k = 0; k <= 16; ++k) {
					float f21 = (float) k * 3.1415927F * 2.0F / 16.0F;
					float f12 = MathHelper.sin(f21);
					float f13 = MathHelper.cos(f21);
					worldrenderer
							.pos((double) (f12 * 120.0F), (double) (f13 * 120.0F), (double) (f13 * 40.0F * afloat[3]))
							.color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
				}

				tessellator.draw();
				GlStateManager.popMatrix();
				GlStateManager.shadeModel(GL_FLAT);
			}

			GlStateManager.enableTexture2D();
			GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, 1, 1, 0);
			GlStateManager.pushMatrix();
			float f16 = 1.0F - this.theWorld.getRainStrength(partialTicks);
			GlStateManager.color(1.0F, 1.0F, 1.0F, f16);
			GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotate(this.theWorld.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
			float f17 = 30.0F;
			this.renderEngine.bindTexture(locationSunPng);
			worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
			worldrenderer.pos((double) (-f17), 100.0D, (double) (-f17)).tex(0.0D, 0.0D).endVertex();
			worldrenderer.pos((double) f17, 100.0D, (double) (-f17)).tex(1.0D, 0.0D).endVertex();
			worldrenderer.pos((double) f17, 100.0D, (double) f17).tex(1.0D, 1.0D).endVertex();
			worldrenderer.pos((double) (-f17), 100.0D, (double) f17).tex(0.0D, 1.0D).endVertex();
			tessellator.draw();
			f17 = 20.0F;
			this.renderEngine.bindTexture(locationMoonPhasesPng);
			int i = this.theWorld.getMoonPhase();
			int j = i % 4;
			int l = i / 4 % 2;
			float f22 = (float) (j + 0) / 4.0F;
			float f23 = (float) (l + 0) / 2.0F;
			float f24 = (float) (j + 1) / 4.0F;
			float f14 = (float) (l + 1) / 2.0F;
			worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
			worldrenderer.pos((double) (-f17), -100.0D, (double) f17).tex((double) f24, (double) f14).endVertex();
			worldrenderer.pos((double) f17, -100.0D, (double) f17).tex((double) f22, (double) f14).endVertex();
			worldrenderer.pos((double) f17, -100.0D, (double) (-f17)).tex((double) f22, (double) f23).endVertex();
			worldrenderer.pos((double) (-f17), -100.0D, (double) (-f17)).tex((double) f24, (double) f23).endVertex();
			tessellator.draw();
			GlStateManager.disableTexture2D();
			float f15 = this.theWorld.getStarBrightness(partialTicks) * f16;
			if (f15 > 0.0F) {
				GlStateManager.color(f15, f15, f15, f15);
				GlStateManager.callList(this.starGLCallList);
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.disableBlend();
			GlStateManager.enableAlpha();
			GlStateManager.enableFog();
			GlStateManager.popMatrix();
			GlStateManager.disableTexture2D();
			GlStateManager.color(0.0F, 0.0F, 0.0F);
			double d0 = this.mc.thePlayer.getPositionEyes(partialTicks).yCoord - this.theWorld.getHorizon();
			if (d0 < 0.0D) {
				GlStateManager.pushMatrix();
				GlStateManager.translate(0.0F, 12.0F, 0.0F);
				GlStateManager.callList(this.glSkyList2);

				GlStateManager.popMatrix();
				float f18 = 1.0F;
				float f19 = -((float) (d0 + 65.0D));
				float f20 = -1.0F;
				worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
				worldrenderer.pos(-1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, (double) f19, -1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(-1.0D, (double) f19, -1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, (double) f19, -1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(-1.0D, (double) f19, -1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(-1.0D, (double) f19, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
				worldrenderer.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
				tessellator.draw();
			}

			if (this.theWorld.provider.isSkyColored()) {
				GlStateManager.color(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
			} else {
				GlStateManager.color(f, f1, f2);
			}

			GlStateManager.pushMatrix();
			GlStateManager.translate(0.0F, -((float) (d0 - 16.0D)), 0.0F);
			GlStateManager.callList(this.glSkyList2);
			GlStateManager.popMatrix();
			GlStateManager.enableTexture2D();
			GlStateManager.depthMask(true);
		}
	}

	public void renderClouds(float partialTicks, int pass) {
		if (this.mc.theWorld.provider.isSurfaceWorld()) {
			if (this.mc.gameSettings.func_181147_e() == 2) {
				this.renderCloudsFancy(partialTicks, pass);
			} else {
				GlStateManager.disableCull();
				float f = (float) (this.mc.getRenderViewEntity().lastTickPosY
						+ (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY)
								* (double) partialTicks);
				boolean flag = true;
				boolean flag1 = true;
				Tessellator tessellator = Tessellator.getInstance();
				WorldRenderer worldrenderer = tessellator.getWorldRenderer();
				this.renderEngine.bindTexture(locationCloudsPng);
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
				Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
				float f1 = (float) vec3.xCoord;
				float f2 = (float) vec3.yCoord;
				float f3 = (float) vec3.zCoord;
				if (pass != 2) {
					float f4 = (f1 * 30.0F + f2 * 59.0F + f3 * 11.0F) / 100.0F;
					float f5 = (f1 * 30.0F + f2 * 70.0F) / 100.0F;
					float f6 = (f1 * 30.0F + f3 * 70.0F) / 100.0F;
					f1 = f4;
					f2 = f5;
					f3 = f6;
				}

				float f10 = 4.8828125E-4F;
				double d2 = (double) ((float) this.cloudTickCounter + partialTicks);
				double d0 = this.mc.getRenderViewEntity().prevPosX
						+ (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX)
								* (double) partialTicks
						+ d2 * 0.029999999329447746D;
				double d1 = this.mc.getRenderViewEntity().prevPosZ
						+ (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ)
								* (double) partialTicks;
				int i = MathHelper.floor_double(d0 / 2048.0D);
				int j = MathHelper.floor_double(d1 / 2048.0D);
				d0 = d0 - (double) (i * 2048);
				d1 = d1 - (double) (j * 2048);
				float f7 = this.theWorld.provider.getCloudHeight() - f + 0.33F;
				float f8 = (float) (d0 * 4.8828125E-4D);
				float f9 = (float) (d1 * 4.8828125E-4D);
				worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);

				for (int k = -256; k < 256; k += 32) {
					for (int l = -256; l < 256; l += 32) {
						worldrenderer.pos((double) (k + 0), (double) f7, (double) (l + 32))
								.tex((double) ((float) (k + 0) * 4.8828125E-4F + f8),
										(double) ((float) (l + 32) * 4.8828125E-4F + f9))
								.color(f1, f2, f3, 0.8F).endVertex();
						worldrenderer.pos((double) (k + 32), (double) f7, (double) (l + 32))
								.tex((double) ((float) (k + 32) * 4.8828125E-4F + f8),
										(double) ((float) (l + 32) * 4.8828125E-4F + f9))
								.color(f1, f2, f3, 0.8F).endVertex();
						worldrenderer.pos((double) (k + 32), (double) f7, (double) (l + 0))
								.tex((double) ((float) (k + 32) * 4.8828125E-4F + f8),
										(double) ((float) (l + 0) * 4.8828125E-4F + f9))
								.color(f1, f2, f3, 0.8F).endVertex();
						worldrenderer.pos((double) (k + 0), (double) f7, (double) (l + 0))
								.tex((double) ((float) (k + 0) * 4.8828125E-4F + f8),
										(double) ((float) (l + 0) * 4.8828125E-4F + f9))
								.color(f1, f2, f3, 0.8F).endVertex();
					}
				}

				tessellator.draw();
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.disableBlend();
				GlStateManager.enableCull();
			}
		}
	}

	/**+
	 * Checks if the given position is to be rendered with cloud fog
	 */
	public boolean hasCloudFog(double x, double y, double z, float partialTicks) {
		return false;
	}

	private void renderCloudsFancy(float partialTicks, int pass) {
		GlStateManager.disableCull();
		float f = (float) (this.mc.getRenderViewEntity().lastTickPosY
				+ (this.mc.getRenderViewEntity().posY - this.mc.getRenderViewEntity().lastTickPosY)
						* (double) partialTicks);
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		float f1 = 12.0F;
		float f2 = 4.0F;
		double d0 = (double) ((float) this.cloudTickCounter + partialTicks);
		double d1 = (this.mc.getRenderViewEntity().prevPosX
				+ (this.mc.getRenderViewEntity().posX - this.mc.getRenderViewEntity().prevPosX) * (double) partialTicks
				+ d0 * 0.029999999329447746D) / 12.0D;
		double d2 = (this.mc.getRenderViewEntity().prevPosZ
				+ (this.mc.getRenderViewEntity().posZ - this.mc.getRenderViewEntity().prevPosZ) * (double) partialTicks)
				/ 12.0D + 0.33000001311302185D;
		float f3 = this.theWorld.provider.getCloudHeight() - f + 0.33F;
		int i = MathHelper.floor_double(d1 / 2048.0D);
		int j = MathHelper.floor_double(d2 / 2048.0D);
		d1 = d1 - (double) (i * 2048);
		d2 = d2 - (double) (j * 2048);
		this.renderEngine.bindTexture(locationCloudsPng);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
		Vec3 vec3 = this.theWorld.getCloudColour(partialTicks);
		float f4 = (float) vec3.xCoord;
		float f5 = (float) vec3.yCoord;
		float f6 = (float) vec3.zCoord;
		if (pass != 2) {
			float f7 = (f4 * 30.0F + f5 * 59.0F + f6 * 11.0F) / 100.0F;
			float f8 = (f4 * 30.0F + f5 * 70.0F) / 100.0F;
			float f9 = (f4 * 30.0F + f6 * 70.0F) / 100.0F;
			f4 = f7;
			f5 = f8;
			f6 = f9;
		}

		float f26 = f4 * 0.9F;
		float f27 = f5 * 0.9F;
		float f28 = f6 * 0.9F;
		float f10 = f4 * 0.7F;
		float f11 = f5 * 0.7F;
		float f12 = f6 * 0.7F;
		float f13 = f4 * 0.8F;
		float f14 = f5 * 0.8F;
		float f15 = f6 * 0.8F;
		float f16 = 0.00390625F;
		float f17 = (float) MathHelper.floor_double(d1) * 0.00390625F;
		float f18 = (float) MathHelper.floor_double(d2) * 0.00390625F;
		float f19 = (float) (d1 - (double) MathHelper.floor_double(d1));
		float f20 = (float) (d2 - (double) MathHelper.floor_double(d2));
		boolean flag = true;
		boolean flag1 = true;
		float f21 = 9.765625E-4F;
		GlStateManager.scale(12.0F, 1.0F, 12.0F);

		for (int k = 0; k < 2; ++k) {
			if (k == 0) {
				GlStateManager.colorMask(false, false, false, false);
			} else {
				switch (pass) {
				case 0:
					GlStateManager.colorMask(false, true, true, true);
					break;
				case 1:
					GlStateManager.colorMask(true, false, false, true);
					break;
				case 2:
					GlStateManager.colorMask(true, true, true, true);
				}
			}

			for (int l = -3; l <= 4; ++l) {
				for (int i1 = -3; i1 <= 4; ++i1) {
					worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
					float f22 = (float) (l * 8);
					float f23 = (float) (i1 * 8);
					float f24 = f22 - f19;
					float f25 = f23 - f20;
					if (f3 > -5.0F) {
						worldrenderer.pos((double) (f24 + 0.0F), (double) (f3 + 0.0F), (double) (f25 + 8.0F))
								.tex((double) ((f22 + 0.0F) * 0.00390625F + f17),
										(double) ((f23 + 8.0F) * 0.00390625F + f18))
								.color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
						worldrenderer.pos((double) (f24 + 8.0F), (double) (f3 + 0.0F), (double) (f25 + 8.0F))
								.tex((double) ((f22 + 8.0F) * 0.00390625F + f17),
										(double) ((f23 + 8.0F) * 0.00390625F + f18))
								.color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
						worldrenderer.pos((double) (f24 + 8.0F), (double) (f3 + 0.0F), (double) (f25 + 0.0F))
								.tex((double) ((f22 + 8.0F) * 0.00390625F + f17),
										(double) ((f23 + 0.0F) * 0.00390625F + f18))
								.color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
						worldrenderer.pos((double) (f24 + 0.0F), (double) (f3 + 0.0F), (double) (f25 + 0.0F))
								.tex((double) ((f22 + 0.0F) * 0.00390625F + f17),
										(double) ((f23 + 0.0F) * 0.00390625F + f18))
								.color(f10, f11, f12, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
					}

					if (f3 <= 5.0F) {
						worldrenderer
								.pos((double) (f24 + 0.0F), (double) (f3 + 4.0F - 9.765625E-4F), (double) (f25 + 8.0F))
								.tex((double) ((f22 + 0.0F) * 0.00390625F + f17),
										(double) ((f23 + 8.0F) * 0.00390625F + f18))
								.color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
						worldrenderer
								.pos((double) (f24 + 8.0F), (double) (f3 + 4.0F - 9.765625E-4F), (double) (f25 + 8.0F))
								.tex((double) ((f22 + 8.0F) * 0.00390625F + f17),
										(double) ((f23 + 8.0F) * 0.00390625F + f18))
								.color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
						worldrenderer
								.pos((double) (f24 + 8.0F), (double) (f3 + 4.0F - 9.765625E-4F), (double) (f25 + 0.0F))
								.tex((double) ((f22 + 8.0F) * 0.00390625F + f17),
										(double) ((f23 + 0.0F) * 0.00390625F + f18))
								.color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
						worldrenderer
								.pos((double) (f24 + 0.0F), (double) (f3 + 4.0F - 9.765625E-4F), (double) (f25 + 0.0F))
								.tex((double) ((f22 + 0.0F) * 0.00390625F + f17),
										(double) ((f23 + 0.0F) * 0.00390625F + f18))
								.color(f4, f5, f6, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
					}

					if (l > -1) {
						for (int j1 = 0; j1 < 8; ++j1) {
							worldrenderer
									.pos((double) (f24 + (float) j1 + 0.0F), (double) (f3 + 0.0F),
											(double) (f25 + 8.0F))
									.tex((double) ((f22 + (float) j1 + 0.5F) * 0.00390625F + f17),
											(double) ((f23 + 8.0F) * 0.00390625F + f18))
									.color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + (float) j1 + 0.0F), (double) (f3 + 4.0F),
											(double) (f25 + 8.0F))
									.tex((double) ((f22 + (float) j1 + 0.5F) * 0.00390625F + f17),
											(double) ((f23 + 8.0F) * 0.00390625F + f18))
									.color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + (float) j1 + 0.0F), (double) (f3 + 4.0F),
											(double) (f25 + 0.0F))
									.tex((double) ((f22 + (float) j1 + 0.5F) * 0.00390625F + f17),
											(double) ((f23 + 0.0F) * 0.00390625F + f18))
									.color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + (float) j1 + 0.0F), (double) (f3 + 0.0F),
											(double) (f25 + 0.0F))
									.tex((double) ((f22 + (float) j1 + 0.5F) * 0.00390625F + f17),
											(double) ((f23 + 0.0F) * 0.00390625F + f18))
									.color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
						}
					}

					if (l <= 1) {
						for (int k1 = 0; k1 < 8; ++k1) {
							worldrenderer
									.pos((double) (f24 + (float) k1 + 1.0F - 9.765625E-4F), (double) (f3 + 0.0F),
											(double) (f25 + 8.0F))
									.tex((double) ((f22 + (float) k1 + 0.5F) * 0.00390625F + f17),
											(double) ((f23 + 8.0F) * 0.00390625F + f18))
									.color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + (float) k1 + 1.0F - 9.765625E-4F), (double) (f3 + 4.0F),
											(double) (f25 + 8.0F))
									.tex((double) ((f22 + (float) k1 + 0.5F) * 0.00390625F + f17),
											(double) ((f23 + 8.0F) * 0.00390625F + f18))
									.color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + (float) k1 + 1.0F - 9.765625E-4F), (double) (f3 + 4.0F),
											(double) (f25 + 0.0F))
									.tex((double) ((f22 + (float) k1 + 0.5F) * 0.00390625F + f17),
											(double) ((f23 + 0.0F) * 0.00390625F + f18))
									.color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + (float) k1 + 1.0F - 9.765625E-4F), (double) (f3 + 0.0F),
											(double) (f25 + 0.0F))
									.tex((double) ((f22 + (float) k1 + 0.5F) * 0.00390625F + f17),
											(double) ((f23 + 0.0F) * 0.00390625F + f18))
									.color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
						}
					}

					if (i1 > -1) {
						for (int l1 = 0; l1 < 8; ++l1) {
							worldrenderer
									.pos((double) (f24 + 0.0F), (double) (f3 + 4.0F),
											(double) (f25 + (float) l1 + 0.0F))
									.tex((double) ((f22 + 0.0F) * 0.00390625F + f17),
											(double) ((f23 + (float) l1 + 0.5F) * 0.00390625F + f18))
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + 8.0F), (double) (f3 + 4.0F),
											(double) (f25 + (float) l1 + 0.0F))
									.tex((double) ((f22 + 8.0F) * 0.00390625F + f17),
											(double) ((f23 + (float) l1 + 0.5F) * 0.00390625F + f18))
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + 8.0F), (double) (f3 + 0.0F),
											(double) (f25 + (float) l1 + 0.0F))
									.tex((double) ((f22 + 8.0F) * 0.00390625F + f17),
											(double) ((f23 + (float) l1 + 0.5F) * 0.00390625F + f18))
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + 0.0F), (double) (f3 + 0.0F),
											(double) (f25 + (float) l1 + 0.0F))
									.tex((double) ((f22 + 0.0F) * 0.00390625F + f17),
											(double) ((f23 + (float) l1 + 0.5F) * 0.00390625F + f18))
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
						}
					}

					if (i1 <= 1) {
						for (int i2 = 0; i2 < 8; ++i2) {
							worldrenderer
									.pos((double) (f24 + 0.0F), (double) (f3 + 4.0F),
											(double) (f25 + (float) i2 + 1.0F - 9.765625E-4F))
									.tex((double) ((f22 + 0.0F) * 0.00390625F + f17),
											(double) ((f23 + (float) i2 + 0.5F) * 0.00390625F + f18))
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + 8.0F), (double) (f3 + 4.0F),
											(double) (f25 + (float) i2 + 1.0F - 9.765625E-4F))
									.tex((double) ((f22 + 8.0F) * 0.00390625F + f17),
											(double) ((f23 + (float) i2 + 0.5F) * 0.00390625F + f18))
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + 8.0F), (double) (f3 + 0.0F),
											(double) (f25 + (float) i2 + 1.0F - 9.765625E-4F))
									.tex((double) ((f22 + 8.0F) * 0.00390625F + f17),
											(double) ((f23 + (float) i2 + 0.5F) * 0.00390625F + f18))
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
							worldrenderer
									.pos((double) (f24 + 0.0F), (double) (f3 + 0.0F),
											(double) (f25 + (float) i2 + 1.0F - 9.765625E-4F))
									.tex((double) ((f22 + 0.0F) * 0.00390625F + f17),
											(double) ((f23 + (float) i2 + 0.5F) * 0.00390625F + f18))
									.color(f13, f14, f15, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
						}
					}

					tessellator.draw();
				}
			}
		}

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableBlend();
		GlStateManager.enableCull();
	}

	public void updateChunks(long finishTimeNano) {
		this.displayListEntitiesDirty |= this.renderDispatcher.updateChunks(finishTimeNano);
		if (!this.chunksToUpdate.isEmpty()) {
			Iterator iterator = this.chunksToUpdate.iterator();

			while (iterator.hasNext()) {
				RenderChunk renderchunk = (RenderChunk) iterator.next();
				if (!this.renderDispatcher.updateChunkLater(renderchunk)) {
					break;
				}

				renderchunk.setNeedsUpdate(false);
				iterator.remove();
				long i = finishTimeNano - System.nanoTime();
				if (i < 0L) {
					break;
				}
			}
		}
	}

	public void renderWorldBorder(Entity partialTicks, float parFloat1) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		WorldBorder worldborder = this.theWorld.getWorldBorder();
		double d0 = (double) (this.mc.gameSettings.renderDistanceChunks * 16);
		if (partialTicks.posX >= worldborder.maxX() - d0 || partialTicks.posX <= worldborder.minX() + d0
				|| partialTicks.posZ >= worldborder.maxZ() - d0 || partialTicks.posZ <= worldborder.minZ() + d0) {
			double d1 = 1.0D - worldborder.getClosestDistance(partialTicks) / d0;
			d1 = Math.pow(d1, 4.0D);
			double d2 = partialTicks.lastTickPosX
					+ (partialTicks.posX - partialTicks.lastTickPosX) * (double) parFloat1;
			double d3 = partialTicks.lastTickPosY
					+ (partialTicks.posY - partialTicks.lastTickPosY) * (double) parFloat1;
			double d4 = partialTicks.lastTickPosZ
					+ (partialTicks.posZ - partialTicks.lastTickPosZ) * (double) parFloat1;
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, 1, 1, 0);
			this.renderEngine.bindTexture(locationForcefieldPng);
			GlStateManager.depthMask(false);
			GlStateManager.pushMatrix();
			int i = worldborder.getStatus().getID();
			float f = (float) (i >> 16 & 255) / 255.0F;
			float f1 = (float) (i >> 8 & 255) / 255.0F;
			float f2 = (float) (i & 255) / 255.0F;
			GlStateManager.color(f, f1, f2, (float) d1);
			GlStateManager.doPolygonOffset(-3.0F, -3.0F);
			GlStateManager.enablePolygonOffset();
			GlStateManager.alphaFunc(GL_GREATER, 0.1F);
			GlStateManager.enableAlpha();
			GlStateManager.disableCull();
			float f3 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F;
			float f4 = 0.0F;
			float f5 = 0.0F;
			float f6 = 128.0F;
			worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
			worldrenderer.setTranslation(-d2, -d3, -d4);
			double d5 = Math.max((double) MathHelper.floor_double(d4 - d0), worldborder.minZ());
			double d6 = Math.min((double) MathHelper.ceiling_double_int(d4 + d0), worldborder.maxZ());
			if (d2 > worldborder.maxX() - d0) {
				float f7 = 0.0F;

				for (double d7 = d5; d7 < d6; f7 += 0.5F) {
					double d8 = Math.min(1.0D, d6 - d7);
					float f8 = (float) d8 * 0.5F;
					worldrenderer.pos(worldborder.maxX(), 256.0D, d7).tex((double) (f3 + f7), (double) (f3 + 0.0F))
							.endVertex();
					worldrenderer.pos(worldborder.maxX(), 256.0D, d7 + d8)
							.tex((double) (f3 + f8 + f7), (double) (f3 + 0.0F)).endVertex();
					worldrenderer.pos(worldborder.maxX(), 0.0D, d7 + d8)
							.tex((double) (f3 + f8 + f7), (double) (f3 + 128.0F)).endVertex();
					worldrenderer.pos(worldborder.maxX(), 0.0D, d7).tex((double) (f3 + f7), (double) (f3 + 128.0F))
							.endVertex();
					++d7;
				}
			}

			if (d2 < worldborder.minX() + d0) {
				float f9 = 0.0F;

				for (double d9 = d5; d9 < d6; f9 += 0.5F) {
					double d12 = Math.min(1.0D, d6 - d9);
					float f12 = (float) d12 * 0.5F;
					worldrenderer.pos(worldborder.minX(), 256.0D, d9).tex((double) (f3 + f9), (double) (f3 + 0.0F))
							.endVertex();
					worldrenderer.pos(worldborder.minX(), 256.0D, d9 + d12)
							.tex((double) (f3 + f12 + f9), (double) (f3 + 0.0F)).endVertex();
					worldrenderer.pos(worldborder.minX(), 0.0D, d9 + d12)
							.tex((double) (f3 + f12 + f9), (double) (f3 + 128.0F)).endVertex();
					worldrenderer.pos(worldborder.minX(), 0.0D, d9).tex((double) (f3 + f9), (double) (f3 + 128.0F))
							.endVertex();
					++d9;
				}
			}

			d5 = Math.max((double) MathHelper.floor_double(d2 - d0), worldborder.minX());
			d6 = Math.min((double) MathHelper.ceiling_double_int(d2 + d0), worldborder.maxX());
			if (d4 > worldborder.maxZ() - d0) {
				float f10 = 0.0F;

				for (double d10 = d5; d10 < d6; f10 += 0.5F) {
					double d13 = Math.min(1.0D, d6 - d10);
					float f13 = (float) d13 * 0.5F;
					worldrenderer.pos(d10, 256.0D, worldborder.maxZ()).tex((double) (f3 + f10), (double) (f3 + 0.0F))
							.endVertex();
					worldrenderer.pos(d10 + d13, 256.0D, worldborder.maxZ())
							.tex((double) (f3 + f13 + f10), (double) (f3 + 0.0F)).endVertex();
					worldrenderer.pos(d10 + d13, 0.0D, worldborder.maxZ())
							.tex((double) (f3 + f13 + f10), (double) (f3 + 128.0F)).endVertex();
					worldrenderer.pos(d10, 0.0D, worldborder.maxZ()).tex((double) (f3 + f10), (double) (f3 + 128.0F))
							.endVertex();
					++d10;
				}
			}

			if (d4 < worldborder.minZ() + d0) {
				float f11 = 0.0F;

				for (double d11 = d5; d11 < d6; f11 += 0.5F) {
					double d14 = Math.min(1.0D, d6 - d11);
					float f14 = (float) d14 * 0.5F;
					worldrenderer.pos(d11, 256.0D, worldborder.minZ()).tex((double) (f3 + f11), (double) (f3 + 0.0F))
							.endVertex();
					worldrenderer.pos(d11 + d14, 256.0D, worldborder.minZ())
							.tex((double) (f3 + f14 + f11), (double) (f3 + 0.0F)).endVertex();
					worldrenderer.pos(d11 + d14, 0.0D, worldborder.minZ())
							.tex((double) (f3 + f14 + f11), (double) (f3 + 128.0F)).endVertex();
					worldrenderer.pos(d11, 0.0D, worldborder.minZ()).tex((double) (f3 + f11), (double) (f3 + 128.0F))
							.endVertex();
					++d11;
				}
			}

			tessellator.draw();
			worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
			GlStateManager.enableCull();
			GlStateManager.disableAlpha();
			GlStateManager.doPolygonOffset(0.0F, 0.0F);
			GlStateManager.disablePolygonOffset();
			GlStateManager.enableAlpha();
			GlStateManager.disableBlend();
			GlStateManager.popMatrix();
			GlStateManager.depthMask(true);
		}
	}

	private void preRenderDamagedBlocks() {
		GlStateManager.tryBlendFuncSeparate(GL_DST_COLOR, GL_SRC_COLOR, 1, 0);
		GlStateManager.enableBlend();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
		GlStateManager.doPolygonOffset(-3.0F, -3.0F);
		GlStateManager.enablePolygonOffset();
		GlStateManager.alphaFunc(GL_GREATER, 0.1F);
		GlStateManager.enableAlpha();
		GlStateManager.pushMatrix();
	}

	private void postRenderDamagedBlocks() {
		GlStateManager.disableAlpha();
		GlStateManager.doPolygonOffset(0.0F, 0.0F);
		GlStateManager.disablePolygonOffset();
		GlStateManager.enableAlpha();
		GlStateManager.depthMask(true);
		GlStateManager.popMatrix();
	}

	public void drawBlockDamageTexture(Tessellator tessellatorIn, WorldRenderer worldRendererIn, Entity entityIn,
			float partialTicks) {
		double d0 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * (double) partialTicks;
		double d1 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * (double) partialTicks;
		double d2 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * (double) partialTicks;
		if (!this.damagedBlocks.isEmpty()) {
			this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
			this.preRenderDamagedBlocks();
			worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
			worldRendererIn.setTranslation(-d0, -d1, -d2);
			worldRendererIn.markDirty();
			Iterator iterator = this.damagedBlocks.values().iterator();

			while (iterator.hasNext()) {
				DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress) iterator.next();
				BlockPos blockpos = destroyblockprogress.getPosition();
				double d3 = (double) blockpos.getX() - d0;
				double d4 = (double) blockpos.getY() - d1;
				double d5 = (double) blockpos.getZ() - d2;
				Block block = this.theWorld.getBlockState(blockpos).getBlock();
				if (!(block instanceof BlockChest) && !(block instanceof BlockEnderChest)
						&& !(block instanceof BlockSign) && !(block instanceof BlockSkull)) {
					if (d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D) {
						iterator.remove();
					} else {
						IBlockState iblockstate = this.theWorld.getBlockState(blockpos);
						if (iblockstate.getBlock().getMaterial() != Material.air) {
							int i = destroyblockprogress.getPartialBlockDamage();
							EaglerTextureAtlasSprite textureatlassprite = this.destroyBlockIcons[i];
							BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
							blockrendererdispatcher.renderBlockDamage(iblockstate, blockpos, textureatlassprite,
									this.theWorld);
						}
					}
				}
			}

			tessellatorIn.draw();
			worldRendererIn.setTranslation(0.0D, 0.0D, 0.0D);
			this.postRenderDamagedBlocks();
		}

	}

	/**+
	 * Draws the selection box for the player. Args: entityPlayer,
	 * rayTraceHit, i, itemStack, partialTickTime
	 */
	public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int partialTicks,
			float parFloat1) {
		if (partialTicks == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
			EaglercraftGPU.glLineWidth(2.0F);
			GlStateManager.disableTexture2D();
			GlStateManager.depthMask(false);
			float f = 0.002F;
			BlockPos blockpos = movingObjectPositionIn.getBlockPos();
			Block block = this.theWorld.getBlockState(blockpos).getBlock();
			if (block.getMaterial() != Material.air && this.theWorld.getWorldBorder().contains(blockpos)) {
				block.setBlockBoundsBasedOnState(this.theWorld, blockpos);
				double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) parFloat1;
				double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) parFloat1;
				double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) parFloat1;
				func_181561_a(block.getSelectedBoundingBox(this.theWorld, blockpos)
						.expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D)
						.offset(-d0, -d1, -d2));
			}

			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
		}

	}

	public static void func_181561_a(AxisAlignedBB parAxisAlignedBB) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(3, DefaultVertexFormats.POSITION);
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.minY, parAxisAlignedBB.maxZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.maxZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ).endVertex();
		tessellator.draw();
		worldrenderer.begin(3, DefaultVertexFormats.POSITION);
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.maxY, parAxisAlignedBB.maxZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.maxZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ).endVertex();
		tessellator.draw();
		worldrenderer.begin(1, DefaultVertexFormats.POSITION);
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.minY, parAxisAlignedBB.maxZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.maxY, parAxisAlignedBB.maxZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.maxZ).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.maxZ).endVertex();
		tessellator.draw();
	}

	public static void func_181563_a(AxisAlignedBB parAxisAlignedBB, int parInt1, int parInt2, int parInt3,
			int parInt4) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldrenderer = tessellator.getWorldRenderer();
		worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.minY, parAxisAlignedBB.maxZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.maxZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		tessellator.draw();
		worldrenderer.begin(3, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.maxY, parAxisAlignedBB.maxZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.maxZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		tessellator.draw();
		worldrenderer.begin(1, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.minY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.maxY, parAxisAlignedBB.minZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.minY, parAxisAlignedBB.maxZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.maxX, parAxisAlignedBB.maxY, parAxisAlignedBB.maxZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.minY, parAxisAlignedBB.maxZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		worldrenderer.pos(parAxisAlignedBB.minX, parAxisAlignedBB.maxY, parAxisAlignedBB.maxZ)
				.color(parInt1, parInt2, parInt3, parInt4).endVertex();
		tessellator.draw();
	}

	/**+
	 * Marks the blocks in the given range for update
	 */
	private void markBlocksForUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
		this.viewFrustum.markBlocksForUpdate(x1, y1, z1, x2, y2, z2);
	}

	public void markBlockForUpdate(BlockPos blockpos) {
		int i = blockpos.getX();
		int j = blockpos.getY();
		int k = blockpos.getZ();
		this.markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
	}

	public void notifyLightSet(BlockPos blockpos) {
		int i = blockpos.getX();
		int j = blockpos.getY();
		int k = blockpos.getZ();
		this.markBlocksForUpdate(i - 1, j - 1, k - 1, i + 1, j + 1, k + 1);
	}

	/**+
	 * On the client, re-renders all blocks in this range,
	 * inclusive. On the server, does nothing. Args: min x, min y,
	 * min z, max x, max y, max z
	 */
	public void markBlockRangeForRenderUpdate(int i, int j, int k, int l, int i1, int j1) {
		this.markBlocksForUpdate(i - 1, j - 1, k - 1, l + 1, i1 + 1, j1 + 1);
	}

	public void playRecord(String s, BlockPos blockpos) {
		ISound isound = (ISound) this.mapSoundPositions.get(blockpos);
		if (isound != null) {
			this.mc.getSoundHandler().stopSound(isound);
			this.mapSoundPositions.remove(blockpos);
		}

		if (s != null) {
			ItemRecord itemrecord = ItemRecord.getRecord(s);
			if (itemrecord != null) {
				this.mc.ingameGUI.setRecordPlayingMessage(itemrecord.getRecordNameLocal());
			}

			PositionedSoundRecord positionedsoundrecord = PositionedSoundRecord.create(new ResourceLocation(s),
					(float) blockpos.getX(), (float) blockpos.getY(), (float) blockpos.getZ());
			this.mapSoundPositions.put(blockpos, positionedsoundrecord);
			this.mc.getSoundHandler().playSound(positionedsoundrecord);
		}

	}

	/**+
	 * Plays the specified sound. Arg: soundName, x, y, z, volume,
	 * pitch
	 */
	public void playSound(String var1, double var2, double var4, double var6, float var8, float var9) {
	}

	/**+
	 * Plays sound to all near players except the player reference
	 * given
	 */
	public void playSoundToNearExcept(EntityPlayer var1, String var2, double var3, double var5, double var7, float var9,
			float var10) {
	}

	public void spawnParticle(int i, boolean flag, final double d0, final double d1, final double d2, double d3,
			double d4, double d5, int... aint) {
		try {
			this.spawnEntityFX(i, flag, d0, d1, d2, d3, d4, d5, aint);
		} catch (Throwable throwable) {
			CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
			CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being added");
			crashreportcategory.addCrashSection("ID", Integer.valueOf(i));
			if (aint != null) {
				crashreportcategory.addCrashSection("Parameters", aint);
			}

			crashreportcategory.addCrashSectionCallable("Position", new Callable<String>() {
				public String call() throws Exception {
					return CrashReportCategory.getCoordinateInfo(d0, d1, d2);
				}
			});
			throw new ReportedException(crashreport);
		}
	}

	private void spawnParticle(EnumParticleTypes particleIn, double parDouble1, double parDouble2, double parDouble3,
			double parDouble4, double parDouble5, double parDouble6, int... parArrayOfInt) {
		this.spawnParticle(particleIn.getParticleID(), particleIn.getShouldIgnoreRange(), parDouble1, parDouble2,
				parDouble3, parDouble4, parDouble5, parDouble6, parArrayOfInt);
	}

	private EntityFX spawnEntityFX(int ignoreRange, boolean parFlag, double parDouble1, double parDouble2,
			double parDouble3, double parDouble4, double parDouble5, double parDouble6, int... parArrayOfInt) {
		if (this.mc != null && this.mc.getRenderViewEntity() != null && this.mc.effectRenderer != null) {
			int i = this.mc.gameSettings.particleSetting;
			if (i == 1 && this.theWorld.rand.nextInt(3) == 0) {
				i = 2;
			}

			double d0 = this.mc.getRenderViewEntity().posX - parDouble1;
			double d1 = this.mc.getRenderViewEntity().posY - parDouble2;
			double d2 = this.mc.getRenderViewEntity().posZ - parDouble3;
			if (parFlag) {
				return this.mc.effectRenderer.spawnEffectParticle(ignoreRange, parDouble1, parDouble2, parDouble3,
						parDouble4, parDouble5, parDouble6, parArrayOfInt);
			} else {
				double d3 = 16.0D;
				return d0 * d0 + d1 * d1 + d2 * d2 > 256.0D ? null
						: (i > 1 ? null
								: this.mc.effectRenderer.spawnEffectParticle(ignoreRange, parDouble1, parDouble2,
										parDouble3, parDouble4, parDouble5, parDouble6, parArrayOfInt));
			}
		} else {
			return null;
		}
	}

	/**+
	 * Called on all IWorldAccesses when an entity is created or
	 * loaded. On client worlds, starts downloading any necessary
	 * textures. On server worlds, adds the entity to the entity
	 * tracker.
	 */
	public void onEntityAdded(Entity var1) {
	}

	/**+
	 * Called on all IWorldAccesses when an entity is unloaded or
	 * destroyed. On client worlds, releases any downloaded
	 * textures. On server worlds, removes the entity from the
	 * entity tracker.
	 */
	public void onEntityRemoved(Entity var1) {
	}

	/**+
	 * Deletes all display lists
	 */
	public void deleteAllDisplayLists() {
	}

	public void broadcastSound(int i, BlockPos blockpos, int var3) {
		switch (i) {
		case 1013:
		case 1018:
			if (this.mc.getRenderViewEntity() != null) {
				double d0 = (double) blockpos.getX() - this.mc.getRenderViewEntity().posX;
				double d1 = (double) blockpos.getY() - this.mc.getRenderViewEntity().posY;
				double d2 = (double) blockpos.getZ() - this.mc.getRenderViewEntity().posZ;
				double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
				double d4 = this.mc.getRenderViewEntity().posX;
				double d5 = this.mc.getRenderViewEntity().posY;
				double d6 = this.mc.getRenderViewEntity().posZ;
				if (d3 > 0.0D) {
					d4 += d0 / d3 * 2.0D;
					d5 += d1 / d3 * 2.0D;
					d6 += d2 / d3 * 2.0D;
				}

				if (i == 1013) {
					this.theWorld.playSound(d4, d5, d6, "mob.wither.spawn", 1.0F, 1.0F, false);
				} else {
					this.theWorld.playSound(d4, d5, d6, "mob.enderdragon.end", 5.0F, 1.0F, false);
				}
			}
		default:
		}
	}

	public void playAuxSFX(EntityPlayer var1, int i, BlockPos blockpos, int j) {
		EaglercraftRandom random = this.theWorld.rand;
		switch (i) {
		case 1000:
			this.theWorld.playSoundAtPos(blockpos, "random.click", 1.0F, 1.0F, false);
			break;
		case 1001:
			this.theWorld.playSoundAtPos(blockpos, "random.click", 1.0F, 1.2F, false);
			break;
		case 1002:
			this.theWorld.playSoundAtPos(blockpos, "random.bow", 1.0F, 1.2F, false);
			break;
		case 1003:
			this.theWorld.playSoundAtPos(blockpos, "random.door_open", 1.0F,
					this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
			break;
		case 1004:
			this.theWorld.playSoundAtPos(blockpos, "random.fizz", 0.5F,
					2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);
			break;
		case 1005:
			if (Item.getItemById(j) instanceof ItemRecord) {
				this.theWorld.playRecord(blockpos, "records." + ((ItemRecord) Item.getItemById(j)).recordName);
			} else {
				this.theWorld.playRecord(blockpos, (String) null);
			}
			break;
		case 1006:
			this.theWorld.playSoundAtPos(blockpos, "random.door_close", 1.0F,
					this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
			break;
		case 1007:
			this.theWorld.playSoundAtPos(blockpos, "mob.ghast.charge", 10.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1008:
			this.theWorld.playSoundAtPos(blockpos, "mob.ghast.fireball", 10.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1009:
			this.theWorld.playSoundAtPos(blockpos, "mob.ghast.fireball", 2.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1010:
			this.theWorld.playSoundAtPos(blockpos, "mob.zombie.wood", 2.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1011:
			this.theWorld.playSoundAtPos(blockpos, "mob.zombie.metal", 2.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1012:
			this.theWorld.playSoundAtPos(blockpos, "mob.zombie.woodbreak", 2.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1014:
			this.theWorld.playSoundAtPos(blockpos, "mob.wither.shoot", 2.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1015:
			this.theWorld.playSoundAtPos(blockpos, "mob.bat.takeoff", 0.05F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1016:
			this.theWorld.playSoundAtPos(blockpos, "mob.zombie.infect", 2.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1017:
			this.theWorld.playSoundAtPos(blockpos, "mob.zombie.unfect", 2.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
			break;
		case 1020:
			this.theWorld.playSoundAtPos(blockpos, "random.anvil_break", 1.0F,
					this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
			break;
		case 1021:
			this.theWorld.playSoundAtPos(blockpos, "random.anvil_use", 1.0F,
					this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
			break;
		case 1022:
			this.theWorld.playSoundAtPos(blockpos, "random.anvil_land", 0.3F,
					this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
			break;
		case 2000:
			int j1 = j % 3 - 1;
			int k = j / 3 % 3 - 1;
			double d15 = (double) blockpos.getX() + (double) j1 * 0.6D + 0.5D;
			double d17 = (double) blockpos.getY() + 0.5D;
			double d19 = (double) blockpos.getZ() + (double) k * 0.6D + 0.5D;

			for (int i2 = 0; i2 < 10; ++i2) {
				double d20 = random.nextDouble() * 0.2D + 0.01D;
				double d21 = d15 + (double) j1 * 0.01D + (random.nextDouble() - 0.5D) * (double) k * 0.5D;
				double d4 = d17 + (random.nextDouble() - 0.5D) * 0.5D;
				double d6 = d19 + (double) k * 0.01D + (random.nextDouble() - 0.5D) * (double) j1 * 0.5D;
				double d8 = (double) j1 * d20 + random.nextGaussian() * 0.01D;
				double d10 = -0.03D + random.nextGaussian() * 0.01D;
				double d12 = (double) k * d20 + random.nextGaussian() * 0.01D;
				this.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d21, d4, d6, d8, d10, d12, new int[0]);
			}

			return;
		case 2001:
			Block block = Block.getBlockById(j & 4095);
			if (block.getMaterial() != Material.air) {
				this.mc.getSoundHandler()
						.playSound(new PositionedSoundRecord(new ResourceLocation(block.stepSound.getBreakSound()),
								(block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getFrequency() * 0.8F,
								(float) blockpos.getX() + 0.5F, (float) blockpos.getY() + 0.5F,
								(float) blockpos.getZ() + 0.5F));
			}

			this.mc.effectRenderer.addBlockDestroyEffects(blockpos, block.getStateFromMeta(j >> 12 & 255));
			break;
		case 2002:
			double d13 = (double) blockpos.getX();
			double d14 = (double) blockpos.getY();
			double d16 = (double) blockpos.getZ();

			for (int k1 = 0; k1 < 8; ++k1) {
				this.spawnParticle(EnumParticleTypes.ITEM_CRACK, d13, d14, d16, random.nextGaussian() * 0.15D,
						random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D,
						new int[] { Item.getIdFromItem(Items.potionitem), j });
			}

			int l1 = Items.potionitem.getColorFromDamage(j);
			float f = (float) (l1 >> 16 & 255) / 255.0F;
			float f1 = (float) (l1 >> 8 & 255) / 255.0F;
			float f2 = (float) (l1 >> 0 & 255) / 255.0F;
			EnumParticleTypes enumparticletypes = EnumParticleTypes.SPELL;
			if (Items.potionitem.isEffectInstant(j)) {
				enumparticletypes = EnumParticleTypes.SPELL_INSTANT;
			}

			for (int j2 = 0; j2 < 100; ++j2) {
				double d22 = random.nextDouble() * 4.0D;
				double d23 = random.nextDouble() * 3.141592653589793D * 2.0D;
				double d24 = Math.cos(d23) * d22;
				double d9 = 0.01D + random.nextDouble() * 0.5D;
				double d11 = Math.sin(d23) * d22;
				EntityFX entityfx = this.spawnEntityFX(enumparticletypes.getParticleID(),
						enumparticletypes.getShouldIgnoreRange(), d13 + d24 * 0.1D, d14 + 0.3D, d16 + d11 * 0.1D, d24,
						d9, d11, new int[0]);
				if (entityfx != null) {
					float f3 = 0.75F + random.nextFloat() * 0.25F;
					entityfx.setRBGColorF(f * f3, f1 * f3, f2 * f3);
					entityfx.multiplyVelocity((float) d22);
				}
			}

			this.theWorld.playSoundAtPos(blockpos, "game.potion.smash", 1.0F,
					this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
			break;
		case 2003:
			double d0 = (double) blockpos.getX() + 0.5D;
			double d1 = (double) blockpos.getY();
			double d2 = (double) blockpos.getZ() + 0.5D;

			for (int l = 0; l < 8; ++l) {
				this.spawnParticle(EnumParticleTypes.ITEM_CRACK, d0, d1, d2, random.nextGaussian() * 0.15D,
						random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D,
						new int[] { Item.getIdFromItem(Items.ender_eye) });
			}

			for (double d18 = 0.0D; d18 < 6.283185307179586D; d18 += 0.15707963267948966D) {
				this.spawnParticle(EnumParticleTypes.PORTAL, d0 + Math.cos(d18) * 5.0D, d1 - 0.4D,
						d2 + Math.sin(d18) * 5.0D, Math.cos(d18) * -5.0D, 0.0D, Math.sin(d18) * -5.0D, new int[0]);
				this.spawnParticle(EnumParticleTypes.PORTAL, d0 + Math.cos(d18) * 5.0D, d1 - 0.4D,
						d2 + Math.sin(d18) * 5.0D, Math.cos(d18) * -7.0D, 0.0D, Math.sin(d18) * -7.0D, new int[0]);
			}

			return;
		case 2004:
			for (int i1 = 0; i1 < 20; ++i1) {
				double d3 = (double) blockpos.getX() + 0.5D + ((double) this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
				double d5 = (double) blockpos.getY() + 0.5D + ((double) this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
				double d7 = (double) blockpos.getZ() + 0.5D + ((double) this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
				this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
				this.theWorld.spawnParticle(EnumParticleTypes.FLAME, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
			}

			return;
		case 2005:
			ItemDye.spawnBonemealParticles(this.theWorld, blockpos, j);
		}

	}

	public void sendBlockBreakProgress(int i, BlockPos blockpos, int j) {
		if (j >= 0 && j < 10) {
			DestroyBlockProgress destroyblockprogress = (DestroyBlockProgress) this.damagedBlocks
					.get(Integer.valueOf(i));
			if (destroyblockprogress == null || destroyblockprogress.getPosition().getX() != blockpos.getX()
					|| destroyblockprogress.getPosition().getY() != blockpos.getY()
					|| destroyblockprogress.getPosition().getZ() != blockpos.getZ()) {
				destroyblockprogress = new DestroyBlockProgress(i, blockpos);
				this.damagedBlocks.put(Integer.valueOf(i), destroyblockprogress);
			}

			destroyblockprogress.setPartialBlockDamage(j);
			destroyblockprogress.setCloudUpdateTick(this.cloudTickCounter);
		} else {
			this.damagedBlocks.remove(Integer.valueOf(i));
		}

	}

	public void setDisplayListEntitiesDirty() {
		this.displayListEntitiesDirty = true;
	}

	public void func_181023_a(Collection<TileEntity> parCollection, Collection<TileEntity> parCollection2) {
		synchronized (this.field_181024_n) {
			this.field_181024_n.removeAll(parCollection);
			this.field_181024_n.addAll(parCollection2);
		}
	}

	class ContainerLocalRenderInformation {
		final RenderChunk renderChunk;
		final EnumFacing facing;
		final Set<EnumFacing> setFacing;
		final int counter;

		private ContainerLocalRenderInformation(RenderChunk renderChunkIn, EnumFacing facingIn, int counterIn) {
			this.setFacing = EnumSet.noneOf(EnumFacing.class);
			this.renderChunk = renderChunkIn;
			this.facing = facingIn;
			this.counter = counterIn;
		}
	}

	public String getDebugInfoShort() {
		int i = this.viewFrustum.renderChunks.length;
		int j = 0;
		int k = 0;

		for (RenderGlobal.ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
			CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;
			if (compiledchunk != CompiledChunk.DUMMY && !compiledchunk.isEmpty()) {
				++j;
				k += compiledchunk.getTileEntities().size();
			}
		}

		return "" + Minecraft.getDebugFPS() + "fps | C: " + j + "/" + i + ", E: " + this.countEntitiesRendered + "+" + k
				+ ", " + renderDispatcher.getDebugInfo();
	}
}