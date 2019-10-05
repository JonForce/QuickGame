package com.saucy.quickgame.art;

import com.saucy.quickgame.main.Camera;
import processing.core.PApplet;

public class Chunk {

    private PApplet applet;

    final float
            GRAVITY,
            BLOOD_SIZE = 5,
            CHUNK_SIZE = 15,
            HORIZONTAL_DAMPING = .975f;

    boolean blood;
    float
            x, y,
            speedX,
            speedY;

    public Chunk(PApplet applet, float x, float y, boolean blood) {
        this.applet = applet;
        this.GRAVITY = applet.random(.3f, 1f);
        this.x = x;
        this.y = y;
        this.speedX = applet.random(-4f, 4f);
        this.speedY = applet.random(-6f, 2f);
        this.blood = blood;
    }

    public void render(Camera camera) {
        // Physics
        x += speedX;
        y += speedY;
        speedY += GRAVITY;

        if (y > applet.height - 30 - (blood? BLOOD_SIZE : CHUNK_SIZE))
            y = applet.height - 30 - (blood? BLOOD_SIZE : CHUNK_SIZE);
        speedX = speedX * HORIZONTAL_DAMPING;

        // Rendering
        if (blood) {
            applet.fill(255, 0, 0);
            applet.rect(x - camera.x + applet.width/2, y - camera.y, BLOOD_SIZE, BLOOD_SIZE);
        } else {
            applet.fill(255, 255, 255);
            applet.rect(x - camera.x + applet.width/2, y - camera.y, CHUNK_SIZE, CHUNK_SIZE);
        }
    }
}