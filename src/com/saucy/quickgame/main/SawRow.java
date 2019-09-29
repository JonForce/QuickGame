package com.saucy.quickgame.main;

import processing.core.PApplet;

public class SawRow extends Spawner {
    final float SPIKE_WIDTH = 70;

    private PApplet applet;

    LevelState level;
    SawRow(PApplet applet, LevelState level) {
        this.applet = applet;
        this.level = level;
        canStack = false;
        minWidth = SPIKE_WIDTH*2;
    }

    @Override
    void generate(float baseX, float baseY, float maxWidth) {
        h = 300;
        w = applet.random(SPIKE_WIDTH, applet.min(600, maxWidth));

        int toSpawn = (int) (w/SPIKE_WIDTH), spawned = 0;
        float x = baseX + w/2;
        while (spawned < toSpawn) {
            level.traps.add(new SawTrap(applet,x + applet.width/2, baseY - SPIKE_WIDTH/2, SPIKE_WIDTH));
            spawned ++;
            x += SPIKE_WIDTH;
        }
    }
}