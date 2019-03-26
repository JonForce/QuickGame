static final boolean
   SINGLE_PLAYER = true;

/** This is a running list of the keys that are currently pressed represented by their Character. */
static ArrayList<Character> keysPressed = new ArrayList<Character>();

GameState state;

Controller controllerA, controllerB;

void setup() {
  fullScreen(P2D);
  //size(800, 600, P2D);

  // First, initialize the controllers.
  controllerA = new Controller(this);
  if (!SINGLE_PLAYER)
    controllerB = new Controller(this);
  
  switchToState(new LevelState(this));
}

void draw() {
  state.update();
  state.render();
}

/** This will shift the game into the newState. It will call all the appropriate
      events on both the new and old states. */
void switchToState(GameState newState) {
  if (state != null)
    state.onExit();
  
  state = newState;
  newState.onStart();
}

void keyPressed() {
  if (!keysPressed.contains(key))
    keysPressed.add(key);
}

void keyReleased() {
  keysPressed.remove(new Character(key));
}