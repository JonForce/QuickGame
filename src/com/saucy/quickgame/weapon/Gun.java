package com.saucy.quickgame.weapon;

import com.saucy.quickgame.main.Camera;
import com.saucy.quickgame.main.Controller;
import com.saucy.quickgame.main.Player;

public abstract class Gun {

  protected Player player;
  public Controller controller;

  public Gun(Player p, Controller c) {
    player = p;
    controller = c;
  }
  
  public abstract void update();
  public abstract void render(Camera camera);
}

