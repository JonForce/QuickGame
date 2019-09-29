package com.saucy.quickgame.main;

import processing.core.PApplet;

import java.util.ArrayList;

/** This class renders all of the stars in the background. */
public class Stars implements Renderable {

    private PApplet applet;

    ArrayList<Star> stars = new ArrayList<Star>();
    /** Define the maximum distance between two stars in order to form a connection. */
    final float CONNECT_DIST = 100;
    /** Define the numbers of total stars in the sky. They are randomly distributed. */
    int starCount;

    Stars(PApplet applet, int starCount) {
        this.applet = applet;
        this.starCount = starCount;
        // Generate the stars into the stars list.
        for (int i = 0; i < starCount; i ++) {
            Star s = new Star();
            s.x = applet.random(-CONNECT_DIST, applet.width);
            s.y = applet.random(-CONNECT_DIST, applet.height/2);
            s.depth = applet.random(.1f, .5f);
            stars.add(s);
        }
    }

    void update() {
        for (Star s : stars) {
            s.x = (s.x + s.depth);
        }
    }

    public void render(Camera camera) {
        // For every star in the list,
        for (Star s : stars) {
            // Loop over every star again, to try to find other stars to connect to.
            for (Star x : stars) {
                // If they're close enough,
                if (applet.dist(s.x, s.y, x.x, x.y) < CONNECT_DIST) {
                    // Connect the two stars with a line.
                    applet.stroke(255, 255, 255);
                    applet.line(s.x, s.y, x.x, x.y);
                }
            }

            // Next draw our current star.
            applet.fill(255);
            applet.ellipse(s.x, s.y, 3, 3);
            applet.stroke(0);

            // Loop back to the left once the star has moved off the right of the screen.
            if (s.x > applet.width + CONNECT_DIST)
                s.x = -CONNECT_DIST;
        }
    }

    @Override
    public float depth() {
        return 0;
    }
}

class Star {
    float x, y, depth;
}