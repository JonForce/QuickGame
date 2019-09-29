package com.saucy.quickgame.main;

import processing.core.PApplet;

public class MainMenuState extends GameState {

  private QuickGame game;

  Menu menu;
  
  public MainMenuState(QuickGame game) {
    this.game = game;
    menu = new MainMenu(game.controllerA, game, this);
  }
  
  @Override
  public void update() {
     
  }
  
  @Override
  public void render() {
    game.background(255);
    
    menu.render();
  }
  
}

