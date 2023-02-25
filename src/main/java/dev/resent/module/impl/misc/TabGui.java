package dev.resent.module.impl.misc;

import java.util.List;

import dev.resent.annotation.RenderModule;
import dev.resent.client.Resent;
import dev.resent.module.base.Mod;
import dev.resent.module.base.Mod.Category;
import dev.resent.module.base.RenderMod;
import dev.resent.module.base.setting.BooleanSetting;
import dev.resent.module.base.setting.ModeSetting;
import dev.resent.module.base.setting.NumberSetting;
import dev.resent.module.base.setting.Setting;
import net.lax1dude.eaglercraft.v1_8.internal.KeyboardConstants;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

@RenderModule(name = "TabGUI", category = Category.HUD, x = 30, y = 150)
public class TabGui extends RenderMod {

    public int current = 0;
    public boolean expanded;

    public int getWidth() {
        return expanded ? 139 : 70;
    }

    public int getHeight() {
        return Category.values().length * 16 + 4;
    }

    public void draw(){
    FontRenderer fr = mc.fontRendererObj;
			
			int primaryColor = 0xffff69b4, secondaryColor = 0xffff46b4;
			
			//original tab gui
			Gui.drawRect(5.5, 5.5f, 70, 5 + Mod.Category.values().length * 16 + 1.5, 0x90000000);
            Gui.drawRect(5.5, 5.5f + current * 16, 70, 13 + current * 16 + 7 + 2.5f, primaryColor);
            
			
			int count = 0;
			for (Category c : Mod.Category.values())
			{
				fr.drawStringWithShadow(c.name, 11, 10 + count*16, -1);
				
				count++;
			}
			
			//expansion
			if (expanded)
			{
				Category category = Mod.Category.values()[current];
				List<Mod> modules = Resent.INSTANCE.modManager.modsInCategory(category);
				
				if(modules.size() == 0)
				{
					return;
				}
				
				
				Gui.drawRect(70, 5.5, 138, 5 + modules.size() * 16 + 1.5, 0x90000000);
		        Gui.drawRect(70, 5.5 +  category.i * 16, 138, 8 + category.i * 16 + 12 + 2.5F, primaryColor);
				
				count = 0;
				for (Mod m : modules)
				{
					fr.drawStringWithShadow(m.getName(), 73, 10 + count*16, -1);
					
					if(count == category.i && m.expanded)
					{
						
						int index = 0, maxLength = 0;
						for (Setting setting : m.settings)
						{
							if(setting instanceof BooleanSetting)
							{
								BooleanSetting bool = (BooleanSetting) setting;
								if(maxLength < fr.getStringWidth(setting.name + ": " + (bool.getValue() ? "Enabled" : "Disabled")))
								{
									maxLength = fr.getStringWidth(setting.name + ": " + (bool.getValue() ? "Enabled" : "Disabled"));
								}
							}
							
							if(setting instanceof NumberSetting)
							{
								NumberSetting number = (NumberSetting) setting;
								if(maxLength < fr.getStringWidth(setting.name + ": " + number.getValue()))
								{
									maxLength = fr.getStringWidth(setting.name + ": " + number.getValue());
								}
								//fr.drawStringWithShadow(setting.name + ": " + number.getValue(), 73 + 68, 35 + index*16, -1);
							}
							
							if(setting instanceof ModeSetting)
							{
								ModeSetting mode = (ModeSetting) setting;
								if(maxLength < fr.getStringWidth(setting.name + ": " + mode.getValue()))
								{
									maxLength = fr.getStringWidth(setting.name + ": " + mode.getValue());
								}
								//fr.drawStringWithShadow(setting.name + ": " + mode.getValue(), 73 + 68, 35 + index*16, -1);
							}
							
							index++;
						}
						
						
						if(!m.settings.isEmpty())
						{
							
							Gui.drawRect(70 + 68, 5.5, 70 + 68 + maxLength + 9, 5 + m.settings.size() * 16 + 1.5, 0x90000000);
							Gui.drawRect(70 + 68, 5.5f + m.index * 16, 7 + 61 + maxLength + 70 + 9, 8 + m.index * 16 + 12 + 2.5f,m.settings.get(m.index).focused ? secondaryColor :  primaryColor); //0xffff69b4);
							
							index = 0;
							for (Setting setting : m.settings)
							{
								if(setting instanceof BooleanSetting)
								{
									BooleanSetting bool = (BooleanSetting) setting;
									fr.drawStringWithShadow(setting.name + ": " + (bool.getValue() ? "Enabled" : "Disabled"), 73 + 68, 10 + index*16, -1);
								}
								
								if(setting instanceof ModeSetting)
								{
									ModeSetting mode = (ModeSetting) setting;
									fr.drawStringWithShadow(setting.name + ": " + mode.getValue(), 73 + 68, 10 + index*16, -1);
								}
								
								index++;
							}
						}
					}
					
					count++;
				}
				
			}
		}
		
