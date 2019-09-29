package com.saucy.quickgame.main;

import processing.core.PApplet;

import java.io.*;
import java.util.ArrayList;

public class LevelEditor extends LevelState {

  final int
    BLOCK = 0, 
    SAW = 1;

  final int
    SNAP_GRID_SIZE = 10;

  int type = BLOCK;
  float w = 30, h = 30;
  long savedTime = -100000000;

  boolean releasedMouse = true;
  boolean releasedE = true;
  boolean releasedQ = true;
  boolean releasedL = true;
  boolean releasedI = true;

  ArrayList<GameObject> objects = new ArrayList<GameObject>();

  Block dummyBlock;
  SawTrap dummySaw = new SawTrap(game, 0, 0);
  
  String[] helpText = new String[] {
    "Adjust size : W, A, S, D",
    "Place Object : Left Click",
    "Delete Object : Right Click",
    "Switch Objects : E",
    "Save Level : Q",
    "Load Level : L",
    "Enable Invincibility : I"
  };

  LevelEditor(QuickGame game) {
    super(game);
    dummyBlock = new Block(game,0, 0, 0, 0);
  }

  @Override
    void spawnLevel() {
    // Add a floor
    super.blocks.add(new Block((PApplet) game, 0, game.height - 30, 50000, 31));
  }

  @Override
  public void render() {
    super.render();

    String modeText = "";
    if (type == BLOCK)
      modeText = "Block Mode";
    else if (type == SAW)
      modeText = "Saw Mode";

    game.fill(255);
    game.rect(game.width/2 - 150, 20, 300, 150);
    game.rect(10, 10, 500, 500);
    game.fill(0);
    game.textSize(40);
    game.text(modeText, game.width/2, 75);
    game.textSize(30);
    game.text("width : " + w, game.width/2, 110);
    game.text("height : " + h, game.width/2, 145);
    
    for (int i = 0; i < helpText.length; i ++) {
      game.text(helpText[i], 10 + 250, 10 + 75 + i * 50);
    }

    if (game.millis() - savedTime < 1500) {
      game.textSize(100);
      game.fill(255);
      game.text("Saved!", game.width/2, game.height/2);
    }


    if (game.keyPressed && activeMenu == null) {
      if (game.key == 'd')
        w += 2;
      else if (game.key == 'a')
        w -= 2;
      else if (game.key == 'w')
        h += 2;
      else if (game.key == 's')
        h -= 2;
      else if (game.key == 'e' && releasedE) {

        if (type == BLOCK)
          type = SAW;
        else if (type == SAW)
          type = BLOCK;

        releasedE = false;
      } else if (game.key == 'q' && releasedQ) {
        super.activeMenu = new SaveLevelMenu(game,this);
        paused = true;
        releasedQ = false;
      } else if (game.key == 'l' && releasedL) {
        super.activeMenu = new LoadLevelMenu(game, "campaign") {
          @Override
          public void loadLevel(String path) {
            ArrayList<GameObject> objects = loadObjectsFrom(path);
            LevelEditor.this.objects = objects;
            blocks.clear();
            traps.clear();
            for (GameObject o : objects)
              o.createAndAddTo(LevelEditor.this);
            paused = false;
            activeMenu = null;
            blocks.add(new Block((PApplet) game,0, game.height - 30, 50000, 31));
          }
        };
        paused = true;
        releasedL = false;
      } else if (game.key == 'i' && releasedI) {
        playerA.invincible = !playerA.invincible;
        releasedI = false;
      }
    }

    if ((game.keyPressed && game.key != 'e') || !game.keyPressed)
      releasedE = true;
    if ((game.keyPressed && game.key != 'q') || !game.keyPressed)
      releasedQ = true;
    if ((game.keyPressed && game.key != 'l') || !game.keyPressed)
      releasedL = true;
    if ((game.keyPressed && game.key != 'i') || !game.keyPressed)
      releasedI = true;

    if (game.mousePressed && releasedMouse) {
      
      // If they've pressed left click, spawn an object.
      if (game.mouseButton == 37) {
        GameObject o = new GameObject();
        o.x = spawnX(type);
        o.y = spawnY(type);
        o.w = w();
        o.h = h();
        o.type = this.type;
        o.createAndAddTo(this);
        objects.add(o);
      } else if (game.mouseButton == 39) {
        // Right click cooresponds to deleting an object.
        
        ArrayList<GameObject> objectsToRemove = new ArrayList<GameObject>();
        for (GameObject o : objects) {
          Rectangle r = new Rectangle((PApplet) game, o.x, o.y, o.w, o.h);
          if (r.contains(spawnX(o.type), spawnY(o.type))) {
            o.removeFrom(this);
            objectsToRemove.add(o);
          }
        }
        for (GameObject o : objectsToRemove)
          objects.remove(o);
      }

      releasedMouse = false;
    }

    if (!game.mousePressed)
      releasedMouse = true;

    if (type == BLOCK) {
      dummyBlock.x = spawnX(BLOCK);
      dummyBlock.y = spawnY(BLOCK);
      dummyBlock.w = w();
      dummyBlock.h = h();
      dummyBlock.render(camera);
    } else if (type == SAW) {
      dummySaw.SPIKE_WIDTH = w();
      dummySaw.SPIKE_HEIGHT = w();
      dummySaw.startPoint.x = spawnX(SAW);
      dummySaw.startPoint.y = spawnY(SAW);
      dummySaw.render(camera);
    }
  }

