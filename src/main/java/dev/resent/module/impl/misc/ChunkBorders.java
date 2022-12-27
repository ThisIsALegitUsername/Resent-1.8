// package dev.resent.module.impl.misc;

// import dev.resent.Resent;
// import dev.resent.event.impl.RenderWorldEvent;
// import dev.resent.module.base.Category;
// import dev.resent.module.base.Mod;
// import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
// import net.minecraft.client.renderer.RenderGlobal;
// import net.minecraft.client.renderer.entity.RenderManager;
// import net.minecraft.util.AxisAlignedBB;

// public class ChunkBorders extends Mod{
//     public ChunkBorders() {
//         super("ChunkBorders", Category.MISC);
//         Resent.INSTANCE.events().subscribe(RenderWorldEvent.class, event -> {
//             if (this.isEnabled() && mc.theWorld != null) {
            
//             final int chunkX = mc.thePlayer.chunkCoordX * 16;
//             final int chunkZ = mc.thePlayer.chunkCoordZ * 16;

//             final AxisAlignedBB chunkBB = AxisAlignedBB.getBoundingBox(
//                     chunkX, 0.0, chunkZ,
//                     chunkX + 16.0, 255.0, chunkZ + 16.0);

//             GlStateManager.pushMatrix();
//             GlStateManager.color(1.0f, 1.0f, 0.0f);
//             GlStateManager.disableTexture2D();
//             GlStateManager.disableDepth();
//             GlStateManager.translate((float)-RenderManager.renderPosX, (float)-RenderManager.renderPosY, (float)-RenderManager.renderPosZ);
//             GlStateManager.line(1f);

//             RenderGlobal.drawOutlinedBoundingBox(chunkBB);
//             GlStateManager.enableDepth();
//             GlStateManager.enableTexture2D();
//             GlStateManager.popMatrix();
//             }
//         });
//     }
    
// }
