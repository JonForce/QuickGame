package com.saucy.quickgame.main;

import processing.core.PApplet;

public class Camera {

  private PApplet applet;

  final float FOLLOW_SPEED = .2f;
  public float x, y;

  public Camera(PApplet applet) {
    this.applet = applet;
  }

  public void update(Player ... players) {
    float xAvg = 0, yAvg = 0, count = 0;
    for (Player p : players) {
      if (!p.isDead()) {
        xAvg += p.x;
        yAvg += p.y;
        count ++;
      }
    }
    xAvg /= count;
    yAvg /= count;
    if (count != 0) {
      x = x + (xAvg - x) * FOLLOW_SPEED;
      y = y + ((yAvg - applet.height*.5f) - y) * FOLLOW_SPEED;
    }

    if (y > 0) y = 0;
  }
}