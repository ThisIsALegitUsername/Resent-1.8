package net.minecraft.client.gui;

import java.util.List;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IntHashMap;

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
public class GuiPageButtonList extends GuiListExtended {
	private final List<GuiPageButtonList.GuiEntry> field_178074_u = Lists.newArrayList();
	private final IntHashMap<Gui> field_178073_v = new IntHashMap();
	private final List<GuiTextField> field_178072_w = Lists.newArrayList();
	private final GuiPageButtonList.GuiListEntry[][] field_178078_x;
	private int field_178077_y;
	private GuiPageButtonList.GuiResponder field_178076_z;
	private Gui field_178075_A;

	public GuiPageButtonList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn,
			GuiPageButtonList.GuiResponder parGuiResponder, GuiPageButtonList.GuiListEntry[]... parArrayOfarray) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
		this.field_178076_z = parGuiResponder;
		this.field_178078_x = parArrayOfarray;
		this.field_148163_i = false;
		this.func_178069_s();
		this.func_178055_t();
	}

	private void func_178069_s() {
		for (GuiPageButtonList.GuiListEntry[] aguipagebuttonlist$guilistentry : this.field_178078_x) {
			for (int i = 0; i < aguipagebuttonlist$guilistentry.length; i += 2) {
				GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry = aguipagebuttonlist$guilistentry[i];
				GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry1 = i < aguipagebuttonlist$guilistentry.length
						- 1 ? aguipagebuttonlist$guilistentry[i + 1] : null;
				Gui gui = this.func_178058_a(guipagebuttonlist$guilistentry, 0,
						guipagebuttonlist$guilistentry1 == null);
				Gui gui1 = this.func_178058_a(guipagebuttonlist$guilistentry1, 160,
						guipagebuttonlist$guilistentry == null);
				GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = new GuiPageButtonList.GuiEntry(gui, gui1);
				this.field_178074_u.add(guipagebuttonlist$guientry);
				if (guipagebuttonlist$guilistentry != null && gui != null) {
					this.field_178073_v.addKey(guipagebuttonlist$guilistentry.func_178935_b(), gui);
					if (gui instanceof GuiTextField) {
						this.field_178072_w.add((GuiTextField) gui);
					}
				}

				if (guipagebuttonlist$guilistentry1 != null && gui1 != null) {
					this.field_178073_v.addKey(guipagebuttonlist$guilistentry1.func_178935_b(), gui1);
					if (gui1 instanceof GuiTextField) {
						this.field_178072_w.add((GuiTextField) gui1);
					}
				}
			}
		}

	}

	private void func_178055_t() {
		this.field_178074_u.clear();

		for (int i = 0; i < this.field_178078_x[this.field_178077_y].length; i += 2) {
			GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry = this.field_178078_x[this.field_178077_y][i];
			GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry1 = i < this.field_178078_x[this.field_178077_y].length
					- 1 ? this.field_178078_x[this.field_178077_y][i + 1] : null;
			Gui gui = (Gui) this.field_178073_v.lookup(guipagebuttonlist$guilistentry.func_178935_b());
			Gui gui1 = guipagebuttonlist$guilistentry1 != null
					? (Gui) this.field_178073_v.lookup(guipagebuttonlist$guilistentry1.func_178935_b())
					: null;
			GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = new GuiPageButtonList.GuiEntry(gui, gui1);
			this.field_178074_u.add(guipagebuttonlist$guientry);
		}

	}

	public void func_181156_c(int parInt1) {
		if (parInt1 != this.field_178077_y) {
			int i = this.field_178077_y;
			this.field_178077_y = parInt1;
			this.func_178055_t();
			this.func_178060_e(i, parInt1);
			this.amountScrolled = 0.0F;
		}
	}

	public int func_178059_e() {
		return this.field_178077_y;
	}

	public int func_178057_f() {
		return this.field_178078_x.length;
	}

	public Gui func_178056_g() {
		return this.field_178075_A;
	}

	public void func_178071_h() {
		if (this.field_178077_y > 0) {
			this.func_181156_c(this.field_178077_y - 1);
		}

	}

	public void func_178064_i() {
		if (this.field_178077_y < this.field_178078_x.length - 1) {
			this.func_181156_c(this.field_178077_y + 1);
		}

	}

	public Gui func_178061_c(int parInt1) {
		return (Gui) this.field_178073_v.lookup(parInt1);
	}

	private void func_178060_e(int parInt1, int parInt2) {
		for (GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry : this.field_178078_x[parInt1]) {
			if (guipagebuttonlist$guilistentry != null) {
				this.func_178066_a((Gui) this.field_178073_v.lookup(guipagebuttonlist$guilistentry.func_178935_b()),
						false);
			}
		}

		for (GuiPageButtonList.GuiListEntry guipagebuttonlist$guilistentry1 : this.field_178078_x[parInt2]) {
			if (guipagebuttonlist$guilistentry1 != null) {
				this.func_178066_a((Gui) this.field_178073_v.lookup(guipagebuttonlist$guilistentry1.func_178935_b()),
						true);
			}
		}

	}

	private void func_178066_a(Gui parGui, boolean parFlag) {
		if (parGui instanceof GuiButton) {
			((GuiButton) parGui).visible = parFlag;
		} else if (parGui instanceof GuiTextField) {
			((GuiTextField) parGui).setVisible(parFlag);
		} else if (parGui instanceof GuiLabel) {
			((GuiLabel) parGui).visible = parFlag;
		}

	}

	private Gui func_178058_a(GuiPageButtonList.GuiListEntry parGuiListEntry, int parInt1, boolean parFlag) {
		return (Gui) (parGuiListEntry instanceof GuiPageButtonList.GuiSlideEntry
				? this.func_178067_a(this.width / 2 - 155 + parInt1, 0,
						(GuiPageButtonList.GuiSlideEntry) parGuiListEntry)
				: (parGuiListEntry instanceof GuiPageButtonList.GuiButtonEntry
						? this.func_178065_a(this.width / 2 - 155 + parInt1, 0,
								(GuiPageButtonList.GuiButtonEntry) parGuiListEntry)
						: (parGuiListEntry instanceof GuiPageButtonList.EditBoxEntry
								? this.func_178068_a(this.width / 2 - 155 + parInt1, 0,
										(GuiPageButtonList.EditBoxEntry) parGuiListEntry)
								: (parGuiListEntry instanceof GuiPageButtonList.GuiLabelEntry
										? this.func_178063_a(this.width / 2 - 155 + parInt1, 0,
												(GuiPageButtonList.GuiLabelEntry) parGuiListEntry, parFlag)
										: null))));
	}

	public void func_181155_a(boolean parFlag) {
		for (GuiPageButtonList.GuiEntry guipagebuttonlist$guientry : this.field_178074_u) {
			if (guipagebuttonlist$guientry.field_178029_b instanceof GuiButton) {
				((GuiButton) guipagebuttonlist$guientry.field_178029_b).enabled = parFlag;
			}

			if (guipagebuttonlist$guientry.field_178030_c instanceof GuiButton) {
				((GuiButton) guipagebuttonlist$guientry.field_178030_c).enabled = parFlag;
			}
		}

	}

	public boolean mouseClicked(int i, int j, int k) {
		boolean flag = super.mouseClicked(i, j, k);
		int l = this.getSlotIndexFromScreenCoords(i, j);
		if (l >= 0) {
			GuiPageButtonList.GuiEntry guipagebuttonlist$guientry = this.getListEntry(l);
			if (this.field_178075_A != guipagebuttonlist$guientry.field_178028_d && this.field_178075_A != null
					&& this.field_178075_A instanceof GuiTextField) {
				((GuiTextField) this.field_178075_A).setFocused(false);
			}

			this.field_178075_A = guipagebuttonlist$guientry.field_178028_d;
		}

		return flag;
	}

	private GuiSlider func_178067_a(int parInt1, int parInt2, GuiPageButtonList.GuiSlideEntry parGuiSlideEntry) {
		GuiSlider guislider = new GuiSlider(this.field_178076_z, parGuiSlideEntry.func_178935_b(), parInt1, parInt2,
				parGuiSlideEntry.func_178936_c(), parGuiSlideEntry.func_178943_e(), parGuiSlideEntry.func_178944_f(),
				parGuiSlideEntry.func_178942_g(), parGuiSlideEntry.func_178945_a());
		guislider.visible = parGuiSlideEntry.func_178934_d();
		return guislider;
	}

	private GuiListButton func_178065_a(int parInt1, int parInt2, GuiPageButtonList.GuiButtonEntry parGuiButtonEntry) {
		GuiListButton guilistbutton = new GuiListButton(this.field_178076_z, parGuiButtonEntry.func_178935_b(), parInt1,
				parInt2, parGuiButtonEntry.func_178936_c(), parGuiButtonEntry.func_178940_a());
		guilistbutton.visible = parGuiButtonEntry.func_178934_d();
		return guilistbutton;
	}

	private GuiTextField func_178068_a(int parInt1, int parInt2, GuiPageButtonList.EditBoxEntry parEditBoxEntry) {
		GuiTextField guitextfield = new GuiTextField(parEditBoxEntry.func_178935_b(), this.mc.fontRendererObj, parInt1,
				parInt2, 150, 20);
		guitextfield.setText(parEditBoxEntry.func_178936_c());
		guitextfield.func_175207_a(this.field_178076_z);
		guitextfield.setVisible(parEditBoxEntry.func_178934_d());
		guitextfield.func_175205_a(parEditBoxEntry.func_178950_a());
		return guitextfield;
	}

	private GuiLabel func_178063_a(int parInt1, int parInt2, GuiPageButtonList.GuiLabelEntry parGuiLabelEntry,
			boolean parFlag) {
		GuiLabel guilabel;
		if (parFlag) {
			guilabel = new GuiLabel(this.mc.fontRendererObj, parGuiLabelEntry.func_178935_b(), parInt1, parInt2,
					this.width - parInt1 * 2, 20, -1);
		} else {
			guilabel = new GuiLabel(this.mc.fontRendererObj, parGuiLabelEntry.func_178935_b(), parInt1, parInt2, 150,
					20, -1);
		}

		guilabel.visible = parGuiLabelEntry.func_178934_d();
		guilabel.func_175202_a(parGuiLabelEntry.func_178936_c());
		guilabel.setCentered();
		return guilabel;
	}

	public void func_178062_a(char parChar1, int parInt1) {
		if (this.field_178075_A instanceof GuiTextField) {
			GuiTextField guitextfield = (GuiTextField) this.field_178075_A;
			if (!GuiScreen.isKeyComboCtrlV(parInt1)) {
				if (parInt1 == 15) {
					guitextfield.setFocused(false);
					int k = this.field_178072_w.indexOf(this.field_178075_A);
					if (GuiScreen.isShiftKeyDown()) {
						if (k == 0) {
							k = this.field_178072_w.size() - 1;
						} else {
							--k;
						}
					} else if (k == this.field_178072_w.size() - 1) {
						k = 0;
					} else {
						++k;
					}

					this.field_178075_A = (Gui) this.field_178072_w.get(k);
					guitextfield = (GuiTextField) this.field_178075_A;
					guitextfield.setFocused(true);
					int l = guitextfield.yPosition + this.slotHeight;
					int i1 = guitextfield.yPosition;
					if (l > this.bottom) {
						this.amountScrolled += (float) (l - this.bottom);
					} else if (i1 < this.top) {
						this.amountScrolled = (float) i1;
					}
				} else {
					guitextfield.textboxKeyTyped(parChar1, parInt1);
				}

			} else {
				String s = GuiScreen.getClipboardString();
				String[] astring = s.split(";");
				int i = this.field_178072_w.indexOf(this.field_178075_A);
				int j = i;

				for (String s1 : astring) {
					((GuiTextField) this.field_178072_w.get(j)).setText(s1);
					if (j == this.field_178072_w.size() - 1) {
						j = 0;
					} else {
						++j;
					}

					if (j == i) {
						break;
					}
				}

			}
		}
	}

	/**+
	 * Gets the IGuiListEntry object for the given index
	 */
	public GuiPageButtonList.GuiEntry getListEntry(int i) {
		return (GuiPageButtonList.GuiEntry) this.field_178074_u.get(i);
	}

	public int getSize() {
		return this.field_178074_u.size();
	}

	/**+
	 * Gets the width of the list
	 */
	public int getListWidth() {
		return 400;
	}

	protected int getScrollBarX() {
		return super.getScrollBarX() + 32;
	}

	public static class EditBoxEntry extends GuiPageButtonList.GuiListEntry {
		private final Predicate<String> field_178951_a;

		public EditBoxEntry(int parInt1, String parString1, boolean parFlag, Predicate<String> parPredicate) {
			super(parInt1, parString1, parFlag);
			this.field_178951_a = (Predicate) Objects.firstNonNull(parPredicate, Predicates.alwaysTrue());
		}

		public Predicate<String> func_178950_a() {
			return this.field_178951_a;
		}
	}

	public static class GuiButtonEntry extends GuiPageButtonList.GuiListEntry {
		private final boolean field_178941_a;

		public GuiButtonEntry(int parInt1, String parString1, boolean parFlag, boolean parFlag2) {
			super(parInt1, parString1, parFlag);
			this.field_178941_a = parFlag2;
		}

		public boolean func_178940_a() {
			return this.field_178941_a;
		}
	}

	public static class GuiEntry implements GuiListExtended.IGuiListEntry {
		private final Minecraft field_178031_a = Minecraft.getMinecraft();
		private final Gui field_178029_b;
		private final Gui field_178030_c;
		private Gui field_178028_d;

		public GuiEntry(Gui parGui, Gui parGui2) {
			this.field_178029_b = parGui;
			this.field_178030_c = parGui2;
		}

		public Gui func_178022_a() {
			return this.field_178029_b;
		}

		public Gui func_178021_b() {
			return this.field_178030_c;
		}

		public void drawEntry(int var1, int var2, int i, int var4, int var5, int j, int k, boolean var8) {
			this.func_178017_a(this.field_178029_b, i, j, k, false);
			this.func_178017_a(this.field_178030_c, i, j, k, false);
		}

		private void func_178017_a(Gui parGui, int parInt1, int parInt2, int parInt3, boolean parFlag) {
			if (parGui != null) {
				if (parGui instanceof GuiButton) {
					this.func_178024_a((GuiButton) parGui, parInt1, parInt2, parInt3, parFlag);
				} else if (parGui instanceof GuiTextField) {
					this.func_178027_a((GuiTextField) parGui, parInt1, parFlag);
				} else if (parGui instanceof GuiLabel) {
					this.func_178025_a((GuiLabel) parGui, parInt1, parInt2, parInt3, parFlag);
				}

			}
		}

		private void func_178024_a(GuiButton parGuiButton, int parInt1, int parInt2, int parInt3, boolean parFlag) {
			parGuiButton.yPosition = parInt1;
			if (!parFlag) {
				parGuiButton.drawButton(this.field_178031_a, parInt2, parInt3);
			}

		}

		private void func_178027_a(GuiTextField parGuiTextField, int parInt1, boolean parFlag) {
			parGuiTextField.yPosition = parInt1;
			if (!parFlag) {
				parGuiTextField.drawTextBox();
			}

		}

		private void func_178025_a(GuiLabel parGuiLabel, int parInt1, int parInt2, int parInt3, boolean parFlag) {
			parGuiLabel.field_146174_h = parInt1;
			if (!parFlag) {
				parGuiLabel.drawLabel(this.field_178031_a, parInt2, parInt3);
			}

		}

		public void setSelected(int var1, int var2, int i) {
			this.func_178017_a(this.field_178029_b, i, 0, 0, true);
			this.func_178017_a(this.field_178030_c, i, 0, 0, true);
		}

		public boolean mousePressed(int var1, int i, int j, int k, int var5, int var6) {
			boolean flag = this.func_178026_a(this.field_178029_b, i, j, k);
			boolean flag1 = this.func_178026_a(this.field_178030_c, i, j, k);
			return flag || flag1;
		}

		private boolean func_178026_a(Gui parGui, int parInt1, int parInt2, int parInt3) {
			if (parGui == null) {
				return false;
			} else if (parGui instanceof GuiButton) {
				return this.func_178023_a((GuiButton) parGui, parInt1, parInt2, parInt3);
			} else {
				if (parGui instanceof GuiTextField) {
					this.func_178018_a((GuiTextField) parGui, parInt1, parInt2, parInt3);
				}

				return false;
			}
		}

		private boolean func_178023_a(GuiButton parGuiButton, int parInt1, int parInt2, int parInt3) {
			boolean flag = parGuiButton.mousePressed(this.field_178031_a, parInt1, parInt2);
			if (flag) {
				this.field_178028_d = parGuiButton;
			}

			return flag;
		}

		private void func_178018_a(GuiTextField parGuiTextField, int parInt1, int parInt2, int parInt3) {
			parGuiTextField.mouseClicked(parInt1, parInt2, parInt3);
			if (parGuiTextField.isFocused()) {
				this.field_178028_d = parGuiTextField;
			}

		}

		public void mouseReleased(int var1, int i, int j, int k, int var5, int var6) {
			this.func_178016_b(this.field_178029_b, i, j, k);
			this.func_178016_b(this.field_178030_c, i, j, k);
		}

		private void func_178016_b(Gui parGui, int parInt1, int parInt2, int parInt3) {
			if (parGui != null) {
				if (parGui instanceof GuiButton) {
					this.func_178019_b((GuiButton) parGui, parInt1, parInt2, parInt3);
				}

			}
		}

		private void func_178019_b(GuiButton parGuiButton, int parInt1, int parInt2, int parInt3) {
			parGuiButton.mouseReleased(parInt1, parInt2);
		}
	}

	public static class GuiLabelEntry extends GuiPageButtonList.GuiListEntry {
		public GuiLabelEntry(int parInt1, String parString1, boolean parFlag) {
			super(parInt1, parString1, parFlag);
		}
	}

	public static class GuiListEntry {
		private final int field_178939_a;
		private final String field_178937_b;
		private final boolean field_178938_c;

		public GuiListEntry(int parInt1, String parString1, boolean parFlag) {
			this.field_178939_a = parInt1;
			this.field_178937_b = parString1;
			this.field_178938_c = parFlag;
		}

		public int func_178935_b() {
			return this.field_178939_a;
		}

		public String func_178936_c() {
			return this.field_178937_b;
		}

		public boolean func_178934_d() {
			return this.field_178938_c;
		}
	}

	public interface GuiResponder {
		void func_175321_a(int var1, boolean var2);

		void onTick(int var1, float var2);

		void func_175319_a(int var1, String var2);
	}

	public static class GuiSlideEntry extends GuiPageButtonList.GuiListEntry {
		private final GuiSlider.FormatHelper field_178949_a;
		private final float field_178947_b;
		private final float field_178948_c;
		private final float field_178946_d;

		public GuiSlideEntry(int parInt1, String parString1, boolean parFlag, GuiSlider.FormatHelper parFormatHelper,
				float parFloat1, float parFloat2, float parFloat3) {
			super(parInt1, parString1, parFlag);
			this.field_178949_a = parFormatHelper;
			this.field_178947_b = parFloat1;
			this.field_178948_c = parFloat2;
			this.field_178946_d = parFloat3;
		}

		public GuiSlider.FormatHelper func_178945_a() {
			return this.field_178949_a;
		}

		public float func_178943_e() {
			return this.field_178947_b;
		}

		public float func_178944_f() {
			return this.field_178948_c;
		}

		public float func_178942_g() {
			return this.field_178946_d;
		}
	}
}