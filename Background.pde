import java.util.*;

/** This class controls the entire background of the game. */
class Background {
  
  /** This defines how much black dust there should be in the background. */
  final int BG_DUST_AMOUNT = 150;
  
  /** Define the baground specks as well as the trees. */
  private Speck[] specks;
  ArrayList<Tree> trees = new ArrayList<Tree>();
  
  /** This comparator will compare Renderables based on their depths. */
  Comparator<Renderable> depthComparator = new Comparator<Renderable>() {
    @Override
      public int compare(Renderable a, Renderable b) {
      return a.depth() > b.depth()? 1 : -1;
    }
  };
  /** This is a list of objects to render in the background. They will be sorted by depth. */
  ArrayList<Renderable> objectsToRender = new ArrayList<Renderable>();
  
  Background() {
    // First create all of the specks and add them to their array.
    specks = new Speck[BG_DUST_AMOUNT];
    for (int i = 0; i < BG_DUST_AMOUNT; i ++) {
      specks[i] = new Speck(random(width), random(height - 30), random(1, 6));
      specks[i].depth = (specks[i].size / 6.0);
      objectsToRender.add(specks[i]);
    }
    
    // Add the trees to the objects to render.
    for (int i = 0; i < 20; i ++) {
      Tree t = new Tree(i * random(100, 300), (1.0/20) * i);
      trees.add(t);
      objectsToRender.add(t);
    }
    
    // Add a the star-rendering object to the objects to render.
    objectsToRender.add(new Stars());
    
    // Sort all of the items in the BG by their depth.
    Collections.sort(objectsToRender, depthComparator);
  }
  
  void update() {
    for (Renderable ren : objectsToRender) {
      if (ren instanceof Stars)
        ((Stars) ren).update();
      else if (ren instanceof Speck)
        ((Speck) ren).x += ((Speck) ren).depth;
    }
  }

  void render(Camera camera) {
    fill(0, 0, 0);

    for (Renderable ren : objectsToRender)
      ren.render(camera);
  }
}

/** This helper class represents a speck of dust in the background. */
class Speck implements Renderable {

  float x, y, size, depth, frequency, amplitude;
  
  Speck(float x, float y, float size) {
    this.x = x;
    this.y = y;
    this.size = size;
    frequency = random(1000, 2000);
    amplitude = random(5, 50);
  }

  void render(Camera camera) {
    y -= 1;
    fill(0);
    // Render the dust particle. The x position is looped around the left side of the screen.
    // Depth is multiplied against the player's x position to produce a parralax affect.
    ellipse(
      width - (x + camera.x * depth + sin(millis()/frequency)*amplitude) % width, 
      (height - (y + camera.y))%height, 
      size, size);
  }
  @Override
    float depth() { 
    return depth;
  }
}

/** This class renders all of the stars in the background. */
class Stars implements Renderable {

  ArrayList<Star> stars = new ArrayList<Star>();
  /** Define the maximum distance between two stars in order to form a connection. */
  final float CONNECT_DIST = 100;
  /** Define the numbers of total stars in the sky. They are randomly distributed. */
  final int STAR_COUNT = 200;

  Stars() {
    // Generate the stars into the stars list.
    for (int i = 0; i < STAR_COUNT; i ++) {
      Star s = new Star();
      s.x = random(-CONNECT_DIST, width);
      s.y = random(-CONNECT_DIST, height/2);
      s.depth = random(.1, .5);
      stars.add(s);
    }
  }
  
  void update() {
     for (Star s : stars) {
       s.x = (s.x + s.depth);
     }
  }

  void render(Camera camera) {
    // For every star in the list,
    for (Star s : stars) {
      // Loop over every star again, to try to find other stars to connect to.
      for (Star x : stars) {
        // If they're close enough,
        if (dist(s.x, s.y, x.x, x.y) < CONNECT_DIST) {
          // Connect the two stars with a line.
          stroke(255, 255, 255);
          line(s.x, s.y, x.x, x.y);
        }
      }
      
      // Next draw our current star.
      fill(255);
      ellipse(s.x, s.y, 3, 3);
      stroke(0);
      
      // Loop back to the left once the star has moved off the right of the screen.
      if (s.x > width + CONNECT_DIST)
        s.x = -CONNECT_DIST;
    }
  }

  @Override
    float depth() {
    return 0;
  }
}

class Star {
  float x, y, depth;
}