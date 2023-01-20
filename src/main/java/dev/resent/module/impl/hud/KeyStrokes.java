package dev.resent.module.impl.hud;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;
import dev.resent.util.render.RainbowUtil;
import dev.resent.util.render.RenderUtils;
import net.lax1dude.eaglercraft.v1_8.Keyboard;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class KeyStrokes extends RenderModule {
    public static KeyStrokes INSTANCE = new KeyStrokes();
    private final Minecraft mc = Minecraft.getMinecraft();

    public KeyStrokes() {
        super("Keystrokes", Category.HUD, 25, 4, true);
        addSetting(chroma, sneak, transparent, tshadow, jump, color, colorp, gcolor, gcolorp, size);
    }
 
    public BooleanSetting chroma = new BooleanSetting("Rainbow", "", false);
    public BooleanSetting sneak = new BooleanSetting("Sneak", "", false);
    public BooleanSetting jump = new BooleanSetting("Jump", "", true);
    public BooleanSetting transparent = new BooleanSetting("Transparent", "", false);
    public BooleanSetting tshadow = new BooleanSetting("Text Shadow", "", false);
    public ModeSetting size = new ModeSetting("Size", "", "Small", "Normal", "Large");
    public ModeSetting color = new ModeSetting("Unpressed text color", "", "White", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "Black");
    public ModeSetting colorp = new ModeSetting("Pressed text color", "", "Black", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "White");
    public ModeSetting gcolor = new ModeSetting("Pressed button color", "", "White", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "Black");
    public ModeSetting gcolorp = new ModeSetting("Unpressed button color", "", "Black", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "White");

    public float getSize(ModeSetting size) {
        if (size.getValue() == "Small") return 0.75f;
        if (size.getValue() == "Normal") return 1.0f;
        if (size.getValue() == "Large") return 1.25f;
        return 1.0f;
    }

    @Override
    public void draw() {

        boolean wKey = Keyboard.isKeyDown(mc.gameSettings.keyBindForward.getKeyCode());
        boolean aKey = Keyboard.isKeyDown(mc.gameSettings.keyBindLeft.getKeyCode());
        boolean sKey = Keyboard.isKeyDown(mc.gameSettings.keyBindBack.getKeyCode());
        boolean dKey = Keyboard.isKeyDown(mc.gameSettings.keyBindRight.getKeyCode());
        boolean spaceKey = Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode());
        boolean pressed = Keyboard.isKeyDown(mc.gameSettings.keyBindAttack.getKeyCode());
        boolean rpressed = Keyboard.isKeyDown(mc.gameSettings.keyBindUseItem.getKeyCode());
        
        GlStateManager.pushMatrix();

        GlStateManager.translate(this.x + 1, this.y + 1, 0);
        GlStateManager.scale(getSize(this.size), getSize(this.size), getSize(this.size));
        GlStateManager.translate(-(this.x + 1), -(this.y + 1), 0);

        if (!transparent.getValue()) {

            //W
            Gui.drawRect(this.x + 30, this.y + 3, this.x + 55, this.y + 25 + 3, wKey ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
            // S
            Gui.drawRect(this.x + 30, this.y + 30, this.x + 55, this.y + 55, sKey ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
            // A
            Gui.drawRect(this.x + 3, this.y + 30, this.x + 25 + 3, this.y + 55, aKey ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
            // D
            Gui.drawRect(this.x + 60 - 3, this.y + 30, this.x + 85 - 3, this.y + 25 + 5 + 25, dKey ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
            // LMB
            Gui.drawRect(this.x + 3, this.y + 57, this.x + 41, this.y + 82, pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
            // RMB
            Gui.drawRect(this.x + 45 - 1, this.y + 60 - 3, this.x + 85 - 3, this.y + 85 - 3, rpressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));

            // Jump
            if (jump.getValue()) Gui.drawRect(this.x + 3, this.y + 84, this.x + 85 - 3, this.y + 105 - 6, spaceKey ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));

            // Sneak
            if (sneak.getValue()) Gui.drawRect(this.x + 3, jump.getValue() ? this.y + 102 : this.y + 84, this.x + 85 - 3, jump.getValue() ? this.y + 120 - 3 : this.y + 105 - 6, mc.gameSettings.keyBindSneak.pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
        }

        mc.fontRendererObj.drawString("W", this.x + 25 + 5 + (25 / 2 - mc.fontRendererObj.getStringWidth("W") + 4), this.y + 8 + 3, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : wKey ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
        mc.fontRendererObj.drawString("S", this.x + 25 + 5 + (25 / 2 - mc.fontRendererObj.getStringWidth("S") + 4), this.y + 38, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : sKey ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
        mc.fontRendererObj.drawString("A", this.x + 3 + (25 / 2 - mc.fontRendererObj.getStringWidth("A") + 4), this.y + 38, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : aKey ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
        mc.fontRendererObj.drawString("D", this.x + -3 + 25 + 25 + 10 + (25 / 2 - mc.fontRendererObj.getStringWidth("D") + 4), this.y + 38, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : dKey ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
        if (jump.getValue()) mc.fontRendererObj.drawString("\u00A7m-------", this.x + 85 + (25 / 2 - mc.fontRendererObj.getStringWidth("u00A7m-------") + 4), this.y + 92 - 3, (chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : spaceKey ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color)), tshadow.getValue());
        if (sneak.getValue()) mc.fontRendererObj.drawString("Sneak", this.x + 38 + 3 + (25 / 2 - mc.fontRendererObj.getStringWidth("Sneak") + 4), jump.getValue() ? this.y + 92 + 15 + 1 - 3 : this.y + 92 - 4, (chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : mc.gameSettings.keyBindSneak.pressed ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color)), tshadow.getValue());
        mc.fontRendererObj.drawString("LMB", this.x + 3 + 40 / 2 - mc.fontRendererObj.getStringWidth("LMB") / 2, (this.y + 60 + 25 / 2) - mc.fontRendererObj.FONT_HEIGHT / 2 - 3, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : Mouse.isButtonDown(0) ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
        mc.fontRendererObj.drawString("RMB", this.x + 40 + 3 + 40 / 2 - mc.fontRendererObj.getStringWidth("RMB") / 2, (this.y + 60 + 25 / 2) - mc.fontRendererObj.FONT_HEIGHT / 2 - 3, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : Mouse.isButtonDown(1) ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());

        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.x + 1, this.y + 1, 0);
        GlStateManager.translate(-(this.x + 1), -(this.y + 1), 0);
        this.setHeight((25 + 5 + 25 + 5 + 25 + 25));
        this.setWidth((25 + 5 + 25 + 5 + 30));

        GlStateManager.popMatrix();
    }

}
