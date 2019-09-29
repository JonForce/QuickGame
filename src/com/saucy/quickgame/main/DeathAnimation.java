package com.saucy.quickgame.main;

import processing.core.PApplet;

public class DeathAnimation {

  private PApplet applet;

  final int
    CHUNKS = 50, 
    PERCENT_BLOOD = 80;

  Player player;
  float x, y;
  Chunk[] chunks;

  DeathAnimation(PApplet applet, Player p, float x, float y) {
    this.applet = applet;
    this.player = p;
    this.x = x;
    this.y = y;
    chunks = new Chunk[CHUNKS];
    for (int i = 0; i < CHUNKS; i++) {
      chunks[i] = new Chunk(applet, x, y, applet.random(100) < PERCENT_BLOOD);
    }
  }

  void render(Camera camera) {
    for (int i = 0; i < CHUNKS; i ++)
      chunks[i].render(camera);
  }
}

