package dev.resent.visual.cosmetic;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;

public abstract class CosmeticBase implements LayerRenderer<AbstractClientPlayer> {

    protected final RenderPlayer playerRenderer;

    public CosmeticBase(RenderPlayer playerRenderer) {
        this.playerRenderer = playerRenderer;
    }

    @Override
    public void doRenderLayer(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (player.hasPlayerInfo() && !player.isInvisible()) {
            render(player, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
        }
    }

    public abstract void render(AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale);

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    public class CosmeticModelBase extends ModelBase {

        protected final ModelBiped playerModel;

        public CosmeticModelBase(RenderPlayer player) {
            this.playerModel = player.getMainModel();
        }
    }
}
