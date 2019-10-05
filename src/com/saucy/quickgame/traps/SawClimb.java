package com.saucy.quickgame.traps;

import com.saucy.quickgame.main.Block;
import com.saucy.quickgame.main.LevelState;
import processing.core.PApplet;

public class SawClimb extends Spawner {

    private PApplet applet;

    LevelState level;
    public SawClimb(PApplet applet, LevelState level) {
        this.applet = applet;
        this.level = level;
        canStack = false;
        minWidth = 700;
    }

    @Override
    public void generate(float baseX, float baseY, float maxWidth) {
        h = 1000;
        w = 700;
        float floorHeight = 200, wallThickness = 60;

        // Vertical walls
        level.blocks.add(new Block(applet, baseX, baseY - h + 100, wallThickness, h - 250));
        level.blocks.add(new Block(applet, baseX + w - wallThickness, baseY - h, wallThickness, h));

        // Platforms
        for (int i = 0; i < h / floorHeight; i ++) {
            level.blocks.add(new Block(applet,baseX + 150*(i % 2 == 0? 1 : 0), baseY - floorHeight*i, w - 175, wallThickness));
            if (applet.random(0, 100) < 70)
                level.traps.add(new SawTrap(applet, baseX + applet.width/2 + 100*(i % 2 == 0? 1 : 0) + w/2 + 50, baseY - floorHeight*i - 8, 60));
            if (applet.random(0, 100) < 70)
                level.traps.add(new SawTrap(applet, baseX + applet.width/2 + 100*(i % 2 == 0? 1 : 0) + w/2 - 150, baseY - floorHeight*i - floorHeight + wallThickness, 60));
        }
    }
}