class LevelState extends GameState {

  /** These are the objects that compose the scene. */
  Player playerA, playerB;
  Camera camera;
  Background background;
  SawTrap trap;
  ArrayList<Block> blocks = new ArrayList<Block>();
  ArrayList<SawTrap> traps = new ArrayList<SawTrap>();
  
  // Level State data
  boolean gameOver = false;
  boolean paused = false;

  long lastAdd;
  Controller controllerA, controllerB;

  Menu activeMenu = null;

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
      background.update();

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

      for (SawTrap t : traps)
        t.update(playerA, playerB);
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
      t.render(camera);

    for (Block b : blocks)
      b.render(camera);

    
    if (playerA.isDead() && playerB.isDead() && !gameOver) {
      gameOver = true;
      activeMenu = new DeathMenu(this, controllerA);
    }

    drawFrameRate();
    
    if (activeMenu != null)
      activeMenu.render();

    if (controllerA.buttonStart.getValue() > 0 && !gameOver && !paused) {
      activeMenu = new PauseMenu(controllerA, this);
      paused = true;
    }
  }

  @Override
    void onExit() {
  }

  void spawnLevel() {
    new ProceduralLevel(this).generate();
  }

  void resume() {
    paused = false;
  }

  void closeMenu() {
    activeMenu = null;
  }

  void resetGame() {
    playerA.y = playerA.START_Y;
    playerA.x = playerA.START_X;
    playerA.death = null;
    if (!SINGLE_PLAYER) {
      playerB.x = width + 50;
      playerB.death = null;
    }
    gameOver = false;
    paused = false;
  }

  void drawFrameRate() {
    textSize(24);
    text((int) frameRate, 50, 50);
  }
}