        public void onKey(int code){
			
			Category category = Mod.Category.values()[current];
			List<Mod> modules = Resent.INSTANCE.modManager.modsInCategory(category);
			
			if(expanded && !modules.isEmpty() && modules.get(category.i).expanded)
			{
				Mod module = modules.get(category.i);

			}
			
			if(code == KeyboardConstants.KEY_UP)
			{
				if(expanded)
				{
					if(expanded && !modules.isEmpty() && modules.get(category.i).expanded)
					{
						Mod module = modules.get(category.i);
						
						if(!module.settings.isEmpty())
						{
							if (module.settings.get(module.index).focused)
							{
								Setting setting = module.settings.get(module.index);
								

	
							}
							else
							{
								if(module.index <= 0)
								{
									module.index = module.settings.size() - 1;
								}
								else
								{
									module.index--;
								}
							}
						}
					}
					else
					{
						if(category.i <= 0)
						{
							category.i = modules.size() - 1;
						}else
							category.i--;
					}
				}else
					if(current <= 0)
					{
						current = Mod.Category.values().length - 1;
					}else
						current--;
			}
			if(code == KeyboardConstants.KEY_DOWN)
			{
				if(expanded)
				{
					if(expanded && !modules.isEmpty() && modules.get(category.i).expanded)
					{
						Mod module = modules.get(category.i);
						
						if(!module.settings.isEmpty())
						{
							if (module.settings.get(module.index).focused)
							{
						}
							else
							{
								if (module.index >= module.settings.size() - 1)
								{
									module.index = 0;
								}
								else
								{
									module.index++;
								}
							}
						}
					}
					else
					{
						if (category.i >= modules.size() - 1)
						{
							category.i = 0;
						}
						else
						{
							category.i++;
						}
					}
				}else
					if (current >= Mod.Category.values().length - 1)
					{
						current = 0;
					}
					else
					{
						current++;
					}
			}
			
			if(code == KeyboardConstants.KEY_RETURN)
			{
				if(expanded && modules.size() != 0)
				{
					Mod module = modules.get(category.i);
					if(!module.expanded && !module.settings.isEmpty())
						module.expanded = true;
					else if(module.expanded && !module.settings.isEmpty())
					{
						module.settings.get(module.index).focused = !module.settings.get(module.index).focused;
					}
				}
			}
			
			
			if(code == KeyboardConstants.KEY_RIGHT)
			{
				if(expanded && modules.size() != 0)
				{
					Mod module = modules.get(category.i);
					

					if(expanded && !modules.isEmpty() && module.expanded)
					{
						if(!module.settings.isEmpty())
						{
							Setting setting = module.settings.get(module.index);
							
							if (setting instanceof BooleanSetting)
							{
								((BooleanSetting)setting).toggle();
							}
							if (setting instanceof ModeSetting)
							{
								((ModeSetting)setting).next();
							}
						}

					}
					else
					{
						if(!module.getName().equals("TabGUI"))
							module.toggle();
					}
				}else
					expanded = true;
			}
			if(code == KeyboardConstants.KEY_LEFT)
			{
				if(expanded && !modules.isEmpty() && modules.get(category.i).expanded)
				{
					Mod module = modules.get(category.i);
					
					if(!module.settings.isEmpty())
					{
						if (module.settings.get(module.index).focused)
						{
							
						}
						else
							modules.get(category.i).expanded = false;
					}
				}else
					expanded = false;
			}
        }
}
