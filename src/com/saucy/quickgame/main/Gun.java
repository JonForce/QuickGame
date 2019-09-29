package com.saucy.quickgame.main;

public abstract class Gun {

  Player player;
  Controller controller;

  Gun(Player p, Controller c) {
    player = p;
    controller = c;
  }
  
  abstract void update();
  abstract void render(Camera camera);
}

