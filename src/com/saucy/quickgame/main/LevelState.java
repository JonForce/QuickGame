package com.saucy.quickgame.main;

import com.saucy.quickgame.main.*;
import processing.core.PApplet;

import java.util.ArrayList;

public class LevelState extends GameState {

  /** These are the objects that compose the scene. */
  Player playerA, playerB;
  Camera camera;
  Background background;
  SawTrap trap;
  public ArrayList<Block> blocks = new ArrayList<Block>();
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
    camera = new Camera(game);
    playerA = new Player(game, this, controllerA);
    playerB = new Player(game, this, controllerB);
    playerB.x += 50;
    background = new Background(game, game.settings);

    // Generate the level.
    spawnLevel();

    // Single player
    if (game.SINGLE_PLAYER)
      playerB.die();
  }

  @Override
  public void update() {
    if (!paused) {
      background.update();

      camera.update(playerA, playerB);
      for (Block b : blocks)
        b.updatePhysics(playerA, playerB);

      // This code is for dubugging purposes. It allows you to click to spawn things.
      //if (mousePressed && millis() - lastAdd > 1000) {
      //  lastAdd = millis();
      //  println("traps.add(new SawTrap(player, "+(playerA.x+mouseX)+", " + mouseY + "));");
      //  //traps.add(new SawTrap((playerA.x+mouseX), mouseY));
      //  blocks.add(new Block((playerA.x+mouseX)-width/2, mouseY, 50, 50));
      //}
      playerA.update();
      if (!game.SINGLE_PLAYER)
        playerB.update();

      for (SawTrap t : traps)
        t.update(playerA, playerB);
    }
  }

  @Override
  public void render() {
    game.background(150);

    background.render(camera);

    playerA.render(camera);
    if (!game.SINGLE_PLAYER)
      playerB.render(camera);

    for (SawTrap t : traps)
      t.render(camera);

    for (Block b : blocks)
      b.render(camera);

    
    if (playerA.isDead() && playerB.isDead() && !gameOver) {
      gameOver = true;
      activeMenu = new DeathMenu(game,this, controllerA);
    }

    drawFrameRate();
    
    if (activeMenu != null)
      activeMenu.render();

    if (controllerA.buttonStart.getValue() > 0 && !gameOver && !paused) {
      activeMenu = new PauseMenu(game, controllerA, this);
      paused = true;
    }
  }

  @Override
    void onExit() {
  }

  void spawnLevel() {
    new ProceduralLevel(game,this).generate();
  }

  void resume() {
    paused = false;
  }

  public void closeMenu() {
    activeMenu = null;
  }
  
  public void returnToMainMenu() {
    game.switchToState(new MainMenuState(game));
  }

  public void resetGame() {
    playerA.y = playerA.START_Y;
    playerA.x = playerA.START_X;
    playerA.death = null;
    if (!game.SINGLE_PLAYER) {
      playerB.x = game.width + 50;
      playerB.death = null;
    }
    gameOver = false;
    paused = false;
  }

  void drawFrameRate() {
    game.textSize(24);
    game.text((int) game.frameRate, 50, 50);
  }
}