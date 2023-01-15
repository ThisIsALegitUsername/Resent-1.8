package dev.resent.cosmetic.impl;

import dev.resent.cosmetic.CosmeticBase;
import dev.resent.cosmetic.CosmeticController;
import dev.resent.cosmetic.CosmeticModelBase;
import dev.resent.util.render.Color;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class Halo extends CosmeticBase {
    private final ModelHalo modelHalo;
    private static final ResourceLocation HALOBLUE;
    
    static {
        HALOBLUE = new ResourceLocation("eagler:gui/blue.jpeg");
    }
    
    public Halo(final RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.modelHalo = new ModelHalo(renderPlayer);
    }
    
    @Override
    public void render(final AbstractClientPlayer player, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float headYaw, final float headPitch, final float scale) {
        if (CosmeticController.renderHalo(player)) {
            GlStateManager.pushMatrix();
            this.playerRenderer.bindTexture(Halo.HALOBLUE);
            GlStateManager.color(1, 1, 1);
            this.modelHalo.render(player, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scale);
            GlStateManager.color(1.0f, 1.0f, 1.0f);
            GlStateManager.popMatrix();
        }
    }
    
    private class ModelHalo extends CosmeticModelBase
    {
        private ModelRenderer halo;
        private boolean hat;
        
        public ModelHalo(final RenderPlayer player) {
            super(player);
            (this.halo = new ModelRenderer(this.playerModel).setTextureSize(14, 2)).addBox(-3.0f, -12.5f, -4.0f, 6, 1, 1, 0.15f);
            this.halo.isHidden = true;
        }
        
        @Override
        public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float headYaw, final float headPitch, final float scale) {
            GlStateManager.pushMatrix();
            final float f = (float)Math.cos(ageInTicks / 10.0) / 20.0f;
            GlStateManager.rotate(headYaw + ageInTicks / 2.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.0f, f, 0.0f);
            Minecraft.getMinecraft().getTextureManager().bindTexture(Halo.HALOBLUE);
            GlStateManager.disableLighting();
            final ModelRenderer modelrenderer = bindTextureAndColor(Color.WHITE, Halo.HALOBLUE, this.halo, null);
            modelrenderer.isHidden = false;
            for (int i = 0; i < 4; ++i) {
                modelrenderer.render(scale);
                GlStateManager.rotate(90.0f, 0.0f, 1.0f, 0.0f);
            }
            modelrenderer.isHidden = true;
            GlStateManager.enableLighting();
            GlStateManager.popMatrix();
        }

        public ModelRenderer bindTextureAndColor(final Color color, final ResourceLocation resourceLocation, final ModelRenderer colorModel, final ModelRenderer playerSkinModel) {
            final boolean flag = false;
            Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
            return colorModel;
        }
        
    }
}
