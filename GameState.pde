abstract class GameState {
  
  /** Called when we switch to this GameState. */
  void onStart() {
    /** Override me. */
  }
  
  /** Update the GameState. */
  abstract void update();
  /** Render to the screen. */
  abstract void render();
  
  /** Called whenever we exit this GameState. */
  void onExit() {
    
  }
  
}