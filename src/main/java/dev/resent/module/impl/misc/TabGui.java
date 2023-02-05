package dev.resent.module.impl.misc;

import dev.resent.Resent;
import dev.resent.annotation.RenderModule;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.util.render.RenderUtils;
import net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

@RenderModule(name = "TabGUI", category = Category.HUD, x = 30, y = 150)
public class TabGui extends RenderMod{

    public int current = 0;
    public boolean expanded;

    public int getWidth(){
        return expanded ? 100 : 75;
    }

    public int getHeight(){
        return Category.values().length*16+4;
    }

    public void draw() {
        Gui.drawRect(x+5, y+2, x+70, y+3+Category.values().length*16, 0x90000000);
        RenderUtils.drawChromaRectangle(x+5, y+2+current*16, x+70, y+18.5f+current*16, 10, 0xff900000);

        int offset = 0;
        for(Category c : Category.values()){
            Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(c.name, x+10, y+6.5f+offset, -1);
            offset += 16;
        }

        if(expanded){

            Category category = Category.values()[current];
            if(Resent.INSTANCE.modManager.modsInCategory(category).size() == 0)
                return;

            Gui.drawRect(x+70, y+2, x+138, y+3+Resent.INSTANCE.modManager.modsInCategory(category).size()*16, 0x90000000);
            RenderUtils.drawChromaRectangle(x+70, y+2+category.i*16, x+138, y+18.5f+category.i*16, 10, 0xff900000);

            offset = 0;
            for(Mod m : Resent.INSTANCE.modManager.modsInCategory(category)){
                Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(m.name, x+73, y+6.5f+offset, -1);
                offset += 16;
            }
        }
    }

    public void onKey(int k){
        Category category = Category.values()[current];
        if (k ==KeyboardConstants.KEY_UP) {
            if(expanded){
                if(category.i <= 0){
                    category.i = Resent.INSTANCE.modManager.modsInCategory(category).size()-1;
                }else{
                    --category.i;
                }
            }else {
                if(current <= 0){
                    current = Category.values().length-1;
                }else {
                    --current;
                }
            }
        }
        if (k ==KeyboardConstants.KEY_DOWN) {
            if(expanded){
                if(category.i >= Resent.INSTANCE.modManager.modsInCategory(category).size() - 1){
                    category.i = 0;
                }else {
                    ++category.i;
                }
            }else {
                if(current >= Category.values().length-1){
                    current = 0;
                }else {
                    ++current;
                }
            }
        }
        if (k ==KeyboardConstants.KEY_RIGHT){
            if(expanded && Resent.INSTANCE.modManager.modsInCategory(category).size() != 0 && Resent.INSTANCE.modManager.modsInCategory(category).get(category.i).name != "TabGUI"){
                Resent.INSTANCE.modManager.modsInCategory(category).get(category.i).toggle();
                mc.gameSettings.saveOptions();
            }else {
                expanded = true;
            }
        }
        if (k ==KeyboardConstants.KEY_LEFT){
            expanded = false;
        }
    }
    
}
