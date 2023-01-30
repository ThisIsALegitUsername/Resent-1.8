package dev.resent.module.impl.hud;

import java.util.ArrayList;
import java.util.List;

//import dev.resent.animation.SimpleAnimation;
import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.module.setting.BooleanSetting;
import dev.resent.module.setting.ModeSetting;
import dev.resent.util.misc.FuncUtils;
import dev.resent.util.render.Color;
import dev.resent.util.render.RainbowUtil;
import dev.resent.util.render.RenderUtils;
import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.lax1dude.eaglercraft.v1_8.opengl.GlStateManager;
import net.minecraft.client.Minecraft;

public class KeyStrokes extends RenderModule{

    public static KeyStrokes INSTANCE = new KeyStrokes();
	private Minecraft mc = Minecraft.getMinecraft();

    public KeyStrokes(){
		super("Keystrokes", Category.HUD, 25, 4, true);
		addSetting(chroma, sneak, transparent, tshadow, jump, color, colorp, gcolor, gcolorp, size);
    }

	public BooleanSetting chroma = new BooleanSetting("Rainbow", "", false);
	public BooleanSetting sneak = new BooleanSetting("Sneak", "", false);
	public BooleanSetting transparent = new BooleanSetting("Transparent", "", false);
	public BooleanSetting jump = new BooleanSetting("Jump", "", true);
	public BooleanSetting tshadow = new BooleanSetting("Text Shadow", "", false);
	public ModeSetting size = new ModeSetting("Size", "", "Small", "Normal", "Large");
	public ModeSetting color = new ModeSetting("Unpressed text color", "", "White", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "Black");
	public ModeSetting colorp = new ModeSetting("Pressed text color", "", "Black", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "White");
	public ModeSetting gcolor = new ModeSetting("Pressed button color", "", "White", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "Black");
	public ModeSetting gcolorp = new ModeSetting("Unpressed button color", "", "Black", "Red", "Yellow", "Green", "Blue", "Pink", "Orange", "White");
	public List<Long> clicks = new ArrayList<>();
    public boolean wasPressed;
    public long lastPressed;
    private List<Long> clicks2 = new ArrayList<>();
    public boolean wasPressed2;
    public long lastPressed2;

	public float getSize(ModeSetting size) {
		if (size.getValue() == "Small")
			return 0.75f;
		if (size.getValue() == "Normal")
			return 1.0f;
		if (size.getValue() == "Large")
			return 1.25f;
		return 1.0f;
	}

	public int getLeftCPS() {        final long leftTime = System.currentTimeMillis() + 100L;
        FuncUtils.removeIf(clicks, beenLeftTime -> beenLeftTime + 1200L < leftTime + 200L);
        return this.clicks.size();
    }

    public int getRightCPS() {
        final long rightTime = System.currentTimeMillis() + 100L;
        FuncUtils.removeIf(clicks2, beenRightTime -> beenRightTime + 1200L < rightTime + 200L);
        return this.clicks2.size();
    }

	//public static SimpleAnimation wOpacityAnimation = new SimpleAnimation(0), aOpacityAnimation = new SimpleAnimation(0), dOpacityAnimation = new SimpleAnimation(0), sOpacityAnimation = new SimpleAnimation(0), jumpOpacityAnimation = new SimpleAnimation(0);
	
