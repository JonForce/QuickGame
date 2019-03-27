import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

class LevelEditor extends LevelState {

  final int
    BLOCK = 0, 
    SAW = 1;

  int type = BLOCK;
  float w = 30, h = 30;
  long savedTime = -100000000;

  boolean releasedMouse = true;
  boolean releasedE = true;
  boolean releasedQ = true;

  ArrayList<GameObject> objects = new ArrayList<GameObject>();

  Block dummyBlock = new Block(0, 0, 0, 0);
  SawTrap dummySaw = new SawTrap(0, 0);

  LevelEditor(QuickGame game) {
    super(game);
  }

  @Override
    void spawnLevel() {
    // Add a floor
    super.blocks.add(new Block(0, height - 30, 50000, 31));
  }

  @Override
    void render() {
    super.render();

    String modeText = "";
    if (type == BLOCK)
      modeText = "Block Mode";
    else if (type == SAW)
      modeText = "Saw Mode";

    fill(0);
    textSize(40);
    text(modeText, width/2, 75);
    textSize(30);
    text("width : " + w, width/2, 110);
    text("height : " + h, width/2, 145);

    text("------------", width/2, 170);
    text("Press e to switch objects", width/2, 185);
    text("User WASD to adjust the size", width/2, 225);

    if (millis() - savedTime < 1500) {
      textSize(100);
      fill(255);
      text("Saved!", width/2, height/2);
    }


    if (keyPressed) {
      if (key == 'd')
        w += 2;
      else if (key == 'a')
        w -= 2;
      else if (key == 'w')
        h += 2;
      else if (key == 's')
        h -= 2;
      else if (key == 'e' && releasedE) {

        if (type == BLOCK)
          type = SAW;
        else if (type == SAW)
          type = BLOCK;

        releasedE = false;
      } else if (key == 'q' && releasedQ) {
        saveTo(sketchPath() + "\\data\\test.txt");
        releasedQ = false;
      }
    }

    if ((keyPressed && key != 'e') || !keyPressed)
      releasedE = true;
    if ((keyPressed && key != 'q') || !keyPressed)
      releasedQ = true;

    if (mousePressed && releasedMouse) {
      GameObject o = new GameObject();
      o.x = spawnX();
      o.y = spawnY();
      o.w = w;
      o.h = h;
      o.type = this.type;
      o.createAndAddTo(this);
      objects.add(o);

      releasedMouse = false;
    }

    if (!mousePressed)
      releasedMouse = true;

    if (type == BLOCK) {
      dummyBlock.x = spawnX();
      dummyBlock.y = spawnY();
      dummyBlock.w = w;
      dummyBlock.h = h;
      dummyBlock.render(camera);
    } else if (type == SAW) {
      dummySaw.SPIKE_WIDTH = w;
      dummySaw.SPIKE_HEIGHT = w;
      dummySaw.startPoint.x = spawnX();
      dummySaw.startPoint.y = spawnY();
      dummySaw.render(camera);
    }
  }

  float spawnX() {
    if (type == BLOCK)
      return (camera.x+mouseX)-width/2 - w/2;
    else if (type == SAW)
      return (camera.x+mouseX);
    else
      throw new RuntimeException("Type of object to spawn unknown!");
  }

  float spawnY() {
    if (type == BLOCK)
      return mouseY - h/2;
    else
      return mouseY;
  }

  void saveTo(String filename) {
    println("Saved level to " + filename);
    savedTime = millis();
    try {
      PrintWriter writer = new PrintWriter(filename, "UTF-8");
      for (GameObject o : objects)
        writer.println(o.toText());
      writer.close();
    } 
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  LevelState getLevel(String filename) {
    final ArrayList<GameObject> objectsFromFile = new ArrayList<GameObject>();
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

    return new LevelState(game) {
      @Override
        public void spawnLevel() {
        blocks.add(new Block(0, height - 30, 50000, 31));
        for (GameObject o : objectsFromFile)
          o.createAndAddTo(this);
      }
    };
  }

  class GameObject {

    int type;
    float x, y, w, h;

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
      if (type == BLOCK) {
        level.blocks.add(new Block(x, y, w, h));
      } else if (type == SAW) {
        level.traps.add(new SawTrap(x, y, w));
      }
    }
  }
}