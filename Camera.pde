class Camera {
  
  float x, y;
  
  void update(Player player) {
    x = player.x;
    y = player.y + 300;
  }
  
}