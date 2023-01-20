package dev.resent.module.base;

import net.lax1dude.eaglercraft.v1_8.Mouse;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

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

    public void draw() {}

    public void resize() {
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
        }
    }

    private void draggingFix(int mouseX, int mouseY) {
        if (this.dragging) {
            this.x = mouseX + this.lastX;
            this.y = mouseY + this.lastY;
            if (!Mouse.isButtonDown(0)) this.dragging = false;
        }
    }

    public void renderLayout(int mouseX, int mouseY) {
        resize();
        draw();
        draggingFix(mouseX, mouseY);

        boolean hovered = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + getWidth() && mouseY < getY() + this.getHeight();

        Gui.drawRect(this.x, this.y, this.x + this.getWidth(), this.y + this.getHeight(), hovered ? 0x50FFFFFF : 0x40FFFFFF);
        Gui.drawRect(this.x, this.y, this.x + this.getWidth(), this.y + 1, -1);
        Gui.drawRect(this.x, this.y, this.x + 1, this.y + getHeight(), -1);
        Gui.drawRect(this.x + this.getWidth() - 1, this.y, this.x + getWidth(), this.y + this.getHeight(), -1);
        Gui.drawRect(this.x, this.y + this.getHeight() - 1, this.x + getWidth(), this.y + this.getHeight(), -1);

        boolean mouseOverX = (mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth());
        boolean mouseOverY = (mouseY >= this.getY() && mouseY <= this.getY() + this.getHeight());
        if (mouseOverX && mouseOverY) {
            if (Mouse.isButtonDown(0)) {
                if (!this.dragging) {
                    this.lastX = x - mouseX;
                    this.lastY = y - mouseY;
                    this.dragging = true;
                }
            }
        }
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
