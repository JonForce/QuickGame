package com.saucy.quickgame.main;

import com.saucy.quickgame.main.Camera;

public interface Renderable {
  void render(Camera camera);
  float depth();
}