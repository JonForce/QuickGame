package com.saucy.quickgame.main;

public abstract class Spawner {
    QuickGame game;
    float w, h, minWidth;
    boolean canStack = false;
    abstract void generate(float baseX, float baseY, float maxWidth);
}