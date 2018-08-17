import org.gamecontrolplus.gui.*; //<>//
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

  float
    x, y, speedX, speedY, 
    size = 30, speed = 3;
  DeathAnimation death;
  long deathTime;
  Controller controller;

  Player(Controller controller) {
    y = height - 30 - size;
    x = width;
    this.controller = controller;
  }

  void render() {
    if (isDead())
      death.render();
    else {
      updateControls();
      updatePhysics();
      drawPlayer();
    }
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

    if (controller.buttonA.pressed() &&
      (collidingDown()))
      speedY = -JUMP_VELOCITY;

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

    x += speedX;
    //if (!(keysPressed.contains('d') && collidingRight()))
    y += speedY;
  }

  void drawPlayer() {
    // Rendering
    fill(255, 255, 255);
    rect(width / 2, y, size, size);
  }

  boolean isDead() {
    return death != null;
  }

  boolean collidingUp() {
    boolean flag = false;
    for (Block b : blocks)
      if (b.collidingUp)
        flag = true;
    return flag;
  }

  boolean collidingDown() {
    boolean flag = false;
    for (Block b : blocks)
      if (b.collidingDown)
        flag = true;
    return flag;
  }

  boolean collidingRight() {
    boolean flag = false;
    for (Block b : blocks)
      if (b.collidingRight)
        flag = true;
    return flag;
  }

  boolean collidingLeft() {
    boolean flag = false;
    for (Block b : blocks)
      if (b.collidingLeft)
        flag = true;
    return flag;
  }
}