package dev.resent.cosmetic.impl;

import dev.resent.cosmetic.CosmeticBase;
import dev.resent.cosmetic.CosmeticController;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class CrystalWings extends CosmeticBase {
  private CrytsalWingsModel crytsalWingsModel;
  
  public CrystalWings(RenderPlayer playerRenderer) {
    super(playerRenderer);
    this.crytsalWingsModel = new CrytsalWingsModel(playerRenderer);
  }
  
  @Override
  public void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    if(CosmeticController.renderCrystalWings(player)){
        GlStateManager.pushMatrix();
        float[] color  = CosmeticController.getCrystalWingsColor(player);
        GlStateManager.color(color[0], color[1], color[2]);
	    this.crytsalWingsModel.render(player, limbSwing, limbSwingAmount, ageInTicks, headPitch, headPitch, scale); 
        GlStateManager.color(1, 1, 1);
        GlStateManager.popMatrix();
    }
  }
  
  public class CrytsalWingsModel extends CosmeticModelBase {
    private ModelRenderer model;
    
    ResourceLocation resourceLocation = new ResourceLocation("eagler:gui/crystal.png");
    
    public CrytsalWingsModel(RenderPlayer player) {
      super(player);
      int i = 30;
      int j = 24;
      this.model = (new ModelRenderer((ModelBase)this)).setTextureSize(i, j).setTextureOffset(0, 8);
      this.model.setRotationPoint(-0.0F, 1.0F, 0.0F);
      this.model.addBox(0.0F, -3.0F, 0.0F, 14, 7, 1);
      this.model.isHidden = true;
      ModelRenderer modelrenderer = (new ModelRenderer((ModelBase)this)).setTextureSize(i, j).setTextureOffset(0, 16);
      modelrenderer.setRotationPoint(-0.0F, 0.0F, 0.2F);
      modelrenderer.addBox(0.0F, -3.0F, 0.0F, 14, 7, 1);
      this.model.addChild(modelrenderer);
      ModelRenderer modelrenderer1 = (new ModelRenderer((ModelBase)this)).setTextureSize(i, j).setTextureOffset(0, 0);
      modelrenderer1.setRotationPoint(-0.0F, 0.0F, 0.2F);
      modelrenderer1.addBox(0.0F, -3.0F, 0.0F, 14, 7, 1);
      modelrenderer.addChild(modelrenderer1);
    }
  
    public void render(Entity entityIn, float p_78088_2_, float walkingSpeed, float tickValue, float p_78088_5_, float p_78088_6_, float scale) {
      float f = (float)Math.cos((tickValue / 10.0F)) / 20.0F - 0.03F - walkingSpeed / 20.0F;
      ModelRenderer modelrenderer = (ModelRenderer) this.model.childModels.get(0);
      ModelRenderer modelrenderer1 = (ModelRenderer) modelrenderer.childModels.get(0);
      this.model.rotateAngleZ = f * 3.0F;
      modelrenderer.rotateAngleZ = f / 2.0F;
      modelrenderer1.rotateAngleZ = f / 2.0F;
      this.model.rotateAngleY = -0.3F - walkingSpeed / 3.0F;
      this.model.rotateAngleX = 0.3F;
      GlStateManager.pushMatrix();
      GlStateManager.scale(1.6D, 1.6D, 1.0D);
      GlStateManager.translate(0.0D, 0.05000000074505806D, 0.05000000074505806D);
      if (entityIn.isSneaking()) {
        GlStateManager.translate(0.0D, 0.07999999821186066D, 0.029999999329447746D);
        GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
        this.model.rotateAngleZ = 0.8F;
        modelrenderer.rotateAngleZ = 0.0F;
        modelrenderer1.rotateAngleZ = 0.0F;
      } else {
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        if (rendermanager != null)
          GlStateManager.rotate(rendermanager.playerViewX / 3.0F, 1.0F, 0.0F, 0.0F); 
      } 
      this.model.isHidden = false;
      for (int i = -1; i <= 1; i += 2) {
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);
        GlStateManager.alphaFunc(516, 0.003921569F);
        GlStateManager.disableLighting();
        

        Minecraft.getMinecraft().getTextureManager().bindTexture(this.resourceLocation);
        if (i == 1)
          GlStateManager.scale(-1.0F, 1.0F, 1.0F); 
        GlStateManager.translate(0.05D, 0.0D, 0.0D);
        this.model.render(scale);
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.color(1,1,1);
      } 
      this.model.isHidden = true;
      GlStateManager.popMatrix();
    }
  }
}