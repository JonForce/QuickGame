package com.saucy.quickgame.main;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.core.PVector;

public class BloodAnimation {

  private PApplet applet;

  final int
    H_PADDING = 50;

  float x, y, w, h;
  float damping;
  int drips;

  PVector[] inkSpots;
  float[] inkSizes;
  PGraphics graphic;

  BloodAnimation(PApplet applet, float x, float y, float w, float h, float damping, int drips) {
    this.applet = applet;
    graphic = applet.createGraphics((int) w + H_PADDING*2, (int) h, applet.P2D);
    this.drips = drips;
    inkSpots = new PVector[drips];
    inkSizes = new float[drips];
    this.damping = damping;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    for (int i = 0; i < drips; i ++)
      createNewDrip(i);
  }

  void render() {
    simulate(1);
    for (int i = 0; i < drips; i ++) {
      applet.image(graphic, x - H_PADDING, y);
    }
  }

  void simulate(int j) {
    while (j > 0) {
      for (int i = 0; i < drips; i ++) {
        graphic.beginDraw();
        graphic.stroke(0, 255);
        graphic.strokeWeight(inkSizes[i]);

        PVector nextLocation = new PVector();
        nextLocation.x = inkSpots[i].x + applet.random(-.5f, .5f);
        nextLocation.y = inkSpots[i].y + applet.random(3);

        graphic.line(inkSpots[i].x, inkSpots[i].y, nextLocation.x, nextLocation.y);
        graphic.endDraw();

        inkSizes[i] -= applet.random(0, damping);
        inkSpots[i] = nextLocation;

        if (inkSizes[i] < 0)
          createNewDrip(i);
      }

      j --;
    }
  }

  void createNewDrip(int i) {
    inkSpots[i] = new PVector(H_PADDING + applet.random(0, w), 0);
    inkSizes[i] = applet.random(10, 20);
  }
}