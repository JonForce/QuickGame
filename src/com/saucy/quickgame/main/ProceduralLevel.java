package com.saucy.quickgame.main;

import com.saucy.quickgame.traps.*;
import processing.core.PApplet;

import java.util.ArrayList;

public class ProceduralLevel {

  final float
    LENGTH = 20000, 
    BUFFER = 400;

  private PApplet applet;

  ArrayList<Spawner> spawners = new ArrayList<Spawner>();
  LevelState level;

  public ProceduralLevel(PApplet applet, LevelState level) {
    this.applet = applet;
    this.level = level;
    spawners.add(new SawHallway(applet, level));
    spawners.add(new SawRow(applet, level));
    spawners.add(new SawPit(applet, level));
    spawners.add(new SawClimb(applet, level));
  }

  void generate() {
    ArrayList<Rect> spawnPoints = new ArrayList<Rect>();
    // Build the ground.
    level.blocks.add(new Block(applet, 0, applet.height - 30, LENGTH, 31));

    float x = level.playerA.x + BUFFER*2;

    while (x < LENGTH) {
      Spawner s = spawners.get((int) applet.random(0, spawners.size()));
      s.generate(x, applet.height, 2500);
      float sx = x;
      x += s.w;
      if (s.canStack) {
        spawnPoints.add(new Rect(sx, applet.height - s.h, s.w, 0));
      }

      x += applet.random(BUFFER, BUFFER * 2);
    }

    while (spawnPoints.size() > 0) {
      Rect point = spawnPoints.get(0);
      Spawner s = getSpawnerForSize(point);
      if (s == null) {
        spawnPoints.remove(0);
        continue;
      }

      //float spawnOffset = random(0, point.w/2);
      s.generate(point.x/* + spawnOffset*/, point.y, point.w/* - spawnOffset*/);
      if (s.canStack) {
        spawnPoints.add(new Rect(point.x/* + spawnOffset*/, point.y - s.h, s.w/* - spawnOffset*/, 0));
      }
      spawnPoints.add(new Rect(point.x + s.w + BUFFER/2, point.y, point.w - (s.w + BUFFER/2), 0));
      spawnPoints.remove(0);
      x += applet.random(BUFFER, BUFFER * 2);
    }
  }

  Spawner getSpawnerForSize(Rect point) {
    Spawner s = spawners.get((int) applet.random(0, spawners.size()));
    int tries = 0;
    while (point.w < s.minWidth) {
      s = spawners.get((int) applet.random(0, spawners.size()));

      if (tries++ > spawners.size() * 2)
        return null;
    }
    return s;
  }
}









