package com.saucy.quickgame.traps;

import com.saucy.quickgame.main.QuickGame;

public abstract class Spawner {

    private QuickGame game;

    public float w, h, minWidth;

    public boolean canStack = false;

    public abstract void generate(float baseX, float baseY, float maxWidth);
}