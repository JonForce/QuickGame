class Tree implements Renderable { //<>// //<>//
  Player player;
  float x, depth;

  ArrayList<Vector> vectors = new ArrayList<Vector>();
  ArrayList<Edge> edges = new ArrayList<Edge>();
  ArrayList<Box> boxes = new ArrayList<Box>();
  ArrayList<Polygon> polygons = new ArrayList<Polygon>();
  PGraphics graphic;
  PImage image;

  Tree(Player player, float x, float depth) {
    this.player = player;
    this.x = x;
    this.depth = depth;
    graphic = createGraphics(800, 800);
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

  void render() {
    float drawX = width - (player.x * depth + x) % (width + 800);
    image(graphic, drawX, height-30-800);
  }
  @Override
  float depth() { 
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
      float dx = random(-50, 50);
      Vector c = new Vector(a.x + dx + random(5, 15), a.y + random(-150, -50));
      Vector d = new Vector(b.x + dx - random(5, 15), b.y + random(-150, -50));

      vectors.add(c);
      vectors.add(d);
      edges.add(new Edge(a, b));
      edges.add(new Edge(a, c));
      edges.add(new Edge(b, d));
      boxes.add(new Box(a, b, c, d));
      a = c;
      b = d;
    }
    edges.add(new Edge(a, b));
    Vector center = new Vector(a.x + (b.x - a.x)/2, a.y + (b.y - a.y)/2);

    // Generate the tree top.
    int points = 6;
    a = b = null;
    float offsetRotation = random(0, 2*PI), distanceOut = random(150, 200);
    for (int i = 0; i < points; i ++) {
      Vector c = new Vector(
        sin(i * (2*PI/points) + offsetRotation) * distanceOut + center.x, 
        cos(i * (2*PI/points) + offsetRotation) * distanceOut + center.y
        );
      Vector d = new Vector(
        sin(i * (2*PI/points) + (2*PI/points)/2 + offsetRotation) * distanceOut*.5f + center.x, 
        cos(i * (2*PI/points) + (2*PI/points)/2 + offsetRotation) * distanceOut*.5f + center.y
        );
      edges.add(new Edge(c, d));
      if (b != null)
        edges.add(new Edge(b, c));
      vectors.add(c);
      vectors.add(d);
      a = c;
      b = d;
    }
    edges.add(new Edge(vectors.get(vectors.size() - 1), vectors.get(vectors.size() - points * 2)));
    Vector[] polygonPoints = new Vector[points * 2];
    for (int i = 1; i <= points * 2; i++)
      polygonPoints[i - 1] = vectors.get(vectors.size() - i);
    polygons.add(new Polygon(polygonPoints));
  }
}