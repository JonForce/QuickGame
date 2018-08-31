class Block {
  float x, y, w, h;
  ArrayList<Player>
    collidingUp = new ArrayList<Player>(), 
    collidingDown = new ArrayList<Player>(), 
    collidingRight = new ArrayList<Player>(), 
    collidingLeft = new ArrayList<Player>();

  Block(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  void updatePhysics(Player ... players) {
    Rectangle self = new Rectangle(x, y, w, h);

    for (Player player : players) {
      Rectangle playerRect = new Rectangle(player.x, player.y, player.size, player.size);

      Rectangle intercept = self.intercept(playerRect);

      if (intercept.w == 0 && intercept.y == 0) {
        collidingDown.remove(player);
        collidingUp.remove(player);
        collidingLeft.remove(player);
        collidingRight.remove(player);
      } else {
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

  void render() {
    fill(0, 0, 0);
    rect(x - camera.x + width/2, y - camera.y, w, h);
  }
}



class Rectangle {
  float x, y, w, h;

  Rectangle(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
  Rectangle() {
  }

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

  void render() {
    fill(255, 0, 0);
    rect(x - camera.x + width/2, y - camera.y, w, h);
    println(x + ", " + y + ", " + w + ", " + h);
  }
}