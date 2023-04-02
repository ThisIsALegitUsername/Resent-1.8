package dev.resent.util.physics;

import dev.resent.module.base.ModManager;
import dev.resent.module.impl.hud.ItemPhysics;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class RenderItemPhysics {

    public static int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_, int func_177078_a) {
        ItemStack itemstack = itemIn.getEntityItem();
        Item item = itemstack.getItem();
        Block block = Block.getBlockFromItem(item);
        
        if (item == null)
        {
            return 0;
        }
        else
        {
            boolean flag = p_177077_9_.isGui3d();
            int i = func_177078_a;
            
            if(ModManager.itemPhysics.isEnabled()) {
            	if(block != null) {
                    GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + 0.15F, (float)p_177077_6_);
            	}else {
                    GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + 0.02F, (float)p_177077_6_);
                    GlStateManager.rotate(-90F, 1F, 0F, 0F);
            	}
            }else {
            	float f1 = MathHelper.sin(((float)itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
                float f2 = p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
                
                GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2, (float)p_177077_6_);
            }

            if(!ModManager.itemPhysics.isEnabled()) {
                if (flag || Minecraft.getMinecraft().getRenderManager().options != null)
                {
                    float f3 = (((float)itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float)Math.PI);
                    GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
                }
            }

            if (!flag)
            {
                float f6 = -0.0F * (float)(i - 1) * 0.5F;
                float f4 = -0.0F * (float)(i - 1) * 0.5F;
                float f5 = -0.046875F * (float)(i - 1) * 0.5F;
                GlStateManager.translate(f6, f4, f5);
            }

            if(ModManager.itemPhysics.isEnabled() && !itemIn.onGround) {
            	float angle = System.currentTimeMillis() % (360 * 20) / (float) (4.5 - ItemPhysics.speed.getValue()/2);
            	GlStateManager.rotate(angle, 1F, 1F, 1F);
            }
            


            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return i;
        }
	}

    
}
