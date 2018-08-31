static final boolean SINGLE_PLAYER = true;

static ArrayList<Character> keysPressed = new ArrayList<Character>();

Player playerA, playerB;
Camera camera;
Background background;
SawTrap trap;
ArrayList<Block> blocks = new ArrayList<Block>();
ArrayList<SawTrap> traps = new ArrayList<SawTrap>();
long lastAdd;
Controller controllerA, controllerB;

void setup() {
  //size(800, 600, P2D);
  fullScreen(P2D);
  controllerA = new Controller(this, "Controller (Afterglow Gamepad for Xbox 360)");
  if (!SINGLE_PLAYER)
    controllerB = new Controller(this, "Controller (Rock Candy Gamepad for Xbox 360)");
  camera = new Camera();
  playerA = new Player(controllerA);
  playerB = new Player(controllerB);
  playerB.x += 50;
  background = new Background();

  spawnLevel();

  // Single player
  if (SINGLE_PLAYER)
    playerB.die();
}

void draw() {
  camera.update(playerA, playerB);

  for (Block b : blocks)
    b.updatePhysics(playerA, playerB);

  background(150);

  background.render();

  playerA.render();
  if (!SINGLE_PLAYER)
    playerB.render();

  for (SawTrap t : traps)
    t.render(playerA, playerB);

  for (Block b : blocks)
    b.render();

  if (playerA.isDead() && playerB.isDead()) {
    fill(255);
    rect(width/2 - 100, height /2 - 100, 400, 200);
    fill(0);
    textSize(45);
    text("You died.", width/2, height/2);
    textSize(30);
    text("[Press Jump To Restart]", width/2 - 75, height/2 + 50);

    if ((keysPressed.contains(' ') || controllerA.buttonA.pressed()) && millis() - playerA.deathTime > 1000)
      resetGame();
  }

  textSize(24);
  text((int) frameRate, 50, 50);

  if (mousePressed && millis() - lastAdd > 1000) {
    lastAdd = millis();
    println("traps.add(new SawTrap(player, "+(playerA.x+mouseX)+", " + mouseY + "));");
    //traps.add(new SawTrap((playerA.x+mouseX), mouseY));
    blocks.add(new Block((playerA.x+mouseX)-width/2, mouseY, 50, 50));
  }
}

void keyPressed() {
  if (!keysPressed.contains(key))
    keysPressed.add(key);
}

void keyReleased() {
  keysPressed.remove(new Character(key));
}

void spawnLevel() {
  new ProceduralLevel(this).generate();
  /*
  blocks.add(new Block(0, height - 30, 10000, 31));
   
   // Set up environment.
   for (int i = 0; i < 7; i ++)
   traps.add(new SawTrap(width * 2 + i*50, height - 30));
   blocks.add(new Block(width * 2 + 400, height - 30 - 200, 700, 50));
   blocks.add(new Block(width * 2 + 50, height - 30 - 200, 100, 200));
   blocks.add(new Block(width * 2 + 1500, height - 30 - 200, 700, 50));
   blocks.add(new Block(5840, 540, 100, 200));
   blocks.add(new Block(width * 2 + 500, 540, 1550, 50));
   // Lower up and down hallway.
   traps.add(new SawTrap(5250.998, 923));
   traps.add(new SawTrap(5450.998, 1050));
   traps.add(new SawTrap(5650.998, 923));
   traps.add(new SawTrap(5000.998, 1050));
   // Splitter
   traps.add(new SawTrap(5936.1865, 877));
   traps.add(new SawTrap(6027.1865, 880));
   traps.add(new SawTrap(6134.1865, 875));
   traps.add(new SawTrap(6226.1865, 876));
   */
}

void resetGame() {
  playerA.x = width;
  playerA.death = null;
  if (!SINGLE_PLAYER) {
    playerB.x = width + 50;
    playerB.death = null;
  }
}