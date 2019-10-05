package com.saucy.quickgame.weapon;

import com.saucy.quickgame.main.Camera;
import com.saucy.quickgame.main.Controller;
import com.saucy.quickgame.main.Player;
import processing.core.PApplet;
import processing.core.PImage;

public class Pistol extends Gun {

    PApplet applet;

    PImage sprite, flipped;
    float aimX, aimY;

    public Pistol(PApplet applet, Player p, Controller c) {
        super(p, c);
        this.applet = applet;
        sprite = applet.loadImage("Pistol.png");
        flipped = applet.loadImage("PistolFlipped.png");
    }

    @Override
    public void update() {
        aimX = controller.sliderBX.getValue();
        aimY = controller.sliderBY.getValue();
    }

    @Override
    public void render(Camera camera) {
        applet.pushMatrix();

        applet.translate(
                player.x - camera.x + applet.width/2 + aimX * 10,
                player.y - camera.y + aimY * 10);

        applet.scale(.125f, .125f);

        PImage image;

        if (aimX >= 0) {
            applet.rotate(applet.atan(aimY / aimX));
            image = sprite;
        } else {
            image = flipped;
            applet.rotate(applet.atan(aimY / aimX));
            applet.translate(-image.width, 0);
        }

        // Gun
        applet.image(image, image.width / 2 - (aimX < 0? 180 : 0), 0);
        // Hand
        applet.fill(255);
        applet.rect(image.width / 2, 120, 90, 90);

        applet.popMatrix();
    }
}