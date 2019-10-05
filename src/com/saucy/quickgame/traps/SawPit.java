package com.saucy.quickgame.traps;

import com.saucy.quickgame.main.Block;
import com.saucy.quickgame.main.LevelState;
import processing.core.PApplet;

public class SawPit extends Spawner {

    final float SPIKE_SIZE = 70;

    private PApplet applet;

    LevelState level;

    public SawPit(PApplet applet, LevelState level) {
        this.applet = applet;
        this.level = level;
        canStack = false;
        minWidth = 1400;
    }

    @Override
    public void generate(float baseX, float baseY, float maxWidth) {
        h = 300;
        w = applet.min(applet.random(1000, 1500), maxWidth);

        // Starting block.
        level.blocks.add(new Block(applet, baseX, baseY - 250, w / 4, 250));
        // Ending block
        level.blocks.add(new Block(applet,baseX + w - w/4, baseY - 100, w / 4, 100));

        // Spawn the saws in the pit.
        float sawsLength = w - w/4;
        int toSpawn = (int) (sawsLength/SPIKE_SIZE), spawned = 0;
        float x = baseX + 250;
        while (spawned < toSpawn) {
            level.traps.add(new SawTrap(applet,x + applet.width/2, baseY - SPIKE_SIZE/2, SPIKE_SIZE));
            spawned ++;
            x += SPIKE_SIZE;
        }
    }
}