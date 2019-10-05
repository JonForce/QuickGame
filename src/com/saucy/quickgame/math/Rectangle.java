package com.saucy.quickgame.math;

import com.saucy.quickgame.main.Camera;
import processing.core.PApplet;

/** This class represents a simple rectangle in the game world. It is a tool used by block
 for collision detection. */
public class Rectangle {

    private PApplet applet;

    public float x, y, w, h;

    public Rectangle(PApplet applet, float x, float y, float w, float h) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

	/** Return the intercept between this rectangle and the other.
	 * This method returns a rect with x=0 y=0 w=0 h=0 if there's no intercept.
	 * @param other The other rectangle to intersect with this.
	 */
	public Rectangle intercept(Rectangle other) {
		java.awt.Rectangle a = new java.awt.Rectangle((int) x, (int) y, (int) w, (int) h);
		java.awt.Rectangle b = new java.awt.Rectangle((int) other.x, (int) other.y, (int) other.w, (int) other.h);
		java.awt.Rectangle inter = a.intersection(b);
		Rectangle result = new Rectangle(applet,inter.x, inter.y, inter.width, inter.height);
		if (result.w <= 0 || result.h <= 0)
			result.w = result.h = result.x = result.y = 0;
		return result;
    }

    public boolean contains(float x, float y) {
        return x >= this.x && x <= this.x + this.w && y >= this.y && y <= this.y + this.h;
    }

    public void render(Camera camera) {
		applet.fill(255, 0, 0);
        applet.rect(x - camera.x + applet.width/2, y - camera.y, w, h);
    }
}