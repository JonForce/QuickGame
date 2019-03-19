class LevelState extends GameState {

  /** These are the objects that compose the scene. */
  Player playerA, playerB;
  Camera camera;
  Background background;
  SawTrap trap;
  ArrayList<Block> blocks = new ArrayList<Block>();
  ArrayList<SawTrap> traps = new ArrayList<SawTrap>();
  boolean paused = false;

  long lastAdd;
  Controller controllerA, controllerB;

  QuickGame game;

  LevelState(QuickGame game) {
    this.game = game;
    this.controllerA = game.controllerA;
    this.controllerB = game.controllerB;
  }

  @Override
    void onStart() {
    // Create the camera and the players.
    camera = new Camera();
    playerA = new Player(this, controllerA);
    playerB = new Player(this, controllerB);
    playerB.x += 50;
    background = new Background();

    // Generate the level.
    spawnLevel();

    // Single player
    if (SINGLE_PLAYER)
      playerB.die();
  }

  @Override
  void update() {
    if (!paused) {
      camera.update(playerA, playerB);
      for (Block b : blocks)
        b.updatePhysics(playerA, playerB);

      // This code is for dubugging purposes. It allows you to click to spawn things.
      if (mousePressed && millis() - lastAdd > 1000) {
        lastAdd = millis();
        println("traps.add(new SawTrap(player, "+(playerA.x+mouseX)+", " + mouseY + "));");
        //traps.add(new SawTrap((playerA.x+mouseX), mouseY));
        blocks.add(new Block((playerA.x+mouseX)-width/2, mouseY, 50, 50));
      }
      playerA.update();
      if (!SINGLE_PLAYER)
        playerB.update();
    }
  }

  @Override
    void render() {

    background(150);

    background.render(camera);

    playerA.render(camera);
    if (!SINGLE_PLAYER)
      playerB.render(camera);

    for (SawTrap t : traps)
      t.render(camera, playerA, playerB);

    for (Block b : blocks)
      b.render(camera);

    if (playerA.isDead() && playerB.isDead())
      renderDeathMenu();

    drawFrameRate();

    /*
    camera.update(playerA, playerB);
     
     for (Block b : blocks)
     b.updatePhysics(playerA, playerB);
     
     background(150);
     
     background.render(camera);
     
     playerA.render(camera);
     if (!SINGLE_PLAYER)
     playerB.render(camera);
     
     for (SawTrap t : traps)
     t.render(camera, playerA, playerB);
     
     for (Block b : blocks)
     b.render(camera);
     
     if (playerA.isDead() && playerB.isDead())
     renderDeathMenu();
     
     drawFrameRate();
     
     // This code is for dubugging purposes. It allows you to click to spawn things.
     if (mousePressed && millis() - lastAdd > 1000) {
     lastAdd = millis();
     println("traps.add(new SawTrap(player, "+(playerA.x+mouseX)+", " + mouseY + "));");
     //traps.add(new SawTrap((playerA.x+mouseX), mouseY));
     blocks.add(new Block((playerA.x+mouseX)-width/2, mouseY, 50, 50));
     }
     */
    if (controllerA.buttonStart.getValue() > 0) {
      paused = !paused;
    }
  }

  @Override
    void onExit() {
  }

  void spawnLevel() {
    new ProceduralLevel(this).generate();
  }

  void resetGame() {
    playerA.x = width;
    playerA.death = null;
    if (!SINGLE_PLAYER) {
      playerB.x = width + 50;
      playerB.death = null;
    }
  }

  void renderDeathMenu() {
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

  void drawFrameRate() {
    textSize(24);
    text((int) frameRate, 50, 50);
  }
}