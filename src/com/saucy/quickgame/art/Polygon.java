package com.saucy.quickgame.art;

import com.saucy.quickgame.math.Vector;
import processing.core.PApplet;
import processing.core.PGraphics;

public class Polygon {

    public Vector[] vectors;

    public Polygon(Vector ... vectors) {
        this.vectors = vectors;
    }

    public void render(PGraphics g) {
        g.fill(75);
        g.beginShape();
        for (Vector v : vectors)
            g.vertex(v.x, v.y);
        g.endShape(PApplet.CLOSE);
        for (Vector v : vectors) {
            g.fill(255, 255, 255);
            g.ellipse(v.x, v.y, 10, 10);
        }
    }
}