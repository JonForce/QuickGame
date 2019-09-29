package com.saucy.quickgame.main;

public abstract class GameState {
  
  /** Called when we switch to this GameState. */
  void onStart() {
    /** Override me. */
  }
  
  /** Update the GameState. */
  public abstract void update();
  /** Render to the screen. */
  public abstract void render();
  
  /** Called whenever we exit this GameState. */
  void onExit() {
    
  }
  
}