    @Override
	public void draw() {

		boolean pressed = mc.gameSettings.keyBindAttack.pressed;
        boolean rpressed = mc.gameSettings.keyBindUseItem.pressed;
		boolean wKey = mc.gameSettings.keyBindForward.pressed;
		boolean aKey = mc.gameSettings.keyBindLeft.pressed;
		boolean dKey = mc.gameSettings.keyBindRight.pressed;
		boolean sKey = mc.gameSettings.keyBindBack.pressed;
		boolean jumpKey = mc.gameSettings.keyBindJump.pressed;

		// wOpacityAnimation.setAnimation(wKey ? 0.8f*255 : 0, 14);
		// aOpacityAnimation.setAnimation(aKey ? 0.8f*255 : 0, 14);
		// dOpacityAnimation.setAnimation(dKey ? 0.8f*255 : 0, 14);
		// sOpacityAnimation.setAnimation(sKey ? 0.8f*255 : 0, 14);
		// jumpOpacityAnimation.setAnimation(jumpKey ? 0.8f*255 : 0, 14);

        if (pressed != this.wasPressed) {
            this.lastPressed = System.currentTimeMillis();
            this.wasPressed = pressed;
            if (pressed)
                this.clicks.add(Long.valueOf(this.lastPressed));
        }
        if (rpressed != this.wasPressed2) {
            this.lastPressed2 = System.currentTimeMillis() + 10L;
            this.wasPressed2 = rpressed;
            if (rpressed)
                this.clicks2.add(Long.valueOf(this.lastPressed2));
        }

			GlStateManager.pushMatrix();

		    GlStateManager.translate(this.x + 1, this.y + 1, 0);
			GlStateManager.scale(getSize(this.size), getSize(this.size), getSize(this.size));
			GlStateManager.translate(-(this.x + 1), -(this.y + 1), 0);

            if (!transparent.getValue()) {
			//W
			RenderUtils.drawRoundedRect(this.x + 30, this.y + 3, this.x + 55, this.y + 25 + 3, 4,
					wKey ? getColor(gcolor) : getColor(gcolorp));
			// S
			RenderUtils.drawRoundedRect(this.x + 30, this.y + 30, this.x + 55, this.y + 55, 4,
					sKey ? getColor(gcolor) : getColor(gcolorp));
			// A
			RenderUtils.drawRoundedRect(this.x + 3, this.y + 30, this.x + 25 + 3, this.y + 55, 4,
					aKey ? getColor(gcolor) : getColor(gcolorp));
			// D
			RenderUtils.drawRoundedRect(this.x + 60 - 3, this.y + 30, this.x + 85 - 3, this.y + 25 + 5 + 25, 4,
					dKey ? getColor(gcolor) : getColor(gcolorp));
			// LMB
			RenderUtils.drawRoundedRect(this.x+3, this.y+57, this.x+41, this.y+82, 4,
					pressed ? getColor(gcolor) : getColor(gcolorp));
			// RMB
			RenderUtils.drawRoundedRect(this.x + 45 - 1, this.y + 60 - 3, this.x + 85 - 3, this.y + 85 - 3, 4,
					rpressed ? getColor(gcolor) : getColor(gcolorp));

			// Jump
			if(jump.getValue())
			RenderUtils.drawRoundedRect(this.x + 3, this.y+84, this.x+85-3,
					this.y + 105 - 6, 4, jumpKey ? getColor(gcolor) : getColor(gcolorp));
		}
		
		// Sneak
		if (sneak.getValue() && !transparent.getValue())
			RenderUtils.drawRoundedRect(this.x + 3, jump.getValue() ? this.y+102 : this.y+84, this.x+85-3,
					jump.getValue() ? this.y+120-3 : this.y+105-6, 4, mc.gameSettings.keyBindSneak.pressed ? getColor(gcolor) : getColor(gcolorp));

		
		mc.fontRendererObj.drawString("W", this.x+25+5+(25/2-mc.fontRendererObj.getStringWidth("W") + 4), this.y+8+3, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : wKey ? getColor(colorp) : getColor(color), tshadow.getValue());
		mc.fontRendererObj.drawString("S", this.x+25+5+(25/2-mc.fontRendererObj.getStringWidth("S") + 4), this.y+38, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : sKey ? getColor(colorp) : getColor(color), tshadow.getValue());
		mc.fontRendererObj.drawString("A", this.x+3+(25/2-mc.fontRendererObj.getStringWidth("A") + 4), this.y+38, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : aKey ? getColor(colorp) : getColor(color), tshadow.getValue());
		mc.fontRendererObj.drawString("D", this.x+-3+25+25+10+(25/2-mc.fontRendererObj.getStringWidth("D") + 4), this.y+38, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : dKey  ? getColor(colorp) : getColor(color), tshadow.getValue());
		if(jump.getValue())
		mc.fontRendererObj.drawString("\u00A7m-------", this.x+85+(25/2-mc.fontRendererObj.getStringWidth("u00A7m-------") + 4), this.y+92-3, (chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : jumpKey  ? getColor(colorp) : getColor(color)), tshadow.getValue());
		if(sneak.getValue())
		mc.fontRendererObj.drawString("Sneak", this.x+38+3+(25/2-mc.fontRendererObj.getStringWidth("Sneak") + 4), jump.getValue() ? this.y+92+15+1-3 : this.y+92-4, (chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : mc.gameSettings.keyBindSneak.pressed  ? getColor(colorp) : getColor(color)), tshadow.getValue());
		mc.fontRendererObj.drawString("LMB", this.x+3+40/2-mc.fontRendererObj.getStringWidth("LMB")/2, (this.y+60+25/2)-mc.fontRendererObj.FONT_HEIGHT/2-3, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : Mouse.isButtonDown(0)  ? getColor(colorp) : getColor(color), tshadow.getValue());
		mc.fontRendererObj.drawString("RMB", this.x+40+3+40/2-mc.fontRendererObj.getStringWidth("RMB")/2, (this.y+60+25/2)-mc.fontRendererObj.FONT_HEIGHT/2-3, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : Mouse.isButtonDown(1)  ? getColor(colorp) : getColor(color), tshadow.getValue());
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		GlStateManager.translate(this.x + 1, this.y + 1, 0);
	    GlStateManager.translate(-(this.x + 1), -(this.y + 1), 0);
		this.setHeight((25 + 5 + 25 + 5 + 25 + 25));
		this.setWidth((25 + 5 + 25 + 5 + 30));

		GlStateManager.popMatrix();
	}

    public static int getColor(ModeSetting asdf) {

        switch (asdf.getValue()) {
            case "Red":
                return new Color(255, 0, 0, 140).getRGB();
            case "Yellow":
                return new Color(255, 255, 0, 140).getRGB();
            case "Green":
                return new Color(0, 255, 0, 140).getRGB();
            case "Blue":
                return new Color(0, 0, 255, 140).getRGB();
            case "Orange":
                return new Color(255, 165, 0, 140).getRGB();
            case "Pink":
                return new Color(255, 102, 255, 140).getRGB();
            case "Black":
                return new Color(0, 0, 0, 140).getRGB();
            case "White":
                return new Color(255, 255, 255, 140).getRGB();
        }
        return -1;
    }

}