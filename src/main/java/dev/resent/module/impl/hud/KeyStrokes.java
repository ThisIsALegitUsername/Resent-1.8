package dev.resent.module.impl.hud;

import java.util.ArrayList;
import java.util.List;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.setting.BooleanSetting;
import dev.resent.setting.ModeSetting;
import dev.resent.util.misc.FuncUtils;
import dev.resent.util.render.RainbowUtil;
import dev.resent.util.render.RenderUtils;
import net.lax1dude.eaglercraft.EaglerAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class KeyStrokes extends RenderModule{

    public static KeyStrokes INSTANCE = new KeyStrokes();
	private Minecraft mc = Minecraft.getMinecraft();

    public KeyStrokes(){
		super("Keystrokes", Category.HUD, 25, 4, true);
		addSetting(chroma, sneak, transparent, lmbcps, rmbcps, tshadow, jump, color, colorp, gcolor, gcolorp, size);
    }

	public BooleanSetting chroma = new BooleanSetting("Rainbow", "", false);
	public BooleanSetting sneak = new BooleanSetting("Sneak", "", false);
	public BooleanSetting transparent = new BooleanSetting("Transparent", "", false);
	public BooleanSetting jump = new BooleanSetting("Jump", "", true);
	public BooleanSetting lmbcps = new BooleanSetting("LMB cps counter", "", true);
	public BooleanSetting rmbcps = new BooleanSetting("RMB cps counter", "", true);
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
	
    @Override
	public void draw() {
		if (!transparent.getValue()) {

		boolean pressed = mc.gameSettings.keyBindAttack.pressed;
        boolean rpressed = mc.gameSettings.keyBindUseItem.pressed;
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

			EaglerAdapter.glTranslatef(this.x + 1, this.y + 1, 0);
			EaglerAdapter.glScalef(getSize(this.size), getSize(this.size), getSize(this.size));
			EaglerAdapter.glTranslatef(-(this.x + 1), -(this.y + 1), 0);

			//W
			Gui.drawRect(this.x + 30, this.y + 3, this.x + 55, this.y + 25 + 3,
					mc.gameSettings.keyBindForward.pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
			// S
			Gui.drawRect(this.x + 30, this.y + 30, this.x + 55, this.y + 55,
					mc.gameSettings.keyBindBack.pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
			// A
			Gui.drawRect(this.x + 3, this.y + 30, this.x + 25 + 3, this.y + 55,
					mc.gameSettings.keyBindLeft.pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
			// D
			Gui.drawRect(this.x + 60 - 3, this.y + 30, this.x + 85 - 3, this.y + 25 + 5 + 25,
					mc.gameSettings.keyBindRight.pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
			// LMB
			Gui.drawRect(this.x+3, this.y+57, this.x+41, this.y+82,
					mc.gameSettings.keyBindAttack.pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
			// RMB
			Gui.drawRect(this.x + 45 - 1, this.y + 60 - 3, this.x + 85 - 3, this.y + 85 - 3,
					mc.gameSettings.keyBindUseItem.pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));

			// Jump
			if(jump.getValue())
			Gui.drawRect(this.x + 3, this.y+84, this.x+85-3,
					this.y + 105 - 6, mc.gameSettings.keyBindJump.pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));
		}
		
		// Sneak
		if (sneak.getValue() && !transparent.getValue())
			Gui.drawRect(this.x + 3, jump.getValue() ? this.y+102 : this.y+84, this.x+85-3,
					jump.getValue() ? this.y+120-3 : this.y+105-6, mc.gameSettings.keyBindSneak.pressed ? RenderUtils.getColor(gcolor) : RenderUtils.getColor(gcolorp));

		
		mc.fontRendererObj.drawString("W", this.x+25+5+(25/2-mc.fontRendererObj.getStringWidth("W") + 4), this.y+8+3, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : mc.gameSettings.keyBindForward.pressed ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
		mc.fontRendererObj.drawString("S", this.x+25+5+(25/2-mc.fontRendererObj.getStringWidth("S") + 4), this.y+38, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : mc.gameSettings.keyBindBack.pressed ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
		mc.fontRendererObj.drawString("A", this.x+3+(25/2-mc.fontRendererObj.getStringWidth("A") + 4), this.y+38, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : mc.gameSettings.keyBindLeft.pressed ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
		mc.fontRendererObj.drawString("D", this.x+-3+25+25+10+(25/2-mc.fontRendererObj.getStringWidth("D") + 4), this.y+38, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : mc.gameSettings.keyBindRight.pressed  ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
		if(jump.getValue())
		mc.fontRendererObj.drawString("\u00A7m-------", this.x+85+(25/2-mc.fontRendererObj.getStringWidth("u00A7m-------") + 4), this.y+92-3, (chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : mc.gameSettings.keyBindJump.pressed  ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color)), tshadow.getValue());
		if(sneak.getValue())
		mc.fontRendererObj.drawString("Sneak", this.x+38+3+(25/2-mc.fontRendererObj.getStringWidth("Sneak") + 4), jump.getValue() ? this.y+92+15+1-3 : this.y+92-4, (chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : mc.gameSettings.keyBindSneak.pressed  ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color)), tshadow.getValue());
		mc.fontRendererObj.drawString("LMB", this.x+3+40/2-mc.fontRendererObj.getStringWidth("LMB")/2, (this.y+60+25/2)-mc.fontRendererObj.FONT_HEIGHT/2-3, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : EaglerAdapter.mouseIsButtonDown(0)  ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
		mc.fontRendererObj.drawString("RMB", this.x+40+3+40/2-mc.fontRendererObj.getStringWidth("RMB")/2, (this.y+60+25/2)-mc.fontRendererObj.FONT_HEIGHT/2-3, chroma.getValue() ? RainbowUtil.getRainbow(4f, 0.8f, 0.85f) : EaglerAdapter.mouseIsButtonDown(1)  ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
		GlStateManager.popMatrix();
		GlStateManager.pushMatrix();
		EaglerAdapter.glTranslatef(this.x + 1, this.y + 1, 0);
			EaglerAdapter.glScalef(getSize(this.size), getSize(this.size), getSize(this.size));
			EaglerAdapter.glTranslatef(-(this.x + 1), -(this.y + 1), 0);
		EaglerAdapter.glTranslatef(this.x+41, this.y+82, 0);
		EaglerAdapter.glScalef(0.5f, 0.5f, 0);
		EaglerAdapter.glTranslatef(-(this.x+41), -(this.y+82), 0);

		if(lmbcps.getValue())
		mc.fontRendererObj.drawString(getLeftCPS() + " CPS", this.x-10, this.y+72, EaglerAdapter.mouseIsButtonDown(0)  ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
		if(rmbcps.getValue())
		mc.fontRendererObj.drawString(getRightCPS() + " CPS", this.x+70, this.y+72, EaglerAdapter.mouseIsButtonDown(1)  ? RenderUtils.getColor(colorp) : RenderUtils.getColor(color), tshadow.getValue());
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		EaglerAdapter.glTranslatef(this.x + 1, this.y + 1, 0);
			EaglerAdapter.glTranslatef(-(this.x + 1), -(this.y + 1), 0);
		this.setHeight((25 + 5 + 25 + 5 + 25 + 25));
		this.setWidth((25 + 5 + 25 + 5 + 30));

		GlStateManager.popMatrix();
	}

}