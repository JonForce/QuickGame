package com.saucy.quickgame.main;

import processing.core.*;

import java.util.ArrayList;

public class QuickGame extends PApplet {

  public static final boolean
          SINGLE_PLAYER = true;

  static enum Mode {
    LEVEL_EDITOR,
    LOAD_LEVEL,
    PROCEDURAL_LEVEL
  }

  static final Mode
          MODE = Mode.LOAD_LEVEL;

  /**
   * This is a running list of the keys that are currently pressed represented by their Character.
   */
  static ArrayList<Character> keysPressed = new ArrayList<Character>();

  public GameState state;

  public com.saucy.quickgame.main.Controller controllerA;
  public com.saucy.quickgame.main.Controller controllerB;

  public GraphicsSettings settings = new GraphicsSettings(this);
  public SavedData savedData = new SavedData(this);

  public static void main(String[] args) {
    PApplet.main("com.saucy.quickgame.main.QuickGame");
  }

  public void settings() {
    fullScreen(P2D);
  }

  public void setup() {
    //size(800, 600, P2D);

    // First, initialize the controllers.
    controllerA = new Controller(this);
    if (!SINGLE_PLAYER)
      controllerB = new Controller(this);

    settings.load();
    savedData.load();

    switchToState(new TitleScreen(this));
  }

  public void draw() {
    state.update();
    state.render();
  }

  /**
   * This will shift the game into the newState. It will call all the appropriate
   * events on both the new and old art.
   */
  public void switchToState(GameState newState) {
    if (state != null)
      state.onExit();

    state = newState;
    newState.onStart();
  }

  public void keyPressed() {
    if (!keysPressed.contains(key))
      keysPressed.add(key);
  }

  public void keyReleased() {
    keysPressed.remove(new Character(key));
  }
}