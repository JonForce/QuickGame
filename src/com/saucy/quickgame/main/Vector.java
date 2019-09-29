package com.saucy.quickgame.main;

import processing.core.PGraphics;

public class Vector {
    float x, y;
    Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }
    Vector() { }
    void render(PGraphics g) {
        g.fill(255, 255, 255);
        g.ellipse(x, y, 10, 10);
    }
}