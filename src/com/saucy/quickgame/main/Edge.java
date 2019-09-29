package com.saucy.quickgame.main;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Edge {

    PApplet applet;

    Vector a, b;
    Edge(PApplet applet, Vector a, Vector b) {
        this.applet = applet;
        this.a = a;
        this.b = b;
    }
    void render(PGraphics g) {
        g.fill(0);
        g.line(a.x, a.y, b.x, b.y);
    }
}