  float w() {
    return w - (w % SNAP_GRID_SIZE);
  }

  float h() {
    return h - (h % SNAP_GRID_SIZE);
  }

  float spawnX(int type) {
    float x;
    if (type == BLOCK)
      x = (camera.x+game.mouseX)-game.width/2;
    else if (type == SAW)
      x = (camera.x+game.mouseX)+w/2;
    else
      throw new RuntimeException("Type of object to spawn unknown!");
    return x - (x % SNAP_GRID_SIZE);
  }

  float spawnY(int type) {
    float y;
    if (type == BLOCK)
      y = game.mouseY;
    else if (type == SAW)
      y = game.mouseY + h/2;
    else
      throw new RuntimeException("Type of object unknown");
    return y - (y % SNAP_GRID_SIZE);
  }

  void saveTo(String filename) {
    game.println("Saved level to " + filename);
    savedTime = game.millis();
    try {
      PrintWriter writer = new PrintWriter(filename, "UTF-8");
      for (GameObject o : objects)
        writer.println(o.toText());
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  LevelState getLevel(String filename) {
    final ArrayList<GameObject> objectsFromFile = loadObjectsFrom(filename);
    return new LevelState(game) {
      @Override
        public void spawnLevel() {
        blocks.add(new Block((PApplet) game, 0, game.height - 30, 50000, 31));
        for (GameObject o : objectsFromFile)
          o.createAndAddTo(this);
      }
    };
  }
  
  ArrayList<GameObject> loadObjectsFrom(String filename) {
    ArrayList<GameObject> objectsFromFile = new ArrayList<GameObject>();
    try {
      BufferedReader br = new BufferedReader(new FileReader(filename));
      String line;
      while ((line = br.readLine()) != null) {
        GameObject o = new GameObject();
        o.fromText(line);
        objectsFromFile.add(o);
      }
      br.close();
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
    return objectsFromFile;
  }

  class GameObject {

    int type;
    float x, y, w, h;
    Object objectInLevel = null;

    String toText() {
      return type + ":" + x + ":" + y + ":" + w + ":" + h;
    }

    void fromText(String text) {
      String[] split = text.split(":");
      type = Integer.parseInt(split[0]);
      x = Float.parseFloat(split[1]);
      y = Float.parseFloat(split[2]);
      w = Float.parseFloat(split[3]);
      h = Float.parseFloat(split[4]);
    }

    void createAndAddTo(LevelState level) {
      if (objectInLevel != null)
        throw new RuntimeException("You shouldn't add the same object to the level twice.");
      
      if (type == BLOCK) {
        Block b = new Block((PApplet) game, x, y, w, h);
        level.blocks.add(b);
        objectInLevel = b;
      } else if (type == SAW) {
        SawTrap t = new SawTrap((PApplet) game, x, y, w);
        level.traps.add(t);
        objectInLevel = t;
      }
    }
    
    void removeFrom(LevelState level) {
      if (objectInLevel == null)
        throw new RuntimeException("Can't remove from level if you haven't added the object.");
      
      if (type == BLOCK) {
        level.blocks.remove((Block) objectInLevel);
      } else if (type == SAW) {
        level.traps.remove((SawTrap) objectInLevel);
      }
      objectInLevel = null;
    }
  }
}