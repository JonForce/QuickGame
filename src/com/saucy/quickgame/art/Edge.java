package com.saucy.quickgame.art;

import com.saucy.quickgame.math.Vector;
import processing.core.PApplet;
import processing.core.PGraphics;

public class Edge {

    private PApplet applet;

    public Vector a, b;

    public Edge(PApplet applet, Vector a, Vector b) {
        this.applet = applet;
        this.a = a;
        this.b = b;
    }

    public void render(PGraphics g) {
        g.fill(0);
        g.line(a.x, a.y, b.x, b.y);
    }
}