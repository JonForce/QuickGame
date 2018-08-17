static ArrayList<Character> keysPressed = new ArrayList<Character>();

Player player;
Camera camera;
Background background;
SawTrap trap;
ArrayList<Block> blocks = new ArrayList<Block>();
ArrayList<SawTrap> traps = new ArrayList<SawTrap>();
long lastAdd;
Controller controllerA;

void setup() {
  //size(800, 600, P2D);
  fullScreen(P2D);
  controllerA = new Controller(this);
  player = new Player(controllerA);
  background = new Background(player);

  spawnLevel();
}

void draw() {
  for (Block b : blocks)
    b.updatePhysics();
  
  background(150);

  background.render();

  player.render();

  for (SawTrap t : traps)
    t.render();

  for (Block b : blocks)
    b.render();

  if (player.isDead()) {
    fill(255);
    rect(width/2 - 100, height /2 - 100, 400, 200);
    fill(0);
    textSize(45);
    text("You died.", width/2, height/2);
    textSize(30);
    text("[Press Space To Restart]", width/2 - 75, height/2 + 50);

    if ((keysPressed.contains(' ') || controllerA.buttonA.pressed()) && millis() - player.deathTime > 1000)
      resetGame();
  }

  textSize(24);
  text((int) frameRate, 50, 50);

  if (mousePressed && millis() - lastAdd > 1000) {
    lastAdd = millis();
    println("traps.add(new SawTrap(player, "+(player.x+mouseX)+", " + mouseY + "));");
    traps.add(new SawTrap(player, (player.x+mouseX), mouseY));
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
  blocks.add(new Block(player, 0, height - 30, 10000, 31));

  // Set up environment.
  for (int i = 0; i < 7; i ++)
    traps.add(new SawTrap(player, width * 2 + i*50, height - 30));
  blocks.add(new Block(player, width * 2 + 400, height - 30 - 200, 700, 50));
  blocks.add(new Block(player, width * 2 + 50, height - 30 - 200, 100, 200));
  blocks.add(new Block(player, width * 2 + 1500, height - 30 - 200, 700, 50));
  blocks.add(new Block(player, 5840, 540, 100, 200));
  blocks.add(new Block(player, width * 2 + 500, 540, 1550, 50));
  // Lower up and down hallway.
  traps.add(new SawTrap(player, 5250.998, 923));
  traps.add(new SawTrap(player, 5450.998, 1050));
  traps.add(new SawTrap(player, 5650.998, 923));
  traps.add(new SawTrap(player, 5000.998, 1050));
  // Splitter
  traps.add(new SawTrap(player, 5936.1865, 877));
  traps.add(new SawTrap(player, 6027.1865, 880));
  traps.add(new SawTrap(player, 6134.1865, 875));
  traps.add(new SawTrap(player, 6226.1865, 876));
}

void resetGame() {
  player.x = width;
  player.death = null;
}