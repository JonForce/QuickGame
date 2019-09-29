package com.saucy.quickgame.main;

import processing.core.PApplet;

public class SawHallway extends Spawner {

    private PApplet applet;

    LevelState level;
    SawHallway(PApplet applet, LevelState level) {
        this.applet = applet;
        this.level = level;
        canStack = true;
        minWidth = 300;
    }

    @Override
    void generate(float baseX, float baseY, float maxWidth) {
        int sawSeperation = (int) applet.random(200, 350), saws = (int) applet.random(1, (int) (maxWidth / sawSeperation));
        h = 200;
        w = saws * sawSeperation;
        level.blocks.add(new Block(applet, baseX, baseY - h, w, 40));
        level.blocks.add(new Block(applet,baseX - 40, baseY - h - 40, w + 80, 40));
        for (int i = 0; i <= saws; i++) {
            if (i % 2 == 0) {
                level.traps.add(new SawTrap(applet, baseX + applet.width/2 + i*sawSeperation, baseY - h + 40));
            } else {
                level.traps.add(new SawTrap(applet, baseX + applet.width/2 + i*sawSeperation, baseY - 30));
            }
        }
        //h += 40;
    }
}