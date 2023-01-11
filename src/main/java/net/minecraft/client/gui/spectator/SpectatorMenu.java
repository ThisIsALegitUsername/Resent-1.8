package net.minecraft.client.gui.spectator;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSpectator;
import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

/**+
 * This portion of EaglercraftX contains deobfuscated Minecraft 1.8 source code.
 * 
 * Minecraft 1.8.8 bytecode is (c) 2015 Mojang AB. "Do not distribute!"
 * Mod Coder Pack v9.18 deobfuscation configs are (c) Copyright by the MCP Team
 * 
 * EaglercraftX 1.8 patch files are (c) 2022-2023 LAX1DUDE. All Rights Reserved.
 * 
 * WITH THE EXCEPTION OF PATCH FILES, MINIFIED JAVASCRIPT, AND ALL FILES
 * NORMALLY FOUND IN AN UNMODIFIED MINECRAFT RESOURCE PACK, YOU ARE NOT ALLOWED
 * TO SHARE, DISTRIBUTE, OR REPURPOSE ANY FILE USED BY OR PRODUCED BY THE
 * SOFTWARE IN THIS REPOSITORY WITHOUT PRIOR PERMISSION FROM THE PROJECT AUTHOR.
 * 
 * NOT FOR COMMERCIAL OR MALICIOUS USE
 * 
 * (please read the 'LICENSE' file this repo's root directory for more info) 
 * 
 */
public class SpectatorMenu {
	private static final ISpectatorMenuObject field_178655_b = new SpectatorMenu.EndSpectatorObject();
	private static final ISpectatorMenuObject field_178656_c = new SpectatorMenu.MoveMenuObject(-1, true);
	private static final ISpectatorMenuObject field_178653_d = new SpectatorMenu.MoveMenuObject(1, true);
	private static final ISpectatorMenuObject field_178654_e = new SpectatorMenu.MoveMenuObject(1, false);
	public static final ISpectatorMenuObject field_178657_a = new ISpectatorMenuObject() {
		public void func_178661_a(SpectatorMenu var1) {
		}

		public IChatComponent getSpectatorName() {
			return new ChatComponentText("");
		}

		public void func_178663_a(float var1, int var2) {
		}

		public boolean func_178662_A_() {
			return false;
		}
	};
	private final ISpectatorMenuRecipient field_178651_f;
	private final List<SpectatorDetails> field_178652_g = Lists.newArrayList();
	private ISpectatorMenuView field_178659_h = new BaseSpectatorGroup();
	private int field_178660_i = -1;
	private int field_178658_j;

	public SpectatorMenu(ISpectatorMenuRecipient parISpectatorMenuRecipient) {
		this.field_178651_f = parISpectatorMenuRecipient;
	}

	public ISpectatorMenuObject func_178643_a(int parInt1) {
		int i = parInt1 + this.field_178658_j * 6;
		return this.field_178658_j > 0 && parInt1 == 0 ? field_178656_c
				: (parInt1 == 7 ? (i < this.field_178659_h.func_178669_a().size() ? field_178653_d : field_178654_e)
						: (parInt1 == 8 ? field_178655_b
								: (i >= 0 && i < this.field_178659_h.func_178669_a().size()
										? (ISpectatorMenuObject) Objects.firstNonNull(
												this.field_178659_h.func_178669_a().get(i), field_178657_a)
										: field_178657_a)));
	}

	public List<ISpectatorMenuObject> func_178642_a() {
		ArrayList arraylist = Lists.newArrayList();

		for (int i = 0; i <= 8; ++i) {
			arraylist.add(this.func_178643_a(i));
		}

		return arraylist;
	}

	public ISpectatorMenuObject func_178645_b() {
		return this.func_178643_a(this.field_178660_i);
	}

	public ISpectatorMenuView func_178650_c() {
		return this.field_178659_h;
	}

	public void func_178644_b(int parInt1) {
		ISpectatorMenuObject ispectatormenuobject = this.func_178643_a(parInt1);
		if (ispectatormenuobject != field_178657_a) {
			if (this.field_178660_i == parInt1 && ispectatormenuobject.func_178662_A_()) {
				ispectatormenuobject.func_178661_a(this);
			} else {
				this.field_178660_i = parInt1;
			}
		}

	}

	public void func_178641_d() {
		this.field_178651_f.func_175257_a(this);
	}

	public int func_178648_e() {
		return this.field_178660_i;
	}

	public void func_178647_a(ISpectatorMenuView parISpectatorMenuView) {
		this.field_178652_g.add(this.func_178646_f());
		this.field_178659_h = parISpectatorMenuView;
		this.field_178660_i = -1;
		this.field_178658_j = 0;
	}

	public SpectatorDetails func_178646_f() {
		return new SpectatorDetails(this.field_178659_h, this.func_178642_a(), this.field_178660_i);
	}

	static class EndSpectatorObject implements ISpectatorMenuObject {
		private EndSpectatorObject() {
		}

		public void func_178661_a(SpectatorMenu spectatormenu) {
			spectatormenu.func_178641_d();
		}

		public IChatComponent getSpectatorName() {
			return new ChatComponentText("Close menu");
		}

		public void func_178663_a(float var1, int var2) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
			Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0F, 0.0F, 16, 16, 256.0F, 256.0F);
		}

		public boolean func_178662_A_() {
			return true;
		}
	}

	static class MoveMenuObject implements ISpectatorMenuObject {
		private final int field_178666_a;
		private final boolean field_178665_b;

		public MoveMenuObject(int parInt1, boolean parFlag) {
			this.field_178666_a = parInt1;
			this.field_178665_b = parFlag;
		}

		public void func_178661_a(SpectatorMenu spectatormenu) {
			spectatormenu.field_178658_j = this.field_178666_a;
		}

		public IChatComponent getSpectatorName() {
			return this.field_178666_a < 0 ? new ChatComponentText("Previous Page")
					: new ChatComponentText("Next Page");
		}

		public void func_178663_a(float var1, int var2) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
			if (this.field_178666_a < 0) {
				Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0F, 0.0F, 16, 16, 256.0F, 256.0F);
			} else {
				Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0F, 0.0F, 16, 16, 256.0F, 256.0F);
			}

		}

		public boolean func_178662_A_() {
			return this.field_178665_b;
		}
	}
}