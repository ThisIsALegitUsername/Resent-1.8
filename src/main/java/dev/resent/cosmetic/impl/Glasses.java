package dev.resent.cosmetic.impl;

import dev.resent.cosmetic.CosmeticBase;
import dev.resent.cosmetic.CosmeticController;
import dev.resent.cosmetic.CosmeticModelBase;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;

public class Glasses extends CosmeticBase {
  private final GlassesRenderer glassesModel;
 
  public Glasses(RenderPlayer renderPlayer) {
    super(renderPlayer);
    this.glassesModel = new GlassesRenderer(renderPlayer);
  }
 
  @Override
  public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float headYaw, float headPitch, float scale) {
    if(CosmeticController.renderGlasses(player)){
    GlStateManager.pushMatrix();
    if(player.isSneaking()) {
      GlStateManager.translate(0, 0.225, 0);
    }
    GlStateManager.rotate(headYaw, 0.0F, 1.0F, 0.0F);
    GlStateManager.rotate(headPitch, 1.0F, 0.0F, 0.0F);
    this.glassesModel.render(player, limbSwing, limbSwingAmount, ageInTicks, headYaw, headPitch, scale);
    GlStateManager.popMatrix();
  }
  }
 
  public class GlassesRenderer extends CosmeticModelBase{
 
      ModelRenderer Glasses1;
      ModelRenderer Glasses2;
      ModelRenderer Glasses3;
      ModelRenderer Glasses4;
      ModelRenderer Glasses5;
      ModelRenderer Glasses6;
      ModelRenderer Glasses7;
      ModelRenderer Glasses8;
      ModelRenderer Glasses9;
 
    public GlassesRenderer(RenderPlayer player) {
      super(player);
        textureWidth = 64;
        textureHeight = 32;
 
          Glasses1 = new ModelRenderer(this, 0, 0);
          Glasses1.addBox(0F, 0F, 0F, 4, 2, 1);
          Glasses1.setRotationPoint(-5F, -3F, -5F);
          Glasses1.setTextureSize(64, 32);
          Glasses1.mirror = true;
          setRotation(Glasses1, 0F, 0F, 0F);
          Glasses2 = new ModelRenderer(this, 0, 0);
          Glasses2.addBox(0F, 0F, 0F, 10, 1, 1);
          Glasses2.setRotationPoint(-5F, -3F, -5F);
          Glasses2.setTextureSize(64, 32);
          Glasses2.mirror = true;
          setRotation(Glasses2, 0F, 0F, 0F);
          Glasses3 = new ModelRenderer(this, 0, 0);
          Glasses3.addBox(0F, 0F, 0F, 4, 2, 1);
          Glasses3.setRotationPoint(1F, -3F, -5F);
          Glasses3.setTextureSize(64, 32);
          Glasses3.mirror = true;
          setRotation(Glasses3, 0F, 0F, 0F);
          Glasses4 = new ModelRenderer(this, 0, 0);
          Glasses4.addBox(-3F, 0F, -2F, 1, 1, 6);
          Glasses4.setRotationPoint(-2F, -3F, -3F);
          Glasses4.setTextureSize(64, 32);
          Glasses4.mirror = true;
          setRotation(Glasses4, 0F, 0F, 0F);
          Glasses5 = new ModelRenderer(this, 0, 0);
          Glasses5.addBox(0F, 0F, 0F, 1, 1, 6);
          Glasses5.setRotationPoint(4F, -3F, -5F);
          Glasses5.setTextureSize(64, 32);
          Glasses5.mirror = true;
          setRotation(Glasses5, 0F, 0F, 0F);
          Glasses6 = new ModelRenderer(this, 0, 0);
          Glasses6.addBox(0F, 0F, 0F, 1, 1, 1);
          Glasses6.setRotationPoint(4F, -2F, 1F);
          Glasses6.setTextureSize(64, 32);
          Glasses6.mirror = true;
          setRotation(Glasses6, 0F, 0F, 0F);
          Glasses7 = new ModelRenderer(this, 0, 0);
          Glasses7.addBox(0F, 0F, 0F, 1, 1, 1);
          Glasses7.setRotationPoint(-5F, -2F, 1F);
          Glasses7.setTextureSize(64, 32);
          Glasses7.mirror = true;
          setRotation(Glasses7, 0F, 0F, 0F);
          Glasses8 = new ModelRenderer(this, 0, 0);
          Glasses8.addBox(0F, 0F, 0F, 4, 2, 1);
          Glasses8.setRotationPoint(-5F, -3F, -5F);
          Glasses8.setTextureSize(64, 32);
          Glasses8.mirror = true;
          setRotation(Glasses8, 0F, 0F, 0F);
          Glasses9 = new ModelRenderer(this, 0, 0);
          Glasses9.addBox(1F, -3F, -5F, 4, 2, 1);
          Glasses9.setRotationPoint(0F, 0F, 0F);
          Glasses9.setTextureSize(64, 32);
          Glasses9.mirror = true;
          setRotation(Glasses9, 0F, 0F, 0F);
    }
 
    public void render(Entity entityIn, float limbSwing, float limbSwingAmout, float ageInTicks, float headYaw, float headPitch, float scale) {
      GlStateManager.pushMatrix();
      GlStateManager.translate(0, -0.1, -0.05);
      GlStateManager.color(0, 0, 0);
        Glasses1.render(scale);
        Glasses2.render(scale);
        Glasses3.render(scale);
        Glasses4.render(scale);
        Glasses5.render(scale);
        Glasses6.render(scale);
        Glasses7.render(scale);
        Glasses8.render(scale);
        Glasses9.render(scale);
        GlStateManager.color(1, 1, 1);
      GlStateManager.popMatrix();
    }  
  }
 
    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
    }
}