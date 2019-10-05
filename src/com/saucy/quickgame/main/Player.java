package com.saucy.quickgame.main;

import com.saucy.quickgame.art.DeathAnimation;
import com.saucy.quickgame.weapon.Gun;
import com.saucy.quickgame.weapon.Pistol;
import processing.core.PApplet;

public class Player {

  PApplet applet;

  public final float
    // This affects the player's sliding distance.
    HORIZONTAL_DAMPING = .75f, 
    // The gravity of the map in pixels per second squared.
    GRAVITY = .5f, 
    // The amount to set the vertical velocity to when jumping.
    JUMP_VELOCITY = 15, 
    AIR_CONTROL = .2f, 
    RUNNING_MULTIPLIER = 1.75f, 
    PUSH_OFF_WALL_VELOCITY = 8, 
    START_X, 
    START_Y;
  final long
    FELL_OFF_WALL_STILL_WALL_JUMP_TIME = 100;

  public float
    x, y, speedX, speedY, 
    size = 30, speed = 3;
  public DeathAnimation death;
  long deathTime, lastWallTouchTime;
  boolean lastWallTouchDirection;
  Controller controller;
  Gun gun;
  LevelState level;
  boolean invincible = false;

  public Player(PApplet applet, LevelState level, Controller controller) {
    this.applet = applet;
    this.level = level;
    y = applet.height - 30 - size;
    x = applet.width;
    START_X = x;
    START_Y = y;

    this.controller = controller;
    gun = new Pistol(applet, this, controller);
  }

  public void update() {
    updateControls();
    updatePhysics();
  }

  public void render(Camera camera) {
    if (isDead())
      death.render(camera);
    else {
      drawPlayer(camera);
    }
    gun.render(camera);
  }

  public void die() {
    if (!invincible) {
      death = new DeathAnimation(applet, this, x, y);
      deathTime = applet.millis();
    }
  }

  void updateControls() {
    gun.update();

    float speedMultiplier = controller.leftTrigger.getValue() > .2f? RUNNING_MULTIPLIER : 1;
    if (!collidingDown())
      speedMultiplier = AIR_CONTROL;
    speedX += speed * controller.sliderAX.getValue() * speedMultiplier;

    boolean wallJump =
      (applet.millis() - lastWallTouchTime < FELL_OFF_WALL_STILL_WALL_JUMP_TIME);

    if (controller.buttonA.pressed() && (collidingDown() || wallJump)) {
      speedY = -JUMP_VELOCITY;
      if (wallJump) {
        if (lastWallTouchDirection)
          speedX = -PUSH_OFF_WALL_VELOCITY;
        else
          speedX = PUSH_OFF_WALL_VELOCITY;
      }
    }

    if (collidingDown())
      speedX = speedX * HORIZONTAL_DAMPING;
    else
      speedX = speedX * .95f;

    if (!collidingDown())
      speedY = speedY + GRAVITY;
  }

  void updatePhysics() {
    if (x < applet.width)
      x = applet.width;

    if (collidingRight()) {
      if (speedY > 0) speedY = 0;
      if (speedX > 0) speedX = 0;
      lastWallTouchTime = applet.millis();
    } else if (collidingLeft()) {
      if (speedY > 0) speedY = 0;
      if (speedX < 0) speedX = 0;
      lastWallTouchTime = applet.millis();
    }

    if (collidingRight()) {
      lastWallTouchDirection = true;
    } else if (collidingLeft()) {
      lastWallTouchDirection = false;
    }

    x += speedX;
    y += speedY;
  }

  void drawPlayer(Camera camera) {
    // Rendering
    applet.fill(255, 255, 255);
    applet.rect(x - camera.x + applet.width/2, y - camera.y, size, size);
  }

  public boolean isDead() {
    return death != null;
  }

  boolean collidingUp() {
    boolean flag = false;
    for (Block b : level.blocks)
      if (b.collidingUp.contains(this))
        flag = true;
    return flag;
  }

  boolean collidingDown() {
    boolean flag = false;
    for (Block b : level.blocks)
      if (b.collidingDown.contains(this))
        flag = true;
    return flag;
  }

  boolean collidingRight() {
    boolean flag = false;
    for (Block b : level.blocks)
      if (b.collidingRight.contains(this))
        flag = true;
    return flag;
  }

  boolean collidingLeft() {
    boolean flag = false;
    for (Block b : level.blocks)
      if (b.collidingLeft.contains(this))
        flag = true;
    return flag;
  }
}