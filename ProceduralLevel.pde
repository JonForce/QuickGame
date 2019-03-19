class ProceduralLevel {

  final float
    LENGTH = 20000, 
    BUFFER = 400;

  ArrayList<Spawner> spawners = new ArrayList<Spawner>();
  LevelState level;
  
  ProceduralLevel(LevelState level) {
    this.level = level;
    spawners.add(new SawHallway(level));
    spawners.add(new SawRow(level));
    spawners.add(new SawPit(level));
    spawners.add(new SawClimb(level));
  }

  void generate() {
    ArrayList<Rect> spawnPoints = new ArrayList<Rect>();
    // Build the ground.
    level.blocks.add(new Block(0, height - 30, LENGTH, 31));

    float x = level.playerA.x + BUFFER*2;

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
  LevelState level;
  SawHallway(LevelState level) { 
    this.level = level;
    canStack = true;
    minWidth = 300;
  }

  @Override
  void generate(float baseX, float baseY, float maxWidth) {
    int sawSeperation = (int)random(200, 350), saws = (int)random(1, (int) (maxWidth / sawSeperation));
    h = 200;
    w = saws * sawSeperation;
    level.blocks.add(new Block(baseX, baseY - h, w, 40));
    level.blocks.add(new Block(baseX - 40, baseY - h - 40, w + 80, 40));
    for (int i = 0; i <= saws; i++) {
      if (i % 2 == 0) {
        level.traps.add(new SawTrap(baseX + width/2 + i*sawSeperation, baseY - h + 40));
      } else {
        level.traps.add(new SawTrap(baseX + width/2 + i*sawSeperation, baseY - 30));
      }
    }
    //h += 40;
  }
}

class SawRow extends Spawner {
  LevelState level;
  SawRow(LevelState level) {
    this.level = level;
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
      level.traps.add(new SawTrap(x + width/2, baseY - SawTrap.SPIKE_HEIGHT/2));
      spawned ++;
      x += SawTrap.SPIKE_WIDTH;
    }
  }
}

class SawPit extends Spawner {
  LevelState level;
  SawPit(LevelState level) { 
    this.level = level;
    canStack = false;
    minWidth = 1400;
  }

  @Override
    void generate(float baseX, float baseY, float maxWidth) {
    h = 300;
    w = min(random(1000, 1500), maxWidth);

    // Starting block.
    level.blocks.add(new Block(baseX, baseY - 250, w / 4, 250));
    // Ending block
    level.blocks.add(new Block(baseX + w - w/4, baseY - 100, w / 4, 100));

    // Spawn the saws in the pit.
    float sawsLength = w - w/4;
    int toSpawn = (int) (sawsLength/SawTrap.SPIKE_WIDTH), spawned = 0;
    float x = baseX + 250;
    while (spawned < toSpawn) {
      level.traps.add(new SawTrap(x + width/2, baseY - SawTrap.SPIKE_HEIGHT/2));
      spawned ++;
      x += SawTrap.SPIKE_WIDTH;
    }
  }
}

class SawClimb extends Spawner {
  LevelState level;
  SawClimb(LevelState level) {
    this.level = level;
    canStack = false;
    minWidth = 700;
  }

  @Override
    void generate(float baseX, float baseY, float maxWidth) {
    h = 1000;
    w = 700;
    float floorHeight = 200, wallThickness = 60;

    // Vertical walls
    level.blocks.add(new Block(baseX, baseY - h + 100, wallThickness, h - 250));
    level.blocks.add(new Block(baseX + w - wallThickness, baseY - h, wallThickness, h));

    // Platforms
    for (int i = 0; i < h / floorHeight; i ++) {
      level.blocks.add(new Block(baseX + 150*(i % 2 == 0? 1 : 0), baseY - floorHeight*i, w - 175, wallThickness));
      if (random(0, 100) < 70)
        level.traps.add(new SawTrap(baseX + width/2 + 100*(i % 2 == 0? 1 : 0) + w/2 + 50, baseY - floorHeight*i - 8));
      if (random(0, 100) < 70)
        level.traps.add(new SawTrap(baseX + width/2 + 100*(i % 2 == 0? 1 : 0) + w/2 - 150, baseY - floorHeight*i - floorHeight + wallThickness));
    }
  }
}