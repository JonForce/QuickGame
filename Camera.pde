class Camera {

  final float FOLLOW_SPEED = .2f;
  float x, y;

  void update(Player ... players) {
    float xAvg = 0, yAvg = 0, count = 0;
    for (Player p : players) {
      if (!p.isDead()) {
        xAvg += p.x;
        yAvg += p.y;
        count ++;
      }
    }
    xAvg /= count;
    yAvg /= count;
    if (count != 0) {
      x = x + (xAvg - x) * FOLLOW_SPEED;
      y = y + ((yAvg - height*.5) - y) * FOLLOW_SPEED;
    }

    if (y > 0) y = 0;
  }
}