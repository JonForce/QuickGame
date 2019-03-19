abstract class Gun {

  Player player;
  Controller controller;

  Gun(Player p, Controller c) {
    player = p;
    controller = c;
  }

  abstract void render(Camera camera);
}

class Pistol extends Gun {

  PImage sprite, flipped;

  Pistol(Player p, Controller c) {
    super(p, c);
    sprite = loadImage("Pistol.png");
    flipped = loadImage("PistolFlipped.png");
  }

  @Override
  void render(Camera camera) {
    pushMatrix();
    
    translate(
      player.x - camera.x + width/2 + aimX() * 10, 
      player.y - camera.y + aimY() * 10);
    
    scale(.125f, .125f);

    PImage image;

    if (aimX() >= 0) {
      rotate(atan(aimY() / aimX()));
      image = sprite;
    } else {
      image = flipped;
      rotate(atan(aimY() / aimX()));
      translate(-image.width, 0);
    }

    // Gun
    image(image, image.width / 2 - (aimX() < 0? 180 : 0), 0);
    // Hand
    fill(255);
    rect(image.width / 2, 120, 90, 90);
    
    popMatrix();
  }

  float aimX() {
    return controller.sliderBX.getValue();
  }

  float aimY() {
    return controller.sliderBY.getValue();
  }
}