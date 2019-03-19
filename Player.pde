import org.gamecontrolplus.gui.*; //<>// //<>//
import org.gamecontrolplus.*;
import net.java.games.input.*;

class Player {

  final float
    // This affects the player's sliding distance.
    HORIZONTAL_DAMPING = .75f, 
    // The gravity of the map in pixels per second squared.
    GRAVITY = .5f, 
    // The amount to set the vertical velocity to when jumping.
    JUMP_VELOCITY = 15, 
    AIR_CONTROL = .2f, 
    RUNNING_MULTIPLIER = 1.75f;
  final long
    MIN_TIME_BETWEEN_WALLJUMPS = 750,
    FELL_OFF_WALL_STILL_WALL_JUMP_TIME = 100;

  float
    x, y, speedX, speedY, 
    size = 30, speed = 3;
  DeathAnimation death;
  long deathTime, lastWallJump, lastWallTouchTime;
  Controller controller;
  Gun gun;
  LevelState level;

  Player(LevelState level, Controller controller) {
    this.level = level;
    y = height - 30 - size;
    x = width;
    this.controller = controller;
    gun = new Pistol(this, controllerA);
  }
  
  void update() {
      updateControls();
      updatePhysics();
  }

  void render(Camera camera) {
    if (isDead())
      death.render(camera);
    else {
      drawPlayer(camera);
    }
    gun.render(camera);
  }

  void die() {
    death = new DeathAnimation(this, x, y);
    deathTime = millis();
  }

  void updateControls() {
    float speedMultiplier = controller.leftTrigger.getValue() > .2f? RUNNING_MULTIPLIER : 1;
    if (!collidingDown())
      speedMultiplier = AIR_CONTROL;
    speedX += speed * controller.sliderA.getValue() * speedMultiplier;

    boolean wallJump =
      (millis() - lastWallTouchTime < FELL_OFF_WALL_STILL_WALL_JUMP_TIME)
      && millis() - lastWallJump > MIN_TIME_BETWEEN_WALLJUMPS;
    if (
      controller.buttonA.pressed() &&
      (collidingDown() || wallJump)) {
      speedY = -JUMP_VELOCITY;
      if (wallJump) lastWallJump = millis();
    }

    if (collidingDown())
      speedX = speedX * HORIZONTAL_DAMPING;
    else
      speedX = speedX * .95f;

    if (!collidingDown())
      speedY = speedY + GRAVITY;
  }

  void updatePhysics() {
    if (x < width)
      x = width;

    if (collidingRight()) {
      if (speedY > 0) speedY = 0;
      if (speedX > 0) speedX = 0;
      lastWallTouchTime = millis();
    } else if (collidingLeft()) {
      if (speedY > 0) speedY = 0;
      if (speedX < 0) speedX = 0;
      lastWallTouchTime = millis();
    }

    x += speedX;
    //if (!(keysPressed.contains('d') && collidingRight()))
    y += speedY;
  }

  void drawPlayer(Camera camera) {
    // Rendering
    fill(255, 255, 255);
    rect(x - camera.x + width/2, y - camera.y, size, size);
  }

  boolean isDead() {
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