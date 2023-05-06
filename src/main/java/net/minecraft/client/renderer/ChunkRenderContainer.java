package net.minecraft.client.renderer;

import java.util.List;

import com.google.common.collect.Lists;

import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.lax1dude.eaglercraft.v1_8.opengl.ext.deferred.DeferredStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;

public abstract class ChunkRenderContainer {
	private double viewEntityX;
	private double viewEntityY;
	private double viewEntityZ;
	protected List<RenderChunk> renderChunks = Lists.newArrayListWithCapacity(17424);
	protected boolean initialized;

	public void initialize(double viewEntityXIn, double viewEntityYIn, double viewEntityZIn) {
		this.initialized = true;
		this.renderChunks.clear();
		this.viewEntityX = viewEntityXIn;
		this.viewEntityY = viewEntityYIn;
		this.viewEntityZ = viewEntityZIn;
	}

	public void preRenderChunk(RenderChunk renderChunkIn, EnumWorldBlockLayer enumworldblocklayer) {
		BlockPos blockpos = renderChunkIn.getPosition();
		float posX = (float) ((double) blockpos.getX() - this.viewEntityX);
		float posY = (float) ((double) blockpos.getY() - this.viewEntityY);
		float posZ = (float) ((double) blockpos.getZ() - this.viewEntityZ);
		GlStateManager.translate(posX, posY, posZ);
		if (DeferredStateManager.isInForwardPass()) {
			posX = (float) (blockpos.getX() - (MathHelper.floor_double(this.viewEntityX / 16.0) << 4)); // TODO
			posY = (float) (blockpos.getY() - (MathHelper.floor_double(this.viewEntityY / 16.0) << 4));
			posZ = (float) (blockpos.getZ() - (MathHelper.floor_double(this.viewEntityZ / 16.0) << 4));
			DeferredStateManager.reportForwardRenderObjectPosition((int) posX, (int) posY, (int) posZ);
		}
	}

	public void addRenderChunk(RenderChunk renderChunkIn, EnumWorldBlockLayer layer) {
		this.renderChunks.add(renderChunkIn);
	}

	public abstract void renderChunkLayer(EnumWorldBlockLayer var1);
}