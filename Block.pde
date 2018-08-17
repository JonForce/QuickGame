class Block {
  float x, y, w, h;
  Player player;
  boolean
    collidingUp, collidingDown, collidingRight, collidingLeft;

  Block(Player p, float x, float y, float w, float h) {
    this.player = p;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }

  void updatePhysics() {
    Rectangle self = new Rectangle(x, y, w, h);
    Rectangle players = new Rectangle(player.x, player.y, player.size, player.size);

    Rectangle intercept = self.intercept(players);

    if (intercept.w == 0 && intercept.y == 0) {
      collidingDown = false;
      collidingUp = false;
      collidingLeft = false;
      collidingRight = false;
    } else {
      if (intercept.h <= intercept.w) {
        if (player.y < y + h/2) {
          collidingDown = true;
          player.y -= intercept.h;
        } else if (player.y > y + h/2) {
          collidingUp = true;
          player.y += intercept.h;
        }
        collidingLeft = false;
        collidingRight = false;

        player.speedY = 0;
      } else {
        if (player.x < x + w/2) {
          player.x -= intercept.w;
          collidingRight = true;
        } else if (player.x > x + w/2) {
          player.x += intercept.w;
          collidingLeft = true;
        }
        collidingDown = false;
        collidingUp = false;
      }
    }
  }

  void render() {
    fill(0, 0, 0);
    rect(x - player.x + width/2, y, w, h);
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

  void render(Player p) {
    fill(255, 0, 0);
    rect(x - p.x + width/2, p.y, w, h);
    println(x + ", " + y + ", " + w + ", " + h);
  }
}