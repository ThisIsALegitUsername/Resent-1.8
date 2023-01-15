// 
// Decompiled by Procyon v0.5.36
// 

package dev.resent.cosmetic.impl;

import dev.resent.cosmetic.CosmeticBase;
import dev.resent.cosmetic.CosmeticController;
import dev.resent.cosmetic.CosmeticModelBase;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class DragonWings extends CosmeticBase{
    private static ModelRenderer wing;
    private static ModelRenderer wingTip;
    boolean flying;
    private final ModelDragonWings modelDragonWings;
    
    @Override
    public void render(final AbstractClientPlayer player, final float limbSwing, final float limbSwingAmount, final float partialTicks, final float ageInTicks, final float HeadYaw, final float headPitch, final float scale) {
        if (CosmeticController.renderDragonWings(player)) {
            GlStateManager.pushMatrix();
            this.modelDragonWings.render(player, limbSwing, limbSwingAmount, ageInTicks, HeadYaw, headPitch, scale);
            this.modelDragonWings.setRotationAngles(scale, limbSwing, limbSwingAmount, ageInTicks, HeadYaw, headPitch, player);
            GlStateManager.popMatrix();
        }
    }
    
    public DragonWings(final RenderPlayer renderPlayer) {
        super(renderPlayer);
        this.flying = false;
        (this.modelDragonWings = new ModelDragonWings(renderPlayer)).setTextureOffset("wingTip.bone", 112, 136);
        this.modelDragonWings.setTextureOffset("wing.skin", -56, 88);
        this.modelDragonWings.setTextureOffset("wing.bone", 112, 88);
        this.modelDragonWings.setTextureOffset("wingTip.skin", -56, 144);
        final int bw = this.modelDragonWings.textureWidth;
        final int bh = this.modelDragonWings.textureHeight;
        this.modelDragonWings.textureWidth = 256;
        this.modelDragonWings.textureHeight = 256;
        (DragonWings.wing = new ModelRenderer(this.modelDragonWings, "wing")).setRotationPoint(-12.0f, 5.0f, 2.0f);
        DragonWings.wing.addBox("bone", -56.0f, -4.0f, -4.0f, 56, 8, 8);
        DragonWings.wing.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        DragonWings.wing.isHidden = true;
        (DragonWings.wingTip = new ModelRenderer(this.modelDragonWings, "wingTip")).setRotationPoint(-56.0f, 0.0f, 0.0f);
        DragonWings.wingTip.isHidden = true;
        DragonWings.wingTip.addBox("bone", -56.0f, -2.0f, -2.0f, 56, 4, 4);
        DragonWings.wingTip.addBox("skin", -56.0f, 0.0f, 2.0f, 56, 0, 56);
        DragonWings.wing.addChild(DragonWings.wingTip);
        this.modelDragonWings.textureWidth = bw;
        this.modelDragonWings.textureWidth = bh;
    }
    



    public void setRotationAngles(final float f, final float f1, final float f2, final float f3, final float f4, final float f5, final Entity entity) {
    }
    
    private class ModelDragonWings extends CosmeticModelBase
    {
        public ModelDragonWings(final RenderPlayer player) {
            super(player);
        }
        
        @Override
        public void render(final Entity entityIn, final float limbSwing, final float limbSwingAmount, final float ageInTicks, final float netHeadYaw, final float headPitch, final float scale) {
            super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GlStateManager.pushMatrix();
                float f1 = 0.0f;
                if (Minecraft.getMinecraft().thePlayer.capabilities.isFlying) {
                    f1 = ageInTicks / 200.0f;
                }
                else {
                    f1 = ageInTicks / 80.0f;
                }
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("eagler:gui/wings.png"));
                float anSpeed = 100.0f;
                if (!entityIn.onGround || DragonWings.this.flying) {
                    anSpeed = 50.0f;
                    DragonWings.this.flying = true;
                }
                GlStateManager.scale(0.15, 0.15, 0.15);
                GlStateManager.translate(0.0, -0.3, 1.1);
                GlStateManager.rotate(50.0f, -50.0f, 0.0f, 0.0f);
                final int x = 0;
                final int index = 0;
                for (int i = 0; i < 2; ++i) {
                    final float f2 = f1 * 9.141593f * 2.0f;
                    DragonWings.wing.rotateAngleX = 0.125f - (float)Math.cos(f2) * 0.2f;
                    DragonWings.wing.rotateAngleY = 0.25f;
                    DragonWings.wing.rotateAngleZ = (float)(Math.sin(f2) + 1.225) * 0.3f;
                    DragonWings.wingTip.rotateAngleZ = -(float)(Math.sin(f2 + 2.0f) + 0.5) * 0.75f;
                    DragonWings.wing.isHidden = false;
                    DragonWings.wingTip.isHidden = false;
                    if (!entityIn.isInvisible()) {
                        GlStateManager.pushMatrix();
                        GlStateManager.disableLighting();
                        DragonWings.wing.render(scale);
                        GlStateManager.blendFunc(1, 1);
                        GlStateManager.enableLighting();
                        GlStateManager.popMatrix();
                    }
                    DragonWings.wing.isHidden = false;
                    DragonWings.wingTip.isHidden = false;
                    if (i == 0) {
                        GlStateManager.scale(-1.0f, 1.0f, 1.0f);
                    }
                }
                GlStateManager.popMatrix();
        }
    }
}