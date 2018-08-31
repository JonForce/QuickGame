class Vector {
  float x, y;
  Vector(float x, float y) { 
    this.x = x; 
    this.y = y;
  }
  Vector() { }
  void render(PGraphics g) {
    g.fill(255, 255, 255);
    g.ellipse(x, y, 10, 10);
  }
}
class Edge {
  Vector a, b;
  Edge(Vector a, Vector b) { 
    this.a = a; 
    this.b = b;
  }
  void render(PGraphics g) {
    g.fill(0);
    g.line(a.x, a.y, b.x, b.y);
  }
}
class Box {
  Vector a, b, c, d;
  Box(Vector a, Vector b, Vector c, Vector d) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }
  void render(PGraphics g) {
    fill(200);
    g.stroke(150);
    g.triangle(a.x, a.y, b.x, b.y, c.x, c.y);
    g.triangle(d.x, d.y, b.x, b.y, c.x, c.y);
    g.stroke(0);
  }
}
class Polygon {
  Vector[] vectors;
  Polygon(Vector ... vectors) {
    this.vectors = vectors;
  }
  void render(PGraphics g) {
    g.fill(75);
    g.beginShape();
    for (Vector v : vectors)
      g.vertex(v.x, v.y);
    g.endShape(CLOSE);
    for (Vector v : vectors) {
      g.fill(255, 255, 255);
      g.ellipse(v.x, v.y, 10, 10);
    }
  }
}

class Rect {
  float x, y, w, h;
  Rect(float x, float y, float w, float h) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
  }
}