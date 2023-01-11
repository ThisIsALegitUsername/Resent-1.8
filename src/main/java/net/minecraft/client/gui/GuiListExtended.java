package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;

public abstract class GuiListExtended extends GuiSlot {
	public GuiListExtended(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
	}

	/**+
	 * The element in the slot that was clicked, boolean for whether
	 * it was double clicked or not
	 */
	protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
	}

	/**+
	 * Returns true if the element passed in is currently selected
	 */
	protected boolean isSelected(int slotIndex) {
		return false;
	}

	protected void drawBackground() {
	}

	protected void drawSlot(int entryID, int mouseXIn, int mouseYIn, int parInt4, int parInt5, int parInt6) {
		this.getListEntry(entryID).drawEntry(entryID, mouseXIn, mouseYIn, this.getListWidth(), parInt4, parInt5,
				parInt6, this.getSlotIndexFromScreenCoords(parInt5, parInt6) == entryID);
	}

	protected void func_178040_a(int parInt1, int parInt2, int parInt3) {
		this.getListEntry(parInt1).setSelected(parInt1, parInt2, parInt3);
	}

	public boolean mouseClicked(int mouseX, int mouseY, int mouseEvent) {
		if (this.isMouseYWithinSlotBounds(mouseY)) {
			int i = this.getSlotIndexFromScreenCoords(mouseX, mouseY);
			if (i >= 0) {
				int j = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
				int k = this.top + 4 - this.getAmountScrolled() + i * this.slotHeight + this.headerPadding;
				int l = mouseX - j;
				int i1 = mouseY - k;
				if (this.getListEntry(i).mousePressed(i, mouseX, mouseY, mouseEvent, l, i1)) {
					this.setEnabled(false);
					return true;
				}
			}
		}

		return false;
	}

	public boolean mouseReleased(int parInt1, int parInt2, int parInt3) {
		for (int i = 0; i < this.getSize(); ++i) {
			int j = this.left + this.width / 2 - this.getListWidth() / 2 + 2;
			int k = this.top + 4 - this.getAmountScrolled() + i * this.slotHeight + this.headerPadding;
			int l = parInt1 - j;
			int i1 = parInt2 - k;
			this.getListEntry(i).mouseReleased(i, parInt1, parInt2, parInt3, l, i1);
		}

		this.setEnabled(true);
		return false;
	}

	public abstract GuiListExtended.IGuiListEntry getListEntry(int var1);

	public interface IGuiListEntry {
		void setSelected(int var1, int var2, int var3);

		void drawEntry(int var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8);

		boolean mousePressed(int var1, int var2, int var3, int var4, int var5, int var6);

		void mouseReleased(int var1, int var2, int var3, int var4, int var5, int var6);
	}
}