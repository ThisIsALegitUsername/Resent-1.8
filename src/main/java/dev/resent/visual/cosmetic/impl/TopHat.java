package dev.resent.visual.cosmetic.impl;

import dev.resent.visual.cosmetic.CosmeticBase;
import dev.resent.visual.cosmetic.CosmeticController;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class TopHat extends CosmeticBase {

    private final ModelTopHat modelTopHat;
    private static final ResourceLocation hat = new ResourceLocation("eagler:gui/hat.png");

    public TopHat(RenderPlayer renderPlayer) {
        super(renderPlayer);
        modelTopHat = new ModelTopHat(renderPlayer);
    }

    @Override
    public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float HeadYaw, float headPitch, float scale) {
        if (CosmeticController.renderTopHat(player)) {
            GlStateManager.pushMatrix();
            playerRenderer.bindTexture(hat);

            if (player.isSneaking()) {
                GlStateManager.translate(0, 0.225D, 0);
            }

            float[] color = CosmeticController.getTopHatColor(player);
            GlStateManager.color(color[0], color[1], color[2]);
            modelTopHat.render(player, limbSwing, limbSwingAmount, ageInTicks, HeadYaw, headPitch, scale);
            GlStateManager.color(1, 1, 1);
            GlStateManager.popMatrix();
        }
    }

    private class ModelTopHat extends CosmeticModelBase {

        private ModelRenderer rim;
        private ModelRenderer tip;

        public ModelTopHat(RenderPlayer player) {
            super(player);
            rim = new ModelRenderer(playerModel, 0, 0);
            rim.addBox(-5.5F, -9F, -5.5F, 11, 2, 11);
            tip = new ModelRenderer(playerModel, 0, 13);
            tip.addBox(-3.5f, -17, -3.5f, 7, 8, 7);
        }

        @Override
        public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch, float scale) {
            rim.rotateAngleX = playerModel.bipedHead.rotateAngleX;
            rim.rotateAngleY = playerModel.bipedHead.rotateAngleY;
            rim.rotationPointX = 0.0f;
            rim.rotationPointY = 0.0f;
            rim.render(scale);

            tip.rotateAngleX = playerModel.bipedHead.rotateAngleX;
            tip.rotateAngleY = playerModel.bipedHead.rotateAngleY;
            tip.rotationPointX = 0.0f;
            tip.rotationPointY = 0.0f;
            tip.render(scale);
        }
    }
}
