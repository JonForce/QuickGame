class DeathAnimation {

  final int
    CHUNKS = 50, 
    PERCENT_BLOOD = 80;

  Player player;
  float x, y;
  Chunk[] chunks;

  DeathAnimation(Player p, float x, float y) {
    this.player = p;
    this.x = x;
    this.y = y;
    chunks = new Chunk[CHUNKS];
    for (int i = 0; i < CHUNKS; i++) {
      chunks[i] = new Chunk(x, y, random(100) < PERCENT_BLOOD);
    }
  }

  void render(Camera camera) {
    for (int i = 0; i < CHUNKS; i ++)
      chunks[i].render(camera);
  }
}

class Chunk {

  final float
    GRAVITY = random(.3f, 1f), 
    BLOOD_SIZE = 5, 
    CHUNK_SIZE = 15, 
    HORIZONTAL_DAMPING = .975f;

  boolean blood;
  float
    x, y, 
    speedX = random(-4, 4), 
    speedY = random(-6, 2);

  Chunk(float x, float y, boolean blood) {
    this.x = x;
    this.y = y;
    this.blood = blood;
  }

  void render(Camera camera) {
    // Physics
    x += speedX;
    y += speedY;
    speedY += GRAVITY;

    if (y > height - 30 - (blood? BLOOD_SIZE : CHUNK_SIZE))
      y = height - 30 - (blood? BLOOD_SIZE : CHUNK_SIZE);
    speedX = speedX * HORIZONTAL_DAMPING;

    // Rendering
    if (blood) {
      fill(255, 0, 0);
      rect(x - camera.x + width/2, y - camera.y, BLOOD_SIZE, BLOOD_SIZE);
    } else {
      fill(255, 255, 255);
      rect(x - camera.x + width/2, y - camera.y, CHUNK_SIZE, CHUNK_SIZE);
    }
  }
}