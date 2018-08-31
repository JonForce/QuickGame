class ProceduralLevel {

  final float
    LENGTH = 20000, 
    BUFFER = 400;

  ArrayList<Spawner> spawners = new ArrayList<Spawner>();

  ProceduralLevel(QuickGame game) {
    spawners.add(new SawHallway(game));
    spawners.add(new SawRow(game));
    spawners.add(new SawPit(game));
    spawners.add(new SawClimb(game));
  }

  void generate() {
    ArrayList<Rect> spawnPoints = new ArrayList<Rect>();
    // Build the ground.
    blocks.add(new Block(0, height - 30, LENGTH, 31));

    float x = playerA.x + BUFFER*2;

    while (x < LENGTH) {
      Spawner s = spawners.get((int)random(0, spawners.size()));
      s.generate(x, height, 2500);
      float sx = x;
      x += s.w;
      if (s.canStack) {
        spawnPoints.add(new Rect(sx, height - s.h, s.w, 0));
      }

      x += random(BUFFER, BUFFER * 2);
    }

    while (spawnPoints.size() > 0) {
      print(spawnPoints.size());
      Rect point = spawnPoints.get(0);
      Spawner s = getSpawnerForSize(point);
      if (s == null) {
        spawnPoints.remove(0);
        continue;
      }

      //float spawnOffset = random(0, point.w/2);
      s.generate(point.x/* + spawnOffset*/, point.y, point.w/* - spawnOffset*/);
      if (s.canStack) {
        spawnPoints.add(new Rect(point.x/* + spawnOffset*/, point.y - s.h, s.w/* - spawnOffset*/, 0));
      }
      spawnPoints.add(new Rect(point.x + s.w + BUFFER/2, point.y, point.w - (s.w + BUFFER/2), 0));
      spawnPoints.remove(0);
      x += random(BUFFER, BUFFER * 2);
    }
  }

  Spawner getSpawnerForSize(Rect point) {
    Spawner s = spawners.get((int) random(0, spawners.size()));
    int tries = 0;
    while (point.w < s.minWidth) {
      s = spawners.get((int) random(0, spawners.size()));

      if (tries++ > spawners.size() * 2)
        return null;
    }
    return s;
  }
}

abstract class Spawner {
  QuickGame game;
  float w, h, minWidth;
  boolean canStack = false;
  abstract void generate(float baseX, float baseY, float maxWidth);
}

class SawHallway extends Spawner {
  SawHallway(QuickGame game) { 
    this.game = game;
    canStack = true;
    minWidth = 300;
  }

  @Override
    void generate(float baseX, float baseY, float maxWidth) {
    int sawSeperation = (int)random(200, 350), saws = (int)random(1, (int) (maxWidth / sawSeperation));
    h = 200;
    w = saws * sawSeperation;
    game.blocks.add(new Block(baseX, baseY - h, w, 40));
    game.blocks.add(new Block(baseX - 40, baseY - h - 40, w + 80, 40));
    for (int i = 0; i <= saws; i++) {
      if (i % 2 == 0) {
        game.traps.add(new SawTrap(baseX + width/2 + i*sawSeperation, baseY - h + 40));
      } else {
        game.traps.add(new SawTrap(baseX + width/2 + i*sawSeperation, baseY - 30));
      }
    }
    //h += 40;
  }
}

class SawRow extends Spawner {
  SawRow(QuickGame game) { 
    this.game = game;
    canStack = false;
    minWidth = SawTrap.SPIKE_WIDTH*2;
  }

  @Override
    void generate(float baseX, float baseY, float maxWidth) {
    h = 300;
    w = random(SawTrap.SPIKE_WIDTH, min(600, maxWidth));

    int toSpawn = (int) (w/SawTrap.SPIKE_WIDTH), spawned = 0;
    float x = baseX + w/2;
    while (spawned < toSpawn) {
      game.traps.add(new SawTrap(x + width/2, baseY - SawTrap.SPIKE_HEIGHT/2));
      spawned ++;
      x += SawTrap.SPIKE_WIDTH;
    }
  }
}

class SawPit extends Spawner {
  SawPit(QuickGame game) { 
    this.game = game;
    canStack = false;
    minWidth = 1400;
  }

  @Override
    void generate(float baseX, float baseY, float maxWidth) {
    h = 300;
    w = min(random(1000, 1500), maxWidth);

    // Starting block.
    game.blocks.add(new Block(baseX, baseY - 250, w / 4, 250));
    // Ending block
    game.blocks.add(new Block(baseX + w - w/4, baseY - 100, w / 4, 100));

    // Spawn the saws in the pit.
    float sawsLength = w - w/4;
    int toSpawn = (int) (sawsLength/SawTrap.SPIKE_WIDTH), spawned = 0;
    float x = baseX + 250;
    while (spawned < toSpawn) {
      game.traps.add(new SawTrap(x + width/2, baseY - SawTrap.SPIKE_HEIGHT/2));
      spawned ++;
      x += SawTrap.SPIKE_WIDTH;
    }
  }
}

class SawClimb extends Spawner {
  SawClimb(QuickGame game) { 
    this.game = game;
    canStack = false;
    minWidth = 700;
  }

  @Override
    void generate(float baseX, float baseY, float maxWidth) {
    h = 1000;
    w = 700;
    float floorHeight = 200, wallThickness = 60;

    // Vertical walls
    blocks.add(new Block(baseX, baseY - h + 100, wallThickness, h - 250));
    blocks.add(new Block(baseX + w - wallThickness, baseY - h, wallThickness, h));

    // Platforms
    for (int i = 0; i < h / floorHeight; i ++) {
      blocks.add(new Block(baseX + 150*(i % 2 == 0? 1 : 0), baseY - floorHeight*i, w - 175, wallThickness));
      if (random(0, 100) < 70)
        traps.add(new SawTrap(baseX + width/2 + 100*(i % 2 == 0? 1 : 0) + w/2 + 50, baseY - floorHeight*i));
      if (random(0, 100) < 70)
        traps.add(new SawTrap(baseX + width/2 + 100*(i % 2 == 0? 1 : 0) + w/2 - 150, baseY - floorHeight*i - floorHeight + wallThickness));
    }
  }
}