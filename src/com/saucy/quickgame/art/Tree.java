package com.saucy.quickgame.art;

import com.saucy.quickgame.main.*;
import com.saucy.quickgame.math.Vector;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;

public class Tree implements Renderable {

  private PApplet applet;

  float x, depth;

  ArrayList<Vector> vectors = new ArrayList<Vector>();
  ArrayList<Edge> edges = new ArrayList<Edge>();
  ArrayList<Box> boxes = new ArrayList<Box>();
  ArrayList<Polygon> polygons = new ArrayList<Polygon>();
  PGraphics graphic;

  public Tree(PApplet applet, float x, float depth) {
    this.applet = applet;
    this.x = x;
    this.depth = depth;
    graphic = applet.createGraphics(800, 800);
    generate();
    graphic.beginDraw();
    for (Box b : boxes)
      b.render(graphic);
    for (Edge e : edges)
      e.render(graphic);
    for (Vector v : vectors)
      v.render(graphic);
    for (Polygon p : polygons)
      p.render(graphic);
    graphic.endDraw();
  }

  public void render(Camera camera) {
    float drawX = applet.width - (camera.x * depth + x) % (applet.width + 800);
    applet.image(graphic, drawX, applet.height-30-800 - camera.y);
  }
  @Override
  public float depth() {
    return depth;
  }

  void generate() {
    edges.clear();
    boxes.clear();
    vectors.clear();
    polygons.clear();

    // Generate tree trunk.
    vectors.add(new Vector(graphic.width/2 - 75, graphic.height));
    vectors.add(new Vector(graphic.width/2 + 75, graphic.height));
    Vector a = vectors.get(0);
    Vector b = vectors.get(1);
    for (int i = 2; i < 7; i ++) {
      float dx = applet.random(-50, 50);
      Vector c = new Vector(a.x + dx + applet.random(5, 15), a.y + applet.random(-150, -50));
      Vector d = new Vector(b.x + dx - applet.random(5, 15), b.y + applet.random(-150, -50));

      vectors.add(c);
      vectors.add(d);
      edges.add(new Edge(applet, a, b));
      edges.add(new Edge(applet,a, c));
      edges.add(new Edge(applet,b, d));
      boxes.add(new Box(applet, a, b, c, d));
      a = c;
      b = d;
    }
    edges.add(new Edge(applet, a, b));
    Vector center = new Vector(a.x + (b.x - a.x)/2, a.y + (b.y - a.y)/2);

    // Generate the tree top.
    int points = 6;
    a = b = null;
    float offsetRotation = applet.random(0, 2*applet.PI), distanceOut = applet.random(150, 200);
    for (int i = 0; i < points; i ++) {
      Vector c = new Vector(
              applet.sin(i * (2*applet.PI/points) + offsetRotation) * distanceOut + center.x,
              applet.cos(i * (2*applet.PI/points) + offsetRotation) * distanceOut + center.y
        );
      Vector d = new Vector(
              applet.sin(i * (2*applet.PI/points) + (2*applet.PI/points)/2 + offsetRotation) * distanceOut*.5f + center.x,
              applet.cos(i * (2*applet.PI/points) + (2*applet.PI/points)/2 + offsetRotation) * distanceOut*.5f + center.y
        );
      edges.add(new Edge(applet, c, d));
      if (b != null)
        edges.add(new Edge(applet, b, c));
      vectors.add(c);
      vectors.add(d);
      a = c;
      b = d;
    }
    edges.add(new Edge(applet, vectors.get(vectors.size() - 1), vectors.get(vectors.size() - points * 2)));
    Vector[] polygonPoints = new Vector[points * 2];
    for (int i = 1; i <= points * 2; i++)
      polygonPoints[i - 1] = vectors.get(vectors.size() - i);
    polygons.add(new Polygon(polygonPoints));
  }
}