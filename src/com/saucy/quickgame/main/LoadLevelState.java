package com.saucy.quickgame.main;

public class LoadLevelState extends GameState {
  
  QuickGame game;
  LoadLevelMenu menu;
  
  LoadLevelState(QuickGame game) {
    this.game = game;
    this.menu = new LoadLevelMenu(game);
  }
  
  @Override
  public void update() {
    //menu.updateSelection();
  }
  
  @Override
  public void render() {
    game.background(255);
    menu.render();
  }
}

