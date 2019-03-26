class BloodAnimation {

  final int
    H_PADDING = 50;

  float x, y, w, h;
  float damping;
  int drips;

  PVector[] inkSpots;
  float[] inkSizes;
  PGraphics graphic;

  BloodAnimation(float x, float y, float w, float h, float damping, int drips) {
    graphic = createGraphics((int) w + H_PADDING*2, (int) h, P2D);
    this.drips = drips;
    inkSpots = new PVector[drips];
    inkSizes = new float[drips];
    this.damping = damping;
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    for (int i = 0; i < drips; i ++)
      createNewDrip(i);
  }

  void render() {
    simulate(1);
    for (int i = 0; i < drips; i ++) {
      image(graphic, x - H_PADDING, y);
    }
  }

  void simulate(int j) {
    while (j > 0) {
      for (int i = 0; i < drips; i ++) {
        graphic.beginDraw();
        graphic.stroke(0, 255);
        graphic.strokeWeight(inkSizes[i]);

        PVector nextLocation = new PVector();
        nextLocation.x = inkSpots[i].x + random(-.5, .5);
        nextLocation.y = inkSpots[i].y + random(3);

        graphic.line(inkSpots[i].x, inkSpots[i].y, nextLocation.x, nextLocation.y);
        graphic.endDraw();

        inkSizes[i] -= random(0, damping);
        inkSpots[i] = nextLocation;

        if (inkSizes[i] < 0)
          createNewDrip(i);
      }

      j --;
    }
  }

  void createNewDrip(int i) {
    inkSpots[i] = new PVector(H_PADDING + random(0, w), 0);
    inkSizes[i] = random(10, 20);
  }
}