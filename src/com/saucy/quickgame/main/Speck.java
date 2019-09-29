package com.saucy.quickgame.main;

import processing.core.PApplet;

/** This helper class represents a speck of dust in the background. */
public class Speck implements Renderable {

    private PApplet applet;

    float x, y, size, depth, frequency, amplitude;

    Speck(PApplet applet, float x, float y, float size) {
        this.applet = applet;
        this.x = x;
        this.y = y;
        this.size = size;
        frequency = applet.random(1000, 2000);
        amplitude = applet.random(5, 50);
    }

    public void render(Camera camera) {
        y -= 1;
        applet.fill(0);
        // Render the dust particle. The x position is looped around the left side of the screen.
        // Depth is multiplied against the player's x position to produce a parralax affect.
        applet.ellipse(
                applet.width - (x + camera.x * depth + applet.sin(applet.millis()/frequency)*amplitude) % applet.width,
                (applet.height - (y + camera.y))%applet.height,
                size, size);
    }
    @Override
    public float depth() {
        return depth;
    }
}