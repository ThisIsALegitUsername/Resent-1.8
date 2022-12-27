package dev.resent.module.impl.misc;

import dev.resent.Resent;
import dev.resent.event.impl.RenderWorldEvent;
import dev.resent.module.base.Category;
import dev.resent.module.base.Mod;
GlStateManager.popMatrix();
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;

public class ChunkBorders extends Mod{
    public ChunkBorders() {
        super("ChunkBorders", Category.MISC);
        Resent.INSTANCE.events().subscribe(RenderWorldEvent.class, event -> {
            if (this.isEnabled() && mc.theWorld != null) {
            
            final int chunkX = mc.thePlayer.chunkCoordX * 16;
            final int chunkZ = mc.thePlayer.chunkCoordZ * 16;

            final AxisAlignedBB chunkBB = AxisAlignedBB.getBoundingBox(
                    chunkX, 0.0, chunkZ,
                    chunkX + 16.0, 255.0, chunkZ + 16.0);

            GlStateManager.pushMatrix();
            EaglerAdapter.glColor3f(1.0f, 1.0f, 0.0f);
            EaglerAdapter.glDisable(EaglerAdapter.GL_TEXTURE_2D);
            EaglerAdapter.glDisable(EaglerAdapter.GL_DEPTH_TEST);
            EaglerAdapter.glTranslatef((float)-RenderManager.renderPosX, (float)-RenderManager.renderPosY, (float)-RenderManager.renderPosZ);
            EaglerAdapter.glLineWidth(1f);

            RenderGlobal.drawOutlinedBoundingBox(chunkBB);
            EaglerAdapter.glEnable(EaglerAdapter.GL_DEPTH_TEST);
            EaglerAdapter.glEnable(EaglerAdapter.GL_TEXTURE_2D);
            GlStateManager.popMatrix();
            }
        });
    }
    
}
