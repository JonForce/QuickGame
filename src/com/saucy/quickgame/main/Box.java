package com.saucy.quickgame.main;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Box {

    private PApplet applet;

    Vector a, b, c, d;
    Box(PApplet applet, Vector a, Vector b, Vector c, Vector d) {
        this.applet = applet;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    void render(PGraphics g) {
        applet.fill(200);
        g.stroke(150);
        g.triangle(a.x, a.y, b.x, b.y, c.x, c.y);
        g.triangle(d.x, d.y, b.x, b.y, c.x, c.y);
        g.stroke(0);
    }
}