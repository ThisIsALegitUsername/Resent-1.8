package net.minecraft.client.gui;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.ResourcePackListEntry;
import net.minecraft.util.EnumChatFormatting;

public abstract class GuiResourcePackList extends GuiListExtended {
	protected final Minecraft mc;
	protected final List<ResourcePackListEntry> field_148204_l;

	public GuiResourcePackList(Minecraft mcIn, int parInt1, int parInt2, List<ResourcePackListEntry> parList) {
		super(mcIn, parInt1, parInt2, 32, parInt2 - 55 + 4, 36);
		this.mc = mcIn;
		this.field_148204_l = parList;
		this.field_148163_i = false;
		this.setHasListHeader(true, (int) ((float) mcIn.fontRendererObj.FONT_HEIGHT * 1.5F));
	}

	/**+
	 * Handles drawing a list's header row.
	 */
	protected void drawListHeader(int i, int j, Tessellator var3) {
		String s = EnumChatFormatting.UNDERLINE + "" + EnumChatFormatting.BOLD + this.getListHeader();
		this.mc.fontRendererObj.drawString(s, i + this.width / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2,
				Math.min(this.top + 3, j), 16777215);
	}

	protected abstract String getListHeader();

	public List<ResourcePackListEntry> getList() {
		return this.field_148204_l;
	}

	protected int getSize() {
		return this.getList().size();
	}

	/**+
	 * Gets the IGuiListEntry object for the given index
	 */
	public ResourcePackListEntry getListEntry(int i) {
		return (ResourcePackListEntry) this.getList().get(i);
	}

	/**+
	 * Gets the width of the list
	 */
	public int getListWidth() {
		return this.width;
	}

	protected int getScrollBarX() {
		return this.right - 6;
	}
}