import java.util.*;

/** This class controls the entire background of the game. */
class Background {
  final int BG_DUST_AMOUNT = 150;

  private Speck[] specks;
  ArrayList<Tree> trees = new ArrayList<Tree>();
  
  Comparator<Renderable> depthComparator = new Comparator<Renderable>() {
    @Override
      public int compare(Renderable a, Renderable b) {
      return a.depth() > b.depth()? 1 : -1;
    }
  };
  ArrayList<Renderable> objectsToRender = new ArrayList<Renderable>();

  Background() {
    specks = new Speck[BG_DUST_AMOUNT];
    for (int i = 0; i < BG_DUST_AMOUNT; i ++) {
      specks[i] = new Speck(random(width), random(height - 30), random(1, 10));
      specks[i].depth = (specks[i].size / 10);
      objectsToRender.add(specks[i]);
    }

    for (int i = 0; i < 20; i ++) {
      Tree t = new Tree(i * random(100, 300), (1.0/20) * i);
      trees.add(t);
      objectsToRender.add(t);
    }
    Collections.sort(objectsToRender, depthComparator);
  }

  void render() {
    fill(0, 0, 0);
    
    for (Renderable ren : objectsToRender)
      ren.render();
  }
}

  /** This helper class represents a speck of dust in the background. */
class Speck implements Renderable {

  float x, y, size, depth;

  Speck(float x, float y, float size) {
    this.x = x;
    this.y = y;
    this.size = size;
  }

  void render() {
    // Render the dust particle. The x position is looped around the left side of the screen.
    // Depth is multiplied against the player's x position to produce a parralax affect.
    ellipse(
      width - (x + camera.x * depth) % width, 
      (height - (y + camera.y))%height, 
      size, size);
  }
  @Override
    float depth() { 
    return depth;
  }
}