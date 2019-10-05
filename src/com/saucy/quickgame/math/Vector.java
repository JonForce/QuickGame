package com.saucy.quickgame.math;

import processing.core.PGraphics;

public class Vector {
    public float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector() { }

    public void render(PGraphics g) {
        g.fill(255, 255, 255);
        g.ellipse(x, y, 10, 10);
    }
}