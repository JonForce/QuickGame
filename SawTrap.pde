class SawTrap {

  static final float
    SPIKE_WIDTH = 70, 
    SPIKE_HEIGHT = 70;
  final int
    SPIKES = 5;

  float r;
  float[] speeds;
  Vector startPoint = new Vector(), endPoint;

  SawTrap(float x, float y) {
    startPoint.x = x;
    startPoint.y = y;
    speeds = new float[SPIKES];
    for (int i = 0; i < SPIKES; i ++)
      speeds[i] = random(1) < .5? random(-5, -8) : random(5, 8);
  }

  void render(Camera camera, Player ... players) {
    fill(0, 0, 0);
    float x = startPoint.x;
    float y = startPoint.y;
    
    if (x - camera.x + SPIKE_HEIGHT > 0 && x - camera.x - SPIKE_HEIGHT < width) {
      for (int i = 0; i < SPIKES; i ++) {
        pushMatrix();
        translate(x - camera.x, y - camera.y);
        rotate(r * speeds[i]);
        triangle(
          -SPIKE_WIDTH/2, -SPIKE_HEIGHT/2, 
          0, SPIKE_HEIGHT/2, 
          SPIKE_WIDTH/2, -SPIKE_HEIGHT/2);
        popMatrix();
      }
      r += .05;
    }

    for (Player p : players) {
      if (dist(p.x + p.size/2 + width/2, p.y + p.size/2, x, y) < SPIKE_HEIGHT && !p.isDead())
        p.die();
    }
  }
}