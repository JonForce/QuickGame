package com.saucy.quickgame.main;

import processing.core.PApplet;

public class SawTrap {

  private PApplet applet;

  float
    SPIKE_WIDTH = 70, 
    SPIKE_HEIGHT = 70;
  final int
    SPIKES = 5;

  float r;
  float[] speeds;
  Vector startPoint = new Vector(), endPoint;

  public SawTrap(PApplet applet, float x, float y, float size) {
    this.applet = applet;
    this.SPIKE_WIDTH = this.SPIKE_HEIGHT = size;
    startPoint.x = x;
    startPoint.y = y;
    speeds = new float[SPIKES];
    for (int i = 0; i < SPIKES; i ++)
      speeds[i] = applet.random(1) < .5? applet.random(-5, -8) : applet.random(5, 8);
  }
  
  SawTrap(PApplet applet, float x, float y) {
    this(applet, x, y, 79);
  }
  
  public void update(Player ... players) {
    r += .05;
    
    for (Player p : players) {
      if (applet.dist(p.x + p.size/2 + applet.width/2, p.y + p.size/2, startPoint.x, startPoint.y) < SPIKE_HEIGHT && !p.isDead())
        p.die();
    }
  }

  public void render(Camera camera) {
    applet.fill(0, 0, 0);
    float x = startPoint.x;
    float y = startPoint.y;
    
    if (x - camera.x + SPIKE_HEIGHT > 0 && x - camera.x - SPIKE_HEIGHT < applet.width) {
      for (int i = 0; i < SPIKES; i ++) {
        applet.pushMatrix();
        applet.translate(x - camera.x, y - camera.y);
        applet.rotate(r * speeds[i]);
        applet.triangle(
          -SPIKE_WIDTH/2, -SPIKE_HEIGHT/2, 
          0, SPIKE_HEIGHT/2, 
          SPIKE_WIDTH/2, -SPIKE_HEIGHT/2);
        applet.popMatrix();
      }
    }
  }
}