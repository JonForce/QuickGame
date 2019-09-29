package com.saucy.quickgame.main;

import processing.core.PApplet;

import java.util.*;

/** This class controls the entire background of the game. */
public class Background {

  private PApplet applet;
  
  /** This defines how much black dust there should be in the background. */
  final int
    MAX_BG_DUST = 150,
    MAX_TREES = 20,
    MAX_STARS = 200;
  int bgDustAmount = 150;
  
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
  
  public Background(PApplet applet, GraphicsSettings settings) {
    this.applet = applet;

    // The amount of dust should be based on their settings.
    this.bgDustAmount = (int)(settings.bgParticles * MAX_BG_DUST);
    // First create all of the specks and add them to their array.
    specks = new Speck[bgDustAmount];
    for (int i = 0; i < bgDustAmount; i ++) {
      specks[i] = new Speck(
              applet,
              applet.random(applet.width),
              applet.random(applet.height - 30),
              applet.random(1, 6));
      specks[i].depth = (specks[i].size / 6.0f);
      objectsToRender.add(specks[i]);
    }
    
    int nTrees = (int) (settings.bgObjects * MAX_TREES);
    // Add the trees to the objects to render.
    for (int i = 0; i < nTrees; i ++) {
      Tree t = new Tree(applet, i * applet.random(100f, 300f), (1.0f/20) * i);
      trees.add(t);
      objectsToRender.add(t);
    }
    
    int nStars = (int) (settings.bgParticles * MAX_STARS);
    // Add a the star-rendering object to the objects to render.
    objectsToRender.add(new Stars(applet, nStars));
    
    // Sort all of the items in the BG by their depth.
    Collections.sort(objectsToRender, depthComparator);
  }
  
  public void update() {
    for (Renderable ren : objectsToRender) {
      if (ren instanceof Stars)
        ((Stars) ren).update();
      else if (ren instanceof Speck)
        ((Speck) ren).x += ((Speck) ren).depth;
    }
  }

  public void render(Camera camera) {
    applet.fill(0, 0, 0);

    for (Renderable ren : objectsToRender)
      ren.render(camera);
  }
}