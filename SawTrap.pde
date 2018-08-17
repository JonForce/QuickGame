class SawTrap {

  final float
    SPIKE_WIDTH = 70, 
    SPIKE_HEIGHT = 70;
  final int
    SPIKES = 5;

  Player p;
  float x, y, r;
  float[] speeds;

  SawTrap(Player p, float x, float y) {
    this.p = p;
    this.x = x;
    this.y = y;
    speeds = new float[SPIKES];
    for (int i = 0; i < SPIKES; i ++)
      speeds[i] = random(1) < .5? random(-5, -8) : random(5, 8);
  }

  void render() {
    fill(0, 0, 0);
    if (x - p.x + SPIKE_HEIGHT > 0 && x - p.x - SPIKE_HEIGHT < width) {
      for (int i = 0; i < SPIKES; i ++) {
        pushMatrix();
        translate(x - p.x, y);
        rotate(r * speeds[i]);
        triangle(
          -SPIKE_WIDTH/2, -SPIKE_HEIGHT/2, 
          0, SPIKE_HEIGHT/2, 
          SPIKE_WIDTH/2, -SPIKE_HEIGHT/2);
        popMatrix();
      }
      r += .05;
    }

    if (dist(p.x + p.size/2 + width/2, p.y + p.size/2, x, y) < SPIKE_HEIGHT && !p.isDead())
      p.die();
  }
}