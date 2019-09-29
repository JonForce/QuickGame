package com.saucy.quickgame.main;

import processing.core.PApplet;

import java.util.ArrayList;

/** A Block represents a solid rectangle in the game world. They are the fundamental building blocks of the
  solid world in game. They compose the ground, and obstacles of the game. */
public class Block {

  private PApplet applet;

  /** Define the x and y positions of the block (referencing the top left corner of the block)
    as well as the block's width and height. */
  float x, y, w, h;
  /** These are running lists of which players are colliding with the block.
    Ex : If a player is on top of the block it will be contained in the collidingDown list. */
  ArrayList<Player>
    collidingUp = new ArrayList<Player>(), 
    collidingDown = new ArrayList<Player>(), 
    collidingRight = new ArrayList<Player>(), 
    collidingLeft = new ArrayList<Player>();
    
  boolean debug = false;

  Block(PApplet applet, float x, float y, float w, float h) {
    this.applet = applet;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  /** Update the physics for each of the players. This detect and resolve collisions
      with the players in the array. */
  public void updatePhysics(Player ... players) {
    // Define the rectangle that represents this block in the physical world.
    Rectangle self = new Rectangle(applet, x, y, w, h);

    for (Player player : players) {
      // This rectangle represents the player's presence in the world.
      Rectangle playerRect = new Rectangle(applet, player.x, player.y, player.size, player.size);
    
      // We detect collision by taking the intersection of these two rectangles.
      Rectangle intercept = self.intercept(playerRect);
    
      // If there was no collision, remove that player from any collision lists.
      if (intercept.w == 0 && intercept.y == 0) {
        collidingDown.remove(player);
        collidingUp.remove(player);
        collidingLeft.remove(player);
        collidingRight.remove(player);
      } else {
        // Otherwise we need to resolve the collision.
        // We resolve collisions by adding the intercept between the two rectangles to the player's position.
        // We need to determine which direction to move the player first though.
        // We want to move the player in the smallest direction.
        
        // If the intercept's smaller vertically,
        if (intercept.h <= intercept.w) {
          if (player.y < y + h/2) {
            collidingDown.add(player);
            player.y -= intercept.h;
          } else if (player.y > y + h/2) {
            collidingUp.add(player);
            player.y += intercept.h;
          }
          collidingLeft.remove(player);
          collidingRight.remove(player);

          player.speedY = 0;
        } else {
          if (player.x < x + w/2) {
            player.x -= intercept.w;
            collidingRight.add(player);
          } else if (player.x > x + w/2) {
            player.x += intercept.w;
            collidingLeft.add(player);
          }
          collidingDown.remove(player);
          collidingUp.remove(player);
        }
      }
    }
  }

  public void render(Camera camera) {
    applet.fill(0, 0, 0);
    applet.rect(x - camera.x + applet.width/2, y - camera.y, w, h);
  }
}


