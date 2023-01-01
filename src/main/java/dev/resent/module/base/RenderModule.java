package dev.resent.module.base;

import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.util.Objects;

public class RenderModule extends Mod {

	public int x, y, width, height;
	public int lastX;
	public int lastY;
	private boolean dragging;
	public boolean hasSetting;

	public RenderModule(String name, Category cat, int x, int y) {
		super(name, cat);
		this.x = x;
		this.y = y;
	}

	public RenderModule(String name, Category cat, int x, int y, boolean hasSetting) {
		super(name, cat, hasSetting);
		this.x = x;
		this.y = y;
		this.hasSetting = hasSetting;
	}

	public void draw() { }

	public void Resize() {
		if ((getX() + getWidth()) > GuiScreen.width) {
			this.x = GuiScreen.width - getWidth();
			dragging = false;
		} else if ((getY() + getHeight()) > GuiScreen.height) {
			this.y = GuiScreen.height - getHeight();
			dragging = false;
		} else if ((getX()) < 0) {
			this.x = 0;
			dragging = false;
		} else if ((getY()) < 0) {
			this.y = 0;
			dragging = false;
		} else if (getX() <= 0 && getY() < 0) {
			this.y = 0;
			this.x = 0;
			dragging = false;
		} else if (getX() + getWidth() > GuiScreen.width && getY() < 0) {
			this.x = GuiScreen.width - getWidth();
			this.y = 0;
			dragging = false;
		} else if (getX() + getWidth() > GuiScreen.width && getY() + getHeight() > GuiScreen.height) {
			this.x = GuiScreen.width - getWidth();
			this.y = GuiScreen.height - getHeight();
			dragging = false;
		} else if (getY() + getHeight() > GuiScreen.height && getX() < 0) {
			this.x = GuiScreen.width - getWidth();
			this.y = GuiScreen.height - getHeight();
			dragging = false;
		}
	}

	private void draggingFix(int mouseX, int mouseY) {
		if (this.dragging && (Objects.equals(ModManager.currentModDragging, this.name) || ModManager.currentModDragging == null)) {
			this.x = mouseX + this.lastX;
			this.y = mouseY + this.lastY;
			if (ModManager.currentModDragging == null)
				ModManager.currentModDragging = this.name;
			if (!Mouse.isButtonDown(0))
				this.dragging = false;
			if (Objects.equals(ModManager.currentModDragging, this.name))
				ModManager.currentModDragging = null;
			if (this.x >= GuiScreen.width - getWidth()) {
				this.dragging = false;
				if (Objects.equals(ModManager.currentModDragging, this.name))
					ModManager.currentModDragging = null;
			}
			if (this.y >= GuiScreen.height - getHeight()) {
				this.dragging = false;
				if (Objects.equals(ModManager.currentModDragging, this.name))
					ModManager.currentModDragging = null;
			}
		}
	}

	public void renderLayout(int mouseX, int mouseY) {
		Resize();
		boolean hovered = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + getWidth()
				&& mouseY < getY() + this.getHeight();

		boolean mouseOverX = (mouseX >= this.x && mouseX <= this.x + this.getWidth());
		boolean mouseOverY = (mouseY >= this.y && mouseY <= this.y + this.getHeight());
		boolean drag = (mouseOverX && mouseOverY && Mouse.isButtonDown(0));
		draggingFix(mouseX, mouseY);

		if (drag && (ModManager.currentModDragging == null || ModManager.currentModDragging.equals(this.name))) {
			if (!this.dragging) {
				this.lastX = x - mouseX;
				this.lastY = y - mouseY;
				this.dragging = true;
				ModManager.currentModDragging = this.name;
			}
		}

		draw();

		
		Gui.drawRect(this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight(), 0x50FFFFFF);
		Gui.drawRect(this.x, this.y, this.x + this.getWidth(), this.y + 1, 0xFFFFFFFF);
		Gui.drawRect(this.x, this.y, this.x + 1, this.y + getHeight(), 0xFFFFFFFF);
		Gui.drawRect(this.x + this.getWidth() - 1, this.y, this.x + getWidth(), this.y + this.getHeight(), 0xFFFFFFFF);
		Gui.drawRect(this.x, this.y + this.getHeight() - 1, this.x + getWidth(), this.y + this.getHeight(), 0xFFFFFFFF);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}