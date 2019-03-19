/** A Block represents a solid rectangle in the game world. They are the fundamental building blocks of the
  solid world in game. They compose the ground, and obstacles of the game. */
class Block {
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

  Block(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  
  /** Update the physics for each of the players. This detect and resolve collisions
      with the players in the array. */
  void updatePhysics(Player ... players) {
    // Define the rectangle that represents this block in the physical world.
    Rectangle self = new Rectangle(x, y, w, h);

    for (Player player : players) {
      // This rectangle represents the player's presence in the world.
      Rectangle playerRect = new Rectangle(player.x, player.y, player.size, player.size);
    
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

  void render(Camera camera) {
    fill(0, 0, 0);
    rect(x - camera.x + width/2, y - camera.y, w, h);
  }
}


/** This class represents a simple rectangle in the game world. It is a tool used by block
  for collision detection. */
class Rectangle {
  float x, y, w, h;

  Rectangle(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  Rectangle() { }

  Rectangle intercept(Rectangle other) {
    Rectangle intercept = new Rectangle();
    if (x > other.x && x < other.x + other.w) {
      intercept.x = x;
      intercept.w = (other.x + other.w) - x;
    }
    if (other.x > x && other.x < x + w) {
      intercept.x = other.x;
      intercept.w = (x + w) - other.x;
    }
    // Contained
    if (x > other.x && x + w < other.x + other.w) {
      intercept.x = x;
      intercept.w = w;
    }
    if (other.x > x && other.x + other.w < x + w) {
      intercept.x = other.x;
      intercept.w = other.w;
    }

    if (y > other.y && y < other.y + other.y) {
      intercept.y = y;
      intercept.h = (other.y + other.h) - y;
    }
    if (other.y > y && other.y < y + h) {
      intercept.y = other.y;
      intercept.h = (y + h) - other.y;
    }
    // Contained
    if (y > other.y && y + h < other.y + other.h) {
      intercept.y = y;
      intercept.h = h;
    }
    if (other.y > y && other.y + other.h < y + h) {
      intercept.y = other.y;
      intercept.h = other.h;
    }

    if (intercept.w <= 0 || intercept.h <= 0) {
      intercept.x = 0;
      intercept.y = 0;
      intercept.w = 0;
      intercept.h = 0;
    }
    return intercept;
  }

  void render(Camera camera) {
    fill(255, 0, 0);
    rect(x - camera.x + width/2, y - camera.y, w, h);
  }
}