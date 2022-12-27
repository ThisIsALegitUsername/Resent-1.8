package dev.resent.module.impl.hud;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import dev.resent.module.base.Category;
import dev.resent.module.base.RenderModule;
import dev.resent.util.render.RainbowUtil;
import net.lax1dude.eaglercraft.EaglerAdapter;

public class FakeArray extends RenderModule{

    public List<String> fakemods = new ArrayList<>();

    public FakeArray(){
        super("Fake Hacks", Category.HUD, 800, 4);
        fakemods.add("AutoClicker");
        fakemods.add("AimAssist");
        fakemods.add("Reach");
        fakemods.add("Velocity");
    }

    public int getWidth(){ return mc.fontRenderer.getStringWidth("Autoclicker "); }
    public int getHeight(){ return 50; }

    public void draw(){
        fakemods.sort(Comparator.comparingInt(m -> mc.fontRenderer.getStringWidth((String) m)).reversed());
        int count = 0;
        for (int i = 0; i < fakemods.size(); i++) {
            
            EaglerAdapter.glPushMatrix();
            mc.fontRenderer.drawStringWithShadow(fakemods.get(i), x + 2, count * mc.fontRenderer.FONT_HEIGHT + (y) + 4, RainbowUtil.getRainbow1(50));
            EaglerAdapter.glPopMatrix();
            count++;
        }

    }
}